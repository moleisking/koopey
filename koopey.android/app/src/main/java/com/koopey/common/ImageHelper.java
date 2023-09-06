package com.koopey.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

//import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImageHelper {

    private final static String default_product_image = "data:image/png;base64,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";
    private final static String default_user_image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAhIAAAISCAMAAACu49aNAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBNYWNpbnRvc2giIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MEQyQzNERDE0QTgzMTFFMUI0N0FDQ0U5NDgzQTI2NEYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MEQyQzNERDI0QTgzMTFFMUI0N0FDQ0U5NDgzQTI2NEYiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDowRDJDM0RDRjRBODMxMUUxQjQ3QUNDRTk0ODNBMjY0RiIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDowRDJDM0REMDRBODMxMUUxQjQ3QUNDRTk0ODNBMjY0RiIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PmY2mEcAAAGAUExURevr7Jydn5ma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";
    private final static String LOG_HEADER = "IMAGE:HELPER";

    /* Image functions*/

   /* public static Bitmap ResizeBitmap(Bitmap bitmap, int width, int height)
    {
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, width, height, true);
        return bMapScaled;
    }*/

    /*public static RoundImage RoundBitmap(String dataUri)
    {
        Bitmap temp = ResizeBitmap(parseImageUri(dataUri),100,100);
        RoundImage roundedImage = new RoundImage(temp);
        return roundedImage;
    }*/

    public static Bitmap UriToBitmap(String uri) {
        //gets bitmap from encoded data of Uri string
        byte[] arr = Base64.decode(uri.split(",", 2)[1], Base64.DEFAULT);
        // ByteArrayInputStream inputStream = new ByteArrayInputStream (arr);
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    public static Bitmap RoundBitmap(String uri) {
        return RoundBitmap(UriToBitmap(uri));
    }

    public static Bitmap ResizeBitmap(String uri, int width, int height) {
        Bitmap bitmap = UriToBitmap(uri);
        return Bitmap.createScaledBitmap(bitmap, width, width, true);
    }

    public static Bitmap ResizeBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, width, true);
    }

    public static Bitmap IconBitmap(Bitmap bitmap) {
        bitmap = RoundBitmap(bitmap);
        return Bitmap.createScaledBitmap(bitmap, 148, 148, true);
    }

    public static Bitmap IconBitmap(String uri) {
        Bitmap bitmap = RoundBitmap(uri);
        return Bitmap.createScaledBitmap(bitmap, 148, 148, true);
    }

    public static Bitmap RoundBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 5;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /*public static Bitmap BitmapFromBitMatrix(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        int WHITE = 0xFF000000;
        int BLACK = 0xFFFFFFFF;
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? WHITE : BLACK;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }*/

    public static String UriToSmallUri(String uri) {
        return BitmapToSmallUri(UriToBitmap(uri));
    }

    public static String BitmapToSmallUri(Bitmap bitmap) {
        Bitmap smallImage = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    public static boolean isImageUri(String uri) {
        Pattern pattern = Pattern.compile("data:image\\/([a-zA-Z]*);base64,([^\\\"]*)");
        Matcher matcher = pattern.matcher(uri);
        return matcher.matches() ? true : false;
    }

    /*public static Bitmap parseImageUri(String dataUri)
    {
        //gets bitmap from encoded data of Uri string
        byte[] arr = Base64.decode(dataUri.split(",",2)[1], Base64.DEFAULT);
        // ByteArrayInputStream inputStream = new ByteArrayInputStream (arr);
        return BitmapFactory.decodeByteArray(arr,0,arr.length);
    }*/

  /*  public static String peakAtImage(Bitmap bitmap){
        return buildImageUri(bitmap).substring(0,200);
    }*/

   /* public static String buildImageUri(Bitmap bitmap)
    {
        //Resize image to 256 * 256
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaleBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP );
    }*/

    public static Bitmap onGalleryImageResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                // bm = MediaStore.Images.Media.getBitmap(this.getApplicationContext().getContentResolver(), data.getData());
                bm = data.getExtras().getParcelable("data");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

    private static Bitmap onCameraImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnail;
    }
}
