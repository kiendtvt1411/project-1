package dhbkhn.kien.kienmessenger.Model.TrangChu;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by kiend on 10/18/2016.
 */
public class HienThiBitmap {
    Context mContext;

    public HienThiBitmap(Context mContext) {
        this.mContext = mContext;
    }

    public String getGalleryPath(Uri uriImage) {
        String[] columnsPath = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uriImage, columnsPath, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(columnsPath[0]);
        String path = cursor.getString(columnIndex);
        cursor.close();
        return path;
    }

    public Bitmap thumbNail(String path) {
        BitmapFactory.Options bound = new BitmapFactory.Options();
        bound.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bound);
        if (bound.outWidth == -1 && bound.outHeight == -1) {
            return null;
        }
        int originalSize = (bound.outHeight>bound.outWidth)? bound.outHeight:bound.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize/500;
        return BitmapFactory.decodeFile(path, opts);
    }
}
