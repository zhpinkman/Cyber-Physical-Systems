package mohsen.zhivar.ali.superspinnerbros.Logic;

public class DirectionVector {
    double x, y;

    DirectionVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double dotProduct(DirectionVector dv2) {
        return x*dv2.x + y*dv2.y;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

}
