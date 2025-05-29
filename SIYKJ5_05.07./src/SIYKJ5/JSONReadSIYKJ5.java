package SIYKJ5;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class JSONReadSIYKJ5 {
    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray rootArray = (JSONArray) parser.parse(new FileReader("orarendSIYKJ5.json"));

            parseArray(rootArray, "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parseObject(JSONObject jsonObject, String indent) {
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                System.out.println(indent + key + ":");
                parseObject((JSONObject) value, indent + "  ");
            } else if (value instanceof JSONArray) {
                System.out.println(indent + key + ":");
                parseArray((JSONArray) value, indent + "  ");
            } else {
                System.out.println(indent + key + ": " + value);
            }
        }
    }

    public static void parseArray(JSONArray array, String indent) {
        for (Object element : array) {
            if (element instanceof JSONObject) {
                parseObject((JSONObject) element, indent);
                System.out.println(); // elválasztás
            } else if (element instanceof JSONArray) {
                parseArray((JSONArray) element, indent + "  ");
            } else {
                System.out.println(indent + "Elem: " + element);
            }
        }
    }
}
