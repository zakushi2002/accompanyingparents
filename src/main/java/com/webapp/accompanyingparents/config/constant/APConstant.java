package com.webapp.accompanyingparents.config.constant;

import java.util.Objects;

public class APConstant {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public static final String SESSION_KEY = "user_session";
    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    public static final Integer ORDER_STATE_UNKNOWN = -1; // Chưa thanh toán thành công khi qua paypal
    public static final Integer ORDER_STATE_PENDING = 0; // đang đặt
    public static final Integer ORDER_STATE_ACTIVE = 1; // đang làm
    public static final Integer ORDER_STATE_DONE = 2; // đã xong
    public static final Integer ORDER_STATE_CANCEL = -2; // đã hủy
    public static final Integer ORDER_STATE_HIDE = -3; // nhung order QR Live ko thanh toan
    public static final String ROLE_SUPER_ADMIN = "ROLE SUPER ADMIN";
    public static final String ROLE_ADMIN = "ROLE ADMIN";
    public static final String ROLE_EXPERT = "ROLE EXPERT";
    public static final String ROLE_END_USER = "ROLE END USER";

    public static boolean validateState(Integer state) {
        boolean rs = true;
        if (state == null || (!Objects.equals(state, ORDER_STATE_UNKNOWN)
                && !Objects.equals(state, ORDER_STATE_PENDING))
                && !Objects.equals(state, ORDER_STATE_ACTIVE)
                && !Objects.equals(state, ORDER_STATE_DONE)
                && !Objects.equals(state, ORDER_STATE_CANCEL)
                && !Objects.equals(state, ORDER_STATE_HIDE)) {
            rs = false;
        }
        return rs;
    }

    private APConstant() {
        throw new IllegalStateException("Utility class");
    }
}