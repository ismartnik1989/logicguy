package com.test.colormemorygame.activity;

import android.app.Dialog;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.test.colormemorygame.R;
import com.test.colormemorygame.adpater.ScoreCardAdapter;
import com.test.colormemorygame.database.DbHelper;
import com.test.colormemorygame.database.InsertCommand;
import com.test.colormemorygame.model.CardModel;
import com.test.colormemorygame.model.ScoreCardModel;
import com.test.colormemorygame.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : mukeshk
 * @created : 02/Dec/2016
 * @description : this is main class which is load all data
 */
public class GameActivity extends BaseActivity {

    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    private int[][] cards;
    private List<Drawable> images;
    private CardModel firstCardModel;
    private CardModel secondCardModel;
    private ButtonListener buttonListener;
    private static Object lock = new Object();
    private int totalTurn=0;
    private TableLayout mainTable;
    private UpdateCardsHandler handler;
    private int defaultRow = 4, defaultCol = 4;
    private Button doneBtn, highScoreCardBtn;
    private TextView scoreNumber, triesNumber;
    private EditText guestName;
    private Dialog inputDialog, highScoreDialog;
    private ListView highScoreListView;
    private TableRow mainTableRow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        handler = new UpdateCardsHandler();
        setRef();

    }

    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : setRef
     * @description : this method get id from layout and load data on screen
     */
    private void setRef() {
        highScoreCardBtn = (Button) findViewById(R.id.highScoreBtn);
        triesNumber = (TextView) findViewById(R.id.tv1);
        mainTable = (TableLayout) findViewById(R.id.TableLayout03);
        mainTableRow = ((TableRow) findViewById(R.id.TableRow03));
        loadImages();
        buttonListener = new ButtonListener();
        proceedGame(defaultRow, defaultCol);
        setListener();
    }


    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : setListener
     * @description : this method set listener on button click
     */
    private void setListener() {

        highScoreCardBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showHighScoreDialog();
            }
        });
    }

    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : proceedGame
     * @description : this method set default grid set 4X4.
     */
    private void proceedGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;
        cards = new int[COL_COUNT][ROW_COUNT];


        mainTableRow.removeAllViews();

        mainTable = new TableLayout(mContext);
        mainTableRow.addView(mainTable);

        for (int y = 0; y < ROW_COUNT; y++) {
            mainTable.addView(createRow(y));
        }

        firstCardModel = null;
        loadCards();

           }

    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : loadImages
     * @description : After set grid this method load image which is use in game.
     */
    private void loadImages() {
        images = new ArrayList<Drawable>();

        images.add(AppUtil.getDrawable(mContext, R.drawable.colour1));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour2));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour3));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour4));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour5));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour6));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour7));
        images.add(AppUtil.getDrawable(mContext, R.drawable.colour8));

    }

    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : loadCards
     * @description : After set grid this method load cards which is use in game.
     */
    private void loadCards() {
        try {
            int size = ROW_COUNT * COL_COUNT;
            ArrayList<Integer> list = new ArrayList<Integer>();

            for (int i = 0; i < size; i++) {
                list.add(new Integer(i));
            }


            Random r = new Random();

            for (int i = size - 1; i >= 0; i--) {
                int t = 0;

                if (i > 0) {
                    t = r.nextInt(i);
                }

                t = list.remove(t).intValue();
                cards[i % COL_COUNT][i / COL_COUNT] = t % (size / 2);

            }
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : createRow
     * @description : After set grid this method create 4X4 grid map with table row and col
     */
    private TableRow createRow(int y) {
        TableRow row = new TableRow(mContext);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x, y));
        }
        return row;
    }

    /**
     * @return : void
     * @author : mukesh
     * @created : 02/Dec/2016
     * @method name : createImageButton
     * @description : After set grid this method create image button
     */
    private View createImageButton(int x, int y) {
        Button button = new Button(mContext);
        button.setBackground(AppUtil.getDrawable(mContext, R.drawable.card_bg));
        button.setId(100 * x + y);
        button.setOnClickListener(buttonListener);
        return button;
    }


    /**
     * @author : mukesh
     * @return : void
     * @created : 02/Dec/2016
     * @class name : ButtonListener
     * @description : this class handle click event on image grid and set timer
     */
    class ButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            synchronized (lock) {
                if (firstCardModel != null && secondCardModel != null) {
                    return;
                }
                int id = v.getId();
                int x = id / 100;
                int y = id % 100;
                turnCard((Button) v, x, y);
            }

        }

        private void turnCard(Button button, int x, int y) {
            button.setBackground(images.get(cards[x][y]));

            if (firstCardModel == null) {
                firstCardModel = new CardModel(button, x, y);
            } else {

                if (firstCardModel.x == x && firstCardModel.y == y) {
                    return; //the user pressed the same card
                }

                secondCardModel = new CardModel(button, x, y);


                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        try {
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        } catch (Exception e) {
                            Log.e("E1", e.getMessage());
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 1000);
            }


        }

    }

    class UpdateCardsHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }

        public void checkCards() {
            if (cards[secondCardModel.x][secondCardModel.y] == cards[firstCardModel.x][firstCardModel.y]) {
                firstCardModel.button.setVisibility(View.INVISIBLE);
                secondCardModel.button.setVisibility(View.INVISIBLE);
                triesNumber.setText(String.valueOf(addScore(triesNumber)));
                totalTurn=totalTurn+2;
                int totalSize= defaultRow*defaultCol;
                if(totalSize==totalTurn){
                    showInputDialog();
                }


            } else {
                secondCardModel.button.setBackground(AppUtil.getDrawable(mContext, R.drawable.card_bg));
                firstCardModel.button.setBackground(AppUtil.getDrawable(mContext, R.drawable.card_bg));
                triesNumber.setText(String.valueOf(subScore(triesNumber)));
            }

            firstCardModel = null;
            secondCardModel = null;
        }
    }


    /**
     * @return : void
     * @author : mukeshk2
     * @created :02/Dec/2016
     * @method : showHighScoreDialog
     * @description This method to show dialog pop up when user click on high score button
     */

    private void showHighScoreDialog() {
        highScoreDialog = new Dialog(mContext, R.style.DialogSlideAnim);
        highScoreDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        highScoreDialog.setContentView(R.layout.dialog_score_card);
        highScoreDialog.setCancelable(true);
        highScoreListView = (ListView) highScoreDialog.findViewById(R.id.listView);
        ImageView cancelImage=(ImageView)highScoreDialog.findViewById(R.id.cancel) ;
        cancelImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                highScoreDialog.dismiss();
            }
        });
        getData();


    }

    /**
     * @return : void
     * @author : mukeshk2
     * @created :02/Dec/2016
     * @method : showInputDialog
     * @description This method to show dialog pop up after successful game finish
     */

    private void showInputDialog() {
        inputDialog = new Dialog(mContext, R.style.DialogSlideAnim);
        inputDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        inputDialog.setContentView(R.layout.dialog_name);
        inputDialog.setCancelable(false);
        doneBtn = (Button) inputDialog.findViewById(R.id.done_text);
        scoreNumber = (TextView) inputDialog.findViewById(R.id.score_card);
        guestName = (EditText) inputDialog.findViewById(R.id.guest_name);
        scoreNumber.setText("Your Score: "+triesNumber.getText().toString().trim());

        setDialogListener();
        inputDialog.show();

    }


    /**
     * @return : void
     * @author : mukeshk2
     * @created :02/Dec/2016
     * @method : setDialogListener
     * @description This method set on all click listener event for input dialog
     */
    private void setDialogListener() {
        doneBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = guestName.getText().toString().trim();
                String score = triesNumber.getText().toString().trim();
                if (name.equals("")) {
                    Toast.makeText(mContext, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    setData(name, score);
                    inputDialog.dismiss();
                    showHighScoreDialog();
                }

            }
        });


    }


    /**
     * @return : void
     * @author : mukeshk2
     * @created :02/Dec/2016
     * @method : setData
     * @description This method insert all data into data base
     */
    private void setData(String guestName, String score) {
        try {

            InsertCommand insertCommand = new InsertCommand(mContext, "1", guestName, score);
            dbHelper.insertData(DbHelper.TABLE_NAME, insertCommand.toValues());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @return : void
     * @author : mukeshk2
     * @created :02/Dec/2016
     * @method : getData
     * @description This method get all data into data base
     */
    private void getData() {

        try {
            ArrayList<ScoreCardModel> arrayList = new ArrayList<>();
            Cursor c = dbHelper.getAllData();
            int rank=1;
            if (c != null && c.getCount() > 0 && c.moveToFirst()) {

                do {
                    ScoreCardModel model = new ScoreCardModel(String.valueOf(rank), c.getString(2), String.valueOf(c.getInt(3)));
                    arrayList.add(model);
                    rank=rank+1;
                } while (c.moveToNext());


            }

            if (arrayList != null && arrayList.size() > 0) {

                ScoreCardAdapter adapter = new ScoreCardAdapter(mContext, arrayList);
                highScoreListView.setAdapter(adapter);
                highScoreDialog.show();

            } else {
                Toast.makeText(mContext, "Oops!! There is no any high score :(", Toast.LENGTH_SHORT).show();
            }
            c.close();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }


    }


    private int addScore(TextView textView){
        if(textView!=null&&!textView.getText().toString().trim().equals("")){

            String scoreString=textView.getText().toString();
            int number= Integer.parseInt(scoreString);
            return number+2;

        }else{
             return 2;
        }


    }

    private int subScore(TextView textView){
        if(textView!=null&&!textView.getText().toString().trim().equals("")){

            String scoreString=textView.getText().toString();
            int number= Integer.parseInt(scoreString);
            return number-1;

        }else{
            return  -1;
        }


    }

}