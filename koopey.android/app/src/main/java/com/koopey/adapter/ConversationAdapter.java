package com.koopey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.koopey.R;
import com.koopey.helper.ImageHelper;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;

/**
 * Created by Scott on 13/10/2016.
 */
public class ConversationAdapter extends ArrayAdapter<Message> {

    private AuthenticationUser authenticationUser;

    public ConversationAdapter(Context context, ArrayList<Message> messages, AuthenticationUser authenticationUser) {
        super(context, 0, messages);
        this.authenticationUser = authenticationUser;
    }

    public ConversationAdapter(Context context, Messages conversations, AuthenticationUser authenticationUser) {
        super(context, 0, conversations);
        this.authenticationUser = authenticationUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Message message = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conversation_row, parent, false);
        }

        // Lookup view for data population
        ImageView img = (ImageView) convertView.findViewById(R.id.imgAvatar);
        TextView txtId = (TextView) convertView.findViewById(R.id.txtId);
        TextView txtSummary = (TextView) convertView.findViewById(R.id.txtSummary);
        TextView txtAlias = (TextView) convertView.findViewById(R.id.txtAlias);

        // Populate the data into the template view using the data object
        txtSummary.setText(message.getSummary());

        // Select correct image and title
        Users users = message.getUsers();
        for (int i = 0 ; i < users.size(); i++){
            User user = users.get(i);
            if (!user.equals(authenticationUser)){
                img.setImageBitmap(ImageHelper.IconBitmap(user.getAvatar())  );
                // Set correct title
                if (message.getUsers().size() > 2){
                    txtAlias.setText(user.getAlias() + "++");
                } else {
                    txtAlias.setText(user.getAlias());
                }
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
