package com.koopey.view;

//import com.tokenautocomplete.TokenCompleteTextView;


/**
 * Created by Scott on 25/02/2017.
 *
 * source: https://github.com/splitwise/TokenAutoComplete
 *
 * Copyright (c) 2013, 2014 splitwise, Wouter Dullaert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TagTokenAutoCompleteView /*extends TokenCompleteTextView<Tag>*/  {

    private final String LOG_HEADER = "TAG:AUTOCOMPLETE:VIEW";
    private boolean tagChanged = false;
    private String language = "en";

   /* public TagTokenAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.allowDuplicates(false);
    }

    @Override
    protected View getViewForObject(Tag tag) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.token_tag, (ViewGroup) getParent(), false);
        view.setText(tag.getText(this.language));
        return view;
    }

    @Override
    protected Tag defaultObject(String completionText) {
        return new Tag();
    }

    public Tags getSelectedTags(){
        Tags currentTags = new Tags();
        currentTags.setTagList(this.getObjects());
        return currentTags;
    }

    public void setLanguage(String language){
        this.language = language;
    }*/
}
