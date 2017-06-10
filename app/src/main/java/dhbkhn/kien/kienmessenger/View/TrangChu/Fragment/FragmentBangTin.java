package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Adapter.DanhBa.AdapterDanhBa;
import dhbkhn.kien.kienmessenger.Adapter.DanhBa.ViewHolderDanhBa;
import dhbkhn.kien.kienmessenger.Adapter.Status.AdapterRecyclerFirebaseStatus;
import dhbkhn.kien.kienmessenger.Adapter.Status.ViewHolderStatus;
import dhbkhn.kien.kienmessenger.Adapter.TrangChu.AdapterSearchBangTin;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.SearchObject;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.TrangChu.DanhSachNhomDaThamGia;
import dhbkhn.kien.kienmessenger.View.TrangChu.QuanLyCaNhan;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangCaNhan;

/**
 * Created by kiend on 10/21/2016.
 */
public class FragmentBangTin extends Fragment {
    RecyclerView rvBangTin;
    String nguoidung = "";
    String emailnguoidung = "";
    DatabaseReference mDatabase;
    DrawerLayout drawerLayoutBangTin;
    ActionBarDrawerToggle toggle;
    Toolbar toolbarBangTin;
    ImageView imgAvatarFragment;
    TextView tvUsernameFragment, tvMenuChinhSua, tvLogout, trangtin;
    RelativeLayout rlFragment;
    RecyclerView rvBanBeOnline;
    AdapterDanhBa adapterDanhBa;
    AdapterRecyclerFirebaseStatus adapterRecyclerFirebaseStatus;
    AutoCompleteTextView auto_edt_bang_tin;
    FirebaseAuth mAuth;
    ArrayList<SearchObject> dsSearch = new ArrayList<>();
    AdapterSearchBangTin adapterSearchBangTin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_bang_tin, container, false);
        drawerLayoutBangTin = (DrawerLayout) row.findViewById(R.id.drawerLayoutBanTin);
        imgAvatarFragment = (ImageView) row.findViewById(R.id.imgAvatarFragment);
        tvUsernameFragment = (TextView) row.findViewById(R.id.tvUsernameFragment);
        tvLogout = (TextView) row.findViewById(R.id.tvLogout);
        tvMenuChinhSua = (TextView) row.findViewById(R.id.tvMenuChinhSua);
        trangtin = (TextView) row.findViewById(R.id.trangtin);
        rlFragment = (RelativeLayout) row.findViewById(R.id.rlFragment);
        auto_edt_bang_tin = (AutoCompleteTextView) row.findViewById(R.id.auto_edt_bang_tin);
        rvBanBeOnline = (RecyclerView) row.findViewById(R.id.rvBanBeOnline);

        rvBanBeOnline.setLayoutManager(new LinearLayoutManager(getContext()));

        toolbarBangTin = (Toolbar) row.findViewById(R.id.toolbarBangTin);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarBangTin);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((AppCompatActivity)getActivity()).setTitle("");

        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayoutBangTin, R.string.open, R.string.close);
        toggle.syncState();

        rvBangTin = (RecyclerView) row.findViewById(R.id.rvBangTin);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mDatabase.child("search").addValueEventListener(valueSearch);

        layRaDuLieuTuIntent();
        LinearLayoutManager mn = new LinearLayoutManager(getContext());
        mn.setReverseLayout(true);
        mn.setStackFromEnd(true);
        rvBangTin.setLayoutManager(mn);

        Query queryDanhBa = mDatabase.child("users/"+nguoidung+"/friendlist").limitToFirst(30);
        adapterDanhBa = new AdapterDanhBa(getContext(), Friend.class, R.layout.custom_item_danh_ba_2, ViewHolderDanhBa.class,
                queryDanhBa, nguoidung, emailnguoidung, true);

        rvBanBeOnline.setAdapter(adapterDanhBa);

        adapterDanhBa.notifyDataSetChanged();

        rlFragment.setOnClickListener(myCLick);
        tvMenuChinhSua.setOnClickListener(myCLick);
        tvLogout.setOnClickListener(myCLick);
        trangtin.setOnClickListener(myCLick);

        layRaStatusVaThongTinCaNhan();
        return row;
    }

    private void layRaDuLieuTuIntent() {
        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
    }

    private void layRaStatusVaThongTinCaNhan(){
        mDatabase.child("users/"+nguoidung).addListenerForSingleValueEvent(laythongtincanhan);
        Query qS = mDatabase.child("status/"+nguoidung+"/cua_ban_be_toi");
        adapterRecyclerFirebaseStatus = new AdapterRecyclerFirebaseStatus(getContext(), nguoidung, Status.class,
                R.layout.custom_rv_trang_ca_nhan, ViewHolderStatus.class, qS, "cua_ban_be_toi");
        rvBangTin.setAdapter(adapterRecyclerFirebaseStatus);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private ValueEventListener valueSearch = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            dsSearch.clear();
            for (DataSnapshot post : dataSnapshot.getChildren()) {
                SearchObject searchObject = post.getValue(SearchObject.class);
                dsSearch.add(searchObject);
            }
            adapterSearchBangTin = new AdapterSearchBangTin(getContext(), R.layout.custom_item_search, dsSearch);
            auto_edt_bang_tin.setAdapter(adapterSearchBangTin);
            adapterSearchBangTin.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private View.OnClickListener myCLick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vt = v.getId();
            switch (vt) {
                case R.id.tvMenuChinhSua:
                    Intent intent = new Intent(getContext(), QuanLyCaNhan.class);
                    getContext().startActivity(intent);
                    break;
                case R.id.tvLogout:
                    mDatabase.child("users").child(nguoidung).child("online").setValue(false);
                    mAuth.signOut();
                    Intent iDangXuat = new Intent(getContext(), DangNhapMessenger.class);
                    getContext().startActivity(iDangXuat);
                    break;
                case R.id.trangtin:
                    Intent iCacNhom = new Intent(getContext(), DanhSachNhomDaThamGia.class);
                    iCacNhom.putExtra("nguoidung", nguoidung);
                    startActivity(iCacNhom);
                    break;
                case R.id.rlFragment:
                    Intent iCaNhan = new Intent(getContext(), TrangCaNhan.class);
                    startActivity(iCaNhan);
            }
        }
    };

    private ValueEventListener laythongtincanhan = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            User u = dataSnapshot.getValue(User.class);
            hienThiAnh(u.getAvatar_url(),imgAvatarFragment);
            tvUsernameFragment.setText(u.getUsername());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_drawer_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDrawerFragment:
                if (drawerLayoutBangTin.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayoutBangTin.closeDrawer(Gravity.RIGHT);
                }
                else {
                    drawerLayoutBangTin.openDrawer(Gravity.RIGHT);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hienThiAnh(String url, ImageView imageView) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(imageView);
        task.execute(url);
    }
}
