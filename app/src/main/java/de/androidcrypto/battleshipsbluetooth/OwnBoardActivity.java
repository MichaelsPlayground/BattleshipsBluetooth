package de.androidcrypto.battleshipsbluetooth;

import android.content.DialogInterface;
import android.content.Intent;
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
    private static String fileNameOwn = "game_123456_own.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_board);

        // populate the buttons
        gridview = (GridView) findViewById(R.id.activity_grid);
        //selectedColor = R.color.red_light;
        selectedColor = R.color.blue_dark;
        addButtons();

        try {
            loadBoardFromInternal(fileNameOwn);
        } catch (RuntimeException e) {
            // new game, save the empty board
            saveBoardToInternal(fileNameOwn);
        }

        // if we receive data from the other device
        Intent intent = getIntent();
        int bomb = intent.getIntExtra("ButtonNumber", 100);
        String gameNumber = intent.getStringExtra("GameNumber");
        if (bomb < 100) {
            System.out.println("Own Board, bomb on: " + bomb);
            // first load the stored board
            loadBoardFromInternal(fileNameOwn);
            // check for color from bomb
            int colorBoard = checkForShipColor(bomb);
            if (colorBoard == 0) {
                System.out.println("Own Board, no ship was found on this place");
                // no ship found on that place
                Button btnShip = (Button) gridview.getAdapter().getItem(bomb);
                btnShip.setBackgroundColor( getResources().getColor(R.color.grey_dark));
                Intent intentResult = new Intent(OwnBoardActivity.this, OtherBoardActivity.class);
                intentResult.putExtra("GameNumber", "123456");
                intentResult.putExtra("ButtonNumber", bomb);
                intentResult.putExtra("ButtonNumberResult", false);
                intentResult.putExtra("ShipSinkResult", false);
                startActivity(intentResult);
                saveBoardToInternal(fileNameOwn);
                finish();
            } else {
                System.out.println("Own Board, ship was found on this place: " + bomb);
                Button btnShip = (Button) gridview.getAdapter().getItem(bomb);
                int oldColor = checkForShipColor(bomb); // we need this color to check for remaining colors
                System.out.println("Own Board, oldColor: " + oldColor);
                // now we are setting the background color depending on the oldColor
                int newColor = getBombedColor(oldColor);
                System.out.println("Own Board, newColor: " + newColor);
                btnShip.setBackgroundColor(newColor);
                int oldColorRemaining = countColors(oldColor);
                System.out.println("Own Board, oldColorRemaining: " + oldColorRemaining);
                Intent intentResult = new Intent(OwnBoardActivity.this, OtherBoardActivity.class);
                intentResult.putExtra("GameNumber", "123456");
                intentResult.putExtra("ButtonNumber", bomb);
                intentResult.putExtra("ButtonNumberResult", true);
                if (oldColorRemaining == 0) {
                    // ship is sunk
                    intentResult.putExtra("ShipSinkResult", true);
                } else {
                    // ship is not sunk
                    intentResult.putExtra("ShipSinkResult", false);
                }
                startActivity(intentResult);
                saveBoardToInternal(fileNameOwn);
                finish();
            }
        }



        Button deployRandom = findViewById(R.id.btnOwnBoardDeployRandom);
        deployRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo this is not the final routine
                addButtons();
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
                deactivateButtonOnClickListener();
                saveBoardToInternal(fileNameOwn);
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
                String fileName = "game_123456_own.txt";
                saveBoardToInternal(fileName);
            }
        });

        Button loadState = findViewById(R.id.btnOwnBoardLoadState);
        loadState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "game_123456_own.txt";
                loadBoardFromInternal(fileName);
            }
        });

    }

    private int countColors(int countColor) {
        int count = 0;
        for (int i = 0; i < 100; i++) {
            int foundColor = checkForShipColor(i);
            if (countColor == foundColor) {
                count++;
            }
        }
        return count;
    }

    private void saveBoardToInternal(String filename) {
        int[] shipNumber = new int[100];
        for (int i = 0; i < 100; i++) {
            shipNumber[i] = checkForShipColor(i);
        }
        OwnBoardModel ownBoardModel = new OwnBoardModel(shipNumber);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ownBoardModel);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("** state saved to internal storage");
    }

    private void loadBoardFromInternal(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            fis = openFileInput(fileName);
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

    private void deactivateButtonOnClickListener() {
        for (int i = 0; i < 100; i++) {
            Button btnShip = (Button) gridview.getAdapter().getItem(i);
            btnShip.setOnClickListener(null);
        }
    }

    private int getBombedColor(int oldColor) {
        int newColor = 0;
        switch (oldColor) {
            case -4274689:
                newColor = R.color.blue_dark;
                break;
            case -3277895:
                newColor = R.color.green_dark;
                break;
            case -1074534:
                newColor = R.color.red_dark;
                break;
            case -321:
                newColor = R.color.yellow_dark;
                break;
            case -1261569:
                newColor = R.color.purple_dark;
                break;
            case 0:
                newColor = R.color.grey_dark;
                break;
        }
        return newColor;
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
                    newButton.setBackgroundColor(getResources().getColor(R.color.blue_dark));
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