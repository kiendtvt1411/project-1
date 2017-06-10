package dhbkhn.kien.kienmessenger.View.ThemBanChat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterPickImage;
import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterRecyclerFirebaseStatus;
import dhbkhn.kien.kienmessenger.Adapter.Status.ViewHolderStatus;
import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.Notification;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Presenter.Status.PresenterDangStatus;
import dhbkhn.kien.kienmessenger.Presenter.TrangChu.FragmentCaNhan.PresenterCaNhan;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.Chat.ChatDoi;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.Status.IViewDangStatus;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class XemTrangCaNhanCuaNguoiKhac extends AppCompatActivity implements IViewDangStatus{
    ImageView imgAvatarCaNhan;
    RecyclerView rvAnhStatus;
    TextView tvTenTrangCaNhan;
    EditText edtDangCaNhan;
    Button btnDangStatusCaNhan, btnHuyStatusCaNhan;
    ImageButton imgChenIcon, imgChenAnh, imgChenTep, btnThemBanTrangCaNhan, btnThemTinNhanTrangCaNhan;
    RecyclerView rvCaNhan;
    DatabaseReference mDatabase;
    LinearLayout llDangStatus;
    StorageReference mStorage;
    String nguoidung = "";
    String emailnd = "";
    String friend = "";
    static String banbe = "";
    PresenterCaNhan presenterCaNhan;
    PresenterDangStatus presenterDangStatus;
    String url = "";
    HienThiBitmap hienThiBitmap;
    AdapterPickImage adapter;
    List<Bitmap> dsBitmap;
    AdapterRecyclerFirebaseStatus adapterRecyclerFirebaseStatus;
    int tongTB = 0;
    int tbChuaXem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_trang_ca_nhan_cua_nguoi_khac);
        addControls();
        layRaDuLieuTuIntent();
        clickSuKien();
        addEvents();
        layRaStatus();
    }

    private void addControls() {
        imgAvatarCaNhan = (ImageView) findViewById(R.id.imgAvatarCaNhan);
        tvTenTrangCaNhan = (TextView) findViewById(R.id.tvTenTrangCaNhan);
        rvAnhStatus = (RecyclerView) findViewById(R.id.rvAnhStatus);
        rvAnhStatus.setLayoutManager(new LinearLayoutManager(XemTrangCaNhanCuaNguoiKhac.this,LinearLayoutManager.HORIZONTAL,false));
        edtDangCaNhan = (EditText) findViewById(R.id.edtDangCaNhan);
        btnDangStatusCaNhan = (Button) findViewById(R.id.btnDangStatusCaNhan);
        btnHuyStatusCaNhan = (Button) findViewById(R.id.btnHuyStatusCaNhan);
        imgChenIcon = (ImageButton) findViewById(R.id.imgChenIcon);
        imgChenAnh = (ImageButton) findViewById(R.id.imgChenAnh);
        imgChenTep = (ImageButton) findViewById(R.id.imgChenTep);
        btnThemBanTrangCaNhan = (ImageButton) findViewById(R.id.btnThemBanTrangCaNhan);
        btnThemTinNhanTrangCaNhan = (ImageButton) findViewById(R.id.btnThemTinNhanTrangCaNhan);
        llDangStatus = (LinearLayout) findViewById(R.id.llDangStatus);
        rvCaNhan = (RecyclerView) findViewById(R.id.rvCaNhan);

        LinearLayoutManager mn = new LinearLayoutManager(XemTrangCaNhanCuaNguoiKhac.this);
        mn.setReverseLayout(true);
        rvCaNhan.setLayoutManager(mn);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        hienThiBitmap = new HienThiBitmap(XemTrangCaNhanCuaNguoiKhac.this);
        presenterCaNhan = new PresenterCaNhan();
        presenterDangStatus = new PresenterDangStatus(this);

        dsBitmap = new ArrayList<>();
        adapter = new AdapterPickImage(XemTrangCaNhanCuaNguoiKhac.this, dsBitmap);
        rvAnhStatus.setAdapter(adapter);
    }

    private void layRaStatus(){
        Query qS = mDatabase.child("status/"+friend+"/cua_toi");
        adapterRecyclerFirebaseStatus = new AdapterRecyclerFirebaseStatus(XemTrangCaNhanCuaNguoiKhac.this, nguoidung, Status.class,
                R.layout.custom_rv_trang_ca_nhan, ViewHolderStatus.class, qS, "cua_toi");
        rvCaNhan.setAdapter(adapterRecyclerFirebaseStatus);
    }

    private void layRaDuLieuTuIntent() {
        nguoidung = DangNhapMessenger.nguoidung;
        emailnd = DangNhapMessenger.emailnguoidung;

        friend = getIntent().getStringExtra("friend");
//        banbe = getIntent().getStringExtra("banbe");
        kiemTraDaLaBanBeChua(friend);
    }

    private void kiemTraDaLaBanBeChua(final String friend) {
        mDatabase.child("users").child(nguoidung).child("friendlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                        Friend f = post.getValue(Friend.class);
                        if (friend.equals(f.getUsername_friend())) {
                            banbe = "da_la_ban_be";
                        }
                    }
                }else {
                    banbe = "chua_la_ban_be";
                }

                if (banbe.equals("da_la_ban_be")) {
                    llDangStatus.setVisibility(View.VISIBLE);
                    btnThemBanTrangCaNhan.setVisibility(View.GONE);
                    btnThemTinNhanTrangCaNhan.setVisibility(View.VISIBLE);
                    mDatabase.child("users").child(friend).child("email").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            btnThemTinNhanTrangCaNhan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String email = dataSnapshot.getValue(String.class);
                                    Intent iChat = new Intent(XemTrangCaNhanCuaNguoiKhac.this, ChatDoi.class);
                                    mDatabase.child("mess_chat_doi").child(nguoidung).child(friend).child("trang_thai_phong").child(nguoidung).setValue("On");
                                    mDatabase.child("mess_chat_doi").child(friend).child(nguoidung).child("trang_thai_phong").child(nguoidung).setValue("On");
                                    iChat.putExtra("tenbanchat",friend);
                                    iChat.putExtra("emailfriendchat",email);
                                    startActivity(iChat);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {
                    llDangStatus.setVisibility(View.GONE);
                    btnThemBanTrangCaNhan.setVisibility(View.VISIBLE);
                    btnThemTinNhanTrangCaNhan.setVisibility(View.GONE);
                    btnThemBanTrangCaNhan.setOnClickListener(myClick);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addEvents() {
        presenterCaNhan.layRaUsernameTrangCaNhan(friend,tvTenTrangCaNhan);
        mDatabase.child("users").child(friend).child("avatar_url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                url = dataSnapshot.getValue().toString();
                StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                final long ONE_MEGABYTE = 1024 * 1024;
                ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        imgAvatarCaNhan.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("anh", "loi load anh!");
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void clickSuKien(){
        edtDangCaNhan.setHint("Hãy viết gì đó lên tường nhà "+friend);
        btnDangStatusCaNhan.setOnClickListener(myClick);
        btnHuyStatusCaNhan.setOnClickListener(myClick);
        imgChenIcon.setOnClickListener(myClick);
        imgChenAnh.setOnClickListener(myClick);
        imgChenTep.setOnClickListener(myClick);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btnDangStatusCaNhan:
                    if (banbe.equals("da_la_ban_be")) {
                        String content = edtDangCaNhan.getText().toString().trim();
                        dangStatus(friend,content,url,"Ha Noi","Cong khai");
                        edtDangCaNhan.setText("");
                        dsBitmap.clear();
                        rvAnhStatus.setVisibility(View.GONE);
                        rvAnhStatus.removeAllViews();
                    }
                    else {

                    }
                    break;
                case R.id.btnHuyStatusCaNhan:
                    dsBitmap.clear();
                    rvAnhStatus.setVisibility(View.GONE);
                    rvAnhStatus.removeAllViews();
                    edtDangCaNhan.setText("");
                    edtDangCaNhan.setHint("Hãy viết gì đó lên tường nhà "+friend);
                    break;
                case R.id.imgChenIcon:
                    break;
                case R.id.imgChenAnh:
                    rvAnhStatus.setVisibility(View.VISIBLE);
                    openGallery();
                    break;
                case R.id.imgChenTep:
                    break;
                case R.id.btnThemBanTrangCaNhan:
                    layRaSoLuong(friend);
                    break;
            }
        }
    };

    private void dangStatus(String username, String content, String avatar_url, String location, String type){
        final Status newStatus = new Status();
        newStatus.setUsername(username);
        newStatus.setContent(content);
        newStatus.setAvatar_url(avatar_url);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date d = Calendar.getInstance().getTime();
        String date = dateFormat.format(d);
        newStatus.setDate(date);
        newStatus.setLocation(location);
        newStatus.setType(type);

        final List<String> list_url = new ArrayList<>();
        final String key = presenterDangStatus.layKeyDangStatus(username);

        newStatus.setKey_status(key);
        if (dsBitmap.size() > 0) {
            for (final Bitmap bm : dsBitmap) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] array = baos.toByteArray();
                final StorageReference ref = mStorage.child("hinh_anh").child("status").child(friend).child(key).child("image"+dsBitmap.indexOf(bm)+".jpg");
                ref.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                            @Override
                            public void onSuccess(StorageMetadata storageMetadata) {
                                String url = storageMetadata.getDownloadUrl() + "";
                                list_url.add(url);
                                if (dsBitmap.indexOf(bm) == (dsBitmap.size() - 1)) {
                                    newStatus.setList_image_url(list_url);
                                    presenterDangStatus.dangStatusCaNhan(friend,key,newStatus);
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }else {
            presenterDangStatus.dangStatusCaNhan(friend,key,newStatus);
        }
    }

    @Override
    public void dangStatusThanhCong() {
        Toast.makeText(XemTrangCaNhanCuaNguoiKhac.this, "Đăng Status thành công!", Toast.LENGTH_SHORT).show();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,128);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 128) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            Bitmap bm = hienThiBitmap.thumbNail(path);
            dsBitmap.add(bm);
            adapter.notifyDataSetChanged();
        }
    }

    private void inItNotify(String nguoidung, String friend, int tong, int chuaxem) {
        Notification notification = new Notification();
        notification.setAuthor(nguoidung);
        notification.setEmail(emailnd);
        notification.setContent(nguoidung);
        notification.setConsequence(false);
        Map<String,Object> hmN = notification.toMap();

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

        Toast.makeText(XemTrangCaNhanCuaNguoiKhac.this, "Bạn đã gửi lời kết bạn tới " + friend, Toast.LENGTH_SHORT).show();

        Intent iTrangChu = new Intent(XemTrangCaNhanCuaNguoiKhac.this, TrangChu.class);
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
                Toast.makeText(XemTrangCaNhanCuaNguoiKhac.this, "Bạn không thể tự kết bạn với chính mình!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
