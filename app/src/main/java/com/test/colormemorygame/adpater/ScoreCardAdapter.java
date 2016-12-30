package com.test.colormemorygame.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.colormemorygame.R;
import com.test.colormemorygame.model.ScoreCardModel;

import java.util.ArrayList;

/**
 * Created by Mukesh on 12/02/2016.
 */

public class ScoreCardAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<ScoreCardModel> mDataList;
    private LayoutInflater inflater=null;

    public ScoreCardAdapter(Context mContext, ArrayList<ScoreCardModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder{

        public TextView rankText;
        public TextView nameText;
        public TextView scoreText;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            holder = new ViewHolder();
            vi = inflater.inflate(R.layout.score_card_list_view_item, null);
            holder.rankText = (TextView) vi.findViewById(R.id.rank);
            holder.nameText=(TextView)vi.findViewById(R.id.name);
            holder.scoreText=(TextView)vi.findViewById(R.id.score);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        ScoreCardModel model=(ScoreCardModel)mDataList.get(position);
        if(model!=null){

            holder.rankText.setText(String.valueOf(model.getRank()));
            holder.nameText.setText(String.valueOf(model.getName()));
            holder.scoreText.setText(String.valueOf(model.getScore()));
        }



        return vi;
    }
}
