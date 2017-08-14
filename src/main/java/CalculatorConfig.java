import java.util.List;

/**
 * Created by xupingmao on 2017/8/15.
 */
public class CalculatorConfig {
    private String nightStart;
    private String nightEnd;
    private List<CalculatorConfigRow> configurations;

    public String getNightStart() {
        return nightStart;
    }

    public void setNightStart(String nightStart) {
        this.nightStart = nightStart;
    }

    public String getNightEnd() {
        return nightEnd;
    }

    public void setNightEnd(String nightEnd) {
        this.nightEnd = nightEnd;
    }

    public List<CalculatorConfigRow> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<CalculatorConfigRow> configurations) {
        this.configurations = configurations;
    }
}
