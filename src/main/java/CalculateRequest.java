import org.joda.time.LocalTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculateRequest {
    private LocalTime time;
    private AreaEnum area;
    private BigDecimal distance;

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }
}
