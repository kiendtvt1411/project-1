package dhbkhn.kien.kienmessenger.Adapter.TrangChu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.Chat.ChatDoi;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

/**
 * Created by kiend on 11/26/2016.
 */
public class AdapterSearchDanhBa extends ArrayAdapter<Friend> {
    Context context;
    int resource;
    ArrayList<Friend> items;
    ArrayList<Friend> itemsAll;
    ArrayList<Friend> suggestions;
    DatabaseReference mDatabase;
    String nguoiDung = "";

    public AdapterSearchDanhBa(Context context, int resource, ArrayList<Friend> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.itemsAll = (ArrayList<Friend>)items.clone();
        this.suggestions = new ArrayList<>();
        this.resource = resource;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nguoiDung = DangNhapMessenger.nguoidung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);
        }
        final Friend f = items.get(position);

        if (f != null) {
            TextView tvUsername = (TextView) v.findViewById(R.id.tvUsernameDanhBa);
            TextView tvAvatarDanhBa = (TextView) v.findViewById(R.id.tvAvatarDanhBa);
            CardView itemDanhBa = (CardView) v.findViewById(R.id.itemDanhBa);
            final ImageView imgOnlineDanhBa = (ImageView) v.findViewById(R.id.imgOnlineDanhBa);
            String tenban = f.getUsername_friend();
            String chucaidau = String.valueOf(tenban.charAt(0));
            tvAvatarDanhBa.setText(chucaidau.toUpperCase());
            tvUsername.setText(f.getUsername_friend());
            final ValueEventListener valueDanhBa = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User userF = dataSnapshot.getValue(User.class);
                    if (userF != null) {
                        if (userF.isOnline()==true) {
                            imgOnlineDanhBa.setVisibility(View.VISIBLE);
                        }else {
                            imgOnlineDanhBa.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            itemDanhBa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thuTuc(f,valueDanhBa);
                }
            });
        }
        return v;
    }

    private void thuTuc(Friend model, ValueEventListener valueDanhBa){
        Intent iChat = new Intent(context, ChatDoi.class);
        mDatabase.child("mess_chat_doi").child(nguoiDung).child(model.getUsername_friend()).child("trang_thai_phong").child(nguoiDung).setValue("On");
        mDatabase.child("mess_chat_doi").child(model.getUsername_friend()).child(nguoiDung).child("trang_thai_phong").child(nguoiDung).setValue("On");
        mDatabase.removeEventListener(valueDanhBa);
        iChat.putExtra("tenbanchat",model.getUsername_friend());
        iChat.putExtra("emailfriendchat",model.getEmail_friend());
        context.startActivity(iChat);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Friend)resultValue).getUsername_friend();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Friend f : itemsAll) {
                    if (f.getUsername_friend().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(f);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }
            else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Friend> filteredList = (ArrayList<Friend>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Friend f : filteredList) {
                    add(f);
                }
                notifyDataSetChanged();
            }
        }
    };
}
