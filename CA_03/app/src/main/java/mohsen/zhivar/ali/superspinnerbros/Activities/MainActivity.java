package mohsen.zhivar.ali.superspinnerbros.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mohsen.zhivar.ali.superspinnerbros.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonGyro = findViewById(R.id.button_gyro);
        Button buttonGravity = findViewById(R.id.button_gravity);

        buttonGyro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("sensor", "gyroscope");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        buttonGravity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("sensor", "gravity");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
