package au.jcu.edu.tipcalculatorv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences savedSettings;
    Button doneBtn;
    TextView userInput;
    int tipPercent;
    SeekBar tipSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Objects.requireNonNull(getSupportActionBar()).hide();

//        Retrieve saved settings
        savedSettings = getApplicationContext().getSharedPreferences("savedSettings", Context.MODE_PRIVATE);
        tipPercent = savedSettings.getInt("tipPercent", 0);

        doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(doneClicked);


//        Display current tip percentage & seek user input
        tipSeekBar = findViewById(R.id.speedSeekBar);
        tipSeekBar.setProgress(tipPercent);
        tipSeekBar.setOnSeekBarChangeListener(new SeekBarListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                super.onProgressChanged(seekBar, i, b);
                tipPercent = i;
                userInput.setHint(tipPercent + "%");
            }
        });

        userInput = findViewById(R.id.tipPercent);
        userInput.setHint(tipPercent + "%");
    }

    //    Save and pass tip percentage to MainActivity
    private void saveSettings(){
        String text = userInput.getText().toString();
        if(!TextUtils.isEmpty(text)) {
            tipPercent = Integer.parseInt(text);
        }

        SharedPreferences.Editor editor = savedSettings.edit();
        editor.putInt("tipPercent", tipPercent);
        editor.apply();

        Intent intent = new Intent();
        intent.putExtra("tipPercent", tipPercent);
        setResult(RESULT_OK, intent);
        finish();
    }

    //    Done button click event
    private final View.OnClickListener doneClicked = view -> saveSettings();

    //    On Back button press without Done, clear field and restore saved tip percentage
    @Override
    public void onBackPressed(){
        userInput.setText("");
        saveSettings();
        super.onBackPressed();
    }

}