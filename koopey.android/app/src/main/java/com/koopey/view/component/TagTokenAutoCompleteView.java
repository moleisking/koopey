package com.koopey.view.component;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koopey.R;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.tokenautocomplete.TokenCompleteTextView;

public class TagTokenAutoCompleteView extends TokenCompleteTextView<Tag> {

    private boolean tagChanged = false;
    private String language = "en";

    private Context context;

    public TagTokenAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //this.allowDuplicates(false);
    }

    @Override
    protected View getViewForObject(Tag tag) {
        LayoutInflater l = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.token_tag, (ViewGroup) getParent(), false);
        view.setText(tag.getText(this.language));
        return view;
    }

    @Override
   protected Tag defaultObject(String completionText) {
        return Tag.builder().build();
    }

    public Tags getSelectedTags(){
        Tags currentTags = new Tags();
       // currentTags.setTagList(this.getObjects());
        return currentTags;
    }

    public void setLanguage(String language){
        this.language = language;
    }
}

