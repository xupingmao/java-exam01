import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.joda.time.LocalTime;
import sun.misc.IOUtils;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

    private String getPeriod(LocalTime localTime) {
        if (!localTime.isBefore(nightStart) && localTime.isBefore(nightEnd)) {
            return "night";
        } else {
            return "day";
        }
    }
}
