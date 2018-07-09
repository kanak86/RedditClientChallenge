package reddit.challenge.client.dependency_injection;

import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import reddit.challenge.client.RedditClientApplication;
import reddit.challenge.client.repositories.RedditListingRepository;
import reddit.challenge.client.retrofitmanager.RedditListingService;
import reddit.challenge.client.retrofitmanager.RetrofitClient;
import reddit.challenge.client.utils.Utils;
import reddit.challenge.client.viewmodel_factory.RedditListingViewModelFactory;

/**
 * Created by apoorvakanaksiwach on 2/8/18.
 */

@Module
public class RetrofitModule {

    private final RedditClientApplication application;

    public RetrofitModule(RedditClientApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    RedditListingService provideRetrofit() {
        return RetrofitClient.getClient(Utils.BASE_URL).create(RedditListingService.class);
    }

    @Provides
    @Singleton
    RedditListingRepository provideListItemRepository(RedditListingService redditListingService){
        return new RedditListingRepository(redditListingService);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(RedditListingRepository repository){
        return new RedditListingViewModelFactory(repository);
    }
}
