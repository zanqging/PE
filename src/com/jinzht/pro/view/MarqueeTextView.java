package com.jinzht.pro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/19.
 */
public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TextView默认设置是第一个获取到的光标，
    //如果想让所有的TextView都有跑马灯效果,则让所有的TextView都获取到光标就行了
    //这里return true 就是让所有的TextView都获取到光标
    @Override
    public boolean isFocused() {
        return true;
    }
}
