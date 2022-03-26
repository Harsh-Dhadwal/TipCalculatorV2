package au.jcu.edu.tipcalculatorv2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    SharedPreferences savedSettings;
    ImageView settingsBtn;
    TextView billInput, showTotal, showTipPercent, showSplitBill;
    NumberPicker splitPicker;
    BillCalculator calculator;
    float inputAmount, totalBill, perPersonBill;
    int splitNum, tipPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

//       store/retrieve saved settings
        savedSettings = getSharedPreferences("savedSettings", Context.MODE_PRIVATE);

        splitPicker = findViewById(R.id.splitPicker);
        splitPicker.setMinValue(1);
        splitPicker.setMaxValue(100);
        splitPicker.setOnValueChangedListener((numberPicker, i, i1) -> refreshDisplay());

        showTotal = findViewById(R.id.showTotal);
        showTipPercent = findViewById(R.id.showTipPercent);
        showSplitBill = findViewById(R.id.showSplitBill);

        billInput = findViewById(R.id.billInput);
        billInput.setOnClickListener(billFieldClicked);
        billInput.addTextChangedListener(new BillInputListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                refreshDisplay();
            }
        });
//        Minimize keyboard on Done button click
        billInput.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            }
            return false;
        });

        settingsBtn = findViewById(R.id.settings);
        settingsBtn.setOnClickListener(settingsClicked);

//       Preserve previous state
        if(savedInstanceState == null){
            tipPercent = 10;
            totalBill = perPersonBill = 0;
        } else {
            tipPercent = savedInstanceState.getInt("tipPercent");
            splitNum = savedInstanceState.getInt("splitNum");
            inputAmount = savedInstanceState.getFloat("inputAmount");
            totalBill = savedInstanceState.getFloat("totalBill");
            perPersonBill = savedInstanceState.getFloat("perPersonBill");
            splitPicker.setValue(splitNum);
            refreshDisplay();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tipPercent", tipPercent);
        outState.putInt("splitNum", splitNum);
        outState.putFloat("inputAmount", inputAmount);
        outState.putFloat("totalBill", totalBill);
        outState.putFloat("perPersonBill", perPersonBill);
    }

//    Clear bill input field on click
    private final View.OnClickListener billFieldClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            billInput.setText("");
            refreshDisplay();
        }
    };

    void refreshDisplay(){
        String userBill = billInput.getText().toString();

        if (!TextUtils.isEmpty(userBill)) {
            inputAmount = Float.parseFloat(userBill);
            splitNum = splitPicker.getValue();

            calculator = new BillCalculator();
            calculator.calculate(inputAmount, tipPercent, splitNum);

            totalBill = calculator.getTotalAmount(Boolean.TRUE);
            perPersonBill = calculator.getPerPersonAmount(Boolean.TRUE);

            showTotal.setText(String.format(Locale.getDefault(), "$%1.2f", totalBill));
            showTipPercent.setText(String.format(Locale.getDefault(), "%d%%", tipPercent));
            showSplitBill.setText(String.format(Locale.getDefault(), "$%1.2f", perPersonBill));
        } else {
            showTotal.setText("");
            showTipPercent.setText("");
            showSplitBill.setText("");
        }
    }

    //    Settings button click event
    private final View.OnClickListener settingsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences.Editor editor = savedSettings.edit();
            editor.putInt("tipPercent", tipPercent);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            launchSettings.launch(intent);
        }
    };

    ActivityResultLauncher<Intent> launchSettings = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        if(data != null){
                            tipPercent = data.getIntExtra("tipPercent", 10  );
                        }
                        refreshDisplay();
                    }
                }
            }

    );
}