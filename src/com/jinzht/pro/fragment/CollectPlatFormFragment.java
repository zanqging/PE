package com.jinzht.pro.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.Bind;

import com.jinzht.pro.R;
import com.jinzht.pro.adapter.CollectNormalAdapter;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 21:25
 */
public class CollectPlatFormFragment extends BaseFragment {


    @Bind(R.id.list)
    ListView list;
     @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_collect_normal;
    }


    @Override
    protected void onFirstUserVisible() {
//        CollectNormalAdapter adapter = new CollectNormalAdapter(getActivity().getApplicationContext());
//        list.setAdapter(adapter);
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onFirstUserInvisble() {

    }

    @Override
    protected void onUserVisble() {

    }

    @Override
    protected void init() {

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