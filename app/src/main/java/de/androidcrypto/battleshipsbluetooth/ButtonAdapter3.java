package de.androidcrypto.battleshipsbluetooth;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class ButtonAdapter3 extends BaseAdapter {

    Context mContext;
    ArrayList<Button> mArrayList;

    public ButtonAdapter3 (Context context, ArrayList<Button> arrayList) {
        this.mContext = context;
        this.mArrayList = arrayList;
    }


    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mArrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button btn = mArrayList.get(i);
        return btn;
    }
}
