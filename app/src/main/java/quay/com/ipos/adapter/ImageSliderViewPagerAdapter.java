package quay.com.ipos.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quay.com.ipos.R;
import quay.com.ipos.productCatalogue.WebViewActivity;
import quay.com.ipos.productCatalogue.productModal.ProductDetailModel;
import quay.com.ipos.utility.NetUtil;

/**
 * Created by niraj.kumar on 4/18/2018.
 */

public class ImageSliderViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ProductDetailModel> productDetailModels;
    private Integer[] images = {R.drawable.elevation, R.drawable.elevation, R.drawable.elevation};

    public ImageSliderViewPagerAdapter(Context context, ArrayList<ProductDetailModel> productDetailModels) {
        this.context = context;
        this.productDetailModels = productDetailModels;
    }

    @Override
    public int getCount() {
        return productDetailModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_image_slider_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        VideoView videoView = view.findViewById(R.id.simpleVideoView);
        final ProductDetailModel productDetailModel = productDetailModels.get(position);

        if (productDetailModel.isImage()) {
            imageView.setVisibility(View.VISIBLE);
            if (NetUtil.isNetworkAvailable(context)) {
                Picasso.get().load(productDetailModel.getImageURL()).into(imageView);

            } else {
                Picasso.get()
                        .load(productDetailModel.getImageURL())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {

                            }

                        });
            }

        } else {
            imageView.setVisibility(View.VISIBLE);
//            videoView.setVisibility(View.VISIBLE);
            String path = productDetailModel.getVideo();
            String imgUrl = "http://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(path) + "/0.jpg";
            Picasso.get().load(imgUrl).into(imageView);


            //            Uri uri = Uri.parse(path);
//            videoView.setVideoURI(uri);

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productDetailModel.isVideo()) {
                    Intent i = new Intent(context, WebViewActivity.class);
                    i.putExtra("Video", productDetailModel.getVideo());
                    context.startActivity(i);
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    public static String getYoutubeVideoIdFromUrl(String inUrl) {
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
