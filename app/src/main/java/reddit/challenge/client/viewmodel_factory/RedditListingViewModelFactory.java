package reddit.challenge.client.viewmodel_factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import reddit.challenge.client.repositories.RedditListingRepository;
import reddit.challenge.client.view_model.RedditListingViewModel;

/**
 * Created by apoorvakanaksiwach on 2/6/18.
 */

public class RedditListingViewModelFactory implements ViewModelProvider.Factory {


    private final RedditListingRepository repository;

    @Inject
    public RedditListingViewModelFactory(RedditListingRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RedditListingViewModel.class))
            return (T) new RedditListingViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}
