package com.koopey.service;

import android.util.Log;
import android.widget.Toast;

import com.koopey.R;
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Alert;
import com.koopey.model.Assets;
import com.koopey.model.AuthUser;
import com.koopey.model.Tags;
import com.koopey.model.Transactions;

public class AssetService implements GetJSON.GetResponseListener, PostJSON.PostResponseListener {
    @Override
    public void onGetResponse(String output) {
        try {
           /* String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isSuccess()) {
                    Log.i(DataService.class.getName(), "Login success");
                    Toast.makeText(activity, activity.getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("assets")) {
                Assets assets = new Assets(Assets.MY_ASSETS_FILE_NAME);
                assets.parseJSON(output);
                SerializeHelper.saveObject(activity, assets);
            } else if (header.contains("tags")) {
                Tags tags = new Tags();
                tags.parseJSON(output);
                SerializeHelper.saveObject(activity, tags);
            } else if (header.contains("transactions")) {
                Transactions transactions = new Transactions();
                transactions.parseJSON(output);
                SerializeHelper.saveObject(activity, transactions);
            } else if (header.contains("user")) {
                authUser = new AuthUser();
                authUser.parseJSON(output);
                authUser.print();
                Toast.makeText(activity, activity.getResources().getString(R.string.info_authentication), Toast.LENGTH_SHORT).show();
                SerializeHelper.saveObject(activity, authUser);
                //showPrivateActivity();
            }*/
        } catch (Exception ex) {
            Log.d(DataService.class.getName() , ex.getMessage());
        }
    }

    @Override
    public void onPostResponse(String output) {
        // activity.showProgress(false);
        try {
         /*   String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isSuccess()) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("user")) {

                this.authUser = new AuthUser();
                this.authUser.parseJSON(output);
                this.authUser.print();
                Toast.makeText(activity, activity.getResources().getString(R.string.info_authentication), Toast.LENGTH_SHORT).show();
                SerializeHelper.saveObject(activity, authUser);
                //showPrivateActivity();
            }*/
        } catch (Exception ex) {
            // Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }
    }
}
