package com.example.digicala.adaptor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digicala.R;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.ui.Login;
import com.example.digicala.ui.ProductInformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleAdaptor extends RecyclerView.Adapter {

    Context context;
    ArrayList<Product> productslist = new ArrayList<>();

    private ImageView im;
    private ImageView im_firstimage;
    private TextView title;
    private TextView price;
    private TextView off;
    private TextView exists;
    private RelativeLayout layout;
    private Button btn;

    private SharePrefernce sharePrefernce;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.custom_recycle_layout, null);
        return new ViewHolder(view);

    }

    public RecycleAdaptor(Context context, ArrayList<Product> productslist) {
        this.context = context;
        this.productslist = productslist;
        sharePrefernce = new SharePrefernce(context);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        Product product = productslist.get(position);


        if (product.isIsimage().equals("true")) {
            im_firstimage.setVisibility(View.VISIBLE);
            im_firstimage.setImageResource(R.drawable.recyclefirstitem);
            title.setVisibility(View.GONE);
            layout.setClickable(false);
            layout.setBackground(null);
            btn.setVisibility(View.GONE);
            im.setVisibility(View.GONE);
            layout.setBackgroundColor(Color.RED);
            price.setVisibility(View.GONE);
            off.setVisibility(View.GONE);
            exists.setVisibility(View.GONE);

        } else if (product.isIsimage().equals("false")) {
            title.setText(product.getTitle());
            price.setText(product.getPrice() + " تومان ");
            if (product.isExists().equals("true")) {
                exists.setText("موجود");

            } else {
                exists.setText("نا موجود");
                exists.setBackground(context.getResources().getDrawable(R.drawable.custom_textview_do_not_exist));
            }
            if (product.getOff() > 0) {
                off.setText(String.valueOf(product.getOff()) + "%");
            } else {
                off.setVisibility(View.INVISIBLE);
            }
            Picasso.get().load(product.getImg()).placeholder(R.drawable.placeholder).into(im);

            title.setTextSize((title.getTextSize() / 3) + sharePrefernce.getfontsize());
            price.setTextSize((price.getTextSize() / 3) + sharePrefernce.getfontsize());

            if (sharePrefernce.gettheme()) {
                layout.setBackgroundColor(context.getResources().getColor(R.color.gray));
                price.setTextColor(context.getResources().getColor(R.color.white_color));


            }


            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductInformation.class);
                    intent.putExtra("id", product.getId());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return productslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View view) {
            super(view);
            im = view.findViewById(R.id.im_custom_grid);
            im_firstimage = view.findViewById(R.id.im_firstitem);
            title = view.findViewById(R.id.tv_title_custom_grid);
            price = view.findViewById(R.id.tv_price_custom_grid);
            exists = view.findViewById(R.id.tv_exist_custom_grid);
            off = view.findViewById(R.id.tv_off_custom_grid);
            layout = view.findViewById(R.id.recycle_layout_id);
            btn = view.findViewById(R.id.btn_custom_recycle);
        }
    }
}
