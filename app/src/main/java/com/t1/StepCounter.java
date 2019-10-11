package com.t1;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StepCounter extends AppCompatActivity implements SensorEventListener, StepListener{

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private TextView TvSteps,calorie;
    private Button BtnStart,BtnStop;
    private EditText age,ht,wt;
    private double bmi,height,calburn,weight;
    private int age1;
    String h1,w1,a1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);


        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);
        age=(EditText)findViewById(R.id.age);
        ht=(EditText)findViewById(R.id.ht);
        wt=(EditText)findViewById(R.id.wt);
        calorie=(TextView) findViewById(R.id.cal);



        //bmi=weight/(height*height);



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(StepCounter.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                h1=ht.getText().toString();
                w1=wt.getText().toString();
                a1=age.getText().toString();
                height=Double.parseDouble(h1);
                weight=Double.parseDouble(w1);
                age1=Integer.parseInt(a1);
                height=height*39.37;
                weight=weight*2.205;
                bmi=weight/(height*height);
                calburn=numSteps*0.04*bmi*age1*3;
                calorie.setText("Congratulations!! You have burnt "+String.valueOf(calburn)+" calories");
                sensorManager.unregisterListener(StepCounter.this);

            }
        });



    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
        //calburn=numSteps*0.04*bmi*age1*3;
        //calorie.setText("Congratulations!! You have burnt"+calburn+"calories");
    }
}
