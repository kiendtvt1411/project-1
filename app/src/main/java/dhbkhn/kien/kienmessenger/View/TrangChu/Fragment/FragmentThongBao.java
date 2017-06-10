package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Adapter.ViewHolderThongBaoChung;
import dhbkhn.kien.kienmessenger.Model.Object.Notification;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.Status.Comment.XemChiTietStatus;

public class FragmentThongBao extends Fragment{
    RecyclerView rvThongBao;
    DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<Notification,ViewHolderThongBaoChung>fra;
    String nguoidung = "";
    String emailnguoidung = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_thong_bao, container, false);
        addControls(row);
        addEvents();
        return row;
    }

    private void addControls(View row) {
        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rvThongBao = (RecyclerView) row.findViewById(R.id.rvThongBao);
        rvThongBao.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void addEvents() {
        Map<String,Object> hm = new HashMap<>();
        hm.put("/notification/statistic/"+nguoidung+"/cong_dong/tbChuaXem/",0);
        mDatabase.updateChildren(hm);
        Query queryThongBao = mDatabase.child("notification").child(nguoidung).child("cong_dong");
        fra = new FirebaseRecyclerAdapter<Notification, ViewHolderThongBaoChung>(Notification.class,
                R.layout.custom_item_thong_bao_chung_status,ViewHolderThongBaoChung.class,queryThongBao) {
            @Override
            protected void populateViewHolder(final ViewHolderThongBaoChung viewHolder, final Notification model, int position) {
                if (model.isConsequence()) {
                    viewHolder.llThongBaoChung.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_light));
                }else {
                    viewHolder.llThongBaoChung.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
                }
                String[] noidung = model.getContent().split(" ");
                Log.d("xem", noidung[0]);
                mDatabase.child("users").child(noidung[0]).child("avatar_url").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String url = dataSnapshot.getValue(String.class);
                        hienThiHinhAnh(url,viewHolder.imgThongBaoChung);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.tvNoiDungThongBaoChung.setText(model.getContent());
                viewHolder.llThongBaoChung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String keyStatus = model.getAuthor();
                        mDatabase.child("status").child(nguoidung).child("cua_toi").child(keyStatus).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Status s = dataSnapshot.getValue(Status.class);
                                model.setConsequence(true);
                                notifyDataSetChanged();
                                Intent iChiTiet = new Intent(getContext(), XemChiTietStatus.class);
                                iChiTiet.putExtra("status_cua_ai", "cua_toi");
                                iChiTiet.putExtra("status", s.getKey_status());
                                iChiTiet.putExtra("chu_status", s.getUsername());
                                iChiTiet.putExtra("duoc_share", s.isDuocShare());
                                startActivity(iChiTiet);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        rvThongBao.setAdapter(fra);
    }

    private void hienThiHinhAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
