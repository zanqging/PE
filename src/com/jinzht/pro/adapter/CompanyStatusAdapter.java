package com.jinzht.pro.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CompanyStatusBean;

import java.util.HashMap;
import java.util.List;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/7/28
 * @time 22:40
 */
public class CompanyStatusAdapter extends BaseAdapter {

    ViewHolder viewHolder;
    private Activity context;
    private int temp = -1;
    Activity activity;
    HashMap<String,Boolean> states=new HashMap<String,Boolean>();
    StatusChanged statusChangeds;
    private List<CompanyStatusBean> companyStatusBeanList;
    int selectedIndex = -1;

    public void setSelectedIndex(int index){
        selectedIndex = index;
    }
    public CompanyStatusAdapter(Activity context, List<CompanyStatusBean> companyStatusBeanList,StatusChanged statusChangeds) {
        this.activity = context;
        this.companyStatusBeanList = companyStatusBeanList;
        this.statusChangeds = statusChangeds;
    }

    @Override
    public int getCount() {
        return companyStatusBeanList.size();
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
    public View getView(int position, View view, ViewGroup parent) {
        if (view!=null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_status, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        viewHolder.checkBox.setText(companyStatusBeanList.get(position).getStatus_name());
        viewHolder.checkBox.setId(companyStatusBeanList.get(position).getCompanystatus_id());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        }
        });
        if(selectedIndex == position){
            viewHolder.checkBox.setChecked(true);
        }
        else{
            viewHolder.checkBox.setChecked(false);
        }
        return view;
    }

    public interface StatusChanged{
        public  void statusChanged(CompoundButton compoundButton, boolean b,int pos);
    }
    static class ViewHolder {

        @Bind(R.id.raido_btn)
        RadioButton checkBox;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
