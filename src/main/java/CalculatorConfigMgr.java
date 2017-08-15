import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.joda.time.LocalTime;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculatorConfigMgr {

    private static String configFile = "META-INF/calculator.json";

    private LocalTime nightStart;
    private LocalTime nightEnd;
    private List<CalculatorConfigRow> configRows = Lists.newArrayList();

    public CalculatorConfigMgr(String configFile) throws IOException, CalculatorConfigParseException {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream(configFile);
            Scanner sc = new Scanner(resourceAsStream).useDelimiter("\\A");
            String jsonStr = sc.hasNext() ? sc.next() : "";
            CalculatorConfig calculatorConf = JSON.parseObject(jsonStr, CalculatorConfig.class);
            processConf(calculatorConf);
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }


    public CalculatorConfigMgr() throws IOException, CalculatorConfigParseException {
        this(configFile);
    }


    private void processConf(CalculatorConfig calculatorConf) throws CalculatorConfigParseException {
        nightStart = LocalTime.parse(calculatorConf.getNightStart());
        nightEnd = LocalTime.parse(calculatorConf.getNightEnd());

        List<CalculatorConfigRow> configurations = calculatorConf.getConfigurations();
        for (CalculatorConfigRow row: configurations) {
            if (row.getPriceRange().size() != row.getDistanceRange().size()) {
                throw new CalculatorConfigParseException("priceRange mismatch distanceRange");
            }
            row.setNightStart(nightStart);
            row.setNightEnd(nightEnd);
            // 处理精度
            row.setInitialDistance(NumberUtils.round(row.getInitialDistance()));
            row.setInitialPrice(NumberUtils.round(row.getInitialPrice()));

            for (int i = 0; i < row.getDistanceRange().size(); i++) {
                BigDecimal step = row.getDistanceRange().get(i);
                BigDecimal price = row.getPriceRange().get(i);

                row.getDistanceRange().set(i, NumberUtils.round(step));
                row.getPriceRange().set(i, NumberUtils.round(price));
            }
            configRows.add(row);
        }
    }

    public CalculatorConfigRow getConfig(AreaEnum area, LocalTime localTime) {
        String period = getPeriod(localTime);
        for (CalculatorConfigRow row: configRows) {
            if (row.getArea().equals(area.name()) && row.getPeriod().equals(period)) {
                return row;
            }
        }
        return null;
    }

    public boolean isBetween(LocalTime target, LocalTime start, LocalTime end) {
        // 左包含又不包含
        return target.compareTo(start) >= 0 && target.compareTo(end) < 0;
    }

    public String getPeriod(LocalTime localTime) {
        if (nightStart.isBefore(nightEnd)) {
            if (isBetween(localTime, nightStart, nightEnd)) {
                return "night";
            } else {
                return "day";
            }
        } else {
            if (isBetween(localTime, nightEnd, nightStart)) {
                // 在夜间范围之外
                return "day";
            } else {
                return "night";
            }
        }
    }
}
