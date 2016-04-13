package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.ProjectShareBean;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.ShareUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/9/22.
 * <p>
 * 关于我们页面，一张二维码
 */
public class AboutUsActivity extends BaseActivity {

    private ProjectShareBean.DataEntity shareBean;// 分享实体类
    ShareUtils shareUtils = null;

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.verson_name)
    TextView verson_name;// 版本号

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    // 点击分享图标，访问网络，保存数据到ProjectShareBean，弹出分享对话框
    @OnClick(R.id.rl_share)
    void ss() {
        AppShareTask appShareTask = new AppShareTask();
        appShareTask.execute();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_about_us;
    }

    // 初始化，显示版本号
    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.about));
        try {
            verson_name.setText(getResources().getString(R.string.edit_num) + UiHelp.getVersionName(AboutUsActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shareUtils = new ShareUtils(AboutUsActivity.this);
    }

    // 访问网络，保存数据到ProjectShareBean，弹出分享对话框
    private class AppShareTask extends AsyncTask<Void, Void, ProjectShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ProjectShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(AboutUsActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SHAREAPP, AboutUsActivity.this);
                    if (FastJsonTools.getBean(body, ProjectShareBean.class) != null && FastJsonTools.getBean(body, ProjectShareBean.class).getData() != null) {
                        shareBean = FastJsonTools.getBean(body, ProjectShareBean.class).getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("share", body);
                return FastJsonTools.getBean(body, ProjectShareBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ProjectShareBean common) {
            super.onPostExecute(common);
            if (common != null) {
                if (common.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (common.getCode() == 0) {
                    DialogUtils.ShareDialog(AboutUsActivity.this, verson_name, shareUtils, common.getData().getTitle(),
                            common.getData().getContent(), common.getData().getImg(), common.getData().getUrl());
                } else {
                    SuperToastUtils.showSuperToast(AboutUsActivity.this, 1, common.getMsg());
                }
            } else {
                SuperToastUtils.showSuperToast(AboutUsActivity.this, 1, R.string.no_wifi);
            }

        }
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    // 刷新
    @Override
    public void successRefresh() {
        AppShareTask appShareTask = new AppShareTask();
        appShareTask.execute();
    }
}