package com.jinzht.pro.application;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.jinzht.pro.R;
import com.jinzht.pro.fragment.BaseFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.okhttp.OkHttpClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/4/14
 * @time 23:00
 */
public class MyApplication extends Application {
    public List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;
    public BaseFragment baseFragment;

    public OkHttpClient okHttpClient = new OkHttpClient();

    public LocationClient mLocationClient;// 百度位置服务
    public MyLocationListener mMyLocationListener;

    //
//    public MyApplication() {
//        super();
//    }
//    private RefWatcher refWatcher;
    //单例模式中获取唯一的ExitApplication 实例
    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    //    public static RefWatcher getRefWatcher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
//        refWatcher = LeakCanary.install(this);
        //配置参数
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.user_loading)
                .showImageOnFail(R.drawable.user_loadingfail)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象
//        setConnectTimeout：设置连接主机超时（单位：毫秒）
//        setReadTimeout：设置从主机读取数据超时（单位：毫秒）
        okHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(5, TimeUnit.SECONDS);// 写入超时
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
//        instance = this;
    }


    /**
     * 实现实位回调监听
     * 百度位置服务
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
//            Log.i("BaiduLocationApiDem", sb.toString());
            // 天安门坐标

            // 构建 route搜索参数以及策略，起终点也可以用name构造
            Log.e("ss", location.getCity() + location.getDistrict() + location.getLatitude() + location.getLongitude() + location.getStreet());
//            RouteParaOption para = new RouteParaOption()
//                    .startPoint(pt_start)
//                    .endPoint(pt_end)
//                    .busStrategyType(RouteParaOption.EBusStrategyType.bus_recommend_way);
//            try {
//                BaiduMapRoutePlan.openBaiduMapTransitRoute(para, getApplicationContext());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            //结束调启功能时调用finish方法以释放相关资源
//            BaiduMapRoutePlan.finish(getApplicationContext());
        }


    }

    //添加Activity 到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity 并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }

//        System.exit(0);
    }
}
