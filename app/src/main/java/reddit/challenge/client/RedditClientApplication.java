package reddit.challenge.client;

import android.app.Application;

import reddit.challenge.client.dependency_injection.AppModule;
import reddit.challenge.client.dependency_injection.ApplicationComponent;
import reddit.challenge.client.dependency_injection.DaggerApplicationComponent;
import reddit.challenge.client.dependency_injection.RetrofitModule;

/**
 * Created by apoorvakanaksiwach on 2/8/18.
 */

public class RedditClientApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
