package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;
import com.koopey.model.authentication.Token;
import com.koopey.service.impl.IAssetService;
import com.koopey.service.impl.ITransactionService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionService {

    public interface TransactionListener {
        void onGetTransaction(Transaction transaction);
        void onGetTransactionSearchByAsset(Transactions transactions);
        void onGetTransactionSearchByBuyer(Transactions transactions);
        void onGetTransactionSearchByBuyerOrSeller(Transactions transactions);
        void onGetTransactionSearchByDestination(Transactions transactions);
        void onGetTransactionSearchBySeller(Transactions transactions);
        void onGetTransactionSearchBySource(Transactions transactions);
        void onPostTransactionCreate(String transactionId);
        void onPostTransactionDelete(Transaction transaction);
        void onPostTransactionSearch(Transactions transactions);
        void onPostTransactionUpdate(Transaction transaction);
    }

    AuthenticationService authenticationService;
    private Context context;

    private List<TransactionService.TransactionListener> transactionListeners = new ArrayList<>();

    public TransactionService(Context context) {
        super();
        this.context = context;
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

    public void getTransaction(String transactionId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transaction> callAsync = service.getTransaction(transactionId);
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                Transaction transaction = response.body();
                if (transaction == null || transaction.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransaction(transaction);
                    }
                    SerializeHelper.saveObject(context, transaction);
                    Log.i(TransactionService.class.getName(), transaction.toString());
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransaction(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getTransactionSearchByAsset(String assetId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.getTransactionSearchByAsset(assetId);
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransactionSearchByAsset(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), transactions.toString());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransactionSearchByAsset(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getTransactionSearchByBuyer() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.getTransactionSearchByBuyer();
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransactionSearchByBuyer(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransactionSearchByBuyer(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getTransactionSearchByBuyerOrSeller() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.getTransactionSearchByBuyerOrSeller();
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransactionSearchByBuyerOrSeller(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Serahc results " + transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransactionSearchByBuyerOrSeller(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getTransactionSearchByDestination(String locationId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.getTransactionSearchByDestination(locationId);
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransactionSearchByDestination(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results "+ transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransactionSearchByDestination(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getTransactionSearchBySeller() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.getTransactionSearchBySeller();
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransactionSearchBySeller(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results "+transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransactionSearchBySeller(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getTransactionSearchBySource(String locationId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.getTransactionSearchBySource(locationId);
        callAsync.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onGetTransactionSearchBySource(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(), "Search results " + transactions.size());
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransactionSearchBySource(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postTransactionCreate(Transaction transaction) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<String> callAsync = service.postTransactionCreate(transaction);
        callAsync.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String transactionId = response.body();
                transaction.id = transactionId;
                if (transaction == null || transaction.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onPostTransactionCreate(transactionId);
                    }
                    Log.i(TransactionService.class.getName(), transaction.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onPostTransactionCreate(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postTransactionDelete(Transaction transaction) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.postTransactionDelete(transaction);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onPostTransactionDelete(transaction);
                    }

                    Log.i(TransactionService.class.getName(), transaction.toString());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransaction(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postTransactionSearch(Search search) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Transactions> callAsync = service.postTransactionSearch(search);
        callAsync.enqueue(new Callback<Transactions>() {
            @Override
            public void onResponse(Call<Transactions> call, Response<Transactions> response) {
                Transactions transactions = response.body();
                if (transactions == null || transactions.isEmpty()) {
                    Log.i(TransactionService.class.getName(), "transaction is null");
                } else {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onPostTransactionSearch(transactions);
                    }
                    SerializeHelper.saveObject(context, transactions);
                    Log.i(TransactionService.class.getName(),String.valueOf(transactions.size()));
                }
            }

            @Override
            public void onFailure(Call<Transactions> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onGetTransaction(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postTransactionUpdate(Transaction transaction) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITransactionService service
                = HttpServiceGenerator.createService(ITransactionService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.postTransactionUpdate(transaction);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                    for (TransactionService.TransactionListener listener : transactionListeners) {
                        listener.onPostTransactionUpdate(transaction);
                    }
                    Log.i(TransactionService.class.getName(),"transaction update success");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (TransactionService.TransactionListener listener : transactionListeners) {
                    listener.onPostTransactionUpdate(null);
                }
                Log.e(TransactionService.class.getName(), throwable.getMessage());
            }
        });
    }
}
