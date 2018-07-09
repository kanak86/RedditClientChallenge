package reddit.challenge.client.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import reddit.challenge.client.datamanagers.Child;
import reddit.challenge.client.datamanagers.Data;
import reddit.challenge.client.datamanagers.TopListings;
import reddit.challenge.client.repositories.RedditListingRepository;

/**
 * Created by apoorvakanaksiwach on 2/6/18.
 */

public class RedditListingViewModel extends ViewModel {

    private LiveData<TopListings> posts;
    private MutableLiveData<Data> data;
    private String afterParamForAPICall;
    private int numberOfItemsSeenSoFar;

    private String afterParamForAPI;
    private int numberOfItemsSeen;

    private RedditListingRepository repository;

    public RedditListingViewModel(RedditListingRepository repository) {
        this.repository = repository;
    }

    public LiveData<TopListings> getPosts() {
        return repository.fetchListing(afterParamForAPI, numberOfItemsSeen);
    }

    public LiveData<Data> getData() {
        return data;
    }

    public void setAfterParamForAPICall(String afterParamForAPICall) {
        this.afterParamForAPICall = afterParamForAPICall;
    }

    public void setNumberOfItemsSeenSoFar(int numberOfItemsSeenSoFar) {
        this.numberOfItemsSeenSoFar = numberOfItemsSeenSoFar;
    }


}
