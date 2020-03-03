package mohsen.zhivar.ali.superspinnerbros.Logic;

import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mohsen.zhivar.ali.superspinnerbros.Config.Config;

public class BoardManager {
    List<Ball> balls = new ArrayList<>();
    int width, height;
    double angleX, angleY, angleZ;  // Only X and Y matter

    public BoardManager(Pair<Integer, Integer> widthHeight) {
        width = widthHeight.first;
        height = widthHeight.second;

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                moveBalls(((double)Config.BOARD_REFRESH_RATE)/1000);
            }
        }, 0, Config.BOARD_REFRESH_RATE);
    }

    protected TimerTask moveBalls(double intervalSeconds) {
        for (Ball ball : balls) {
            ball.updateAcceleration(angleX, angleY, angleZ);
            ball.move(intervalSeconds);
        }
        return null;
    }


    public void addBall(ImageView ballImageView, double mass) {
        balls.add(new Ball(ballImageView, mass));
    }

    public void updateAnglesByGyroscope(float axisSpeedX, float axisSpeedY, float axisSpeedZ, float dT) {
        angleX = angleX + axisSpeedX * dT;
        angleY = angleY + axisSpeedY * dT;
        angleZ = angleZ + axisSpeedZ * dT;
//        Log.i("ANGLE", ""+angleX + "|" + angleY + "|" + angleZ + "|" + dT);
    }


}
