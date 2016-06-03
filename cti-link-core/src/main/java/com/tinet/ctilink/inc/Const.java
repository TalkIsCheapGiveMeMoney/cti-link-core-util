package com.tinet.ctilink.inc;

/**
 * @author fengwei //
 * @date 16/4/12 11:50
 */
public class Const {
    public static final String APPLICATION_VERSION = "application.version";



    public static final String SOUNDS_MOH_ABS_PATH = "/var/nfs/ctilink/voices/moh/";
    public static final String SOUNDS_MOH_CTI_ABS_PATH = "/var/lib/moh/";
    public static final String SOUNDS_IVR_VOICE_ABS_PATH = "/var/nfs/ctilink/voices/ivr_voice/";

    public static final String SOUNDS_PATH = "/voices/";
    public static final String SOUNDS_RECORD_PATH = "/voices/record/";
    public static final String SOUNDS_RECORD_ASR_PATH = "/voices/record_asr/";
    public static final String SOUNDS_VOICE_MAIL_PATH = "/voices/voice_mail/";
    public static final String SOUNDS_MOH_PATH = "/voices/moh/";
    public static final String SOUNDS_IVR_VOICE_PATH = "/voices/ivr_voice/";
    public static final String AWS_S3_BUCKETNAME = "tinet-ctilink";

    public static final String PATTERN_MOBILE_WITH_PREFIX0 = "^01[3|5|8|4|7]\\d{9}";
    public static final String PATTERN_MOBILE_WITHOUT_PREFIX0 = "^1[3|5|8|4|7]\\d{9}";
    public static final String PATTERN_NUMBER_400 = "^400\\d{7}";
    public static final String PATTERN_MOBILE_PREFIX = "13|14|15|18|17";
    public static final String PATTERN_WORLD_PREFIX = "00";

    /**
     * 企业号长度
     */
    public static final int ENTERPRISE_ID_LEN = 7;

    /*
     * enterprise status definition
     */
    public static final int ENTITY_STATUS_NO_SERVICE = 0;
    public static final int ENTITY_STATUS_OK = 1;
    public static final int ENTITY_STATUS_PAUSED = 2;
    public static final int ENTITY_STATUS_STOP = 3;
    public static final int ENTITY_STATUS_CLOSE = 4;


    

    /** 获取座席loginStatus和DeviceStatus的变量 */
    public static final String AGENT_LOGIN_STATUS = "agent_login_status";
    public static final String AGENT_DEVICE_STATUS = "agent_device_status";

    public static final String IVR_DB_SQL = "ivr_db_sql";
    public static final String IVR_DB_ENTERPRISE_IVR_ID = "ivr_db_enterprise_ivr_id";

    public static final String QUEUE = "queue";
    public static final String QUEUE_FILE = "queue_file";
    public static final String WEBCALL_IVR_ID = "webcall_ivr_id";

    public static final String CDR_FORCE_DISCONNECT = "cdr_force_disconnect";
    public static final String RDNIS = "RDNIS";

    public static final String OB_DEST_NUMBER = "ob_dest_number";

    public static final String IVR_WAIT_STATUS = "IVR_WAIT_STATUS";
    public static final String IVR_WAIT_DONE = "IVR_WAIT_DONE";

    public static final String DIRECT_CALL_READ_STATUS = "DIRECT_CALL_READ_STATUS";
    public static final String DIRECT_CALL_READ_DONE = "DIRECT_CALL_READ_DONE";

    public static final String WEBCALL_INDEX = "webcall_index";
    public static final String WEBCALL_REQUEST_TIME = "webcall_request_time";


   
    public static final int CDR_CALLER_TYPE_UNKNOWN = 1;
    public static final int CDR_CALLER_TYPE_KNOWN = 2;

    public static final int CDR_HANGUP_CAUSE_CUSTOMER = 1000; // 客户挂机
    public static final int CDR_HANGUP_CAUSE_CLIENT = 1001; // 座席挂机
    public static final int CDR_HANGUP_CAUSE_DISCONNECT = 1002; // 被强拆


    /*
	 * enterprise_iv op_path and op_action and file_path definition
	 */
    public static final String ENTERPRISE_IVR_OP_PATH_WELCOME = "1";
    public static final String ENTERPRISE_IVR_OP_PATH_VOICEMAIL = "default_voice_mail";
    public static final String ENTERPRISE_IVR_FILE_PATH_VOICEMAIL = "default_voice_mail.wav";
    public static final String ENTERPRISE_IVR_FILE_PATH_QUEUE = "default_transfer.wav";
    public static final String ENTERPRISE_IVR_FILE_PATH_GROUP_ON_DUTY = "on_duty.wav";
    public static final String ENTERPRISE_IVR_FILE_PATH_EXTEN_ON_DUTY = "tel_exten_on_duty.wav";
    public static final String ENTERPRISE_IVR_FILE_PATH_AREA = "public_transfer.wav";

    // public static final int ENTERPRISE_IVR_OP_ACTION_EXTEN = 0;
    public static final int ENTERPRISE_IVR_OP_ACTION_PLAY = 1;
    public static final int ENTERPRISE_IVR_OP_ACTION_SELECT = 2;
    public static final int ENTERPRISE_IVR_OP_ACTION_VOICEMAIL = 3;
    public static final int ENTERPRISE_IVR_OP_ACTION_QUEUE = 4;
    public static final int ENTERPRISE_IVR_OP_ACTION_TIME = 5;
    public static final int ENTERPRISE_IVR_OP_ACTION_AREA = 6;
    public static final int ENTERPRISE_IVR_OP_ACTION_FAX = 7;
    public static final int ENTERPRISE_IVR_OP_ACTION_SET = 8;
    public static final int ENTERPRISE_IVR_OP_ACTION_READ = 9;
    public static final int ENTERPRISE_IVR_OP_ACTION_WAIT = 10;
    public static final int ENTERPRISE_IVR_OP_ACTION_BRANCH = 11;
    public static final int ENTERPRISE_IVR_OP_ACTION_CURL = 12;
    public static final int ENTERPRISE_IVR_OP_ACTION_DIAL = 13;
    public static final int ENTERPRISE_IVR_OP_ACTION_DB = 14;
    public static final int ENTERPRISE_IVR_OP_ACTION_MEETTING = 15;
    public static final int ENTERPRISE_IVR_OP_ACTION_SWITCH = 16;

    /**  */
    public static final int ENTERPRISE_IVR_OP_ACTION_DB_TYPE_ORACLE = 1;
    public static final int ENTERPRISE_IVR_OP_ACTION_DB_TYPE_MYSQL = 2;
    public static final int ENTERPRISE_IVR_OP_ACTION_DB_TYPE_POSTGRESQL = 3;

    public static final int ENTERPRISE_IVR_OP_ACTION_DIRECT_TYPE_TEL = 1;
    public static final int ENTERPRISE_IVR_OP_ACTION_DIRECT_TYPE_CNO = 2;
    public static final int ENTERPRISE_IVR_OP_ACTION_DIRECT_TYPE_CRM_ID = 3;
    public static final int ENTERPRISE_IVR_OP_ACTION_DIRECT_TYPE_EXTEN = 4;
    public static final int ENTERPRISE_IVR_OP_ACTION_DIRECT_TYPE_QNO = 5;
    public static final int ENTERPRISE_IVR_OP_ACTION_DIRECT_TYPE_TSNO = 6;

    public static final int ENTERPRISE_IVR_PARENT_ID_DEFAULT = 0;

    public static final int ENTERPRISE_TIME_TYPE_WEEK = 1;
    public static final int ENTERPRISE_TIME_TYPE_DAY = 2;
    public static final String ENTERPRISE_TIME_DAYOFWEEK_ALL = "%";
    public static final String ENTERPRISE_AREA_AREA_CODE_ALL = "%";

    /*
     * enterprise_area_group group_name definition
     */
    public static final String ENTERPRISE_AREA_GROUP_GROUP_NAME = "其它";
    public static final int ENTERPRISE_AREA_GROUP_GROUP_TYPE_USER_ADD = 1;
    public static final int ENTERPRISE_AREA_GROUP_GROUP_TYPE_OTHERS = 2;


    public static final int ENTERPRISE_PUSH_TYPE_HANGUP_IB = 1;// 来电挂机
    public static final int ENTERPRISE_PUSH_TYPE_HANGUP_OB = 2;// 外呼挂机
    public static final int ENTERPRISE_PUSH_TYPE_BRIDGE_OB = 3;// 外呼接通
    public static final int ENTERPRISE_PUSH_TYPE_BRIDGE_IB = 4;// 来电接通
    public static final int ENTERPRISE_PUSH_TYPE_INCOMING_IB = 6;// 来电incoming
    public static final int ENTERPRISE_PUSH_TYPE_CLIENT_STATUS = 5;// 坐席状态
    public static final int ENTERPRISE_PUSH_TYPE_RINGING_IB = 7;// 来电ring
    public static final int ENTERPRISE_PUSH_TYPE_RINGING_OB = 8;// 外呼ring
    public static final int ENTERPRISE_PUSH_TYPE_PRESS_KEY = 9;// 按键推送
    public static final int ENTERPRISE_PUSH_TYPE_TEL_STATUS = 10;// 号码状态识别
    public static final int ENTERPRISE_PUSH_TYPE_RINGING_WEB_CALL = 11;// 网上回呼客户侧响铃


    /** 号码状态识别推送鉴权参数**/
    public static final String ENTERPRISE_PUSH_TYPE_TEL_STATUS_SIGN_NAME = "customSignName"; // 签名名称
    public static final String ENTERPRISE_PUSH_TYPE_TEL_STATUS_SIGN__KEY_NAME = "customSignKeyName"; // key名称
    public static final String ENTERPRISE_PUSH_TYPE_TEL_STATUS_SIGN__KEY_VALUE = "customSignKeyValue"; // key值
    public static final String ENTERPRISE_PUSH_TYPE_TEL_STATUS_TIMESTAMP = "timestamp";

    /**
     * restrict_tel const
     */
    public static final int RESTRICT_TEL_RESTRICT_TYPE_BLACK = 1;
    public static final int RESTRICT_TEL_RESTRICT_TYPE_WHITE = 2;
    public static final int RESTRICT_TEL_TYPE_DEFAULT = 1;
    public static final int RESTRICT_TEL_TYPE_IB = 1;
    public static final int RESTRICT_TEL_TYPE_OB = 2;
    public static final int RESTRICT_TEL_TEL_TYPE_DEFAULT=1;
    public static final int RESTRICT_TEL_TEL_TYPE_TELNUMBER = 1;
    public static final int RESTRICT_TEL_TEL_TYPE_AREA = 2;
    public static final int RESTRICT_TEL_TEL_TYPE_UNKNOWN = 3;

    /**
     * telSet strategy
     */
    /** 顺序 */
    public static final String TEL_SET_STRATEGY_ORDER = "order";
    /** 随机 */
    public static final String TEL_SET_STRATEGY_RANDOM = "random";

    /**
     * queue strategy
     */
    /** 顺序 */
    public static final String STRATEGY_ORDER = "order";
    public static final String STRATEGY_RRORDERED = "rrordered";
    /** 轮询 */
    public static final String STRATEGY_RRMEMORY = "rrmemory";
    /** 带权的随机 */
    public static final String STRATEGY_WRANDOM = "wrandom";
    /** 平均 */
    public static final String STRATEGY_FEWEST_CALLS = "fewestcalls";
    /** 随机 */
    public static final String STRATEGY_RANDOM = "random";
    /** 技能优先 */
    public static final String STRATEGY_SKILL = "skill";
    /** 最长空闲时间 */
    public static final String STRATEGY_LEASTRECENT = "leastrecent";

    public static final boolean QUEUE_SAY_AGENTNO_DEFAULT = false;

    public static final boolean QUEUE_SAY_AGENTNO_ON = true;

    public static final int QUEUE_VIP_SUPPORT_DEFAULT = 0;

    public static final int QUEUE_VIP_SUPPORT_YES = 1;

    /**
     * validation
     */
    public static final String LANDLINE_VALIDATION = "^010\\d{8}$|^02\\d{9}$|^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$";
    public static final String TELEPHONE_VALIDATION = "^010\\d{8}$|^02\\d{9}$|^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$|^(13|15|18|14|17)\\d{9}$";
    public static final String TEL_SET_TEL_VALIDATION = "^400\\d{7}$|^010\\d{8}$|^02\\d{9}$|^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$|^(13|14|15|18|17)\\d{9}";
    public static final String TEL_VALIDATION = "^400\\d{7}$|^010\\d{8}$|^02\\d{9}$|^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$|^(13|14|15|18|17)\\d{9}$|^9\\d{4,5}$|^1010\\d{4}$|^\\d{5}$";
    public static final String MOBILE_VALIDATION = "^1[3,4,5,7,8]\\d{9}(,1[3,5,8,4,7]\\d{9}){0,2}$";
    public static final String EMAIL_VALIDATION = "^[ \\w\\.\\-]+@([a-zA-Z0-9\\-]+\\.)+[a-zA-Z0-9]{2,4}$";
    public static final String FAX_VALIDATION = "^010\\d{8}$|^02\\d{9}$|^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$";
    public static final String TEL_NUMBER_VALIDATION = "^400\\d{7}$|^010\\d{8}$|^02\\d{9}$|^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$|^0(13|14|15|18|17)\\d{9}$|^(13|14|15|18|17)\\d{9}$|^1010\\d{4}$";
    public static final String IS_NUMBER_VALIDATION = "^[0-9_]+$";
    /**分机号-正则*/
    public static final String EXTEN_TEL_VALIDATION = "^\\d{3,5}$";

    public static final String LANDLINE_VALIDATION_3 = "^010\\d{8}$|^02\\d{9}$";
    public static final String LANDLINE_VALIDATION_4 = "^0[3,4,5,6,7,8,9][0-9]\\d{8,9}$";

    public static final String AREA_CODE_VALIDATION = "^010|^02\\d{1}|^0[3,4,5,6,7,8,9][0-9]\\d{1}";

    /**
     * client const
     */
    public static final int AGENT_ACTIVE_DEFAULT = 1;
    public static final int AGENT_ACTIVE_OFF = 0;

    public static final int AGENT_TYPE_TEL = 1;
    public static final int AGENT_TYPE_WEB = 2;

    public static final int AGENT_WRAPUP_DELFAULT = 10;

    public static final int AGENT_CALL_POWER_ALL = 0;
    public static final int AGENT_CALL_POWER_NATIONAL = 1;
    public static final int AGENT_CALL_POWER_LOCAL = 2;
    public static final int AGENT_CALL_POWER_INTERNAL = 3;

    public static final int AGENT_IS_OB_DEFAULT = 1;
    public static final int AGENT_IS_OB_OFF = 0;

    public static final int AGENT_IB_RECORD_DEFAULT = 1;
    public static final int AGENT_IB_RECORD_OFF = 0;

    public static final int AGENT_OB_RECORD_DEFAULT = 1;
    public static final int AGENT_OB_RECORD_OFF = 0;

    public static final int AGENT_TEL_IS_VALIDITY_DEFAULT = 0;

    public static final int AGENT_TEL_IS_BIND_NO = 0;
    public static final int AGENT_TEL_IS_BIND_YES = 1;

    /**座机*/
    public static final int TEL_TYPE_LANDLINE = 1;
    /**手机*/
    public static final int TEL_TYPE_MOBILE = 2;
    /**分机*/
    public static final int TEL_TYPE_EXTEN = 3;
    /**sip*/
    public static final int TEL_TYPE_SIP = 4;

    // begin Redis消息广播的channel
    public static final String REDIS_CHANNEL_SYNC_SYSTEMSETTING = "channel:sync:systemsetting";
    public static final String REDIS_CHANNEL_SYNC_ENTERPRISESETTING = "channel:sync:enterprisesetting";
    public static final String REDIS_CHANNEL_SYNC_ENTERPRISENODESETTING = "channel:sync:enterprisenodesetting";
    public static final String REDIS_CHANNEL_SYNC_PUBLICMOH = "channel:sync:publicmoh";
    public static final String REDIS_CHANNEL_SYNC_ENTERPRISEMOH = "channel:sync:enterprisemoh";
    public static final String REDIS_CHANNEL_SYNC_ENTERPRISEHOTLINE = "channel:sync:enterprisehotline";
    public static final String REDIS_CHANNEL_SYNC_QUEUE = "channel:sync:queue";
    public static final String REDIS_CHANNEL_SYNC_QUEUEMEMBER = "channel:sync:queuemember";
    public static final String REDIS_CHANNEL_SYNC_GATEWAY = "channel:sync:gateway";
    public static final String REDIS_CHANNEL_SYNC_CTI = "channel:sync:cti";
    public static final String REDIS_CHANNEL_SYNC_SIPCONF = "channel:sync:sipconf";
    public static final String REDIS_CHANNEL_AMIEVENT = "channel:amievent";
    // end 通过Redis进行消息广播的channel

    // begin Redis的缓存Key
    public static final String REDIS_CTIAGENT_ONLINE = "ctiagent:online";
    public static final String REDIS_CTIAGENT_PAUSE = "ctiagent:pause";
    public static final String REDIS_CTIAGENT_COUNT = "ctiagent:count";
    public static final String REDIS_CTIAGENT_LOCK = "ctiagent:lock";
    public static final String REDIS_CURL_PUSH = "curl:push";
    public static final String REDIS_CURL_RESEND = "curl:resend";
    public static final String REDIS_CURL_LOG = "curl:log";
    public static final String REDIS_PORTAL_IMAGELINK = "portal:imagelink";
    public static final String REDIS_PORTAL_IMAGEURL = "portal:imageurl";
    public static final String REDIS_PORTAL_BACKGROUNDCOLOR = "portal:backgroundcolor";

    /**
     * bindType
     */
    public static final int BIND_TYPE_TEL = 1;
    public static final int BIND_TYPE_EXTEN = 2;
    public static final int BIND_TYPE_SOFT_PHONE = 3;

    /**
     * dialplay const
     */
    public static final String DIALPLAN_CONTEXT_TRANSFER = "web_transfer";
    public static final String DIALPLAN_CONTEXT_CONSULT = "web_consult";
    public static final String DIALPLAN_CONTEXT_CONSULT_THREEWAY = "consult_threeway";

    public static final String DIALPLAN_CONTEXT_BARGE = "barge";
    public static final String DIALPLAN_CONTEXT_SPY = "spy";
    public static final String DIALPLAN_CONTEXT_PREVIEW_OUTCALL = "preview_outcall";
    public static final String DIALPLAN_CONTEXT_MIX_SOUND = "mix_sound";
    public static final String DIALPLAN_CONTEXT_WEB400 = "web400";
    public static final String DIALPLAN_CONTEXT_WEB400_PUSH = "web400_push";
    public static final String DIALPLAN_CONTEXT_WEB400_NOPUSH = "web400_nopush";
    public static final String DIALPLAN_CONTEXT_PREVIEW_OUTCALL_PREDIAL = "agent_call_start_dest";
    /** 自助录音 */
    public static final String DIALPLAN_CONTEXT_SELF_RECORD = "self_record";
    /** 验证绑定电话 */
    public static final String DIALPLAN_CONTEXT_BINDPHONE_VERIFY = "bindphone_verify";
    public static final String DIALPLAN_CONTEXT_SEND_FAX = "send_fax";
    public static final String DIALPLAN_CONTEXT_PICKUP = "pickup";
    public static final String DIALPLAN_CONTEXT_WHISPER = "whisper";
    public static final String DIALPLAN_CONTEXT_THREEWAY = "threeway";
    /** IVR外呼context */
    public static final String DIALPLAN_CONTEXT_IVROUTCALL = "ivr_outcall";

    public static final String DIALPLAN_CONTEXT_INTERACT = "interact";

    public static final String DIALPLAN_CONTEXT_IVR_WAIT = "ivr_wait";

    public static final String DIALPLAN_CONTEXT_DIRECT_CALL_READ = "direct_call_read";

    /**
     * clid type
     */
    public static final int ROUTER_CLID_CALL_TYPE_IB_RIGHT = 1;
	public static final int ROUTER_CLID_CALL_TYPE_PREVIEW_OB_LEFT = 2;
	public static final int ROUTER_CLID_CALL_TYPE_PREVIEW_OB_RIGHT = 3;
	public static final int ROUTER_CLID_CALL_TYPE_PREDICTIVE_OB_LEFT = 4;
	public static final int ROUTER_CLID_CALL_TYPE_PREDICTIVE_OB_RIGHT = 5;

    /** CURL 推送类型 */
    public static final Integer CURL_TYPE_HANGUP_IB = 1; // 来电挂机推送
    public static final Integer CURL_TYPE_HANGUP_OB = 6; // 外呼挂机推送
    public static final Integer CURL_TYPE_IVR = 2;
    public static final Integer CURL_TYPE_WEBCALL = 3;
    public static final Integer CURL_TYPE_RINGING_IB = 4;
    public static final Integer CURL_TYPE_CLIENT_STATUS = 5;
    public static final Integer CURL_TYPE_INCOMING = 7;
    public static final Integer CURL_TYPE_RINGING_OB = 8;
    public static final Integer CURL_TYPE_BRIDGE_IB = 9;
    public static final Integer CURL_TYPE_BRIDGE_OB = 10;
    public static final Integer CURL_TYPE_PRESS_KEY = 11;
    public static final Integer CURL_TYPE_TEL_STAUTS = 12;
    public static final Integer CURL_TYPE_RINGING_WEBCALL = 13;


    public static final Integer LOG_TTS_CALL_FROM_DIALPLAN = 1;
    public static final Integer LOG_TTS_CALL_FROM_WEBCALL = 2;


    /*
 * WebCall_call return_type definition
 */
    public static final int WEB_CALL_FAILED = 0;
    public static final int WEB_CALL_SUCCESS = 1;
    public static final int WEB_CALL_UNAVAILABLE_AMI_SERVER = 2;
    public static final int WEB_CALL_UNAVAILABLE_SYSTEM_SETTING = 3;
    public static final int WEB_CALL_UNAVAILABLE_ENTERPRISE_SETTING = 4;
    public static final int WEB_CALL_UNAVAILABLE_ROUTER = 5;

    /*
     * Web400CallService_call return_type definition
     */
    public static final int WEB400_CALL_CHECK_OK = -1;
    public static final int WEB400_CALL_FAILED = 0;
    public static final int WEB400_CALL_SUCCESS = 1;
    public static final int WEB400_CALL_UNAVAILABLE_AMI_SERVER = 2;
    public static final int WEB400_CALL_UNAVAILABLE_SYSTEM_SETTING = 3;
    public static final int WEB400_CALL_UNAVAILABLE_ENTERPRISE_SETTING = 4;
    public static final int WEB400_CALL_UNAVAILABLE_ROUTER = 5;
    public static final int WEB400_CALL_UNAVAILABLE_ENTERPRISE_STATUS = 6;
    public static final int WEB400_CALL_UNAVAILABLE_TEL = 7;
    public static final int WEB400_CALL_UNAVAILABLE_TIME = 8;
    public static final int WEB400_CALL_UNAVAILABLE_QNO = 9;
    public static final int WEB400_CALL_BLACK_IP = 10;
    public static final int WEB400_CALL_BLACK_COOKIENAME = 11;
    public static final int WEB400_CALL_BLACK_TEL = 12;
    public static final int WEB400_CALL_ENTERPRISE_BLACK_TEL = 13;
    public static final int WEB400_CALL_NO_IVR = 14;
    public static final int WEB400_CALL_UNKNOWN_ERROR = 15;
    public static final int WEB400_CALL_ILLEGAL_ENTERPRISE = 16;
    public static final int WEB400_CALL_UNAVAILABLE_IVR_ID = 17;
    public static final int WEB400_CALL_UNAVAILABLE_CLID_NUMBER = 18;
    public static final int WEB400_CALL_PLATFORM_MAX_LIMIT = 19;
    public static final int WEB400_CALL_ENTERPRISE_MAX_LIMIT = 20;
    public static final int WEB400_CALL_SECURITY_CODE_ERROR = 21;

    public static final int WEB400_CALL_TIMEOUT_DEFAULT = 30;


    public static final String PREDICTIVE_OUT_CALL_MODE_AMI = "ami";
    public static final String PREDICTIVE_OUT_CALL_MODE_SBC = "sbc";
	
    public static final String UNKNOWN_NUMBER = "unknown_number";
    public static final String UNKNOWN_AREA = "unknown_area";


    public static final int REDIS_DB_CONF_INDEX = 1;
    public static final int REDIS_DB_DUBBO_INDEX = 2;
    public static final int REDIS_DB_SESSION_INDEX = 3;
    public static final int REDIS_DB_CTI_INDEX = 4;
    public static final int REDIS_DB_AREA_CODE_INDEX = 5;
    public static final int REDIS_DB_NON_CONFIGURE_INDEX = 6;

    public static final Integer HANGUP_SET_TYPE_IB = 0;
    public static final Integer HANGUP_SET_TYPE_OB = 1;

    public static final Integer TEL_SET_STATUS_STOP = 1;
    public static final Integer TEL_SET_STATUS_ACTIVE = 0;

    /**
     * 呼入路由  enterprise_ivr_router
     *
     */
    public static final int ENTERPRISE_IVR_ROUTER_TYPE_IVR = 1;
    public static final int ENTERPRISE_IVR_ROUTER_TYPE_TEL = 2;
    public static final int ENTERPRISE_IVR_ROUTER_TYPE_EXTEN = 3;


	public static final String OBJECT_TYPE_TEL="0";
	public static final String OBJECT_TYPE_CNO="1";
	public static final String OBJECT_TYPE_EXTEN="2";
	public static final String OBJECT_TYPE_IVR_NODE="3";
	public static final String OBJECT_TYPE_IVR_ID="4";
	
	public static final int ROUTER_TYPE_PREFIX = 1;
	public static final int ROUTER_TYPE_INTERNAL = 2;
	
	public static final int CLIENT_CALL_POWER_ALL=0;
	public static final int CLIENT_CALL_POWER_NATIONAL=1;
	public static final int CLIENT_CALL_POWER_LOCAL=2;
	public static final int CLIENT_CALL_POWER_INTERNAL=3;
	
	public static final int CDR_CALL_TYPE_IB=1;                 //cdr_ib
	public static final int CDR_CALL_TYPE_OB_WEBCALL=2;         //cdr_ob_
	public static final int CDR_CALL_TYPE_OB_PREVIEW=4;         //cdr_ob
	public static final int CDR_CALL_TYPE_OB_PREDICTIVE=5;      //cdr_ob
	public static final int CDR_CALL_TYPE_OB_DIRECT=6;
	public static final int CDR_CALL_TYPE_OB_INTERNAL=9;

	public static final int CDR_CALL_TYPE_IB_CALL_AGNET=101;   //呼入后呼转座席
	public static final int CDR_CALL_TYPE_IB_CALL_TEL=109;
	public static final int CDR_CALL_TYPE_IB_TRANSFER=102;      //呼入的转移
	public static final int CDR_CALL_TYPE_IB_CONSULT=103;       //呼入的咨询
	public static final int CDR_CALL_TYPE_IB_THREEWAY=104;      //呼入的三方通话
	public static final int CDR_CALL_TYPE_IB_SPY=105;           //呼入的被监听
	public static final int CDR_CALL_TYPE_IB_WHISPER=106;       //呼入的被耳语
	public static final int CDR_CALL_TYPE_IB_BARGE=107;         //呼入的被强插
	public static final int CDR_CALL_TYPE_IB_PICKUP=108;        //呼入的被抢线

	public static final int CDR_CALL_TYPE_OB_CALL_AGENT=201;    //外呼的呼座席
	public static final int CDR_CALL_TYPE_OB_CALL_TEL=201;      //外呼的呼电话
	public static final int CDR_CALL_TYPE_OB_TRANSFER=202;      //外呼的转移
	public static final int CDR_CALL_TYPE_OB_CONSULT=203;       //外呼的咨询
	public static final int CDR_CALL_TYPE_OB_THREEWAY=204;      //外呼的三方
	public static final int CDR_CALL_TYPE_OB_SPY=205;           //外呼的被监听
	public static final int CDR_CALL_TYPE_OB_WHISPER=206;       //外呼的被三方通话
	public static final int CDR_CALL_TYPE_OB_BARGE=207;         //外呼的被强插
	public static final int CDR_CALL_TYPE_OB_CALL_CUSTOMER=208;    //点击外呼，预览外呼，直接外呼的呼叫客户

    /*  call_status 呼叫结果定义 */
	public static final int CDR_STATUS_IB_BRIDGED=1;
	public static final int CDR_STATUS_IB_CALLED=2;
	public static final int CDR_STATUS_IB_SYSTEM_ANSWER=3;
	public static final int CDR_STATUS_IB_BAD_IVR=4;
	public static final int CDR_STATUS_IB_ENTERPRISE_STOP=5;
	public static final int CDR_STATUS_IB_ENTERPRISE_PAUSED=6;
	public static final int CDR_STATUS_IB_BLACK=7;
	public static final int CDR_STATUS_IB_ENTERPRISE_NO_REG=8;
	public static final int CDR_STATUS_IB_NOANSWER_COLORRING=9;
	public static final int CDR_STATUS_IB_OVER_LIMIT=11;
	public static final int CDR_STATUS_IB_SYSTEM_NOANSWER=12;
	public static final int CDR_STATUS_IB_FAILED_OTHER=13;

	public static final int CDR_STATUS_OB_WEBCALL_CUSTOMER_TTS_FAIL=20;
	public static final int CDR_STATUS_OB_WEBCALL_NOANSWER=21;
	public static final int CDR_STATUS_OB_WEBCALL_ANSWER=22;
	public static final int CDR_STATUS_OB_WEBCALL_CALLED=23;
	public static final int CDR_STATUS_OB_WEBCALL_BRIDGED=24;
	
	public static final int CDR_STATUS_OB_PREVIEW_AGENT_NO_ANSWER=30;
	public static final int CDR_STATUS_OB_PREVIEW_AGENT_ANSWER=31;
	public static final int CDR_STATUS_OB_PREVIEW_CALLED=32;
	public static final int CDR_STATUS_OB_PREVIEW_BRIDGED=33;

	public static final int CDR_STATUS_OB_PREDICTIVE_CUSTOMER_NO_ANSWER=40;
	public static final int CDR_STATUS_OB_PREDICTIVE_ANSWER=41;
	public static final int CDR_STATUS_OB_PREDICTIVE_CALLED=42;
	public static final int CDR_STATUS_OB_PREDICTIVE_BRIDGED=43;

	public static final int CDR_STATUS_OB_DIRECT_ANSWER=40;
	public static final int CDR_STATUS_OB_DIRECT_CALLED=42;
	public static final int CDR_STATUS_OB_DIRECT_BRIDGED=43;

	public static final int CDR_STATUS_DETAIL_CALL_FAIL=1;
	public static final int CDR_STATUS_DETAIL_ANSWER=2;
}
