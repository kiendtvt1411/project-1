package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
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
import dhbkhn.kien.kienmessenger.Adapter.ViewHolderThongBaoKetBan;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.Notification;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.Chat.ChatDoi;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.LoiMoiThemBan;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.XemTrangCaNhanCuaNguoiKhac;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 11/30/2016.
 */
public class FragmentKetBan extends Fragment {
    RecyclerView rvKetBan;
    DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<Notification,ViewHolderThongBaoKetBan> fra;
    String nguoidung = "";
    String emailnguoidung = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_ket_ban, container, false);
        addControls(row);
        addEvents();
        return row;
    }

    private void addControls(View row) {
        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rvKetBan = (RecyclerView) row.findViewById(R.id.rvKetBan);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        rvKetBan.setLayoutManager(llm);
    }

    private void addEvents() {
        Map<String,Object> hm = new HashMap<>();
        hm.put("/notification/statistic/"+nguoidung+"/ket_ban/tbChuaXem/",0);
        mDatabase.updateChildren(hm);
        Query queryThongBao = mDatabase.child("notification").child(nguoidung).child("ket_ban");
        fra = new FirebaseRecyclerAdapter<Notification, ViewHolderThongBaoKetBan>(Notification.class,
                R.layout.custom_item_thong_bao_ket_ban,ViewHolderThongBaoKetBan.class,queryThongBao) {
            @Override
            protected void populateViewHolder(final ViewHolderThongBaoKetBan viewHolder, final Notification model, int position) {
                if (!model.isConsequence()) {
                    viewHolder.llChuaLaBanBe.setVisibility(View.VISIBLE);
                    viewHolder.llDaLaBanBe.setVisibility(View.GONE);
                    viewHolder.tvThongBao.setText(model.getContent());
                    mDatabase.child("users").child(model.getAuthor()).child("avatar_url").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String url = dataSnapshot.getValue(String.class);
                            hienThiHinhAnh(url,viewHolder.imgThongBao);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    viewHolder.btnOKKetBan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chapNhanKetBan(nguoidung,emailnguoidung,model.getAuthor(),model.getEmail());
                            mDatabase.child("notification").child(nguoidung).child("ket_ban")
                                    .orderByChild("author").equalTo(model.getAuthor()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot post : dataSnapshot.getChildren()) {
                                        String key = post.getKey();
                                        mDatabase.child("notification").child(nguoidung).child("ket_ban")
                                                .child(key).child("consequence").setValue(true);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            mDatabase.child("mess_chat_doi").child(nguoidung).child(model.getAuthor()).child("so_luong_tin_nhan_chua_xem").setValue(0);
                            mDatabase.child("mess_chat_doi").child(model.getAuthor()).child(nguoidung).child("so_luong_tin_nhan_chua_xem").setValue(0);
                            Intent iTrangChu = new Intent(getContext(), TrangChu.class);
                            startActivity(iTrangChu);
                        }
                    });

                    viewHolder.btnCancelKetBan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iTrangChu2 = new Intent(getContext(), TrangChu.class);
                            startActivity(iTrangChu2);
                        }
                    });

                    viewHolder.tvThongBao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iTrangCaNhan = new Intent(getContext(), XemTrangCaNhanCuaNguoiKhac.class);
                            iTrangCaNhan.putExtra("nguoidung", nguoidung);
                            iTrangCaNhan.putExtra("tenban",model.getAuthor());
                            startActivity(iTrangCaNhan);
                        }
                    });
                    viewHolder.llThongBao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iMoiKetBan = new Intent(getContext(), LoiMoiThemBan.class);
                            iMoiKetBan.putExtra("friend", model.getAuthor());
                            iMoiKetBan.putExtra("emailfriend",model.getEmail());
                            startActivity(iMoiKetBan);
                        }
                    });
                }
                else{
                    viewHolder.llDaLaBanBe.setVisibility(View.VISIBLE);
                    viewHolder.llChuaLaBanBe.setVisibility(View.GONE);

                    String tenban = model.getAuthor();
                    String sourceString = "<b>" + tenban + "</b>";
                    Spanned boidam = Html.fromHtml(sourceString);
                    String uData = "Bạn và "+boidam+ " đã trở thành bạn bè.\nHãy gửi tin nhắn đến người ấy ngay thôi!";
                    SpannableString content = new SpannableString(uData);
                    content.setSpan(new UnderlineSpan(), 7, 7+boidam.length(), 0);
                    viewHolder.tvThongBaoDaLaBanBe.setText(uData);

                    viewHolder.llDaLaBanBe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iChat = new Intent(getContext(), ChatDoi.class);
                            iChat.putExtra("tenbanchat", model.getAuthor());
                            iChat.putExtra("emailfriendchat",model.getEmail());
                            startActivity(iChat);
                        }
                    });
                }
            }
        };
        rvKetBan.setAdapter(fra);
    }

    private void chapNhanKetBan(String user1, String email1, String user2, String email2){
        String key = mDatabase.child("users").child(user1).push().getKey();
        String key2 = mDatabase.child("users").child(user2).push().getKey();

        Friend f1 = new Friend(email1, user1);
        Map<String,Object>map1 = f1.toMap();

        Friend f2 = new Friend(email2, user2);
        Map<String,Object>map2 = f2.toMap();

        Map<String, Object> hmUp = new HashMap<>();
        hmUp.put("/users/" + user1 + "/friendlist/" + key, map2);
        hmUp.put("/users/" + user2 + "/friendlist/" + key2, map1);
        mDatabase.updateChildren(hmUp);
    }

    private void hienThiHinhAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
