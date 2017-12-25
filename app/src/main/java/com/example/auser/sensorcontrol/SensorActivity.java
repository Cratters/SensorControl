package com.example.auser.sensorcontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends Activity {

    ListView sensorListView;
    SensorManager sensor_manager;
    List<Sensor> sensorList;
    List<String> listName;
    ArrayAdapter<String> adapter;
    Context context;
    String sensorName;
    Sensor sensor;
    Intent newrActivityIntent;
    private final String TAG ="SensorList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorListView = (ListView) findViewById(R.id.listView_id);
        sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensor_manager.getSensorList(Sensor.TYPE_ALL);
        listName = new ArrayList<>();
        context = this;
        for (Sensor sensor : sensorList){
            listName.add(sensor.getType() + "-" + sensor.getName() + "：" + sensor.getPower() + "mA");
        }
        adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, listName);
        sensorListView.setAdapter(adapter);

        setTitle("Sensor List number：" + listName.size());

        sensorListView.setOnItemClickListener( itemOnClick );
//        Toast.makeText( context, sensorName, Toast.LENGTH_SHORT).show();
    }

    AdapterView.OnItemClickListener itemOnClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Log.d(TAG,"Item on click");
            String sensorName = adapter.getItemAtPosition(position).toString();
            Toast.makeText( SensorActivity.this, sensorName, Toast.LENGTH_SHORT).show();

            int index = sensorName.indexOf('-');
            String sensorType = sensorName.substring(0,index);
            int sensorId = Integer.valueOf(sensorType);

            switch (sensorId){
                case Sensor.TYPE_ACCELEROMETER:
                    sensor = sensor_manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                    if(sensor == null){
                        Toast.makeText(context," there is no ACC sensor.",Toast.LENGTH_SHORT).show();
                    } else {
                        newrActivityIntent =new Intent(context, AccMeterActivity.class);
                        startActivity(newrActivityIntent);
                    }

                    break;
                case Sensor.TYPE_LIGHT:

                    break;
                case Sensor.TYPE_PROXIMITY:

                    break;

                default:
                    Toast.makeText(context,"This function does not work",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

}
