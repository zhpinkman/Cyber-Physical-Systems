package mohsen.zhivar.ali.superspinnerbros.Activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Pair;
import android.view.Display;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mohsen.zhivar.ali.superspinnerbros.Config.Config;
import mohsen.zhivar.ali.superspinnerbros.Logic.BoardManager;
import mohsen.zhivar.ali.superspinnerbros.R;

public class GameActivity extends AppCompatActivity {

    protected BoardManager boardManager;
    protected String sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sensorType = getIntent().getStringExtra("sensor");
        ((TextView) findViewById(R.id.textViewSensor)).setText(sensorType);

        initBoard();
    }

    protected void initBoard() {
        ImageView ballImageView1 = findViewById(R.id.ball1);
        ImageView ballImageView2 = findViewById(R.id.ball2);
        boardManager = new BoardManager(getDisplayWidthHeight());
        boardManager.addBall(ballImageView1, Config.Mass1);
        boardManager.addBall(ballImageView2, Config.Mass2);
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
