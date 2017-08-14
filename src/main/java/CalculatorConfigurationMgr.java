import org.joda.time.LocalTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.CharBuffer;
import java.util.Properties;

/**
 * Created by xupingmao on 2017/8/14.
 */
public class CalculatorConfigurationMgr {

    private static String configFile = "META-INF/calculator.json";
    private Properties properties = new Properties();

    private LocalTime nightStart;
    private LocalTime nightEnd;

    public CalculatorConfigurationMgr(String configFile) throws IOException, CalculatorConfigParseException {
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream(configFile);
            InputStreamReader reader = new InputStreamReader(resourceAsStream);
            reader.read(charBuffer);
            parseProperties();

        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }
    }

    private void parseProperties() throws CalculatorConfigParseException {
        nightStart = LocalTime.parse(properties.getProperty("night.start"));
        nightEnd = LocalTime.parse(properties.getProperty("night.end"));

        String areas = properties.getProperty("areas");

        if (areas == null || areas.isEmpty()) {
            throw new CalculatorConfigParseException("areas is null");
        }
        for (String area: areas.split(",")) {
            area = area.trim();
            if (area.length() > 0) {
                BigDecimal initialPrice = new BigDecimal(properties.getProperty(area + ".day.initialPrice"));
                BigDecimal initialDistance = new BigDecimal(properties.getProperty(area + ".day.initialDistance"));
                String distanceRange = properties.getProperty(area + ".day.distanceRange");

            }
        }
    }

    private void loadProperties(String name, String day) {

    }

    public CalculatorConfigurationMgr() throws IOException, CalculatorConfigParseException {
        this(configFile);
    }

}
