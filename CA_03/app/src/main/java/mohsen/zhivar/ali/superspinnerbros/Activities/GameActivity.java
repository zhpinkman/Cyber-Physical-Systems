package mohsen.zhivar.ali.superspinnerbros.Activities;

//import android.app.Activity;
//import android.content.Context;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

import mohsen.zhivar.ali.superspinnerbros.Config.Config;
import mohsen.zhivar.ali.superspinnerbros.Config.Config.sensorType;
import mohsen.zhivar.ali.superspinnerbros.Logic.BoardManager;
import mohsen.zhivar.ali.superspinnerbros.R;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    protected BoardManager boardManager;
    protected sensorType sensorType;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float timestamp;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);
        context = this;

        sensorType = (sensorType) getIntent().getExtras().get("sensor");
        ((TextView) findViewById(R.id.textViewSensor)).setText(sensorType.toString());

        initBoard();
        initSensor(sensorType);

        ConstraintLayout screen = findViewById(R.id.frameLayout);

        screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d("Touch", event.getX() + "|" + event.getY());
                if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    ImageView imageView = new ImageView(context);

                    ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.frameLayout);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    constraintLayout.addView(imageView, layoutParams);
                    if (boardManager.getBallsCount() == 0) {
                        imageView.setImageResource(R.drawable.ball1);
                        boardManager.addBall(imageView, Config.Mass1, event.getX(), event.getY());
                    } else if (boardManager.getBallsCount() == 1) {
                        imageView.setImageResource(R.drawable.ball5);
                        boardManager.addBall(imageView, Config.Mass2, event.getX(), event.getY());
                    } else {
                        imageView.setImageResource(R.drawable.ball);
                        boardManager.addBall(imageView, makeRandomInt(1, 5000), event.getX(), event.getY());
                    }
                }
                return true;
            }
        });
    }

    private int makeRandomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    protected void initSensor(sensorType sensorType) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorType.equals(Config.sensorType.GYROSCOPE)) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }
    }

    protected void initBoard() {
        ImageView ballImageView1 = findViewById(R.id.ball1);
        ImageView ballImageView2 = findViewById(R.id.ball2);
        boardManager = new BoardManager(getDisplayWidthHeight());
//        boardManager.addBall(ballImageView1, Config.Mass1);
//        boardManager.addBall(ballImageView2, Config.Mass2);
    }

    protected Pair getDisplayWidthHeight() {
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;
        return new Pair(maxX, maxY);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float dT = (event.timestamp - timestamp) * Config.NS2S;
        if (timestamp != 0) {
            if (sensorType.equals(Config.sensorType.GYROSCOPE)) {
                float axisSpeedX = event.values[0];
                float axisSpeedY = event.values[1];
                float axisSpeedZ = event.values[2];
//                System.out.println(" A "+axisSpeedX + "|" + axisSpeedY + "|" + axisSpeedZ + "|" + dT);
                boardManager.updateAnglesByGyroscope(axisSpeedX, axisSpeedY, axisSpeedZ, dT);
            } else {

            }
        }
        timestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
