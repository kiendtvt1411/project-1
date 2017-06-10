package dhbkhn.kien.kienmessenger.Model.TrangChu;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

/**
 * Created by kiend on 10/18/2016.
 */
public class XuLyCapNhatNguoiDung {
    public void capNhatAvatarNguoiDung(final String nguoiDung) {
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference ref = storageRef.child("hinh_anh").child("trang_ca_nhan").child(nguoiDung).child("anh_dai_dien.jpg");
        ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                databaseRef.child("users").child(nguoiDung).child("avatar_url").setValue(storageMetadata.getDownloadUrl()+"");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ref", "that bai!");
            }
        });
    }
}
