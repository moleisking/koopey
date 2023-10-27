package com.koopey.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class MessageAdapter extends ArrayAdapter<Message> {

    private AuthenticationUser authenticationUser;

    public MessageAdapter(Context context, ArrayList<Message> messages, AuthenticationUser authenticationUser) {
        super(context, 0, messages);
        this.authenticationUser = authenticationUser;
    }

    public MessageAdapter(Context context, Messages messages, AuthenticationUser authUser) {
        super(context, 0, messages);
        this.authenticationUser = authUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Message message = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_row, parent, false);
        }
        LinearLayout rowLinearLayout = (LinearLayout) convertView.findViewById(R.id.rowMessage);
        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageItem);
        ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.imgAvatarItem);

        User sender = message.getSender();
        Users receivers = message.getReceivers();

        //Set control data
        if (sender != null && sender.equals(authenticationUser) ) {
            //Set indentation, sender is my user
            rowLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            //Set text
            txtMessage.setBackgroundColor(getContext().getResources().getColor(R.color.color_background_light));
            txtMessage.setText(message.getDescription());
            //Set image
            if (ImageHelper.isImageUri(authenticationUser.getAvatar())){
                imgAvatar.setImageBitmap(ImageHelper.IconBitmap(authenticationUser.getAvatar()));
            }else {
                Bitmap defaultBitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.default_user)).getBitmap();
                imgAvatar.setImageBitmap(ImageHelper.IconBitmap(defaultBitmap));
            }
        } else  {
            //Set indentation, sender is not my user
            rowLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            txtMessage.setBackgroundColor(getContext().getResources().getColor(R.color.color_background_dark));
            txtMessage.setText(message.getDescription());
            //Set image
            if (sender != null  && ImageHelper.isImageUri(sender.getAvatar())) {
                imgAvatar.setImageBitmap(ImageHelper.IconBitmap(sender.getAvatar()));
            }else {
                Bitmap defaultBitmap = ((BitmapDrawable)getContext().getResources().getDrawable(R.drawable.default_user)).getBitmap();
                imgAvatar.setImageBitmap(ImageHelper.IconBitmap(defaultBitmap));
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }
}