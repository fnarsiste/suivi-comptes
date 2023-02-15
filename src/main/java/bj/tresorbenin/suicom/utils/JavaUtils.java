package bj.tresorbenin.suicom.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

@SuppressWarnings("all")
public class JavaUtils {
    public static String pathParams(HttpServletRequest request, String key) {
        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return (String) getMapValue(pathVariables, key);
    }

    public static <K, V> V getMapValue(Map<K, V> params, String key) {
        if (!params.isEmpty())
            if (params.containsKey(key))
                return params.get(key);
        return null;
    }

    public static String getParams(Map<String, String> params, String key) {
        return getMapValue(params, key);
    }

    public static boolean notNullString(String stringValue) {
        return stringValue != null && !stringValue.equals("") && !stringValue.equals("null") && !stringValue.equals("NULL") && !stringValue.equals("undefined")
                && !stringValue.equals("UNDEFINED");
    }

    public static <K, E> Map<K, E> doPut(Map<K, E> map, K key, E value) {
        if (map == null)
            map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static boolean setModelAttribute(Model model, String key, Object value, Object def) {
        boolean okay = value != null;
        model.addAttribute(key, okay ? value : def);
        return okay;
    }

    public static boolean setModelAttribute(Model model, String key, Object value) {
        return setModelAttribute(model, key, value, "");
    }

    public static String doNVL(String value, String defaultValue) {
        return notNullString(value) ? value : defaultValue;
    }

    public static <E> E getModelMapValue(Model model, String key) {
        return (E) getMapValue(model.asMap(), key);
    }

    public static <E> E getModelAttribute(Model model, String key) {
        return getModelMapValue(model, key);
    }

    public static String getMsgPropertiesValue(String propKey) {
        return getPropertiesValue(propKey, "messages.properties");
    }

    public static String getPropertiesValue(String propKey, String filename) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            String pathFilename = filename.contains("/") ? filename : "/" + filename;
            input = JavaUtils.class.getClassLoader().getResourceAsStream(pathFilename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return "N/A";
            }
            // load a properties file from class path, inside static method
            prop.load(input);
            // get the property value and print it out
            return prop.getProperty(propKey);
        } catch (IOException ex) {
            ex.printStackTrace();
            return "N/A";
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static <E> boolean notEmptyArrayList(List<E> list) {
        if (list != null && list.size() > 0)
            return true;
        else
            return false;
    }


    public static Date stringIntoDateWithFormat(String dateInString, String customFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(customFormat);
        if (dateInString != null)
            try {
                Date date = formatter.parse(dateInString);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        else
            return null;
    }

    public static Date stringIntoDateWithFormat(String dateInString) {
        return stringIntoDateWithFormat(dateInString, "dd/MM/yyyy");
    }
}
