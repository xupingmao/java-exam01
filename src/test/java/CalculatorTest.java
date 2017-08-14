import org.joda.time.LocalTime;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculatorTest {

    private Calculator calculator = new CalculatorImpl();

    public CalculatorTest() throws IOException, CalculatorConfigParseException {
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWithNullRequest() {
        calculator.computePrice(null);
    }

    @Test
    public void normal() {
        CalculateRequest request = new CalculateRequest();
        request.setArea(AreaEnum.DOWNTOWN);
        request.setDistance(BigDecimal.valueOf(3.5));
        request.setTime(new LocalTime(10,0,0));
        calculator.computePrice(request);
    }

}
