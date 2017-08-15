import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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

        if (request.getDistance().compareTo(config.getInitialDistance()) <= 0) {
            // 起步价
            return NumberUtils.round(config.getInitialPrice());
        }

        BigDecimal total = NumberUtils.round(BigDecimal.ZERO);
        total = total.add(config.getInitialPrice());
        BigDecimal distance = NumberUtils.round(request.getDistance());

        for (int i = 0; i < config.getDistanceRange().size(); i++) {
            BigDecimal start = config.getDistanceRange().get(i);
            BigDecimal price = config.getPriceRange().get(i);
            if (i+1 < config.getDistanceRange().size()) {
                BigDecimal end = config.getDistanceRange().get(i+1);
                if (distance.compareTo(end) <= 0) {
                    BigDecimal sum = distance.subtract(start).multiply(price);
                    total = total.add(sum);
                    break;
                } else {
                    BigDecimal sum = end.subtract(start).multiply(price);
                    total = total.add(sum);
                }
            } else {
                BigDecimal sum = request.getDistance().subtract(start).multiply(price);
                total = total.add(sum);
            }
        }
        return NumberUtils.round(total);
    }
}
