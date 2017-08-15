import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/8/15.
 */
public class CalculatorConfigMgrTest {

    private CalculatorConfigMgr calculatorConfigMgr;


    @Before
    public void init() throws IOException, CalculatorConfigParseException {
        calculatorConfigMgr = new CalculatorConfigMgr();
    }

    @Test
    public void getPeriod() {
        Assert.assertEquals("night", calculatorConfigMgr.getPeriod(new LocalTime(0,0,0)));
        Assert.assertEquals("night", calculatorConfigMgr.getPeriod(new LocalTime(23,0,0)));
        Assert.assertEquals("day", calculatorConfigMgr.getPeriod(new LocalTime(6,0,0)));
        Assert.assertEquals("day", calculatorConfigMgr.getPeriod(new LocalTime(10,0,0)));
    }

    @Test
    public void checkExistence() {
        Assert.assertNotNull(calculatorConfigMgr.getConfig(AreaEnum.DOWNTOWN, new LocalTime(10, 0, 0)));
        Assert.assertNotNull(calculatorConfigMgr.getConfig(AreaEnum.DOWNTOWN, new LocalTime(0, 0, 0)));
        Assert.assertNotNull(calculatorConfigMgr.getConfig(AreaEnum.SUBURB, new LocalTime(10, 0, 0)));
        Assert.assertNotNull(calculatorConfigMgr.getConfig(AreaEnum.SUBURB, new LocalTime(0, 0, 0)));
    }

    @Test
    public void getConfig() {
        CalculatorConfigRow config = calculatorConfigMgr.getConfig(AreaEnum.DOWNTOWN, new LocalTime(10, 0, 0));
        Assert.assertNotNull(config);
        Assert.assertEquals(AreaEnum.DOWNTOWN.name(), config.getArea());
        Assert.assertEquals("day", config.getPeriod());
        Assert.assertEquals(BigDecimal.valueOf(3), config.getInitialDistance());
        Assert.assertEquals(BigDecimal.valueOf(14), config.getInitialPrice());
        Assert.assertEquals("06:00:00", config.getNightEnd().toString("HH:mm:ss"));
        Assert.assertEquals("23:00:00", config.getNightStart().toString("HH:mm:ss"));
    }
}
