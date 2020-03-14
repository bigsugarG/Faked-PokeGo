package com.example.projectb;

//import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.webkit.*;
import android.webkit.GeolocationPermissions.Callback;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String EXTRA_MESSAGE = "com.example.projectb.MESSAGE";

    @Override
    public void onBackPressed(){
    }

    private SensorManager sensorManager;

    private float acc[] = new float[3];
    private float gyro[] = new float[3];
    private float mag[] = new float[3];

    TextView accview;
    TextView gyroview;
    TextView magview;
    TextView stepView;
    TextView totalpointview;
    TextView bagDrugview;
    TextView bagBallview;

    int step_flag = 0;
    int step_count = 0;
    float theta = 0;
    int drugStock=0;
    int ballStock=0;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        accview = (TextView)findViewById(R.id.acc);
        gyroview = (TextView)findViewById(R.id.gyro);
        magview = (TextView)findViewById(R.id.mag);
        stepView = (TextView)findViewById(R.id.step);

        Intent intent2 =getIntent();  //intentによる引数の受け取り方

        int totalPointshow1=intent2.getIntExtra(EXTRA_MESSAGE,0);//-1はデフォルト値なので何でもよい;
        totalpointview = (TextView)findViewById(R.id.totalpoint);
        totalpointview.setText("totalpoint: "+String.valueOf(totalPointshow1));

        drugStock = intent2.getIntExtra("DRUG",0);
        bagDrugview = findViewById(R.id.bagDrug);
        bagDrugview.setText("キズ薬: "+String.valueOf(drugStock));

        ballStock = intent2.getIntExtra("BALL",0);
        bagBallview = findViewById(R.id.bagBall);
        bagBallview.setText("モンスターボール: "+String.valueOf(ballStock));


        WebView webView1 = findViewById(R.id.webView1);

        class MainJsInterface{
            Intent intent =getIntent();  //intentによる引数の受け取り方
            private int totalpoint=intent.getIntExtra(EXTRA_MESSAGE,0);//-1はデフォルト値なので何でもよい;

            @JavascriptInterface
            public float stepDetection(){
                double sum = Math.sqrt(acc[0]*acc[0]+acc[1]*acc[1]+acc[2]*acc[2]);
                theta += gyro[2]*50/1000 *180/Math.PI;
                if(theta > 180) theta -= 360;
                if(theta < -180) theta += 360;

                if(sum>11.5){
                    if(step_flag==2){
                        step_flag=1;
                        step_count++;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                stepView.setText("step:"+step_count);
                            }
                        });
                        return theta;

                    }else if (step_flag==0){
                        step_flag=1;
                    }
                }else if(sum<8.5&&step_flag==1){
                    step_flag=2;
                }
//                stepView.setText("歩数："+step_count);
                return -1000;
            }

            @JavascriptInterface
            public void changeActivity(int gamepoint, int id){
                Context context = getApplicationContext();
                Toast toast =Toast.makeText(context,"BattlePhase", Toast.LENGTH_LONG);
                toast.setGravity(0,0,4);
                toast.show();
                totalpoint = totalpoint + gamepoint;
                Intent intent = new Intent(MainActivity.this,BattleActivity.class);
                intent.putExtra("A",gamepoint);
                intent.putExtra("B",totalpoint);
                intent.putExtra("C",id);
                intent.putExtra("D",drugStock);
                intent.putExtra("E",ballStock);
                startActivity(intent);
            }

            @JavascriptInterface
            public void addGoods(int goodId) {
                if(goodId==1){
                    drugStock = drugStock + 1;
                    bagDrugview.setText("キズ薬: "+String.valueOf(drugStock));
                }else if(goodId==2){
                    ballStock = ballStock + 1;
                    bagBallview.setText("モンスターボール: "+String.valueOf(ballStock));
                }
            }
        }

        //JavaScript有効化
        webView1.getSettings().setJavaScriptEnabled(true);

        //位置情報有効化
        webView1.getSettings().setGeolocationEnabled(true);
        webView1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, Callback callback){
                callback.invoke(origin, true, false);
            }
        });

        //ここで指定した名前で呼び出すことができる
        webView1.addJavascriptInterface(new MainJsInterface(), "Android");

        //map_display.htmlをロード
        webView1.loadUrl("file:///android_asset/mapDisplay.html");
    }



    @Override
    protected void onResume() {
        super.onResume();
        Sensor acc_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyro_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor mag_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorManager.registerListener((SensorEventListener)this, acc_sensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener((SensorEventListener)this, gyro_sensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener((SensorEventListener)this, mag_sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener)this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            acc[0] = event.values[0];
            acc[1] = event.values[1];
            acc[2] = event.values[2];

            accview.setText("acc: "+String.valueOf(acc[2]));
        }
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            gyro[0] = event.values[0];
            gyro[1] = event.values[1];
            gyro[2] = event.values[2];

            gyroview.setText("gyro: "+String.valueOf(gyro[2]));
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mag[0] = event.values[0];
            mag[1] = event.values[1];
            mag[2] = event.values[2];

            magview.setText("mag: "+String.valueOf(mag[2]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onClickGPS(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Reset this game.")
                .setTitle("WARNING!")
                .setNegativeButton("NO",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
        builder.show();
    }

    public void onClickData(View v){
        LinearLayout linearLayout = findViewById(R.id.data);
        if(linearLayout.getVisibility()==View.INVISIBLE) {
            linearLayout.setVisibility(View.VISIBLE);
        }else{linearLayout.setVisibility(View.INVISIBLE);}
    }

    public void onClickMenu(View v) {
        TextView bagview = findViewById(R.id.chackBag);
        TextView getDataview = findViewById(R.id.getdata);
        TextView resetview = findViewById(R.id.reset);
        LinearLayout linearLayout = findViewById(R.id.data);
        LinearLayout linearLayout1 = findViewById(R.id.baglist);

        if (getDataview.getVisibility() == View.INVISIBLE && resetview.getVisibility() == View.INVISIBLE) {
            getDataview.setVisibility(View.VISIBLE);
            resetview.setVisibility(View.VISIBLE);
            bagview.setVisibility(View.VISIBLE);
        } else {
            bagview.setVisibility(View.INVISIBLE);
            resetview.setVisibility(View.INVISIBLE);
            getDataview.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            linearLayout1.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickBag(View v){
        LinearLayout linearLayout = findViewById(R.id.baglist);
        if(linearLayout.getVisibility()==View.INVISIBLE){
            linearLayout.setVisibility(View.VISIBLE);
        }else{
            linearLayout.setVisibility(View.INVISIBLE);
        }
    }
}