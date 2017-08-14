import java.math.BigDecimal;

/**
 * Created by xupingmao on 2017/8/14.
 */
public interface Calculator {
    BigDecimal computePrice(CalculateRequest request);
}
