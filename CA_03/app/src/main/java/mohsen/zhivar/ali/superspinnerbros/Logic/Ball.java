package mohsen.zhivar.ali.superspinnerbros.Logic;

import android.widget.ImageView;

public class Ball {
    ImageView imageView;
    double m;  // Mass
    double x, y;  // Position
    double vx = 0, vy = 0;  // Velocity
    double ax = 0, ay = 0;  //acceleration

    public Ball(ImageView imageView, double m) {
        this.imageView = imageView;
        x = imageView.getX();
        y = imageView.getY();
    }
}
