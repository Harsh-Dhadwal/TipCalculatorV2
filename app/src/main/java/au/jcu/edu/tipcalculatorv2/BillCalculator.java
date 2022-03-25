package au.jcu.edu.tipcalculatorv2;

public class BillCalculator {
    private float totalAmount;
    private float perPersonAmount;

    BillCalculator(){}

    void calculate(float billAmount, int tipPercent, int splitNum){

        float tipAmount = billAmount * tipPercent / 100;
        this.totalAmount = (float) Math.ceil((billAmount + tipAmount)*10)/10;
        this.perPersonAmount = (float) (Math.ceil((totalAmount / splitNum)*10)/10);
    }

    float getTotalAmount(){
        return this.totalAmount;
    }

    float getPerPersonAmount(){
        return this.perPersonAmount;
    }
}
