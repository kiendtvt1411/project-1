package dhbkhn.kien.kienmessenger.View.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Adapter.ChatDon.ChatDonAdapter;
import dhbkhn.kien.kienmessenger.Model.Object.ChatMessage;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.ShowLassMess;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.Presenter.DangNhap_DangKy.SharePreferenceTrangThai;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class ChatDoi extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    RecyclerView rvChat;
    ImageView imgSend;
    EditText edtChat;
    TextView tvTenBanChat,tvTrangThaiBanChat,tvUsernameCuaToi;
    LinearLayoutManager linearLayoutManager;
    LinearLayout llXemThongTinBanChat;
    ChatDonAdapter adapter;
    String nguoiDungChatDon ="";
    String tenBanChat = "";
    String emailNguoiDung = "";
    String emailFriend="";
    String trangthaiphong = "";
    SharePreferenceTrangThai sharePreferenceTrangThai;
    ShowLassMess showLassMess = new ShowLassMess();
    List<ChatMessage> dsChat;
    DrawerLayout drawerLayoutChatDoi;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    ValueEventListener valueBan;
    int tinnhanchuaxem = 0;
    int tongsotinnhanchuaxem = 0;
    int tncxnd = 0;
    int tstncxnd = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_doi);
        addControls();
        addEvents();
    }


    private void addControls() {
        drawerLayoutChatDoi = (DrawerLayout) findViewById(R.id.drawerLayoutChatDoi);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toggle = new ActionBarDrawerToggle(ChatDoi.this, drawerLayoutChatDoi, R.string.open, R.string.close);
        toggle.syncState();

        imgSend = (ImageView) findViewById(R.id.imgSend);
        edtChat = (EditText) findViewById(R.id.edtChat);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        llXemThongTinBanChat = (LinearLayout) findViewById(R.id.llXemThongTinBanChat);
        tvTenBanChat = (TextView) findViewById(R.id.tvTenBanChat);
        tvTrangThaiBanChat = (TextView) findViewById(R.id.tvTrangThaiBanChat);
        tvUsernameCuaToi = (TextView) findViewById(R.id.tvUsernameCuaToi);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        linearLayoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(linearLayoutManager);

        sharePreferenceTrangThai = new SharePreferenceTrangThai();

        nguoiDungChatDon = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;
        Intent iDB = getIntent();
        tenBanChat = iDB.getStringExtra("tenbanchat");
        emailFriend = iDB.getStringExtra("emailfriendchat");

        mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat)
                .child("so_luong_tin_nhan_chua_xem").addListenerForSingleValueEvent(laySoLuongChuaXemNguoiDung);
    }

    private void addEvents() {
        tvTenBanChat.setText(tenBanChat);
        tvUsernameCuaToi.setText(nguoiDungChatDon);

        mDatabase.child("users").child(tenBanChat).addValueEventListener(listenerOnline);

        mDatabase.child("mess_chat_doi").child(tenBanChat).child(nguoiDungChatDon).child("so_luong_tin_nhan_chua_xem")
                    .addValueEventListener(laySoLuongChuaXem);
        mDatabase.child("mess_chat_doi").child(tenBanChat).child("tong_so_luong_tin_nhan_chua_xem")
                .addValueEventListener(layTongSoLuongTinNhanChuaXem);

        mDatabase.child("mess_chat_doi").child(nguoiDungChatDon)
                .child(tenBanChat).child("trang_thai_phong").child(tenBanChat).addValueEventListener(myListenTrangThaiPhong);
        imgSend.setOnClickListener(btnListener);
        mDatabase.child("mess_chat_doi/"+nguoiDungChatDon+"/"+tenBanChat+"/mess_chung").addValueEventListener(listenerDsMess);
    }

    private ValueEventListener listenerOnline = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User u = dataSnapshot.getValue(User.class);
            if (u.isOnline()) {
                tvTrangThaiBanChat.setVisibility(View.VISIBLE);
                tvTrangThaiBanChat.setText("Đang hoạt động");
                mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat).child("mess_chung")
                        .orderByChild("author").startAt(emailNguoiDung).limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot post : dataSnapshot.getChildren()) {
                            ChatMessage chatMessage = post.getValue(ChatMessage.class);
                            String key = post.getKey();
                            if (chatMessage.getStatus().equals("Đã gửi")) {
                                mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat)
                                        .child("mess_chung").child(key).child("status").setValue("Đã nhận");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else {
                tvTrangThaiBanChat.setVisibility(View.VISIBLE);
                tvTrangThaiBanChat.setText("Đã ngừng hoạt động");
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener layTongSoLuongTinNhanChuaXemNguoiDung = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int tongsoluong = dataSnapshot.getValue(Integer.class);
            tstncxnd = tongsoluong;
            Log.d("tstncxnd",tstncxnd+"");
            int capnhatsoluong = tstncxnd - tncxnd;
            mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child("tong_so_luong_tin_nhan_chua_xem").setValue(capnhatsoluong);
            mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat).child("so_luong_tin_nhan_chua_xem").setValue(0);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener laySoLuongChuaXemNguoiDung = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int soluongchuaxem = dataSnapshot.getValue(Integer.class);
            tncxnd = soluongchuaxem;
            Log.d("tncxnd", tncxnd + "");
            mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child("tong_so_luong_tin_nhan_chua_xem")
                    .addListenerForSingleValueEvent(layTongSoLuongTinNhanChuaXemNguoiDung);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener layTongSoLuongTinNhanChuaXem = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int tongsoluong = dataSnapshot.getValue(Integer.class);
            tongsotinnhanchuaxem = tongsoluong;
            Log.d("tongsoluong",tongsotinnhanchuaxem+"");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener laySoLuongChuaXem = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int soluongchuaxem = dataSnapshot.getValue(Integer.class);
            tinnhanchuaxem = soluongchuaxem;
            Log.d("soluongtinchuaxem", tinnhanchuaxem + "");
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener listenerDsMess = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            dsChat = new ArrayList<ChatMessage>();
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                ChatMessage mess = post.getValue(ChatMessage.class);
                dsChat.add(mess);
            }

            adapter = new ChatDonAdapter(ChatDoi.this, dsChat, nguoiDungChatDon, emailNguoiDung);
            rvChat.setAdapter(adapter);
            rvChat.scrollToPosition(dsChat.size()-1);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener myListenTrangThaiPhong = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            trangthaiphong = dataSnapshot.getValue(String.class);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDrawer:
                if (drawerLayoutChatDoi.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayoutChatDoi.closeDrawer(Gravity.RIGHT);
                }
                else {
                    drawerLayoutChatDoi.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.itemVideoStream:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.imgSend:
                    submitChatContent();
                    edtChat.setText("");
                    break;
            }
        }
    };

    private void submitChatContent(){
        String chat = edtChat.getText().toString().trim();
        if(TextUtils.isEmpty(chat)){
            edtChat.setError("Bạn chưa nhập gì mà!");
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        final String uEmail = user.getEmail();
        final ChatMessage message = new ChatMessage();
        message.setAuthor(uEmail);
        message.setContent(chat);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        message.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));

        valueBan = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                if (u != null) {
                    if (u.isOnline()) {
                        if (trangthaiphong == null) {
                            message.setStatus("Đã nhận");
                            ++tinnhanchuaxem;
                            ++tongsotinnhanchuaxem;
                        } else if (trangthaiphong.equals("On")) {
                            message.setStatus("Đã xem");
                        }else {
                            message.setStatus("Đã nhận");
                            ++tinnhanchuaxem;
                            ++tongsotinnhanchuaxem;
                        }
                    }else {
                        message.setStatus("Đã gửi");
                        ++tinnhanchuaxem;
                        ++tongsotinnhanchuaxem;
                    }
                    Map<String,Object> hmM = message.toMap();

                    Map<String, Object>childUpdates = new HashMap<>();

                    String keyChung = mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat).push().getKey();
                    childUpdates.put("/mess_chat_doi/"+nguoiDungChatDon+"/"+tenBanChat+"/mess_chung/"+keyChung,hmM);
                    String keyChung2 = mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat).push().getKey();
                    childUpdates.put("/mess_chat_doi/"+tenBanChat+"/"+nguoiDungChatDon+"/mess_chung/"+keyChung2,hmM);

                    showLassMess.setMessage(message);

                    if(emailNguoiDung.equals(uEmail)){
                        childUpdates.put("/mess_chat_doi/"+nguoiDungChatDon+"/"+tenBanChat+"/mess_rieng/"+nguoiDungChatDon+"/"+keyChung,hmM);
                        childUpdates.put("/mess_chat_doi/"+tenBanChat+"/"+nguoiDungChatDon+"/mess_rieng/"+nguoiDungChatDon+"/"+keyChung2,hmM);
                    }
                    else {
                        childUpdates.put("/mess_chat_doi/"+tenBanChat+"/"+nguoiDungChatDon+"/mess_rieng/"+tenBanChat+"/"+keyChung,hmM);
                        childUpdates.put("/mess_chat_doi/"+nguoiDungChatDon+"/"+tenBanChat+"/mess_rieng/"+tenBanChat+"/"+keyChung2,hmM);
                    }
                    mDatabase.updateChildren(childUpdates);

                    showLassMess.setFriend(new Friend(emailFriend,tenBanChat));
                    mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child("show_last_mess").child(tenBanChat).setValue(showLassMess);

                    showLassMess.setFriend(new Friend(emailNguoiDung,nguoiDungChatDon));
                    mDatabase.child("mess_chat_doi").child(tenBanChat).child("show_last_mess").child(nguoiDungChatDon).setValue(showLassMess);

                    mDatabase.child("mess_chat_doi").child(tenBanChat).child(nguoiDungChatDon).child("so_luong_tin_nhan_chua_xem").setValue(tinnhanchuaxem);
                    mDatabase.child("mess_chat_doi").child(tenBanChat).child("tong_so_luong_tin_nhan_chua_xem").setValue(tongsotinnhanchuaxem);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.child("users").child(tenBanChat).addListenerForSingleValueEvent(valueBan);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ChatDoi.this, TrangChu.class);
        mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat).child("trang_thai_phong").child(nguoiDungChatDon).removeValue();
        mDatabase.child("mess_chat_doi").child(tenBanChat).child(nguoiDungChatDon).child("trang_thai_phong").child(nguoiDungChatDon).removeValue();
        mDatabase.removeEventListener(myListenTrangThaiPhong);
        mDatabase.removeEventListener(listenerDsMess);
        mDatabase.removeEventListener(listenerOnline);
        mDatabase.removeEventListener(layTongSoLuongTinNhanChuaXem);
        mDatabase.removeEventListener(laySoLuongChuaXem);
        mDatabase.removeEventListener(laySoLuongChuaXemNguoiDung);
        mDatabase.removeEventListener(layTongSoLuongTinNhanChuaXemNguoiDung);
        if (valueBan != null) {
            mDatabase.removeEventListener(valueBan);
        }
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.removeEventListener(myListenTrangThaiPhong);
        mDatabase.removeEventListener(listenerDsMess);
        mDatabase.removeEventListener(listenerOnline);
        mDatabase.removeEventListener(layTongSoLuongTinNhanChuaXem);
        mDatabase.removeEventListener(laySoLuongChuaXem);
        mDatabase.removeEventListener(laySoLuongChuaXemNguoiDung);
        mDatabase.removeEventListener(layTongSoLuongTinNhanChuaXemNguoiDung);
        mDatabase.child("mess_chat_doi").child(nguoiDungChatDon).child(tenBanChat).child("trang_thai_phong").child(nguoiDungChatDon).removeValue();
        mDatabase.child("mess_chat_doi").child(tenBanChat).child(nguoiDungChatDon).child("trang_thai_phong").child(nguoiDungChatDon).removeValue();
        if (valueBan != null) {
            mDatabase.removeEventListener(valueBan);
        }
    }
}
