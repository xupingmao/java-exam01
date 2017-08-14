import org.junit.Test;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculatorTest {

    private Calculator calculator = new CalculatorImpl();

    @Test(expected = IllegalArgumentException.class)
    public void calculateWithNullRequest() {
        calculator.computePrice(null);
    }


}
