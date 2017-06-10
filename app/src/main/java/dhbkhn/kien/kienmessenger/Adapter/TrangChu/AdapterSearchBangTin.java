package dhbkhn.kien.kienmessenger.Adapter.TrangChu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.SearchObject;
import dhbkhn.kien.kienmessenger.Model.Object.User;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.XemTrangCaNhanCuaNguoiKhac;

/**
 * Created by kiend on 12/8/2016.
 */
public class AdapterSearchBangTin extends ArrayAdapter<SearchObject>{
    Context context;
    int resource;
    ArrayList<SearchObject> items;
    ArrayList<SearchObject> itemsAll;
    ArrayList<SearchObject> itemSuggestions;

    public AdapterSearchBangTin(Context context, int resource, ArrayList<SearchObject> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.items = objects;
        this.itemsAll = (ArrayList<SearchObject>) items.clone();
        this.itemSuggestions = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, null);
        }
        final SearchObject searchObject = items.get(position);
        if (searchObject != null) {
            TextView tvSearch = (TextView) row.findViewById(R.id.tvSearch);
            ImageView imgSearch = (ImageView) row.findViewById(R.id.imgAvatarSearch);
            LinearLayout llSearch = (LinearLayout) row.findViewById(R.id.llSearch);

            tvSearch.setText(searchObject.getDisplay_name());
            hienThiHinhAnh(searchObject.getAvatar_url(), imgSearch);
            llSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = searchObject.getType();
                    switch (type){
                        case "user":
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                            db.child("users").child(searchObject.getDisplay_name()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User u = dataSnapshot.getValue(User.class);
                                    Intent iXem = new Intent(context, XemTrangCaNhanCuaNguoiKhac.class);
                                    iXem.putExtra("friend", u.getUsername());
                                    iXem.putExtra("emailfriend",u.getEmail());
                                    iXem.putExtra("banbe","chua_la_ban_be");
                                    context.startActivity(iXem);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            break;
                        case "":
                            break;
                    }
                }
            });
        }
        return row;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((SearchObject)resultValue).getDisplay_name();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                itemSuggestions.clear();
                for (SearchObject searchObject : itemsAll) {
                    if (searchObject.getDisplay_name().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        itemSuggestions.add(searchObject);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = itemSuggestions;
                results.count = itemSuggestions.size();
                return results;
            }
            else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<SearchObject> filteredList = (ArrayList<SearchObject>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SearchObject f : filteredList) {
                    add(f);
                }
                notifyDataSetChanged();
            }
        }
    };

    private void hienThiHinhAnh(String url, ImageView imageView) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(imageView);
        task.execute(url);
    }
}
