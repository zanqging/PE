package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.*;

import butterknife.Bind;

import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.ConditionAdapter;
import com.jinzht.pro.adapter.WorkFieldAdapter;
import com.jinzht.pro.model.*;
import com.jinzht.pro.utils.*;
import com.jinzht.pro.view.MyListview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 投资者认证页面
 *
 * @auther Mr.Jobs
 * @date 2015/5/27
 * @time 8:46
 */

public class InvestorAuthenticationActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ConditionAdapter.ConditionChanged, WorkFieldAdapter.ChangeChecked, View.OnClickListener {

    private String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "invest.jpg";

    @Bind(R.id.back)
    LinearLayout back;
    private PopupWindow mPopupWindow;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private int company_id = 0;
    private int image_flag = 0;
    String work_field_type = "";
    private int fund_size_flag = 0;
    ConditionAdapter adapter;
    private String fileNames = "invest.jpg";  //报文中的文件名参数

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    String new_work_id = "";

    @Bind({R.id.line_first, R.id.line_second})
    List<ImageView> line_text;//导航栏变色的三角
    private Intent intent;

    @Bind(R.id.radioGroup_title)
    RadioGroup radioGroup;

    @Bind(R.id.scrollview)
    ScrollView scrollview;

    @Bind({R.id.ll_organization_invest, R.id.ll_person})
    List<LinearLayout> linearLayouts;
    private List<InvestorConditionBean> list = new ArrayList<>();
    private List<InvestorConditionBean> fund_size_list = new ArrayList<>();
    private List<String> fund_string_list = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private List<CompanyListBean> companyListBeanList = new ArrayList<>();
    List<WorkfieldBean> workfieldBeanList = new ArrayList<>();
    @Bind(R.id.conditon)
    MyListview conditon_list;
    private int width = 0;
    private HashMap<Integer, Boolean> map = new HashMap<>();
    private HashMap<Integer, Boolean> maps;
    private AlertDialog dialog;
    private GridView gridView;
    private Button dialog_ok;
    @Bind({R.id.your_name, R.id.position})
    List<EditText> editTextList;
    @Bind(R.id.tv_fund)
    TextView tv_fund;

    @OnClick(R.id.tv_fund)
    void fund() {
        showFundSize();
    }

    private boolean is_success = true;
    @Bind({R.id.et_organization})
    List<EditText> et_list_org;
    @Bind(R.id.invest_range)
    TextView invest_range;
    @Bind({R.id.iv_identity, R.id.iv_card})
    List<ImageView> imageViews;
    @Bind(R.id.et_company)
    EditText et_company;//个人的公司
    private int flag = 0;
    private String condition_id = "";
    private String video_paths = "";

    @OnClick(R.id.invest_toast)
    void toasht() {
        intent = new Intent(mContext, NoticeActivity.class);
        intent.putExtra("title", getResources().getString(R.string.invest_toast));
        intent.putExtra("flag", 2);
        startActivity(intent);
    }

    @OnClick(R.id.company_add)
    void add() {
    }

    @OnClick(R.id.invest_range)
    void in() {
        showWorkFieldDialog();
    }

    @OnClick({R.id.rl_identity, R.id.rl_card})
    void onv(RelativeLayout textView) {
        switch (textView.getId()) {
            case R.id.rl_identity:
                image_flag = 0;
                showImageDialog();
                break;
            case R.id.rl_card:
                image_flag = 1;
                showImageDialog();
                break;
        }
    }

    @Bind({R.id.tv_identity, R.id.tv_card})
    List<TextView> textViewList;
    @Bind(R.id.select_company)
    TextView select_company;

    @OnClick(R.id.select_company)
    void select() {
        showPopupWindow();
    }

    private void showPopupWindow() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_company_list, null);
        mPopupWindow = new PopupWindow(popupView, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        点击空白处的时候PopupWindow会消失
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAsDropDown(select_company);
        listView = (ListView) popupView.findViewById(R.id.company_list);
        arrayAdapter = new ArrayAdapter<String>(InvestorAuthenticationActivity.this, R.layout.item_company_list, R.id.tv_company, stringList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                select_company.setText(stringList.get(i));
                company_id = companyListBeanList.get(i).getId();
                mPopupWindow.dismiss();
            }
        });
    }

    private void showFundSize() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_company_list, null);
        mPopupWindow = new PopupWindow(popupView, width, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        点击空白处的时候PopupWindow会消失
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAsDropDown(tv_fund);
        listView = (ListView) popupView.findViewById(R.id.company_list);
        arrayAdapter = new ArrayAdapter<String>(InvestorAuthenticationActivity.this, R.layout.item_company_list, R.id.tv_company, fund_string_list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_fund.setText(fund_string_list.get(i));
                fund_size_flag = fund_size_list.get(i).getId();
                mPopupWindow.dismiss();
            }
        });
    }

    @OnClick(R.id.post_information)
    void post() {
        if (flag == 0) {//个人投资者
            condition_id = "";
            for (Integer key : map.keySet()) {
                if (map.get(key)) {
                    condition_id = condition_id + list.get(key).getId() + ",";
                }
            }
            if (StringUtils.isBlank(editTextList.get(0).getText().toString())) {
                UiHelp.ToastMessageShort(mContext, R.string.your_name_hint);
            } else if (StringUtils.isBlank(editTextList.get(1).getText().toString())) {
                UiHelp.ToastMessageShort(mContext, R.string.position_hint);
            } else if (StringUtils.isBlank(et_company.getText().toString())) {
                UiHelp.ToastMessageShort(mContext, R.string.company_name_hint);
            } else if (map == null || map.size() == 0) {
                UiHelp.ToastMessageShort(mContext, R.string.condication_hint);
            } else {
                SubmitTask task = new SubmitTask();
                task.execute();
            }
        } else {
            condition_id = "";
            for (Integer key : map.keySet()) {
                Log.e(TAG, "size" + map.size());
                if (map.get(key)) {
                    condition_id = condition_id + list.get(key).getId() + ",";
                    Log.e(TAG, "conditaion_id" + condition_id);
                }
            }
            if (StringUtils.isBlank(et_list_org.get(0).getText().toString())) {
                UiHelp.ToastMessageShort(mContext, R.string.your_name_hint);
            } else if (StringUtils.isBlank(invest_range.getText().toString())) {
                UiHelp.ToastMessageShort(mContext, R.string.invest_range_hint);
            } else if (StringUtils.isBlank(tv_fund.getText().toString())) {
                UiHelp.ToastMessageShort(mContext, R.string.fund_hint);
            } else if (map == null || map.size() == 0) {
                UiHelp.ToastMessageShort(mContext, R.string.condication_hint);
            } else {
                SubmitTask submitTask = new SubmitTask();
                submitTask.execute();
            }
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.acitivity_inverstor_authentication;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
//        FundSizeTask tasks = new FundSizeTask();
//        tasks.execute();
        ConditionTask task = new ConditionTask();
        task.execute();
//        CompanyListTask companyListTask = new CompanyListTask();
//        companyListTask.execute();
        title.setText(getResources().getString(R.string.authentication));
        line_text.get(0).setImageResource(R.drawable.small_strigle);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        CompanyListTask companyListTask = new CompanyListTask();
//        companyListTask.execute();
        ConditionTask conditionTask = new ConditionTask();
        conditionTask.execute();
//        for (Integer key : map.keySet()) {
//            if (map.get(key)){
//
//            }
//        }
    }

    private void showWorkFieldDialog() {
        maps = new HashMap<>();
        dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_working_field);
        gridView = (GridView) view.findViewById(R.id.gridView);
        dialog_ok = (Button) view.findViewById(R.id.dialog_ok);
        dialog_ok.setOnClickListener(this);
        WorkFieldTask workFieldTask = new WorkFieldTask();
        workFieldTask.execute();
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_image_title))
//                .setIcon(R.drawable.ic_launcher)
//                .setAdapter();//自定义列表项，如果有image,,,
                .setItems(R.array.image_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "invest.jpg"));
                            //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
                        } else {
                            Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            openAlbumIntent.setType("image/*");
                            startActivityForResult(openAlbumIntent, Constant.CHOOSE_PICTURE);
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.TAKE_PICTURE:
                    if (image_flag == 0) {
                        textViewList.get(0).setVisibility(View.GONE);
                        imageViews.get(0).setVisibility(View.VISIBLE);
                        //将保存在本地的图片取出并缩小后显示在界面上
                        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/invest.jpg");
                        Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / Constant.SCALE, bitmap.getHeight() / Constant.SCALE);
                        //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                        bitmap.recycle();
                        //将处理过的图片显示在界面上，并保存到本地
                        imageViews.get(0).setImageBitmap(ImageTools.getRoundedCornerBitmap(newBitmap, 6));
                        ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    UpLoadImageUtils.uploadFile(Constant.BASE_URL + Constant.PHONE + Constant.IDENTITY, InvestorAuthenticationActivity.this, FILE_PATH, fileNames);
                                } catch (Exception e) {
                                    handler.sendEmptyMessage(2);
                                }
                                handler.sendEmptyMessage(3);
                            }
                        }).start();
                    } else if (image_flag == 1) {
                        textViewList.get(1).setVisibility(View.GONE);
                        imageViews.get(1).setVisibility(View.VISIBLE);
                        //将保存在本地的图片取出并缩小后显示在界面上
                        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/invest.jpg");
                        Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / Constant.SCALE, bitmap.getHeight() / Constant.SCALE);
                        //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                        bitmap.recycle();
                        //将处理过的图片显示在界面上，并保存到本地
                        imageViews.get(1).setImageBitmap(ImageTools.getRoundedCornerBitmap(newBitmap, 6));
                        ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
                    }
                    break;

                case Constant.CHOOSE_PICTURE:
                    if (image_flag == 0) {
                        textViewList.get(0).setVisibility(View.GONE);
                        imageViews.get(0).setVisibility(View.VISIBLE);
                        ContentResolver resolver = getContentResolver();
                        //照片的原始资源地址
                        Uri originalUri = data.getData();
                        try {
                            //使用ContentProvider通过URI获取原始图片
                            Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                            if (photo != null) {
                                //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                                Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / Constant.SCALE, photo.getHeight() / Constant.SCALE);
                                //释放原始图片占用的内存，防止out of memory异常发生
                                photo.recycle();
                                imageViews.get(0).setImageBitmap(ImageTools.getRoundedCornerBitmap(smallBitmap, 6));
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (image_flag == 1) {
                        textViewList.get(1).setVisibility(View.GONE);
                        imageViews.get(1).setVisibility(View.VISIBLE);
                        ContentResolver resolver = getContentResolver();
                        //照片的原始资源地址
                        Uri originalUri = data.getData();
                        try {
                            //使用ContentProvider通过URI获取原始图片
                            Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                            if (photo != null) {
                                //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                                Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / Constant.SCALE, photo.getHeight() / Constant.SCALE);
                                //释放原始图片占用的内存，防止out of memory异常发生
                                photo.recycle();
                                imageViews.get(1).setImageBitmap(ImageTools.getRoundedCornerBitmap(smallBitmap, 6));
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    break;

                default:
                    break;
            }
        } else if (resultCode == Constant.RESULT_AUTHERTION) {
            if (requestCode == Constant.WANT_ADD_COMPANY) {
                if (data == null) {

                } else {
                    company_id = data.getIntExtra("company_id", 0);
                    select_company.setText(data.getStringExtra("company_name"));
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.person_invest://个人投资者
                flag = 0;
                scrollview.smoothScrollTo(0, 0);
                line_text.get(0).setImageResource(R.drawable.small_strigle);
                line_text.get(1).setImageResource(0);
                linearLayouts.get(0).setVisibility(View.GONE);
                linearLayouts.get(1).setVisibility(View.VISIBLE);
                break;
            case R.id.organization_invest://机构投资者
                flag = 1;
                scrollview.smoothScrollTo(0, 0);
                line_text.get(1).setImageResource(R.drawable.small_strigle);
                line_text.get(0).setImageResource(0);
                linearLayouts.get(0).setVisibility(View.VISIBLE);
                linearLayouts.get(1).setVisibility(View.GONE);
                ViewTreeObserver vto2 = select_company.getViewTreeObserver();
                vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        select_company.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        width = select_company.getWidth();
                        Log.e(TAG, width + "width");
                    }
                });
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    UiHelp.ToastMessageShort(mContext, R.string.no_wifi);
                    break;
                case 2:
                    UiHelp.ToastMessageShort(mContext, R.string.update_fail);
                case 3:
                    dismissProgressDialog();
                    UiHelp.ToastMessageShort(mContext, R.string.update_ok);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void conditionChanged(CompoundButton compoundButton, boolean b, int postion) {
        if (b) {
            map.put(postion, true);
        } else {
            map.put(postion, false);
        }
    }

    @Override
    public void changeChecked(CompoundButton compoundButton, boolean b, int pos) {
        if (b) {
            maps.put(pos, true);
        } else {
            maps.put(pos, false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_ok:
                new_work_id = "";
                work_field_type = "";
                for (Integer key : maps.keySet()) {
                    if (maps.get(key)) {
                        new_work_id = new_work_id + workfieldBeanList.get(key).getId() + ",";
                        work_field_type = work_field_type + workfieldBeanList.get(key).getType_name() + " ";
                    }
                }
                invest_range.setText(work_field_type);
                dialog.dismiss();
                maps.clear();
                break;
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

    }

    private class ConditionTask extends AsyncTask<Void, Void, List<InvestorConditionBean>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<InvestorConditionBean> doInBackground(Void... voids) {
            String body = null;
//            ACache aCache = ACache.get(mContext);
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                body = aCache.getAsString("InvestorConditionBean");
                if (body == null) {
                    handler.sendEmptyMessage(1);
                    dismissProgressDialog();
                    return null;
                }
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.INVESTORCONDITION, mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                try {
                    if (aCache.get("InvestorConditionBean") == null || aCache.get("InvestorConditionBean").equals("")) {
                        aCache.put("InvestorConditionBean", body, 1 * ACache.TIME_DAY);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (list != null || list.size() != 0) {
                    list.clear();
                }
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    if (jsonObject.optInt("status") == 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            InvestorConditionBean bean = new InvestorConditionBean(jsonObject1.optInt("id"), jsonObject1.optString("desc"));
                            list.add(bean);
                        }
                    } else
                        return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<InvestorConditionBean> investorConditionBeanList) {
            super.onPostExecute(investorConditionBeanList);
            if (investorConditionBeanList == null) {

            } else {
//                adapter = new ConditionAdapter(mContext,investorConditionBeanList,InvestorAuthenticationActivity.this);
//                conditon_list.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
                scrollview.smoothScrollTo(0, 0);
            }
        }


    }

    private class FundSizeTask extends AsyncTask<Void, Void, List<InvestorConditionBean>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<InvestorConditionBean> doInBackground(Void... voids) {
            String body = null;
//            ACache aCache = ACache.get(mContext);
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                body = aCache.getAsString("FundSizeTask");
                if (body == null) {
                    handler.sendEmptyMessage(1);
                    dismissProgressDialog();
                    return null;
                }
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.FUNDSIZE, mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                try {
                    if (aCache.get("FundSizeTask") == null || aCache.get("FundSizeTask").equals("")) {
                        aCache.put("FundSizeTask", body, 1 * ACache.TIME_DAY);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (fund_size_list.size() != 0) {
                    fund_size_list.clear();
                }
                if (fund_string_list.size() != 0) {
                    fund_string_list.clear();
                }
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    if (jsonObject.optInt("status") == 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            InvestorConditionBean bean = new InvestorConditionBean(jsonObject1.optInt("id"), jsonObject1.optString("desc"));
                            fund_size_list.add(bean);
                            fund_string_list.add(jsonObject1.optString("desc"));
                        }
                    } else
                        return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return fund_size_list;
        }

        @Override
        protected void onPostExecute(List<InvestorConditionBean> investorConditionBeanList) {
            super.onPostExecute(investorConditionBeanList);
            if (investorConditionBeanList == null) {

            } else {
            }
        }
    }

    private class SubmitTask extends AsyncTask<Void, Void, AuthenticationBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthenticationBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(InvestorAuthenticationActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //有网
                try {
                    if (flag == 0) {
                        body = OkHttpUtils.doSubmitPost(/*"investor_type", flag + "",*/ "name", editTextList.get(0).getText().toString(), "position", editTextList.get(1).getText().toString(), "company",
                                et_company.getText().toString(), "qualification", condition_id.substring(0, condition_id.length() - 1),
                                Constant.BASE_URL + Constant.PHONE + Constant.AUTHENTICATE, InvestorAuthenticationActivity.this);
                    } else {
                        Log.e(TAG, condition_id + "condition_id");
                        body = OkHttpUtils.doSubmitPost("investor_type", 1 + "", "real_name", et_list_org.get(0).getText().toString(), "company",
                                company_id + "", "fund_size_range", fund_size_flag + "", "investor_qualification", condition_id.substring(0, condition_id.length() - 1), "industry_type", new_work_id.substring(0, new_work_id.length() - 1),
                                Constant.BASE_URL + Constant.PHONE + Constant.AUTHENTICATE, InvestorAuthenticationActivity.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, AuthenticationBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthenticationBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                dismissProgressDialog();
                return;
            } else {
//                UiHelp.ToastMessageShort(InvestorAuthenticationActivity.this, aVoid.getMsg());
                if (aVoid.getCode() == 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                UpLoadImageUtils.uploadFile(Constant.BASE_URL + Constant.PHONE + Constant.CARD + aVoid.getData() + "/", InvestorAuthenticationActivity.this, FILE_PATH, fileNames);
                            } catch (Exception e) {
                                dismissProgressDialog();
                                is_success = false;
                                handler.sendEmptyMessage(2);
                            }
                        }
                    }).start();
                    dismissProgressDialog();
//                    DialogUtils.passDialog(InvestorAuthenticationActivity.this,aVoid.getMsg(),getResources().getString(R.string.progress_btn),Constant.AUTHERTIONFLAG);
                } else {
                    dismissProgressDialog();
//                    DialogUtils.failDialog(InvestorAuthenticationActivity.this, aVoid.getMsg(), getResources().getString(R.string.submit),Constant.AUTHERTIONFLAG);
                }
            }
        }
    }

    private class CompanyListTask extends AsyncTask<Void, Void, List<CompanyListBean>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<CompanyListBean> doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(InvestorAuthenticationActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.COMPANYLIST, InvestorAuthenticationActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    if (jsonObject.getInt("status") != 0) {
                        return null;
                    }
                    if (list.size() != 0) {
                        list.clear();
                    }
                    if (stringList.size() != 0) {
                        stringList.clear();
                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        CompanyListBean companyListBean = new CompanyListBean(jsonObject1.optInt("id"), jsonObject1.optString("company_name"));
                        stringList.add(jsonObject1.optString("company_name"));
                        companyListBeanList.add(companyListBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return companyListBeanList;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<CompanyListBean> aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                return;
            } else {

            }
        }
    }

    private class WorkFieldTask extends AsyncTask<Void, Void, List<WorkfieldBean>> {


        @Override
        protected List<WorkfieldBean> doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.WORKFIELD, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                if (workfieldBeanList.size() != 0) {
                    workfieldBeanList.clear();
                }
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    if (jsonObject.getInt("status") == 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            WorkfieldBean workfieldBean = new WorkfieldBean(jsonObject1.optInt("id"), jsonObject1.optString("type_name"));
                            workfieldBeanList.add(workfieldBean);
                        }
                    } else
                        return null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return workfieldBeanList;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<WorkfieldBean> workfieldBeans) {
            super.onPostExecute(workfieldBeans);
            if (workfieldBeans == null) {

            } else {
                WorkFieldAdapter workFieldAdapter = new WorkFieldAdapter(mContext, workfieldBeanList, InvestorAuthenticationActivity.this);
                gridView.setAdapter(workFieldAdapter);
            }
        }
    }

}