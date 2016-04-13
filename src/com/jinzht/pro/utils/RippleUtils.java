package com.jinzht.pro.utils;

import android.view.View;
import com.jinzht.pro.R;
import com.jinzht.pro.materialripple.MaterialRippleLayout;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * ����Ч��
 *
 * @auther Mr Jobs
 * @date 2015/9/5
 * @time 1:06
 */
public class RippleUtils {
    public static void rippleNormal(View view) {
        MaterialRippleLayout.on(view)
                .rippleColor(R.color.white)
                .rippleAlpha(0.2f)
                .rippleHover(true)
                .create();
    }

}
