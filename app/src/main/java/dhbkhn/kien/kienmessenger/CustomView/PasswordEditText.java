package dhbkhn.kien.kienmessenger.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
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
public class PasswordEditText extends EditText{
    Drawable hinh, eye, eyeNope;
    boolean useNope = false;
    boolean visible = false;
    boolean useValidate = false;

    public PasswordEditText(Context context) {
        super(context);
        initDrawable(null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrawable(attrs);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDrawable(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDrawable(attrs);
    }

    private void initDrawable(AttributeSet attributeSet){
        if (attributeSet != null) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.PasswordEditText, 0, 0);
            this.useNope = typedArray.getBoolean(R.styleable.PasswordEditText_useNope, true);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_black_24dp).mutate();
        eyeNope = ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_black_24dp).mutate();
        cauHinh();
    }

    private void cauHinh(){
        setInputType(InputType.TYPE_CLASS_TEXT|(visible?InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables = getCompoundDrawables();
        hinh = useNope && !visible?eyeNope:eye;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],hinh,drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= (getRight() - hinh.getBounds().width())) {
            visible = !visible;
            cauHinh();
            invalidate();//phai co de reset
        }
        return super.onTouchEvent(event);
    }
}
