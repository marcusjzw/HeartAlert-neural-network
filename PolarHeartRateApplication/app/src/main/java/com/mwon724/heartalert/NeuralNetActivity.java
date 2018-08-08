package com.mwon724.heartalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetActivity extends Activity{
    private static final String TAG = "NeuralNetActivity";
    List rriValuesList = new ArrayList<>();
    TextView rriValuesReceived;
    // TensorFlowInferenceInterface tensorflow = new TensorFlowInferenceInterface(getAssets(), "file:///android_asset/opt_vt_classification_tf.pb");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neural);
        Log.d(TAG, "onCreate: Neural Screen Entered");

        Button btnToMain = (Button) findViewById(R.id.btnGoToMain);
        btnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked back to main");
                Intent intent = new Intent(NeuralNetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rriValuesList = getIntent().getStringArrayListExtra("RRI_VALUES");
        rriValuesReceived = (TextView) findViewById(R.id.receivedRRIText);
        rriValuesReceived.append("\n" + rriValuesList);
        rriValuesReceived.append("\n Sending for prediction into neural network...");
        // 1. convert to LF, HF, LF/HF....
        // fetch prediction from .pb
        // convert ArrayList to int array, so it can be loaded onto a tensor
        int[] rriArray = Ints.toArray(rriValuesList);
        Log.d(TAG, "ARRAY CONVERSION: " + rriArray.toString());
        // load input? Node names: dense_1_input, dense_2/Relu, dense_3/Sigmoid
        // tensorflow.feed();
        // display result
    }

    protected int predictHeartState(ArrayList list) {
        return 0;
    }

}
