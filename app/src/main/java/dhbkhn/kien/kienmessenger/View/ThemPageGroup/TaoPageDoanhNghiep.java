package dhbkhn.kien.kienmessenger.View.ThemPageGroup;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Adapter.PageDoanhNghiep.ViewPagerAdapterDoanhNghiep;
import dhbkhn.kien.kienmessenger.Model.Object.LoaiNguoiDung.PageDoanhNghiep;
import dhbkhn.kien.kienmessenger.Model.QuyenAdmin.XuLyThemCongTy;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

public class TaoPageDoanhNghiep extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap mMap;
    TabLayout tabDoanhNghiep;
    ViewPager viewPagerDoanhNghiep;
    ViewPagerAdapterDoanhNghiep viewPagerAdapterDoanhNghiep;
    ImageView imgAnhBiaDoanhNghiepTest, imgAvatarDoanhNghiepTest;
    TextView tvTenTrangDoanhNghiepTest, tvViTriDoanhNghiepTest, tvMoTaDoanhNghiepTest, tvDiaChiDoanhNghiepTest;
    EditText edtMoTaDoanhNghiepTest, edtDiaChiDoanhNghiepTest, edtTenTrangDoanhNghiepTest;
    Button btnXacNhanTaoDoanhNghiep, btnHuyTaoDoanhNghiep;
    HienThiBitmap hienThiBitmap;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    String nguoidung = "";
    String emailnguoidung = "";
    Bitmap bm1 = null;
    Bitmap bm2 = null;
    ProgressDialog progressDialog;
    XuLyThemCongTy xuLyThemCongTy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_page_doanh_nghiep);
        addControls();
        addEvents();
    }

    private void addControls() {
        tabDoanhNghiep = (TabLayout) findViewById(R.id.tabDoanhNghiep);
        viewPagerDoanhNghiep = (ViewPager) findViewById(R.id.viewPagerDoanhNghiep);
        viewPagerAdapterDoanhNghiep = new ViewPagerAdapterDoanhNghiep(getSupportFragmentManager(), this);
        viewPagerDoanhNghiep.setAdapter(viewPagerAdapterDoanhNghiep);
        tabDoanhNghiep.setupWithViewPager(viewPagerDoanhNghiep);

        imgAnhBiaDoanhNghiepTest = (ImageView) findViewById(R.id.imgAnhBiaDoanhNghiepTest);
        imgAvatarDoanhNghiepTest = (ImageView) findViewById(R.id.imgAvatarDoanhNghiepTest);
        tvTenTrangDoanhNghiepTest = (TextView) findViewById(R.id.tvTenTrangDoanhNghiepTest);
        tvViTriDoanhNghiepTest = (TextView) findViewById(R.id.tvViTriDoanhNghiepTest);
        tvMoTaDoanhNghiepTest = (TextView) findViewById(R.id.tvMoTaDoanhNghiepTest);
        tvDiaChiDoanhNghiepTest = (TextView) findViewById(R.id.tvDiaChiDoanhNghiepTest);
        edtMoTaDoanhNghiepTest = (EditText) findViewById(R.id.edtMoTaDoanhNghiepTest);
        edtDiaChiDoanhNghiepTest = (EditText) findViewById(R.id.edtDiaChiDoanhNghiepTest);
        edtTenTrangDoanhNghiepTest = (EditText) findViewById(R.id.edtTenTrangDoanhNghiepTest);
        btnXacNhanTaoDoanhNghiep = (Button) findViewById(R.id.btnXacNhanTaoDoanhNghiep);
        btnHuyTaoDoanhNghiep = (Button) findViewById(R.id.btnHuyTaoDoanhNghiep);
        hienThiBitmap = new HienThiBitmap(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        xuLyThemCongTy = new XuLyThemCongTy();

        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapDoanhNghiep);
        mapFragment.getMapAsync(this);
    }

    private void addEvents() {
        edtTenTrangDoanhNghiepTest.setOnFocusChangeListener(myFocus);
        edtDiaChiDoanhNghiepTest.setOnFocusChangeListener(myFocus);
        imgAnhBiaDoanhNghiepTest.setOnClickListener(myClick);
        imgAvatarDoanhNghiepTest.setOnClickListener(myClick);
        btnXacNhanTaoDoanhNghiep.setOnClickListener(myClick);
        btnHuyTaoDoanhNghiep.setOnClickListener(myClick);
    }

    private void themDuLieuDoanhNghiep(){
        if (ktraNhapDuLieu()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Đang tạo nhóm");
            progressDialog.setMessage("Xin chờ xử lý...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            final String key_page_dn = mDatabase.child("cong_ty_du_lich").child("thong_tin_so_luoc").push().getKey();
            final PageDoanhNghiep pagedn = new PageDoanhNghiep();
            pagedn.setTen_cty(edtTenTrangDoanhNghiepTest.getText().toString().trim());
            pagedn.setGiam_doc_cty(nguoidung);
            pagedn.setDiachi_cty(edtDiaChiDoanhNghiepTest.getText().toString().trim());
            pagedn.setMo_ta_cty(edtMoTaDoanhNghiepTest.getText().toString().trim());
            pagedn.setKey_page_cty(key_page_dn);
            Map<String, Object> hmPage = pagedn.toMap();
            final Map<String, Object> hm = new HashMap<>();
            hm.put("/cong_ty_du_lich/thong_tin_so_luoc/" + key_page_dn, hmPage);
            mDatabase.updateChildren(hm);

            if (bm1 != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm1.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] array1 = baos.toByteArray();
                final StorageReference ref = mStorage.child("hinh_anh").child("page").child("doanh_nghiep").child(key_page_dn)
                        .child("anh_bia_page_dn.jpg");
                ref.putBytes(array1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                            @Override
                            public void onSuccess(StorageMetadata storageMetadata) {
                                mDatabase.child("/cong_ty_du_lich/thong_tin_so_luoc/"+key_page_dn+
                                "/anh_bia_url_cty").setValue(storageMetadata.getDownloadUrl() + "");
                            }
                        });
                    }
                });
            }
            if (bm2 != null) {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                bm2.compress(Bitmap.CompressFormat.PNG, 100, baos2);
                byte[] array2 = baos2.toByteArray();
                final StorageReference ref2 = mStorage.child("hinh_anh").child("page").child("doanh_nghiep").child(key_page_dn)
                        .child("avatar_page_dn.jpg");
                ref2.putBytes(array2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref2.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                            @Override
                            public void onSuccess(StorageMetadata storageMetadata) {
                                mDatabase.child("/cong_ty_du_lich/thong_tin_so_luoc/"+key_page_dn+
                                        "/avatar_url_cty").setValue(storageMetadata.getDownloadUrl() + "");
                                progressDialog.dismiss();
                            }
                        });
                    }
                });
            }
//            CongTyDuLich congTyDuLich = new CongTyDuLich();
//            congTyDuLich.setTen_cong_ty(edtTenTrangDoanhNghiepTest.getText().toString().trim());
//            congTyDuLich.setDia_chi(edtDiaChiDoanhNghiepTest.getText().toString().trim());
//            congTyDuLich.setMo_ta(edtMoTaDoanhNghiepTest.getText().toString().trim());
//            congTyDuLich.setSdt("0969435986");
//            congTyDuLich.setGiam_doc(nguoidung);
//            congTyDuLich.setWeb("kienvipdz.com.vn");
//            xuLyThemCongTy.themCongTyMoi(congTyDuLich);
        }else {
            Toast.makeText(TaoPageDoanhNghiep.this, "Bạn cần nhập đủ 3 mục!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery(int code){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,code);
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vt = v.getId();
            switch (vt) {
                case R.id.btnXacNhanTaoDoanhNghiep:
                    themDuLieuDoanhNghiep();
                    Intent iTrangChu = new Intent(TaoPageDoanhNghiep.this, TrangChu.class);
                    Toast.makeText(TaoPageDoanhNghiep.this, "Tạo thành công, chờ kiểm duyệt!", Toast.LENGTH_SHORT).show();
                    startActivity(iTrangChu);
                    break;
                case R.id.btnHuyTaoDoanhNghiep:
                    Intent iBack = new Intent(TaoPageDoanhNghiep.this, TrangChu.class);
                    startActivity(iBack);
                    break;
                case R.id.imgAnhBiaDoanhNghiepTest:
                    openGallery(99);
                    break;
                case R.id.imgAvatarDoanhNghiepTest:
                    openGallery(100);
                    break;
            }
        }
    };

    private boolean ktraNhapDuLieu(){
        if (edtTenTrangDoanhNghiepTest.equals("") || edtMoTaDoanhNghiepTest.equals("") || edtDiaChiDoanhNghiepTest.equals("")) {
            return false;
        }else {
            return true;
        }
    }

    private View.OnFocusChangeListener myFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v.getId() == R.id.edtTenTrangDoanhNghiepTest) {
                if (!hasFocus && !edtTenTrangDoanhNghiepTest.equals("")) {
                    String ten = edtTenTrangDoanhNghiepTest.getText().toString().trim();
                    tvTenTrangDoanhNghiepTest.setVisibility(View.VISIBLE);
                    tvTenTrangDoanhNghiepTest.setText(ten);
                    edtTenTrangDoanhNghiepTest.setVisibility(View.GONE);
                }
            } else if (v.getId() == R.id.edtDiaChiDoanhNghiepTest) {
                if (!hasFocus && !edtDiaChiDoanhNghiepTest.equals("")) {
                    String dchi = edtDiaChiDoanhNghiepTest.getText().toString().trim();
                    tvViTriDoanhNghiepTest.setVisibility(View.VISIBLE);
                    tvViTriDoanhNghiepTest.setText(dchi);
                }
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng doican = new LatLng(21.035557, 105.816578);
        mMap.addMarker(new MarkerOptions().position(doican).title("Nha cua Kien la day!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doican,16.0f));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 99) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            bm1 = hienThiBitmap.thumbNail(path);
            imgAnhBiaDoanhNghiepTest.setImageBitmap(bm1);
        } else if (resultCode == RESULT_OK && requestCode == 100) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            bm2 = hienThiBitmap.thumbNail(path);
            imgAvatarDoanhNghiepTest.setImageBitmap(bm2);
        }
    }
}
