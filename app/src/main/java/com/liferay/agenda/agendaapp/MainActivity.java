package com.liferay.agenda.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ListView;
import java.io.IOException;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
		//	.addConverterFactory(GsonConverterFactory.create())
		//	.build();

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {

				String authorizationString =
					"Basic " + Base64.encodeToString(("test@liferay.com:test").getBytes(), Base64.NO_WRAP);
				Request request = chain.request()
					.newBuilder()
					.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.addHeader("Authorization", authorizationString)
					.build();
				return chain.proceed(request);
			}
		});

		final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://web.liferay.com/skinny-web/")
			.client(builder.build())
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				GitHubService service = retrofit.create(GitHubService.class);
				Call<ResponseBody> call = service.events("ddlRecordSetId=70383795");
				try {
					Response<ResponseBody> response = call.execute();
					EventBus.getDefault().post(response);
				} catch (IOException e) {
					Log.e("Agenda", "Don't do this");
				}
			}
		}).start();
	}

	@Override
	public void onStart() {
		super.onStart();
		EventBus.getDefault().register(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessageEvent(Response<List<Event>> response) {
		final ListView listView = (ListView) findViewById(R.id.list);
		EventAdapter adapter = new EventAdapter(MainActivity.this, R.layout.event_row, response.body());
		listView.setAdapter(adapter);
	}
}
