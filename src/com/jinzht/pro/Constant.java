package com.jinzht.pro;

import android.os.Environment;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/5/26
 * @time 9:11
 */

public class Constant {

    // APP_ID �滻Ϊ���Ӧ�ôӹٷ���վ���뵽�ĺϷ�appId

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
    public static final int TAKE_PICTURE = 0;//ѡ����Ƭ�ķ�ʽ����
    public static final int CHOOSE_PICTURE = 1;//ѡ����Ƭ�ķ�ʽ���
    public static final int WANT_ADD_COMPANY = 99;
    public static final String ACTIVITY_WANT_ROAD_SHOW = "1";
    public static final int RESULT_WANT_ROAD_SHOW = -1;
    public static final int RESULT_AUTHERTION = -2;
    public static final String ACTIVITY_AUTHERTION = "2";
    public static final int AUTHERTIONFLAG = 1;
    public static final int ROADSHOWFLAG = 2;
    public static final int COMELOCEALFLAG = 3;
    public static final int SCALE = 10;//��Ƭ��С����
    public static final int SEARCH_INVEST = 1;
    public static final int SEARCH_THREE_BORAD = 2;
    public static final int SHARE_PROJECT = 1;
    public static final int SHARE_NEWS = 2;
    public static final int SHARE_APP = 3;
    public static final int PERSON_INVESTOR = 1;
    public static final int ORI_INVESTOR = 2;
    public static final String BASE_URL = "http://www.jinzht.com/";
//    public static final String BASE_URL = "http://115.28.110.243:8000/";// ���Խӿ�
    public static final String PHONE = "phone5/";
//    public static final String PHONE = "phone/";// ���Խӿ�
    /**
     * ��¼�ӿ�
     */
    public static final String LOGIN = "login/";
    /**
     * �޸�����ӿ�
     */
    public static final String MODIFY = "modifypasswd/";

    /**
     * �޸��ձ�1���У�0��Ů
     */
    public static final String SEX = "gender/";
    /**
     * �޸���ʵ����
     */
    public static final String REALNAME = "realname/";
    /**
     * �޸�ְλ
     */
    public static final String POSITION = "position/";
    /**
     * �޸Ĺ�˾
     */
    public static final String COMPANY = "company/";
    /**
     * �޸��ǳ�
     */
    public static final String NICK_NAME = "nickname/";
    /**
     * �޸��ֻ�����
     */
    public static final String TELEPHONE = "telephone/";
    /**
     * �޸�ͷ��Ľӿ�
     */
    public static final String IMAGEURL = "userimg/";
    /**
     * ��ȡBanner�Ľӿ�
     */
    public static final String BANNER = "banner/";
    /**
     * ���Ų�ѯ�Ľӿ�
     */
    public static final String CREDIT = "credit/";
    /**
     * ��ҳ�ĺü�������һ��Ľӿ�
     */
    public static final String HOME_PAGE = "home/";
    /**
     * ��ȡ΢·�ݵĽӿ�
     */
    public static final String STRAGGERED = "project/";
    /**
     * ��ȡƽ̨�Ƽ��Ľӿ�
     */
    public static final String RECOMMEND = "recommendproject/";
    /**
     * ��ȡ��֤��
     */
    public static final String SENDCODE = "sendcode/";
    /**
     * ����������֤��֤���Ƿ���ȷ
     */
    public static final String CHECKCODE = "checkcode/";
    /**
     * �����������һ���ύ
     */
    public static final String RESET = "resetpasswd/";
    /**
     * ��ȡ������ɵĽӿ�
     */
    public static final String FINISHFINACNCE = "finishfinance/";
    /**
     * ע��Ľӿ�
     */
    public static final String REGISTER = "registe/";
    /**
     * Ͷ����ҳ��Ͷ�����б�
     */
    public static final String INVESTOR = "thinktank/";

    /**
     * ��ҵ����б�
     */
    public static final String WORKFIELD = "industrytype/";
    /**
     * ��˾״̬�б�
     */
    public static final String COMPANYSTATUS = "companystatus/";
    /**
     * ���ʼƻ��ӿ�
     */
    public static final String FINANCEPLAN = "financeplan/";

    /**
     * �����Ŷӽӿ�
     */
    public static final String CORETEAM = "member/";
    /**
     * Ͷ���б�ӿ�
     */
    public static final String INVESTORLIST = "investlist/";
    /**
     * Ͷ������ҳ�Ľӿ�
     */
    public static final String PROGECTDETAILS = "projectdetail/";

    /**
     * Ͷ���������Ľӿ�
     */
    public static final String INVESTORCONDITION = "investorqualification/";
    /**
     * ����·�ݵĹ�˾���
     */
    public static final String ADDCOMPANY = "addcompany/";
    /**
     * ��Ҫǩ��һ������ȡ��Ϣ�ӿ�
     */
    public static final String GETSIGIN = "activity/";

    /**
     * ��Ҫǩ�����ύ
     */
    public static final String SIGNIN = "signin/";
    /**
     * ���͵�register_id
     */
    public static final String REGID = "regid/";
    /**
     * �����еĽӿ�
     */
    public static final String WAITFINACE = "waitforfinance/";
    /**
     * Ͷ������֤0�Ǹ���1��һ�ǻ���Ͷ����
     */
    public static final String AUTHENTICATE = "authenticate/";
    /**
     * ���֤�ϴ�
     */
    public static final String IDENTITY = "idfore/";
    /**
     * ��Ҫ·�ݺ���ҪͶ�ʵĹ�˾���б�
     */
    public static final String COMPANYLIST = "companylist";
    /**
     * ���ֳ��Ľӿ�
     */
    public static final String COMELOACLE = "attend/";
    /**
     * �û����
     */
    public static final String USERTYPE = "positiontype/";
    /**
     * �޸��û����Ľӿ�
     */
    public static final String MODIFYTYPE = "modifypositiontype/";
    /**
     * �����ӿ�
     */
    public static final String FREEBACK = "feedback/";
    /**
     * ͶƱ�ӿ�
     */
    public static final String VOTE = "vote/";
    /**
     * ����������ȡ������Ϣ�Ľӿ�
     */
    public static final String GETINFORMATION = "userinfo/";
    /**
     * �ղصĽӿ�
     */
    public static final String COLLECT = "collect/";
    /**
     * ϲ���Ľӿ�
     */
    public static final String LOVE = "like/";
    /**
     * �ղصĴ�����
     */
    public static final String MYROADSHOW = "collectfinance/";
    /**
     * �ղص�������
     */
    public static final String COLLECTFINACING = "collectfinancing/";
    /**
     * �ղص�������
     */
    public static final String COLLECTFINACED = "collectfinanced/";
    /**
     * �ղص�Ԥѡ��Ŀ
     */
    public static final String COLLECT_PRE_LIST = "collectupload/";
    /**
     * �ϴ���Ŀ
     */
    public static final String WANTROADSHOW = "upload/";
    /**
     * ��˾����Ŀ��ӿ�
     */
    public static final String COMPANYINFO = "companyinfo/";
    /**
     * ��Ƭ�Ľӿ�
     */
    public static final String CARD = "businesscard/";
    /**
     * ������ӿ�
     */
    public static final String MODIFYIMAGE = "leftslide/";
    /**
     * �ϴ�����ͷ��
     */
    public static final String POST_IMAGE = "photo/";
    /**
     * ��ţ��ȡtoken�Ĵ���
     */
    public static final String TOKEN = "token/";
    /**
     * �ж��Ƿ���Ͷ����
     */
    public static final String ISINVESTOR = "myauth/";
    /**
     * �ؼ����б�
     */
    public static final String KEYWORD = "keyword/";
    /**
     * �����Ľӿ�
     */
    public static final String SEARCH = "projectsearch/";
    /**
     * ��ҪͶ�ʵ��б�ӿ�
     */
    public static final String MYINVESTOR = "myinvestorlist/";
    /**
     * ��ҪͶ���ύ����
     */
    public static final String WANTINCEST = "wantinvest/";
    /**
     * �����ģ�ӿ�
     */
    public static final String FUNDSIZE = "fundsizerange/";
    /**
     * ���Ȳ鿴�Ľӿ�
     */
    public static final String PROGRESSCHECK = "myinvestorauthentication/";
    /**
     * �ж��ǲ����ҵ���Ŀ
     */
    public static final String ISMYPROJECT = "ismyproject/";
    /**
     * ��Ͷ�ʵ���Ŀ
     */
    public static final String MYINVEST = "myinvest/";
    /**
     * ���ϴ�����Ŀ
     */
    public static final String MYCREATE = "myupload/";
    /**
     * Ͷ���˵�����
     */
    public static final String INVESTORINFO = "investorinfo/";
    /**
     * �����ŵ�����ҳ
     */
    public static final String THINKTANKDETAIL = "thinktankdetail/";
    /**
     * �绰����Ľӿ�
     */
    public static final String TELEPHONENUM = "contactus/";
    /**
     * �汾���µĽӿ�
     */
    public static final String UPDATEVERSON = "checkupdate/";
    /**
     * �޸ĵ���
     */
    public static final String AREA = "addr/";
    /**
     * ����Ľӿ�
     */
    public static final String SHAREAPP = "shareapp/";
    /**
     * ������Ŀ�Ľӿ�
     */
    public static final String SHAREPROJECT = "shareproject/";
    /**
     * ע���Э��
     */
    public static final String REGISTERXY = "useragreement/";
    /**
     * Ͷ������֤�ķ�����ʾ�Ľӿ�
     */
    public static final String AUTHNOTICE = "risk/";
    /**
     * ��Ҫ·�ݵķ�����ʾ�Ľӿ�
     */
    public static final String ROADNOTICE = "aboutroadshow/";
    /**
     * ��˽���ߵĽӿ�
     */
    public static final String PRINOTICE = "privacy/";
    /**
     * �����Ŷӵ�����ӿ�
     */
    public static final String CORETEAMDETAIL = "corememberdetail/";
    /**
     * �˳���¼
     */
    public static final String EXITLOGIN = "logout/";
    /**
     * ���ֳ����Ȳ鿴
     */
    public static final String ROADSHOWPROCESS = "myparticipate/";
    /**
     * �ҵ�·�ݽ��Ȳ鿴
     */
    public static final String MYPROGRESSROADSHOW = "myroadshow/";
    /**
     * �������۵Ľӿ�
     */
    public static final String POSTCOMEMNTS = "topic/";
    /**
     * �ظ����б�Ľӿ�
     */
    public static final String COMMENTSLIST = "topiclist/";
    /**
     * �ظ�һ���б�
     */
    public static final String REPLYCOMMENTS = "reply/";
    /**
     * �ظ���Ϣ
     */
    public static final String MYMESSAGELIST = "mytopic/";
    /**
     * �������ж��Ƿ��л���title
     */
    public static final String NEWS = "newstype/";
    /**
     * ��������б�Ľӿ�
     */
    public static final String NEWSLIST = "news/";
//    /**���Ե�pdf*/
//    public static final String TEXT = "https://cs.au.dk/~adc/files/vimtutor.pdf";
    /**
     * ������֪ʶ��Ľӿ�
     */
    public static final String KNOWLEDGE = "knowledge/";
    /**
     * ������֪ʶ���tag�Ľӿ�
     */
    public static final String KONWLEDGETAG = "knowledgetag/";
    /**
     * �����������title�б�Ľӿ�
     */
    public static final String SEARCH_TITLE = "newssearch/";
    /**
     * �������tag���б�Ľӿ�
     */
    public static final String NEWS_TAG_LIST = "newstagsearch/";
    /**
     * �������ϲ���Ľӿں��ж�ʱ���Ƿ�ϲ����
     */
    public static final String THREE_READ = "newsread/";
    /***/
    public static final String THREE_TRUE_LOVE = "newslike/";
    /**
     * ���ŵķ�������
     */
    public static final String NEWS_SHARE = "sharenews/";
    /**
     * �Ƿ��Ķ�
     */
    public static final String READ_NEWS_MSG = "hasinform/";
    public static final String READ_TOPOIC_MSG = "hastopic/";
    /**
     * ϵͳ��Ϣ����ʾ�Ľӿ�
     */
    public static final String SYSTEM_NOTICE = "systeminform/";
    /***/
    public static final String TOPIC_READ = "mytopiclist/";
    /**
     * ɾ��ϵͳ�ָ�
     */
    public static final String DELETE_SYSMSG = "deletesysteminform/";
    /**
     * ɾ��ϵͳ�ָ�
     */
    public static final String TOP_READMSG = "readtopic/";
    /**
     * ����Ϊ���Ķ�
     */
    public static final String READ_SYSOVER = "setsysteminform/";
    /**
     * ����״̬
     */
    public static final String PUBLISH_STATES = "postfeeling/";
    /**
     * ����Ȧ�ӵ��б�ӿ�
     */
    public static final String CIRCLE_LIST = "feeling/";
    /**
     * �ظ�ĳһ������
     */
    public static final String REPLY_SOMEONE = "postfeelingcomment/";
    /**
     * ɾ��ĳһ������Ȧ
     */
    public static final String DELETE_SOMEONE = "deletefeeling/";
    /**
     * ����Ȧ�ĵ���
     */
    public static final String LOVE_SOMEONE = "likefeeling/";
    /**
     * ĳһ��Ȧ�ӵĵ�������
     */
    public static final String CIRCLE_LOVE_DETAIL = "feelinglikers/";
    /**
     * ĳһ��Ȧ�ӵ���������
     */
    public static final String CIRCLE_COMMENT_DETAIL = "feelingcomment/";
    /**
     * Ȧ�ӵ�header
     */
    public static final String CIRCLEHEADER = "bg/";
    /**
     * Ȧ�ӵ�ĳ������ָ���ɾ��
     */
    public static final String DELETE_REPLY_SOME = "hidefeelingcomment/";
    /**
     * Ͷ���ʵĴ���
     */
    public static final String TITLE_FINACING = "defaultclassify/";
    /**
     * ������Ŀ���title
     */
    public static final String PROJECT_TITLE = "project/";
    public static final String FIRST_INVEST = "cursor/";

    /**
     * ������ķ�����+1�ӿ�
     */
    public static final String SHARE_NUM = "newsshare/";
    /**
     * Ͷ������֤������
     */
    public static final String INVEST_CONDITION = "auth/";
    /**
     * �����ŵĽӿ�
     */
    public static final String THINK_TANK = "thinktank/";
    /**
     * Ͷ���˵Ľӿ�
     */
    public static final String INVESTOR_INVESTOR = "investor/";
    /**
     * ����ò���΢�ŵ�¼��
     */
    public static final String OPEN_ID = "openid/";
    /**
     * �绰����Ľӿ�
     */
    public static final String MAIN_TELEPHONE = "customservice/";
    /**
     * ����Ͷ���ߵ�����ҳ
     */
    public static final String PERSON_DETAIL = "authdetail/";
    /**
     * ����Ͷ���ߵ�����ҳ
     */
    public static final String ORI_DETAIL = "institutedetail/";
    /**
     * Ԥѡ��Ŀ������ҳ
     */
    public static final String PRE_DETAIL = "uploaddetail/";
    /**
     * ���Ǹ���Ͷ�ʵĵڶ����ӿ�
     */
    public static final String PERSON_AUTH_NEXT = "authpersonoptional/";
    /**
     * Ԥѡ��Ŀ���ղ�
     */
    public static final String PRE_COLLECT = "uploadcollect/";
    /**
     * Ԥѡ��Ŀ�ĵ���
     */
    public static final String PRE_LIKE = "uploadlike/";
    /**
     * ����
     */
    public static final String TEXT = "text/";
    /**
     * �Ƿ���Ͷ�ʹ�
     */
    public static final String ISTENDERED = "IsTendered/";
    /**
     * ǩ������ǩ�����ݲ�ͬ����
     */
    public static final String SIGNVERIFY = "signVerify/";
    /**
     * �ױ�ֱ���ӿ�
     */
    // ���Ի���
    public static final String YIBAODIRECT = "http://220.181.25.233:8081/member/bhaexter/bhaController";
    // ��������
//    public static final String YIBAODIRECT = "https://member.yeepay.com/member/bhaexter/bhaController";
    /**
     * �ױ��ƶ������ؽӿ�
     */
    // ���Ի���
    public static final String YIBAOGATEWAY = "http://220.181.25.233:8081/member/bhawireless/";
    // ��������
//    public static final String YIBAOGATEWAY = "https://member.yeepay.com/member/bhawireless/";
    /**
     * �ױ�ֱ����ʽ�˻���ѯ
     */
    public static final String YIBAOACCOUNTINFO = "ACCOUNT_INFO";
    /**
     * �ױ�ֱ����ʽ������п�
     */
    public static final String YIBAOUNBIND = "UNBIND_CARD";
    /**
     * �ױ��ƶ������ط�ʽע��
     */
    public static final String YIBAOREGISTER = "toRegister";
    /**
     * �ױ��ƶ������ط�ʽ��ֵ
     */
    public static final String YIBAORECHARGE = "toRecharge";
    /**
     * �ױ��ƶ������ط�ʽת����Ȩ
     */
    public static final String YIBAOTRANSACTION = "toCpTransaction";
    /**
     * �ױ��ƶ������ط�ʽ�����п�
     */
    public static final String YIBAOBINDCARD = "toBindBankCard";
    /**
     * �ױ��ƶ������ط�ʽ�����п�
     */
    public static final String YIBAOAUTOTRANSFER = "toAuthorizeAutoTransfer";
    /**
     * �ױ��ƶ������ط�ʽ����
     */
    public static final String YIBAOWITHDRAW = "toWithdraw";
    /**
     * �ױ�����ҳ��
     */
    // �Լ�tomcat�ϵĽӿ�
//    public static final String YIBAOCALLBACK = "http://192.168.5.112:8080/YibaoCallback.jsp";
    // ��ָͶ������ϵĽӿ�
    public static final String YIBAOCALLBACK = "yeePayCallBack/";
    /**
     * �ױ��ص�֪ͨ
     */
    public static final String YIBAONOTIFY = "http://www.jinzht.com/phone5/notify/";
}
