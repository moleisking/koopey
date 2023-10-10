package com.koopey.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.koopey.R;
import com.koopey.controller.MessageReceiver;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.Search;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.IMessageService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageService extends IntentService {

    public interface MessageCountListener {

        void onMessageCountByReceiver(Integer sum);

        void onMessageCountByReceiverOrSender(Integer sum);

        void onMessageCountBySender(Integer sum);

    }

    public interface MessageCrudListener {
        void onMessageRead(int code, String note, Message message);

        void onMessageCreate(int code, String message, String messageId);

        void onMessageDelete(int code, String message);

        void onMessageUpdate(int code, String message);

    }

    public interface MessageSearchListener {

        void onMessageSearchByReceiverOrSender(Messages messages);

        void onMessageSearch(Messages messages);
    }

    public interface OnMessageListener {
        void updateMessages(Messages messages);
    }

    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
    private Context context;
    private Messages messages;
    private List<MessageService.MessageCountListener> messageCountListeners = new ArrayList<>();
    private List<MessageService.MessageCrudListener> messageCrudListeners = new ArrayList<>();
    private List<MessageService.MessageSearchListener> messageSearchListeners = new ArrayList<>();

    private static final int MESSAGE_NOTIFICATION = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public MessageService(Context context) {
        super(MessageService.class.getSimpleName());
        context = context;
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

    public void readMessage(String messageId) {
        HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .readMessage(messageId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message message = response.body();
                        if (message == null || message.isEmpty()) {
                            for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                                listener.onMessageRead(HttpURLConnection.HTTP_NO_CONTENT, "", message);
                            }
                            Log.i(MessageService.class.getName(), "message is null");
                        } else {
                            for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                                listener.onMessageRead(HttpURLConnection.HTTP_OK, "", message);
                            }
                            SerializeHelper.saveObject(context, message);
                            Log.i(MessageService.class.getName(), message.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable throwable) {
                        for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                            listener.onMessageRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(MessageService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void countByReceiver() {

        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Integer> callAsync = service.countMessagesByReceiver();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer sum = response.body();
                for (MessageService.MessageCountListener listener : messageCountListeners) {
                    listener.onMessageCountByReceiver(sum);
                }
                Log.i(MessageService.class.getName(), "Sum " + sum);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                for (MessageService.MessageCountListener listener : messageCountListeners) {
                    listener.onMessageCountByReceiver(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void countByReceiverOrSender() {
        IMessageService service               =
                HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage());
        service.countMessagesByReceiver().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Integer sum = response.body();
                        for (MessageService.MessageCountListener listener : messageCountListeners) {
                            listener.onMessageCountByReceiverOrSender(sum);
                        }
                        Log.i(MessageService.class.getName(), "Sum " + sum);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable throwable) {
                        for (MessageService.MessageCountListener listener : messageCountListeners) {
                            listener.onMessageCountByReceiverOrSender(null);
                        }
                        Log.e(MessageService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void countBySender() {

        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());
        Call<Integer> callAsync = service.countMessagesBySender();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer sum = response.body();
                for (MessageService.MessageCountListener listener : messageCountListeners) {
                    listener.onMessageCountBySender(sum);
                }
                Log.i(MessageService.class.getName(), "Sum " + sum);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                for (MessageService.MessageCountListener listener : messageCountListeners) {
                    listener.onMessageCountBySender(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchMessagesByReceiverOrSender() {

        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Messages> callAsync = service.searchMessageByReceiverOrSender();
        callAsync.enqueue(new Callback<Messages>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Messages messages = response.body();
                if (messages == null || messages.isEmpty()) {
                    Log.i(MessageService.class.getName(), "message is null");
                } else {
                    for (MessageService.MessageSearchListener listener : messageSearchListeners) {
                        listener.onMessageSearchByReceiverOrSender(messages);
                    }
                    SerializeHelper.saveObject(context, messages);
                    Log.i(MessageService.class.getName(), String.valueOf(messages.size()));
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable throwable) {
                for (MessageService.MessageSearchListener listener : messageSearchListeners) {
                    listener.onMessageSearchByReceiverOrSender(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void createMessage(Message message) {

        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());
        service.createMessage(message).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                message.setId(response.body());
                for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                    listener.onMessageCreate(HttpURLConnection.HTTP_OK, "", message.getId());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                    listener.onMessageCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void deleteMessage(Message message) {

        HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .deleteMessage(message).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                            listener.onMessageDelete(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                            listener.onMessageDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(MessageService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchMessage(Search search) {

        IMessageService service
                = HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());
        service.searchMessage(search).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Messages> call, Response<Messages> response) {
                Messages messages = response.body();
                for (MessageService.MessageSearchListener listener : messageSearchListeners) {
                    listener.onMessageSearch(messages);
                }
            }

            @Override
            public void onFailure(Call<Messages> call, Throwable throwable) {
                for (MessageService.MessageSearchListener listener : messageSearchListeners) {
                    listener.onMessageSearch(null);
                }
                Log.e(MessageService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void updateMessage(Message message) {
        HttpServiceGenerator.createService(IMessageService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage()).
                updateMessage(message).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                            listener.onMessageUpdate(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (MessageService.MessageCrudListener listener : messageCrudListeners) {
                            listener.onMessageUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
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
                if (authenticationUser != null) {
                  // searchMessage(Search.builder().re);
                   // getMessages();
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

  /*  private void getMessages() {
        if (SerializeHelper.hasFile(this, AuthenticationUser.AUTH_USER_FILE_NAME)) {
            this.authenticationUser = (AuthenticationUser) SerializeHelper.loadObject(getApplicationContext(), AuthenticationUser.AUTH_USER_FILE_NAME);
            if (authenticationUser != null && !authenticationUser.isEmpty()) {
                Log.d(MessageService.class.getName(), "getMessages");
                String url = getResources().getString(R.string.get_message_read_many_undelivered);
                GetJSON asyncTask = new GetJSON(this.getApplication());
                // GetJSON asyncTask =new GetJSON(context);
                //asyncTask.delegate = this;
                asyncTask.execute(url, "", this.authenticationUser.getToken());
            }
        }
    }*/

    public void setOnMessageCountListener(MessageService.MessageCountListener messageCountListener) {
        messageCountListeners.add(messageCountListener);
    }

    public void setOnMessageCrudListener(MessageService.MessageCrudListener messageCrudListener) {
        messageCrudListeners.add(messageCrudListener);
    }

    public void setOnMessageSearchListener(MessageService.MessageSearchListener messageSearchListener) {
        messageSearchListeners.add(messageSearchListener);
    }

}
