import org.joda.time.LocalTime;
import org.junit.Assert;
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

    @Test(expected = IllegalArgumentException.class)
    public void calculateWithNullRequest_1() {
        CalculateRequest request = new CalculateRequest();
        calculator.computePrice(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWithNullRequest_2() {
        CalculateRequest request = new CalculateRequest();
        request.setArea(AreaEnum.DOWNTOWN);
        calculator.computePrice(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWithNullRequest_3() {
        CalculateRequest request = new CalculateRequest();
        request.setArea(AreaEnum.DOWNTOWN);
        request.setDistance(BigDecimal.valueOf(-1));
        calculator.computePrice(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWithNullRequest_4() {
        CalculateRequest request = new CalculateRequest();
        request.setArea(AreaEnum.DOWNTOWN);
        request.setDistance(BigDecimal.valueOf(3));
        calculator.computePrice(request);
    }

    private void calculatePrice(String expected, AreaEnum area, LocalTime localTime, double distance) {
        CalculateRequest request = new CalculateRequest();
        request.setArea(area);
        request.setDistance(BigDecimal.valueOf(distance));
        request.setTime(localTime);
        BigDecimal price = calculator.computePrice(request);
        System.out.println(price);

        Assert.assertEquals(expected, price.toString());
    }

    @Test
    public void downtownDay() {
        // 起步价
        calculatePrice("14.00", AreaEnum.DOWNTOWN, new LocalTime(10,0), 2);

        // 14 + 2.5 * 0.5
        calculatePrice("15.25", AreaEnum.DOWNTOWN, new LocalTime(10,0), 3.5);

        // 14 + 2.5 * 1
        calculatePrice("16.50", AreaEnum.DOWNTOWN, new LocalTime(10,0), 4);

        // 超过10公里
        // 14 + 2.5 * 7 + 2 * 3.5
        calculatePrice("38.50", AreaEnum.DOWNTOWN, new LocalTime(10,0), 12);
    }

    @Test
    public void downtownNight() {
        // 起步价
        calculatePrice("18.00", AreaEnum.DOWNTOWN, new LocalTime(23,0), 2);

        // 18 + 3 * 0.5
        calculatePrice("19.50", AreaEnum.DOWNTOWN, new LocalTime(0,0), 3.5);

        // 18 + 3 * 1
        calculatePrice("21.00", AreaEnum.DOWNTOWN, new LocalTime(1,0), 4);

        // 超过10公里
        // 18 + 3 * 7 + 2 * 4.7
        calculatePrice("48.40", AreaEnum.DOWNTOWN, new LocalTime(5,59), 12);
    }

    @Test
    public void suburbDay() {
        // 起步价
        calculatePrice("14.00", AreaEnum.SUBURB, new LocalTime(6,0), 2);

        // 14 + 2.5 * 0.5
        calculatePrice("15.25", AreaEnum.SUBURB, new LocalTime(7,0), 3.5);

        // 14 + 2.5 * 1
        calculatePrice("16.50", AreaEnum.SUBURB, new LocalTime(8,0), 4);

        // 超过10公里
        // 14 + 2.5 * 9
        calculatePrice("36.50", AreaEnum.SUBURB, new LocalTime(22,59), 12);
    }

    @Test
    public void suburbNight() {
        // 起步价
        calculatePrice("18.00", AreaEnum.SUBURB, new LocalTime(23,0), 2);

        // 18 + 3 * 0.5
        calculatePrice("19.50", AreaEnum.SUBURB, new LocalTime(0,0), 3.5);

        // 18 + 3 * 1
        calculatePrice("21.00", AreaEnum.SUBURB, new LocalTime(1,0), 4);

        // 超过10公里
        // 18 + 3 * 9
        calculatePrice("45.00", AreaEnum.SUBURB, new LocalTime(5,59), 12);
    }

}
