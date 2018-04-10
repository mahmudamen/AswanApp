package com.newaswan.seven;

/**
 * Created by Anonymo on 21/11/2017.
 */

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;


public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyHoder>{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    List<PhotoModel> list;
    Context context;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public ProjectAdapter(List<PhotoModel> list, Context context) {
        this.list = list;
        this.context = context;


    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        //   View view = LayoutInflater.from(context).inflate(R.layout.cardsales,parent,false);
        //   MyHoder myHoder = new MyHoder(view);

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardproject, parent, false);

            vh = new MyHoder(v);


        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }

        return (MyHoder) vh;
    }

    @Override
    public void onBindViewHolder(final MyHoder holder, int position) {
        if (holder instanceof MyHoder) {

            PhotoModel mylist = list.get(position);
            String Url = mylist.getImage();
            holder.title.setText(mylist.getTitle());
            holder.shordesc.setText(mylist.getShortdesc());
            holder.dayx.setText(mylist.getDayx());
            holder.date.setText(mylist.getDate());

            //  holder.imageView.setImageResource(Integer.parseInt(mylist.getImage()));
            Glide.with(context).load(mylist.getImage()).into(holder.imageView);
            holder.overflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(holder.overflow);

                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(holder.overflow);
                }
            });

        } else {
            ((ProgressViewHolder)list).progressBar.setVisibility(View.VISIBLE);

        }

    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main7, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }
    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_like:
                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_fav:
                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{
                arr=list.size();
            }
        }catch (Exception e){
        }
        return arr;
    }
    class MyHoder extends RecyclerView.ViewHolder{
        TextView title,shordesc,dayx,date;
        ImageView imageView,overflow;
        public MyHoder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.textViewTitle);
            shordesc= (TextView)itemView.findViewById(R.id.textViewShortDesc);
            date= (TextView)itemView.findViewById(R.id.textViewRating);
            dayx= (TextView)itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            overflow = itemView.findViewById(R.id.overflow);
        }

    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar1);
        }
    }
    public int getItemViewType(int position) {
        return list.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

}