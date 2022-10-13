package de.androidcrypto.battleshipsbluetooth;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ButtonAdapter3Material extends BaseAdapter {

    Context mContext;
    ArrayList<MaterialButton> mArrayList;

    public ButtonAdapter3Material(Context context, ArrayList<MaterialButton> arrayList) {
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
        MaterialButton btn = mArrayList.get(i);
        return btn;
    }
}
