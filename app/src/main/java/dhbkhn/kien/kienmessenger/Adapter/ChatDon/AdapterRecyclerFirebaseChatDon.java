package dhbkhn.kien.kienmessenger.Adapter.ChatDon;

import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import dhbkhn.kien.kienmessenger.Adapter.ViewHolderChat;
import dhbkhn.kien.kienmessenger.Model.Object.ChatMessage;

/**
 * Created by kiend on 10/23/2016.
 */
public class AdapterRecyclerFirebaseChatDon extends FirebaseRecyclerAdapter<ChatMessage, ViewHolderChat> {
    Class<ChatMessage> modelClass;
    int modelLayout;
    Class<ViewHolderChat> viewHolderClass;
    Query ref;
    String nguoidung;
    String tenban;
    String emailFriend;
    DatabaseReference mDatabase;

    public AdapterRecyclerFirebaseChatDon(Class<ChatMessage> modelClass, int modelLayout, Class<ViewHolderChat> viewHolderClass
            , Query ref, String nguoidung, String tenban, String emailFriend) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.nguoidung = nguoidung;
        this.tenban = tenban;
        this.emailFriend = emailFriend;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void populateViewHolder(final ViewHolderChat viewHolder, final ChatMessage model, final int position) {
        viewHolder.tvChatDate.setText(model.getDate());
        if (model.getAuthor().equals(emailFriend)) {
            viewHolder.llLeft.setVisibility(View.VISIBLE);
            viewHolder.tvItemAuthorLeft.setText(model.getAuthor());
            viewHolder.tvItemChatLeft.setText(model.getContent());
        }
        else {
            viewHolder.llRight.setVisibility(View.VISIBLE);
            viewHolder.tvItemAuthorRight.setText(model.getAuthor());
            viewHolder.tvItemChatRight.setText(model.getContent());
        }
    }
}
