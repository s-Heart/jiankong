package com.regongzaixian.jiankong.main.uihelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.regongzaixian.jiankong.R;
import com.regongzaixian.jiankong.main.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2017/4/13.
 */

public class BlackBodyAdapter extends BaseAdapter {
    private final Context context;
    private List<Object> data = new ArrayList<>();

    public BlackBodyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_black_body, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvSign = (TextView) convertView.findViewById(R.id.tv_sign);
            viewHolder.tvNum = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        viewHolder.tvName.setText(data.get(position).toString());
//        viewHolder.tvSign.setText("设备信号：" + data.get(position).toString());
//        viewHolder.tvNum.setText("设备数量：" + data.get(position).toString());

        return convertView;
    }

    private class ViewHolder {
        public TextView tvName;
        public TextView tvSign;
        public TextView tvNum;
    }
}
