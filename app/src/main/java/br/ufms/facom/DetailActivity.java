package br.ufms.facom;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import br.ufms.facom.app.AppController;
import br.ufms.facom.model.Movie;
import br.ufms.facom.task.MovieTask;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class DetailActivity extends Activity implements Refreshable
{
	private ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_detail);
		Movie m = (Movie) getIntent().getExtras().get("movie");
		
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
		title.setText(m.getTitle());
		
		// rating
		rating.setText("Rating: " + String.valueOf(m.getRating()));
		
		// gênero
		String genreStr = "";
		for (String str : m.getGenre())
		{
			genreStr += str + ", ";
		}
		genreStr = genreStr.length() > 0 ? genreStr.substring(0, genreStr.length() - 2) : genreStr;
		genre.setText(genreStr);
		
		// ano de realease
		
		year.setText(String.valueOf(m.getYear()));
		
		Animation fade = AnimationUtils.loadAnimation(this, R.anim.animation);
		thumbNail.startAnimation(fade);
		
		// new
		new MovieTask(this).execute("http://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&Episode=1");
	}
	
	public void refresh(String content)
	{
		TextView text = (TextView) findViewById(R.id.additional);
		
		text.setText(content);
	}
}
