package mohsen.zhivar.ali.superspinnerbros.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mohsen.zhivar.ali.superspinnerbros.Logic.BoardManager;
import mohsen.zhivar.ali.superspinnerbros.R;

public class GameActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String sensorType = intent.getStringExtra("sensor");
        TextView sensorTextView = findViewById(R.id.textViewSensor);
        sensorTextView.setText(sensorType);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        final double M1 = 0.01, M2 = 0.05;  // 1gr - 5gr
        ImageView ballImageView1 = findViewById(R.id.ball1);
        ImageView ballImageView2 = findViewById(R.id.ball2);

        BoardManager boardManager = new BoardManager(getDisplayWidthHeight());
        boardManager.addBall(ballImageView1, M1);
        boardManager.addBall(ballImageView2, M2);
    }

    protected Pair<Integer, Integer> getDisplayWidthHeight() {
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;
        return new Pair(maxX, maxY);
    }
}
