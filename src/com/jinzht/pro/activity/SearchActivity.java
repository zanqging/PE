package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.SearchAdapter;
import com.jinzht.pro.model.KeyWordBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.UiHelp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * 投融资页面右上角点击后进入到的搜索页面
 *
 * @auther Mr Jobs
 * @date 2015/5/18
 * @time 21:46
 */
public class SearchActivity extends BaseActivity {
    private Intent intent;
    private KeyWordBean bean =null;
    private List<String> list = new ArrayList<>();
    @OnClick(R.id.search_back) void back(){
        this.finish();
    }
    @Bind(R.id.search_back)
    RelativeLayout search_back;
     @Bind(R.id.jazzyGridView)
    GridView jazzyGridView;
     @Bind(R.id.search_edit)
    EditText search_edit;
     @OnItemClick(R.id.jazzyGridView)void pos(int pos){
        search_edit.setText(list.get(pos));
        intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
        intent.putExtra("word",list.get(pos));
        startActivity(intent);
    }
     @OnClick(R.id.search_btn) void search(){
         if (getIntent().getExtras()!=null){
                 intent = new Intent(SearchActivity.this,SearchDetailActivity.class);
                 intent.putExtra("word",search_edit.getText().toString());
                 startActivity(intent);
         }
    }
    @Bind(R.id.search_btn) TextView search_btn;
    @Override
    protected int getResourcesId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(search_back);
        RippleUtils.rippleNormal(search_btn);
        if (getIntent().getExtras()!=null){
            if (getIntent().getExtras().getInt("flag")==Constant.SEARCH_INVEST){
                KeyWordTask keyWordTask = new KeyWordTask();
                keyWordTask.execute();
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

    }

    private class KeyWordTask extends AsyncTask<Void,Void,KeyWordBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected KeyWordBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.KEYWORD, mContext);
                    if (list.size()!=0){
                        list.clear();
                    }
                    if (FastJsonTools.getBean(body,KeyWordBean.class)!=null){
                        list.addAll(FastJsonTools.getBean(body,KeyWordBean.class).getData().getKeyword());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);

                return FastJsonTools.getBean(body,KeyWordBean.class);
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(KeyWordBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid==null){
                dismissProgressDialog();
                UiHelp.ToastMessageShort(mContext, R.string.no_wifi);
                return;
            }else {
                SearchAdapter adapter = new SearchAdapter(mContext,list);
                jazzyGridView.setAdapter(adapter);
                dismissProgressDialog();
            }
        }
    }
}