package com.example.dell.search;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import httpClient.webPic;

/**
 * Created by dell on 2018/4/12.
 */

public class RecipeAdapter extends ArrayAdapter<Recipe>{
    private int resourceId;
    LayoutInflater inflater;
    private List<Recipe> list ;
    Context context;
    TextView name;
    ImageView image;
    public RecipeAdapter(@NonNull Context context, int resource, @NonNull List<Recipe> objects) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
        list = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        Recipe recipe = list.get(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.show_pic,parent,false);
            image = (ImageView)view.findViewById(R.id.image);
            name = (TextView)view.findViewById(R.id.name);
        }
        else {
            view = convertView;
            image = (ImageView)view.findViewById(R.id.image);
            name = (TextView)view.findViewById(R.id.name);
        }
        image.setImageBitmap(recipe.getImage());
        name.setText(recipe.getName());
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Recipe getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
