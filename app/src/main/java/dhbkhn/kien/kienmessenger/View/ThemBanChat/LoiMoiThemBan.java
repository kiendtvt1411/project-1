package dhbkhn.kien.kienmessenger.View.ThemBanChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class LoiMoiThemBan extends AppCompatActivity {
    TextView tvHomeMoiKetBan, tvPhoneMoiKetBan, tvEmailMoiKetBan, tvXemThongTinMoiKetBan;
    Button btnChapNhanKetBan, btnTuChoiKetBan;
    String nguoidung = "";
    String emailFriend = "";
    String emailNguoiDung = "";
    String friend = "";
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loi_moi_them_ban);
        addControls();
        addEvents();
    }

    private void addControls() {
        tvHomeMoiKetBan = (TextView) findViewById(R.id.tvHomeMoiKetBan);
        tvPhoneMoiKetBan = (TextView) findViewById(R.id.tvPhoneMoiKetBan);
        tvEmailMoiKetBan = (TextView) findViewById(R.id.tvEmailMoiKetBan);
        tvXemThongTinMoiKetBan = (TextView) findViewById(R.id.tvXemThongTinMoiKetBan);

        btnChapNhanKetBan = (Button) findViewById(R.id.btnChapNhanKetBan);
        btnTuChoiKetBan = (Button) findViewById(R.id.btnTuChoiKetBan);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nguoidung = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;
        friend = getIntent().getStringExtra("friend");
        emailFriend = getIntent().getStringExtra("emailfriend");
    }

    private void addEvents() {
        mDatabase.child("users").child(friend).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    String name = user.getUsername();
                    String email = user.getEmail();
                    String phone = user.getPhone();

                    tvHomeMoiKetBan.setText(name);
                    tvEmailMoiKetBan.setText(email);
                    tvPhoneMoiKetBan.setText(phone);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tvXemThongTinMoiKetBan.setOnClickListener(myOnClick);
        btnChapNhanKetBan.setOnClickListener(myOnClick);
        btnTuChoiKetBan.setOnClickListener(myOnClick);
    }

    private View.OnClickListener myOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tvXemThongTinMoiKetBan:
                    Intent iXem = new Intent(LoiMoiThemBan.this, XemTrangCaNhanCuaNguoiKhac.class);
                    iXem.putExtra("friend", friend);
                    iXem.putExtra("emailfriend", emailFriend);
                    iXem.putExtra("banbe", "chua_la_ban_be");
                    startActivity(iXem);
                    break;
                case R.id.btnChapNhanKetBan:
                    chapNhanKetBan(nguoidung,emailNguoiDung,friend,emailFriend);
                    mDatabase.child("notification").child(nguoidung).child("ket_ban")
                            .orderByChild("author").equalTo(friend).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot post : dataSnapshot.getChildren()) {
                                String key = post.getKey();
                                mDatabase.child("notification").child(nguoidung).child("ket_ban")
                                        .child(key).child("consequence").setValue(true);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mDatabase.child("mess_chat_doi").child(nguoidung).child(friend).child("so_luong_tin_nhan_chua_xem").setValue(0);
                    mDatabase.child("mess_chat_doi").child(friend).child(nguoidung).child("so_luong_tin_nhan_chua_xem").setValue(0);
                    Intent iTrangChu = new Intent(LoiMoiThemBan.this, TrangChu.class);
                    startActivity(iTrangChu);
                    break;
                case R.id.btnTuChoiKetBan:
                    Intent iTrangChu2 = new Intent(LoiMoiThemBan.this, TrangChu.class);
                    startActivity(iTrangChu2);
                    break;
            }
        }
    };

    private void chapNhanKetBan(String user1, String email1, String user2, String email2){
        String key = mDatabase.child("users").child(user1).push().getKey();
        String key2 = mDatabase.child("users").child(user2).push().getKey();

        Friend f1 = new Friend(email1, user1);
        Map<String,Object>map1 = f1.toMap();

        Friend f2 = new Friend(email2, user2);
        Map<String,Object>map2 = f2.toMap();

        Map<String, Object> hmUp = new HashMap<>();
        hmUp.put("/users/" + user1 + "/friendlist/" + key, map2);
        hmUp.put("/users/" + user2 + "/friendlist/" + key2, map1);
        mDatabase.updateChildren(hmUp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent iTrangChu2 = new Intent(LoiMoiThemBan.this, TrangChu.class);
        startActivity(iTrangChu2);
    }
}
