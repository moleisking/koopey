package com.koopey.model;

import android.graphics.Bitmap;

import com.koopey.model.base.BaseCollection;

public class Images extends BaseCollection<Image> {
    public static final String IMAGES_FILE_NAME = "images.dat";
    public static final int IMAGES_COUNT_MAX = 4;


    public Image getFirstImage() {
        if (this.size() > 0) {
            return this.get(0);
        } else {
            //R.string.default_user_image
            return null;
        }
    }



   /* public void setPrimaryImageUri(Image i) {
        if(this.size() > 0) {
           this.images.set(0, i);
        }
        else
        {
            this.images.add(i);
        }
    }*/

    /*********  Update *********/


    public void setPrimaryImageUri(Bitmap bm) {

        if (this.size() > 0) {
            this.get(0).setUri(bm);
        } else {
            Image image = new Image();
            image.setUri(bm);
            this.add(image);
        }
    }

}
