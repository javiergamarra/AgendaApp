package com.liferay.agenda.agendaapp;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GitHubService {
	@GET("users/{user}/repos")
	Call<List<Event>> listRepos(@Path("user") String user);

	@POST("api/jsonws/skinny/get-skinny-ddl-records/")
	Call<ResponseBody> events(@Body String ddlRecordSetId);
}