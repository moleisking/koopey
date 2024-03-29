package com.koopey.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.ListFragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.MessageAdapter;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.type.MessageType;
import com.koopey.service.MessageService;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.view.MainActivity;

public class MessageListFragment extends ListFragment implements MessageService.OnMessageListener,  View.OnKeyListener{

    AuthenticationUser authenticationUser;
    Messages conversation = new Messages();
    Messages messages = new Messages();
    Message message ;
    TextView txtMessage;
    Users users;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Define views
        this.txtMessage = (TextView) getActivity().findViewById(R.id.txtMessage);

        //Set listeners
        this.txtMessage.setOnKeyListener(this);

        //Populate controls
        this.syncConversation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        if (getActivity().getIntent().hasExtra("users")) {
            this.users = (Users) getActivity().getIntent().getSerializableExtra("users");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.message_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
    }

    @Override
    public boolean onKey( View view, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            //Create message, Id and createTimeStamp already generated
            this.buildMessage();

            //Post message to backend
           // this.postMessage(this.message);

            //Reset local objects and txtMessage
            this.txtMessage.setText("");

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateMessages(int code, String message,Messages conversations) {
        Log.w("Conversations", "updateConversations");
     //   conversations.print();
        // this.messages.addAll(conversations);
        Log.w("Conversations:after", "updateConversations");
      //  this.messages.print();
    }

    private Message buildMessage() {
        //Reset message object
        //this.message ;
        this.message.setDescription(txtMessage.getText().toString());

        //Set flags
        this.message.setType(MessageType.Sent);


        //Set user sender and receivers
        for (int i = 0; i < this.users.size(); i++) {
            User user = this.users.get(i);
            if (this.authenticationUser.equals(user)) {
                user.setType( "sender");
                this.users.set(user);
            } else {
                user.setType( "receiver");
                this.users.set(user);
            }
        }
        this.message.setUsers(this.users);

        return message;
    }

    private void populateConversation() {
        if (this.messages != null && this.messages.size() > 0 ) {
            // Filter conversation
            this.conversation =  new Messages();
            for (int i = 0; i < this.messages.size(); i++) {
                Message message = this.messages.get(i);
                if (this.users.equals(message.getUsers())) {
                    this.conversation.add(message);
                }
            }

            // Reset adapter with new messages
            MessageAdapter conversationAdapter = new MessageAdapter(this.getActivity(), this.conversation, this.authenticationUser);
            this.setListAdapter(conversationAdapter);
        }
    }

    public void syncConversation() {
        if (SerializeHelper.hasFile(this.getActivity(), Messages.MESSAGES_FILE_NAME) && this.users != null && this.users.size() > 0 ) {

            //Messages found so try read unsent messages
            this.messages = (Messages) SerializeHelper.loadObject(this.getActivity(), Messages.MESSAGES_FILE_NAME);
            this.populateConversation();
           // this.getMessages();// TODO:  this.getMessagesUnsent();
        } else {
            //No messages found so try read all messages
            this.messages = new Messages();
           // this.getMessages();
        }
    }
}
