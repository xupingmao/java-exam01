import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculatorImpl implements Calculator {

    private CalculatorConfigMgr calculatorConfigMgr;

    public CalculatorImpl() throws IOException, CalculatorConfigParseException {
        calculatorConfigMgr = new CalculatorConfigMgr();
    }

    @Override
    public BigDecimal computePrice(CalculateRequest request) {
        Preconditions.checkArgument(request != null, "request is null");
        Preconditions.checkArgument(request.getTime() != null, "time is null");
        Preconditions.checkArgument(request.getArea() != null, "area is null");
        Preconditions.checkArgument(request.getDistance() != null, "distance is null");
        Preconditions.checkArgument(request.getDistance().compareTo(BigDecimal.ZERO) > 0, "distance <= 0");

        CalculatorConfigRow config = calculatorConfigMgr.getConfig(request.getArea(), request.getTime());
        Preconditions.checkState(config != null, "no matching config");

        System.out.println(JSON.toJSONString(config, true));
        return null;
    }
}
