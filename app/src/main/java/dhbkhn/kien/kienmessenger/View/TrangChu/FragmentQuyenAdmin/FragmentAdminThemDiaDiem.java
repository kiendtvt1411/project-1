package dhbkhn.kien.kienmessenger.View.TrangChu.FragmentQuyenAdmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterPickImage;
import dhbkhn.kien.kienmessenger.Model.Object.Tour.DiaDiemDuLich;
import dhbkhn.kien.kienmessenger.Model.TrangChu.HienThiBitmap;
import dhbkhn.kien.kienmessenger.Presenter.Tour.DiaDiem.PresenterThemDiaDiem;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 11/5/2016.
 */
public class FragmentAdminThemDiaDiem extends Fragment implements IViewFragmnetThemDiaDiem{
    EditText edtTenDiaDiem,edtDiaChiDiaDiem, edtMoTaDiaDiem, edtQuanLyDiaDiem;
    Button btnThemAnhDiaDiem, btnXacNhanThemDiaDiem, btnHuyThemDiaDiem;
    RecyclerView rvAnhDiaDiem;
    HienThiBitmap hienThiBitmap;
    AdapterPickImage adapter;
    List<Bitmap> dsBitmap;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    PresenterThemDiaDiem presenterThemDiaDiem;
    String nguoidung="";
    String emailnguoidung="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_admin_them_dia_diem, container, false);
        edtTenDiaDiem = (EditText) row.findViewById(R.id.edtTenDiaDiem);
        edtDiaChiDiaDiem = (EditText) row.findViewById(R.id.edtDiaChiDiaDiem);
        edtMoTaDiaDiem = (EditText) row.findViewById(R.id.edtMoTaDiaDiem);
        edtQuanLyDiaDiem = (EditText) row.findViewById(R.id.edtQuanLyDiaDiem);
        btnThemAnhDiaDiem = (Button) row.findViewById(R.id.btnThemAnhDiaDiem);
        btnXacNhanThemDiaDiem = (Button) row.findViewById(R.id.btnXacNhanThemDiaDiem);
        btnHuyThemDiaDiem = (Button) row.findViewById(R.id.btnHuyThemDiaDiem);
        rvAnhDiaDiem = (RecyclerView) row.findViewById(R.id.rvAnhDiaDiem);
        rvAnhDiaDiem.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        hienThiBitmap = new HienThiBitmap(getContext());

        dsBitmap = new ArrayList<>();
        adapter = new AdapterPickImage(getActivity(), dsBitmap);
        rvAnhDiaDiem.setAdapter(adapter);

        presenterThemDiaDiem = new PresenterThemDiaDiem(this);

        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;

        btnThemAnhDiaDiem.setOnClickListener(myClick);
        btnXacNhanThemDiaDiem.setOnClickListener(myClick);
        btnHuyThemDiaDiem.setOnClickListener(myClick);
        return row;
    }

    private View.OnClickListener myClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vt = v.getId();
            switch (vt) {
                case R.id.btnThemAnhDiaDiem:
                    rvAnhDiaDiem.setVisibility(View.VISIBLE);
                    openGallery();
                    break;
                case R.id.btnHuyThemDiaDiem:
                    dsBitmap.clear();
                    rvAnhDiaDiem.setVisibility(View.GONE);
                    rvAnhDiaDiem.removeAllViews();
                    Intent iTrangChuHuy = new Intent(getContext(), TrangChu.class);
                    getActivity().startActivity(iTrangChuHuy);
                    break;
                case R.id.btnXacNhanThemDiaDiem:
                    String loaiDiaDiem = "van_hoa";
                    String key = presenterThemDiaDiem.layKeyDiaDiem(loaiDiaDiem);
                    themDuLieuDiaDiem(key, loaiDiaDiem);
                    dsBitmap.clear();
                    rvAnhDiaDiem.setVisibility(View.GONE);
                    rvAnhDiaDiem.removeAllViews();
                    Intent iTrangChu = new Intent(getContext(), TrangChu.class);
                    getActivity().startActivity(iTrangChu);
                    break;
            }
        }
    };

    private void themDuLieuDiaDiem(final String keyDiaDiem, final String loaiDiaDiem){
        final DiaDiemDuLich diaDiemDuLich = new DiaDiemDuLich();
        diaDiemDuLich.setTen_dia_diem(edtTenDiaDiem.getText().toString().trim());
        diaDiemDuLich.setDia_chi(edtDiaChiDiaDiem.getText().toString().trim());
        diaDiemDuLich.setMo_ta(edtMoTaDiaDiem.getText().toString().trim());
        diaDiemDuLich.setChu_dia_diem(edtQuanLyDiaDiem.getText().toString().trim());
        diaDiemDuLich.setKey_dia_diem(keyDiaDiem);
        final List<String> listUrl = new ArrayList<>();

        if (dsBitmap.size() > 0) {
            for (final Bitmap bm : dsBitmap) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] array = baos.toByteArray();
                final StorageReference ref = mStorage.child("hinh_anh").child("dia_diem")
                        .child(loaiDiaDiem).child(keyDiaDiem).child("image"+dsBitmap.indexOf(bm)+".jpg");
                ref.putBytes(array).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                            @Override
                            public void onSuccess(StorageMetadata storageMetadata) {
                                String url = storageMetadata.getDownloadUrl() + "";
                                listUrl.add(url);
                                if (dsBitmap.indexOf(bm) == (dsBitmap.size() - 1)) {
                                    diaDiemDuLich.setList_hinh_anh_url(listUrl);
                                    presenterThemDiaDiem.themDiaDiemDuLich(loaiDiaDiem,keyDiaDiem,diaDiemDuLich);
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
            presenterThemDiaDiem.themDiaDiemDuLich(loaiDiaDiem,keyDiaDiem,diaDiemDuLich);
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,999);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK && requestCode == 999) {
            Uri uri = data.getData();
            String path = hienThiBitmap.getGalleryPath(uri);
            Bitmap bm = hienThiBitmap.thumbNail(path);
            dsBitmap.add(bm);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void themDiaDiemThanhCong() {
        Toast.makeText(getContext(), "Thêm địa điểm thành công", Toast.LENGTH_SHORT).show();
    }
}
