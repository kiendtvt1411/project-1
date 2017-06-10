package dhbkhn.kien.kienmessenger.Model.Status;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import dhbkhn.kien.kienmessenger.Model.Object.DuLieuThongBao;
import dhbkhn.kien.kienmessenger.Model.Object.Friend;
import dhbkhn.kien.kienmessenger.Model.Object.Notification;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;

/**
 * Created by kiend on 12/4/2016.
 */
public class XuLyLikeShareComment {
    DatabaseReference mDatabase;
    String nguoidung = "";
    String emailnguoidung = "";

    //viet phan xu ly so luong comment va share tuong tu;

    public XuLyLikeShareComment() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nguoidung = DangNhapMessenger.nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
    }

    private void xuLyTangGiamLike(final String username, final Status s, String thuocTinh, int soLuong){
        mDatabase.child("status").child(username).child("cua_ban_be_toi").child(s.getKey_status()).child(thuocTinh).setValue(soLuong);
    }

    public void xuLyCapNhatSoLuongTangGiamLike(final Status s, final String thuocTinh, final int soLuong){
        ValueEventListener valueDanhSachBan = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot post: dataSnapshot.getChildren()){
                    Friend f = post.getValue(Friend.class);
                    if (f != null) {
                        xuLyTangGiamLike(f.getUsername_friend(),s, thuocTinh, soLuong);
                    }
                }
                if (s.isDuocShare()) {
                    String[] haiNguoi2 = s.getUsername().split(",");
                    String nguoishare2 = haiNguoi2[0];
                    mDatabase.child("status").child(nguoishare2)
                            .child("cua_toi").child(s.getKey_status()).child(thuocTinh).setValue(soLuong);
                }else {
                    mDatabase.child("status").child(s.getUsername())
                            .child("cua_toi").child(s.getKey_status()).child(thuocTinh).setValue(soLuong);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if (s.isDuocShare()) {
            String[] haiNguoi1 = s.getUsername().split(",");
            String nguoishare1 = haiNguoi1[0];
            mDatabase.child("users").child(nguoishare1).child("friendlist").addListenerForSingleValueEvent(valueDanhSachBan);
        }else {
            mDatabase.child("users").child(s.getUsername()).child("friendlist").addListenerForSingleValueEvent(valueDanhSachBan);
        }
    }

    public void themNotiCongDong(final Map<String,Object> update, final Status s, final String keyS, String noiDungThongBao, final String theLoai){
        if (!nguoidung.equals(s.getUsername())) {
            final Notification noti = new Notification();
            noti.setAuthor(s.getKey_status());
            noti.setContent(nguoidung + " " + noiDungThongBao);
            noti.setConsequence(false);
            noti.setEmail(emailnguoidung);

            ValueEventListener vl = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DuLieuThongBao dl = dataSnapshot.getValue(DuLieuThongBao.class);
                    int tongTB = dl.getTongTB();
                    int tbChuaXem = dl.getTbChuaXem();
                    DuLieuThongBao duLieuThongBao = new DuLieuThongBao();
                    duLieuThongBao.setFriend(s.getUsername());
                    duLieuThongBao.setTongTB(++tongTB);
                    duLieuThongBao.setTbChuaXem(++tbChuaXem);
                    Map<String,Object>hmDL = duLieuThongBao.toMap();
                    update.put("/notification/statistic/"+s.getUsername()+"/cong_dong", hmDL);
                    update.put("/notification/"+s.getUsername()+"/cong_dong/"+keyS + theLoai,noti.toMap());
                    mDatabase.updateChildren(update);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            if (s.isDuocShare()) {
                String[] haiNguoi = s.getUsername().split(",");
                final String nguoiShare = haiNguoi[0];
                mDatabase.child("notification").child("statistic")
                        .child(nguoiShare).child("cong_dong").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DuLieuThongBao dl = dataSnapshot.getValue(DuLieuThongBao.class);
                        int tongTB = dl.getTongTB();
                        int tbChuaXem = dl.getTbChuaXem();
                        DuLieuThongBao duLieuThongBao = new DuLieuThongBao();
                        duLieuThongBao.setFriend(nguoiShare);
                        duLieuThongBao.setTongTB(++tongTB);
                        duLieuThongBao.setTbChuaXem(++tbChuaXem);
                        Map<String,Object>hmDL = duLieuThongBao.toMap();
                        update.put("/notification/statistic/"+nguoiShare+"/cong_dong", hmDL);
                        update.put("/notification/"+nguoiShare+"/cong_dong/"+keyS,noti.toMap());
                        mDatabase.updateChildren(update);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }else {
                mDatabase.child("notification").child("statistic")
                        .child(s.getUsername()).child("cong_dong").addListenerForSingleValueEvent(vl);
            }
        }
    }
}
