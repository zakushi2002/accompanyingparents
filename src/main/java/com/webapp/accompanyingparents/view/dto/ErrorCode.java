package com.webapp.accompanyingparents.view.dto;

public class ErrorCode {
    /**
     * General error code
     */
    public static final String GENERAL_ERROR_REQUIRE_PARAMS = "ERROR-GENERAL-0000";
    public static final String GENERAL_ERROR_ACCOUNT_LOCKED = "ERROR-GENERAL-0001";
    public static final String GENERAL_ERROR_ACCOUNT_NOT_FOUND = "ERROR-GENERAL-0002";
    /**
     * Starting error code Account
     */
    public static final String ACCOUNT_ERROR_UNKNOWN = "ERROR-ACCOUNT-0000";
    public static final String ACCOUNT_ERROR_EMAIL_EXIST = "ERROR-ACCOUNT-0001";
    public static final String ACCOUNT_ERROR_NOT_FOUND = "ERROR-ACCOUNT-0002";
    public static final String ACCOUNT_ERROR_WRONG_PASSWORD = "ERROR-ACCOUNT-0003";
    public static final String ACCOUNT_ERROR_WRONG_HASH_RESET_PASS = "ERROR-ACCOUNT-0004";
    public static final String ACCOUNT_ERROR_LOCKED = "ERROR-ACCOUNT-0005";
    public static final String ACCOUNT_ERROR_OPT_INVALID = "ERROR-ACCOUNT-0006";
    public static final String ACCOUNT_ERROR_LOGIN = "ERROR-ACCOUNT-0007";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_ERROR_DEVICE = "ERROR-ACCOUNT-0008";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_ERROR_RESTAURANT = "ERROR-ACCOUNT-0009";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_WRONG_RESTAURANT = "ERROR-ACCOUNT-0010";
    public static final String ACCOUNT_ERROR_MERCHANT_SERVICE_NOT_REGISTER = "ERROR-ACCOUNT-0011";


    /**
     * Starting error code AuthenticationToken
     */
    public static final String AUTH_TOKEN_ERROR_UNKNOWN = "ERROR-AUTH-TOKEN-0000";
    public static final String AUTH_TOKEN_ERROR_WRONG_SHOP = "ERROR-AUTH-TOKEN-0001";
    public static final String AUTH_TOKEN_ERROR_NOT_FOUND = "ERROR-AUTH-TOKEN-0002";
    public static final String AUTH_TOKEN_ERROR_INVALID = "ERROR-AUTH-TOKEN-0003";
    public static final String AUTH_TOKEN_ERROR_INVALID_RESTAURANT = "ERROR-AUTH-TOKEN-0004";
    public static final String AUTH_TOKEN_ERROR_INVALID_DEVICE = "ERROR-AUTH-TOKEN-0005";

    /**
     * Starting error code User
     */
    public static final String USER_ERROR_UNKNOWN = "ERROR-USER-0000";
    public static final String USER_ERROR_EXIST = "ERROR-USER-0002";
    public static final String USER_ERROR_UPDATE = "ERROR-USER-0003";
    public static final String USER_ERROR_NOT_FOUND = "ERROR-USER-0004";
    /**
     * Starting error code Employee
     */
    public static final String EMPLOYEE_ERROR_UNKNOWN = "ERROR-EMPLOYEE-0000";
    public static final String EMPLOYEE_ERROR_EXIST = "ERROR-EMPLOYEE-0001";
    public static final String EMPLOYEE_ERROR_NOT_FOUND = "ERROR-EMPLOYEE-0003";
    public static final String EMPLOYEE_ERROR_PASSWORD_DUPLICATE = "ERROR-EMPLOYEE-0004";
    public static final String EMPLOYEE_ERROR_WRONG_RESTAURANT = "ERROR-EMPLOYEE-0005";

    /**
     * Starting error code SHOP ACCOUNT
     */
    public static final String SHOP_ACCOUNT_ERROR_UNKNOWN = "ERROR-SHOP_ACCOUNT-0000";
    public static final String SHOP_ACCOUNT_ERROR_NOT_FOUND = "ERROR-SHOP_ACCOUNT-0001";
    public static final String SHOP_ACCOUNT_ERROR_USERNAME_EXIST = "ERROR-SHOP_ACCOUNT-0002";
    public static final String SHOP_ACCOUNT_ERROR_WRONG_OLD_PWD = "ERROR-SHOP_ACCOUNT-0003";
    /**
     * Starting error code TOKEN
     */
    public static final String ACCESS_TOKEN = "Accessed";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    /**
     * Starting error code Post
     */
    public static final String POST_ERROR_UNKNOWN = "ERROR-POST-0000";
    public static final String POST_ERROR_EXIST = "ERROR-POST-0002";
    public static final String POST_ERROR_UPDATE = "ERROR-POST-0003";
    public static final String POST_ERROR_NOT_FOUND = "ERROR-POST-0004";
    /**
     * Starting error code COMMENT
     */
    public static final String COMMENT_ERROR_UNKNOWN = "ERROR-COMMENT-0000";
    public static final String COMMENT_ERROR_EXIST = "ERROR-COMMENT-0002";
    public static final String COMMENT_ERROR_UPDATE = "ERROR-COMMENT-0003";
    public static final String COMMENT_ERROR_NOT_FOUND = "ERROR-COMMENT-0004";
}