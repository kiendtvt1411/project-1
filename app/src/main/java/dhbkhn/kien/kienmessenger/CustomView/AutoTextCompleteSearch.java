package dhbkhn.kien.kienmessenger.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/4/2016.
 */
public class AutoTextCompleteSearch extends AutoCompleteTextView {
    Drawable search;
    DatabaseReference mDatabase;

    public AutoTextCompleteSearch(Context context) {
        super(context);
        inIt();
    }

    public AutoTextCompleteSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        inIt();
    }

    public AutoTextCompleteSearch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inIt();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoTextCompleteSearch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inIt();
    }

    @TargetApi(Build.VERSION_CODES.N)
    public AutoTextCompleteSearch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, popupTheme);
        inIt();
    }


    private void cauHinh(){
        setInputType(InputType.TYPE_CLASS_TEXT);
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],search,drawables[3]);
    }

    private void inIt() {
        search = ContextCompat.getDrawable(getContext(), R.drawable.ic_search_black_24dp).mutate();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cauHinh();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= (getRight() - search.getBounds().width())) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);

    }
}
