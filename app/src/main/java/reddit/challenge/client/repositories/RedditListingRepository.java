package reddit.challenge.client.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import reddit.challenge.client.datamanagers.TopListings;
import reddit.challenge.client.retrofitmanager.RedditListingService;

/**
 * Created by apoorvakanaksiwach on 2/6/18.
 */

public class RedditListingRepository {

    RedditListingService serviceInterface;

    int counter = 0;
    private MutableLiveData<TopListings> posts = new MutableLiveData<>();

    @Inject
    public RedditListingRepository(RedditListingService redditListingService) {
        this.serviceInterface = redditListingService;
    }

    public LiveData<TopListings> fetchListing(String afterParamForAPI, int numberOfItemsSeen) {

        Map<String, String> params = new HashMap<>();
        params.put("limit", "10");
        if(afterParamForAPI != null && !afterParamForAPI.isEmpty()) {
            params.put("after", afterParamForAPI);
        }
        if(numberOfItemsSeen != 0) {
            counter = counter + numberOfItemsSeen;
            params.put("count", ""+counter);
        }
        serviceInterface.getTopWithLimit(params).enqueue(new Callback<TopListings>() {
            @Override
            public void onResponse(Call<TopListings> call, Response<TopListings> response) {

                if(response.isSuccessful()) {
                    posts.setValue(response.body());
                    //adapter.updateListing(response.body());
                }else {
                    int statusCode  = response.code();
                }
            }

            @Override
            public void onFailure(Call<TopListings> call, Throwable t) {
                //showErrorMessage();
                Log.d("MainActivity", "error loading from API");

            }
        });
        return posts;
    }
}
