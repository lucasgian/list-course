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
import br.ufms.facom.CourseDetailActivity;
import br.ufms.facom.R;
import br.ufms.facom.adater.CourseListAdapter;
import br.ufms.facom.app.AppController;
import br.ufms.facom.model.Course;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

public class CourseFragment extends Fragment implements OnItemClickListener
{
    private static String TAG = CourseFragment.class.getCanonicalName();

    // URL com os filmes
    private static final String url = "http://gradweb.facom.ufms.br/~201519060378/Mobile/cursos.json";
    private ProgressDialog pDialog;
    private List<Course> movieList = new ArrayList<Course>();
    private ListView listView;
    private CourseListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        adapter = new CourseListAdapter(getActivity(), movieList);
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
                        Course movie = new Course();
                        movie.setName(obj.getString("nome"));
                        movie.setThumbnailUrl(obj.getString("image"));
                        movie.setCode(( obj.getString("codigo")));
                        movie.setShift(obj.getString("turno"));
                        movie.setCenter(obj.getString("centro"));

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
        Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
        intent.putExtra("course", movieList.get(position));

        startActivity(intent);
    }
}
