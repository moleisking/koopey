package com.koopey.view.component;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.TagService;
import com.koopey.view.MainActivity;

public class PrivateDialogFragment extends DialogFragment {

    private AuthenticationService authenticationService;
    public AuthenticationUser authenticationUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public void hideKeyboard() {
        ((MainActivity) getActivity()).hideKeyboard();
     /*   View currentView = this.getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }*/
    }
}
