package com.lbc.context;


import java.util.HashMap;
import java.util.Map;

/**
 * @author lbc
 * @classname TaContext
 */
public class AprilContext {

    private static ThreadLocal<Map<Class<?>, Object>> context = new ThreadLocal<>();

    public static <T>T get(Class<T> clazz){
        Map<Class<?>, Object> map = context.get();
        return (T)map.get(clazz);
    }

    public static void put(Object obj){
        Map<Class<?>, Object> map = context.get();
        if (map == null) {
            map = new HashMap<>();
            context.set(map);
        }
        map.put(obj.getClass(),obj);
    }

    public static void clean(){
        context.remove();
    }
}
