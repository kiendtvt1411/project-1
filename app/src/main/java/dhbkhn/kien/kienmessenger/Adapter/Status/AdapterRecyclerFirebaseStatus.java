package dhbkhn.kien.kienmessenger.Adapter.Status;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.Model.Status.XuLyLikeShareComment;
import dhbkhn.kien.kienmessenger.Presenter.Status.PresenterDangStatus;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.Status.Comment.XemChiTietStatus;
import dhbkhn.kien.kienmessenger.View.Status.IViewDangStatus;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.XemTrangCaNhanCuaNguoiKhac;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangCaNhan;

/**
 * Created by kiend on 10/20/2016.
 */
public class AdapterRecyclerFirebaseStatus extends FirebaseRecyclerAdapter<Status, ViewHolderStatus> implements IViewDangStatus{
    Context mContext;
    String nguoidung, emailnguoidung;
    Class<Status> modelClass;
    int modelLayout;
    Class<ViewHolderStatus> viewHolderClass;
    Query ref;
    String statusCuaAi;
    DatabaseReference mDatabase;
    XuLyLikeShareComment xuLyLikeShareComment;
    PresenterDangStatus presenterDangStatus;

    public AdapterRecyclerFirebaseStatus(Context mContext, String nguoidung, Class<Status> modelClass, int modelLayout,
                                         Class<ViewHolderStatus> viewHolderClass, Query ref, String statusCuaAi) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.nguoidung = nguoidung;
        emailnguoidung = DangNhapMessenger.emailnguoidung;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.statusCuaAi = statusCuaAi;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        presenterDangStatus = new PresenterDangStatus(this);
        xuLyLikeShareComment = new XuLyLikeShareComment();
    }

    @Override
    public ViewHolderStatus onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case 1:
                View userType1 = inflater
                        .inflate(R.layout.custom_rv_trang_ca_nhan, parent, false);
                return new ViewHolderStatus(userType1);
            case 2:
                View userType2 = inflater
                        .inflate(R.layout.custom_item_share_bai_viet, parent, false);
                return new ViewHolderStatus(userType2);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        Status s = getItem(position);
        if (!s.isDuocShare()) {
            return 1;
        }else {
            return 2;
        }
    }

    @Override
    protected void populateViewHolder(final ViewHolderStatus viewHolder, final Status model, int position) {
        final boolean[] clickLike = {model.isClickLike()};
        final int[] soLike = {model.getLikes()};
        final int[] soShare = {model.getShares()};
        final Map<String, Object> hmUpdate = new HashMap<String, Object>();

        viewHolder.tvTransactionLike.setText(String.valueOf(soLike[0]));
        viewHolder.tvTransactionComment.setText(String.valueOf(model.getComments()));
        viewHolder.tvTransactionShare.setText(String.valueOf(soShare[0]));
        final String keyS = model.getKey_status();

        if (!model.isDuocShare()) {
            hienThiAnh(model.getAvatar_url(),viewHolder.imgStatus);
            viewHolder.tvUsernameStatus.setText(model.getUsername());
            viewHolder.tvUsernameStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nguoidung.equals(model.getUsername())) {
                        Intent iCaNhan = new Intent(mContext, TrangCaNhan.class);
                        mContext.startActivity(iCaNhan);
                    }else {
                        Intent iNguoiKhac = new Intent(mContext, XemTrangCaNhanCuaNguoiKhac.class);
                        iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                        iNguoiKhac.putExtra("friend", model.getUsername());
                        mContext.startActivity(iNguoiKhac);
                    }
                }
            });
            viewHolder.tvLocationStatus.setText(model.getLocation());
            viewHolder.tvDateStatus.setText(model.getDate());
            viewHolder.tvContentStatus.setText(model.getContent());
            List<String> dsUrl = model.getList_image_url();
            if (dsUrl != null) {
                viewHolder.llAnh.setVisibility(View.VISIBLE);
                if (dsUrl.size() > 3) {
                    viewHolder.imgAnh1Status.setVisibility(View.VISIBLE);
                    viewHolder.imgAnh2Status.setVisibility(View.VISIBLE);
                    viewHolder.imgAnh3Status.setVisibility(View.VISIBLE);
                    viewHolder.tvNhieuAnh.setVisibility(View.VISIBLE);
                    hienThiAnh(dsUrl.get(0),viewHolder.imgAnh1Status);
                    hienThiAnh(dsUrl.get(1),viewHolder.imgAnh2Status);
                    hienThiAnh(dsUrl.get(2),viewHolder.imgAnh3Status);
                    viewHolder.tvNhieuAnh.setText("+"+String.valueOf(dsUrl.size()-2));
                }
                else if (dsUrl.size() == 3) {
                    viewHolder.imgAnh1Status.setVisibility(View.VISIBLE);
                    viewHolder.imgAnh2Status.setVisibility(View.VISIBLE);
                    viewHolder.imgAnh3Status.setVisibility(View.VISIBLE);
                    hienThiAnh(dsUrl.get(0),viewHolder.imgAnh1Status);
                    hienThiAnh(dsUrl.get(1),viewHolder.imgAnh2Status);
                    hienThiAnh(dsUrl.get(2),viewHolder.imgAnh3Status);
                }
                else if (dsUrl.size() == 2) {
                    viewHolder.imgAnh1Status.setVisibility(View.VISIBLE);
                    viewHolder.imgAnh2Status.setVisibility(View.VISIBLE);
                    hienThiAnh(dsUrl.get(0),viewHolder.imgAnh1Status);
                    hienThiAnh(dsUrl.get(1),viewHolder.imgAnh2Status);
                }
                else if (dsUrl.size() == 1) {
                    viewHolder.imgAnh1Status.setVisibility(View.VISIBLE);
                    hienThiAnh(dsUrl.get(0),viewHolder.imgAnh1Status);
                }
            }
            viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Status statusDuocShare = new Status();
                    statusDuocShare.setUsername(nguoidung + "," +model.getUsername());
                    statusDuocShare.setLocation("No");
                    statusDuocShare.setDate("No");
                    statusDuocShare.setLikes(0);
                    statusDuocShare.setComments(0);
                    statusDuocShare.setShares(0);
                    statusDuocShare.setContent(model.getKey_status());
                    statusDuocShare.setType("Công khai");
                    statusDuocShare.setDuocShare(true);
                    String keyStatusDuocShareMoi = presenterDangStatus.layKeyDangStatus(nguoidung);
                    statusDuocShare.setKey_status(keyStatusDuocShareMoi);
                    presenterDangStatus.dangStatusCaNhan(nguoidung,keyStatusDuocShareMoi,statusDuocShare);
                    ++soShare[0];
                    xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(model,"shares", soShare[0]);
                    xuLyLikeShareComment.themNotiCongDong(hmUpdate, model, model.getKey_status(), " đã chia sẻ bài viết của bạn", "_so_share");
                }
            });
        }
        else {
            String author = model.getUsername();
            final String[] arrAuthor = author.split(",");
            viewHolder.tvNguoiShare.setText(arrAuthor[0]);
            viewHolder.tvNguoiDuocShare1.setText(arrAuthor[1]);

            View.OnClickListener myCLick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tvNguoiShare:
                            if (nguoidung.equals(arrAuthor[0])) {
                                Intent iCaNhan = new Intent(mContext, TrangCaNhan.class);
                                mContext.startActivity(iCaNhan);
                            }else {
                                Intent iNguoiKhac = new Intent(mContext, XemTrangCaNhanCuaNguoiKhac.class);
                                iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                iNguoiKhac.putExtra("friend",arrAuthor[0]);
                                mContext.startActivity(iNguoiKhac);
                            }
                            break;
                        case R.id.tvNguoiDuocShare1:
                            if (nguoidung.equals(arrAuthor[1])) {
                                Intent iCaNhan = new Intent(mContext, TrangCaNhan.class);
                                mContext.startActivity(iCaNhan);
                            }else {
                                Intent iNguoiKhac = new Intent(mContext, XemTrangCaNhanCuaNguoiKhac.class);
                                iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                iNguoiKhac.putExtra("friend",arrAuthor[1]);
                                mContext.startActivity(iNguoiKhac);
                            }
                            break;
                        case R.id.tvNguoiDuocShare2:
                            if (nguoidung.equals(arrAuthor[1])) {
                                Intent iCaNhan = new Intent(mContext, TrangCaNhan.class);
                                mContext.startActivity(iCaNhan);
                            }else {
                                Intent iNguoiKhac = new Intent(mContext, XemTrangCaNhanCuaNguoiKhac.class);
                                iNguoiKhac.putExtra("banbe", "da_la_ban_be");
                                iNguoiKhac.putExtra("friend",arrAuthor[1]);
                                mContext.startActivity(iNguoiKhac);
                            }
                            break;
                    }
                }
            };

            viewHolder.tvNguoiShare.setOnClickListener(myCLick);
            viewHolder.tvNguoiDuocShare1.setOnClickListener(myCLick);
            viewHolder.tvNguoiDuocShare2.setOnClickListener(myCLick);

            String keyStatusDuocShare = model.getContent();
            mDatabase.child("status").child(arrAuthor[1]).child("cua_toi").child(keyStatusDuocShare).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Status s = dataSnapshot.getValue(Status.class);
                    viewHolder.tvNguoiDuocShare2.setText(s.getUsername());
                    hienThiAnh(s.getAvatar_url(), viewHolder.imgAvatarNguoiDuocShare);

                    viewHolder.tvLocationStatusDuocShare.setText(s.getLocation());
                    viewHolder.tvDateStatusDuocShare.setText(s.getDate());
                    viewHolder.tvContentStatusDuocShare.setText(s.getContent());

                    List<String> dsLinkAnh = s.getList_image_url();
                    if (dsLinkAnh != null) {
                        viewHolder.llAnhStatusDuocShare.setVisibility(View.VISIBLE);
                        if (dsLinkAnh.size() > 3) {
                            viewHolder.imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                            viewHolder.imgAnh2StatusDuocShare.setVisibility(View.VISIBLE);
                            viewHolder.imgAnh3StatusDuocShare.setVisibility(View.VISIBLE);
                            viewHolder.tvNhieuAnhDuocShare.setVisibility(View.VISIBLE);
                            hienThiAnh(dsLinkAnh.get(0), viewHolder.imgAnh1StatusDuocShare);
                            hienThiAnh(dsLinkAnh.get(1), viewHolder.imgAnh2StatusDuocShare);
                            hienThiAnh(dsLinkAnh.get(2), viewHolder.imgAnh3StatusDuocShare);
                            viewHolder.tvNhieuAnhDuocShare.setText("+" + String.valueOf(dsLinkAnh.size() - 2));
                        } else if (dsLinkAnh.size() == 3) {
                            viewHolder.imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                            viewHolder.imgAnh2StatusDuocShare.setVisibility(View.VISIBLE);
                            viewHolder.imgAnh3StatusDuocShare.setVisibility(View.VISIBLE);
                            hienThiAnh(dsLinkAnh.get(0), viewHolder.imgAnh1StatusDuocShare);
                            hienThiAnh(dsLinkAnh.get(1), viewHolder.imgAnh2StatusDuocShare);
                            hienThiAnh(dsLinkAnh.get(2), viewHolder.imgAnh3StatusDuocShare);
                        } else if (dsLinkAnh.size() == 2) {
                            viewHolder.imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                            viewHolder.imgAnh2StatusDuocShare.setVisibility(View.VISIBLE);
                            hienThiAnh(dsLinkAnh.get(0), viewHolder.imgAnh1StatusDuocShare);
                            hienThiAnh(dsLinkAnh.get(1), viewHolder.imgAnh2StatusDuocShare);
                        } else if (dsLinkAnh.size() == 1) {
                            viewHolder.imgAnh1StatusDuocShare.setVisibility(View.VISIBLE);
                            hienThiAnh(dsLinkAnh.get(0), viewHolder.imgAnh1StatusDuocShare);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            viewHolder.tvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Bạn không thể chia sẻ lại status này nữa\nHãy chia sẻ bài viết gốc!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (clickLike[0]) {
            viewHolder.imgLike.setImageResource(R.drawable.full_heart);
            viewHolder.tvLike.setText("Bỏ thích");
        }else {
            viewHolder.imgLike.setImageResource(R.drawable.empty_heart);
            viewHolder.tvLike.setText("Thích");
        }
        viewHolder.llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLike[0] = !clickLike[0];
                if (clickLike[0]) {
                    ++soLike[0];
                    xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(model, "likes", soLike[0]);
                    xuLyLikeShareComment.themNotiCongDong(hmUpdate, model, keyS, "đã thích bài viết của bạn", "_so_like");
                }else {
                    if (soLike[0] > 0) {
                        --soLike[0];
                        xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(model, "likes", soLike[0]);
                        mDatabase.child("notification").child("statistic")
                                .child(model.getUsername()).child("cong_dong").child(model.getKey_status()).removeValue();
                    }
                }
                hmUpdate.put("/status/" + nguoidung + "/" + statusCuaAi + "/" + keyS + "/clickLike", clickLike[0]);
                mDatabase.updateChildren(hmUpdate);
            }
        });

        viewHolder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTiet = new Intent(mContext, XemChiTietStatus.class);
                iChiTiet.putExtra("status", model.getKey_status());
                iChiTiet.putExtra("chu_status", model.getUsername());
                iChiTiet.putExtra("status_cua_ai", statusCuaAi);
                iChiTiet.putExtra("duoc_share", model.isDuocShare());
                mContext.startActivity(iChiTiet);
            }
        });

        viewHolder.imgSettingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, viewHolder.imgSettingStatus);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup_status,popupMenu.getMenu());
                setForceShowIcon(popupMenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int vt = item.getItemId();
                        switch (vt) {
                            case R.id.item_status_share:
                                break;
                            case R.id.item_status_update:
                                break;
                            case R.id.item_status_delete:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
    }

    @Override
    public void dangStatusThanhCong() {
        Toast.makeText(mContext, "Đã chia sẻ bài viết lên tường nhà bạn và bạn bè!!!", Toast.LENGTH_SHORT).show();
    }
}
