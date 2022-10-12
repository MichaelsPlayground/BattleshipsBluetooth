package de.androidcrypto.battleshipsbluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class OtherBoardActivity extends AppCompatActivity {

    GridView gridview;

    int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_board);

        // populate the buttons
        gridview = (GridView) findViewById(R.id.activity_grid);
        selectedColor = R.color.red_light;
        selectedColor = R.color.blue_dark;
        addButtons();
    }

    void addButtons() {
        ArrayList<Button> arrayList = new ArrayList<Button>();

        for (int i = 0; i < 100; i++) {
            Button newButton = new Button(this);
            String buttonText = "";
            //++btn_id;
            if (i < 10) buttonText = "A";
            else if (i < 20) buttonText = "B";
            else if (i < 30) buttonText = "C";
            else if (i < 40) buttonText = "D";
            else if (i < 50) buttonText = "E";
            else if (i < 60) buttonText = "F";
            else if (i < 70) buttonText = "G";
            else if (i < 80) buttonText = "H";
            else if (i < 90) buttonText = "I";
            else if (i < 100) buttonText = "J";

            buttonText += (i % 10);
            newButton.setText(buttonText);
            newButton.setTextSize(10);
            newButton.setWidth(20);
            newButton.setId(i);
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("clicked: " + newButton.getId());
                    newButton.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), selectedColor));
                }
            });
            arrayList.add(newButton);

        }
        ButtonAdapter3 buttonAdapter3 = new ButtonAdapter3(this, arrayList);
        gridview.setAdapter(buttonAdapter3);

    }
}