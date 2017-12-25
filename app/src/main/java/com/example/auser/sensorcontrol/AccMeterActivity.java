package com.example.auser.sensorcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;

public class AccMeterActivity extends Activity {

    SensorManager sensor_manager;
    Sensor sensor;
    Context context;
    ImageView upView,downView,liftView,rightView;
    TextView textView;
    private final String TAG = "Sensor_ACC";
    MyListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_meter);
        context = this;
        findViews();
        Log.d(TAG,"ACC activity start");
        Toast.makeText(context,"Enter AccMetertivity",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Textview OK");
        textView.setText("");
        sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new MyListener();
        sensor_manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    void findViews(){
        context = this;
        textView = (TextView)findViewById(R.id.ACC_textView);
        upView = (ImageView)findViewById(R.id.image_id1);
        downView = (ImageView)findViewById(R.id.image_id2);
        liftView = (ImageView)findViewById(R.id.image_id3);
        rightView = (ImageView)findViewById(R.id.image_id4);
    }

    private class MyListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {

            StringBuilder sb = new StringBuilder();
            sb.append("sensor： " + event.sensor.getName() +"\n");
            sb.append("values: " + "\n");
            sb.append("X : " + event.values[0]+ "\n");
            sb.append("Y : " + event.values[1]+ "\n");
            sb.append("Z : " + event.values[2]+ "\n");

            String msg = sb.toString();
            textView.setText(msg);

            float X_value = event.values[0];
            float Y_value = event.values[1];
            float Z_value = event.values[2];

            if (X_value < -1.5){
                liftView.setVisibility(View.INVISIBLE);
                rightView.setVisibility(View.VISIBLE);
            } else if (X_value > 1.5) {
                liftView.setVisibility(View.VISIBLE);
                rightView.setVisibility(View.INVISIBLE);
            } else  {
                liftView.setVisibility(View.INVISIBLE);
                rightView.setVisibility(View.INVISIBLE);
            } if (Z_value > 9) {
                upView.setVisibility(View.VISIBLE);
                downView.setVisibility(View.INVISIBLE);
            } else if (Z_value < 5) {
                upView.setVisibility(View.INVISIBLE);
                downView.setVisibility(View.VISIBLE);
            } else  {
                upView.setVisibility(View.INVISIBLE);
                downView.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }


    }
}
