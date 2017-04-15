package com.regongzaixian.jiankong.main.uihelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.regongzaixian.jiankong.R;
import com.regongzaixian.jiankong.model.InstrumentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2017/4/13.
 */

public class BlackBodyAdapter extends BaseAdapter {
    private final Context context;
    private List<InstrumentEntity> data = new ArrayList<>();

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
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_black_body, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvModel = (TextView) convertView.findViewById(R.id.tv_model);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.tvModel.setText("设备型号：" + data.get(position).getModel());
        viewHolder.tvId.setText("设备编号：" + data.get(position).getId());

        return convertView;
    }

    public void setData(List<InstrumentEntity> instrumentEntities) {
        this.data.clear();
        this.data.addAll(instrumentEntities);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView tvName;
        public TextView tvModel;
        public TextView tvId;
    }
}
