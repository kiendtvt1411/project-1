package dhbkhn.kien.kienmessenger.View.Status.Comment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterPickImage;
import dhbkhn.kien.kienmessenger.Adapter.Status.Comment.AdapterCommentCha;
import dhbkhn.kien.kienmessenger.Adapter.Status.Comment.ViewHolderComment;
import dhbkhn.kien.kienmessenger.Model.Object.Comment;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.Model.Status.XuLyLikeShareComment;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Presenter.Status.Comment.PresenterDangComment;
import dhbkhn.kien.kienmessenger.Presenter.Status.PresenterDangStatus;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.Status.IViewDangStatus;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.XemTrangCaNhanCuaNguoiKhac;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangCaNhan;

public class XemChiTietStatus extends AppCompatActivity implements IViewDangComment, IViewDangStatus{
    DatabaseReference mDatabase;
    StorageReference mStorage;
    ImageView imgStatusChiTiet,imgLikeChiTiet,imgAnh1StatusChiTiet,imgAnh2StatusChiTiet,imgAnh3StatusChiTiet;
    ImageButton imgSettingStatusChiTiet,imgChenIconComment,imgChenAnhComment,imgChenTepComment;
    TextView tvUsernameStatusChiTiet, tvLocationStatusChiTiet, tvDateStatusChiTiet, tvContentStatusChiTiet, tvThemComment;
    TextView tvTransactionLikeChiTiet,tvTransactionCommentChiTiet,tvTransactionShareChiTiet,tvLikeChiTiet,tvNhieuAnhChiTiet,tvShareChiTiet;
    LinearLayout llLikeChiTiet,llAnhChiTiet, llKhongDuocShare, llDuocShare;
    EditText edtDangComment;
    Button btnDangComment,btnHuyComment;
    RecyclerView rvAnhComment, rvBinhLuanCha;

    //cho layout duoc share
    TextView tvNguoiShare,tvNguoiDuocShare1,tvNguoiDuocShare2, tvLocationStatusDuocShare,tvDateStatusDuocShare
            ,tvContentStatusDuocShare, tvNhieuAnhDuocShare;
    ImageView imgAvatarNguoiDuocShare, imgAnh1StatusDuocShare, imgAnh2StatusDuocShare,imgAnh3StatusDuocShare;
    LinearLayout llAnhStatusDuocShare;

    AdapterCommentCha adapter;
    PresenterDangComment presenterDangComment;
    HienThiBitmap hienThiBitmap;
    List<Bitmap> dsBitmap;
    AdapterPickImage pick;
    String nguoidung = "";
    String emailnguoidung = "";
    String url = "";
    String statusCuaAi = "";
    String keyStatus = "";
    String chuStatus = "";
    boolean duocShare = false;
    XuLyLikeShareComment xuLyLikeShareComment;
    PresenterDangStatus presenterDangStatus;
    static int soComment = 0;
    static Status status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_chi_tiet_status);
        addControls();
        layDuLieuIntent();
        addEvents();
    }

    private void addControls() {
        imgStatusChiTiet = (ImageView) findViewById(R.id.imgStatusChiTiet);
        imgLikeChiTiet = (ImageView) findViewById(R.id.imgLikeChiTiet);
        imgAnh1StatusChiTiet = (ImageView) findViewById(R.id.imgAnh1StatusChiTiet);
        imgAnh2StatusChiTiet = (ImageView) findViewById(R.id.imgAnh2StatusChiTiet);
        imgAnh3StatusChiTiet = (ImageView) findViewById(R.id.imgAnh3StatusChiTiet);
        imgSettingStatusChiTiet = (ImageButton) findViewById(R.id.imgSettingStatusChiTiet);
        imgChenIconComment = (ImageButton) findViewById(R.id.imgChenIconComment);
        imgChenAnhComment = (ImageButton) findViewById(R.id.imgChenAnhComment);
        imgChenTepComment = (ImageButton) findViewById(R.id.imgChenTepComment);
        tvUsernameStatusChiTiet = (TextView) findViewById(R.id.tvUsernameStatusChiTiet);
        tvLocationStatusChiTiet = (TextView) findViewById(R.id.tvLocationStatusChiTiet);
        tvDateStatusChiTiet = (TextView) findViewById(R.id.tvDateStatusChiTiet);
        tvContentStatusChiTiet = (TextView) findViewById(R.id.tvContentStatusChiTiet);
        tvShareChiTiet = (TextView) findViewById(R.id.tvShareChiTiet);
        tvThemComment = (TextView) findViewById(R.id.tvThemComment);

        //cho layout duoc share
        tvNguoiShare = (TextView) findViewById(R.id.tvNguoiShare);
        tvNguoiDuocShare1 = (TextView) findViewById(R.id.tvNguoiDuocShare1);
        tvNguoiDuocShare2 = (TextView) findViewById(R.id.tvNguoiDuocShare2);
        tvDateStatusDuocShare = (TextView) findViewById(R.id.tvDateStatusDuocShare);
        tvLocationStatusDuocShare = (TextView) findViewById(R.id.tvLocationStatusDuocShare);
        tvContentStatusDuocShare = (TextView) findViewById(R.id.tvContentStatusDuocShare);
        tvNhieuAnhDuocShare = (TextView) findViewById(R.id.tvNhieuAnhDuocShare);
        imgAvatarNguoiDuocShare = (ImageView) findViewById(R.id.imgAvatarNguoiDuocShare);
        imgAnh1StatusDuocShare = (ImageView) findViewById(R.id.imgAnh1StatusDuocShare);
        imgAnh2StatusDuocShare = (ImageView) findViewById(R.id.imgAnh2StatusDuocShare);
        imgAnh3StatusDuocShare = (ImageView) findViewById(R.id.imgAnh3StatusDuocShare);
        llAnhStatusDuocShare = (LinearLayout) findViewById(R.id.llAnhStatusDuocShare);

        tvTransactionLikeChiTiet = (TextView) findViewById(R.id.tvTransactionLikeChiTiet);
        tvTransactionCommentChiTiet = (TextView) findViewById(R.id.tvTransactionCommentChiTiet);
        tvTransactionShareChiTiet = (TextView) findViewById(R.id.tvTransactionShareChiTiet);
        tvLikeChiTiet = (TextView) findViewById(R.id.tvLikeChiTiet);
        tvNhieuAnhChiTiet = (TextView) findViewById(R.id.tvNhieuAnhChiTiet);
        llAnhChiTiet = (LinearLayout) findViewById(R.id.llAnhChiTiet);
        llLikeChiTiet = (LinearLayout) findViewById(R.id.llLikeChiTiet);
        llKhongDuocShare = (LinearLayout) findViewById(R.id.llKhongDuocShare);
        llDuocShare = (LinearLayout) findViewById(R.id.llDuocShare);
        edtDangComment = (EditText) findViewById(R.id.edtDangComment);
        btnDangComment = (Button) findViewById(R.id.btnDangComment);
        btnHuyComment = (Button) findViewById(R.id.btnHuyComment);
        rvBinhLuanCha = (RecyclerView) findViewById(R.id.rvBinhLuanCha);

        dsBitmap = new ArrayList<>();
        pick = new AdapterPickImage(this, dsBitmap);
        rvAnhComment = (RecyclerView) findViewById(R.id.rvAnhComment);
        rvAnhComment.setLayoutManager(new LinearLayoutManager(XemChiTietStatus.this,LinearLayoutManager.HORIZONTAL,false));
        rvAnhComment.setAdapter(pick);

        presenterDangStatus = new PresenterDangStatus(this);

        LinearLayoutManager mn = new LinearLayoutManager(XemChiTietStatus.this);
        mn.setReverseLayout(true);
        mn.setStackFromEnd(true);
        rvBinhLuanCha.setLayoutManager(mn);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        presenterDangComment = new PresenterDangComment(this);
        hienThiBitmap = new HienThiBitmap(XemChiTietStatus.this);
        xuLyLikeShareComment = new XuLyLikeShareComment();
    }

    private void layDuLieuIntent() {
        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;

        Intent iCT = getIntent();
        keyStatus = iCT.getStringExtra("status");
        statusCuaAi = iCT.getStringExtra("status_cua_ai");
        chuStatus = iCT.getStringExtra("chu_status");
        duocShare = iCT.getBooleanExtra("duoc_share", false);
    }

    private void addEvents() {
        mDatabase.child("users").child(nguoidung).child("avatar_url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                url = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (keyStatus != null && !keyStatus.equals("")) {
                mDatabase.child("status").child(nguoidung).child(statusCuaAi).child(keyStatus)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                status = dataSnapshot.getValue(Status.class);
                                if (status != null) {
                                    final boolean[] clickLike = {status.isClickLike()};
                                    final int[] soLike = {status.getLikes()};
                                    soComment = status.getComments();
                                    final int[] soShare = {status.getShares()};
                                    final Map<String, Object> hmUpdate = new HashMap<String, Object>();

                                    tvTransactionLikeChiTiet.setText(String.valueOf(soLike[0]));
                                    tvTransactionCommentChiTiet.setText(String.valueOf(soComment));
                                    tvTransactionShareChiTiet.setText(String.valueOf(soShare[0]));
                                    final String keyS = status.getKey_status();

                                    if (!duocShare) {
                                        llDuocShare.setVisibility(View.GONE);
                                        llKhongDuocShare.setVisibility(View.VISIBLE);
                                        hienThiAnh(status.getAvatar_url(),imgStatusChiTiet);
                                        tvUsernameStatusChiTiet.setText(status.getUsername());
                                        tvLocationStatusChiTiet.setText(status.getLocation());
                                        tvDateStatusChiTiet.setText(status.getDate());
                                        tvContentStatusChiTiet.setText(status.getContent());

                                        tvUsernameStatusChiTiet.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (nguoidung.equals(status.getUsername())) {
                                                    Intent iCaNhan = new Intent(XemChiTietStatus.this, TrangCaNhan.class);
                                                    startActivity(iCaNhan);
                                                }else {
                                                    Intent iNguoiKhac = new Intent(XemChiTietStatus.this, XemTrangCaNhanCuaNguoiKhac.class);
                                                    iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                                    iNguoiKhac.putExtra("friend", status.getUsername());
                                                    startActivity(iNguoiKhac);
                                                }
                                            }
                                        });

                                        List<String> dsUrl = status.getList_image_url();
                                        if (dsUrl != null) {
                                            llAnhChiTiet.setVisibility(View.VISIBLE);
                                            if (dsUrl.size() > 3) {
                                                imgAnh1StatusChiTiet.setVisibility(View.VISIBLE);
                                                imgAnh2StatusChiTiet.setVisibility(View.VISIBLE);
                                                imgAnh3StatusChiTiet.setVisibility(View.VISIBLE);
                                                tvNhieuAnhChiTiet.setVisibility(View.VISIBLE);
                                                hienThiAnh(dsUrl.get(0),imgAnh1StatusChiTiet);
                                                hienThiAnh(dsUrl.get(1),imgAnh2StatusChiTiet);
                                                hienThiAnh(dsUrl.get(2),imgAnh3StatusChiTiet);
                                                tvNhieuAnhChiTiet.setText("+"+String.valueOf(dsUrl.size()-2));
                                            }
                                            else if (dsUrl.size() == 3) {
                                                imgAnh1StatusChiTiet.setVisibility(View.VISIBLE);
                                                imgAnh2StatusChiTiet.setVisibility(View.VISIBLE);
                                                imgAnh3StatusChiTiet.setVisibility(View.VISIBLE);
                                                hienThiAnh(dsUrl.get(0),imgAnh1StatusChiTiet);
                                                hienThiAnh(dsUrl.get(1),imgAnh2StatusChiTiet);
                                                hienThiAnh(dsUrl.get(2),imgAnh3StatusChiTiet);
                                            }
                                            else if (dsUrl.size() == 2) {
                                                imgAnh1StatusChiTiet.setVisibility(View.VISIBLE);
                                                imgAnh2StatusChiTiet.setVisibility(View.VISIBLE);
                                                hienThiAnh(dsUrl.get(0),imgAnh1StatusChiTiet);
                                                hienThiAnh(dsUrl.get(1),imgAnh2StatusChiTiet);
                                            }
                                            else if (dsUrl.size() == 1) {
                                                imgAnh1StatusChiTiet.setVisibility(View.VISIBLE);
                                                hienThiAnh(dsUrl.get(0),imgAnh1StatusChiTiet);
                                            }
                                        }

                                        tvShareChiTiet.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Status statusDuocShare = new Status();
                                                statusDuocShare.setUsername(nguoidung + "," +status.getUsername());
                                                statusDuocShare.setLocation("No");
                                                statusDuocShare.setDate("No");
                                                statusDuocShare.setLikes(0);
                                                statusDuocShare.setComments(0);
                                                statusDuocShare.setShares(0);
                                                statusDuocShare.setContent(status.getKey_status());
                                                statusDuocShare.setType("Công khai");
                                                statusDuocShare.setDuocShare(true);
                                                String keyStatusDuocShareMoi = presenterDangStatus.layKeyDangStatus(nguoidung);
                                                statusDuocShare.setKey_status(keyStatusDuocShareMoi);
                                                presenterDangStatus.dangStatusCaNhan(nguoidung,keyStatusDuocShareMoi,statusDuocShare);
                                                ++soShare[0];
                                                xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(status,"shares", soShare[0]);
                                                xuLyLikeShareComment.themNotiCongDong(hmUpdate, status, status.getKey_status(), " đã chia sẻ bài viết của bạn", "_so_share");
                                            }
                                        });
                                    }
                                    else {
                                        llDuocShare.setVisibility(View.VISIBLE);
                                        llKhongDuocShare.setVisibility(View.GONE);

                                        String author = status.getUsername();
                                        final String[] arrAuthor = author.split(",");
                                        tvNguoiShare.setText(arrAuthor[0]);
                                        tvNguoiDuocShare1.setText(arrAuthor[1]);

                                        View.OnClickListener myCLick = new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                switch (v.getId()) {
                                                    case R.id.tvNguoiShare:
                                                        if (nguoidung.equals(arrAuthor[0])) {
                                                            Intent iCaNhan = new Intent(XemChiTietStatus.this, TrangCaNhan.class);
                                                            startActivity(iCaNhan);
                                                        }else {
                                                            Intent iNguoiKhac = new Intent(XemChiTietStatus.this, XemTrangCaNhanCuaNguoiKhac.class);
                                                            iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                                            iNguoiKhac.putExtra("friend",arrAuthor[0]);
                                                            startActivity(iNguoiKhac);
                                                        }
                                                        break;
                                                    case R.id.tvNguoiDuocShare1:
                                                        if (nguoidung.equals(arrAuthor[1])) {
                                                            Intent iCaNhan = new Intent(XemChiTietStatus.this, TrangCaNhan.class);
                                                            startActivity(iCaNhan);
                                                        }else {
                                                            Intent iNguoiKhac = new Intent(XemChiTietStatus.this, XemTrangCaNhanCuaNguoiKhac.class);
                                                            iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                                            iNguoiKhac.putExtra("friend",arrAuthor[1]);
                                                            startActivity(iNguoiKhac);
                                                        }
                                                        break;
                                                    case R.id.tvNguoiDuocShare2:
                                                        if (nguoidung.equals(arrAuthor[1])) {
                                                            Intent iCaNhan = new Intent(XemChiTietStatus.this, TrangCaNhan.class);
                                                            startActivity(iCaNhan);
                                                        }else {
                                                            Intent iNguoiKhac = new Intent(XemChiTietStatus.this, XemTrangCaNhanCuaNguoiKhac.class);
                                                            iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                                            iNguoiKhac.putExtra("friend",arrAuthor[1]);
                                                            startActivity(iNguoiKhac);
                                                        }
                                                        break;
                                                }
                                            }
                                        };

                                        tvNguoiShare.setOnClickListener(myCLick);
                                        tvNguoiDuocShare1.setOnClickListener(myCLick);
                                        tvNguoiDuocShare2.setOnClickListener(myCLick);

                                        String keyStatusDuocShare = status.getContent();
                                        mDatabase.child("status").child(arrAuthor[1]).child("cua_toi").child(keyStatusDuocShare).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                Status s = dataSnapshot.getValue(Status.class);
                                                tvNguoiDuocShare2.setText(s.getUsername());
                                                hienThiAnh(s.getAvatar_url(), imgAvatarNguoiDuocShare);

                                                tvLocationStatusDuocShare.setText(s.getLocation());
                                                tvDateStatusDuocShare.setText(s.getDate());
                                                tvContentStatusDuocShare.setText(s.getContent());

                                                List<String> dsLinkAnh = s.getList_image_url();
                                                if (dsLinkAnh != null) {
                                                    llAnhStatusDuocShare.setVisibility(View.VISIBLE);
                                                    if (dsLinkAnh.size() > 3) {
                                                        imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                                                        imgAnh2StatusDuocShare.setVisibility(View.VISIBLE);
                                                        imgAnh3StatusDuocShare.setVisibility(View.VISIBLE);
                                                        tvNhieuAnhDuocShare.setVisibility(View.VISIBLE);
                                                        hienThiAnh(dsLinkAnh.get(0), imgAnh1StatusDuocShare);
                                                        hienThiAnh(dsLinkAnh.get(1), imgAnh2StatusDuocShare);
                                                        hienThiAnh(dsLinkAnh.get(2), imgAnh3StatusDuocShare);
                                                        tvNhieuAnhDuocShare.setText("+" + String.valueOf(dsLinkAnh.size() - 2));
                                                    } else if (dsLinkAnh.size() == 3) {
                                                        imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                                                        imgAnh2StatusDuocShare.setVisibility(View.VISIBLE);
                                                        imgAnh3StatusDuocShare.setVisibility(View.VISIBLE);
                                                        hienThiAnh(dsLinkAnh.get(0), imgAnh1StatusDuocShare);
                                                        hienThiAnh(dsLinkAnh.get(1), imgAnh2StatusDuocShare);
                                                        hienThiAnh(dsLinkAnh.get(2), imgAnh3StatusDuocShare);
                                                    } else if (dsLinkAnh.size() == 2) {
                                                        imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                                                        imgAnh2StatusDuocShare.setVisibility(View.VISIBLE);
                                                        hienThiAnh(dsLinkAnh.get(0), imgAnh1StatusDuocShare);
                                                        hienThiAnh(dsLinkAnh.get(1), imgAnh2StatusDuocShare);
                                                    } else if (dsLinkAnh.size() == 1) {
                                                        imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                                                        hienThiAnh(dsLinkAnh.get(0), imgAnh1StatusDuocShare);
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        tvShareChiTiet.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(XemChiTietStatus.this, "Bạn không thể chia sẻ lại status này nữa\nHãy chia sẻ bài viết gốc!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    if (clickLike[0]) {
                                        imgLikeChiTiet.setImageResource(R.drawable.full_heart);
                                        tvLikeChiTiet.setText("Bỏ thích");
                                    }else {
                                        imgLikeChiTiet.setImageResource(R.drawable.empty_heart);
                                        tvLikeChiTiet.setText("Thích");
                                    }
                                    llLikeChiTiet.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            clickLike[0] = !clickLike[0];
                                            if (clickLike[0]) {
                                                ++soLike[0];
                                                imgLikeChiTiet.setImageResource(R.drawable.full_heart);
                                                tvLikeChiTiet.setText("Bỏ thích");
                                                xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(status, "likes", soLike[0]);
                                                xuLyLikeShareComment.themNotiCongDong(hmUpdate, status, keyS, "đã thích bài viết của bạn", "so_like");
                                            }else {
                                                if (soLike[0] > 0) {
                                                    --soLike[0];
                                                    imgLikeChiTiet.setImageResource(R.drawable.empty_heart);
                                                    tvLikeChiTiet.setText("Thích");
                                                    xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(status, "likes", soLike[0]);
                                                    mDatabase.child("notification").child("statistic")
                                                            .child(status.getUsername()).child("cong_dong").child(status.getKey_status()).removeValue();
                                                }
                                            }

                                            hmUpdate.put("/status/" + nguoidung + "/" + statusCuaAi + "/" + keyS + "/clickLike", clickLike[0]);
                                            mDatabase.updateChildren(hmUpdate);
                                        }
                                    });
                                    initAdapter(keyStatus);

                                    imgSettingStatusChiTiet.setOnClickListener(myClick);
                                    imgChenAnhComment.setOnClickListener(myClick);
                                    tvThemComment.setOnClickListener(myClick);

                                    btnDangComment.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String content = edtDangComment.getText().toString().trim();
                                            Date date = Calendar.getInstance().getTime();
                                            SimpleDateFormat fr = new SimpleDateFormat("dd/MM/yyyy");
                                            String time = fr.format(date);
                                            String location = "Học viện Ăn hàng - Ở không";
                                            if (keyStatus != null) {
                                                ++soComment;
                                                dangComment(status, nguoidung, url, location,time,content,keyStatus,soComment);
                                                edtDangComment.setText("");
                                                dsBitmap.clear();
                                                rvAnhComment.setVisibility(View.GONE);
                                                rvAnhComment.removeAllViews();
                                            }
                                        }
                                    });
                                    btnHuyComment.setOnClickListener(myClick);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
        }
    }

    private void initAdapter(String key_status){
        Query queryC = mDatabase.child("comment").child(key_status);
        adapter = new AdapterCommentCha(XemChiTietStatus.this, Comment.class, R.layout.custom_rv_binh_luan_cha
                , ViewHolderComment.class, queryC, key_status, soComment, status);
        rvBinhLuanCha.setAdapter(adapter);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.imgSettingComment:
                    break;
                case R.id.tvThemComment:
                    break;
                case R.id.btnHuyComment:
                    edtDangComment.setText("");
                    dsBitmap.clear();
                    rvAnhComment.setVisibility(View.GONE);
                    rvAnhComment.removeAllViews();
                    break;
                case R.id.imgChenAnhComment:
                    rvAnhComment.setVisibility(View.VISIBLE);
                    openGallery();
                    break;
                case R.id.imgSettingStatusChiTiet:
                    PopupMenu popupMenu = new PopupMenu(XemChiTietStatus.this, imgSettingStatusChiTiet);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup_status,popupMenu.getMenu());
                    setForceShowIcon(popupMenu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int vt = item.getItemId();
                            switch (vt) {
                                case R.id.item_status_share:
                                    break;
                                case R.id.item_status_update:
                                    break;
                                case R.id.item_status_delete:
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    break;
            }
        }
    };

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }

    private void dangComment(final Status s, String username, String avatar_url, String location, String time
            , String content, final String key_status, final int soComment){
        final Comment comment = new Comment();
        comment.setUsername(username);
        comment.setAvatar_url(avatar_url);
        comment.setLocation(location);
        comment.setTime(time);
        comment.setContent(content);

        final String key = presenterDangComment.layKeyDangComment(key_status);
        final Map<String, Object> hmUpdate = new HashMap<String, Object>();

        if (dsBitmap.size()==1) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            dsBitmap.get(0).compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] array = baos.toByteArray();
            final StorageReference ref = mStorage.child("hinh_anh").child("comment").child(key_status).child(key).child("image_comment.jpg");
            ref.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                        @Override
                        public void onSuccess(StorageMetadata storageMetadata) {
                            String url = storageMetadata.getDownloadUrl() + "";
                            comment.setImage_url(url);
                            presenterDangComment.xuLyDangComment(key_status,key,comment);
                            xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(s, "comments", soComment);
                            xuLyLikeShareComment.themNotiCongDong(hmUpdate, s, key_status, "đã bình luận bài viết của bạn", "_so_comment");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("test_comment", "fail");
                }
            });
        }else {
            presenterDangComment.xuLyDangComment(key_status,key,comment);
            xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(s, "comments", soComment);
            xuLyLikeShareComment.themNotiCongDong(hmUpdate, s, key_status, "đã bình luận bài viết của bạn", "_so_comment");
        }
    }

    @Override
    public void dangCommentThanhCong() {
        Toast.makeText(XemChiTietStatus.this, "Đăng bình luận thành công!", Toast.LENGTH_SHORT).show();
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 111) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            Bitmap bitmap = hienThiBitmap.thumbNail(path);
            if (dsBitmap.size() == 0) {
                dsBitmap.add(bitmap);
                pick.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void dangStatusThanhCong() {
        Toast.makeText(XemChiTietStatus.this, "Đã chia sẻ bài viết lên tường nhà bạn và bạn bè!!!", Toast.LENGTH_SHORT).show();
    }

    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
