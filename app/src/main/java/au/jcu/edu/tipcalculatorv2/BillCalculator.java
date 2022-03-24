package au.jcu.edu.tipcalculatorv2;

public class BillCalculator {
    private float totalAmount;
    private float perPersonAmount;

    BillCalculator(){}

    void calculate(float billAmount, int tipPercent, int splitNum){

        float tipAmount = billAmount * tipPercent / 100;
        this.totalAmount = billAmount + tipAmount;
        this.perPersonAmount = totalAmount / splitNum;
    }

    float getTotalAmount(){
        return this.totalAmount;
    }

    float getPerPersonAmount(){
        return this.perPersonAmount;
    }
}
