package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CirclePersonBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/10/16.
 * <p>
 * Ȧ������ҳ�棬չʾ�û���Ϣ��ò��û���õ�
 */
public class CirclePersonActivity extends BaseActivity {

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_user)
    ImageView iv_user;// �û�ͼƬ
    @Bind({R.id.tv_name, R.id.tv_positon, R.id.tv_type, R.id.tv_adress})
    List<TextView> textViews;// ������ְλ�����͡���ַ
    private List<CirclePersonBean.DataEntity> list = new ArrayList<>();// �û���Ϣʵ�弯��
    CirclePersonBean.DataEntity bean;// �û���Ϣʵ��

    @Override
    protected int getResourcesId() {
        return R.layout.activity_circle_person_detail;
    }

    @Override
    protected void init() {
        GetInformationTask ta = new GetInformationTask();
        ta.execute();
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    @Override
    public void successRefresh() {

    }

    // �������磬��ȡ�û���Ϣ��չʾ
    private class GetInformationTask extends AsyncTask<Void, Void, Map<String, Object>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {

            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION + getIntent().getExtras().getInt("id") + "/", mContext);
                    if (FastJsonTools.getMap(body) != null || FastJsonTools.getMap(body).get("data") != null
                            || !FastJsonTools.getMap(body).get("data").toString().equals("") || !FastJsonTools.getMap(body).get("data").toString().equals("[]")) {
                        Log.e(TAG, FastJsonTools.getMap(body).get("data") + "");
                        bean = FastJsonTools.getBean(FastJsonTools.getMap(body).get("data").toString(), CirclePersonBean.DataEntity.class);
//                        lover_list = FastJsonTools.getBeanList(FastJsonTools.getMap(body).get("likers").toString(),CircleBean.DataEntity.LikersEntity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getMap(body);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(CirclePersonActivity.this, 1, R.string.no_wifi);
            } else {
                textViews.get(0).setText(bean.getReal_name());
                if (bean.getGender().toString().equals("null")) {
                    textViews.get(1).setText(getResources().getString(R.string.man));
                } else if (bean.getGender().toString().equals("true")) {
                    textViews.get(1).setText(getResources().getString(R.string.man));
                } else {
                    textViews.get(1).setText(getResources().getString(R.string.women));
                }
                textViews.get(2).setText(bean.getPosition_type() + "");
                textViews.get(3).setText(bean.getProvince() + bean.getCity());
                UpLoadImageUtils.getRoundImage(bean.getUser_img(), iv_user);
            }
        }
    }

}