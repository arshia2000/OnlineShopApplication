package com.example.digicala.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.digicala.R;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.ui.ProductInformation;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class Adaptor extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Product> productlist = new ArrayList<>();
    ArrayList<Product> template = new ArrayList<>();
    boolean islistview;

    private ImageView im;
    private TextView title;
    private TextView price;
    private TextView off;
    private TextView exists;
    private RelativeLayout listview;
    private RelativeLayout gridview;

    private SharePrefernce sharePrefernce;

    public Adaptor(Context context, ArrayList<Product> productlist, boolean islistview) {
        this.context = context;
        this.productlist = productlist;
        this.islistview = islistview;
        this.template = productlist;
        sharePrefernce = new SharePrefernce(context);
    }

    @Override
    public int getCount() {
        return this.productlist.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            if (islistview == true) {

                view = LayoutInflater.from(context).inflate(R.layout.custom_listview_layout, null);

            } else {
                view = LayoutInflater.from(context).inflate(R.layout.custum_grid_layout, null);

            }
        }

        Product product = productlist.get(position);
        if (islistview) {

            im = view.findViewById(R.id.im_custom_list);
            title = view.findViewById(R.id.tv_title_custom_list);
            price = view.findViewById(R.id.tv_price_custom_list);
            exists = view.findViewById(R.id.tv_exist_custom_list);
            off = view.findViewById(R.id.tv_off_custom_list);
            listview = view.findViewById(R.id.custom_list_relative_layout);


            listview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ProductInformation.class);
                    intent.putExtra("id", product.getId());
                    context.startActivity(intent);
                }
            });


            title.setTextSize((title.getTextSize() / 3) + sharePrefernce.getfontsize());
            price.setTextSize((price.getTextSize() / 3) + sharePrefernce.getfontsize());
            if (sharePrefernce.gettheme()) {
                listview.setBackgroundColor(context.getResources().getColor(R.color.gray));
                price.setTextColor(context.getResources().getColor(R.color.white_color));


            }

            title.setText(product.getTitle());
            price.setText(product.getPrice() + " تومان ");
            if (product.isExists().equals("true")) {
                exists.setText("موجود");

            } else {
                exists.setText("ناموجود");
                exists.setBackground(context.getResources().getDrawable(R.drawable.custom_textview_do_not_exist));
            }

            if (product.getOff() > 0) {
                off.setText(String.valueOf(product.getOff()) + "%");
            } else {
                off.setVisibility(View.INVISIBLE);
            }
            Picasso.get().load(product.getImg().toString()).placeholder(R.drawable.placeholder).into(im);


            return view;

        } else {


            im = view.findViewById(R.id.im_custom_grid);
            title = view.findViewById(R.id.tv_title_custom_grid);
            price = view.findViewById(R.id.tv_price_custom_grid);
            exists = view.findViewById(R.id.tv_exist_custom_grid);
            off = view.findViewById(R.id.tv_off_custom_grid);
            gridview = view.findViewById(R.id.custom_grid_view_relative_layout);


            title.setText(product.getTitle());
            price.setText(product.getPrice() + " تومان ");
            if (product.isExists().equals("true")) {
                exists.setText("موجود");

            } else {
                exists.setText("ناموجود");
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
                gridview.setBackgroundColor(context.getResources().getColor(R.color.gray));
                price.setTextColor(context.getResources().getColor(R.color.white_color));
            }

            gridview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ProductInformation.class);
                    intent.putExtra("id", product.getId());
                    context.startActivity(intent);


                }
            });


            return view;

        }


    }

    @Override
    public Filter getFilter() {
        return new MyFilter();
    }


    private class MyFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence text) {
            FilterResults resualts = new FilterResults();

            if (text.toString().isEmpty()) {
                return resualts;
            }

            ArrayList<Product> filterlist = new ArrayList<>();
            for (Product item : template) {
                if (item.getTitle().contains(text.toString())) {
                    filterlist.add(item);
                }
            }

            resualts.count = filterlist.size();
            resualts.values = filterlist;

            return resualts;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0) {
                return;
            }
            productlist = (ArrayList<Product>) results.values;
            notifyDataSetChanged();
        }
    }


}
