package com.koopey.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.koopey.R;
import com.koopey.controller.GetJSON;
import com.koopey.controller.MessageReceiver;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Alert;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.AuthUser;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.Search;
import com.koopey.model.authentication.Token;
import com.koopey.service.impl.IAssetService;
import com.koopey.service.impl.IMessageService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageService extends IntentService implements GetJSON.GetResponseListener {

    public interface MessageListener {
        void onGetMessage(Message message);

        void onGetMessageCountByReceiver(Integer sum);

        void onGetMessageCountByReceiverOrSender(Integer sum);

        void onGetMessageCountBySender(Integer sum);

        void onGetMessageSearchReceiverOrSender(Messages messages);

        void onPostMessageCreate(String messageId);

        void onPostMessageDelete();

        void onPostMessageUpdate();

        void onPostMessageSearch(Messages messages);
    }

    AuthenticationService authenticationService;
    private Context context;

    private List<MessageService.MessageListener> messageListeners = new ArrayList<>();

    private static final int MESSAGE_NOTIFICATION = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private AuthUser authUser = new AuthUser();
    private Messages messages;
    // public ResponseMSG messageDelegate = null;

    public MessageService() {
        super(MessageService.class.getSimpleName());
    }


    public static Intent createIntentStartNotificationService(Context context) {
        Log.d(MessageService.class.getName(), "start");
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Log.d(MessageService.class.getName(), "delete");
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    public Messages getLocalMessagesFromFile() {
        Messages messages = new Messages();
        if (SerializeHelper.hasFile(context, Messages.MESSAGES_FILE_NAME)) {
            messages = (Messages) SerializeHelper.loadObject(context, Messages.MESSAGES_FILE_NAME);
        }
        return messages;
    }

    public boolean hasMessagesFile() {
        Messages messages = getLocalMessagesFromFile();
        return messages.size() <= 0 ? false : true;
    }

    public void getMessage(String messageId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Message> callAsync = service.getMessage(messageId);
        callAsync.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message message = response.body();
                if (message == null || message.isEmpty()) {
                    Log.i(MessageService.class.getName(), "message is null");
                } else {
                    for (MessageService.MessageListener listener : messageListeners) {
                        listener.onGetMessage(message);
                    }
                    SerializeHelper.saveObject(context, message);
                    Log.i(MessageService.class.getName(), message.toString());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessage(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getCountByReceiver() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Integer> callAsync = service.getCountByReceiver();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer sum = response.body();
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessageCountByReceiver(sum);
                }
                Log.i(MessageService.class.getName(), "Sum " + sum);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessageCountByReceiver(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getCountByReceiverOrSender() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Integer> callAsync = service.getCountByReceiver();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer sum = response.body();
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessageCountByReceiverOrSender(sum);
                }
                Log.i(MessageService.class.getName(), "Sum " + sum);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessageCountByReceiverOrSender(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getCountBySender() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Integer> callAsync = service.getCountByReceiver();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer sum = response.body();
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessageCountBySender(sum);
                }
                Log.i(MessageService.class.getName(), "Sum " + sum);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessageCountBySender(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getMessageSearchReceiverOrSender() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Messages> callAsync = service.getMessageSearchReceiverOrSender();
        callAsync.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Messages messages = response.body();
                if (messages == null || messages.isEmpty()) {
                    Log.i(MessageService.class.getName(), "message is null");
                } else {
                    for (MessageService.MessageListener listener : messageListeners) {
                        listener.onGetMessageSearchReceiverOrSender(messages);
                    }
                    SerializeHelper.saveObject(context, messages);
                    Log.i(MessageService.class.getName(), String.valueOf(messages.size()));
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onGetMessage(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postCreate(Message message) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);
        service.postMessageCreate(message).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                message.id = response.body();
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageCreate(message.id);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageCreate(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postDelete(Message message) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);
        service.postMessageCreate(message).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageDelete();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageDelete();
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postMessageSearch(Search search) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);
        service.postMessageSearch(search).enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
              Messages  messages = response.body();
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageSearch(messages);
                }
            }
            @Override
            public void onFailure(Call<Messages> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageSearch(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postUpdate(Message message) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url), token.token);
        service.postMessageCreate(message).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageUpdate();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (MessageService.MessageListener listener : messageListeners) {
                    listener.onPostMessageUpdate();
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(MessageService.class.getName(), "handle");
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                // processStartNotification();
                if (authUser != null) {
                    getMessages();
                }
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
        Log.d(MessageService.class.getName(), "process delete");
    }

    private void processStartNotification() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?
        Log.d(MessageService.class.getName(), "process start");
        //getMessages();
        //build notification
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Scheduled Notification")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.color_background_light))
                .setContentText("This notification has been triggered by Notification Service")
                .setSmallIcon(R.drawable.ic_error_outline_black_24dp);
        //set delete action
        builder.setDeleteIntent(MessageReceiver.getDeleteIntent(this));
        //add to notification
        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(MESSAGE_NOTIFICATION, builder.build());
    }

    private void getMessages() {
        if (SerializeHelper.hasFile(this, AuthUser.AUTH_USER_FILE_NAME)) {
            this.authUser = (AuthUser) SerializeHelper.loadObject(getApplicationContext(), AuthUser.AUTH_USER_FILE_NAME);
            if (authUser != null && !authUser.isEmpty()) {
                Log.d(MessageService.class.getName(), "getMessages");
                String url = getResources().getString(R.string.get_message_read_many_undelivered);
                GetJSON asyncTask = new GetJSON(this.getApplication());
                // GetJSON asyncTask =new GetJSON(context);
                asyncTask.delegate = this;
                asyncTask.execute(url, "", this.authUser.getToken());
            }
        }
    }

    @Override
    public void onGetResponse(String output) {
        try {
            //Get user account
            this.authUser = new AuthUser();
            this.authUser = (AuthUser) SerializeHelper.loadObject(getApplicationContext(), AuthUser.AUTH_USER_FILE_NAME);
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            //Get JSON and add to object
            if (header.contains("messages")) {
                Log.d(MessageService.class.getName(), "Read undelivered");
                messages = new Messages();
                messages.parseJSON(output);
                messages.print();
                if (output.length() > 20) {
                    //New messages found
                    Log.d(MessageService.class.getName(), "New messages found");
                    processStartNotification();
                } else {
                    //No messages found
                    Log.d(MessageService.class.getName(), "No messages found");
                }
                //Pass new conversations to message fragment where they can be added to the list and file
                //messageDelegate.updateMessages(messages);
            } else if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isError()) {
                    Log.d(MessageService.class.getName(), getResources().getString(R.string.error_update));
                } else if (alert.isSuccess()) {
                    Log.d(MessageService.class.getName(), getResources().getString(R.string.info_update));
                }
            }
        } catch (Exception ex) {
            Log.w(MessageService.class.getName(), ex.getMessage());
        }
    }

    public interface OnMessageListener {
        void updateMessages(Messages messages);
    }
}
