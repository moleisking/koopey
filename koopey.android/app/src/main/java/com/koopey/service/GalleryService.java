package com.koopey.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GalleryService implements DefaultLifecycleObserver {

    public interface GalleryListener {
        void onImageLoadFromGallery(Bitmap bitmap);

        void onImageGalleryError(String error);

    }

    private final ActivityResultRegistry activityResultRegistry;
    private ActivityResultLauncher<String> activityResultLauncher;
    private List<GalleryListener> galleryListeners;
    private FragmentActivity activity;
    public static final String GALLERY_REQUEST = "GALLERY_REQUEST";
    public static final int IMAGE_SIZE = 256;

    public GalleryService(@NonNull ActivityResultRegistry registry, FragmentActivity activity) {
        this.activityResultRegistry = registry;
        this.galleryListeners = new ArrayList<>();
        this.activity = activity;
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        activityResultLauncher = activityResultRegistry.register(GALLERY_REQUEST, owner, new ActivityResultContracts.GetContent(),
                uri -> {
                    Log.d(GalleryService.class.getSimpleName(), uri.toString());

                    try {
                        Log.d(GalleryService.class.getSimpleName(), "image to bitmap");
                        Bitmap bitmap = this.getThumbnail(uri);
                        for (GalleryListener listener : galleryListeners) {
                            listener.onImageLoadFromGallery(bitmap);
                        }
                    } catch (IOException ioex) {
                        Log.d(GalleryService.class.getSimpleName(), ioex.getMessage());
                        for (GalleryListener listener : galleryListeners) {
                            listener.onImageGalleryError(ioex.getMessage());
                        }
                    }


                });
    }

    public void selectImage() {
        activityResultLauncher.launch("image/*");
    }

    public void setGalleryListener(GalleryListener galleryListener) {
        galleryListeners.add(galleryListener);
    }

    public Bitmap getThumbnail(Uri uri) throws IOException {
        InputStream input = activity.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > IMAGE_SIZE) ? (originalSize / IMAGE_SIZE) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//
        input = activity.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }
}
