package au.jcu.edu.tipcalculatorv2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BillCalculatorTest {
    @Test
    public void calculation_isCorrect() {
        BillCalculator calculator = new BillCalculator();
        calculator.calculate(100, 10, 2);

        float totalAmount = calculator.getTotalAmount();
        assertEquals(110, totalAmount, 0);

        float perPersonAmount = calculator.getPerPersonAmount();
        assertEquals(55, perPersonAmount, 0);

    }
}