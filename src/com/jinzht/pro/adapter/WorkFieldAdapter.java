package com.jinzht.pro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import butterknife.ButterKnife;
import butterknife.Bind;

import com.jinzht.pro.R;
import com.jinzht.pro.model.WorkfieldBean;

import java.util.List;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/7/28
 * @time 21:18
 */
public class WorkFieldAdapter extends BaseAdapter {

    ViewHolder viewHolder;
    private Context context;
    private List<WorkfieldBean> workfieldBeanList;
    private ChangeChecked changeCheckeds;

    public WorkFieldAdapter(Context context, List<WorkfieldBean> workfieldBeanList,ChangeChecked changeCheckeds) {
        this.context = context;
        this.workfieldBeanList = workfieldBeanList;
        this.changeCheckeds = changeCheckeds;
    }

    @Override
    public int getCount() {
        return workfieldBeanList.size();
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
        if (view!=null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_working_field, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.checkBox.setText(workfieldBeanList.get(pos).getType_name());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeCheckeds.changeChecked(compoundButton,b,pos);
            }
        });
        return view;
    }
    public interface ChangeChecked{
        public  void changeChecked(CompoundButton compoundButton, boolean b,int pos);
    }
    static class ViewHolder {
         @Bind(R.id.user_checkbox) CheckBox checkBox;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
