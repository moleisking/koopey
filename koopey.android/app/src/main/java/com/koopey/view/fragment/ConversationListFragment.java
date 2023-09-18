package com.koopey.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.ConversationAdapter;
import com.koopey.controller.GetJSON;
import com.koopey.service.MessageService;
import com.koopey.model.Alert;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.Users;
import com.koopey.view.PrivateActivity;
import com.koopey.view.component.PrivateListFragment;

public class ConversationListFragment extends PrivateListFragment implements  MessageService.OnMessageListener {

    private Messages conversations = new Messages();
    private Messages messages = new Messages();

    MessageService messageService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageService = new MessageService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversations, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);

        if (this.messages.size() > 0) {
            // Save conversation users for MessageList
            getActivity().getIntent().putExtra("users", this.messages.get(position).users);
            ((PrivateActivity) getActivity()).showMessageListFragment();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void updateMessages(Messages conversations) {
        Log.w("Conversations", "updateConversations");
       // conversations.print();
    }

    private boolean isDuplicateConversation(Message message) {
        boolean duplicate = false;
        for (int i = 0; i < this.conversations.size(); i++) {
            if (Users.equals(message.users, this.conversations.get(i).users)) {
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

    private void populateConversations() {
        if (this.messages != null && this.messages.size() > 0 ) {

            // Filter conversations
            this.conversations =  new Messages();
            for (int i = 0; i < this.messages.size(); i++) {
                Message message = messages.get(i);
                if (!isDuplicateConversation(message)) {
                    this.conversations.add(message);
                } else {
                    Log.w(ConversationListFragment.class.getName(),"No match");
                }
            }

            // Reset adapter with new conversations
            ConversationAdapter conversationsAdapter = new ConversationAdapter(this.getActivity(), this.conversations, this.authUser);
            this.setListAdapter(conversationsAdapter);
        } else {
            this.messages = new Messages();
            Log.w(ConversationListFragment.class.getName(),"No messages");
        }
    }

    public void syncConversations() {
        //NOTE: PrivateActivity refresh and this.onActivityCreated
        if (SerializeHelper.hasFile(this.getActivity(), Messages.MESSAGES_FILE_NAME)) {
            //Messages found so try read unsent messages
            this.messages = (Messages) SerializeHelper.loadObject(this.getActivity(), Messages.MESSAGES_FILE_NAME);
            this.populateConversations();
            messageService.searchMessagesByReceiverOrSender();

        } else {
            //No messages found so try read all messages
            this.messages = new Messages();
            messageService.searchMessagesByReceiverOrSender();
        }
    }


}
