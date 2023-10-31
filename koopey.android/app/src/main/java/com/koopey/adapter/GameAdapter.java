package com.koopey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.koopey.R;
import com.koopey.model.Game;
import com.koopey.model.Games;

public class GameAdapter extends ArrayAdapter<Game> {

    public GameAdapter(Context context, Games games) {
        super(context, 0, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)    {
        try {
            Game game = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_row, parent, false);
            }

            TextView txtName = convertView.findViewById(R.id.txtNameItem);
            TextView txtScore = convertView.findViewById(R.id.txtScoreItem);
            TextView txtTime = convertView.findViewById(R.id.txtDurationItem);

            txtName.setText(game.getName());
            txtScore.setText(game.getScore());
                txtTime.setText(game.getDuration());

        }catch (Exception ex){
            Log.d(WalletAdapter.class.getName(),ex.getMessage());
        }
        return convertView;
    }
}
