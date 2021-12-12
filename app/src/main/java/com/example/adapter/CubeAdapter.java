package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.CrosspuzzleApplication;
import com.example.R;
import com.example.game.drawable.IDrawable4ColorMapper;

import java.util.List;

public class CubeAdapter extends BaseAdapter {

    private final List<Integer> cubeColors;
    private final LayoutInflater inflater;

    public CubeAdapter(Context context, List<Integer> cubeColors) {
        inflater = LayoutInflater.from(context);
        this.cubeColors = cubeColors;
    }

    @Override
    public int getCount() {
        return cubeColors.size();
    }

    @Override
    public Object getItem(int position) {
        return cubeColors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cube, null);
            holder = new ViewHolder();
            holder.view = convertView.findViewById(R.id.cube_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int color = cubeColors.get(position);
        IDrawable4ColorMapper mapper = CrosspuzzleApplication.getMapper();
        holder.view.setBackground(mapper.map(color)  );
        return convertView;
    }



    static class ViewHolder {
        View view;
    }
}
