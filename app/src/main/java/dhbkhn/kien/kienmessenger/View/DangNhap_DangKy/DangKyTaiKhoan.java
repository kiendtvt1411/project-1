package dhbkhn.kien.kienmessenger.View.DangNhap_DangKy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import dhbkhn.kien.kienmessenger.CustomView.ClearEditText;
import dhbkhn.kien.kienmessenger.CustomView.PasswordEditText;
import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;
import dhbkhn.kien.kienmessenger.Model.Object.SearchObject;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.Presenter.DangNhap_DangKy.SharePreferenceTrangThai;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.TrangChu.ITrangChu;

public class DangKyTaiKhoan extends AppCompatActivity implements ITrangChu {
    Toolbar toolbar;
    TextInputLayout input_edtHoTenDK,input_edtEmailDK,input_edtMatKhauDK, input_edtNhapLaiMatKhauDK;
    ClearEditText edtHoTenDK, edtEmailDK;
    PasswordEditText edtMatKhauDK, edtNhapLaiMatKhauDK;
    Button btnDangKy;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProgressDialog pr;
    SharePreferenceTrangThai sharePreferenceTrangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_tai_khoan);
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        input_edtHoTenDK = (TextInputLayout) findViewById(R.id.input_edtHoTenDK);
        input_edtEmailDK = (TextInputLayout) findViewById(R.id.input_edtEmailDK);
        input_edtMatKhauDK = (TextInputLayout) findViewById(R.id.input_edtMatKhauDK);
        input_edtNhapLaiMatKhauDK = (TextInputLayout) findViewById(R.id.input_edtNhapLaiMatKhauDK);
        edtHoTenDK = (ClearEditText) findViewById(R.id.edtHoTenDK);
        edtEmailDK = (ClearEditText) findViewById(R.id.edtEmailDK);
        edtMatKhauDK = (PasswordEditText) findViewById(R.id.edtMatKhauDK);
        edtNhapLaiMatKhauDK = (PasswordEditText) findViewById(R.id.edtNhapLaiMatKhauDK);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        pr = new ProgressDialog(DangKyTaiKhoan.this);
        pr.setTitle("Đang tạo tài khoản");
        pr.setMessage("Vui lòng chờ trong giây lát...");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharePreferenceTrangThai = new SharePreferenceTrangThai();
    }

    private void addEvents() {
        btnDangKy.setOnClickListener(myClick);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDangKy:
                    pr.show();
                    String email = edtEmailDK.getText().toString().trim();
                    String username = edtHoTenDK.getText().toString().trim();
                    String pass = edtMatKhauDK.getText().toString().trim();
                    creatAccount(email,username,pass);
                    break;
            }
        }
    };

    private void creatAccount(String email, final String username,final String pass){
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    saveUserToDatabase(username,pass);
                }
                else {
                    Toast.makeText(DangKyTaiKhoan.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserToDatabase(final String username, final String pass) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final User user = new User();
        user.setEmail(currentUser.getEmail());
        user.setUsername(username);
        user.setPassword(pass);
        user.setPhone("099999999");

        Bitmap ava = BitmapFactory.decodeResource(getResources(),
                R.drawable.avatar_default);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ava.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] array = baos.toByteArray();
        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        StorageReference ref = mStorage.child("hinh_anh").child("trang_ca_nhan")
                .child(username).child("anh_dai_dien.jpg");
        ref.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mDatabase.child("users").child(user.getUsername()).updateChildren(user.toMap());
                final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                StorageReference ref = storageRef.child("hinh_anh").child("trang_ca_nhan").child(username).child("anh_dai_dien.jpg");
                ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        databaseRef.child("users").child(username).child("avatar_url").setValue(storageMetadata.getDownloadUrl()+"");
                        mDatabase.child("users").child(username).child("online").setValue(true);
                        mDatabase.child("mess_chat_doi").child(username).child("tong_so_luong_tin_nhan_chua_xem").setValue(0);
                        DuLieuThongBao dltb = new DuLieuThongBao();
                        dltb.setFriend(username);
                        dltb.setTbChuaXem(0);
                        dltb.setTongTB(0);
                        mDatabase.child("notification").child("statistic").child(username).child("ket_ban").setValue(dltb);
                        mDatabase.child("notification").child("statistic").child(username).child("cong_dong").setValue(dltb);

                        SearchObject searchObject = new SearchObject();
                        searchObject.setDisplay_name(username);
                        searchObject.setType("user");
                        searchObject.setAvatar_url(storageMetadata.getDownloadUrl()+"");
                        searchObject.setKey("");
                        mDatabase.child("search").push().setValue(searchObject);

                        pr.dismiss();
                        gotoTrangChu(username,user.getEmail(), pass);
                        Toast.makeText(DangKyTaiKhoan.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ref", "that bai!");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void gotoTrangChu(String username, String email, String pass){
        Intent iTrangChu = new Intent(DangKyTaiKhoan.this, DangNhapMessenger.class);
        sharePreferenceTrangThai.capNhatUserPass(this,email, username, pass);
        startActivity(iTrangChu);
    }

    @Override
    public void capNhatAvatar() {

    }
}
