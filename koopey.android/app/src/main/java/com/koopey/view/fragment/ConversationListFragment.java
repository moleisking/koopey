package com.koopey.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.ConversationAdapter;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.type.MeasureType;
import com.koopey.model.type.MessageType;
import com.koopey.service.MessageService;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.view.MainActivity;

public class ConversationListFragment extends ListFragment implements  MessageService.OnMessageListener {

    AuthenticationUser authenticationUser;
    Messages conversations = new Messages();
    Messages messages = new Messages();
    MessageService messageService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageService = new MessageService(getContext());
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.conversation_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);

        if (this.messages.size() > 0) {
            // Save conversation users for MessageList
            getActivity().getIntent().putExtra("users", this.messages.get(position).getUsers());
            ((MainActivity) getActivity()).showMessageListFragment();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void updateMessages(int code, String message,Messages conversations) {
        Log.w("Conversations", "updateConversations");
       // conversations.print();
    }

    private boolean isDuplicateConversation(Message message) {
        boolean duplicate = false;
        for (int i = 0; i < this.conversations.size(); i++) {
            if (message.getUsers().equals(this.conversations.get(i).getUsers())) {
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
            ConversationAdapter conversationsAdapter = new ConversationAdapter(this.getActivity(), this.conversations, this.authenticationUser);
            this.setListAdapter(conversationsAdapter);
        } else {
            this.messages = new Messages();
            Log.w(ConversationListFragment.class.getName(),"No messages");
        }
    }

    public void syncConversations() {
        //NOTE: MainActivity refresh and this.onActivityCreated
        if (SerializeHelper.hasFile(this.getActivity(), Messages.MESSAGES_FILE_NAME)) {
            //Messages found so try read unsent messages
            this.messages = (Messages) SerializeHelper.loadObject(this.getActivity(), Messages.MESSAGES_FILE_NAME);
            this.populateConversations();
            messageService.searchByReceiver(MessageType.Sent);

        } else {
            //No messages found so try read all messages
            this.messages = new Messages();
            messageService.searchMessagesByReceiverOrSender(null);
        }
    }


}
