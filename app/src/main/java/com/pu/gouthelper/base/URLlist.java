package com.pu.gouthelper.base;

/**
 * Created by gyj on 2016/3/4.
 */
public class URLlist {
    public static final String URI_IP = "http://www.tfzs999.com";

    /**
     * 推送注册
     */
    public static final String JPUSH = URI_IP + "/Mobile/Api/set_client_id";
    /**
     * 首页轮播图
     */
    public static final String SLIDE_LIST = URI_IP + "/Mobile/Slide/lists";
    /**
     * login
     */
    public static final String LOGIN = URI_IP + "/Mobile/Passport/login";
    /**
     * 个人信息上传
     */
    public static final String SUBMIT_MYINFO = URI_IP + "/Mobile/User/save_profile";
    /**
     * 个人信息获取 电子病历
     */
    public static final String GET_MYINFO = URI_IP + "/Mobile/User/profile";
    /**
     * 获取短信验证码
     */
    public static final String GET_MOBILE_CODE = URI_IP + "/Mobile/Passport/get_mobile_code";
    /**
     * 验证短信验证码
     */
    public static final String VERIFY_MOBILE_CODE = URI_IP + "/Mobile/Passport/verify_mobile_code";
    /**
     * 注册接口post
     */
    public static final String REGISTER = URI_IP + "/Mobile/Passport/reg";
    /**
     * 嘌呤列表查询 purin
     */
    public static final String PURIN_LIST = URI_IP + "/Mobile/Food/lists";
    /**
     * 嘌呤列表查询 详情
     */
    public static final String PURIN_LIST_INFO = URI_IP + "/Mobile/Food/show";

    /**
     * 痛风知识 分类
     */
    public static final String GOUT_MSG_CATE = URI_IP + "/Mobile/News/cates";
    /**
     * 痛风知识 列表
     */
    public static final String GOUT_MSG_LIST = URI_IP + "/Mobile/News/lists";
    /**
     * 痛风知识 详情
     */
    public static final String GOUT_MSG_INFO = URI_IP + "/Mobile/News/show";
    /**
     * 药品口碑 列表
     */
    public static final String GOUT_DRUG_lIST = URI_IP + "/Mobile/Drug/lists";
    /**
     * 药品口碑 详情
     */
    public static final String GOUT_DRUG_INFO = URI_IP + "/Mobile/Drug/show";
    /**
     * 互助社区 发布帖子
     */
    public static final String GOUT_TOPIC_ADD = URI_IP + "/Mobile/Topic/add";
    /**
     * 互助社区 帖子列表
     */
    public static final String GOUT_TOPIC_LIST = URI_IP + "/Mobile/Topic/lists";
    /**
     * 互助社区 帖子详情
     */
    public static final String GOUT_TOPIC_INFO = URI_IP + "/Mobile/Topic/show";
    /**
     * 个人中心  我的帖子
     */
    public static final String GOUT_USER_TOPIC = URI_IP + "/Mobile/User/topic";
    /**
     * 个人中心  我的消息
     */
    public static final String USER_MESSAGE = URI_IP + "/Mobile/User/message";
    /**
     * 个人中心  碱性食谱
     */
    public static final String USER_RECIPE_LIST = URI_IP + "/Mobile/Recipe/lists";
    /**
     * 个人中心  碱性食谱 详情
     */
    public static final String USER_RECIPE_INFO = URI_IP + "/Mobile/Recipe/show";
    /**
     * 发作记录
     */
    public static final String ATTACK_RECORED = URI_IP + "/Mobile/User/attack_records";
    /**
     * 标记发作
     */
    public static final String ADD_ATTACK_RECORED = URI_IP + "/Mobile/User/add_attack_records";
    /**
     * 嗑药提醒
     */
    public static final String DRUG_CLOCK = URI_IP + "/Mobile/User/clock";

    /**
     * 嗑药提醒
     */
    public static final String ADD_DRUG_CLOCK = URI_IP + "/Mobile/User/add_clock";
    /**
     * 删除提醒
     */
    public static final String DEL_DRUG_CLOCK = URI_IP + "/Mobile/User/del_clock";
    /**
     * 评论 列表
     */
    public static final String COMMENT_LIST = URI_IP + "/Mobile/Api/get_comment_list";
    /**
     * 评论 回复
     */
    public static final String ADD_COMMENT = URI_IP + "/Mobile/Api/add_comment";
    /**
     * 发作记录
     */
    public static final String URL_FORECAST = URI_IP + "/Mobile/User/forecast";

    /**
     * 发作记录文章
     */
    public static final String URL_FORECAST_NEWS = URI_IP + "/Mobile/User/forecast_news";
    /**
     * 密码修改
     */
    public static final String URL_CHANGE_PSW = URI_IP + "/Mobile/User/change_password";
    /**
     * 意见反馈
     */
    public static final String URL_FEEDBACK = URI_IP + "/Mobile/User/feedback";

    /**
     * 踩
     */
    public static final String URL_UP = URI_IP + "/Mobile/Drug/up";
    /**
     * 赞
     */
    public static final String URL_DOWN = URI_IP + "/Mobile/Drug/down";

    /**
     * 修改头像
     */
    public static final String URL_USER_IMG = URI_IP + "/Mobile/User/save_profile_img";

    /**
     * 收货地址列表
     */
    public static final String URL_USER_ADDRESS = URI_IP + "/Mobile/User/address";

    /**
     * 添加收货地址
     */
    public static final String URL_USER_ADD_ADDRESS = URI_IP + "/Mobile/User/add_address";

    /**
     * 删除收藏地址
     */
    public static final String URL_USER_DEL_ADDRESS = URI_IP + "/Mobile/User/del_address";
    /**
     * 捐献列表
     */
    public static final String URL_REWARD_LIST = URI_IP + "/Mobile/reward/lists";


}
