package com.study.android.zhangsht.mapsensor2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;


public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    ToggleButton mToggleButton;
    SensorManager mSensorManager = null;
    Sensor mMagneticSensor = null;
    Sensor mAccelerometerSensor = null;
    LocationManager mLocationManager = null;
    String provider = LocationManager.GPS_PROVIDER;
    LatLng latLng = null;
    float rotationDegree = 0;

//    private void updateWithLocation(Location location) {
//        if (location != null) {
//            CoordinateConverter converter  = new CoordinateConverter();
//            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.coord(new LatLng(location.getLatitude(), location.getLongitude()));
//            destLatLng = converter.convert();
//
//            MyLocationData.Builder data = new MyLocationData.Builder();
//            if (destLatLng != null) {
//                data.latitude(destLatLng.latitude);
//                data.longitude(destLatLng.longitude);
//                data.direction(rotationDegree);
//                mMapView.getMap().setMyLocationData(data.build());
//            }
//            if (mToggleButton.isChecked()) {
//                MapStatus mapStatus = new MapStatus.Builder().target(destLatLng).build();
//                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
//                mMapView.getMap().setMapStatus(mapStatusUpdate);
//            }
//        }
//    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        float[] accValues = null;
        float[] magValues = null;
        long lastShakeTime = 0;

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accValues = event.values.clone();
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    magValues = event.values.clone();
                    break;
                default:
                    break;
            }

            if (accValues != null && magValues != null) {
                //获取旋转角度
                float[] R = new float[9];
                float[] values = new float[3];
                SensorManager.getRotationMatrix(R, null, accValues, magValues);
                SensorManager.getOrientation(R, values);
                rotationDegree = (float) Math.toDegrees(values[0]);
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.direction(rotationDegree);

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);
                criteria.setAltitudeRequired(false);
                criteria.setBearingRequired(false);
                criteria.setCostAllowed(true);
                provider = mLocationManager.getBestProvider(criteria, true);
                mLocationManager.getLastKnownLocation(provider);
                Location location = mLocationManager.getLastKnownLocation(provider);
                if(location != null) {
                    CoordinateConverter converter  = new CoordinateConverter();
                    converter.from(CoordinateConverter.CoordType.GPS);
                    converter.coord(new LatLng(location.getLatitude(),location.getLongitude()));

                    LatLng desLatLng = converter.convert();
                    latLng = desLatLng;
                    builder.latitude(desLatLng.latitude);
                    builder.longitude(desLatLng.longitude);
                    builder.direction(rotationDegree);

                    MapStatusUpdate localMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(desLatLng).build());
                    mMapView.getMap().setMapStatus(localMapStatusUpdate);
                }
                mMapView.getMap().setMyLocationData(builder.build());
            }
            return;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Toast.makeText(MainActivity.this,  "now your location is " + location.toString(), Toast.LENGTH_SHORT);
                CoordinateConverter converter  = new CoordinateConverter();
                converter.from(CoordinateConverter.CoordType.GPS);
                converter.coord(new LatLng(location.getLatitude(),location.getLongitude()));

                LatLng desLatLng = converter.convert();
                latLng = desLatLng;
                MyLocationData.Builder builder = new MyLocationData.Builder();
                builder.latitude(desLatLng.latitude);
                builder.longitude(desLatLng.longitude);
                builder.direction(rotationDegree);
                mMapView.getMap().setMyLocationData(builder.build());

               if (mToggleButton.isChecked()) {
                   MapStatusUpdate localMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(desLatLng).build());
                   mMapView.getMap().setMapStatus(localMapStatusUpdate);
               }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mToggleButton = (ToggleButton) findViewById(R.id.tb_center);

        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLocationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pointer),100,100,true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
        mMapView.getMap().setMyLocationEnabled(true);
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,bitmapDescriptor);
        mMapView.getMap().setMyLocationConfigeration(myLocationConfiguration);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        provider = mLocationManager.getBestProvider(criteria, true);
        mLocationManager.getLastKnownLocation(provider);
        Location location = mLocationManager.getLastKnownLocation(provider);
        if(location != null) {
            CoordinateConverter converter  = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.GPS);
            converter.coord(new LatLng(location.getLatitude(),location.getLongitude()));

            LatLng desLatLng = converter.convert();
            latLng = desLatLng;
            MyLocationData.Builder builder = new MyLocationData.Builder();
            builder.latitude(desLatLng.latitude);
            builder.longitude(desLatLng.longitude);
            builder.direction(0);
            mMapView.getMap().setMyLocationData(builder.build());

            MapStatusUpdate localMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(desLatLng).build());
            mMapView.getMap().setMapStatus(localMapStatusUpdate);
        }

        mMapView.getMap().setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch ((motionEvent.getAction())) {
                    case MotionEvent.ACTION_MOVE:
                        mToggleButton.setChecked(false);
                        break;
                    default:
                        break;
                }
            }
        });

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToggleButton.isChecked()) {
                    MapStatusUpdate localMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(latLng).build());
                    mMapView.getMap().setMapStatus(localMapStatusUpdate);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

        mSensorManager.registerListener(mSensorEventListener, mMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(mSensorEventListener, mAccelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        mLocationManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
        mLocationManager.removeUpdates(mLocationListener);
    }
}
