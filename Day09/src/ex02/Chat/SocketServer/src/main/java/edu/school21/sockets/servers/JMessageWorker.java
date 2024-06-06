package edu.school21.sockets.servers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class JMessageWorker {
    private static final String MESSAGE = "message";
    private static final String COLOR = "color";

    public static JMessage parseToObject(String message) {
        JMessage jMessage = new JMessage();
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject messageObject = (JSONObject) jsonParser.parse(message);
            jMessage.setMessage((String) messageObject.get(MESSAGE));
            jMessage.setColor((String) messageObject.get(COLOR));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return jMessage;
    }

    public static JSONObject makeJSONObject(String message, String color) {
        Map<String, String> map = new HashMap<>();
        map.put(MESSAGE, message);
        map.put(COLOR, color);
        return new JSONObject(map);
    }
}
