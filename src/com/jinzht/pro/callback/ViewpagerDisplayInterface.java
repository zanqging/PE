package com.jinzht.pro.callback;

/**
 * 代码有问题别找我！虽然是我写的，但是他们自己长歪了。
 *
 * @auth Mr.Jobs
 * @date 2016/1/25
 * @time 11:42
 */
public interface ViewpagerDisplayInterface {
    /**准备做个懒加载，而非预加载*/
   public void onDisplay(int position);
}
