/* 
 * An adapter object for Article Page 
 * https://stackoverflow.com/questions/30751870/can-one-use-cardview-for-listview-item-and-how
 */
package com.foodie.foodographer;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.content.Context;

public class RecyclerArticleList extends RecyclerView.Adapter<RecyclerArticleList.MyViewHolder> {

    private List<Article> article_list;
    private Context context;

    public RecyclerArticleList (ArrayList<Article> article_list){
        this.article_list = article_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the list view
        context = parent.getContext();
        View listItem = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.article_card, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // set view content with article object value
        Article article = article_list.get(position);
        final String url = article.getArticleURL();
        holder.artTitle.setText(article.getTitle());
        // download image from url
        new DownloadImageTask(holder.artIMG).execute(article.getIMGURL());
        // set onClick on each item
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // jump to Article page with web view
                Intent intent = new Intent(context, ArticlePage.class);
                intent.putExtra("webURL", url);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        // article count
        return article_list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView artTitle;
        private ImageView artIMG;
        // find views and assign to holder
        public MyViewHolder(View itemView) {
            super(itemView);
            artTitle = (TextView) itemView.findViewById(R.id.title);
            artIMG = (ImageView) itemView.findViewById(R.id.IMG);
        }
    }


}

