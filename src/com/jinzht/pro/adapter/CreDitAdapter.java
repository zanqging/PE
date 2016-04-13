package com.jinzht.pro.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CreditBean;
import com.jinzht.pro.model.FinishFinacingBean;
import com.jinzht.pro.utils.FileUtil;
import com.jinzht.pro.utils.SuperToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class CreDitAdapter extends BaseAdapter {


    Context context;

    ViewHolder viewHolder;
    List<CreditBean.DataEntity> finish_list;

    public CreDitAdapter(Context context, List<CreditBean.DataEntity> finish_list) {
        this.context = context;
        this.finish_list = finish_list;
    }

    @Override
    public int getCount() {
        return finish_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view!=null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credit, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        Log.e("tag",finish_list.get(i).getTitle());
        String ss = finish_list.get(i).getTitle();
        ss = ss.replaceAll("<em>","<font color=\"#e94819\">");
        ss = ss.replaceAll("</em>","</font>");
        String pp = finish_list.get(i).getContent();
        pp = pp.replaceAll("<em>","");
        pp = pp.replaceAll("</em>","");
        viewHolder.textViewList.get(0).setText(Html.fromHtml(ss));/**֧������*/
        viewHolder.textViewList.get(1).setText(Html.fromHtml(pp));
        return view;
    }
    static class ViewHolder {
        @Bind({R.id.company_name,R.id.project_summary}) List<TextView> textViewList;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
