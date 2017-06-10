package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.ViewHolderChatNhom;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.ProfileGroupChat;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.Chat.ChatNhom;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

/**
 * Created by kiend on 9/22/2016.
 */
public class FragmentChatNhom extends Fragment {
    DatabaseReference mDatabase;
    RecyclerView rvChatNhom;
    FirebaseRecyclerAdapter<ProfileGroupChat,ViewHolderChatNhom> fra;
    RecyclerView.LayoutManager layoutManager;
    String nguoiDung = "";
    String emailNguoiDung = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_chat_nhom, container, false);
        rvChatNhom = (RecyclerView) row.findViewById(R.id.rvChatNhom);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        layoutManager = new GridLayoutManager(getContext(),2);

        nguoiDung = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;

        rvChatNhom.setLayoutManager(layoutManager);
        Query queryChat = mDatabase.child("mess_chat_nhom").child(nguoiDung).child("quan_ly_nhom");
        fra = new FirebaseRecyclerAdapter<ProfileGroupChat, ViewHolderChatNhom>(ProfileGroupChat.class,R.layout.custom_item_chat_nhom,
                ViewHolderChatNhom.class,queryChat) {
            @Override
            protected void populateViewHolder(ViewHolderChatNhom viewHolder, final ProfileGroupChat model, final int position) {
                viewHolder.tvTenNhom.setText(model.getTen_nhom());
                StringBuilder builder = new StringBuilder();
                final List<Friend> dsF = model.getList_friend();

                final StringBuilder builderTen = new StringBuilder();
                final StringBuilder builderEmail = new StringBuilder();

                for(int i = 0; i<dsF.size();i++){
                    if (i == dsF.size() - 1) {
                        builderTen.append(dsF.get(i).getUsername_friend());
                        builderEmail.append(dsF.get(i).getEmail_friend());
                    }
                    else {
                        builderTen.append(dsF.get(i).getUsername_friend()+", ");
                        builderEmail.append(dsF.get(i).getEmail_friend()+ ", ");
                    }
                    String dsFriend = dsF.get(i).getUsername_friend();
                    if (dsFriend.equals(nguoiDung)) {
                        dsFriend = "Bạn";
                    }
                    if(i==(dsF.size()-1)){
                        builder.append(dsFriend);
                    }
                    else {
                        builder.append(dsFriend + ", ");
                    }
                }
                viewHolder.tvNguoiThamGiaNhom.setText(builder.toString());
                viewHolder.cvKhungChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iChatNhom = new Intent(getContext(), ChatNhom.class);
                        for (Friend f : dsF) {
                            String ten = f.getUsername_friend();
                            mDatabase.child("mess_chat_nhom").child(ten).child("trang_thai_phong").child(model.getKey_nhom())
                                    .child(nguoiDung).setValue("On");
                        }
                        iChatNhom.putExtra("ds", model);
                        iChatNhom.putExtra("tenbanchat", builderTen.toString());
                        iChatNhom.putExtra("emailfriendchat", builderEmail.toString());
                        getActivity().startActivity(iChatNhom);
                    }
                });
            }
        };
        rvChatNhom.setAdapter(fra);
        return row;
    }
}
