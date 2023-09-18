package com.koopey.view.component;

import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;

public abstract class PrivateListFragment extends ListFragment {

    private AuthenticationService authenticationService;
    public AuthenticationUser authenticationUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
        hideKeyboard();

    }

    public void hideKeyboard() {
        ((PrivateActivity) getActivity()).hideKeyboard();
     /*   View currentView = this.getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }*/
    }
}
