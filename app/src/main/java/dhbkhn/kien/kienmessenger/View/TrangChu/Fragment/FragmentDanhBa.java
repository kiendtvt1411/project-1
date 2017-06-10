package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dhbkhn.kien.kienmessenger.Adapter.DanhBa.AdapterDanhBa;
import dhbkhn.kien.kienmessenger.Adapter.DanhBa.ViewHolderDanhBa;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

/**
 * Created by kiend on 9/22/2016.
 */
public class FragmentDanhBa extends Fragment {
    DatabaseReference mDatabase;
    RecyclerView rvDanhBa;
    AdapterDanhBa adapterDanhBa;
    LinearLayoutManager linearLayoutManager;
    String nguoiDung="";
    String emailNguoiDung ="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_danh_ba, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rvDanhBa = (RecyclerView) view.findViewById(R.id.rvDanhBa);

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvDanhBa.setLayoutManager(linearLayoutManager);

        nguoiDung = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;

        Query queryDanhBa = mDatabase.child("users/"+nguoiDung+"/friendlist");
        adapterDanhBa = new AdapterDanhBa(getContext(), Friend.class, R.layout.custom_item_danh_ba, ViewHolderDanhBa.class,
                queryDanhBa, nguoiDung, emailNguoiDung, false);

        rvDanhBa.setAdapter(adapterDanhBa);

        adapterDanhBa.notifyDataSetChanged();
        registerForContextMenu(rvDanhBa);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_context_xoa_ban, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((AdapterDanhBa)rvDanhBa.getAdapter()).getIndex();
        } catch (Exception e) {
            Log.d("MyException", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.item_xoa_ban:
                String friend = adapterDanhBa.getItem(position).getUsername_friend();
                cauHoiCuoi(friend);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void cauHoiCuoi(final String friend) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa bạn bè");
        builder.setMessage("Bạn có muốn xóa bạn bè này khỏi danh sách bạn bè không?");
        builder.setNegativeButton("Không", null);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xoaBan(friend,nguoiDung);
                xoaBan(nguoiDung,friend);
            }
        });
        builder.create().show();
    }

    private void xoaBan(final String minh, String ban) {
        mDatabase.child("users").child(minh).child("friendlist")
                .orderByChild("username_friend").equalTo(ban).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    String key = post.getRef().getKey();
                    mDatabase.child("users").child(minh).child("friendlist").child(key).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
