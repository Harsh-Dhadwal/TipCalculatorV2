package au.jcu.edu.tipcalculatorv2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumberPicker splitPicker = findViewById(R.id.splitPicker);
        splitPicker.setMinValue(1);
        splitPicker.setMaxValue(100);
    }
}