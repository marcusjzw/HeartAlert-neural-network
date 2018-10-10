package com.mwon724.heartalert;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TimingLogger;
import android.widget.TextView;
import org.tensorflow.contrib.android.*;
import com.google.common.primitives.Ints;

import java.math.BigDecimal;
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

    List rriValuesList = new ArrayList<>();
    TextView rriValuesReceived;

    protected void onCreate(Bundle savedInstanceState) {
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00C4CD")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neural);
        Log.d(TAG, "onCreate: Neural Screen Entered");
        TimingLogger timings = new TimingLogger(TAG, "NN-log");
        inferenceInterface = new TensorFlowInferenceInterface(getAssets(), MODEL_FILE);
        rriValuesList = getIntent().getStringArrayListExtra("RRI_VALUES");
        int[] rriIntArray = Ints.toArray(rriValuesList); // convert Integer list to primitive array
        rriValuesReceived = (TextView) findViewById(R.id.receivedRRIText);
        rriValuesReceived.append("\n" + rriValuesList);
        rriValuesReceived.append("\n\n\nSending for prediction into neural network...");

        // 1. convert to VLF, LF, HF, LF/HF....
        float[] hrvParameterArray = {0, 0, 0, 0};
        List<HRVParameter> hrvParameterList = freqParamCalculation(rriIntArray);
        hrvParameterList.get(2).setValue(hrvParameterList.get(2).getValue()/10000);
        for (int i = 0; i < 4; i++) {
            BigDecimal bd = new BigDecimal(hrvParameterList.get(i).getValue());
            rriValuesReceived.append("\n"+hrvParameterList.get(i).getName()+": " +
                    bd.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
            hrvParameterArray[i] = (float)hrvParameterList.get(i).getValue();
        }
        timings.addSplit("finished HRV calc");
        // fetch prediction from .pb
        float[] classifierResult = new float[1];
        inferenceInterface.feed(INPUT_NODE, hrvParameterArray, 1, 4);
        inferenceInterface.run(new String[] {OUTPUT_NODE});
        inferenceInterface.fetch(OUTPUT_NODE, classifierResult);
        String labelResult;
        if (classifierResult[0] == 1 || DataHandler.getInstance().getDeviceStatus()) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(NeuralNetActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(NeuralNetActivity.this);
            }
            builder.setTitle("Ventricular Tachycardia Detected!")
                    .setMessage("Do you want to call emergency services?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // call emergency services. This is placeholder
                            try {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:123456789"));
                                startActivity(intent);
                            } catch (SecurityException e) {
                                // do nothing
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            labelResult = "The onset of ventricular tachycardia has been detected. Call emergency services!!";
        }
        else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(NeuralNetActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(NeuralNetActivity.this);
            }
            builder.setTitle("Normal condition detected.")
                    .setMessage("You're fine!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            labelResult = "No anomalies detected.\n";
        }
        timings.addSplit("finished NN predict");
        timings.dumpToLog();
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
