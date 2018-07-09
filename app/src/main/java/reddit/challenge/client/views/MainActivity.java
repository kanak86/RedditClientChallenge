package reddit.challenge.client.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import javax.inject.Inject;

import reddit.challenge.client.R;
import reddit.challenge.client.RedditClientApplication;
import reddit.challenge.client.adapters.ListingsAdapter;
import reddit.challenge.client.base.BaseActivity;
import reddit.challenge.client.datamanagers.TopListings;
import reddit.challenge.client.view_model.RedditListingViewModel;
import reddit.challenge.client.viewmodel_factory.RedditListingViewModelFactory;

public class MainActivity extends BaseActivity {

    @Inject
    RedditListingViewModelFactory viewModelFactory;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Parcelable currentViewState = null;
    private static final String RECYCLER_VIEW_INSTANCE = "recyclerViewState";
    private RedditListingViewModel viewModel;
    private ListingsAdapter adapter;

    private static final String MAIN_FRAG = "MAIN_FRAG";
    private static final String DIALOG_FRAG = "DIALOG_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*FragmentManager manager = getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) manager.findFragmentByTag(MAIN_FRAG);

        if(mainFragment == null) {
            mainFragment = MainFragment.newInstance();
        }

        addFragmentToActivity(manager, mainFragment, R.id.root_activity_frame, MAIN_FRAG);*/

        ((RedditClientApplication)getApplication()).getApplicationComponent().inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RedditListingViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListingsAdapter(this, recyclerView);
        recyclerView.setAdapter(adapter);

        adapter.setOnLoadMoreListener(adapter);
        viewModel.setAfterParamForAPICall("");
        viewModel.setNumberOfItemsSeenSoFar(0);
        viewModel.getPosts();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                observeViewModel();
            }
        }, 5000);
    }

    private void observeViewModel() {
        viewModel.getPosts().observe(this, new Observer<TopListings>() {
            @Override
            public void onChanged(@Nullable TopListings listings) {
                adapter.updateListing(listings);
                adapter.setLoaded();
            }
        });
    }

    public void showErrorMessage() {
        Toast.makeText(this, "Error loading listing", Toast.LENGTH_SHORT).show();
    }

    public void showFullSizeImageDialog(String title, String URL) {
        FragmentManager fm = getSupportFragmentManager();
        ImageDialogFragment editNameDialogFragment = ImageDialogFragment.newInstance(title, URL);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    public RedditListingViewModel getViewModel() {
        return viewModel;
    }
}
