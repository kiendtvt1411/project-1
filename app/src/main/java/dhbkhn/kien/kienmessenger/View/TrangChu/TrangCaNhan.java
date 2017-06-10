package dhbkhn.kien.kienmessenger.View.TrangChu;

import android.app.ProgressDialog;
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
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterPickImage;
import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterRecyclerFirebaseStatus;
import dhbkhn.kien.kienmessenger.Adapter.Status.ViewHolderStatus;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Presenter.Status.PresenterDangStatus;
import dhbkhn.kien.kienmessenger.Presenter.TrangChu.FragmentCaNhan.PresenterCaNhan;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.Status.IViewDangStatus;

public class TrangCaNhan extends AppCompatActivity implements IViewDangStatus{
    ImageView imgAvatarCaNhan;
    RecyclerView rvAnhStatus;
    TextView tvTenTrangCaNhan;
    EditText edtDangCaNhan;
    Button btnDangStatusCaNhan, btnHuyStatusCaNhan;
    ImageButton imgChenIcon, imgChenAnh, imgChenTep;
    RecyclerView rvCaNhan;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    String nguoidung = "";
    String emailnguoidung="";
    PresenterCaNhan presenterCaNhan;
    PresenterDangStatus presenterDangStatus;
    String url = "";
    HienThiBitmap hienThiBitmap;
    AdapterPickImage adapter;
    List<Bitmap> dsBitmap;
    AdapterRecyclerFirebaseStatus adapterRecyclerFirebaseStatus;
    ProgressDialog prd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);
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
        rvAnhStatus.setLayoutManager(new LinearLayoutManager(TrangCaNhan.this,LinearLayoutManager.HORIZONTAL,false));
        edtDangCaNhan = (EditText) findViewById(R.id.edtDangCaNhan);
        btnDangStatusCaNhan = (Button) findViewById(R.id.btnDangStatusCaNhan);
        btnHuyStatusCaNhan = (Button) findViewById(R.id.btnHuyStatusCaNhan);
        imgChenIcon = (ImageButton) findViewById(R.id.imgChenIcon);
        imgChenAnh = (ImageButton) findViewById(R.id.imgChenAnh);
        imgChenTep = (ImageButton) findViewById(R.id.imgChenTep);
        rvCaNhan = (RecyclerView) findViewById(R.id.rvCaNhan);

        LinearLayoutManager mn = new LinearLayoutManager(TrangCaNhan.this);
        mn.setReverseLayout(true);
        rvCaNhan.setLayoutManager(mn);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        hienThiBitmap = new HienThiBitmap(TrangCaNhan.this);
        presenterCaNhan = new PresenterCaNhan();
        presenterDangStatus = new PresenterDangStatus(this);

        dsBitmap = new ArrayList<>();
        adapter = new AdapterPickImage(TrangCaNhan.this, dsBitmap);
        rvAnhStatus.setAdapter(adapter);
    }

    private void layRaStatus(){
        Query qS = mDatabase.child("status/"+nguoidung+"/cua_toi");
        adapterRecyclerFirebaseStatus = new AdapterRecyclerFirebaseStatus(TrangCaNhan.this, nguoidung, Status.class,
                R.layout.custom_rv_trang_ca_nhan, ViewHolderStatus.class, qS, "cua_toi");
        rvCaNhan.setAdapter(adapterRecyclerFirebaseStatus);
    }

    private void layRaDuLieuTuIntent() {
        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
    }

    private void addEvents() {
        presenterCaNhan.layRaUsernameTrangCaNhan(nguoidung,tvTenTrangCaNhan);
        mDatabase.child("users").child(nguoidung).child("avatar_url").addValueEventListener(new ValueEventListener() {
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
        edtDangCaNhan.setHint("Xin chào " + nguoidung + "! Hôm nay bạn cảm thấy như thế nào? Hãy cập nhật trạng thái nhé!");
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
                    prd = new ProgressDialog(TrangCaNhan.this);
                    prd.setTitle("Đang thực hiện đăng status");
                    prd.setMessage("Vui lòng chờ...");
                    prd.show();
                    String content = edtDangCaNhan.getText().toString().trim();
                    dangStatus(nguoidung,content,url,"Ha Noi","Cong khai");
                    edtDangCaNhan.setText("");
                    dsBitmap.clear();
                    rvAnhStatus.setVisibility(View.GONE);
                    rvAnhStatus.removeAllViews();
                    break;
                case R.id.btnHuyStatusCaNhan:
                    dsBitmap.clear();
                    rvAnhStatus.setVisibility(View.GONE);
                    rvAnhStatus.removeAllViews();
                    edtDangCaNhan.setText("");
                    edtDangCaNhan.setHint("Xin chào " + nguoidung + "! Hôm nay bạn cảm thấy như thế nào? Hãy cập nhật trạng thái nhé!");
                    break;
                case R.id.imgChenIcon:
                    break;
                case R.id.imgChenAnh:
                    rvAnhStatus.setVisibility(View.VISIBLE);
                    openGallery();
                    break;
                case R.id.imgChenTep:
                    break;

            }
        }
    };

    private void dangStatus(String username, String content, String avatar_url, String location, String type){
        try {
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
                    final StorageReference ref = mStorage.child("hinh_anh").child("status").child(nguoidung).child(key).child("image"+dsBitmap.indexOf(bm)+".jpg");
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
                                        presenterDangStatus.dangStatusCaNhan(nguoidung,key,newStatus);
                                        prd.dismiss();
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
                presenterDangStatus.dangStatusCaNhan(nguoidung,key,newStatus);
                prd.dismiss();
            }
        } catch (Exception ex) {
            Log.d("loiroi", ex.toString());
            Toast.makeText(TrangCaNhan.this, "Dung lượng bộ nhớ quá đầy!!!\nKhông thực hiện thao tác này được!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void dangStatusThanhCong() {
        Toast.makeText(TrangCaNhan.this, "Đăng Status thành công!", Toast.LENGTH_SHORT).show();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 123) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            Bitmap bm = hienThiBitmap.thumbNail(path);
            dsBitmap.add(bm);
            adapter.notifyDataSetChanged();
        }
    }
}
