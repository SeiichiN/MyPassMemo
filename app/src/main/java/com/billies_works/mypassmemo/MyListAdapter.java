package com.billies_works.mypassmemo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by se-ichi on 17/09/03.
 */

public class MyListAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<ListItem> data = null;
    private int resource = 0;

    // コンストラクタ
    public MyListAdapter(Context context, ArrayList<ListItem> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    // データ項目の個数を取得
    @Override
    public int getCount() {
        return data.size();
    }

    // 指定された項目を取得
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    // 指定された項目を識別するためのid値を取得
    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    // リスト項目を表示するためのViewを取得
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) context;
        ListItem item = (ListItem) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }
        ((TextView) convertView.findViewById(R.id.textView01Id))
                .setText(String.valueOf(item.getId()));
        ((TextView) convertView.findViewById(R.id.textView01Library))
                .setText(item.getLibrary());
        return convertView;
    }
}
