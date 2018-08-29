package com.mwon724.heartalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.tensorflow.contrib.android.*;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.nio.*;

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

    List rriValuesList = new ArrayList<>();
    TextView rriValuesReceived;
    // TensorFlowInferenceInterface tensorflow = new TensorFlowInferenceInterface(getAssets(), "file:///android_asset/opt_vt_classification_tf.pb");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neural);
        Log.d(TAG, "onCreate: Neural Screen Entered");
        inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);
        rriValuesList = getIntent().getStringArrayListExtra("RRI_VALUES");
        int[] rriIntArray = Ints.toArray(rriValuesList); // convert Integer list to primitive array
        rriValuesReceived = (TextView) findViewById(R.id.receivedRRIText);
        rriValuesReceived.append("\n" + rriValuesList);
        rriValuesReceived.append("\n\n\nSending for prediction into neural network...");

        // 1. convert to VLF, LF, HF, LF/HF....
        float[] hrvParameterArray = {0, 0, 0, 0};
        List<HRVParameter> hrvParameterList = freqParamCalculation(rriIntArray);
        for (int i = 0; i < 4; i++) {
            rriValuesReceived.append("\n"+hrvParameterList.get(i).getName()+": " +
                    hrvParameterList.get(i).getValue());
            hrvParameterArray[i] = (float)hrvParameterList.get(i).getValue();
        }

        // fetch prediction from .pb
        float[] classifierResult = new float[1];
        inferenceInterface.feed(INPUT_NODE, hrvParameterArray, 1, 4);
        inferenceInterface.run(new String[] {OUTPUT_NODE});
        inferenceInterface.fetch(OUTPUT_NODE, classifierResult);
        rriValuesReceived.append("\n\n\n\nRESULT: " + classifierResult[0]);
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
