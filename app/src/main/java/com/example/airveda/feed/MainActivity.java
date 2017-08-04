package com.example.airveda.feed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.airveda.feed.Model.Header;
import com.example.airveda.feed.Model.ListData;
import com.example.airveda.feed.Model.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    public  static List<ListData> dataList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_view);
        prepareContent();
    }
    private void prepareContent() {
        String dataString = getResources().getString(R.string.data_string);
        Set<String> dateStringsSet = new HashSet<>();
        try {
            JSONArray jsonArray = new JSONArray(dataString);
            for(int i =0 ; i < jsonArray.length();i++)
            {
                JSONObject postObject = jsonArray.getJSONObject(i);
                dateStringsSet.add(postObject.getString("time"));
            }
            Iterator iterator = dateStringsSet.iterator();

            while (iterator.hasNext()) {
                  Header header = new Header();
                  String currentDate = iterator.next().toString();
                  header.setDateString(currentDate);
                  dataList.add(header);
                for(int i =0 ; i < jsonArray.length();i++)
                {
                    JSONObject postObject = jsonArray.getJSONObject(i);
                    if(postObject.getString("time").equals(currentDate))
                    {
                        Post post = new Post();
                        post.setTitle(postObject.getString("title"));
                        post.setDescription(postObject.getString("description"));
                        if(postObject.has("text"))
                           post.setText(postObject.getString("text"));
                        if(postObject.has("imageUrl"))
                            post.setImageUrl(postObject.getString("imageUrl"));
                        post.setName(postObject.getString("name"));
                        post.setTime(postObject.getString("time"));
                        dataList.add(post);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        setAdapter();
    }

    @Override
    protected void onResume() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private void setAdapter() {
        adapter = new CustomAdapter(MainActivity.this, dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getAdapter().getItemViewType(i)==0)
                {
                    Intent in = new Intent(MainActivity.this,DetailView.class);
                    in.putExtra("position", i);
                    startActivity(in);
                }
            }
        });
    }

}
