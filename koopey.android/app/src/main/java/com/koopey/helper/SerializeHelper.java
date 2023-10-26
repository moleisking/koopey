package com.koopey.helper;

import android.content.Context;
import android.util.Log;

import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Bitcoin;
import com.koopey.model.Classification;
import com.koopey.model.Classifications;
import com.koopey.model.Ethereum;
import com.koopey.model.Game;
import com.koopey.model.Games;
import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Message;
import com.koopey.model.Messages;
import com.koopey.model.Tags;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;
import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.Wallet;
import com.koopey.model.Wallets;
import com.koopey.model.authentication.AuthenticationUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeHelper {

    public static boolean hasFile(Context context, String filename) {
        boolean result = false;
        try {
            File file = context.getFileStreamPath(filename);
            if (file == null || !file.exists()) {
                result = false;
            } else {
                result = true;
            }
        } catch (Exception ioex) {
            result = false;
            Log.d(SerializeHelper.class.getName(), ioex.getMessage());
        } finally {
            return result;
        }
    }

    private static void saveObject(Context context, Object obj, String path) {
        try {
            FileOutputStream fos = context.openFileOutput(path, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
            Log.d(SerializeHelper.class.getName(), "saveObject " + obj.getClass());
        } catch (Exception e) {
            Log.d(SerializeHelper.class.getName(), e.getMessage());
        }
    }

    public static void saveObject(Context context, Asset obj) {
        saveObject(context, obj, Asset.ASSET_FILE_NAME);
    }

    public static void saveObject(Context context, Assets obj) {
        if (obj != null && obj.getType() != null && !obj.getType().isEmpty()) {
            if (obj.getType() == Assets.MY_ASSETS_FILE_NAME) {
                saveObject(context, obj, Assets.MY_ASSETS_FILE_NAME);
            } else if (obj.getType() == Assets.ASSET_SEARCH_RESULTS_FILE_NAME) {
                saveObject(context, obj, Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
            } else if (obj.getType() == Assets.ASSET_WATCH_LIST_FILE_NAME) {
                saveObject(context, obj, Assets.ASSET_WATCH_LIST_FILE_NAME);
            }
        }
    }

    public static void saveObject(Context context, AuthenticationUser obj) {
        saveObject(context, obj, AuthenticationUser.AUTH_USER_FILE_NAME);
    }

    public static void saveObject(Context context, Bitcoin obj) {
        saveObject(context, obj, Bitcoin.BITCOIN_FILE_NAME);
    }

    public static void saveObject(Context context, Classification obj) {
        saveObject(context, obj, Classification.CLASSIFICATION_FILE_NAME);
    }

    public static void saveObject(Context context, Classifications obj) {
        saveObject(context, obj, Classifications.CLASSIFICATIONS_FILE_NAME);
    }

    public static void saveObject(Context context, Ethereum obj) {
        saveObject(context, obj, Ethereum.ETHEREUM_FILE_NAME);
    }

    public static void saveObject(Context context, Game obj) {
        saveObject(context, obj, Game.GAME_FILE_NAME);
    }

    public static void saveObject(Context context, Games obj) {
        saveObject(context, obj, Games.GAMES_FILE_NAME);
    }

    public static void saveObject(Context context, Location obj) {
        saveObject(context, obj, Location.LOCATION_FILE_NAME);
    }

    public static void saveObject(Context context, Locations obj) {
        saveObject(context, obj, Locations.LOCATIONS_FILE_NAME);
    }

    public static void saveObject(Context context, Message obj) {
        saveObject(context, obj, Message.MESSAGE_FILE_NAME);
    }

    public static void saveObject(Context context, Messages obj) {
        saveObject(context, obj, Messages.MESSAGES_FILE_NAME);
    }

    public static void saveObject(Context context, User obj) {
        saveObject(context, obj, User.USER_FILE_NAME);
    }

    public static void saveObject(Context context, Users obj) {
        saveObject(context, obj, Users.USERS_FILE_NAME);
    }

    public static void saveObject(Context context, Tags obj) {
        saveObject(context, obj, Tags.TAGS_FILE_NAME);
    }

    public static void saveObject(Context context, Transaction obj) {
        saveObject(context, obj, Transaction.TRANSACTION_FILE_NAME);
    }

    public static void saveObject(Context context, Transactions obj) {
        saveObject(context, obj, Transactions.TRANSACTIONS_FILE_NAME);
    }

    public static void saveObject(Context context, Wallet obj) {
        saveObject(context, obj, Wallet.WALLET_FILE_NAME);
    }

    public static void saveObject(Context context, Wallets obj) {
        saveObject(context, obj, Wallets.WALLETS_FILE_NAME);
    }

    public static void deleteObject(Context context, String filename) {
        try {
            context.deleteFile(filename);
            Log.d(SerializeHelper.class.getName(), "Success");
        } catch (Exception e) {
            Log.d(SerializeHelper.class.getName(), e.getMessage());
        }
    }

    public static Object loadObject(Context context, String filename) {
        Object obj = null;
        try {
            if (filename.equals(Asset.ASSET_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Asset) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(AuthenticationUser.AUTH_USER_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (AuthenticationUser) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Bitcoin.BITCOIN_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Bitcoin) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Ethereum.ETHEREUM_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Ethereum) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Game.GAME_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Game) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Games.GAMES_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (com.koopey.helper.Games) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Location.LOCATION_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Location) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Locations.LOCATIONS_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Locations) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Messages.MESSAGES_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Messages) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Assets.ASSET_SEARCH_RESULTS_FILE_NAME) ||
                    filename.equals(Assets.ASSET_WATCH_LIST_FILE_NAME) ||
                    filename.equals(Assets.MY_ASSETS_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Locations) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Tags.TAGS_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Tags) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Transaction.TRANSACTION_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Transaction) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Transactions.TRANSACTIONS_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Transactions) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(User.USER_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (User) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Users.USERS_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Users) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Wallet.WALLET_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Wallet) is.readObject();
                is.close();
                fis.close();
            } else if (filename.equals(Wallets.WALLETS_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Wallets) is.readObject();
                is.close();
                fis.close();
            }
        } catch (InvalidClassException e) {
            deleteObject(context, filename);
            Log.d(SerializeHelper.class.getName(), e.getMessage());
        } catch (Exception e) {
            Log.d(SerializeHelper.class.getName(), e.getMessage());
        }

        return obj;
    }
}