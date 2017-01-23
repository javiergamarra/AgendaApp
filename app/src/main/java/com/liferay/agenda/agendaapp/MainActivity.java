package com.liferay.agenda.agendaapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		final ListView listView = (ListView) findViewById(R.id.list);

		GitHubService service = retrofit.create(GitHubService.class);
		Call<List<Event>> call = service.listRepos("octocat");
		call.enqueue(new Callback() {
			@Override
			public void onResponse(Call call, Response response) {
				EventAdapter adapter =
					new EventAdapter(MainActivity.this, R.layout.event_row, (List<Event>) response.body());
				listView.setAdapter(adapter);
			}

			@Override
			public void onFailure(Call call, Throwable t) {

			}
		});
	}
}
