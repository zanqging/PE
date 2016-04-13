package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.materialedittext.MaterialEditText;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;

/**
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 输入昵称界面
 *
 * @auther Mr.Jobs
 * @date 2015/5/20
 * @time 17:30
 */

public class UpdateInformationActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.submit_edit)
    MaterialEditText submit_edit;
    UpdateTask task;

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @OnClick(R.id.update_ok)
    void ok() {
        switch (getIntent().getExtras().getInt("flag")) {
            case 1://修改真实姓名
                task = new UpdateTask();
                task.execute();
                break;
            case 2:
//                TelephoneTask task1 = new TelephoneTask();
//                task1.execute();
                break;
            case 3:
                task = new UpdateTask();
                task.execute();
                break;
            case 4:
                task = new UpdateTask();
                task.execute();
                break;
            case 5:
                task = new UpdateTask();
                task.execute();
                break;
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_update_information;
    }

    // 提价修改过的昵称
    private class UpdateTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    if (getIntent().getExtras().getInt("flag") == 5) {
                        body = AsynOkUtils.doPushPost("nickname", submit_edit.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.NICK_NAME, mContext);
                    } else if (getIntent().getExtras().getInt("flag") == 4) {
                        body = AsynOkUtils.doPushPost("position", submit_edit.getText().toString().trim(), Constant.BASE_URL + Constant.PHONE + Constant.POSITION, mContext);
                    } else if (getIntent().getExtras().getInt("flag") == 3) {
                        body = AsynOkUtils.doPushPost("company", submit_edit.getText().toString().trim(), Constant.BASE_URL + Constant.PHONE + Constant.COMPANY, mContext);
                    } else if (getIntent().getExtras().getInt("flag") == 2) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
//                SuperToastUtils.showSuperToast(UpdateInformationActivity.this, 1, R.string.no_wifi);
            } else {
                if (aVoid.getCode() == 0) {
                    if (getIntent().getExtras().getInt("flag") == 5) {
                        Intent intent = new Intent(UpdateInformationActivity.this, PersonActivity.class);
                        intent.putExtra("nick_name", submit_edit.getText().toString().trim());
                        setResult(103, intent);
                        finish();
                    } else if (getIntent().getExtras().getInt("flag") == 4) {
                        Intent intent = new Intent(UpdateInformationActivity.this, PersonActivity.class);
                        intent.putExtra("position", submit_edit.getText().toString().trim());
                        setResult(104, intent);
                        finish();
                    } else if (getIntent().getExtras().getInt("flag") == 3) {
                        Intent intent = new Intent(UpdateInformationActivity.this, PersonActivity.class);
                        intent.putExtra("company", submit_edit.getText().toString().trim());
                        setResult(105, intent);
                        finish();
                    }
                }
                UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
            }
        }
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        submit_edit.setText(getIntent().getStringExtra("hint"));
        switch (getIntent().getExtras().getInt("flag")) {
            case 1://修改真实姓名
                break;
            case 2:
//                TelephoneTask task1 = new TelephoneTask();
//                task1.execute();
                break;
            case 3:
                submit_edit.setHint(getResources().getString(R.string.update_two));
                break;
            case 4:
                submit_edit.setHint(getResources().getString(R.string.update_three));
                break;
            case 5:
                submit_edit.setHint(getResources().getString(R.string.update_one));
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
}