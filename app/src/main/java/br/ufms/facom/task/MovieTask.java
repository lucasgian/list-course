package br.ufms.facom.task;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import br.ufms.facom.Refreshable;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class MovieTask extends AsyncTask<String, Integer, String>
{
	private OkHttpClient client = new OkHttpClient();
	private static final String TAG = MovieTask.class.getCanonicalName();
	
	private Refreshable ref;
	
	public MovieTask(Refreshable ref)
	{
		this.ref = ref;
	}
	
	// UI Thread
	// Método executado na UI Thread antes das operações começarem
	@Override
	protected void onPreExecute()
	{
		Log.v(TAG, "onPreExecute");
	}
	
	// Out UI Thread
	@Override
	protected String doInBackground(String... url)
	{
		Log.v(TAG, "doInBackground");
		
		try
		{
			return doGetRequest(url[0]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		// atualiza o andamento das operações em background
		publishProgress(1);
		
		return null;
	}
	
	// UI Thread
	// Atualiza o progresso das operações.
	protected void onProgressUpdate(Integer... progress)
	{
		Log.v(TAG, "onProgressUpdate");
	}
	
	// UI Thread
	// Método executado na UI Thread após as operações em
	// background terminarem
	@Override
	protected void onPostExecute(String result)
	{
		Log.v(TAG, "onPostExecute");
		ref.refresh(result);
		try
		{
			Log.v(TAG, result);
			
			JSONObject Jobject = new JSONObject(result);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	private String doGetRequest(String url) throws IOException
	{
		
		Log.v(TAG, "doGetRequest");
		
		Request request = new Request.Builder().url(url).build();
		
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
