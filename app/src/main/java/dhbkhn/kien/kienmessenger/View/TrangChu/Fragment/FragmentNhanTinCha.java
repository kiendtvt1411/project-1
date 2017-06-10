package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dhbkhn.kien.kienmessenger.Adapter.TrangChu.AdapterSearchDanhBa;
import dhbkhn.kien.kienmessenger.Adapter.ViewPagerAdapter;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.ThemNhomChat.ThemNhomChat;

/**
 * Created by kiend on 11/12/2016.
 */
public class FragmentNhanTinCha extends Fragment {
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPagerChaNhanTin;
    TextView tvLichSuChaNhanTin,tvNhomChaNhanTin,tvDanhBaChaNhanTin;
    ImageView imgNewMess;
    AutoCompleteTextView auto_edt_cha_nhan_tin;
    AdapterSearchDanhBa adapterSearchDanhBa;
    String nguoidung = "";
    String email = "";
    ArrayList<Friend>dsDanhBa = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_cha_nhan_tin, container, false);
        tvLichSuChaNhanTin = (TextView) row.findViewById(R.id.tvLichSuChaNhanTin);
        tvNhomChaNhanTin = (TextView) row.findViewById(R.id.tvNhomChaNhanTin);
        tvDanhBaChaNhanTin = (TextView) row.findViewById(R.id.tvDanhBaChaNhanTin);
        imgNewMess = (ImageView) row.findViewById(R.id.imgNewMess);
        auto_edt_cha_nhan_tin = (AutoCompleteTextView) row.findViewById(R.id.auto_edt_cha_nhan_tin);

        nguoidung = DangNhapMessenger.nguoidung;
        email = DangNhapMessenger.emailnguoidung;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(nguoidung).child("friendlist").addValueEventListener(myValueListen);

        viewPagerChaNhanTin = (ViewPager) row.findViewById(R.id.viewPagerChaNhanTin);
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerChaNhanTin.setAdapter(viewPagerAdapter);
        viewPagerChaNhanTin.setCurrentItem(0);


        tvLichSuChaNhanTin.setOnClickListener(myCLick);
        tvNhomChaNhanTin.setOnClickListener(myCLick);
        tvDanhBaChaNhanTin.setOnClickListener(myCLick);
        imgNewMess.setOnClickListener(myCLick);

        viewPagerChaNhanTin.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        chonTab0();
                        break;
                    case 1:
                        chonTab1();
                        break;
                    case 2:
                        chonTab2();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return row;
    }

    private ValueEventListener myValueListen = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            dsDanhBa.clear();
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                Friend f = post.getValue(Friend.class);
                dsDanhBa.add(f);
            }
            adapterSearchDanhBa = new AdapterSearchDanhBa(getContext(), R.layout.custom_item_danh_ba, dsDanhBa);
            auto_edt_cha_nhan_tin.setAdapter(adapterSearchDanhBa);
            adapterSearchDanhBa.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void chonTab0(){
        tvLichSuChaNhanTin.setTextColor(layMau(R.color.colorWhite));
        tvLichSuChaNhanTin.setBackground(layDuongVien(R.drawable.tab));
        tvNhomChaNhanTin.setTextColor(layMau(R.color.colorToolbar));
        tvNhomChaNhanTin.setBackground(layDuongVien(R.drawable.duong_vien_khong_corner));
        tvDanhBaChaNhanTin.setTextColor(layMau(R.color.colorToolbar));
        tvDanhBaChaNhanTin.setBackground(layDuongVien(R.drawable.duong_vien_khong_corner));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void chonTab1(){
        tvLichSuChaNhanTin.setTextColor(layMau(R.color.colorToolbar));
        tvLichSuChaNhanTin.setBackground(layDuongVien(R.drawable.duong_vien_khong_corner));
        tvNhomChaNhanTin.setTextColor(layMau(R.color.colorWhite));
        tvNhomChaNhanTin.setBackground(layDuongVien(R.drawable.tab));
        tvDanhBaChaNhanTin.setTextColor(layMau(R.color.colorToolbar));
        tvDanhBaChaNhanTin.setBackground(layDuongVien(R.drawable.duong_vien_khong_corner));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void chonTab2(){
        tvLichSuChaNhanTin.setTextColor(layMau(R.color.colorToolbar));
        tvLichSuChaNhanTin.setBackground(layDuongVien(R.drawable.duong_vien_khong_corner));
        tvNhomChaNhanTin.setTextColor(layMau(R.color.colorToolbar));
        tvNhomChaNhanTin.setBackground(layDuongVien(R.drawable.duong_vien_khong_corner));
        tvDanhBaChaNhanTin.setTextColor(layMau(R.color.colorWhite));
        tvDanhBaChaNhanTin.setBackground(layDuongVien(R.drawable.tab));
    }

    private View.OnClickListener myCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vt = v.getId();
            switch (vt) {
                case R.id.tvLichSuChaNhanTin:
                    chonTab0();
                    viewPagerChaNhanTin.setCurrentItem(0);
                    break;
                case R.id.tvNhomChaNhanTin:
                    chonTab1();
                    viewPagerChaNhanTin.setCurrentItem(1);
                    break;
                case R.id.tvDanhBaChaNhanTin:
                    chonTab2();
                    viewPagerChaNhanTin.setCurrentItem(2);
                    break;
                case R.id.imgNewMess:
                    Intent iAddGr = new Intent(getContext(), ThemNhomChat.class);
                    startActivity(iAddGr);
                    break;
            }
        }
    };

    private Drawable layDuongVien(int idDw){
        Drawable drawble;
        if (Build.VERSION.SDK_INT > 21) {
            drawble = ContextCompat.getDrawable(getContext(), idDw);
        }else {
            drawble = getResources().getDrawable(idDw);
        }
        return drawble;
    }

    private int layMau(int idDw){
        int drawble;
        if (Build.VERSION.SDK_INT > 21) {
            drawble = ContextCompat.getColor(getContext(), idDw);
        }else {
            drawble = getResources().getColor(idDw);
        }
        return drawble;
    }
}
