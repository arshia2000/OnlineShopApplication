package com.example.digicala.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.digicala.MainActivity;
import com.example.digicala.R;
import com.example.digicala.entity.ImageCategory;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.ui.Category;
import com.example.digicala.ui.DownloadActivity;
import com.example.digicala.ui.Login;
import com.example.digicala.ui.ProductInformation;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ImageSliderAdaptor extends SliderViewAdapter implements View.OnClickListener {

    private ImageView background;
    private ImageView gifcontainer;
    private TextView describtion;
    private View itemview;
    private RelativeLayout sliderlayout;

    private SharePrefernce sharePrefernce;


    Context context;
    ArrayList<ImageCategory> imagelist = new ArrayList<>();

    public ImageSliderAdaptor(Context context, ArrayList<ImageCategory> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
        sharePrefernce = new SharePrefernce(context);
    }

    @Override
    public SliderViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_imageslider, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewAdapter.ViewHolder viewHolder, int position) {

        final ImageCategory imageCategory = imagelist.get(position);

        background.setImageResource(imageCategory.getImg_source());
        describtion.setText(imageCategory.getDescription());

        describtion.setTextSize((describtion.getTextSize() / 3) + sharePrefernce.getfontsize());


        sliderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageCategory.getTitle().equals("digital")) {

                    Intent intent = new Intent(context, Category.class);
                    intent.putExtra("category", "digital");
                    context.startActivity(intent);


                } else if (imageCategory.getTitle().equals("home")) {

                    Intent intent = new Intent(context, Category.class);
                    intent.putExtra("category", "home");
                    context.startActivity(intent);

                } else if (imageCategory.getTitle().equals("book")) {
                    Intent intent = new Intent(context, DownloadActivity.class);
                    context.startActivity(intent);

                }


            }
        });


    }

    @Override
    public int getCount() {
        return imagelist.size();
    }

    @Override
    public void onClick(View v) {

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Login.class);
                context.startActivity(intent);
            }
        });


    }


    public class ViewHolder extends SliderViewAdapter.ViewHolder {


        public ViewHolder(View view) {
            super(view);

            background = view.findViewById(R.id.im_custom_slider);
            describtion = view.findViewById(R.id.tv_custom_slider);
            itemview = view;
            sliderlayout = view.findViewById(R.id.slider_image_layout);


        }


    }


    public interface onnotelistener {
        void onnotelistener(int position);
    }
}
