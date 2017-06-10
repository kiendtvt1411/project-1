package dhbkhn.kien.kienmessenger.Adapter.DanhBa;

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

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.Chat.ChatDoi;

/**
 * Created by kiend on 11/21/2016.
 */
public class AdapterDanhBa extends FirebaseRecyclerAdapter<Friend, ViewHolderDanhBa> {
    Context mContext;
    String nguoiDung, emailNguoiDung;
    Class<Friend> modelClass;
    int modelLayout;
    Class<ViewHolderDanhBa> viewHolderClass;
    Query ref;
    DatabaseReference mDatabase;
    boolean dungLayout2;
    int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public AdapterDanhBa(Context mContext, Class<Friend> modelClass, int modelLayout,
                         Class<ViewHolderDanhBa> viewHolderClass, Query ref, String nguoiDung, String emailNguoiDung,
                         boolean dungLayout2) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.nguoiDung = nguoiDung;
        this.emailNguoiDung = emailNguoiDung;
        this.dungLayout2 = dungLayout2;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void populateViewHolder(final ViewHolderDanhBa viewHolder, final Friend model, int position) {
        final ValueEventListener valueDanhBa = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userF = dataSnapshot.getValue(User.class);
                if (userF != null) {
                    if (userF.isOnline()==true) {
                        viewHolder.imgOnlineDanhBa.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.imgOnlineDanhBa.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("users").child(model.getUsername_friend()).addValueEventListener(valueDanhBa);
        viewHolder.imgOnlineDanhBa.setImageResource(R.drawable.green);
        viewHolder.tvUsernameDanhBa.setText(model.getUsername_friend());

        if (dungLayout2) {
            String friend = model.getUsername_friend();
            mDatabase.child("users").child(friend).child("avatar_url").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String url = dataSnapshot.getValue(String.class);
                    hienThiAnh(url,viewHolder.imgAvaDanhBa);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            viewHolder.llItemDanhBa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuTuc(model,valueDanhBa);
                }
            });
        }else {
            String tenban = model.getUsername_friend();
            String chucaidau = String.valueOf(tenban.charAt(0));
            viewHolder.tvAvatarDanhBa.setText(chucaidau.toUpperCase());

            viewHolder.itemDanhBa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuTuc(model,valueDanhBa);
                }
            });

            viewHolder.itemDanhBa.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setIndex(viewHolder.getPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public void onViewRecycled(ViewHolderDanhBa holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    private void thuTuc(Friend model, ValueEventListener valueDanhBa){
        Intent iChat = new Intent(mContext, ChatDoi.class);
        mDatabase.child("mess_chat_doi").child(nguoiDung).child(model.getUsername_friend()).child("trang_thai_phong").child(nguoiDung).setValue("On");
        mDatabase.child("mess_chat_doi").child(model.getUsername_friend()).child(nguoiDung).child("trang_thai_phong").child(nguoiDung).setValue("On");
        mDatabase.removeEventListener(valueDanhBa);
        iChat.putExtra("tenbanchat",model.getUsername_friend());
        iChat.putExtra("emailfriendchat",model.getEmail_friend());
        mContext.startActivity(iChat);
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }
}
