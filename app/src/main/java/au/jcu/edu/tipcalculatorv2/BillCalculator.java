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

    float getTotalAmount(Boolean roundUpCents){
        if (roundUpCents){
            return roundUp(this.totalAmount);
        } else {
            return this.totalAmount;
        }
    }

    float getPerPersonAmount(Boolean roundUpCents){
        if (roundUpCents){
            return roundUp(this.perPersonAmount);
        } else {
            return this.perPersonAmount;
        }
    }

    private float roundUp(float amount) {
        return (float) (Math.ceil((amount)*10)/10);
    }
}
