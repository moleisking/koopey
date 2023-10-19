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
        void onTransactionRead(int code, String message, Transaction transaction);

        void onTransactionCreate(int code, String message, String transactionId);

        void onTransactionDelete(int code, String message, Transaction transaction);

        void onTransactionUpdate(int code, String message, Transaction transaction);
    }

    public interface TransactionSearchListener {
        void onTransactionSearch(int code, String message, Transactions transactions);
    }

    public interface TransactionSearchByLocationListener {
        void onTransactionSearchByDestination(int code, String message, Transactions transactions);

        void onTransactionSearchByLocation(int code, String message, Transactions transactions);

        void onTransactionSearchBySource(int code, String message, Transactions transactions);
    }

    public interface TransactionSearchByBuyerOrSellerListener {
        void onTransactionSearchByBuyer(int code, String message, Transactions transactions);

        void onTransactionSearchByBuyerOrSeller(int code, String message, Transactions transactions);

        void onTransactionSearchBySeller(int code, String message, Transactions transactions);
    }

    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
    private Context context;

    private List<TransactionService.TransactionCrudListener> transactionCrudListeners = new ArrayList<>();
    private List<TransactionService.TransactionSearchListener> transactionSearchListeners = new ArrayList<>();
    private List<TransactionService.TransactionSearchByLocationListener> transactionSearchByLocationListeners = new ArrayList<>();
    private List<TransactionService.TransactionSearchByBuyerOrSellerListener> transactionSearchByBuyerOrSellerListeners = new ArrayList<>();

    public TransactionService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public void setOnTransactionCrudListener(TransactionService.TransactionCrudListener listener) {
        transactionCrudListeners.add(listener);
    }

    public void setOnTransactionSearchListener(TransactionService.TransactionSearchListener listener) {
        transactionSearchListeners.add(listener);
    }

    public void setOnTransactionSearchByLocationListener(TransactionService.TransactionSearchByLocationListener listener) {
        transactionSearchByLocationListeners.add(listener);
    }

    public void setOnTransactionSearchByBuyerOrSellerListener(TransactionService.TransactionSearchByBuyerOrSellerListener listener) {
        transactionSearchByBuyerOrSellerListeners.add(listener);
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

    public void read(String transactionId) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage()).read(transactionId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                        Transaction transaction = response.body();
                        if (transaction == null || transaction.isEmpty()) {
                            for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                                listener.onTransactionRead(HttpURLConnection.HTTP_NO_CONTENT, "", null);
                            }
                            Log.i(TransactionService.class.getName(), "transaction is null");
                        } else {
                            for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                                listener.onTransactionRead(HttpURLConnection.HTTP_OK, "", transaction);
                            }
                            SerializeHelper.saveObject(context, transaction);
                            Log.i(TransactionService.class.getName(), transaction.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Transaction> call, Throwable throwable) {
                        for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                            listener.onTransactionRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByLocation(String locationId) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage()).searchByLocation(locationId)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                        Transactions transactions = response.body();
                        if (transactions == null || transactions.isEmpty()) {
                            Log.i(TransactionService.class.getName(), "transaction is null");
                        } else {
                            for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                                listener.onTransactionSearchByLocation(HttpURLConnection.HTTP_OK, "", transactions);
                            }
                            SerializeHelper.saveObject(context, transactions);
                            Log.i(TransactionService.class.getName(), transactions.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Transactions> call, Throwable throwable) {
                        for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                            listener.onTransactionSearchByLocation(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByBuyer() {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage()).searchByBuyer().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                        listener.onTransactionSearchByBuyer(HttpURLConnection.HTTP_OK, "", transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                    listener.onTransactionSearchByBuyer(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchTransactionByBuyerOrSeller() {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByBuyerOrSeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                        Transactions transactions = response.body();
                        if (transactions == null || transactions.isEmpty()) {
                            Log.i(TransactionService.class.getName(), "transaction is null");
                        } else {
                            for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                                listener.onTransactionSearchByBuyerOrSeller(HttpURLConnection.HTTP_OK, "", transactions);
                            }
                            SerializeHelper.saveObject(context, transactions);
                            Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<Transactions> call, Throwable throwable) {
                        for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                            listener.onTransactionSearchByBuyerOrSeller(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchTransactionByDestination(String locationId) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByDestination(locationId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                        Transactions transactions = response.body();
                        if (transactions == null || transactions.isEmpty()) {
                            Log.i(TransactionService.class.getName(), "transaction is null");
                        } else {
                            for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                                listener.onTransactionSearchByDestination(HttpURLConnection.HTTP_OK, "", transactions);
                            }
                            SerializeHelper.saveObject(context, transactions);
                            Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<Transactions> call, Throwable throwable) {
                        for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                            listener.onTransactionSearchByDestination(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchBySeller() {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchBySeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                        Transactions transactions = response.body();
                        if (transactions == null || transactions.isEmpty()) {
                            for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                                listener.onTransactionSearchBySeller(HttpURLConnection.HTTP_NO_CONTENT, "", transactions);
                            }
                            Log.i(TransactionService.class.getName(), "transaction is null");
                        } else {
                            for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                                listener.onTransactionSearchBySeller(HttpURLConnection.HTTP_OK, "", transactions);
                            }
                            SerializeHelper.saveObject(context, transactions);
                            Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<Transactions> call, Throwable throwable) {
                        for (TransactionService.TransactionSearchByBuyerOrSellerListener listener : transactionSearchByBuyerOrSellerListeners) {
                            listener.onTransactionSearchBySeller(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchBySource(String locationId) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchBySource(locationId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                        Transactions transactions = response.body();
                        if (transactions == null || transactions.isEmpty()) {
                            for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                                listener.onTransactionSearchBySource(HttpURLConnection.HTTP_NO_CONTENT, "", transactions);
                            }
                            Log.i(TransactionService.class.getName(), "transaction is null");
                        } else {
                            for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                                listener.onTransactionSearchBySource(HttpURLConnection.HTTP_OK, "", transactions);
                            }
                            SerializeHelper.saveObject(context, transactions);
                            Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<Transactions> call, Throwable throwable) {
                        for (TransactionService.TransactionSearchByLocationListener listener : transactionSearchByLocationListeners) {
                            listener.onTransactionSearchBySource(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void create(Transaction transaction) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage()).create(transaction).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String transactionId = response.body();
                transaction.setId(transactionId);
                if (transaction == null || transaction.isEmpty()) {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionCreate(HttpURLConnection.HTTP_NO_CONTENT, "", transactionId);
                    }
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                        listener.onTransactionCreate(HttpURLConnection.HTTP_OK, "", transactionId);
                    }
                    Log.i(TransactionService.class.getName(), transaction.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void delete(Transaction transaction) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage()).delete(transaction).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionDelete(HttpURLConnection.HTTP_OK, "", transaction);
                }
                Log.i(TransactionService.class.getName(), transaction.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionDelete(HttpURLConnection.HTTP_NO_CONTENT, throwable.getMessage(), null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void search(Search search) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage()).search(search).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                        listener.onTransactionSearch(HttpURLConnection.HTTP_OK, "", transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), String.valueOf(transactions.size()));
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionSearchListener listener : transactionSearchListeners) {
                    listener.onTransactionSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void update(Transaction transaction) {
        HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage()).update(transaction).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionUpdate(HttpURLConnection.HTTP_OK, "", transaction);
                }
                Log.i(TransactionService.class.getName(), "transaction update success");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (TransactionService.TransactionCrudListener listener : transactionCrudListeners) {
                    listener.onTransactionUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }
}
