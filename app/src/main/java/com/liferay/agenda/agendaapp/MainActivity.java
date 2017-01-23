package com.liferay.agenda.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import java.io.IOException;
import java.util.List;
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

		final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		new Thread(new Runnable() {
			@Override
			public void run() {
				GitHubService service = retrofit.create(GitHubService.class);
				Call<List<Event>> call = service.listRepos("octocat");
				try {
					Response<List<Event>> response = call.execute();
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
