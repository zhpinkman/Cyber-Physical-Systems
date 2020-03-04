package mohsen.zhivar.ali.superspinnerbros.Logic;

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
    double angleX, angleY, angleZ;  // Only X and Y matter // so why we have angleZ :)))

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
            ball.updateVelocity(intervalSeconds, this);
        }

        Pair<Double, Double> ball1NewPositions = balls.get(0).getNextPosition(intervalSeconds);
        Pair<Double, Double> ball2NewPositions = balls.get(1).getNextPosition(intervalSeconds);

//        if (doBallsHit(ball1NewPositions.first, ball2NewPositions.first, ball1NewPositions.second, ball2NewPositions.second)) {
//            handleBallCollision(ball1NewPositions, ball2NewPositions);
//            handleBallCollision(ball1NewPositions, ball2NewPositions);
//        }

        balls.get(0).handleWallCollision(ball1NewPositions.first, ball1NewPositions.second, this);
        balls.get(1).handleWallCollision(ball2NewPositions.first, ball2NewPositions.second, this);

        for (Ball ball : balls) {
            ball.updatePosition(intervalSeconds);
        }
        return null;
    }

    public void handleBallCollision(Pair<Double, Double> ball1Positions, Pair<Double, Double> ball2Positions) {
        DirectionVector n = new DirectionVector(ball2Positions.first - ball1Positions.first, ball2Positions.second - ball1Positions.second);
        DirectionVector un = n.divideBy(n.magnitude());
        DirectionVector ut = new DirectionVector(- un.getY(), un.getX());
        DirectionVector v1 = new DirectionVector(balls.get(0).vx, balls.get(0).vy);
        DirectionVector v2 = new DirectionVector(balls.get(1).vx, balls.get(1).vy);
        double v1n = un.dotProduct(v1);
        double v2n = un.dotProduct(v2);
        double v1t = ut.dotProduct(v1);
        double v2t = ut.dotProduct(v2);
        double newV1t = v1t;
        double newV2t = v2t;
        double m1 = balls.get(0).m;
        double m2 = balls.get(1).m;
        double newV1n = (v1n*(m1 - m2) + 2*m2*v2n) / (m1 + m2);
        double newV2n = (v2n*(m2 - m1) + 2*m1*v1n) / (m1 + m2);
        DirectionVector newV1nVector = un.multiplyBy(newV1n);
        DirectionVector newV2nVector = un.multiplyBy(newV2n);
        DirectionVector newV1tVector = ut.multiplyBy(newV1t);
        DirectionVector newV2tVector = ut.multiplyBy(newV2t);

        DirectionVector newV1 = newV1nVector.plus(newV1tVector);
        DirectionVector newV2 = newV2nVector.plus(newV2tVector);

        balls.get(0).vx = newV1.getX();
        balls.get(0).vx = newV1.getY();
        balls.get(1).vx = newV2.getX();
        balls.get(1).vx = newV2.getY();
    }


    public boolean doBallsHit(double b1x, double b2x, double b1y, double b2y){
        return getDistanceOfPoints(b1x, b2x, b1y, b2y) < Config.BALL_WIDTH;
    }


    public double getDistanceOfPoints(double b1x, double b2x, double b1y, double b2y) {
        return Math.sqrt(Math.pow(b1x - b2x, 2) + Math.pow(b1y - b2y, 2));
    }

    public boolean doesHitWall(double x, double y){
        return x >= width || x <= 0 || y >= height || y <= 0;
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
