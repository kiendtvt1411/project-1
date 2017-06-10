package dhbkhn.kien.kienmessenger.View.ThemPageGroup.Tour;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.Tour.AdapterListLoTrinh;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.ChiDeShow;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.TourDuLich;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Presenter.Tour.Tour.PresenterThemTour;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.TrangChu.DanhSachNhomDaThamGia;

public class ThemTourMoi extends AppCompatActivity implements IViewThemTourMoi{
    ImageView imgAnhBiaThemTour;
    EditText edtTenThemTour,edtNguoiToChucThemTour,edtGiaThemTour,edtSDTThemTour, edtMoTaThemTour;
    Spinner spinnerPhuongTienThemTour,spinnerThoiGianThemTour,spinnerDiemXuatPhatThemTour,spinnerDiemKetThucThemTour,spinnerThemLoTrinhThemTour;
    Button btnNgayXuatPhatThemTour,btnThemLoTrinhThemTour, btnHuyThemTour,btnDongYThemTour;
    RecyclerView rvLoTrinhThemTour;
    AdapterListLoTrinh adapterLT;
    TextView tvNgayXuatPhat;
    DatabaseReference mDatabase;
    List<String> dsPhuongTien;
    List<String> dsThoiGian;
    static List<String> dsLoTrinh = new ArrayList<>();
    static List<String> dsKeyLoTrinh = new ArrayList<>();
    List<String> list_lo_trinh = new ArrayList<>();
    List<String> list_key_lo_trinh = new ArrayList<>();
    PresenterThemTour presenterThemTour;
    HienThiBitmap hienThiBitmap;
    Bitmap bmAnhBia = null;
    StorageReference mStorage;
    String keyCongTy = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tour_moi);
        addControls();
        addEvents();
    }

    private void addControls() {
        imgAnhBiaThemTour = (ImageView) findViewById(R.id.imgAnhBiaThemTour);
        edtTenThemTour = (EditText) findViewById(R.id.edtTenThemTour);
        edtNguoiToChucThemTour = (EditText) findViewById(R.id.edtNguoiToChucThemTour);
        edtGiaThemTour = (EditText) findViewById(R.id.edtGiaThemTour);
        edtSDTThemTour = (EditText) findViewById(R.id.edtSDTThemTour);
        edtMoTaThemTour = (EditText) findViewById(R.id.edtMoTaThemTour);
        spinnerPhuongTienThemTour = (Spinner) findViewById(R.id.spinnerPhuongTienThemTour);
        spinnerThoiGianThemTour = (Spinner) findViewById(R.id.spinnerThoiGianThemTour);
        spinnerDiemXuatPhatThemTour = (Spinner) findViewById(R.id.spinnerDiemXuatPhatThemTour);
        spinnerDiemKetThucThemTour = (Spinner) findViewById(R.id.spinnerDiemKetThucThemTour);
        spinnerThemLoTrinhThemTour = (Spinner) findViewById(R.id.spinnerThemLoTrinhThemTour);
        btnNgayXuatPhatThemTour = (Button) findViewById(R.id.btnNgayXuatPhatThemTour);
        btnThemLoTrinhThemTour = (Button) findViewById(R.id.btnThemLoTrinhThemTour);
        btnHuyThemTour = (Button) findViewById(R.id.btnHuyThemTour);
        btnDongYThemTour = (Button) findViewById(R.id.btnDongYThemTour);
        rvLoTrinhThemTour = (RecyclerView) findViewById(R.id.rvLoTrinhThemTour);
        rvLoTrinhThemTour.setLayoutManager(new LinearLayoutManager(this));
        tvNgayXuatPhat = (TextView) findViewById(R.id.tvNgayXuatPhat);

        hienThiBitmap = new HienThiBitmap(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        presenterThemTour = new PresenterThemTour(this);
        keyCongTy = getIntent().getStringExtra("keyCongTy");

        dsPhuongTien = new ArrayList<>();
        dsThoiGian = new ArrayList<>();

        adapterLT = new AdapterListLoTrinh(this, list_lo_trinh, list_key_lo_trinh);
        rvLoTrinhThemTour.setAdapter(adapterLT);
        adapterLT.notifyDataSetChanged();
    }

    private void addEvents() {
        imgAnhBiaThemTour.setOnClickListener(myCLick);
        setUpSpinner();
        setUpButton();
    }

    private void setUpButton() {
        btnNgayXuatPhatThemTour.setOnClickListener(myCLick);
        btnThemLoTrinhThemTour.setOnClickListener(myCLick);
        btnDongYThemTour.setOnClickListener(myCLick);
        btnHuyThemTour.setOnClickListener(myCLick);
    }

    private void setUpSpinner() {
        dsPhuongTien.add("Ô tô");
        dsPhuongTien.add("Máy bay");
        dsPhuongTien.add("Tàu hỏa");
        dsPhuongTien.add("Tàu thủy");

        ArrayAdapter<String> adapterSpinnerPhuongTien = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dsPhuongTien);
        spinnerPhuongTienThemTour.setAdapter(adapterSpinnerPhuongTien);

        for(int i = 1;i<31;i++) {
            dsThoiGian.add(i + " ngày");
        }

        ArrayAdapter<String> adapterSpinnerThoiGian = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dsThoiGian);
        spinnerThoiGianThemTour.setAdapter(adapterSpinnerThoiGian);

        mDatabase.child("dia_diem").child("chi_de_hien_thi").child("van_hoa").addListenerForSingleValueEvent(myListener);
    }

    private View.OnClickListener myCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vt = v.getId();
            switch (vt) {
                case R.id.imgAnhBiaThemTour:
                    openGallery();
                    break;
                case R.id.btnNgayXuatPhatThemTour:
                    showDatePickerDialog(tvNgayXuatPhat);
                    break;
                case R.id.btnDongYThemTour:
                    themTourMoi(keyCongTy);
                    Intent iTrangChu = new Intent(ThemTourMoi.this, DanhSachNhomDaThamGia.class);
                    startActivity(iTrangChu);
                    break;
                case R.id.btnHuyThemTour:
                    break;
                case R.id.btnThemLoTrinhThemTour:
                    String vitri = dsKeyLoTrinh.get(spinnerThemLoTrinhThemTour.getSelectedItemPosition());
                    Log.d("myTour", vitri);
                    if (list_key_lo_trinh.indexOf(dsKeyLoTrinh.get(spinnerThemLoTrinhThemTour.getSelectedItemPosition())) < 0) {
                        list_key_lo_trinh.add(dsKeyLoTrinh.get(spinnerThemLoTrinhThemTour.getSelectedItemPosition()));
                        list_lo_trinh.add(spinnerThemLoTrinhThemTour.getSelectedItem().toString());
                        adapterLT.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    private AdapterView.OnItemSelectedListener mySpinnerXuatPhat = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.d("myTOur", dsKeyLoTrinh.get(position) + dsLoTrinh.get(position));
            if (list_key_lo_trinh.indexOf(dsKeyLoTrinh.get(position))<0) {
                list_key_lo_trinh.add(dsKeyLoTrinh.get(position));
                list_lo_trinh.add(dsLoTrinh.get(position));
                Log.d("myTOur", dsKeyLoTrinh.get(position) + dsLoTrinh.get(position));
                adapterLT.notifyDataSetChanged();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener mySpinnerKetThuc = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener mySpinnerThemLoTrinh = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void themTourMoi(final String keyCongTy){
        final TourDuLich tour = new TourDuLich();
        tour.setGia_tien(Integer.parseInt(edtGiaThemTour.getText().toString().trim()));
        tour.setTen_tour(edtTenThemTour.getText().toString().trim());
        tour.setLien_he(edtNguoiToChucThemTour.getText().toString().trim());
        tour.setSdt(edtSDTThemTour.getText().toString().trim());
        tour.setMo_ta(edtMoTaThemTour.getText().toString().trim());
        tour.setPhuong_tien(spinnerPhuongTienThemTour.getSelectedItem().toString().trim());
        tour.setThoi_gian(spinnerThoiGianThemTour.getSelectedItem().toString().trim());
        tour.setList_lo_trinh(list_key_lo_trinh);
        if (tvNgayXuatPhat.getText().toString() != null && !tvNgayXuatPhat.getText().toString().equals("")) {
            tour.setXuat_phat(tvNgayXuatPhat.getText().toString().trim());
        }
        final String keyTour = presenterThemTour.layKeyTour("");
        tour.setKey_tour(keyTour);
        if (bmAnhBia != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmAnhBia.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] array = baos.toByteArray();
            final StorageReference ref = mStorage.child("hinh_anh").child("cong_ty").child(keyCongTy).child("tour")
                    .child(keyTour).child("anh_bia_tour.jpg");
            ref.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                        @Override
                        public void onSuccess(StorageMetadata storageMetadata) {
                            String url = storageMetadata.getDownloadUrl() + "";
                            tour.setAnh_bia_url(url);
                            presenterThemTour.themTour(keyCongTy,keyTour,tour);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        else {
            presenterThemTour.themTour(keyCongTy,keyTour,tour);
        }
    }

    private ValueEventListener myListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                ChiDeShow chiDeShow = post.getValue(ChiDeShow.class);
                String tendd = chiDeShow.getTen_dd();
                String keydd = chiDeShow.getKey_dd();
                dsLoTrinh.add(tendd);
                dsKeyLoTrinh.add(keydd);
            }
            Log.d("mYTour", dsLoTrinh.size() + " - " + dsKeyLoTrinh.size());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ThemTourMoi.this, android.R.layout.simple_spinner_dropdown_item, dsLoTrinh);

            spinnerThemLoTrinhThemTour.setAdapter(adapter);
            spinnerDiemXuatPhatThemTour.setAdapter(adapter);
            spinnerDiemKetThucThemTour.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            spinnerThemLoTrinhThemTour.setOnItemSelectedListener(mySpinnerThemLoTrinh);
            spinnerDiemXuatPhatThemTour.setOnItemSelectedListener(mySpinnerXuatPhat);
            spinnerDiemKetThucThemTour.setOnItemSelectedListener(mySpinnerKetThuc);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.child("dia_diem").child("chi_de_hien_thi").child("van_hoa").removeEventListener(myListener);
    }

    @Override
    public void themTourThanhCong() {
        Toast.makeText(ThemTourMoi.this, "Thêm tour mới thành công!!!", Toast.LENGTH_SHORT).show();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,101);
    }

    private void showDatePickerDialog(final TextView tv) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThemTourMoi.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tv.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 101) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            bmAnhBia = hienThiBitmap.thumbNail(path);
            imgAnhBiaThemTour.setImageBitmap(bmAnhBia);
        }
    }
}
