package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.cropimageview.Crop;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.InformationBean;
import com.jinzht.pro.model.UserBean;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.CountryUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.wheelview.AbstractWheelTextAdapter;
import com.jinzht.pro.wheelview.OnWheelChangedListener;
import com.jinzht.pro.wheelview.OnWheelScrollListener;
import com.jinzht.pro.wheelview.WheelView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * ��������ҳ��
 *
 * @auther Mr.Jobs
 * @date 2015/5/20
 * @time 16:27
 */

public class PersonActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener, OnWheelScrollListener {

    private AlertDialog dialog;// ѡ����������ĶԻ���
    private String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + "cameras.jpg";// ��Ƭ���λ��
    private Intent intent;
    private Button popup_cancel, popup_submit;//ѡ������Ի���ȷ�ϡ�ȡ����ť
    public final static int IMAGE_CAMERA = 110;// ��ת��ϵͳ���յ�������
    private List<String> country_list = new ArrayList<>();
    private List<List<String>> city_list = new ArrayList<>();
    private String fileNames = "cameras.jpg";  //�����е��ļ�������
    WheelView wheel_provice, wheel_city;// ���ֿؼ�
    public static String fileName = "countrys.json";// ʡ������
    private Bitmap bitmap;
    private UserBean userBean;
    private boolean province_scrolling = false;
    private boolean city_scrolling = false;
    private String adress = "";

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.result_iamge)
    PolygonImageView resultView;// �û���Բ��ͷ��
    @Bind(R.id.area)
    TextView area;// ��ʾ�ĵ���

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @Bind({R.id.man, R.id.women})
    List<RadioButton> radioButtons;// �Ա�ť

    @Override
    protected int getResourcesId() {
        return R.layout.activity_person;
    }

    @Bind({R.id.true_name, R.id.phone_num, R.id.company_name, R.id.position, R.id.tv_nickname})
    List<TextView> right_text;// �������绰����˾��ְλ���ǳ�

    @OnClick({R.id.ll_image, R.id.ll_name, R.id.ll_phone_num, R.id.ll_company, R.id.ll_position, R.id.ll_area, R.id.ll_update_passwd, R.id.ll_nickname})
    void person(LinearLayout linearLayout) {
        switch (linearLayout.getId()) {
            case R.id.ll_name://��ʵ����
//                intent = new Intent(mContext,UpdateInformationActivity.class);
//                intent.putExtra("flag",1);
//                intent.putExtra("hint",right_text.get(0).getText().toString());
//                startActivityForResult(intent, 110);
                break;
            case R.id.ll_phone_num://�ֻ���
                break;
            case R.id.ll_company://��˾
//                intent = new Intent(mContext,UpdateInformationActivity.class);
//                intent.putExtra("hint",right_text.get(2).getText().toString());
//                intent.putExtra("flag",3);
//                startActivityForResult(intent, 110);
                break;
            case R.id.ll_position://ְλ
//                intent = new Intent(mContext,UpdateInformationActivity.class);
//                intent.putExtra("hint",right_text.get(3).getText().toString());
//                intent.putExtra("flag",4);
//                startActivityForResult(intent, 110);
                break;
            case R.id.ll_image://ѡ��ͷ��
                showImageDialog();
                break;
            case R.id.ll_area://ѡ�����
                showAreaDialog();
                break;
            case R.id.ll_update_passwd://�޸����룬�����޸��������ModifyActivity
                intent = new Intent(mContext, ModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_nickname:// �޸��ǳƣ������޸��ǳƽ���UpdateInformationActivity
                intent = new Intent(mContext, UpdateInformationActivity.class);
                intent.putExtra("hint", right_text.get(4).getText().toString());
                intent.putExtra("flag", 5);
                startActivityForResult(intent, 110);
                break;
        }
    }

    // ͷ���ѡ��Ի���
    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_image_title))
                .setItems(R.array.image_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File file = new File(FILE_PATH);
                            if (file.exists()) {
                                file.delete();
                            }
                            // ���ļ���ַת����Uri��ʽ
                            Uri uri = Uri.fromFile(file);
                            // ����ϵͳ���������Ƭ��ɺ�ͼƬ�ļ��Ĵ�ŵ�ַ
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, IMAGE_CAMERA);
                        } else {
                            resultView.setImageResource(R.drawable.user_loading);
                            // ��������
                            Crop.pickImage(PersonActivity.this);
                        }
                        String[] items = PersonActivity.this.getResources().getStringArray(R.array.image_dialog);
                    }
                });
        builder.create().show();
    }

    // ͷ��ѡ����޸��ǳƷ��ؽ��
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        } else if (requestCode == IMAGE_CAMERA && resultCode == RESULT_OK) {
            Log.i("����", "ϵͳ���������ɣ�resultCode=" + resultCode);
            File file = new File(FILE_PATH);
            Uri uri = Uri.fromFile(file);
            beginCameraCrop(uri);
        } else if (requestCode == 110 && resultCode == 102) {
            right_text.get(0).setText(result.getStringExtra("real_name"));
        } else if (requestCode == 110 && resultCode == 103) {
            right_text.get(4).setText(result.getStringExtra("nick_name"));
        } else if (requestCode == 110 && resultCode == 104) {
            right_text.get(3).setText(result.getStringExtra("position"));
        } else if (requestCode == 110 && resultCode == 105) {
            right_text.get(2).setText(result.getStringExtra("company"));
        }
    }

    // ����ѡ��Ƭ
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cameras.jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    // ����ѡ��Ƭ
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            resultView.setImageURI(Crop.getOutput(result));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(PersonActivity.this.getContentResolver(), Crop.getOutput(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
            postImageTask tass = new postImageTask();
            tass.execute();
        } else if (resultCode == Crop.RESULT_ERROR) {
            SuperToastUtils.showSuperToast(PersonActivity.this, 1, Crop.getError(result).getMessage());
        }
    }

    // ����ѡ��Ƭ
    private void beginCameraCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cameras.jpg"));
        Crop.of(source, outputUri).asSquare().start(this);
    }

    // ѡ������Ĺ���ѡ��Ի���
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
        wheel_provice.setCyclic(true);// ��ѭ��
        wheel_city.setCyclic(true);
        wheel_provice.addChangingListener(this);
        wheel_city.addChangingListener(this);
        wheel_provice.addScrollingListener(this);
        wheel_city.addScrollingListener(this);
        wheel_provice.setViewAdapter(new ProvinceAdapter(PersonActivity.this));
        wheel_city.setViewAdapter(new CityAdapter(PersonActivity.this));
    }

    // ����ϴ�����
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                break;
            case R.id.popup_submit:
                adress = country_list.get(wheel_provice.getCurrentItem()) + " " + city_list.get(wheel_provice.getCurrentItem()).get(wheel_city.getCurrentItem());
                UpdateAreaTask updateAreaTask = new UpdateAreaTask();
                updateAreaTask.execute();
                dialog.dismiss();
                break;
            case R.id.popup_cancel:
                dialog.dismiss();
                break;
        }
    }

    // ʡ���������
    private class ProvinceAdapter extends AbstractWheelTextAdapter {
        protected ProvinceAdapter(Context context) {
            super(context, R.layout.layout_province, NO_RESOURCE);
            setItemTextResource(R.id.province_name);
        }

        @Override
        protected CharSequence getItemText(int index) {
//            Log.i("tts",index+"province");
            return country_list.get(index);
        }

        @Override
        public int getItemsCount() {
            return country_list.size();
        }
    }

    // �����������
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

    // ����ѡ����
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()) {
            case R.id.wheel_provice://ʡ
                if (province_scrolling) {
                    updateCitys(wheel_city, wheel_provice.getCurrentItem());
                }
                break;
        }
    }

    // ����ѡ����
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

    // ����ѡ����
    @Override
    public void onScrollingFinished(WheelView wheel) {
        switch (wheel.getId()) {
            case R.id.wheel_provice://ʡ
                province_scrolling = false;
                updateCitys(wheel_city, wheel_provice.getCurrentItem());
                break;
        }
    }

    // ����ѡ����
    private void updateCitys(WheelView wheelView, int index) {
        switch (wheelView.getId()) {
            case R.id.wheel_city:
                CityAdapter cityAdapter = new CityAdapter(PersonActivity.this);
                cityAdapter.setTextSize(18);
                wheel_city.setViewAdapter(cityAdapter);
                wheel_city.setCyclic(true);
                wheel_city.setCurrentItem(index % city_list.get(wheel_provice.getCurrentItem()).size());
                break;
        }
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        try {
            country_list = (List<String>) CountryUtils.countryThread(PersonActivity.this, fileName).get("country_list");
            city_list = (List<List<String>>) CountryUtils.countryThread(PersonActivity.this, fileName).get("city_list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultView.setImageResource(R.drawable.user_loading);// �û�ͷ��
        // ��ȡ�û����ݲ���ʾ
        GetInformationTask task = new GetInformationTask();
        task.execute();
    }

    // ��ȡ�û���Ϣ���������
    private class GetInformationTask extends AsyncTask<Void, Void, InformationBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showLoadingProgressDialog();
        }

        @Override
        protected InformationBean doInBackground(Void... voids) {

            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("�û���Ϣ", body);
                return FastJsonTools.getBean(body, InformationBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InformationBean aVoid) {
            super.onPostExecute(aVoid);
//            dismissProgressDialog();
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(PersonActivity.this, 1, R.string.no_wifi);
            } else {
                if (aVoid.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                }
                if (aVoid.getCode() == 0) {
                    right_text.get(0).setText(aVoid.getData().getName());
                    right_text.get(4).setText(aVoid.getData().getNickname());
                    right_text.get(2).setText(aVoid.getData().getCompany());
                    right_text.get(3).setText(aVoid.getData().getPosition());
                    right_text.get(1).setText(aVoid.getData().getTel());
                    area.setText(aVoid.getData().getAddr());
                    radioButtons.get(0).setEnabled(false);
                    radioButtons.get(1).setEnabled(false);
                    if (aVoid.getData().getGender() != null) {
                        radioButtons.get(0).setChecked(true);
                        radioButtons.get(1).setChecked(false);
                    } else {
                        radioButtons.get(0).setChecked(false);
                        radioButtons.get(1).setChecked(true);
                    }
                    if (!aVoid.getData().getPhoto().equals("")) {
                        UpLoadImageUtils.getImage(aVoid.getData().getPhoto(), resultView);
                    }
                }
            }
        }
    }

    // �ϴ�����ͷ��
    private class postImageTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            try {
                body = OkHttpUtils.doOnePhotoPost(getCacheDir() + "/" + fileNames, Constant.BASE_URL + Constant.PHONE + Constant.POST_IMAGE, mContext);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("�ϴ�����ͷ��", body);
            return FastJsonTools.getBean(body, CommonBean.class);
        }

        @Override
        protected void onPostExecute(CommonBean commonBean) {
            super.onPostExecute(commonBean);
//            dismissProgressDialog();
            if (commonBean != null) {
                if (commonBean.getCode() == -1) {
                    return;
                }
                if (commonBean.getCode() == 0) {
                    MainActivity.isFirst = true;
                }
                UiHelp.printMsg(commonBean.getCode(), commonBean.getMsg(), mContext);
            }
        }
    }

    // �ϴ�����
    private class UpdateAreaTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("addr", adress, Constant.BASE_URL + Constant.PHONE + Constant.AREA, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("����", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
//            dismissProgressDialog();
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(PersonActivity.this, 1, R.string.no_wifi);
            } else {
                if (aVoid.getCode() == 0) {
                    area.setText(country_list.get(wheel_provice.getCurrentItem()) + " " + city_list.get(wheel_provice.getCurrentItem()).get(wheel_city.getCurrentItem()));
                }
                UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
            }
        }
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    @Override
    public void successRefresh() {
        GetInformationTask task = new GetInformationTask();
        task.execute();
    }
}