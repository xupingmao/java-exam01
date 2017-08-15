import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by xupingmao on 2017/8/15.
 */
public class NumberUtils {

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
