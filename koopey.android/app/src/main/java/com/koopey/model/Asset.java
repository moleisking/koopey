package com.koopey.model;

import android.graphics.Bitmap;

import com.koopey.helper.ImageHelper;
import com.koopey.model.base.Base;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Asset extends Base {

    public static final String ASSET_FILE_NAME = "asset.dat";
    public static final String MY_ASSET_FILE_NAME = "my_asset.dat";
    @Builder.Default
    public Advert advert = new Advert();
    private String firstImage;
    private String secondImage;
    private String thirdImage;
    private String fourthImage;
    private Location location;
    private Transactions transactions;
    private Tags tags;
    private String buyerId;
    private String sellerId;
    private String measure;
    @Builder.Default
    private String currency = "eur";
    @Builder.Default
    private int width = 0;
    @Builder.Default
    private int height = 0;
    @Builder.Default
    private int length = 0;
    @Builder.Default
    private int weight = 0;
    @Builder.Default
    private int value = 0;
    @Builder.Default
    private long manufacture = 0;
    @Builder.Default
    private int distance = 0;
    @Builder.Default
    private int quantity = 0;
    @Builder.Default
    private boolean available = true;

    public Boolean hasFirstImage() {
       return firstImage == null || firstImage.length() <= 0 ? false : true;
    }
    public Boolean hasSecondImage() {
        return secondImage == null || secondImage.length() <= 0 ? false : true;
    }
    public Boolean hasThirdImage() {
        return thirdImage == null || thirdImage.length() <= 0 ? false : true;
    }
    public Boolean hasFourthImage() {
        return fourthImage == null || fourthImage.length() <= 0 ? false : true;
    }
    public Bitmap getFirstImageAsBitmap() {
        return ImageHelper.UriToBitmap(firstImage);
    }

    public Bitmap getSecondImageAsBitmap() {
        return ImageHelper.UriToBitmap(secondImage);
    }

    public Bitmap getThirdImageAsBitmap() {
        return ImageHelper.UriToBitmap(thirdImage);
    }

    public Bitmap getFourthImageAsBitmap() {
        return ImageHelper.UriToBitmap(fourthImage);
    }

    public String getValueAsString() {
        return Double.toString(value);
    }

    public boolean isEmpty() {
        return buyerId == null || firstImage == null || sellerId == null || buyerId.length() <= 0
                || firstImage.length() <= 0 || sellerId.length() <= 0 || super.isEmpty() ? true : false;
    }
}
