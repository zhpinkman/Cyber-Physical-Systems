package mohsen.zhivar.ali.superspinnerbros.Logic;

import android.util.Pair;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class BoardManager {
    List<Ball> balls = new ArrayList<>();
    int width, height;

    public BoardManager(Pair<Integer, Integer> widthHeight) {
        width = widthHeight.first;
        height = widthHeight.second;
    }

    public void addBall(ImageView ballImageView, double mass) {
        balls.add(new Ball(ballImageView, mass));
    }
}
