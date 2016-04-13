package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.model.InvestListBean;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/6/9
 * @time 14:03
 */

public class InvestListAdapter extends BaseAdapter {
    ViewHolder viewHolder;
    List<InvestListBean.DataEntity> investListBeanList;
    Context context;
    private boolean auth;

    public InvestListAdapter(List<InvestListBean.DataEntity> investListBeanList, Context context, boolean auth) {
        this.investListBeanList = investListBeanList;
        this.context = context;
        this.auth = auth;
    }

    @Override
    public int getCount() {
        return investListBeanList.size();
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
    public View getView(int pos, View view, ViewGroup parent) {
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invest_list, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        if (auth) {
            viewHolder.textViewList.get(0).setText(investListBeanList.get(pos).getName());
        } else {
            if (investListBeanList.get(pos).getName().length() != 0) {
                if (investListBeanList.get(pos).getName().length() <= 2) {
                    viewHolder.textViewList.get(0).setText(investListBeanList.get(pos).getName().substring(0, 1) + "*");
                } else {
                    String ss = "";
                    for (int i = 0; i < investListBeanList.get(i).getName().length() - 2; i++) {
                        ss = "*" + ss;
                    }
                    viewHolder.textViewList.get(0).setText(investListBeanList.get(pos).getName().substring(0, 1) + ss + investListBeanList.get(pos).getName().substring(investListBeanList.get(pos).getName().length() - 1, investListBeanList.get(pos).getName().length()));
                }
            }
        }
        viewHolder.textViewList.get(1).setText(investListBeanList.get(pos).getAmount() + context.getResources().getString(R.string.wan));
        viewHolder.textViewList.get(2).setText(investListBeanList.get(pos).getDate());
        UpLoadImageUtils.getUserImage(investListBeanList.get(pos).getPhoto(), viewHolder.imageView);
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.invest_image)
        PolygonImageView imageView;
        @Bind({R.id.invest_name, R.id.invest_num, R.id.invest_time})
        List<TextView> textViewList;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
