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
import java.util.EnumSet;
import java.util.List;

import hrv.HRVLibFacade;
import hrv.RRData;
import hrv.calc.parameter.HRVParameter;
import hrv.calc.parameter.HRVParameterEnum;
import units.TimeUnit;

public class NeuralNetActivity extends Activity{
    private static final String TAG = "NeuralNetActivity";
    private static final String MODEL_FILE = "file:///android_asset/vt_classifier_75percent.pb";
    private static final String INPUT_NODE = "dense_1_input";
    private static final String OUTPUT_NODE = "output_node0";
    private TensorFlowInferenceInterface inferenceInterface;

    private static final long[] INPUT_SIZE = {1,70};
    List rriValuesList = new ArrayList<>();
    TextView rriValuesReceived;
    // TensorFlowInferenceInterface tensorflow = new TensorFlowInferenceInterface(getAssets(), "file:///android_asset/opt_vt_classification_tf.pb");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neural);
        Log.d(TAG, "onCreate: Neural Screen Entered");
        inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);

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
        int[] rriIntArray = Ints.toArray(rriValuesList); // convert Integer list to primitive array
        rriValuesReceived = (TextView) findViewById(R.id.receivedRRIText);
        rriValuesReceived.append("\n" + rriValuesList);
        rriValuesReceived.append("\n\n\nSending for prediction into neural network...");

        // 1. convert to VLF, LF, HF, LF/HF....
        List<HRVParameter> hrvParameterList = freqParamCalculation(rriIntArray);
        for (int i = 0; i < 4; i++) {
            rriValuesReceived.append("\n"+hrvParameterList.get(i).getName()+": " +
                    hrvParameterList.get(i).getValue()); // may need /100 on HF and LF
        }

        // fetch prediction from .pb
        // inferenceInterface.feed(INPUT_NODE, rriIntArray, INPUT_SIZE);
        // inferenceInterface.run(OUTPUT_NODES);
        // inferenceInterface.fetch(OUTPUT_NODE, result);
        rriValuesReceived.append("\n\n\n\nRESULT: " + result[0]);


        // convert ArrayList to int array, so it can be loaded onto a tensor
        Log.d(TAG, "ARRAY CONVERSION: " + rriIntArray.toString());
        // load input? Node names: dense_1_input, dense_2/Relu, dense_3/Sigmoid
        // tensorflow.feed();
        // display result
    }

    protected List<HRVParameter> freqParamCalculation(int[] rriValues) {
        double[] rriDoubleArray = new double[rriValues.length];
        for(int i = 0; i < rriValues.length; i++) {
            rriDoubleArray[i] = rriValues[i]; // convert int array to double array
        }
        // pass in double array
        RRData data = RRData.createFromRRInterval(rriDoubleArray, TimeUnit.MILLISECOND);

        HRVLibFacade facade = new HRVLibFacade(data);
        facade.setParameters(EnumSet.of(HRVParameterEnum.HF, HRVParameterEnum.LF,
                HRVParameterEnum.VLF, HRVParameterEnum.LFHF));

        return facade.calculateParameters();
    }

}
