package com.wzb.visual.dto;


import com.wzb.visual.exception.SatsukiWebError;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装json对象，所有返回结果都使用它
 */
public class AjaxResult extends HashMap<String, Object> {

    //TODO::深度依赖了，后面需要统一throw
    @Deprecated
    public static final String COMMON_ERROR = "接口调用出错";

    public static final String MESSAGE = "message";
    public static final String SUCCESS = "success";
    public static final String DATA = "data";
    public static final String CODE = "code";

    public static final AjaxResult BLANK_SUCCESS = new AjaxResult().setSuccess(true);

    private static final Map<SatsukiWebError, AjaxResult> map = new HashMap<>();

    public static AjaxResult fixedError(SatsukiWebError SatsukiWebError) {
        if (map.get(SatsukiWebError) == null) {
            synchronized (AjaxResult.class) {
                map.computeIfAbsent(SatsukiWebError, e -> new AjaxResult().setState(e));
            }
        }
        return map.get(SatsukiWebError);
    }

    public AjaxResult() {
        put(MESSAGE, "");
        put(SUCCESS, false);
    }

    public AjaxResult setState(SatsukiWebError satsukiWebError) {
        put(MESSAGE, satsukiWebError.errMsg);
        put(CODE, satsukiWebError.code);
        put(SUCCESS, false);
        return this;
    }

    private AjaxResult setCode(int code) {
        put(CODE, code);
        return this;
    }

    public AjaxResult setMessage(String msg) {
        setSuccess(false);
        put(MESSAGE, msg);
        return this;
    }

    public AjaxResult setSuccess(boolean ret) {
        put(SUCCESS, ret);
        return this;
    }

    public AjaxResult setData(Object obj) {
        setSuccess(true);
        put(DATA, obj);
        return this;
    }

}