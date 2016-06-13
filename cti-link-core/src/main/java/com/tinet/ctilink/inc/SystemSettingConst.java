package com.tinet.ctilink.inc;

/**
 * @author fengwei //
 * @date 16/5/19 15:39
 */
public class SystemSettingConst {
    /*
     * system_setting property definition
     */
    public static final String SYSTEM_SETTING_PROPERTY_DAY = "day";
    public static final String SYSTEM_SETTING_PROPERTY_HOUR = "hour";
    public static final String SYSTEM_SETTING_PROPERTY_MINUTE = "minute";
    public static final String SYSTEM_SETTING_PROPERTY_SECOND = "second";
    public static final String SYSTEM_SETTING_PROPERTY_TIMES = "times";

    /**
     * system_setting name definition
     */
    /* 话单保留时间 36个月 */
    public static final String SYSTEM_SETTING_NAME_CDR_EXPIRE_MONTH = "cdr_expire_month";

    /* 日志保留时间 3个月 */
    public static final String SYSTEM_SETTING_NAME_LOG_EXPIRE_MONTH = "log_expire_month";

    public static final String SYSTEM_SETTING_NAME_FAXC_URL = "faxc_url";

    public static final String SYSTEM_SETTING_NAME_DEFAULT_ROUTER = "default_router";
    public static final String SYSTEM_SETTING_NAME_DEFAULT_AREA_CODE = "default_area_code";
    public static final String SYSTEM_SETTING_NAME_IB_CALL_REMEMBER_TIME = "ib_call_remember_time";
    public static final String SYSTEM_SETTING_NAME_OB_CALL_REMEMBER_TIME = "ob_call_remember_time";
    public static final String SYSTEM_SETTING_NAME_AMI_RESPONSE_TIMEOUT = "ami_response_timeout";

    public static final String SYSTEM_SETTING_NAME_SMS_URL = "sms_url";
    public static final String SYSTEM_SETTING_NAME_TIMED_TASK_EMAIL_ADDRESS = "timed_task_email_address";
    /** 预测外呼未接通话语音分析模块 */
    public static final String SYSTEM_SETTING_NAME_SPEECH_ENGINE = "speech_engine";
    /** websocket server rtcweb breaker url */
    public static final String SYSTEM_SETTING_NAME_WEBRTC_WEBSOCKET_URL = "webrtc_websocket_url";
    /** websocket stun server url */
    public static final String SYSTEM_SETTING_NAME_WEBRTC_STUN_SERVER = "webrtc_stun_server";
    /** 队列中座席接听电话，服务水平秒数 */
    public static final String SYSTEM_SETTING_NAME_SERVICE_LEVEL = "service_level";

    /** 号码号段 */
    public static final String SYSTEM_SETTING_NAME_UNICOM_SEGMENT = "unicom_segment";
    public static final String SYSTEM_SETTING_NAME_TELECOM_SEGMENT = "telecom_segment";
    public static final String SYSTEM_SETTING_NAME_MOBILE_SEGMENT = "mobile_segment";

    /** 再次推送开关 */
    public static final String SYSTEM_SETTING_NAME_CURL_AGAIN = "curl_again";

    public static final String SYSTEM_SETTING_NAME_TTSS_PROXY_URL = "tts_proxy_url";

    public static final String SYSTEM_SETTING_NAME_CONTROL_API_MAX_REQUEST_COUNT = "control_api_max_request_count";

    public static final String SYSTEM_SETTING_NAME_CONTROL_API_WHITE_IP_LIST = "control_api_white_ip_list";

    /** DynamoDB表名前后缀配置 */
    public static final String SYSTEM_SETTING_NAME_DYNAMODB_TABLE_NAME_PREFIX = "dynamodb_table_name_prefix";

    public static final String SYSTEM_SETTING_NAME_DYNAMODB_TABLE_NAME_SUFFIX = "dynamodb_table_name_suffix";

}
