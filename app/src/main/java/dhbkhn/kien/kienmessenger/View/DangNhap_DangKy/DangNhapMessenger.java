package dhbkhn.kien.kienmessenger.View.DangNhap_DangKy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import dhbkhn.kien.kienmessenger.Model.DangNhap_DangKy.DangNhapFacebookGoogle;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.Presenter.DangNhap_DangKy.SharePreferenceTrangThai;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class DangNhapMessenger extends AppCompatActivity {
    EditText edtEmailLogin, edtIdDangNhap;
    EditText edtPassLogin;
    Button btnLogin, btnRegister, btnClose, btnDangNhapFacebook;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    CallbackManager callbackManager;
    DangNhapFacebookGoogle dangNhap;
    ProgressDialog prd;

    public static String nguoidung = "";
    public static String emailnguoidung = "";

    SharePreferenceTrangThai sharePreferenceTrangThai;
    FirebaseAuth.AuthStateListener mListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                List<String> ds = sharePreferenceTrangThai.layUserPass(DangNhapMessenger.this);
                if (!ds.get(0).equals("") && !ds.get(1).equals("") && !ds.get(2).equals("")) {
                    gotoTrangChu(ds.get(1),ds.get(0));
                }else {
                    edtEmailLogin.setVisibility(View.GONE);
                    edtIdDangNhap.setVisibility(View.GONE);
                    edtPassLogin.setVisibility(View.GONE);
                    btnRegister.setVisibility(View.GONE);
                    btnLogin.setText("Đăng xuất");
                }
            }
            else {
                edtEmailLogin.setVisibility(View.VISIBLE);
                edtIdDangNhap.setVisibility(View.VISIBLE);
                edtPassLogin.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
                btnLogin.setText("Đăng nhập");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_dang_nhap_messenger);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtIdDangNhap = (EditText) findViewById(R.id.edtIdDangNhap);
        edtPassLogin = (EditText) findViewById(R.id.edtPassDangNhap);
        edtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        btnLogin = (Button) findViewById(R.id.btnAgreeLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnDangNhapFacebook = (Button) findViewById(R.id.btnDangNhapFacebook);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharePreferenceTrangThai = new SharePreferenceTrangThai();

        prd = new ProgressDialog(this);
        dangNhap = new DangNhapFacebookGoogle();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(onListener);
        btnRegister.setOnClickListener(onListener);
        btnClose.setOnClickListener(onListener);
        btnDangNhapFacebook.setOnClickListener(onListener);
    }



    private View.OnClickListener onListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAgreeLogin:
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        mAuth.signOut();
                    }
                    else {
                        String email = edtEmailLogin.getText().toString().trim();
                        String username = edtIdDangNhap.getText().toString().trim();
                        String pass = edtPassLogin.getText().toString().trim();

                        if (nhapDuMucChua(email, username, pass)) {
                            prd.setTitle("Đang thực hiện đăng nhập");
                            prd.setMessage("Vui lòng chờ...");
                            prd.show();
                            signInAccount(email,username,pass);
                        }else {
                            Toast.makeText(DangNhapMessenger.this, "Bạn cần nhập đủ 3 mục!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.btnRegister:
                    Intent iDangKy = new Intent(DangNhapMessenger.this, DangKyTaiKhoan.class);
                    startActivity(iDangKy);
                    finish();
                    break;
                case R.id.btnClose:
                    finish();
                    break;
                case R.id.btnDangNhapFacebook:
                    LoginManager.getInstance().logInWithReadPermissions(DangNhapMessenger.this, Arrays.asList("public_profile"));
                    break;
            }
        }
    };

    private boolean nhapDuMucChua(String email, String username, String pass){
        if (email == null || username == null || pass == null
                || email.equals("") || username.equals("") || pass.equals("")) {
            return false;
        }else {
            return true;
        }
    }

    private void gotoTrangChu(String username, String email){
        Intent iTrangChu = new Intent(DangNhapMessenger.this, TrangChu.class);
        nguoidung = username;
        emailnguoidung = email;
        startActivity(iTrangChu);
    }

    private void saveUserToDatabase(String username, String pass) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        User user = new User();
        user.setEmail(currentUser.getEmail());
        user.setUsername(username);
        user.setPassword(pass);
        user.setPhone("099999999");

        mDatabase.child("users").child(user.getUsername()).updateChildren(user.toMap());
        mDatabase.child("users").child(username).child("online").setValue(true);
        gotoTrangChu(username,user.getEmail());
    }

    private void signInAccount(final String email, final String username, final String pass){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sharePreferenceTrangThai.capNhatUserPass(DangNhapMessenger.this,email,username,pass);
                    saveUserToDatabase(username,pass);
                    prd.dismiss();
                    Toast.makeText(DangNhapMessenger.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DangNhapMessenger.this, "Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
