package dhbkhn.kien.kienmessenger.Adapter.TrangChu;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dhbkhn.kien.kienmessenger.Adapter.ChatDon.ChatDonViewHolder;
import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.ChatMessage;
import dhbkhn.kien.kienmessenger.Model.Object.ShowLassMess;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.View.Chat.ChatDoi;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangChu;

/**
 * Created by kiend on 10/25/2016.
 */
public class AdapterFragmentChatDoi extends FirebaseRecyclerAdapter<ShowLassMess, ChatDonViewHolder> {
    Context mContext;
    Class<ShowLassMess> modelClass;
    int modelLayout;
    Class<ChatDonViewHolder> viewHolderClass;
    Query ref;
    DatabaseReference mDatabase;
    String nguoiDung, emailNguoiDung;

    public AdapterFragmentChatDoi(Context mContext, Class<ShowLassMess> modelClass, int modelLayout, Class<ChatDonViewHolder> viewHolderClass, Query ref,
                                   String nguoiDung, String emailNguoiDung) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.nguoiDung = nguoiDung;
        this.emailNguoiDung = emailNguoiDung;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void populateViewHolder(final ChatDonViewHolder viewHolder, final ShowLassMess model, int position) {
        mDatabase.child("users").child(model.getFriend().getUsername_friend()).child("avatar_url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);
                hienThiAnh(url,viewHolder.imgAvatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewHolder.tvStatus.setText(model.getMessage().getStatus());
        viewHolder.tvUsername.setText(model.getFriend().getUsername_friend());
        viewHolder.tvLastMess.setText(model.getMessage().getContent());
        viewHolder.tvDate.setText(model.getMessage().getDate());

        final String tenbanchat = model.getFriend().getUsername_friend();

        final ValueEventListener listenerSoLuongTinChuaXem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int soluongchuaxem = dataSnapshot.getValue(Integer.class);
                if (soluongchuaxem > 0) {
                    viewHolder.tvSoLuongTinNhanChuaXem.setVisibility(View.VISIBLE);
                    viewHolder.tvSoLuongTinNhanChuaXem.setText(String.valueOf(soluongchuaxem));
                }else {
                    viewHolder.tvSoLuongTinNhanChuaXem.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("mess_chat_doi").child(nguoiDung).child(tenbanchat).child("so_luong_tin_nhan_chua_xem")
                .addValueEventListener(listenerSoLuongTinChuaXem);

        final ValueEventListener listenerLM = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ShowLassMess showLassMess = dataSnapshot.getValue(ShowLassMess.class);
                if (showLassMess.getMessage().getStatus().equals("Đã gửi") &&
                        showLassMess.getMessage().getAuthor().equals(nguoiDung)) {
                    mDatabase.child("mess_chat_doi").child(nguoiDung).child("show_last_mess")
                            .child(tenbanchat).child("message").child("status").setValue("Đã nhận");
                }
                //co nen tao them 1 luong lang nghe hay khong
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        final ValueEventListener listenerBanOnline = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user.isOnline()) {
                    mDatabase.child("mess_chat_doi").child(nguoiDung).child("show_last_mess")
                            .child(tenbanchat).addListenerForSingleValueEvent(listenerLM);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.child("users").child(tenbanchat).addValueEventListener(listenerBanOnline);

        final ValueEventListener listenerDaXemBanChat = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    String key = post.getKey();
                    mDatabase.child("mess_chat_doi").child(tenbanchat).child(nguoiDung).child("mess_chung")
                            .child(key).child("status").setValue("Đã xem");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        final ValueEventListener listenerDaXemNguoiDung = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = post.getValue(ChatMessage.class);
                    String key = post.getKey();
                    if (!chatMessage.getAuthor().equals(emailNguoiDung)) {
                        mDatabase.child("mess_chat_doi").child(nguoiDung).child(tenbanchat).child("mess_chung")
                                .child(key).child("status").setValue("Đã xem");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        viewHolder.llKhungCHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChat = new Intent(mContext, ChatDoi.class);
                mDatabase.child("mess_chat_doi").child(nguoiDung).child("show_last_mess")
                        .child(model.getFriend().getUsername_friend()).child("so_lan_thong_bao").setValue(0);
                mContext.stopService(TrangChu.iService);
                mDatabase.child("mess_chat_doi").child(nguoiDung).child(tenbanchat).child("trang_thai_phong").child(nguoiDung).setValue("On");
                mDatabase.child("mess_chat_doi").child(tenbanchat).child(nguoiDung).child("trang_thai_phong").child(nguoiDung).setValue("On");
                if (!model.getMessage().getAuthor().equals(emailNguoiDung)) {
                    mDatabase.child("mess_chat_doi").child(tenbanchat).child("show_last_mess")
                            .child(nguoiDung).child("message").child("status").setValue("Đã xem");
                }
                mDatabase.child("mess_chat_doi").child(tenbanchat).child(nguoiDung)
                        .child("mess_chung").addListenerForSingleValueEvent(listenerDaXemBanChat);
                mDatabase.child("mess_chat_doi").child(nguoiDung).child(tenbanchat)
                        .child("mess_chung").addListenerForSingleValueEvent(listenerDaXemNguoiDung);

                iChat.putExtra("tenbanchat",model.getFriend().getUsername_friend());
                iChat.putExtra("emailfriendchat",model.getFriend().getEmail_friend());
                mDatabase.removeEventListener(listenerDaXemBanChat);
                mDatabase.removeEventListener(listenerDaXemNguoiDung);
                mDatabase.removeEventListener(listenerLM);
                mDatabase.removeEventListener(listenerBanOnline);
                mDatabase.removeEventListener(listenerSoLuongTinChuaXem);
                mContext.startActivity(iChat);
            }
        });
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
