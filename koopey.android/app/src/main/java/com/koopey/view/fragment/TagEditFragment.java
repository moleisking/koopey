package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.model.Tag;
import com.koopey.service.TagService;

import java.net.HttpURLConnection;

public class TagEditFragment extends Fragment implements TagService.TagCrudListener,  View.OnClickListener {

    private EditText txtChinese, txtDescription, txtName, txtEnglish, txtDutch,  txtFrench, txtGerman,
            txtItalian,txtPortuguese ,txtSpanish;
    private FloatingActionButton btnSave, btnDelete;
    private Tag tag;
    private TagService tagService;
    private boolean checkForm() {
         if (txtDescription.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_description) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtName.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_name) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getActivity().getIntent().hasExtra("tag")) {
            tag = (Tag) this.getActivity().getIntent().getSerializableExtra("tag");
        } else {
            tag = Tag.builder().build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tag_edit, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity().getIntent().hasExtra("tag")) {
            getActivity().getIntent().removeExtra("tag");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.tag != null) {
            txtDescription.setText(tag.getDescription());
            txtName.setText(tag.getName());
            txtEnglish.setText(tag.getEn());
            txtGerman.setText(tag.getDe());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDelete = getActivity().findViewById(R.id.btnDelete);
        btnSave = getActivity().findViewById(R.id.btnSave);

        txtChinese = getActivity().findViewById(R.id.txtChinese);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtDutch = getActivity().findViewById(R.id.txtDutch);
        txtEnglish = getActivity().findViewById(R.id.txtEnglish);
        txtGerman = getActivity().findViewById(R.id.txtGerman);
        txtFrench = getActivity().findViewById(R.id.txtFrench);
        txtItalian = getActivity().findViewById(R.id.txtItalian);
        txtName = getActivity().findViewById(R.id.txtName);
        txtPortuguese = getActivity().findViewById(R.id.txtPortuguese);
        txtSpanish = getActivity().findViewById(R.id.txtSpanish);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == this.btnDelete.getId()) {
            tagService.delete(tag);
        } else if (v.getId() == this.btnSave.getId() && checkForm()) {
            tag.setDe(txtGerman.getText().toString());
            tag.setDescription(txtDescription.getText().toString());
            tag.setEn(txtEnglish.getText().toString());
            tag.setEs(txtSpanish.getText().toString());
            tag.setFr(txtFrench.getText().toString());
            tag.setName(txtName.getText().toString());
            tag.setPt(txtPortuguese.getText().toString());
            tag.setZh(txtChinese.getText().toString());

            if (tag.getType().equals("create")) {
                tag.setType("");
                tagService.create(tag);
            } else {
                tagService.update(tag);
            }
        }
    }

    @Override
    public void onTagCreate(int code, String message, String tagId) {
        if (code == HttpURLConnection.HTTP_OK) {
            tag.setId(tagId);
            Toast.makeText(this.getActivity(), "Create", Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(this.getActivity(), "Create fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Create else", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTagDelete(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "@string/label_delete", Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(this.getActivity(), "Delete fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Delete fail", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTagRead(int code, String message, Tag tag) {

    }

    @Override
    public void onTagUpdate(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "@string/label_update", Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(this.getActivity(), "Update fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Update fail", Toast.LENGTH_LONG).show();
        }
    }
}
