package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Search;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.ITransactionService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionService {

    public interface TransactionCrudListener {
        void onTransactionRead(int code, String message,Transaction transaction);
        void onTransactionCreate(int code, String message,String transactionId);
        void onTransactionDelete(int code, String message,Transaction transaction);
        void onTransactionUpdate(int code, String message,Transaction transaction);
    }

    public interface TransactionSearchListener {
        void onTransactionSearchByAsset(int code, String message,Transactions transactions);
        void onTransactionSearchByBuyer(int code, String message,Transactions transactions);
        void onTransactionSearchByBuyerOrSeller(int code, String message,Transactions transactions);
        void onTransactionSearchByDestination(int code, String message,Transactions transactions);
        void onTransactionSearchBySeller(int code, String message,Transactions transactions);
        void onTransactionSearchBySource(int code, String message,Transactions transactions);
        void onTransactionSearch(int code, String message,Transactions transactions);
    }

    AuthenticationService authenticationService;
    AuthenticationUser authenticationUser;
    private Context context;

    private List<TransactionService.TransactionCrudListener> transactionCrudListeners = new ArrayList<>();
    private List<TransactionService.TransactionSearchListener> transactionSearchListeners = new ArrayList<>();

    public TransactionService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public void setOnTransactionCrudListener(TransactionService.TransactionCrudListener transactionCrudListener) {
        transactionCrudListeners.add(transactionCrudListener);
    }

    public void setOnTransactionSearchListener(TransactionService.TransactionSearchListener transactionSearchListener) {
        transactionSearchListeners.add(transactionSearchListener);
    }

    public Transactions getLocalTransactionsFromFile() {
        Transactions transactions = new Transactions();
        if (SerializeHelper.hasFile(context, Transactions.TRANSACTIONS_FILE_NAME)) {
            transactions = (Transactions) SerializeHelper.loadObject(context, Transactions.TRANSACTIONS_FILE_NAME);
        }
        return transactions;
    }

    public boolean hasTransactionsFile() {
        Transactions transactions = getLocalTransactionsFromFile();
        return transactions.size() <= 0 ? false : true;
    }

    public void readTransaction(String transactionId) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transaction> callAsync = service.getTransaction(transactionId);
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Transaction transaction = response.body();
                if (transaction == null || transaction.isEmpty()) {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionRead(HttpURLConnection.HTTP_NO_CONTENT, "",null);
                    }
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionRead(HttpURLConnection.HTTP_OK, "",transaction);
                    }
                    SerializeHelper.saveObject(context, transaction);
                    Log.i(TransactionService.class.getName(), transaction.toString());
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionRead(HttpURLConnection.HTTP_BAD_REQUEST,throwable.getMessage() ,null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionByAsset(String assetId) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.getTransactionSearchByAsset(assetId);
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchByAsset(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), transactions.toString());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearchByAsset(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionByBuyer() {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.getTransactionSearchByBuyer();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchByBuyer(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearchByBuyer(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionByBuyerOrSeller() {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.getTransactionSearchByBuyerOrSeller();
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchByBuyerOrSeller(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Serahc results " + transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearchByBuyerOrSeller(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionByDestination(String locationId) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.getTransactionSearchByDestination(locationId);
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchByDestination(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results "+ transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearchByDestination(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionBySeller() {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.getTransactionSearchBySeller();
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchBySeller(HttpURLConnection.HTTP_NO_CONTENT, "",transactions);
                    }
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchBySeller(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results "+transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearchBySeller(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionBySource(String locationId) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.getTransactionSearchBySource(locationId);
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchBySource(HttpURLConnection.HTTP_NO_CONTENT, "",transactions);
                    }
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearchBySource(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                }
            }
            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearchBySource(HttpURLConnection.HTTP_BAD_REQUEST,throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void createTransaction(Transaction transaction) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<String> callAsync = service.postTransactionCreate(transaction);
        callAsync.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String transactionId = response.body();
                transaction.setId(transactionId);
                if (transaction == null || transaction.isEmpty()) {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionCreate(HttpURLConnection.HTTP_NO_CONTENT, "",transactionId);
                    }
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionCreate(HttpURLConnection.HTTP_OK, "",transactionId);
                    }
                    Log.i(TransactionService.class.getName(), transaction.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionCreate(HttpURLConnection.HTTP_BAD_REQUEST,throwable.getMessage() ,null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void deleteTransaction(Transaction transaction) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Void> callAsync = service.postTransactionDelete(transaction);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionDelete(HttpURLConnection.HTTP_OK,"",transaction);
                    }
                    Log.i(TransactionService.class.getName(), transaction.toString());
            }
            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionDelete(HttpURLConnection.HTTP_NO_CONTENT,throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransaction(Search search) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Transactions> callAsync = service.postTransactionSearch(search);
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearch(HttpURLConnection.HTTP_OK, "",transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(),String.valueOf(transactions.size()));
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void updateTransaction(Transaction transaction) {

        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage());

        Call<Void> callAsync = service.postTransactionUpdate(transaction);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionUpdate(HttpURLConnection.HTTP_OK, "",transaction);
                    }
                    Log.i(TransactionService.class.getName(),"transaction update success");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }
}
