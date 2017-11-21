package br.ufms.facom.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.ufms.facom.DetailActivity;
import br.ufms.facom.R;
import br.ufms.facom.adater.CustomListAdapter;
import br.ufms.facom.app.AppController;
import br.ufms.facom.model.Movie;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class MovieFragment extends Fragment implements OnItemClickListener
{
	private static String TAG = MovieFragment.class.getCanonicalName();
	
	// URL com os filmes
	private static final String url = "http://www.facom.ufms.br/~marcio/movies.json";
	private ProgressDialog pDialog;
	private List<Movie> movieList = new ArrayList<Movie>();
	private ListView listView;
	private CustomListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		Log.d(TAG, "onCreateView");
		
		View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
		listView = (ListView) rootView.findViewById(R.id.list);
		adapter = new CustomListAdapter(getActivity(), movieList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		pDialog = new ProgressDialog(getActivity());
		// mostrando uma progressbar
		pDialog.setMessage("Loading...");
		pDialog.show();
		
		// mudando a cor da actionbar
		// getActionBar().setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#1b1b1b")));
		
		// Criando um objeto Volley
		JsonArrayRequest movieReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>()
		{
			@Override
			public void onResponse(JSONArray response)
			{
				Log.d(TAG, response.toString());
				hidePDialog();
				
				// Parseando o JSON
				for (int i = 0; i < response.length(); i++)
				{
					try
					{
						
						JSONObject obj = response.getJSONObject(i);
						Movie movie = new Movie();
						movie.setTitle(obj.getString("title"));
						movie.setThumbnailUrl(obj.getString("image"));
						movie.setRating(((Number) obj.get("rating")).doubleValue());
						movie.setYear(obj.getInt("releaseYear"));
						
						// Os gêneros dos filmes estão em um Array
						JSONArray genreArry = obj.getJSONArray("genre");
						ArrayList<String> genre = new ArrayList<String>();
						for (int j = 0; j < genreArry.length(); j++)
						{
							genre.add((String) genreArry.get(j));
						}
						movie.setGenre(genre);
						
						// Adicionar o filme no Array de Filmes
						movieList.add(movie);
						
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
					
				}
				
				// Noficando o array adapter que os dados mudaram
				// assim ele exibirá a lista com os dados atualizados
				adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				hidePDialog();
				
			}
		});
		
		// Adicionando uma requisição à Fila
		AppController.getInstance().addToRequestQueue(movieReq);
		return rootView;
	}
	
	private void hidePDialog()
	{
		if (pDialog != null)
		{
			pDialog.dismiss();
			pDialog = null;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
	{
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("movie", movieList.get(position));
		
		startActivity(intent);
	}
}
