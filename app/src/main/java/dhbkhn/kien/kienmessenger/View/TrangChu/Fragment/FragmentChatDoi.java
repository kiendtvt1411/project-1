package dhbkhn.kien.kienmessenger.View.TrangChu.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import dhbkhn.kien.kienmessenger.Adapter.ChatDon.ChatDonViewHolder;
import dhbkhn.kien.kienmessenger.Adapter.TrangChu.AdapterFragmentChatDoi;
import dhbkhn.kien.kienmessenger.Adapter.TrangChu.AdapterFragmentChatNhom;
import dhbkhn.kien.kienmessenger.Model.Object.ShowLassMess;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

/**
 * Created by kiend on 9/29/2016.
 */
public class FragmentChatDoi extends Fragment {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    RecyclerView rvChatDoi, rvChatDoiNhom;
    AdapterFragmentChatDoi fra;
    AdapterFragmentChatNhom fraNhom;
    String nguoiDung ="";
    String emailNguoiDung = "";

    @Override
    public void onPause() {
        super.onPause();
        addControls();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View row = inflater.inflate(R.layout.layout_fragment_chat_doi,container,false);
        rvChatDoi = (RecyclerView) row.findViewById(R.id.rvChatDon);
        rvChatDoiNhom = (RecyclerView) row.findViewById(R.id.rvChatDonNhom);

        addControls();

        return row;
    }

    private void addControls(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nguoiDung = DangNhapMessenger.nguoidung;
        emailNguoiDung = DangNhapMessenger.emailnguoidung;

        rvChatDoi.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChatDoiNhom.setLayoutManager(new LinearLayoutManager(getContext()));

        Query queryLM = mDatabase.child("mess_chat_doi/"+nguoiDung+"/show_last_mess");
        fra = new AdapterFragmentChatDoi(getContext(),ShowLassMess.class,R.layout.custom_item_chat_doi,
                ChatDonViewHolder.class,queryLM,nguoiDung,emailNguoiDung);
        rvChatDoi.setAdapter(fra);

        Query queryNhom = mDatabase.child("mess_chat_nhom/"+nguoiDung+"/quan_ly_mess/show_last_mess");
        fraNhom = new AdapterFragmentChatNhom(getContext(), nguoiDung, emailNguoiDung,
                ShowLassMess.class, R.layout.custom_item_chat_doi, ChatDonViewHolder.class, queryNhom);
        rvChatDoiNhom.setAdapter(fraNhom);
    }
}
