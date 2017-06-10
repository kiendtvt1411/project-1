package dhbkhn.kien.kienmessenger.Adapter.ChatNhom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by kiend on 10/20/2016.
 */
public class AsyncTaskHienThiAnh extends AsyncTask<String, Bitmap, Void>{
    ImageView img;
    Bitmap bitmap = null;

    public AsyncTaskHienThiAnh(ImageView img) {
        this.img = img;
    }

    @Override
    protected Void doInBackground(String... params) {
        String url = params[0];
        if (url != null) {
            StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
            final long MEGE_BYTE = 1024 * 1024;
            ref.getBytes(MEGE_BYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    try {
                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        publishProgress(bitmap);
                    } catch (Exception ex) {
                        Log.d("myError", ex.toString());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        super.onProgressUpdate(values);
        bitmap = values[0];
        img.setImageBitmap(bitmap);
    }
}
