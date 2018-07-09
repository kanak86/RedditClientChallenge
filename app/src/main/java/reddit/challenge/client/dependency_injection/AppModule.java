package reddit.challenge.client.dependency_injection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import reddit.challenge.client.RedditClientApplication;

/**
 * Created by apoorvakanaksiwach on 2/8/18.
 */

@Module
public class AppModule {

    private final RedditClientApplication application;

    public AppModule(RedditClientApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication(){
        return application;
    }

}
