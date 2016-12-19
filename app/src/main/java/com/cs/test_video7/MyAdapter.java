package com.cs.test_video7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chenshuai on 2016/12/19.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<String > mlist;
    private ViewHolder mViewHolder;

    public MyAdapter(Context context, List<String> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mViewHolder=new ViewHolder();
        if (view == null) {
            view= LayoutInflater.from(context).inflate(R.layout.item_url,viewGroup,false);
            mViewHolder.mTvUrl= (TextView) view.findViewById(R.id.tv_url);
            view.setTag(mViewHolder);
        }
            mViewHolder= (ViewHolder) view.getTag();
            mViewHolder.mTvUrl.setText(mlist.get(i));

        return view;
    }
    protected class ViewHolder{
        public TextView mTvUrl;
    }
}
