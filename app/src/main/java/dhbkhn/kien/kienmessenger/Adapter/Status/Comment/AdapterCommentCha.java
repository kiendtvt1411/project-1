package dhbkhn.kien.kienmessenger.Adapter.Status.Comment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dhbkhn.kien.kienmessenger.Adapter.ChatNhom.AsyncTaskHienThiAnh;
import dhbkhn.kien.kienmessenger.Model.Object.Comment;
import dhbkhn.kien.kienmessenger.Model.Object.Status;
import dhbkhn.kien.kienmessenger.Model.Status.XuLyLikeShareComment;
import dhbkhn.kien.kienmessenger.R;
import dhbkhn.kien.kienmessenger.View.DangNhap_DangKy.DangNhapMessenger;
import dhbkhn.kien.kienmessenger.View.ThemBanChat.XemTrangCaNhanCuaNguoiKhac;
import dhbkhn.kien.kienmessenger.View.TrangChu.TrangCaNhan;

/**
 * Created by kiend on 10/21/2016.
 */
public class AdapterCommentCha extends FirebaseRecyclerAdapter<Comment,ViewHolderComment> {
    Context mContext;
    Class<Comment> modelClass;
    int modelLayout;
    Class<ViewHolderComment> viewHolderClass;
    String nguoidung = "";
    Query ref;
    String keyStatus;
    int soComment;
    Status status;
    DatabaseReference mDatabase;
    XuLyLikeShareComment xuLyLikeShareComment;

    public AdapterCommentCha(Context mContext, Class<Comment> modelClass, int modelLayout, Class<ViewHolderComment> viewHolderClass
            , Query ref, String keyStatus, int soComment, Status status) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.mContext = mContext;
        this.modelClass = modelClass;
        this.modelLayout = modelLayout;
        this.viewHolderClass = viewHolderClass;
        this.ref = ref;
        this.nguoidung = DangNhapMessenger.nguoidung;
        this.keyStatus = keyStatus;
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.soComment = soComment;
        this.status = status;
        xuLyLikeShareComment = new XuLyLikeShareComment();
    }

    @Override
    protected void populateViewHolder(final ViewHolderComment viewHolder, final Comment model, int position) {
        hienThiAnh(model.getAvatar_url(),viewHolder.imgAvatarComment);
        viewHolder.tvUsernameComment.setText(model.getUsername());
        viewHolder.tvUsernameComment.setOnClickListener(new View.OnClickListener() {
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
        viewHolder.tvLocationComment.setText(model.getLocation());
        if (!model.getContent().equals("") && model.getContent() != null) {
            viewHolder.tvContentComment.setVisibility(View.VISIBLE);
            viewHolder.tvContentComment.setText(model.getContent());
        }
        viewHolder.tvTimeComment.setText(model.getTime());
        viewHolder.tvTransactionLikeComment.setText(String.valueOf(model.getLike()));
        if (model.getImage_url() != null) {
            viewHolder.imgAnhMinhHoaComment.setVisibility(View.VISIBLE);
            hienThiAnh(model.getImage_url(),viewHolder.imgAnhMinhHoaComment);
        }
        viewHolder.imgSettingComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, viewHolder.imgSettingComment);
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
                                if (nguoidung.equals(model.getUsername())) {
                                    xuLyLikeShareComment.xuLyCapNhatSoLuongTangGiamLike(status, "comments", --soComment);
                                    mDatabase.child("comment").child(keyStatus).child(model.getKey_comment()).removeValue();
                                    Toast.makeText(mContext, "Bạn đã xóa bình luận ở đây!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(mContext, "Bạn không được phép xóa bình luận này!", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void hienThiAnh(String url, ImageView img) {
        AsyncTaskHienThiAnh task = new AsyncTaskHienThiAnh(img);
        task.execute(url);
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
}
