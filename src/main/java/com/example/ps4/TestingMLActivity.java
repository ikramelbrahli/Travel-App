package com.example.ps4;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.ml.modeldownloader.CustomModel;
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions;
import com.google.firebase.ml.modeldownloader.DownloadType;
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.tensorflow.lite.task.text.nlclassifier.NLClassifier.createFromFile;

/** The main activity to provide interactions with users. */
public class TestingMLActivity extends AppCompatActivity {
    TensorBuffer modelOutput;
    Interpreter interpreter;
    File modelFile;
    Interpreter.Options options = new Interpreter.Options();
    private static final String TAG = "TextClassificationDemo";

    private TextView resultTextView;
    private EditText inputEditText;
    private ExecutorService executorService;
    private ScrollView scrollView;
    private Button predictButton;

    // TODO 5: Define a NLClassifier variable
    private NLClassifier textClassifier;
    private String outputString ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_m_l);
        Log.v(TAG, "onCreate");

        executorService = Executors.newSingleThreadExecutor();
        resultTextView = findViewById(R.id.result_text_view);
        inputEditText = findViewById(R.id.input_text);
        scrollView = findViewById(R.id.scroll_view);

        predictButton = findViewById(R.id.predict_button);
        predictButton.setOnClickListener(
                (View v) -> {
                    Log.d("test",inputEditText.getText().toString());
                    classify(inputEditText.getText().toString());
                //   String input= inputEditText.getText().toString();




                });

        // TODO 3: Call the method to download TFLite model
        downloadModel();
        try{
            interpreter = new Interpreter(loadModelFile());
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("C:\\Users\\Ikram\\AndroidStudioProjects\\PS4\\app\\src\\androidTest\\assets\\sentiment_analysis.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY ,startOffset,declaredLength);
    }
    /** Send input text to TextClassificationClient and get the classify messages. */
    private void classify(final String text) {
        executorService.execute(
                () -> {
                    Log.d("test2" , text);

                    // TODO 7: Run sentiment analysis on the input text
                    List<Category> results = textClassifier.classify("1");
                    // TODO 8: Convert the result to a human-readable text
                    String textToShow = "Input: " + text + "\nOutput:\n";
                    for (int i = 0; i < results.size(); i++) {
                        Category result = results.get(i);
                        textToShow += String.format("    %s: %s\n", result.getLabel(),
                                result.getScore());
                    }
                    textToShow += "---------\n";

                    // Show classification result on screen
                    showResult(textToShow);
                });
    }

    /** Show classification result on the screen. */
    private void showResult(final String textToShow) {
        // Run on UI thread as we'll updating our app UI
        runOnUiThread(
                () -> {
                    // Append the result to the UI.
                    resultTextView.append(textToShow);

                    // Clear the input text.
                    inputEditText.getText().clear();

                    // Scroll to the bottom to show latest entry's classification result.
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                });
    }

    // TODO 2: Implement a method to download TFLite model from Firebase

    private synchronized void downloadModel() {
        CustomModelDownloadConditions conditions = new CustomModelDownloadConditions.Builder()
                .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
                .build();
        FirebaseModelDownloader.getInstance()
                .getModel("sentiment_analysis", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND, conditions)
                .addOnSuccessListener(new OnSuccessListener<CustomModel>()
                                      {

                                          @Override
                                          public void onSuccess(CustomModel customModel)
                                          {


                                              // TODO 6: Initialize a TextClassifier with the downloaded model
                                              try {
                                                  textClassifier = createFromFile(customModel.getFile());
                                                  Log.d("inside test","machine learning test");
                                              } catch (IOException e) {
                                                  e.printStackTrace();
                                              }
                                              if (customModel.getFile() != null) {
                                                  interpreter = new Interpreter(customModel.getFile());
                                                  predictButton.setEnabled(true);
                                              }
                                              // Enable predict button

                                          }
                                      });


    }


}