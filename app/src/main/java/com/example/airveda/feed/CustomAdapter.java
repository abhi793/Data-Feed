package com.example.airveda.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.airveda.feed.Model.Header;
import com.example.airveda.feed.Model.ListData;
import com.example.airveda.feed.Model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by airveda on 03/08/17.
 */

class CustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_NONE = 2;
    private static final String FROM = "From ";


    private LayoutInflater mInflater;

    private List<ListData> dataList;

    private Context context;

    public CustomAdapter(Context context, List<ListData> dataList) {
        this.dataList = dataList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if (getCount() > 0) {
            ListData listData = getItem(position);
            if (listData instanceof Header) {
                return TYPE_SEPARATOR;
            } else if (listData instanceof Post) {
                return TYPE_ITEM;
            } else {
                return TYPE_NONE;
            }
        } else {
            return TYPE_NONE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public ListData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == TYPE_SEPARATOR) {
            return getSectionView(position);
        } else if (getItemViewType(position) == TYPE_ITEM) {
            return getItemView(position);
        }
        return null;
    }

    @NonNull
    private View getItemView(int position) {
        ListItemViewHolder itemViewHolder;
        View myView = mInflater.inflate(R.layout.list_item, null);
        itemViewHolder = new ListItemViewHolder(myView);
        Post listItem = (Post) getItem(position);
        itemViewHolder.setValues(listItem);
        if(listItem.getText()==null)
        {
             itemViewHolder.ifTextNull(listItem.getImageUrl());
        }
        else if(listItem.getImageUrl()==null)
        {
              itemViewHolder.ifImageNUll(listItem.getText());
        }
        else {
               itemViewHolder.ifImageTextPresent(listItem.getImageUrl(),listItem.getText());
        }
        return myView;
    }

    @NonNull
    private View getSectionView(int position) {
        ViewHolder sectionViewHolder;
        View myView = mInflater.inflate(R.layout.list_header, null);
        sectionViewHolder = new ViewHolder(myView);
        sectionViewHolder.showText(((Header) getItem(position)).getDateString());
        return myView;
    }

    private class ViewHolder {

        private TextView textView;

        public ViewHolder(View view)
        {
           textView = (TextView) view.findViewById(R.id.title);
        }
        public void showText(String text)
        {
            textView.setText(text);
        }

    }
    private class ListItemViewHolder {

        private TextView titleTextView;
        private View imageTextContainerLayout;
        private TextView textView;
        private ImageView imageView;
        private TextView aloneTextView;
        private ImageView aloneImageView;
        private Button likeButton;
        private TextView sourceTextView;

        public ListItemViewHolder(View view)
        {
            titleTextView = (TextView) view.findViewById(R.id.title_item);
            imageTextContainerLayout = view.findViewById(R.id.both_visible);
            textView = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.image);
            aloneTextView = (TextView) view.findViewById(R.id.alone_text);
            aloneImageView = (ImageView) view.findViewById(R.id.alone_image);
            likeButton = (Button) view.findViewById(R.id.like_button);
            sourceTextView = (TextView) view.findViewById(R.id.source);
        }
        public  void setValues(final Post postData)
        {
            if(!postData.isLiked())
            {
                likeButton.setBackground(context.getResources().getDrawable(R.drawable.unlike_button));
                likeButton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                likeButton.setText(context.getResources().getString(R.string.like));
            }
            else {
                likeButton.setBackground(context.getResources().getDrawable(R.drawable.like_button));
                likeButton.setTextColor(context.getResources().getColor(R.color.color_white));
                likeButton.setText(context.getResources().getString(R.string.unlike));
            }
            titleTextView.setText(postData.getTitle());
            String sourceString = FROM + postData.getName();
            sourceTextView.setText(sourceString);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!postData.isLiked())
                    {
                        likeButton.setBackground(context.getResources().getDrawable(R.drawable.like_button));
                        likeButton.setTextColor(context.getResources().getColor(R.color.color_white));
                        likeButton.setText(context.getResources().getString(R.string.unlike));
                        postData.setLiked(true);
                    }
                    else {
                        likeButton.setBackground(context.getResources().getDrawable(R.drawable.unlike_button));
                        likeButton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        likeButton.setText(context.getResources().getString(R.string.like));
                        postData.setLiked(false);
                    }
                }
            });
        }
        public void ifTextNull(String imageUrl)
        {
            imageTextContainerLayout.setVisibility(View.GONE);
            aloneTextView.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(imageUrl)
                    .into(aloneImageView);
        }
        public void ifImageNUll(String text)
        {
            imageTextContainerLayout.setVisibility(View.GONE);
            aloneImageView.setVisibility(View.GONE);
            aloneTextView.setText(text);
        }
        public  void ifImageTextPresent(String imageUrl, String text)
        {
            aloneTextView.setVisibility(View.GONE);
            aloneImageView.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(imageUrl)
                    .into(imageView);
            textView.setText(text);
        }
    }

}