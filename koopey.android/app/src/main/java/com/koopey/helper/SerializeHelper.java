/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.koopey.helper;

import android.content.Context;
import android.util.Log;

//import com.google.zxing.common.BitMatrix;
import com.koopey.model.Bitcoin;
import com.koopey.model.Ethereum;
import com.koopey.model.Event;
import com.koopey.model.Events;
import com.koopey.model.Game;
import com.koopey.model.Games;
import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Messages;
import com.koopey.model.Location;
import com.koopey.model.Locations;
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

    public static void saveObject(Context context, Object obj) {
        try {
            if ((obj instanceof Location) && !(obj instanceof Location)) {
                FileOutputStream fos = context.openFileOutput(Location.ASSET_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof AuthenticationUser) {
                FileOutputStream fos = context.openFileOutput(AuthenticationUser.AUTH_USER_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Locations) {
                if (((Locations) obj).getType() == Locations.MY_ASSETS_FILE_NAME) {
                    FileOutputStream fos = context.openFileOutput(Locations.MY_ASSETS_FILE_NAME, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(obj);
                    os.close();
                    fos.close();
                } else if (((Locations) obj).getType() == Locations.ASSET_SEARCH_RESULTS_FILE_NAME) {
                    FileOutputStream fos = context.openFileOutput(Locations.ASSET_SEARCH_RESULTS_FILE_NAME, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(obj);
                    os.close();
                    fos.close();
                } else if (((Locations) obj).getType() == Locations.ASSET_WATCH_LIST_FILE_NAME) {
                    FileOutputStream fos = context.openFileOutput(Locations.ASSET_WATCH_LIST_FILE_NAME, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(obj);
                    os.close();
                    fos.close();
                }
            } else if (obj instanceof Bitcoin) {
                FileOutputStream fos = context.openFileOutput(Bitcoin.BITCOIN_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Ethereum) {
                FileOutputStream fos = context.openFileOutput(Ethereum.ETHEREUM_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Event) {
                FileOutputStream fos = context.openFileOutput(Event.EVENT_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Events) {
                FileOutputStream fos = context.openFileOutput(Events.EVENTS_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Game) {
                FileOutputStream fos = context.openFileOutput(Game.GAME_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Games) {
                FileOutputStream fos = context.openFileOutput(Games.GAMES_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Location) {
                FileOutputStream fos = context.openFileOutput(Location.LOCATION_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Locations) {
                FileOutputStream fos = context.openFileOutput(Locations.LOCATIONS_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Messages) {
                FileOutputStream fos = context.openFileOutput(Messages.MESSAGES_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof User) {
                FileOutputStream fos = context.openFileOutput(User.USER_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Users) {
                FileOutputStream fos = context.openFileOutput(Users.USERS_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Tags) {
                FileOutputStream fos = context.openFileOutput(Tags.TAGS_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Transaction) {
                FileOutputStream fos = context.openFileOutput(Transaction.TRANSACTION_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            } else if (obj instanceof Transactions) {
                FileOutputStream fos = context.openFileOutput(Transactions.TRANSACTIONS_FILE_NAME, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(obj);
                os.close();
                fos.close();
            }
            Log.d(SerializeHelper.class.getName(), "Success");
        } catch (Exception e) {
            Log.d(SerializeHelper.class.getName(), e.getMessage());
        }
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
            if (filename.equals(Location.ASSET_FILE_NAME)) {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream is = new ObjectInputStream(fis);
                obj = (Location) is.readObject();
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
            } else if (filename.equals(Locations.ASSET_SEARCH_RESULTS_FILE_NAME) ||
                    filename.equals(Locations.ASSET_WATCH_LIST_FILE_NAME) ||
                    filename.equals(Locations.MY_ASSETS_FILE_NAME)) {
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
        } catch (Exception e) {
            Log.d(SerializeHelper.class.getName(), e.getMessage());
        }

        return obj;
    }
}