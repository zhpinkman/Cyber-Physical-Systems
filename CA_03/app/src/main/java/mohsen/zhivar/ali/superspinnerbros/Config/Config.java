package mohsen.zhivar.ali.superspinnerbros.Config;

public class Config {
    public static final double Mass1 = 0.01, Mass2 = 0.05;  // 1gr - 5gr
    public enum sensorType{GYROSCOPE, GRAVITY};
    public static final double g = 9.81;
    public static final int BOARD_REFRESH_RATE = 10; // 1 millisecond

    public static final float NS2S = 1.0f / 1000000000.0f;  // ns to second
    public static final double SPEED_UP = 40;
}
