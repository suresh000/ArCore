package com.bjmasc.sharedcode.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.bjmasc.sharedcode.R;
import com.bjmasc.sharedcode.utils.UIUtils;

public class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.CustomEditText, 0, 0);
            if (typedArray != null) {
                setFont(typedArray);
                setTextSize();
            }
        }
    }

    private void setFont(TypedArray typedArray) {
        try {
            int textFont = typedArray.getInteger(R.styleable.CustomEditText_text_font, 0);
            setTypeface(UIUtils.getFont(getContext(), textFont));
        } finally {
            typedArray.recycle();
        }
    }

    private void setTextSize() {
        final float DEFAULT_TEXT_SIZE = 14;
        setTextSize(DEFAULT_TEXT_SIZE);
    }
}
