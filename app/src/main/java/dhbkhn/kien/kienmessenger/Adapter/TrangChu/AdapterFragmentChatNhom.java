package dhbkhn.kien.kienmessenger.Adapter.TrangChu;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.ChatDon.ChatDonViewHolder;
import dhbkhn.kien.kienmessenger.Model.Object.ChatMessage;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.ShowLassMess;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.Chat.ChatNhom;

/**
 * Created by kiend on 10/28/2016.
 */
public class AdapterFragmentChatNhom extends FirebaseRecyclerAdapter<ShowLassMess, ChatDonViewHolder> {
    Context mContext;
    String nguoidung, emailNguoiDung;
    Class<ShowLassMess> modelClass;
    int modelLayout;
    Class<ChatDonViewHolder> viewHolderClass;
    Query ref;
    DatabaseReference mDatabase;

    public AdapterFragmentChatNhom(Context mContext, String nguoidung, String emailNguoiDung,
                                   Class<ShowLassMess> modelClass, int modelLayout, Class<ChatDonViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.nguoidung = nguoidung;
        this.emailNguoiDung = emailNguoiDung;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void populateViewHolder(ChatDonViewHolder viewHolder, final ShowLassMess model, int position) {
        viewHolder.imgAvatar.setImageResource(R.drawable.girl);
        viewHolder.tvUsername.setText(model.getFriend().getUsername_friend());
        viewHolder.tvLastMess.setText(model.getMessage().getContent());
        viewHolder.tvDate.setText(model.getMessage().getDate());
        viewHolder.tvStatus.setText(model.getMessage().getStatus());

        Friend friend = model.getFriend();
        String email = friend.getEmail_friend();
        String user = friend.getUsername_friend();

        String[] e = email.split(", ");
        String[] u = user.split(", ");

        final List<Friend> dsFriend = new ArrayList<>();
        for(int i = 0;i<e.length;i++) {
            Friend newFriend = new Friend(e[i], u[i]);
            dsFriend.add(newFriend);
        }

        viewHolder.llKhungCHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChat = new Intent(mContext, ChatNhom.class);
                iChat.putExtra("tenbanchat",model.getFriend().getUsername_friend());
                iChat.putExtra("emailfriendchat",model.getFriend().getEmail_friend());
                iChat.putExtra("key", model.getKey_nhom());

                for (final Friend f : dsFriend) {
                    mDatabase.child("mess_chat_nhom").child(f.getUsername_friend()).child("trang_thai_phong").child(model.getKey_nhom())
                                .child(nguoidung).setValue("On");
                    if (!f.getUsername_friend().equals(nguoidung)&&!model.getMessage().getAuthor().equals(emailNguoiDung)) {
                        mDatabase.child("mess_chat_nhom").child(f.getUsername_friend())
                                .child("quan_ly_mess").child("show_last_mess")
                                .child(model.getKey_nhom()).child("message").child("status").setValue("Đã xem");
                    }
                    if (f.getUsername_friend().equals(nguoidung)) {
                        mDatabase.child("mess_chat_nhom").child(nguoidung).child("quan_ly_mess")
                                .child(model.getKey_nhom()).child("mess_chung").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot post : dataSnapshot.getChildren()) {
                                    String keyMess = post.getKey();
                                    ChatMessage chatMessage = post.getValue(ChatMessage.class);
                                    if (!chatMessage.getAuthor().equals(emailNguoiDung)) {
                                        mDatabase.child("mess_chat_nhom").child(nguoidung).child("quan_ly_mess")
                                                .child(model.getKey_nhom()).child("mess_chung").child(keyMess)
                                                .child("status").setValue("Đã xem");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }else {
                        mDatabase.child("mess_chat_nhom").child(f.getUsername_friend()).child("quan_ly_mess")
                                .child(model.getKey_nhom()).child("mess_chung").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot post : dataSnapshot.getChildren()) {
                                    String keyMess = post.getKey();
                                    mDatabase.child("mess_chat_nhom").child(f.getUsername_friend()).child("quan_ly_mess")
                                                .child(model.getKey_nhom()).child("mess_chung").child(keyMess)
                                                .child("status").setValue("Đã xem");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
                mContext.startActivity(iChat);
            }
        });
    }
}
