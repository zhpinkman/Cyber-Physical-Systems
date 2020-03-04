package mohsen.zhivar.ali.superspinnerbros.Logic;

import android.util.Log;
import android.widget.ImageView;

import mohsen.zhivar.ali.superspinnerbros.Config.Config;

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
        Log.d("Image", x + "|" + y + "|" + imageView.getX());
        this.m = m;
        x = 50;
        y = 50;
    }

    public void move(double intervalSeconds) {
        x = 0.5 * ax * Math.pow(intervalSeconds, 2) + vx * intervalSeconds + x;
//        Log.d("T", intervalSeconds + "|" + 0.5 * ax * Math.pow(intervalSeconds, 2));
        y = 0.5 * ay * Math.pow(intervalSeconds, 2) + vy * intervalSeconds + y;
        vx = ax * intervalSeconds + vx;
        vy = ay * intervalSeconds + vy;
        Log.d("POS", "" + x + "|" + y);
        Log.d("F", "" + ax + "|" + ay);
        refreshImage();
    }

    private void refreshImage() {
        imageView.setX((float) x);
        imageView.setY((float) y);
    }

    public void updateAcceleration(double angleX, double angleY, double angleZ) {
        double fX = m * Config.g * Math.sin(angleY);
        double fY = m * Config.g * Math.sin(angleX);
        double N = m * Config.g * Math.cos(Math.atan(euclideanNorm(Math.sin(angleX), Math.sin(angleY)) / (Math.cos(angleX) + Math.cos(angleY))));  // ZHIVAR TODO Prove cos theta = cosx*cosy/2
        Log.d("NG1", fX + "|" + fY + "|" + N);
        if (this.isMoving() || this.canMove(fX, fY, N)) {
            double frictionMagnitude = N * Config.M_k;
            double frictionX = 0;
            double frictionY = 0;
            if (euclideanNorm(vx, vy) > 0) {
                frictionX = frictionMagnitude * vx / euclideanNorm(vx, vy);
                frictionY = frictionMagnitude * vy / euclideanNorm(vx, vy);
            }
            fX += -Math.signum(vx) * frictionX;
            fY += -Math.signum(vy) * frictionY;
            Log.d("NG2", fX + "|" + fY + "|" + frictionMagnitude + "|" + frictionX + "|" + vx + "|" + vy);
        } else {
            fX = 0;
            fY = 0;
        }
        ax = fX / m;
        ay = fY / m;

        ax *= Config.SPEED_UP;
        ay *= Config.SPEED_UP;
    }

    private boolean canMove(double fX, double fY, double N) {
        double fMagnitude = euclideanNorm(fX, fY);
        double frictionMagnitude = N * Config.M_k;
        return fMagnitude > frictionMagnitude;
    }

    private double euclideanNorm(double a, double b) {  // Length of Vector (Magnitude)
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    private boolean isMoving() {
        if (euclideanNorm(vx, vy) < Config.BALL_STOP_SPEED_THRESHOLD)
            return false;
        else
            return true;
    }
}
