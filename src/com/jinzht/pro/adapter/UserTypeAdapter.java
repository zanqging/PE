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
import com.jinzht.pro.activity.PersonActivity;
import com.jinzht.pro.callback.UserTypeCallback;
import com.jinzht.pro.model.UserTypeBean;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/5/25
 * @time 14:38
 */

public class UserTypeAdapter extends BaseAdapter {
    ViewHolder viewHolder;
    private Context context;
    UserTypeCallback userTypeCallback ;
    List<UserTypeBean> list;

    public UserTypeAdapter(Context context, UserTypeCallback userTypeCallback,List<UserTypeBean> list) {
        this.context = context;
        this.userTypeCallback = userTypeCallback;
        this.list = list;
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
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        String[] items = context.getResources().getStringArray(R.array.user_type);
        if (view!=null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_type, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
//        userTypeCallback.userType(i);

        viewHolder.checkBox.setText(list.get(i).getType_name());

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userTypeCallback.userType(compoundButton,i,b);

            }
        });
        return view;
    }


    static class ViewHolder {

        @Bind(R.id.user_checkbox) CheckBox checkBox;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
