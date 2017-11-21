package br.ufms.facom;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import br.ufms.facom.app.AppController;
import br.ufms.facom.model.Course;
import br.ufms.facom.task.CourseTask;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CourseDetailActivity extends Activity implements Refreshable
{
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);
        Course m = (Course) getIntent().getExtras().get("course");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.thumbnail);
        TextView title = (TextView) findViewById(R.id.title);
        TextView rating = (TextView) findViewById(R.id.rating);
        TextView genre = (TextView) findViewById(R.id.genre);
        TextView year = (TextView) findViewById(R.id.releaseYear);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // título
        title.setText(m.getName());

        // rating
        rating.setText("Codigo: " + String.valueOf(m.getCode()));

        // genero
        genre.setText("Turno: " + String.valueOf(m.getShift()));


        // ano de realease

        year.setText(String.valueOf(m.getCenter()));

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.animation);
        thumbNail.startAnimation(fade);

        // new
        //new CourseTask(this).execute("http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&Episode=1");
    }

    public void refresh(String content)
    {
        TextView text = (TextView) findViewById(R.id.additional);

        text.setText(content);
    }
}
