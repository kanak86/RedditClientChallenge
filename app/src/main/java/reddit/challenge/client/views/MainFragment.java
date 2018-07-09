package reddit.challenge.client.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by apoorvakanaksiwach on 2/8/18.
 */

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
