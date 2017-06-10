package dhbkhn.kien.kienmessenger.View.DangNhap_DangKy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class DangNhap extends AppCompatActivity {
    EditText edtEmail, edtPassword, edtUsername, edtPhone, edtEmailFriend,edtNameFriend;
    TextView tvDisplayID, tvDisplayEmail;
    Button btnSignin, btnCreate;
    boolean isVali = false;
    String nguoiDung="";
    String friend = "";
    String emailNguoiDung ="";
    String emailFriend="";

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
//    FirebaseAuth.AuthStateListener mFirebaseListener = new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser user = mAuth.getCurrentUser();
//            if (user != null) {
//                tvDisplayEmail.setText(user.getEmail());
//                tvDisplayID.setText(user.getUid());
//                tvDisplayEmail.setVisibility(View.VISIBLE);
//                tvDisplayID.setVisibility(View.VISIBLE);
//
//                edtEmail.setVisibility(View.INVISIBLE);
//                edtPassword.setVisibility(View.INVISIBLE);
//                btnCreate.setVisibility(View.INVISIBLE);
//                btnSignin.setText("SIGN OUT");
//            }else {
//                tvDisplayEmail.setText("Fail");
//                edtEmail.setVisibility(View.VISIBLE);
//                edtPassword.setVisibility(View.VISIBLE);
//                btnCreate.setVisibility(View.VISIBLE);
//                tvDisplayEmail.setVisibility(View.INVISIBLE);
//                tvDisplayID.setVisibility(View.INVISIBLE);
//                btnSignin.setText("SIGN IN");
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        addControls();
        addEvents();
    }

    private void addControls() {
        mAuth = FirebaseAuth.getInstance();

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPw);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtEmailFriend = (EditText) findViewById(R.id.edtEmailFriend);
        edtNameFriend = (EditText) findViewById(R.id.edtNameFriend);

        tvDisplayEmail = (TextView) findViewById(R.id.tvDisplayEmail);
        tvDisplayID = (TextView) findViewById(R.id.tvDisplayID);

        btnSignin = (Button) findViewById(R.id.btnSignIn);
        btnCreate = (Button) findViewById(R.id.btnCreate);
    }

    private void addEvents() {
        btnSignin.setOnClickListener(myBtnClickListener);
        btnCreate.setOnClickListener(myBtnClickListener);
    }

    private View.OnClickListener myBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.btnSignIn:
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        mAuth.signOut();
                    }
                    else {
                        String email = edtEmail.getText().toString().trim();
                        String password = edtPassword.getText().toString().trim();
                        isVali = isValidate(email, password);
                        if (isVali) {
                            signInApp(email,password);
                        }else {
                            Toast.makeText(DangNhap.this, "Bạn cần nhập đủ 2 mục!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case R.id.btnCreate:
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();

                    isVali = isValidate(email,password);
                    if(isVali){
                        createUser(email,password);
                    }else {
                        Toast.makeText(DangNhap.this, "Bạn cần nhập đủ 2 mục!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private boolean isValidate(String email, String pass) {
        if (email != null && pass != null) {
            return true;
        }else {
            return false;
        }
    }

    private void signInApp(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //trả về task để xem trạng thái thành công hay thất bại
                if (task.isSuccessful()) {
                    saveUserToDatabase();
                    Toast.makeText(DangNhap.this, "Bạn đã đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DangNhap.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //trả về task để xem trạng thái thành công hay thất bại
                if (task.isSuccessful()) {
                    saveUserToDatabase();
                    Toast.makeText(DangNhap.this, "Bạn đã đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DangNhap.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUserToDatabase(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        User user = new User();
        nguoiDung = edtUsername.getText().toString().trim();
        user.setUsername(nguoiDung);
        user.setEmail(currentUser.getEmail());
        emailNguoiDung = user.getEmail();
        user.setPassword(edtPassword.getText().toString().trim());
        user.setPhone(edtPhone.getText().toString().trim());

        mDatabase.child("users").child(user.getUsername()).setValue(user);

        //Luôn phải push thêm một key không trùng lặp vào, ở đây là biến key
        Friend fr = new Friend();
        fr.setEmail_friend(edtEmailFriend.getText().toString().trim());
        fr.setUsername_friend(edtNameFriend.getText().toString().trim());
        emailFriend=fr.getEmail_friend();
        Map<String,Object> hmF = fr.toMap();
        Map<String,Object> hm;

        friend = edtNameFriend.getText().toString().trim();
        hm = autoAddFriend(user.getUsername(),hmF);

        Friend fr2 = new Friend();
        fr2.setEmail_friend("phuc@gmail.com");
        fr2.setUsername_friend("phucham");
        Map<String,Object>hmF2 = fr2.toMap();
        String key2 = mDatabase.child("users").child(user.getUsername()).child("friendlist").push().getKey();
        hm.put("/users/"+user.getUsername()+"/friendlist/"+key2,hmF2);
        mDatabase.updateChildren(hm);

        goToChat();
    }

    private Map<String,Object> autoAddFriend(String username, Map<String,Object>hmF){
        Map<String,Object> hm = new HashMap<>();
        String key = mDatabase.child("users").child(username).child("friendlist").push().getKey();
        hm.put("/users/"+username+"/friendlist/"+key,hmF);
        return hm;
    }

    private void goToChat() {
        Intent iTrangChu = new Intent(DangNhap.this, TrangChu.class);
        iTrangChu.putExtra("nguoidung",nguoiDung);
        iTrangChu.putExtra("friend",friend);
        iTrangChu.putExtra("emailnguoidung",emailNguoiDung);
        iTrangChu.putExtra("emailfriend",emailFriend);
        startActivity(iTrangChu);
    }


    @Override
    protected void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(mFirebaseListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mAuth.removeAuthStateListener(mFirebaseListener);
    }
}
