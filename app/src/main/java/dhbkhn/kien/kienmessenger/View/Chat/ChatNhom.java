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
import dhbkhn.kien.kienmessenger.Model.Object.ProfileGroupChat;
import dhbkhn.kien.kienmessenger.Model.Object.ShowLassMess;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.Presenter.DangNhap_DangKy.SharePreferenceTrangThai;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class ChatNhom extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    RecyclerView rvChat;
    ImageView imgSend;
    EditText edtChat;
    TextView tvTenBanChat,tvTrangThaiBanChat,tvUsernameCuaToi;
    LinearLayoutManager linearLayoutManager;
    String nguoiDungChatDon ="";
    String emailNguoiDung = "";
    String tenNguoiTrongNhom = "";
    String emailNguoiTrongNhom = "";
    String key = "";
    ProfileGroupChat profileGroupChat = null;
    ChatDonAdapter adapter;
    List<Friend> dsF = new ArrayList<>();
    List<ChatMessage> dsChat = new ArrayList<>();
    SharePreferenceTrangThai sharePreferenceTrangThai;
    DrawerLayout drawerLayoutChatDoi;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    List<String> dsBanDangTrongPhong = new ArrayList<>();

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
        toggle = new ActionBarDrawerToggle(ChatNhom.this, drawerLayoutChatDoi, R.string.open, R.string.close);
        toggle.syncState();

        tvTenBanChat = (TextView) findViewById(R.id.tvTenBanChat);
        tvTrangThaiBanChat = (TextView) findViewById(R.id.tvTrangThaiBanChat);
        tvUsernameCuaToi = (TextView) findViewById(R.id.tvUsernameCuaToi);
        imgSend = (ImageView) findViewById(R.id.imgSend);
        edtChat = (EditText) findViewById(R.id.edtChat);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        linearLayoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(linearLayoutManager);

        sharePreferenceTrangThai = new SharePreferenceTrangThai();

        nguoiDungChatDon = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;
        Intent iDB = getIntent();
        tenNguoiTrongNhom = iDB.getStringExtra("tenbanchat");
        emailNguoiTrongNhom = iDB.getStringExtra("emailfriendchat");

        key = iDB.getStringExtra("key");
        profileGroupChat = (ProfileGroupChat) iDB.getSerializableExtra("ds");
        if(profileGroupChat!=null){
            key = profileGroupChat.getKey_nhom();
        }

        String[] e = emailNguoiTrongNhom.split(", ");
        String[] u = tenNguoiTrongNhom.split(", ");

        for(int i = 0;i<e.length;i++) {
            Friend newFriend = new Friend(e[i], u[i]);
            dsF.add(newFriend);
        }
    }

    private void addEvents() {
        if(profileGroupChat!=null) tvTenBanChat.setText(profileGroupChat.getTen_nhom());
        tvUsernameCuaToi.setText(nguoiDungChatDon);
        mDatabase.child("mess_chat_nhom").child(nguoiDungChatDon).child("trang_thai_phong")
                .child(key).addValueEventListener(listenerTrangThaiPhong);
        mDatabase.child("mess_chat_nhom/"+nguoiDungChatDon+"/quan_ly_mess/"+key+"/mess_chung").addValueEventListener(listenerDsMess);
        imgSend.setOnClickListener(btnListener);
    }

    private ValueEventListener listenerDsMess = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            dsChat = new ArrayList<ChatMessage>();
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                ChatMessage mess = post.getValue(ChatMessage.class);
                dsChat.add(mess);
            }

            adapter = new ChatDonAdapter(ChatNhom.this, dsChat, nguoiDungChatDon, emailNguoiDung);
            rvChat.setAdapter(adapter);
            rvChat.scrollToPosition(dsChat.size()-1);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private ValueEventListener listenerTrangThaiPhong = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                String ten = post.getRef().getKey();
                String trangthai = post.getValue(String.class);
                if (trangthai.equals("On") && dsBanDangTrongPhong.indexOf(ten)<0) {
                    dsBanDangTrongPhong.add(ten);
                }
            }
            Log.d("ds", dsBanDangTrongPhong.size() + "");
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
        String uEmail = user.getEmail();
        final ChatMessage message = new ChatMessage();
        Log.d("myCCCC", uEmail);
        message.setAuthor(uEmail);
        message.setContent(chat);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        message.setDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
        for (final Friend f : dsF) {
            mDatabase.child("users").child(f.getUsername_friend()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User u = dataSnapshot.getValue(User.class);
                    if (!u.getUsername().equals(nguoiDungChatDon)) {
                        if (u.isOnline()) {
                            if (dsBanDangTrongPhong.indexOf(f.getUsername_friend()) >= 0) {
                                message.setStatus("Đã xem");
                            }else {
                                message.setStatus("Đã nhận");
                            }
                        }else {
                            message.setStatus("Đã gửi");
                        }
                    }
                    Map<String,Object> hmM = message.toMap();
                    Map<String, Object>childUpdates = new HashMap<>();
                    String keyChung = mDatabase.child("mess_chat_nhom").child(f.getUsername_friend()).child("quan_ly_mess").child(key).push().getKey();
                    childUpdates.put("/mess_chat_nhom/"+f.getUsername_friend()+"/quan_ly_mess/"+key+"/mess_chung/"+keyChung,hmM);
                    mDatabase.updateChildren(childUpdates);

                    ShowLassMess showLassMess = new ShowLassMess();
                    showLassMess.setMessage(message);
                    showLassMess.setKey_nhom(key);
                    showLassMess.setFriend(new Friend(emailNguoiTrongNhom,tenNguoiTrongNhom));
                    mDatabase.child("mess_chat_nhom")
                            .child(f.getUsername_friend())
                            .child("quan_ly_mess")
                            .child("show_last_mess")
                            .child(key).setValue(showLassMess);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        mDatabase.removeEventListener(listenerDsMess);
        mDatabase.removeEventListener(listenerTrangThaiPhong);
        Intent iBack = new Intent(ChatNhom.this,TrangChu.class);
        iBack.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        for (Friend f : dsF) {
            mDatabase.child("mess_chat_nhom").child(f.getUsername_friend()).child("trang_thai_phong").child(key)
                    .child(nguoiDungChatDon).removeValue();
        }
        startActivity(iBack);
    }
}

