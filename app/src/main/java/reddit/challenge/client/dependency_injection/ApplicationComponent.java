package reddit.challenge.client.dependency_injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import reddit.challenge.client.repositories.RedditListingRepository;
import reddit.challenge.client.views.ImageDialogFragment;
import reddit.challenge.client.views.MainActivity;
import reddit.challenge.client.views.MainFragment;

/**
 * Created by apoorvakanaksiwach on 2/8/18.
 */

@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(MainFragment fragment);

    Application application();
}
