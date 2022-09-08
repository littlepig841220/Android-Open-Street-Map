package cbs.example.openstreetmap.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import cbs.example.openstreetmap.R;

public class CustomIcon {
    Context context;
    public CustomIcon(Context context) {
        this.context = context;
    }

    public Bitmap compositePicture(int drawableID) {
        //bitmapFactoryOption
        BitmapFactory.Options bitmapFactoryOption = new BitmapFactory.Options();
        bitmapFactoryOption.inScaled = false;

        //Load image from drawable
        Bitmap bitmapOriginal = BitmapFactory.decodeResource(context.getResources(), drawableID, bitmapFactoryOption);
        int width = bitmapOriginal.getWidth();
        int height = bitmapOriginal.getHeight();

        //Create canvas
        Bitmap bitmapCrop = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCrop);
        Paint paint = new Paint();
        Rect rect = new Rect(0,0, width, height);

        //Crop image
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(width >> 1, height >> 1, height >> 1, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapOriginal, rect, rect, paint);

        //Resize image
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmapCrop, width/2 - height /2, 0, height, height);
        int newWidth = 700;
        int newHeight = 700;
        float scaleWidth = ((float) newWidth) / resizeBitmap.getWidth();
        float scaleHeight = ((float) newHeight) / resizeBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap1 = Bitmap.createBitmap(resizeBitmap, 0, 0, resizeBitmap.getWidth(), resizeBitmap.getHeight(), matrix, true);

        //Load the marker mask
        Bitmap bitmapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker, bitmapFactoryOption);
        Bitmap bitmapFinal = Bitmap.createBitmap(bitmapIcon.getWidth(), bitmapIcon.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmapFinal);

        //Composite picture
        canvas1.drawBitmap(bitmapIcon, new Matrix(), null);
        canvas1.drawBitmap(bitmap1, 30, 30, null);

        return bitmapFinal;
    }
}
