package com.jinzht.pro.qiniu.storage;


import com.jinzht.pro.qiniu.http.CancellationHandler;

/**
 * 定义用户取消数据或文件上传的信号
 * 用户取消上传时，必须实现的接口
 */
public interface UpCancellationSignal extends CancellationHandler {

}
