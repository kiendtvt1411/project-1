package dhbkhn.kien.kienmessenger.Adapter.ChatNhom;

import android.view.View;
import android.widget.CompoundButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/13/2016.
 */
public class AdapterThemChatNhom extends FirebaseRecyclerAdapter<Friend,ViewHolderThemNhomChat> {
    DatabaseReference mDatabase;
    Class<Friend> modelClass;
    int modelLayout;
    Class<ViewHolderThemNhomChat> viewHolderClass;
    Query ref;
    List<String> dsF;

    public AdapterThemChatNhom(Class<Friend> modelClass, int modelLayout, Class<ViewHolderThemNhomChat> viewHolderClass
            , Query ref, List<String> dsF) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.dsF = dsF;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void populateViewHolder(final ViewHolderThemNhomChat viewHolder, final Friend model, final int position) {
        if (dsF.indexOf(model.getUsername_friend()) >= 0) {
            viewHolder.chkMoiChatNhom.setChecked(true);
        }
        viewHolder.imgAvatarMoiChatNhom.setImageResource(R.drawable.girl);
        mDatabase.child("users").child(model.getUsername_friend()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userF = dataSnapshot.getValue(User.class);
                if (userF != null) {
                    if (userF.isOnline()==true) {
                        viewHolder.imgOnlineMoiChatNhom.setVisibility(View.VISIBLE);
                    }else {
                        viewHolder.imgOnlineMoiChatNhom.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        viewHolder.tvUsernameMoiChatNhom.setText(model.getUsername_friend());
        viewHolder.tvEmailMoiChatNhom.setText(model.getEmail_friend());
        viewHolder.chkMoiChatNhom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (dsF.indexOf(model.getUsername_friend()) < 0) {
                        dsF.add(model.getUsername_friend());
                    }
                }else {
                    if (dsF.indexOf(model.getUsername_friend()) >= 0) {
                        dsF.remove(model.getUsername_friend());
                    }
                }
            }
        });
    }
}
