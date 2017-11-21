package br.ufms.facom.adater;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ufms.facom.R;
import br.ufms.facom.app.AppController;
import br.ufms.facom.model.Course;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CourseListAdapter extends BaseAdapter
{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Course> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CourseListAdapter(Activity activity, List<Course> movieItems)
    {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount()
    {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location)
    {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // pegando dados do filme
        Course m = movieItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // Nome
        title.setText(m.getName());

        // Codigo
        rating.setText("Codigo: " + String.valueOf(m.getCode()));




        String genreStr =  m.getCenter();

        //genreStr = genreStr.length() > 0 ? genreStr.substring(0, genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);

        // ano de realease
        year.setText(String.valueOf(m.getShift()));

        return convertView;
    }

}