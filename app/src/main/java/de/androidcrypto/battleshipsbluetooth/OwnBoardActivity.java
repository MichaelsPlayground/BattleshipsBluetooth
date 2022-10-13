package de.androidcrypto.battleshipsbluetooth;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OwnBoardActivity extends AppCompatActivity {

    GridView gridview;

    int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_board);

        // populate the buttons
        gridview = (GridView) findViewById(R.id.activity_grid);
        selectedColor = R.color.red_light;
        selectedColor = R.color.blue_dark;
        addButtons();

        Button deployRandom = findViewById(R.id.btnOwnBoardDeployRandom);
        deployRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo this is not the final routine
                // ship 1 = 5 buttons to fill
                int startId = 0;
                for (int i = 0; i < 5; i++) {
                    Button btnShip = (Button) gridview.getAdapter().getItem(i);
                    btnShip.setBackgroundColor(getResources().getColor(R.color.red_light));
                    //btnShip.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.red_light));
                }
                // ship 2 = 4 buttons to fill
                for (int i = 10; i < 14; i++) {
                    Button btnShip = (Button) gridview.getAdapter().getItem(i);
                    btnShip.setBackgroundColor(getResources().getColor(R.color.blue_light));
                    //btnShip.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.blue_light));
                }
                // ship 3 = 3 buttons to fill
                for (int i = 20; i < 23; i++) {
                    Button btnShip = (Button) gridview.getAdapter().getItem(i);
                    btnShip.setBackgroundColor(getResources().getColor(R.color.green_light));
                    //btnShip.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.green_light));
                }
                // ship 4 = 3 buttons to fill
                for (int i = 30; i < 33; i++) {
                    Button btnShip = (Button) gridview.getAdapter().getItem(i);
                    btnShip.setBackgroundColor(getResources().getColor(R.color.yellow_light));
                    //btnShip.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.yellow_light));
                }
                // ship 5 = 2 buttons to fill
                for (int i = 40; i < 42; i++) {
                    Button btnShip = (Button) gridview.getAdapter().getItem(i);
                    btnShip.setBackgroundColor(getResources().getColor(R.color.purple_light));
                    //btnShip.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), R.color.purple_light));
                }
            }
        });

        Button checkButton = findViewById(R.id.btnOwnBoardCheckButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("checkButton");
                int buttonToCheck = 04;
                int checkedColor = checkForShipColor(buttonToCheck);
                System.out.println("checkForShipColor: " + checkedColor
                        + " shipType: " + getShipType(checkedColor));

                buttonToCheck = 11;
                checkedColor = checkForShipColor(buttonToCheck);
                System.out.println("checkForShipColor: " + checkedColor
                        + " shipType: " + getShipType(checkedColor));

                buttonToCheck = 21;
                checkedColor = checkForShipColor(buttonToCheck);
                System.out.println("checkForShipColor: " + checkedColor
                        + " shipType: " + getShipType(checkedColor));

                buttonToCheck = 31;
                checkedColor = checkForShipColor(buttonToCheck);
                System.out.println("checkForShipColor: " + checkedColor
                        + " shipType: " + getShipType(checkedColor));

                buttonToCheck = 41;
                checkedColor = checkForShipColor(buttonToCheck);
                System.out.println("checkForShipColor: " + checkedColor
                        + " shipType: " + getShipType(checkedColor));

                buttonToCheck = 16;
                checkedColor = checkForShipColor(buttonToCheck);
                System.out.println("checkForShipColor: " + checkedColor
                        + " shipType: " + getShipType(checkedColor));
            }
        });

        Button placeBomb = findViewById(R.id.btnOwnBoardBomb);
        placeBomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputBomb();
            }
        });

        Button saveState = findViewById(R.id.btnOwnBoardSaveState);
        saveState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] shipNumber = new int[100];
                for (int i = 0; i < 100; i++) {
                    shipNumber[i] = checkForShipColor(i);
                }
                OwnBoardModel ownBoardModel = new OwnBoardModel(shipNumber);
                FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                try {
                    fos = openFileOutput("game_123456.txt", MODE_PRIVATE);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(ownBoardModel);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("** state saved to internal storage");
            }
        });

        Button loadState = findViewById(R.id.btnOwnBoardLoadState);
        loadState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileInputStream fis = null;
                ObjectInputStream ois = null;
                Object obj = null;
                try {
                    fis = openFileInput("game_123456.txt");
                    ois = new ObjectInputStream(fis);
                    obj = ois.readObject();
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                OwnBoardModel ownBoardModel;
                ownBoardModel = (OwnBoardModel) obj;
                int[] shipNumber = ownBoardModel.getColor();
                int shipNumberSize = shipNumber.length;
                System.out.println("shipNumberSize: " + shipNumberSize);
                for (int i = 0; i < shipNumberSize; i++) {
                    int shipColor = shipNumber[i];
                    if (shipColor != 0) {
                        Button btnShip = (Button) gridview.getAdapter().getItem(i);
                        btnShip.setBackgroundColor(shipColor);
                    }
                }
                System.out.println("own board state loaded");

            }
        });

    }

    private String getShipType(int checkedColor) {
        switch (checkedColor) {
            case -4274689:
                return "blue";
            case -3277895:
                return "green";
            case -1074534:
                return "red";
            case -321:
                return "yellow";
            case -1261569:
                return "purple";
            case 0:
                return "no";
        }
        return "no";
    }

    private int checkForShipColor(int buttonNumber) {
        Button btnCheck = (Button) gridview.getAdapter().getItem(buttonNumber);
        try {
            ColorDrawable colorDrawable = (ColorDrawable) btnCheck.getBackground();
            return colorDrawable.getColor();
        } catch (ClassCastException e) {
            return 0;
        }
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
                    //newButton.setBackgroundTintList(ContextCompat.getColorStateList(view.getContext(), selectedColor));
                    newButton.setBackgroundColor(getResources().getColor(R.color.red_light));
                }
            });
            arrayList.add(newButton);

        }
        ButtonAdapter3 buttonAdapter3 = new ButtonAdapter3(this, arrayList);
        gridview.setAdapter(buttonAdapter3);
    }

    private void inputBomb() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Place a bomb");
        alert.setMessage("Input a number (00-99) to place your bomb");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                System.out.println("input: " + value);
                // Do something with value!
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}