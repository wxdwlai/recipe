package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.search.R;
import com.example.dell.use.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/4/22.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {

    List<Comment> list = new ArrayList<Comment>();
    private int resourceId;
    Context context;
    ImageView head;
    TextView userId,userCom;
    TextView time;
    Comment[] comment;

//    public CommentAdapter(@NonNull Context context, int resource, @NonNull Comment[] objects) {
//        super(context, resource, objects);
//        this.context = context;
//        resourceId = resource;
//        comment = objects;
//    }

    public CommentAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        resourceId = resource;
        list = new ArrayList<>(objects);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Comment getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Comment comment = list.get(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.comment,parent,false);
            head = (ImageView)view.findViewById(R.id.head);
            userId = (TextView)view.findViewById(R.id.userId);
            userCom = (TextView)view.findViewById(R.id.userCom);
            time = (TextView)view.findViewById(R.id.time);
        }
        else{
            view = convertView;
            head = (ImageView)view.findViewById(R.id.head);
            userId = (TextView)view.findViewById(R.id.userId);
            userCom = (TextView)view.findViewById(R.id.userCom);
            time = (TextView)view.findViewById(R.id.time);
        }
//        head.setText(comment.getHead());
        userId.setText(comment.getUserId());
        userCom.setText(comment.getUserCom());
        time.setText(comment.getTime());
        return view;
    }

    public void addComment(Comment comment){
        list.add(comment);
        notifyDataSetChanged();
    }
}
