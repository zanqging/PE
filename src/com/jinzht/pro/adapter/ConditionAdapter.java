package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jinzht.pro.R;
import com.jinzht.pro.model.PersonAuthBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 世上唯有贫穷可以不劳而获。
 * 认证条件数据适配器
 *
 * @auther Mr.Jobs
 * @date 2015/8/1
 * @time 17:35
 */

public class ConditionAdapter extends BaseAdapter {
    public ViewHolder viewHolder;
    private Context context;
    List<PersonAuthBean.DataEntity.QualificationEntity> list;
    public ConditionChanged mConditionChanged;

    public ConditionAdapter(Context context, List<PersonAuthBean.DataEntity.QualificationEntity> list, ConditionChanged mConditionChanged) {
        this.context = context;
        this.list = list;
        this.mConditionChanged = mConditionChanged;
    }

    @Override
    public int getCount() {
        return list.size();
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
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_condition, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        if (list.size() == 0) {
            return null;
        }
        viewHolder.checkBox.setText(list.get(i).getValue());
        if (i == 0) {
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.if_one, 0, 0, 0);
        } else if (i == 1) {
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.if_two, 0, 0, 0);
        } else if (i == 2) {
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.if_three, 0, 0, 0);
        } else if (i == 3) {
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.if_four, 0, 0, 0);
        } else if (i == 4) {
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.if_five, 0, 0, 0);
        } else {
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.if_six, 0, 0, 0);
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mConditionChanged.conditionChanged(compoundButton, b, i);

            }
        });
        return view;
    }

    public interface ConditionChanged {
        public void conditionChanged(CompoundButton compoundButton, boolean b, int postion);
    }

    static class ViewHolder {
        @Bind(R.id.check_condition)
        CheckBox checkBox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
