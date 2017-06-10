package dhbkhn.kien.kienmessenger.View.ThemNhomChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AdapterThemChatNhom;
import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AdapterNhapThemAnhNguoiTrongNhom;
import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.ViewHolderThemNhomChat;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.ProfileGroupChat;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class ThemNhomChat extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
    Toolbar toolbar;
    RecyclerView rvThemNhomChat;
    ImageView imgAvatarNhomChat;
    Button btnOKTenNhomChat;
    EditText edtTenNhomChat;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    AdapterThemChatNhom fra;
    String nguoiDung="";
    String emailNguoiDung ="";
    CollapsingToolbarLayout collapsingNhomChat;
    AppBarLayout appbarNhomChat;
    ProfileGroupChat profileGroupChat;
    List<Friend> dsFriend;
    List<String>ds;
    FrameLayout frameNhapThemNhom;
    RecyclerView rvNhapThemNhomChat;
    Friend appPlay = null;
    AdapterNhapThemAnhNguoiTrongNhom adapterNhapThemAnhNguoiTrongNhom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhom_chat);
        addControls();
        addEvents();
    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        frameNhapThemNhom = (FrameLayout) findViewById(R.id.frameNhapThemNhom);
        rvNhapThemNhomChat = (RecyclerView) findViewById(R.id.rvNhapThemNhom);
        imgAvatarNhomChat = (ImageView) findViewById(R.id.imgAvatarNhomChat);
        btnOKTenNhomChat = (Button) findViewById(R.id.btnOKTenNhomChat);
        collapsingNhomChat = (CollapsingToolbarLayout) findViewById(R.id.collapsingNhomChat);
        appbarNhomChat = (AppBarLayout) findViewById(R.id.appBarNhomChat);
        edtTenNhomChat = (EditText) findViewById(R.id.edtTenNhomChat);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rvThemNhomChat = (RecyclerView) findViewById(R.id.rvThemNhomChat);
        rvThemNhomChat.setLayoutManager(new LinearLayoutManager(this));

        rvNhapThemNhomChat.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        nguoiDung = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;

        appbarNhomChat.addOnOffsetChangedListener(this);
        profileGroupChat = new ProfileGroupChat();
        dsFriend = new ArrayList<>();
        ds = new ArrayList<>();
        appPlay = new Friend(emailNguoiDung,nguoiDung);
        dsFriend.add(appPlay);
        ds.add(nguoiDung);

        adapterNhapThemAnhNguoiTrongNhom = new AdapterNhapThemAnhNguoiTrongNhom(this, dsFriend);
        rvNhapThemNhomChat.setAdapter(adapterNhapThemAnhNguoiTrongNhom);
    }

    private void addEvents() {
        frameNhapThemNhom.setVisibility(View.VISIBLE);
        Query query = mDatabase.child("users/"+nguoiDung+"/friendlist").orderByChild("username_friend");
        initAdapter(query);
        btnOKTenNhomChat.setOnClickListener(myOnClick);
    }

    private void initAdapter(Query query){
        fra = new AdapterThemChatNhom(Friend.class, R.layout.custom_them_nhom_chat, ViewHolderThemNhomChat.class, query, ds);
        rvThemNhomChat.setAdapter(fra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_nhom_chat,menu);
        MenuItem item = menu.findItem(R.id.itemSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(myQuery);
        return true;
    }

    private SearchView.OnQueryTextListener myQuery = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.equals("")) {
                Log.d("luong", ds.size() + " - " + dsFriend.size());
                Query query = mDatabase.child("users/"+nguoiDung+"/friendlist").orderByChild("username_friend");
                initAdapter(query);
                adapterNhapThemAnhNguoiTrongNhom.notifyDataSetChanged();
            }else {
                Log.d("luong", ds.size() + " - " + dsFriend.size());
                Query query = mDatabase.child("users/"+nguoiDung+"/friendlist").orderByChild("username_friend")
                        .startAt(newText).endAt(newText+"~");
                initAdapter(query);
                adapterNhapThemAnhNguoiTrongNhom.notifyDataSetChanged();
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener myOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnOKTenNhomChat:
                    String eh = edtTenNhomChat.getText().toString().trim();
                    if (eh == null || eh.equals("")) {
                        Toast.makeText(ThemNhomChat.this, "Bạn cần đặt tên nhóm đã!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        themNhom(eh);
                    }
                    break;
            }
        }
    };

    private void themNhom(final String tennhom){
        mDatabase.child("users").child(nguoiDung).child("friendlist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    Friend f = post.getValue(Friend.class);
                    if (ds.indexOf(f.getUsername_friend()) >= 0) {
                        dsFriend.add(f);
                    }
                }
                String keyTaoNhom = mDatabase.child("mess_chat_nhom").child(nguoiDung).child("quan_ly_nhom").push().getKey();
                for(final Friend f: dsFriend){
                    profileGroupChat.setList_friend(dsFriend);
                    profileGroupChat.setTen_nhom(tennhom);
                    profileGroupChat.setKey_nhom(keyTaoNhom);

                    mDatabase.child("mess_chat_nhom").child(f.getUsername_friend()).child("quan_ly_nhom")
                            .child(keyTaoNhom).setValue(profileGroupChat);
                    if (dsFriend.indexOf(f)==dsFriend.size()-1) {
                        Intent iGroup = new Intent(ThemNhomChat.this, TrangChu.class);
                        startActivity(iGroup);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (collapsingNhomChat.getHeight() + verticalOffset <= 1.5 * ViewCompat.getMinimumHeight(collapsingNhomChat)) {
            LinearLayout llSearch = (LinearLayout) appBarLayout.findViewById(R.id.ll);
            llSearch.animate().alpha(0).setDuration(2000);
        }
        else {
            LinearLayout llSearch = (LinearLayout) appBarLayout.findViewById(R.id.ll);
            llSearch.animate().alpha(1).setDuration(2000);
        }
    }
}