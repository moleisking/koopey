package com.koopey.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.PostJSON;
import com.koopey.model.Alert;
import com.koopey.model.Transaction;

/**
 * Created by Scott on 04/11/2017.
 */
public class TransactionDialogFragment extends DialogFragment implements PostJSON.PostResponseListener, View.OnClickListener {

    public interface OnTransactionDialogFragmentListener {
        void createTransactionDialogEvent(Transaction transaction);
        void updateTransactionDialogEvent(Transaction transaction);
        void deleteTransactionDialogEvent(Transaction transaction);
    }

    public OnTransactionDialogFragmentListener delegate = (OnTransactionDialogFragmentListener) getTargetFragment();

    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private Button btnCancel, btnCreate, btnDelete, btnUpdate;
    private EditText txtName, txtValue, txtTotal, txtQuantity;
    private Spinner lstCurrency;
    private Transaction transaction ;
    private boolean showCreateButton = true;
    private boolean showUpdateButton = true;
    private boolean showDeleteButton = true;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            //Set listeners
            this.btnCancel.setOnClickListener(this);
            this.btnUpdate.setOnClickListener(this);
            this.btnDelete.setOnClickListener(this);
            this.setVisibility(View.GONE);
            //Set buttons
            this.showCreateButton = this.getActivity().getIntent().getBooleanExtra("showCreateButton", false);
            this.showUpdateButton = this.getActivity().getIntent().getBooleanExtra("showUpdateButton", false);
            this.showDeleteButton = this.getActivity().getIntent().getBooleanExtra("showDeleteButton", false);
            this.setVisibility();
        } catch (Exception ex) {
            Log.w(TransactionDialogFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == this.btnCancel.getId()) {
                this.dismiss();
            } else if (v.getId() == this.btnCreate.getId()) {
                this.getTransaction();
                ((TransactionDialogFragment.OnTransactionDialogFragmentListener) getTargetFragment()).createTransactionDialogEvent(transaction);
                this.dismiss();
            } else if (v.getId() == this.btnDelete.getId()) {
                ((TransactionDialogFragment.OnTransactionDialogFragmentListener) getTargetFragment()).deleteTransactionDialogEvent(transaction);
                this.dismiss();
            } else if (v.getId() == this.btnUpdate.getId()) {
                this.getTransaction();
                ((TransactionDialogFragment.OnTransactionDialogFragmentListener) getTargetFragment()).updateTransactionDialogEvent(transaction);
                this.dismiss();
            }
        } catch (Exception ex) {
            Log.d(TransactionDialogFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Define transaction
        this.transaction = (Transaction) this.getActivity().getIntent().getSerializableExtra("transaction");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_dialog, container, false);
        //Set controls
        this.btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        this.btnCreate = (Button) rootView.findViewById(R.id.btnCreate);
        this.btnUpdate = (Button) rootView.findViewById(R.id.btnUpdate);
        this.btnDelete = (Button) rootView.findViewById(R.id.btnDelete);
        this.lstCurrency = (Spinner) rootView.findViewById(R.id.lstCurrency);
        this.txtName = (EditText) rootView.findViewById(R.id.txtName);
        this.txtValue = (EditText) rootView.findViewById(R.id.txtItemValue);
        this.txtTotal = (EditText) rootView.findViewById(R.id.txtTotalValue);
        this.txtQuantity = (EditText) rootView.findViewById(R.id.txtQuantity);
        //this.delegate = (OnFeeDialogFragmentListener) getTargetFragment();
        return rootView;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
        super.onDismiss(dialog);
    }

    @Override
    public void onPostResponse(String output) {
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if(alert.isSuccess()) {
                    Toast.makeText(this.getActivity(), getResources().getString(R.string.info_update), Toast.LENGTH_LONG).show();
                } else if (alert.isError()){
                    Toast.makeText(this.getActivity(), getResources().getString(R.string.error_update), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.w(TransactionDialogFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.populateCurrencies();
        this.setTransaction();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int width = size.x;
        window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    public void setVisibility(int visible) {
        this.btnCreate.setVisibility(visible);
        this.btnUpdate.setVisibility(visible);
        this.btnDelete.setVisibility(visible);
    }

    public void setVisibility() {
        if (this.showCreateButton) {
            this.btnCreate.setVisibility(View.VISIBLE);
        } else {
            this.btnCreate.setVisibility(View.GONE);
        }
        if (this.showUpdateButton) {
            this.btnUpdate.setVisibility(View.VISIBLE);
        } else {
            this.btnUpdate.setVisibility(View.GONE);
        }
        if (this.showDeleteButton) {
            this.btnDelete.setVisibility(View.VISIBLE);
        } else {
            this.btnDelete.setVisibility(View.GONE);
        }
    }

    public void getTransaction() {
        transaction = Transaction.builder()
                .name(this.txtName.getText().toString())
                .quantity(Integer.parseInt(this.txtQuantity.getText().toString()))
                .itemValue(Double.parseDouble(this.txtValue.getText().toString()))
                .totalValue(Double.parseDouble(this.txtTotal.getText().toString()))
                .build();
    }

    public void setTransaction() {
        if (this.transaction != null) {

            this.txtName.setText(this.transaction.getName());
            this.txtQuantity.setText(this.transaction.getQuantity());
            this.txtValue.setText(String.valueOf(this.transaction.getItemValue()));
            this.txtTotal.setText(String.valueOf(this.transaction.getTotalValue()));
        } else {
           // this.transaction ;
        }
    }

    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(transaction.getCurrency()));
    }

   /* private void postTransaction() {
        PostJSON asyncTask = new PostJSON();
        asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_transaction_create), transaction.toString(), myUser.getToken());
    }*/


}
