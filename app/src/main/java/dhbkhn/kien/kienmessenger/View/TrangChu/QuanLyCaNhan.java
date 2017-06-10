package dhbkhn.kien.kienmessenger.View.TrangChu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Presenter.TrangChu.CapNhatNguoiDung.PresenterTrangChu;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.ThemBanChat;

public class QuanLyCaNhan extends AppCompatActivity implements ITrangChu{
    ImageView imgAvatarQuanLyTaiKhoan;
    TextView tvUsernameQuanLyTaiKhoan, tvEditDisplayName, tvShowDisplayName;
    EditText edtShowDisplayName;
    CardView cvChinhSuaCaNhan, cvQuyenAdmin, cvThemBanMoi;
    LinearLayout llMoRongChinhSuaCaNhan;
    DatabaseReference mDatabase;
    String nguoidung="";
    String emailnguoidung="";
    boolean isEdit = false;
    boolean editDisplayName = false;
    ImageView imgHoanThienDangKy;
    StorageReference mStorage;
    ProgressDialog pr;
    Dialog dialog;
    HienThiBitmap hienThi;
    PresenterTrangChu presenterTrangChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ca_nhan);
        addCOntrols();
        addEvents();
    }

    private void addCOntrols() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imgAvatarQuanLyTaiKhoan = (ImageView) findViewById(R.id.imgAvatarQuanLyTaiKhoan);
        tvUsernameQuanLyTaiKhoan = (TextView) findViewById(R.id.tvUsernameQuanLyTaiKhoan);
        tvShowDisplayName = (TextView) findViewById(R.id.tvShowDisplayName);
        edtShowDisplayName = (EditText) findViewById(R.id.edtShowDisplayName);
        tvEditDisplayName = (TextView) findViewById(R.id.tvEditDisplayName);
        cvChinhSuaCaNhan = (CardView) findViewById(R.id.cvChinhSuaCaNhan);
        cvQuyenAdmin = (CardView) findViewById(R.id.cvQuyenAdmin);
        cvThemBanMoi = (CardView) findViewById(R.id.cvThemBanMoi);
        llMoRongChinhSuaCaNhan = (LinearLayout) findViewById(R.id.llMoRongChinhSuaCaNhan);

        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
    }

    private void addEvents() {
        hienThi = new HienThiBitmap(QuanLyCaNhan.this);
        mStorage = FirebaseStorage.getInstance().getReference();
        presenterTrangChu = new PresenterTrangChu(this);
        cvChinhSuaCaNhan.setOnClickListener(myCLick);
        cvQuyenAdmin.setOnClickListener(myCLick);
        cvThemBanMoi.setOnClickListener(myCLick);
        tvEditDisplayName.setOnClickListener(myCLick);
        imgAvatarQuanLyTaiKhoan.setOnClickListener(myCLick);
        mDatabase.child("users").child(nguoidung).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User s = dataSnapshot.getValue(User.class);
                String url = s.getAvatar_url();
                hienThiAnh(url,imgAvatarQuanLyTaiKhoan);
                tvUsernameQuanLyTaiKhoan.setText(s.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void hienThiAnh(String url, ImageView imageView) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(imageView);
        task.execute(url);
    }

    private View.OnClickListener myCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vt = v.getId();
            switch (vt) {
                case R.id.cvChinhSuaCaNhan:
                    isEdit = !isEdit;
                    if (isEdit) {
                        llMoRongChinhSuaCaNhan.setVisibility(View.VISIBLE);
                    }else {
                        llMoRongChinhSuaCaNhan.setVisibility(View.GONE);
                    }
                    break;
                case R.id.cvQuyenAdmin:
                    Intent iAdmin = new Intent(QuanLyCaNhan.this, QuyenCuaAdmin.class);
                    startActivity(iAdmin);
                    break;
                case R.id.cvThemBanMoi:
                    Intent iAddFr = new Intent(QuanLyCaNhan.this,ThemBanChat.class);
                    startActivity(iAddFr);
                    break;
                case R.id.tvEditDisplayName:
                    editDisplayName = !editDisplayName;
                    if (editDisplayName) {
                        tvShowDisplayName.setVisibility(View.GONE);
                        edtShowDisplayName.setVisibility(View.VISIBLE);
                    }else {
                        tvShowDisplayName.setVisibility(View.VISIBLE);
                        tvShowDisplayName.setText(edtShowDisplayName.getText().toString().trim());
                        edtShowDisplayName.setVisibility(View.GONE);
                    }
                    break;
                case R.id.imgAvatarQuanLyTaiKhoan:
                    hienThiTuyChonAvatar();
                    break;
            }
        }
    };

    @Override
    public void capNhatAvatar() {
        Toast.makeText(QuanLyCaNhan.this, "Cập nhật ảnh đại diện thành công!!!", Toast.LENGTH_SHORT).show();
    }

    private void hienThiTuyChonAvatar(){
        dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(true);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_lan_dau_dang_ky, null);
        imgHoanThienDangKy = (ImageView) row.findViewById(R.id.imgHoanThienDangKy);
        Button btnChonAnhDangKy = (Button) row.findViewById(R.id.btnChonAnhDangKy);
        Button btnTiepTucDangKy = (Button) row.findViewById(R.id.btnTiepTucDangKy);
        Button btnBoQuaDangKy = (Button) row.findViewById(R.id.btnBoQuaDangKy);

        btnChonAnhDangKy.setOnClickListener(myClick);
        btnTiepTucDangKy.setOnClickListener(myClick);
        btnBoQuaDangKy.setOnClickListener(myClick);

        int divierId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider",   null, null);
        View divider = dialog.findViewById(divierId);
        if(divider!=null){
            divider.setBackgroundColor(getResources().getColor(R.color.transparent_dialog));}
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(row);
        dialog.show();
    }

    public Drawable layAnh(int idDw){
        Drawable drawble;
        if (Build.VERSION.SDK_INT > 21) {
            drawble = ContextCompat.getDrawable(QuanLyCaNhan.this, idDw);
        }else {
            drawble = getResources().getDrawable(idDw);
        }
        return drawble;
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnChonAnhDangKy:
                    openGallery();
                    break;
                case R.id.btnTiepTucDangKy:
                    uploadAnhDaiDien();
                    break;
                case R.id.btnBoQuaDangKy:
                    dialog.dismiss();
                    break;
            }
        }
    };

    private void uploadAnhDaiDien(){
        Bitmap bm = null;
        BitmapDrawable draw = (BitmapDrawable)imgHoanThienDangKy.getDrawable();
        if (draw != null) {
            bm = draw.getBitmap();
            if (bm != null) {
                pr = new ProgressDialog(this);
                pr.setTitle("Đang cập nhật ảnh...");
                pr.setMessage("Vui lòng chờ...");
                pr.show();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] array = baos.toByteArray();
                StorageReference ref = mStorage.child("hinh_anh").child("trang_ca_nhan")
                        .child(nguoidung).child("anh_dai_dien.jpg");
                ref.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pr.dismiss();
                        Toast.makeText(QuanLyCaNhan.this, "Cập nhật ảnh đại diện thành công!", Toast.LENGTH_SHORT).show();
                        presenterTrangChu.xuLyHienThiAvatar(nguoidung);
                        dialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pr.dismiss();
                        Toast.makeText(QuanLyCaNhan.this, "Chưa cập nhật được!!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        }
        else {
            Toast.makeText(QuanLyCaNhan.this, "Bạn chưa chọn ảnh mà!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri uri = data.getData();
            String path = hienThi.getGalleryPath(uri);
            Bitmap bm = hienThi.thumbNail(path);
            imgHoanThienDangKy.setImageBitmap(bm);
        }
    }
}
