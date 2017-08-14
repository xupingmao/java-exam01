import com.google.common.collect.Lists;
import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculatorConfiguration {

    private LocalTime nightStart;
    private LocalTime nightEnd;
    private String area;
    private String period;
    private List<BigDecimal> distanceRange = Lists.newArrayList();
    private List<BigDecimal> priceRange = Lists.newArrayList();

    public LocalTime getNightStart() {
        return nightStart;
    }

    public void setNightStart(LocalTime nightStart) {
        this.nightStart = nightStart;
    }

    public LocalTime getNightEnd() {
        return nightEnd;
    }

    public void setNightEnd(LocalTime nightEnd) {
        this.nightEnd = nightEnd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<BigDecimal> getDistanceRange() {
        return distanceRange;
    }

    public void setDistanceRange(List<BigDecimal> distanceRange) {
        this.distanceRange = distanceRange;
    }

    public List<BigDecimal> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(List<BigDecimal> priceRange) {
        this.priceRange = priceRange;
    }
}
