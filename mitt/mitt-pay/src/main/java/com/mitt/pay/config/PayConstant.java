package com.mitt.pay.config;

/**
 * @author mitt
 * @className PayConstant
 * @descriotion
 * @date 2021/4/15 10:06
 **/
public class PayConstant {
    /**
     * 异步回调地址
     */
    public static final String NOTIFY_URL = "https://www.612ch.com/";

    /**
     * 同步回调地址
     */
    public static final String RETURN_URL = "https://www.612ch.com/";

    /**
     * QRCodeUrl http://qr.6api.top/?data= http://qr.612ch.com/?data=
     */
    public static final String QR_CODE_URL = "http://qr.612ch.com/?data=";

    /**
     * 字符
     */
    public static final String CHARSET = "UTF-8";

    public enum PayEnum {
        WEB, CNY, SUCCESS, FAIL, MWEB, NATIVE
    }
    public enum PayType {
        QQ,WX,ZFB
    }


}
