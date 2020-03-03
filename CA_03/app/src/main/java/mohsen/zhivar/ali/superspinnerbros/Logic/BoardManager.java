package mohsen.zhivar.ali.superspinnerbros.Logic;

import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BoardManager {
    List<Ball> balls = new ArrayList<>();
    int width, height;
    double angleX, angleY, angleZ;  // Only X and Y matter

    public BoardManager(Pair<Integer, Integer> widthHeight) {
        width = widthHeight.first;
        height = widthHeight.second;

    }

    public void addBall(ImageView ballImageView, double mass) {
        balls.add(new Ball(ballImageView, mass));
    }

    public void updateAnglesByGyroscope(float axisSpeedX, float axisSpeedY, float axisSpeedZ, float dT){
        angleX = angleX + axisSpeedX * dT;
        angleY = angleY + axisSpeedY * dT;
        angleZ = angleZ + axisSpeedZ * dT;
//        Log.i("ANGLE", ""+angleX + "|" + angleY + "|" + angleZ + "|" + dT);
    }
}
