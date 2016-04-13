package com.jinzht.pro.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by Administrator on 2015/11/23.
 */
public class PathConvertUri {

    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                  Context activity) {
        String filePath = "";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.getContentResolver().query(selectedVideoUri, filePathColumn, null, null, null);
//      Ҳ��������ķ����õ�cursor
//      Cursor cursor = this.managedQuery(selectedVideoUri, filePathColumn, null, null, null);
        if (cursor!=null&& cursor.moveToFirst()){
            int columnIndex =cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }
    public static String getImagePathFromUri(Uri fileUrl,Context context) {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null)
        {
            if (fileUrl.getScheme().toString().compareTo("content") == 0)
            {
                // content://��ͷ��uri
                Cursor cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // ȡ���ļ�·��
                    // Android 4.1 ������SD��Ŀ¼��sdcardӳ�䵽/storage/sdcard0
//                    if (!fileName.startsWith("/storage") && !fileName.startsWith("/mnt"))
//                    {
//                        // ����Ƿ��С�/mnt��ǰ׺
//                        fileName = "/mnt" + fileName;
//                    }
                    cursor.close();
                }
            }
            else if (fileUrl.getScheme().compareTo("file") == 0) // file:///��ͷ��uri
            {
//                fileName = filePathUri.toString();// �滻file://
                fileName = filePathUri.toString().replace("file://", "");
//                 int index = fileName.indexOf("/sdcard");
//                fileName  = index == -1 ? fileName : fileName.substring(index);
//                if (!fileName.startsWith("/mnt"))
//                {
//                    // ����"/mnt"ͷ
//                    fileName = "/mnt"+fileName;
//                }
            }
        }
        return fileName;
    }
    public static Uri getImageContentUri(Context context, java.io.File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
}
