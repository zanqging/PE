package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.CountryUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.wheelview.AbstractWheelTextAdapter;
import com.jinzht.pro.wheelview.OnWheelChangedListener;
import com.jinzht.pro.wheelview.OnWheelScrollListener;
import com.jinzht.pro.wheelview.WheelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/5.
 * <p>
 * 完善资料页面
 */
public class ImproveActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener, OnWheelScrollListener {

    public static String fileName = "countrys.json";// 省市数据文件
    private AlertDialog dialog;// 选择地区的对话框
    private List<String> country_list = new ArrayList<>();// 省列表
    private List<List<String>> city_list = new ArrayList<>();// 市列表
    private boolean province_scrolling = false;
    private boolean city_scrolling = false;
    private String adress = "";
    WheelView wheel_provice, wheel_city;// 滚动选择控件，省、市
    private Button popup_cancel, popup_submit;// 取消、确定按钮
    Intent intent;

    @Bind(R.id.title)
    TextView title;
    @Bind({R.id.ed_name, R.id.ed_shenfen, R.id.ed_company, R.id.ed_position})
    List<EditText> editTexts;// 姓名、身份证号、公司名、职位
    @Bind(R.id.ed_addr)
    TextView ed_addr;// 住址

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.ll_addr)
    void lll() {
        showAreaDialog();
    }

    @OnClick(R.id.btn_ok)
    void ok() {
        if (editTexts.get(0).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(ImproveActivity.this, 1, getResources().getString(R.string.true_name_hint));
        } else if (editTexts.get(1).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(ImproveActivity.this, 1, getResources().getString(R.string.shenfen_hint));
        } else if (ed_addr.getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(ImproveActivity.this, 1, getResources().getString(R.string.now_addr_hint));
        } else if (editTexts.get(2).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(ImproveActivity.this, 1, getResources().getString(R.string.tv_company_hint));
        } else if (editTexts.get(3).getText().toString().equals("")) {
            if (!UiHelp.isFastClick())
                SuperToastUtils.showSuperToast(ImproveActivity.this, 1, getResources().getString(R.string.tv_position_hint));
        } else {
            MainTask mainTask = new MainTask();
            mainTask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_improve_information;
    }

    @Override
    protected void init() {
        try {
            country_list = (List<String>) CountryUtils.countryThread(ImproveActivity.this, fileName).get("country_list");
            city_list = (List<List<String>>) CountryUtils.countryThread(ImproveActivity.this, fileName).get("city_list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        title.setText(getResources().getString(R.string.improve_informaion));
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

    // 弹出选择地区的滚动对话框
    private void showAreaDialog() {
        dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_city);
        dialog.setCanceledOnTouchOutside(true);
        popup_cancel = (Button) view.findViewById(R.id.popup_cancel);
        popup_submit = (Button) view.findViewById(R.id.popup_submit);
        popup_cancel.setOnClickListener(this);
        popup_submit.setOnClickListener(this);
        wheel_provice = (WheelView) view.findViewById(R.id.wheel_provice);
        wheel_city = (WheelView) view.findViewById(R.id.wheel_city);
        wheel_provice.setVisibleItems(2);
        wheel_city.setVisibleItems(2);
        wheel_provice.setCyclic(true);
        wheel_city.setCyclic(true);
        wheel_provice.addChangingListener(this);
        wheel_city.addChangingListener(this);
        wheel_provice.addScrollingListener(this);
        wheel_city.addScrollingListener(this);
        wheel_provice.setViewAdapter(new ProvinceAdapter(mContext));
        wheel_city.setViewAdapter(new CityAdapter(mContext));
    }

    // 省数据填充
    private class ProvinceAdapter extends AbstractWheelTextAdapter {
        protected ProvinceAdapter(Context context) {
            super(context, R.layout.layout_province, NO_RESOURCE);
            setItemTextResource(R.id.province_name);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return country_list.get(index);
        }

        @Override
        public int getItemsCount() {
            return country_list.size();
        }
    }

    // 市数据填充
    private class CityAdapter extends AbstractWheelTextAdapter {
        protected CityAdapter(Context context) {
            super(context, R.layout.layout_province, NO_RESOURCE);
            setItemTextResource(R.id.province_name);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return city_list.get(wheel_provice.getCurrentItem()).get(index % city_list.get(wheel_provice.getCurrentItem()).size());
        }

        @Override
        public int getItemsCount() {
            return city_list.get(wheel_provice.getCurrentItem()).size();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popup_submit:
                adress = country_list.get(wheel_provice.getCurrentItem()) + " " + city_list.get(wheel_provice.getCurrentItem()).get(wheel_city.getCurrentItem());
                ed_addr.setText(adress);
                dialog.dismiss();
                break;
            case R.id.popup_cancel:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()) {
            case R.id.wheel_provice:
                if (province_scrolling) {
                    updateCitys(wheel_city, wheel_provice.getCurrentItem());
                }
                break;
        }
    }

    // 更新城市列表
    private void updateCitys(WheelView wheelView, int index) {
        switch (wheelView.getId()) {
            case R.id.wheel_city:
                CityAdapter cityAdapter = new CityAdapter(mContext);
                cityAdapter.setTextSize(18);
                wheel_city.setViewAdapter(cityAdapter);
                wheel_city.setCyclic(true);
                wheel_city.setCurrentItem(index % city_list.get(wheel_provice.getCurrentItem()).size());
                break;
        }
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {
        switch (wheel.getId()) {
            case R.id.wheel_provice://ʡ
                province_scrolling = true;
                break;
            case R.id.wheel_city://��
                city_scrolling = true;
                break;
        }
    }

    // 省滚动框停止时，更新市列表
    @Override
    public void onScrollingFinished(WheelView wheel) {
        switch (wheel.getId()) {
            case R.id.wheel_provice://ʡ
                province_scrolling = false;
                updateCitys(wheel_city, wheel_provice.getCurrentItem());
                break;
        }
    }

    // 提交资料后返回主页
    private class MainTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            try {
                body = AsynOkUtils.doImprovePost("name", editTexts.get(0).getText().toString(),
                        "idno", editTexts.get(1).getText().toString(), "company", editTexts.get(2).getText().toString(),
                        "position", editTexts.get(3).getText().toString(), "addr", ed_addr.getText().toString(),
                        Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION, mContext);
                Log.i("提交资料返回信息", body);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
                e.printStackTrace();
            }
            return FastJsonTools.getBean(body, CommonBean.class);
        }

        @Override
        protected void onPostExecute(CommonBean stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            dismissProgressDialog();
            if (stringObjectMap != null) {
                if (stringObjectMap.getCode() == 0) {
                    SharePreferencesUtils.setPerfectInformation(mContext, true);
                    Intent intent = new Intent(mContext, MainActivity.class);
                    setResult(30, intent);
                    MainActivity.vpFragment.setCurrentItem(0);
                    finish();
                } else
                    UiHelp.printMsg(stringObjectMap.getCode(), stringObjectMap.getMsg(), mContext);
            }
        }
    }
}