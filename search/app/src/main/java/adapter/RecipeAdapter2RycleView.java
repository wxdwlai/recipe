package adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.search.CollectResultActivity;
import com.example.dell.search.R;
import com.example.dell.search.Recipe;
import com.example.dell.search.SearchActivity;
import com.example.dell.search.SearchResultActivity;

import java.util.List;

/**
 * Created by dell on 2018/4/20.
 */

public class RecipeAdapter2RycleView extends RecyclerView.Adapter<RecipeAdapter2RycleView.ViewHolder>{

    private Context context;
    private List<Recipe> list;
    private AdapterView.OnItemClickListener onItemClickListener = null;
    private OnItemClickListener itemClickListener = null;

    private List<ViewHolder> holderList;
    private String title;
    private String path;
    private ImageView image;

    public RecipeAdapter2RycleView(List<Recipe> recipeList){
        list = recipeList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_pic,parent,false);
//        ViewHolder holder = new ViewHolder(view);
        final ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                itemClickListener.OnItemClick(holder.itemView,position);
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = holder.name.getText().toString();
                if(str.length()>2){
                    Intent intent = new Intent(v.getContext(),SearchResultActivity.class);
                    intent.putExtra("keyWord",str);
                    v.getContext().startActivity(intent);
                }
            }
        });
        return holder;
    }

    public interface OnItemClickListener{
        void OnItemClick(View view,int pisition);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = list.get(position);
        holder.image.setImageBitmap(recipe.getImage());
        holder.name.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        View view;
        public ViewHolder(View itemView) {

            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.name);
            view = itemView;
        }

    }
}
