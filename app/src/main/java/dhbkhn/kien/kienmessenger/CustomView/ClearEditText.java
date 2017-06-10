package dhbkhn.kien.kienmessenger.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import dhbkhn.kien.kienmessenger.R;

/**
 * Created by kiend on 10/16/2016.
 */
public class ClearEditText extends EditText {
    Drawable hinh, crossX, nopeCrossX;
    boolean visible = false;

    public ClearEditText(Context context) {
        super(context);
        initDrawable();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawable();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawable();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDrawable();
    }

    private void initDrawable(){
        crossX = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp).mutate();
        nopeCrossX = ContextCompat.getDrawable(getContext(),android.R.drawable.screen_background_light_transparent).mutate();
        cauHinh();
    }

    private void cauHinh(){
        setInputType(InputType.TYPE_CLASS_TEXT);
        Drawable[] drawables = getCompoundDrawables();
        hinh = visible? crossX:nopeCrossX;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],hinh,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= (getRight() - hinh.getBounds().width())) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (start == 0 && lengthAfter == 0) {
            visible = false;
            cauHinh();
        }else {
            visible = true;
            cauHinh();
        }
    }
}
