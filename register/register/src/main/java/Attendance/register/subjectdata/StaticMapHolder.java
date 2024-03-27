package Attendance.register.subjectdata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StaticMapHolder {
    static private final Map<String, String> map = new HashMap<>();

    public static void mapInsert() {
        map.put("Математика", "math");
        map.put("Иностранный язык", "foreign_language");
        map.put("Программирование", "programming");
    }

    public static final Map<String, String> immutableMap = Collections.unmodifiableMap(map);

}
