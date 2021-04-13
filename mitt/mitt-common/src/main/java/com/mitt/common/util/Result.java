package com.mitt.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mitt
 * @className Result
 **/
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 200);
        put("msg", "success");
    }

    public static Result fail() {
        return fail(500, "未知异常，请联系管理员");
    }

    public static Result fail(String msg) {
        return fail(500, msg);
    }

    public static Result fail(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Result msg(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }
    public static Result ok(Object value) {
        Result r = new Result();
        r.put("data", value);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return new Result();
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
