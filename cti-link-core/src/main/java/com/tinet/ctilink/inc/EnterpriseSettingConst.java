package com.tinet.ctilink.inc;

/**
 * @author fengwei //
 * @date 16/5/19 14:23
 */
public class EnterpriseSettingConst {

    /*
	 * enterprise enterprise_setting name definition
	 */
    /**enterprise_test_expiry_date 测试账户使用有效日期*/
    public static final String ENTERPRISE_SETTING_NAME_ENTERPRISE_TEST_EXPIRY_DATE = "enterprise_test_expiry_date";
    /** 测试账户的使用状态 0-空闲  1-已用 **/
    public static final String ENTERPRISE_SETTING_NAME_ENTERPRISE_TEST_STATUS = "enterprise_test_status";

    /**fax_mail_address 企业接收电子传真的Email地址，多个之间使用英文逗号分隔*/
    public static final String ENTERPRISE_SETTING_NAME_FAX_MAIL_ADDRESS = "fax_mail_address";

    /**auto_investigation 企业是否开启自动满意度调查：0-关闭  1-开启 **/
    public static final String ENTERPRISE_SETTING_NAME_AUTO_INVESTIGATION = "auto_investigation";

    /**auto_investigation 企业是否开启呼入自动满意度调查：0-关闭  1-开启 **/
    public static final String ENTERPRISE_SETTING_NAME_AUTO_INVESTIGATION_IB = "auto_investigation_ib";

    /**auto_investigation 企业是否开启外呼自动满意度调查：0-关闭  1-开启 **/
    public static final String ENTERPRISE_SETTING_NAME_AUTO_INVESTIGATION_OB = "auto_investigation_ob";

    /** 队列监控设置 **/
    public static final String ENTERPRISE_SETTING_NAME_QUEUE_OBSERVER = "queue_observer";
    /** IVR监控设置 **/
    public static final String ENTERPRISE_SETTING_NAME_IVR_OBSERVER = "ivr_observer";
    /** 黑白名单设置 **/
    public static final String ENTERPRISE_SETTING_NAME_RESTRICT_TEL_TYPE = "restrict_tel_type";

    /** 呼入录音功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_RECORD_IB = "is_record_ib";

    /** 外呼录音功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_RECORD_OB = "is_record_ob";

    /** IVR录音功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_RECORD_IVR = "is_record_ivr";

    /** 来电记忆功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_REMEMBER_CALL = "is_remember_call";

    /** 来电记忆时间 **/
    public static final String ENTERPRISE_SETTING_NAME_IB_CALL_REMEMBER_TIME = "ib_call_remember_time";

    /** 外呼记忆功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_OUT_REMEMBER_CALL = "is_out_remember_call";

    /** 留言功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_VOICEMAIL = "is_voicemail";

    /** 网上400功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_WEB400 = "is_web400";

    /** 传真接收功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_FAX_RECEIVE = "is_fax_receive";

    /** 短信发送功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_SMS_SEND = "is_sms_send";

    /** 短信接收功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_SMS_RECEIVE = "is_sms_receive";

    /** 即时通信功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_IM = "is_im";

    /** 点击外呼功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_OB_CLICK = "is_ob_click";

    /** 主叫外呼功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_OB_DIRECT = "is_ob_direct";

    /** 预览外呼功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_OB_PREVIEW = "is_ob_preview";

    /** 预测外呼功能是否开启 **/
    public static final String ENTERPRISE_SETTING_NAME_IS_OB_PREDICTIVE = "is_ob_predictive";

    public static final String ENTERPRISE_SETTING_NAME_OB_PREDICTIVE_WORK_TIME = "ob_predictive_work_time";


    /** 外呼号码状态识别选项 0 关闭 1开启 */
    public static final String ENTERPRISE_SETTING_NAME_TEL_STATUS_IDENTIFICATION = "tel_status_identification";

    /** 接口秘钥 */
    public static final String ENTERPRISE_SETTING_NAME_ENTERPRISE_SECRET = "enterprise_secret";
    /** 接口token */
    public static final String ENTERPRISE_SETTING_NAME_ENTERPRISE_TOKEN = "enterprise_token";

    //是否开启彩铃
    public static final String ENTERPRISE_SETTING_NAME_IS_CRBT = "is_crbt";
    //是否开启呼叫未接通录音
    public static final String ENTERPRISE_SETTING_NAME_IS_RECORD_TEL = "is_record_tel";

    public static final String ENTERPRISE_SETTING_NAME_IS_IVR_LOCK = "is_ivr_lock";

    public static final String ENTERPRISE_SETTING_NAME_IS_RECORD = "is_record";

    public static final String ENTERPRISE_SETTING_NAME_IS_CRBT_OPEN = "is_crbt_open"; //是否彩铃，对呼入有效
    public static final String ENTERPRISE_SETTING_NAME_RECORD_FILE_USERFIELD = "record_file_userfield";
    public static final String ENTERPRISE_SETTING_NAME_MP3_RATIO = "mp3_ratio";
    // 网上400推送
    public static final String ENTERPRISE_SETTING_NAME_CRM_CURL_WEB400_STATUS = "crm_curl_web400_status";

    /** webcall默认ivr设置 **/
    public static final String ENTERPRISE_SETTING_NAME_WEBCALL_DEFAULT_IVR = "webcall_default_ivr";

    public static final String ENTERPRISE_SETTING_NAME_CLID_LIST="clid_list";

    /** CRM对接弹屏自定义参数 **/
    public static final String ENTERPRISE_SETTING_NAME_CRM_URL_POPUP_USER_FIELD = "crm_url_popup_user_field";

    public static final String ENTERPRISE_SETTING_NAME_CURL_LEVEL = "curl_level";

    public static final String ENTERPRISE_SETTING_NAME_CDR_USER_FIELD = "cdr_user_field";
    /**
     * enterprise_setting value
     */
    public static final String ENTERPRISE_SETTING_VALUE_RESTRICT_TEL_TYPE_OFF = "0";
    public static final String ENTERPRISE_SETTING_VALUE_RESTRICT_TEL_TYPE_BLACK = "1";
    public static final String ENTERPRISE_SETTING_VALUE_RESTRICT_TEL_TYPE_WHITE = "2";
    
    public static final String ENTERPRISE_SETTING_NAME_CALL_LIMIT_IB ="enterprise_call_limit_ib";
    public static final String ENTERPRISE_SETTING_NAME_CDR_USER_FIELD = "cdr_user_field";

}
