package com.example.digicala.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.digicala.R;
import com.example.digicala.entity.BookProduct;
import com.example.digicala.entity.Product;
import com.example.digicala.sharedpreferences.SharePrefernce;
import com.example.digicala.ui.ProductInformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DownloadListAdapter extends BaseAdapter {


    public DownloadListAdapter(Context context, ArrayList<BookProduct> downloadlist) {
        this.context = context;
        this.downloadlist = downloadlist;
        sharePrefernce = new SharePrefernce(context);
    }

    Context context;
    ArrayList<BookProduct> downloadlist = new ArrayList<>();
    boolean islistview;

    private ImageView im;
    private TextView title;
    private TextView athor;
    private RelativeLayout relativeLayout;

    private SharePrefernce sharePrefernce;


    @Override
    public int getCount() {
        return this.downloadlist.size();
    }

    @Override
    public Object getItem(int position) {
        return this.downloadlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            view = LayoutInflater.from(context).inflate(R.layout.custom_listview_download, null);

        }

        final BookProduct product = downloadlist.get(position);

        im = view.findViewById(R.id.im_custom_list_download);
        title = view.findViewById(R.id.tv_title_download);
        athor = view.findViewById(R.id.tv_athor_download);
        relativeLayout = view.findViewById(R.id.relative_layout_download);


        im.setImageResource(product.getImg());
        title.setText(product.getTitle());
        athor.setText(product.getAthor());


        title.setTextSize((title.getTextSize() / 3) + sharePrefernce.getfontsize());
        athor.setTextSize((athor.getTextSize() / 3) + sharePrefernce.getfontsize());

        if (sharePrefernce.gettheme()) {
            relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.gray));
            title.setTextColor(context.getResources().getColor(R.color.white_color));
            athor.setTextColor(context.getResources().getColor(R.color.white_color));

        }


        return view;

    }


}










