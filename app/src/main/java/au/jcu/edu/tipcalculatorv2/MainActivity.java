package au.jcu.edu.tipcalculatorv2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

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

        splitPicker = findViewById(R.id.splitPicker);
        splitPicker.setMinValue(1);
        splitPicker.setMaxValue(100);
        splitPicker.setValue(1);
        splitPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                refreshDisplay();
            }
        });

        showTotal = findViewById(R.id.showTotal);
        showTipPercent = findViewById(R.id.showTipPercent);
        showSplitBill = findViewById(R.id.showSplitBill);

        billInput = findViewById(R.id.billInput);
        billInput.addTextChangedListener(new BillInputListener() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                refreshDisplay();
            }
        });
    };

    void refreshDisplay(){
        String userBill = billInput.getText().toString();

        tipPercent = 10;
        if (!TextUtils.isEmpty(userBill)) {
            inputAmount = Float.parseFloat(userBill);
            splitNum = splitPicker.getValue();

            calculator = new BillCalculator();
            calculator.calculate(inputAmount, tipPercent, splitNum);

            totalBill = calculator.getTotalAmount();
            perPersonBill = calculator.getPerPersonAmount();

            showTotal.setText(String.format(Locale.getDefault(), "$%1.2f", totalBill));
            showTipPercent.setText(String.format(Locale.getDefault(), "%d%%", tipPercent));
            showSplitBill.setText(String.format(Locale.getDefault(), "$%1.2f", perPersonBill));
        }
    }
}