package com.example.vehicleapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    int[] images = {
            R.drawable.search,
            R.drawable.location,
            R.drawable.book,
            R.drawable.ride
    };

    int[] headings = {
            R.string.heading_1,
            R.string.heading_2,
            R.string.heading_3,
            R.string.heading_4
    };
    int[] desc = {
            R.string.desc_1,
            R.string.desc_2,
            R.string.desc_3,
            R.string.desc_4
    };

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.title_image);
        TextView slideheading = (TextView) view.findViewById(R.id.heading);
        TextView slidedesc = (TextView) view.findViewById(R.id.desc);

        slidetitleimage.setImageResource(images[position]);
        slideheading.setText(headings[position]);
        slidedesc.setText(desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
