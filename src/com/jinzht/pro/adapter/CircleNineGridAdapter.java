package com.jinzht.pro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jinzht.pro.R;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.view.SquareLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/10/12.
 */
public class CircleNineGridAdapter extends BaseAdapter {
    ViewHolder1 vh1=null;
    ViewHolder2 vh2=null;

    Context context;
    private LayoutInflater li;
    private List<String> list;
    private final int TYPE_ONE=0,TYPE_TWO=1,TYPE_COUNT=2;

    public CircleNineGridAdapter( Context context,List<String> list) {
        this.context = context;
        li=LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    /** 该方法返回多少个不同的布局*/
    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }
    /** 根据position返回相应的Item*/
    @Override
    public int getItemViewType(int position) {
        if (list==null||list.size()==0){
            return TYPE_TWO;
        }
        if (position<list.size()){
            return TYPE_ONE;
        }else {
            return TYPE_TWO;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type=getItemViewType(position);
        if(convertView==null){
            switch (type) {
                case TYPE_ONE:
                    convertView=li.inflate(R.layout.item_circle_squre_one, null);/*先把视图获取，要不然buffer报空*/
                    vh1=new ViewHolder1(convertView);
                    convertView.setTag(vh1);
                    break;
                case TYPE_TWO:
                    convertView=li.inflate(R.layout.item_circle_squre_one, null);
                    vh2=new ViewHolder2(convertView);
                    convertView.setTag(vh2);
                    break;
            }
        }else {
            switch (type) {
                case TYPE_ONE:
                    vh1=(ViewHolder1) convertView.getTag();
                    break;
                case TYPE_TWO:
                    vh2=(ViewHolder2) convertView.getTag();
                    break;
            }
        }
        if (type==TYPE_ONE){
            vh1.iv_circle_image.setScaleType(ImageView.ScaleType.FIT_XY);
            UpLoadImageUtils.getSelectImage(context, "file://" + list.get(position), vh1.iv_circle_image);
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.user_loading)
//                    .showImageOnFail(R.drawable.user_loadingfail)
//                    .cacheInMemory(false).resetViewBeforeLoading(false)
//                    .cacheOnDisk(true)
//                    .bitmapConfig(Bitmap.Config.RGB_565)
////                .displayer(new RoundedBitmapDisplayer(5,5))
//
//                    .build();
//            ImageLoader.getInstance().displayImage("file://" + list.get(position), vh1.iv_circle_image, options, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String s, View view) {
//
//                }
//
//                @Override
//                public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//                }
//
//                @Override
//                public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
//                    if (bitmap!=null){
//                        ImageView imageView = (ImageView) view;
//                        boolean firstDisplay = !list.get(position).contains(imageUri);
//                        if (firstDisplay) {
//                            FadeInBitmapDisplayer.animate(imageView, 500); // 设置image隐藏动画500ms
//                            list.add(imageUri); // 将图片uri添加到集合中
//                        }
//                    }
//                }
//
//                @Override
//                public void onLoadingCancelled(String s, View view) {
//
//                }
//            });
//            setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));
//            File file = new File(ImageLoader.getInstance().getDiskCache().get("file://" + list.get(position)).getPath());
//            Log.e("ssss", file.length()+"lenghth");
//                Uri uri_temp = Uri.parse(list.get(position));
//            vh1.iv_circle_image.setImageURI(uri_temp);
//                UpLoadImageUtils.getUserImage(list.get(position), vh1.iv_circle_image);
        }else {
            vh2.square.setBackgroundResource(R.drawable.circle_add_bg);
//            vh2.iv_circle_image.setBackgroundColor(context.getResources().getColor(R.color.gray1));
            vh2.iv_circle_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            vh2.iv_circle_image.setImageResource(R.drawable.circle_add);
        }
        return convertView;
    }
    static class ViewHolder1 {
        @Bind(R.id.iv_circle_image) ImageView iv_circle_image;
        public ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class ViewHolder2 {
        @Bind(R.id.iv_circle_image) ImageView iv_circle_image;
        @Bind(R.id.square) SquareLayout square;
        public ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
