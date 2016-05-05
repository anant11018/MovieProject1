package mmi.com.movieproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mmi.com.movieproject.R;
import mmi.com.movieproject.pojo.popular_movies.MovieData;

/**
 * Created by Ce on 5/3/2016.
 */
public class PopularMovieGridAdapter extends BaseAdapter{

    private Context ctx;
    private MovieData movieDatas;
    public PopularMovieGridAdapter(Context context, MovieData movieDatas)
    {
        this.ctx=context;
        this.movieDatas = movieDatas;
    }

    @Override
    public int getCount() {
        return movieDatas.getMovieResults().size();
    }

    @Override
    public Object getItem(int position) {
        return movieDatas.getMovieResults().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;


        // The convertView argument is essentially a "ScrapView" as described is Lucas post
        // http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
        // It will have a non-null value when ListView is asking you recycle the row layout.
        // So, when convertView is not null, you should simply update its contents instead of inflating a new row    layout.

        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item_layout, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textView =(TextView)convertView.findViewById(R.id.textview_f);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        // object item based on the position


        // assign values if the object is not null

            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
        viewHolder.textView.setText(movieDatas.getMovieResults().get(position).getTitle());
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500" + movieDatas.getMovieResults().get(position).getPosterPath()).noFade().fit().placeholder(R.drawable.popcorn).into(viewHolder.imageView);
        return convertView;

    }
    static class ViewHolderItem {
        ImageView imageView;
        TextView textView;
    }
}
