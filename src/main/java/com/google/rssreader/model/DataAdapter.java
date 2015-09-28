package com.google.rssreader.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.rssreader.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

/**
 * Created by Roman on 26.09.2015.
 */
public class DataAdapter extends BaseAdapter {

    private List<Data> items;
    private Context context;

    public DataAdapter(Context context, List<Data> items) {
        super();
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Data data = getItem(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item, null);
        }

        ((TextView) convertView.findViewById(R.id.title))
                .setText(data.getTitle());
        ((TextView) convertView.findViewById(R.id.pubDate))
                .setText(data.getPubDate());
        ((TextView) convertView.findViewById(R.id.author))
                .setText(data.getAuthor());
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        if (data.getImageUrl() == null || data.getImageUrl() == "") {
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.no_photo));

        } else {
            UrlImageViewHelper.setUrlDrawable(imageView, data.getImageUrl());
        }
        return convertView;
    }
}
