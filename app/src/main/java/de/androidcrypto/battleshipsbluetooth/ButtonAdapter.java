package de.androidcrypto.battleshipsbluetooth;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class ButtonAdapter extends BaseAdapter {

    private Context mContext;
    private int btn_id;
    private int total_btns = 100;

    public ButtonAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return total_btns;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button btn;
        if (view == null) {
            btn = new Button(mContext);
            btn.setTextSize(10);
            //btn.setMinimumWidth(0);
            //btn.setWidth(30);
//            btn.setText("B" + (++btn_id));

            // set text like A0 or C3 depending on btn_id
            String buttonText = "";
            //++btn_id;
            if (i < 10) buttonText = "A";
            else if (i < 20) buttonText = "B";
            else if (i < 30) buttonText = "C";
            else if (i < 40) buttonText = "D";
            else if (i < 50) buttonText = "E";
            else if (i < 60) buttonText = "F";
            else if (btn_id < 70) buttonText = "G";
            else if (btn_id < 80) buttonText = "H";
            else if (btn_id < 90) buttonText = "I";
            else if (btn_id < 100) buttonText = "J";

            buttonText += (btn_id % 10);
            //btn_id++;
            btn.setText(buttonText);
            ++btn_id;
        } else {
            btn = (Button) view;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Button #" + (i + 1), Toast.LENGTH_SHORT).show();

                if (i > 10) {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(mContext.getApplicationContext(), R.color.red_dark));
                } else {
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(mContext.getApplicationContext(), R.color.red_light));
                }
                btn.setOnClickListener(null); // button is no longer clickable
            }
        });

        return btn;
    }
}
