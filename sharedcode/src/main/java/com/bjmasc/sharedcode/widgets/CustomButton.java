package com.bjmasc.sharedcode.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.bjmasc.sharedcode.R;
import com.bjmasc.sharedcode.utils.UIUtils;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        init(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.CustomButton, 0, 0);
            if (typedArray != null) {
                setFont(typedArray);
            }
        }
    }

    private void setFont(TypedArray typedArray) {
        try {
            int textFont = typedArray.getInteger(R.styleable.CustomButton_text_font, 0);
            setTypeface(UIUtils.getFont(getContext(), textFont));
        } finally {
            typedArray.recycle();
        }
    }
}
