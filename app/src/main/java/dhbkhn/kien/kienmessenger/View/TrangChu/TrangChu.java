package dhbkhn.kien.kienmessenger.View.TrangChu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;
import dhbkhn.kien.kienmessenger.Model.Object.ShowLassMess;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Model.TrangChu.TrangChuService;
import dhbkhn.kien.kienmessenger.Presenter.TrangChu.CapNhatNguoiDung.PresenterTrangChu;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentBangTin;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentKetBan;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentNhanTinCha;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentThongBao;
import dhbkhn.kien.kienmessenger.View.TrangChu.Fragment.FragmentThongTinDuLich;

public class TrangChu extends AppCompatActivity implements ITrangChu{
    public static final String SERVER_NAME_URL = "http://192.168.0.103:88/chat/chat.php";
    public static final String SERVER_ASP_NAME_URL = "http://localhost:3098/DuLich/";

    FragmentTabHost tabHost;
    FirebaseAuth mAuth;
    String nguoidung = "";
    String email = "";
    DatabaseReference databaseReference;
    int tbChuaXem = 0;
    int tinNhanChuaXem = 0;
    StorageReference mStorage;
    HienThiBitmap hienThi;
    PresenterTrangChu presenterTrangChu;
    CardView cvToast;
    TextView tvToast;
    String friend ="";

    public static Intent iService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        addControls();
        iService = new Intent(TrangChu.this, TrangChuService.class);
        startService(iService);
    }


    private void addControls() {
        nguoidung = DangNhapMessenger.nguoidung;
        email = DangNhapMessenger.emailnguoidung;

        databaseReference = FirebaseDatabase.getInstance().getReference();

        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        cvToast = (CardView) findViewById(R.id.cvToast);
        tvToast = (TextView) findViewById(R.id.tvToast);
        mAuth = FirebaseAuth.getInstance();
        kiemTraTinNhanDen();

        customViewTab("Bảng tin",R.drawable.home, 0);
        customViewTab("Trò chuyện",R.drawable.mess, 1);
        customViewTab("Du lịch",R.drawable.search_blue, 2);
        customViewTab("Kết bạn",R.drawable.add_friend_blue, 3);
        customViewTab("Thông báo",R.drawable.earth, 4);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "Bảng tin":
                        break;
                    case "Trò chuyện":
                        break;
                    case "Du lịch":
                        break;
                    case "Kết bạn":
                        break;
                    case "Thông báo":
                        break;
                }
            }
        });

        tabHost.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        hienThi = new HienThiBitmap(TrangChu.this);
        mStorage = FirebaseStorage.getInstance().getReference();
        presenterTrangChu = new PresenterTrangChu(this);
    }

    private void customViewTab(String tentab, int idIcon, int soHieuTab){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View custom = inflater.inflate(R.layout.custom_item_notification, null);
        final TextView tvNotify = (TextView) custom.findViewById(R.id.tvSoLuongThongBao);
        ImageView imgIconTab = (ImageView) custom.findViewById(R.id.imgIconTab);
        imgIconTab.setImageDrawable(layAnh(idIcon));

        switch (soHieuTab) {
            case 0:
                tabHost.addTab(tabHost.newTabSpec(tentab).setIndicator(custom),FragmentBangTin.class,null);
                break;
            case 1:
                tabHost.addTab(tabHost.newTabSpec(tentab).setIndicator(custom),FragmentNhanTinCha.class,null);
                databaseReference.child("mess_chat_doi").child(nguoidung).child("tong_so_luong_tin_nhan_chua_xem").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue(Integer.class) != null) {
                                int tncx = dataSnapshot.getValue(Integer.class);
                                tinNhanChuaXem = tncx;
                                if (tinNhanChuaXem > 0) {
                                    tvNotify.setVisibility(View.VISIBLE);
                                    tvNotify.setText(String.valueOf(tinNhanChuaXem));
                                }
                                else {
                                    tvNotify.setVisibility(View.GONE);
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(TrangChu.this, "Có lỗi " + ex.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case 2:
                tabHost.addTab(tabHost.newTabSpec(tentab).setIndicator(custom),FragmentThongTinDuLich.class,null);
                break;
            case 3:
                tabHost.addTab(tabHost.newTabSpec(tentab).setIndicator(custom),FragmentKetBan.class,null);
                databaseReference.child("notification").child("statistic").child(nguoidung).child("ket_ban")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DuLieuThongBao duLieuThongBao = dataSnapshot.getValue(DuLieuThongBao.class);
                                if (duLieuThongBao != null) {
                                    tbChuaXem = duLieuThongBao.getTbChuaXem();
                                    if (tbChuaXem > 0) {
                                        tvNotify.setVisibility(View.VISIBLE);
                                        tvNotify.setText(String.valueOf(tbChuaXem));
                                    }
                                    else {
                                        tvNotify.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                break;
            case 4:
                tabHost.addTab(tabHost.newTabSpec(tentab).setIndicator(custom),FragmentThongBao.class,null);
                databaseReference.child("notification").child("statistic").child(nguoidung).child("cong_dong")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DuLieuThongBao duLieuThongBao = dataSnapshot.getValue(DuLieuThongBao.class);
                                if (duLieuThongBao != null) {
                                    tbChuaXem = duLieuThongBao.getTbChuaXem();
                                    if (tbChuaXem > 0) {
                                        tvNotify.setVisibility(View.VISIBLE);
                                        tvNotify.setText(String.valueOf(tbChuaXem));
                                    }
                                    else {
                                        tvNotify.setVisibility(View.GONE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                break;
        }
    }

    public Drawable layAnh(int idDw){
        Drawable drawble;
        if (Build.VERSION.SDK_INT > 21) {
            drawble = ContextCompat.getDrawable(TrangChu.this, idDw);
        }else {
            drawble = getResources().getDrawable(idDw);
        }
        return drawble;
    }

    @Override
    public void capNhatAvatar() {

    }

    private void kiemTraTinNhanDen(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("mess_chat_doi").child(nguoidung).child("show_last_mess").addValueEventListener(myValue);
    }

    private ValueEventListener myValue = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                ShowLassMess slm = post.getValue(ShowLassMess.class);
                friend = slm.getFriend().getUsername_friend();
                if (slm != null && !slm.getMessage().getAuthor().equals(email)
                        && !slm.getMessage().getStatus().equals("Đã xem") && slm.getSo_lan_thong_bao()==0) {
                    customToast(friend, slm.getFriend().getEmail_friend());
                    databaseReference.child("mess_chat_doi").child(nguoidung).child("show_last_mess")
                            .child(slm.getFriend().getUsername_friend()).child("so_lan_thong_bao").setValue(1);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void customToast(final String friend2, final String emailfriend) {
        Toast t = new Toast(TrangChu.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_toast, null);
        TextView tvT = (TextView) row.findViewById(R.id.tvToast);
        tvT.setText(friend + " đã gửi tin nhắn cho bạn");
        t.setDuration(Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,0);
        t.setView(row);
        t.show();
    }

    @Override
    protected void onDestroy() {
        stopService(iService);
        super.onDestroy();
    }
}
