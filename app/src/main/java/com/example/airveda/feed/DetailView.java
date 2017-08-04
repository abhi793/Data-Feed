package com.example.airveda.feed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.airveda.feed.Model.Post;
import com.squareup.picasso.Picasso;

public class DetailView extends AppCompatActivity {

    private TextView titleTextView;
    private View imageTextContainerLayout;
    private TextView textView;
    private ImageView imageView;
    private TextView aloneTextView;
    private ImageView aloneImageView;
    private Button likeButton;
    private TextView sourceTextView;
    private TextView descriptionContent;
    private static final String FROM = "From";
    private Post currentPost;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Intent i = getIntent();
        position = i.getIntExtra("position", 1);
        currentPost = (Post) MainActivity.dataList.get(position);
        findViewsById();
        setData(currentPost);
    }
    private void findViewsById()
    {
        titleTextView = (TextView) findViewById(R.id.title_item);
        imageTextContainerLayout = findViewById(R.id.both_visible);
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);
        aloneTextView = (TextView) findViewById(R.id.alone_text);
        aloneImageView = (ImageView) findViewById(R.id.alone_image);
        likeButton = (Button) findViewById(R.id.like_button);
        sourceTextView = (TextView) findViewById(R.id.source);
        descriptionContent = (TextView) findViewById(R.id.desc_content);
    }
    private void setData(final Post post)
    {
        descriptionContent.setText(post.getDescription());
        if(!post.isLiked())
        {
            likeButton.setBackground(getResources().getDrawable(R.drawable.unlike_button));
            likeButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            likeButton.setText(getResources().getString(R.string.like));
        }
        else {
            likeButton.setBackground(getResources().getDrawable(R.drawable.like_button));
            likeButton.setTextColor(getResources().getColor(R.color.color_white));
            likeButton.setText(getResources().getString(R.string.unlike));
        }
        titleTextView.setText(post.getTitle());
        String sourceString = FROM + post.getName();
        sourceTextView.setText(sourceString);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!post.isLiked())
                {
                    likeButton.setBackground(getResources().getDrawable(R.drawable.like_button));
                    likeButton.setTextColor(getResources().getColor(R.color.color_white));
                    likeButton.setText(getResources().getString(R.string.unlike));
                    ((Post)MainActivity.dataList.get(position)).setLiked(true);
                }
                else {
                    likeButton.setBackground(getResources().getDrawable(R.drawable.unlike_button));
                    likeButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                    likeButton.setText(getResources().getString(R.string.like));
                    ((Post)MainActivity.dataList.get(position)).setLiked(false);
                }
            }
        });

        if(post.getText()==null)
        {
            imageTextContainerLayout.setVisibility(View.GONE);
            aloneTextView.setVisibility(View.GONE);
            Picasso.with(this)
                    .load(post.getImageUrl())
                    .into(aloneImageView);
        }
        else if(post.getImageUrl()==null) {
            imageTextContainerLayout.setVisibility(View.GONE);
            aloneImageView.setVisibility(View.GONE);
            aloneTextView.setText(post.getText());
        }
        else {
            aloneTextView.setVisibility(View.GONE);
            aloneImageView.setVisibility(View.GONE);
            Picasso.with(this)
                    .load(post.getImageUrl())
                    .into(imageView);
            textView.setText(post.getText());
        }

    }

}
