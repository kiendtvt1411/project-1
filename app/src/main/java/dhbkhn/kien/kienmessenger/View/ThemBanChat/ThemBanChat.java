package dhbkhn.kien.kienmessenger.View.ThemBanChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.Notification;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.Map.DanhSachBanQuanhDay;
import dhbkhn.kien.kienmessenger.View.Map.MapsActivity;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class ThemBanChat extends AppCompatActivity{
    Button btnAddFr;
    ImageButton btnQuetMap, btnQuetBan;
    String nguoidung = "";
    String email = "";
    DatabaseReference mDatabase;

    AutoCompleteTextView edtAddFrByName, edtAddFrByPhone;
    TextInputLayout input_edtAddFrByName, input_edtAddFrByPhone;

    int tongTB = 0;
    int tbChuaXem = 0;
    boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ban_chat);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtAddFrByName = (AutoCompleteTextView) findViewById(R.id.edtAddFrByName);
        edtAddFrByPhone = (AutoCompleteTextView) findViewById(R.id.edtAddFrByPhone);
        input_edtAddFrByName = (TextInputLayout) findViewById(R.id.input_edtAddFrByName);
        input_edtAddFrByPhone = (TextInputLayout) findViewById(R.id.input_edtAddFrByPhone);
        btnAddFr = (Button) findViewById(R.id.btnAddFriend);
        btnQuetMap = (ImageButton) findViewById(R.id.btnQuetMap);
        btnQuetBan = (ImageButton) findViewById(R.id.btnQuetBan);

        nguoidung = DangNhapMessenger.nguoidung;
        email = DangNhapMessenger.emailnguoidung;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void addEvents() {
        edtAddFrByName.setOnFocusChangeListener(myFocus);
        edtAddFrByPhone.setOnFocusChangeListener(myFocus);
        btnAddFr.setOnClickListener(myClick);
        btnQuetMap.setOnClickListener(myClick);
        btnQuetBan.setOnClickListener(myClick);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAddFriend:
                    String friend = edtAddFrByName.getText().toString().trim();
                    kiemTraDaLaBanBeChua(friend);
                    break;
                case R.id.btnQuetMap:
                    Intent iMap = new Intent(ThemBanChat.this, MapsActivity.class);
                    startActivity(iMap);
                    break;
                case R.id.btnQuetBan:
                    Intent iBan = new Intent(ThemBanChat.this, DanhSachBanQuanhDay.class);
                    startActivity(iBan);
                    break;
            }
        }
    };

    private void kiemTraDaLaBanBeChua(final String friend) {
        mDatabase.child("users").child(nguoidung).child("friendlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Friend f = post.getValue(Friend.class);
                    if (friend.equals(f.getUsername_friend())) {
                        Toast.makeText(ThemBanChat.this, "Đã là bạn bè với " + f.getUsername_friend(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                layRaSoLuong(friend);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inItNotify(String nguoidung, String friend, int tong, int chuaxem) {
        Notification notification = new Notification();
        notification.setAuthor(nguoidung);
        notification.setEmail(email);
        notification.setContent(nguoidung);
        notification.setConsequence(false);
        Map<String,Object>hmN = notification.toMap();

        String key = mDatabase.child("notification").child(friend).child("ket_ban").push().getKey();
        Map<String, Object> hmUp = new HashMap<>();
        hmUp.put("/notification/" + friend + "/ket_ban/" + key, hmN);

        DuLieuThongBao duLieuThongBao = new DuLieuThongBao();
        duLieuThongBao.setFriend(friend);
        duLieuThongBao.setTongTB(tong);
        duLieuThongBao.setTbChuaXem(chuaxem);
        Map<String,Object>hmDL = duLieuThongBao.toMap();
        hmUp.put("/notification/statistic/"+friend+"/ket_ban", hmDL);

        mDatabase.updateChildren(hmUp);
        Toast.makeText(ThemBanChat.this, "Bạn đã gửi lời kết bạn tới " + friend, Toast.LENGTH_SHORT).show();
        Intent iTrangChu = new Intent(ThemBanChat.this, TrangChu.class);
        startActivity(iTrangChu);
    }

    private void layRaSoLuong(final String friend) {
        if (friend != null && !friend.equals("")) {
            if(!friend.equals(nguoidung)) {
                mDatabase.child("notification").child("statistic")
                        .child(friend).child("ket_ban").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DuLieuThongBao dl = dataSnapshot.getValue(DuLieuThongBao.class);
                        if (dl != null) {
                            tongTB = dl.getTongTB();
                            tbChuaXem = dl.getTbChuaXem();
                        }
                        inItNotify(nguoidung, friend, ++tongTB, ++tbChuaXem);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else {
                Toast.makeText(ThemBanChat.this, "Bạn không thể tự kết bạn với chính mình!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private View.OnFocusChangeListener myFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            int id = v.getId();
            switch (id) {
                case R.id.edtAddFrByName:
                    if (!hasFocus) {
                        String chuoi = ((AutoCompleteTextView) v).getText().toString().trim();
                        if (chuoi == null || chuoi.equals("")) {
                            input_edtAddFrByName.setErrorEnabled(true);
                            input_edtAddFrByName.setError("Bạn chưa nhập gì cả!");
                            validate = false;
                        }else {
                            input_edtAddFrByName.setErrorEnabled(false);
                            input_edtAddFrByName.setError("");
                            validate = true;
                            layRaSoLuong(edtAddFrByName.getText().toString().trim());
                        }
                    }else {
                        edtAddFrByPhone.setText("");
                    }
                    break;
                case R.id.edtAddFrByPhone:
                    if (!hasFocus) {
                        String chuoi = ((AutoCompleteTextView) v).getText().toString().trim();
                        if (chuoi == null || chuoi.equals("")) {
                            input_edtAddFrByPhone.setErrorEnabled(true);
                            input_edtAddFrByPhone.setError("Dãy số phải bắt đầu từ số 0!");
                        }else {
                            input_edtAddFrByPhone.setErrorEnabled(false);
                            input_edtAddFrByPhone.setError("");
                        }
                    }else {
                        edtAddFrByName.setText("");
                    }
                    break;
            }
        }
    };
}
