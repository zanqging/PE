package com.jinzht.pro;

import android.os.Environment;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/5/26
 * @time 9:11
 */

public class Constant {

    // APP_ID 替换为你的应用从官方网站申请到的合法appId

    public static String FILE_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String APP_ID = "wx33aa0167f6a81dac";
    public static final String TEL = "18710948468";
    public static final int COLLECT_FLAG = 0;
    public static final int RECOMMEDPLAT = 1;
    public static final int WATIFACNCE = 2;
    public static final int FANCECOMPLETE = 3;
    public static final int FANCEINVESTOR = 4;
    public static int MAX_IMAGE = 9;
    public static final String MY_ACTION = "aaa.bbb";
    public static final String HIDE_CIRCLE = "bb.cc.ee";
    public static final String SHOW_CIRCLE = "dd.ee.ff";
    public static final int TAKE_PICTURE = 0;//选择照片的方式照相
    public static final int CHOOSE_PICTURE = 1;//选择照片的方式相册
    public static final int WANT_ADD_COMPANY = 99;
    public static final String ACTIVITY_WANT_ROAD_SHOW = "1";
    public static final int RESULT_WANT_ROAD_SHOW = -1;
    public static final int RESULT_AUTHERTION = -2;
    public static final String ACTIVITY_AUTHERTION = "2";
    public static final int AUTHERTIONFLAG = 1;
    public static final int ROADSHOWFLAG = 2;
    public static final int COMELOCEALFLAG = 3;
    public static final int SCALE = 10;//照片缩小比例
    public static final int SEARCH_INVEST = 1;
    public static final int SEARCH_THREE_BORAD = 2;
    public static final int SHARE_PROJECT = 1;
    public static final int SHARE_NEWS = 2;
    public static final int SHARE_APP = 3;
    public static final int PERSON_INVESTOR = 1;
    public static final int ORI_INVESTOR = 2;
    public static final String BASE_URL = "http://www.jinzht.com/";
//    public static final String BASE_URL = "http://115.28.110.243:8000/";// 测试接口
    public static final String PHONE = "phone5/";
//    public static final String PHONE = "phone/";// 测试接口
    /**
     * 登录接口
     */
    public static final String LOGIN = "login/";
    /**
     * 修改密码接口
     */
    public static final String MODIFY = "modifypasswd/";

    /**
     * 修改姓别，1是男，0是女
     */
    public static final String SEX = "gender/";
    /**
     * 修改真实姓名
     */
    public static final String REALNAME = "realname/";
    /**
     * 修改职位
     */
    public static final String POSITION = "position/";
    /**
     * 修改公司
     */
    public static final String COMPANY = "company/";
    /**
     * 修改昵称
     */
    public static final String NICK_NAME = "nickname/";
    /**
     * 修改手机号码
     */
    public static final String TELEPHONE = "telephone/";
    /**
     * 修改头像的接口
     */
    public static final String IMAGEURL = "userimg/";
    /**
     * 获取Banner的接口
     */
    public static final String BANNER = "banner/";
    /**
     * 征信查询的接口
     */
    public static final String CREDIT = "credit/";
    /**
     * 主页的好几个何在一起的接口
     */
    public static final String HOME_PAGE = "home/";
    /**
     * 获取微路演的接口
     */
    public static final String STRAGGERED = "project/";
    /**
     * 获取平台推荐的接口
     */
    public static final String RECOMMEND = "recommendproject/";
    /**
     * 获取验证码
     */
    public static final String SENDCODE = "sendcode/";
    /**
     * 忘记密码验证验证码是否正确
     */
    public static final String CHECKCODE = "checkcode/";
    /**
     * 忘记密码最后一步提交
     */
    public static final String RESET = "resetpasswd/";
    /**
     * 获取融资完成的接口
     */
    public static final String FINISHFINACNCE = "finishfinance/";
    /**
     * 注册的接口
     */
    public static final String REGISTER = "registe/";
    /**
     * 投融资页面投资人列表
     */
    public static final String INVESTOR = "thinktank/";

    /**
     * 行业类别列表
     */
    public static final String WORKFIELD = "industrytype/";
    /**
     * 公司状态列表
     */
    public static final String COMPANYSTATUS = "companystatus/";
    /**
     * 融资计划接口
     */
    public static final String FINANCEPLAN = "financeplan/";

    /**
     * 核心团队接口
     */
    public static final String CORETEAM = "member/";
    /**
     * 投资列表接口
     */
    public static final String INVESTORLIST = "investlist/";
    /**
     * 投资详情页的接口
     */
    public static final String PROGECTDETAILS = "projectdetail/";

    /**
     * 投资人条件的接口
     */
    public static final String INVESTORCONDITION = "investorqualification/";
    /**
     * 我延路演的公司添加
     */
    public static final String ADDCOMPANY = "addcompany/";
    /**
     * 我要签到一进来拉取信息接口
     */
    public static final String GETSIGIN = "activity/";

    /**
     * 我要签到的提交
     */
    public static final String SIGNIN = "signin/";
    /**
     * 推送的register_id
     */
    public static final String REGID = "regid/";
    /**
     * 融资中的接口
     */
    public static final String WAITFINACE = "waitforfinance/";
    /**
     * 投资人认证0是个人1，一是机构投资者
     */
    public static final String AUTHENTICATE = "authenticate/";
    /**
     * 身份证上传
     */
    public static final String IDENTITY = "idfore/";
    /**
     * 我要路演和我要投资的公司的列表
     */
    public static final String COMPANYLIST = "companylist";
    /**
     * 来现场的接口
     */
    public static final String COMELOACLE = "attend/";
    /**
     * 用户类别
     */
    public static final String USERTYPE = "positiontype/";
    /**
     * 修改用户类别的接口
     */
    public static final String MODIFYTYPE = "modifypositiontype/";
    /**
     * 反馈接口
     */
    public static final String FREEBACK = "feedback/";
    /**
     * 投票接口
     */
    public static final String VOTE = "vote/";
    /**
     * 个人中心拉取基本信息的接口
     */
    public static final String GETINFORMATION = "userinfo/";
    /**
     * 收藏的接口
     */
    public static final String COLLECT = "collect/";
    /**
     * 喜欢的接口
     */
    public static final String LOVE = "like/";
    /**
     * 收藏的待融资
     */
    public static final String MYROADSHOW = "collectfinance/";
    /**
     * 收藏的融资中
     */
    public static final String COLLECTFINACING = "collectfinancing/";
    /**
     * 收藏的已融资
     */
    public static final String COLLECTFINACED = "collectfinanced/";
    /**
     * 收藏的预选项目
     */
    public static final String COLLECT_PRE_LIST = "collectupload/";
    /**
     * 上传项目
     */
    public static final String WANTROADSHOW = "upload/";
    /**
     * 公司详情的卡接口
     */
    public static final String COMPANYINFO = "companyinfo/";
    /**
     * 名片的接口
     */
    public static final String CARD = "businesscard/";
    /**
     * 侧边栏接口
     */
    public static final String MODIFYIMAGE = "leftslide/";
    /**
     * 上传个人头像
     */
    public static final String POST_IMAGE = "photo/";
    /**
     * 七牛获取token的代码
     */
    public static final String TOKEN = "token/";
    /**
     * 判断是否是投资人
     */
    public static final String ISINVESTOR = "myauth/";
    /**
     * 关键词列表
     */
    public static final String KEYWORD = "keyword/";
    /**
     * 搜索的接口
     */
    public static final String SEARCH = "projectsearch/";
    /**
     * 我要投资的列表接口
     */
    public static final String MYINVESTOR = "myinvestorlist/";
    /**
     * 我要投资提交资料
     */
    public static final String WANTINCEST = "wantinvest/";
    /**
     * 基金规模接口
     */
    public static final String FUNDSIZE = "fundsizerange/";
    /**
     * 进度查看的接口
     */
    public static final String PROGRESSCHECK = "myinvestorauthentication/";
    /**
     * 判断是不是我的项目
     */
    public static final String ISMYPROJECT = "ismyproject/";
    /**
     * 我投资的项目
     */
    public static final String MYINVEST = "myinvest/";
    /**
     * 我上传的项目
     */
    public static final String MYCREATE = "myupload/";
    /**
     * 投资人的详情
     */
    public static final String INVESTORINFO = "investorinfo/";
    /**
     * 智囊团的详情页
     */
    public static final String THINKTANKDETAIL = "thinktankdetail/";
    /**
     * 电话号码的接口
     */
    public static final String TELEPHONENUM = "contactus/";
    /**
     * 版本更新的接口
     */
    public static final String UPDATEVERSON = "checkupdate/";
    /**
     * 修改地域
     */
    public static final String AREA = "addr/";
    /**
     * 分享的接口
     */
    public static final String SHAREAPP = "shareapp/";
    /**
     * 分享项目的接口
     */
    public static final String SHAREPROJECT = "shareproject/";
    /**
     * 注册的协议
     */
    public static final String REGISTERXY = "useragreement/";
    /**
     * 投资人认证的风险提示的接口
     */
    public static final String AUTHNOTICE = "risk/";
    /**
     * 我要路演的分线提示的接口
     */
    public static final String ROADNOTICE = "aboutroadshow/";
    /**
     * 隐私政策的接口
     */
    public static final String PRINOTICE = "privacy/";
    /**
     * 核心团队的详情接口
     */
    public static final String CORETEAMDETAIL = "corememberdetail/";
    /**
     * 退出登录
     */
    public static final String EXITLOGIN = "logout/";
    /**
     * 来现场进度查看
     */
    public static final String ROADSHOWPROCESS = "myparticipate/";
    /**
     * 我的路演进度查看
     */
    public static final String MYPROGRESSROADSHOW = "myroadshow/";
    /**
     * 发表评论的接口
     */
    public static final String POSTCOMEMNTS = "topic/";
    /**
     * 回复的列表的接口
     */
    public static final String COMMENTSLIST = "topiclist/";
    /**
     * 回复一级列表
     */
    public static final String REPLYCOMMENTS = "reply/";
    /**
     * 回复消息
     */
    public static final String MYMESSAGELIST = "mytopic/";
    /**
     * 新三板判断是否有滑动title
     */
    public static final String NEWS = "newstype/";
    /**
     * 新三板的列表的接口
     */
    public static final String NEWSLIST = "news/";
//    /**测试的pdf*/
//    public static final String TEXT = "https://cs.au.dk/~adc/files/vimtutor.pdf";
    /**
     * 新三板知识库的接口
     */
    public static final String KNOWLEDGE = "knowledge/";
    /**
     * 新三板知识库的tag的接口
     */
    public static final String KONWLEDGETAG = "knowledgetag/";
    /**
     * 新三板的搜索title列表的接口
     */
    public static final String SEARCH_TITLE = "newssearch/";
    /**
     * 新三板的tag的列表的接口
     */
    public static final String NEWS_TAG_LIST = "newstagsearch/";
    /**
     * 新三板的喜欢的接口和判断时候是否喜欢过
     */
    public static final String THREE_READ = "newsread/";
    /***/
    public static final String THREE_TRUE_LOVE = "newslike/";
    /**
     * 新闻的分享链接
     */
    public static final String NEWS_SHARE = "sharenews/";
    /**
     * 是否阅读
     */
    public static final String READ_NEWS_MSG = "hasinform/";
    public static final String READ_TOPOIC_MSG = "hastopic/";
    /**
     * 系统消息的提示的接口
     */
    public static final String SYSTEM_NOTICE = "systeminform/";
    /***/
    public static final String TOPIC_READ = "mytopiclist/";
    /**
     * 删除系统恢复
     */
    public static final String DELETE_SYSMSG = "deletesysteminform/";
    /**
     * 删除系统恢复
     */
    public static final String TOP_READMSG = "readtopic/";
    /**
     * 设置为已阅读
     */
    public static final String READ_SYSOVER = "setsysteminform/";
    /**
     * 发表状态
     */
    public static final String PUBLISH_STATES = "postfeeling/";
    /**
     * 朋友圈子的列表接口
     */
    public static final String CIRCLE_LIST = "feeling/";
    /**
     * 回复某一个评论
     */
    public static final String REPLY_SOMEONE = "postfeelingcomment/";
    /**
     * 删除某一个朋友圈
     */
    public static final String DELETE_SOMEONE = "deletefeeling/";
    /**
     * 朋友圈的点赞
     */
    public static final String LOVE_SOMEONE = "likefeeling/";
    /**
     * 某一个圈子的点赞详情
     */
    public static final String CIRCLE_LOVE_DETAIL = "feelinglikers/";
    /**
     * 某一个圈子的评论详情
     */
    public static final String CIRCLE_COMMENT_DETAIL = "feelingcomment/";
    /**
     * 圈子的header
     */
    public static final String CIRCLEHEADER = "bg/";
    /**
     * 圈子的某个具体恢复的删除
     */
    public static final String DELETE_REPLY_SOME = "hidefeelingcomment/";
    /**
     * 投融资的代码
     */
    public static final String TITLE_FINACING = "defaultclassify/";
    /**
     * 此乃项目库的title
     */
    public static final String PROJECT_TITLE = "project/";
    public static final String FIRST_INVEST = "cursor/";

    /**
     * 新三板的分享量+1接口
     */
    public static final String SHARE_NUM = "newsshare/";
    /**
     * 投资人认证的条件
     */
    public static final String INVEST_CONDITION = "auth/";
    /**
     * 智囊团的接口
     */
    public static final String THINK_TANK = "thinktank/";
    /**
     * 投资人的接口
     */
    public static final String INVESTOR_INVESTOR = "investor/";
    /**
     * 检测用不用微信登录了
     */
    public static final String OPEN_ID = "openid/";
    /**
     * 电话号码的接口
     */
    public static final String MAIN_TELEPHONE = "customservice/";
    /**
     * 个人投资者的详情页
     */
    public static final String PERSON_DETAIL = "authdetail/";
    /**
     * 机构投资者的详情页
     */
    public static final String ORI_DETAIL = "institutedetail/";
    /**
     * 预选项目的详情页
     */
    public static final String PRE_DETAIL = "uploaddetail/";
    /**
     * 这是个人投资的第二个接口
     */
    public static final String PERSON_AUTH_NEXT = "authpersonoptional/";
    /**
     * 预选项目的收藏
     */
    public static final String PRE_COLLECT = "uploadcollect/";
    /**
     * 预选项目的点赞
     */
    public static final String PRE_LIKE = "uploadlike/";
    /**
     * 测试
     */
    public static final String TEXT = "text/";
    /**
     * 是否已投资过
     */
    public static final String ISTENDERED = "IsTendered/";
    /**
     * 签名和验签，根据不同参数
     */
    public static final String SIGNVERIFY = "signVerify/";
    /**
     * 易宝直连接口
     */
    // 测试环境
    public static final String YIBAODIRECT = "http://220.181.25.233:8081/member/bhaexter/bhaController";
    // 生产环境
//    public static final String YIBAODIRECT = "https://member.yeepay.com/member/bhaexter/bhaController";
    /**
     * 易宝移动端网关接口
     */
    // 测试环境
    public static final String YIBAOGATEWAY = "http://220.181.25.233:8081/member/bhawireless/";
    // 生产环境
//    public static final String YIBAOGATEWAY = "https://member.yeepay.com/member/bhawireless/";
    /**
     * 易宝直连方式账户查询
     */
    public static final String YIBAOACCOUNTINFO = "ACCOUNT_INFO";
    /**
     * 易宝直连方式解绑银行卡
     */
    public static final String YIBAOUNBIND = "UNBIND_CARD";
    /**
     * 易宝移动端网关方式注册
     */
    public static final String YIBAOREGISTER = "toRegister";
    /**
     * 易宝移动端网关方式充值
     */
    public static final String YIBAORECHARGE = "toRecharge";
    /**
     * 易宝移动端网关方式转账授权
     */
    public static final String YIBAOTRANSACTION = "toCpTransaction";
    /**
     * 易宝移动端网关方式绑定银行卡
     */
    public static final String YIBAOBINDCARD = "toBindBankCard";
    /**
     * 易宝移动端网关方式绑定银行卡
     */
    public static final String YIBAOAUTOTRANSFER = "toAuthorizeAutoTransfer";
    /**
     * 易宝移动端网关方式提现
     */
    public static final String YIBAOWITHDRAW = "toWithdraw";
    /**
     * 易宝回跳页面
     */
    // 自己tomcat上的接口
//    public static final String YIBAOCALLBACK = "http://192.168.5.112:8080/YibaoCallback.jsp";
    // 金指投服务端上的接口
    public static final String YIBAOCALLBACK = "yeePayCallBack/";
    /**
     * 易宝回调通知
     */
    public static final String YIBAONOTIFY = "http://www.jinzht.com/phone5/notify/";
}
