package com.mwon724.heartalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.lang.reflect.Array;
import android.content.res.AssetManager;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetActivity extends Activity{
    private static final String TAG = "NeuralNetActivity";
    List rriValuesList = new ArrayList<>();
    TextView rriValuesReceived;

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
        // fetch prediction from .pb

    }

    protected int predictHeartState(ArrayList list) {
        return 0;
    }
}
