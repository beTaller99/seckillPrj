package prj.util;

import com.google.gson.Gson;

/**
 * @className: JsonUtil
 * @Description: TODO
 * @version: jdk11
 * @author: asher
 * @date: 2022/12/4 20:31
 */
public class JsonUtil {
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }
}
