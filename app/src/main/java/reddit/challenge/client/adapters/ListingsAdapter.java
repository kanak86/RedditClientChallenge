package reddit.challenge.client.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import reddit.challenge.client.views.MainActivity;
import reddit.challenge.client.OnLoadMoreListener;
import reddit.challenge.client.R;
import reddit.challenge.client.datamanagers.Child;
import reddit.challenge.client.datamanagers.Data;
import reddit.challenge.client.datamanagers.TopListings;

/**
 * Created by apoorvakanaksiwach on 2/4/18.
 */

public class ListingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnLoadMoreListener {

    private Data data;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading = false;
    public static List<Child> childrenList = new ArrayList<>();

    public class ListingsCardViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;

        @BindView(R.id.thumbnail)
        public ImageView itemImage;
        @BindView(R.id.title)
        public TextView itemTitle;
        @BindView(R.id.time_and_author)
        public TextView timeAndAuthor;
        @BindView(R.id.comments_count)
        public TextView itemCommentsCount;
        @BindView(R.id.card_view)
        CardView cardView;

        public ListingsCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.thumbnail, R.id.card_view})
        public void clickListItem() {
            int position = getAdapterPosition();
            Child item = childrenList.get(position);
            String fullImageUrl = item.getData().getPreview().getImages().get(0).getSource().getUrl();
            ((MainActivity) context).showFullSizeImageDialog(item.getData().getTitle(), fullImageUrl);
            notifyDataSetChanged();
        }
    }

    public ListingsAdapter(Context context, RecyclerView recyclerView) {
        this.context = context;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) && data.getDist()%10 == 0) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore(data);
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_layout, parent, false);
            return new ListingsCardViewHolder(v);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ListingsCardViewHolder) {
            ListingsCardViewHolder listingsCardViewHolder = (ListingsCardViewHolder) holder;
            Child item = childrenList.get(position);
            listingsCardViewHolder.itemTitle.setText(item.getData().getTitle());
            listingsCardViewHolder.timeAndAuthor.setText("published by "+item.getData().getAuthor());
            Glide.with(context)
                    .load(item.getData().getThumbnail())
                    .into(listingsCardViewHolder.itemImage);
            listingsCardViewHolder.itemCommentsCount.setText(item.getData().getNumComments()+" comments!");
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(data != null) {
            count = childrenList.size();
        }
        return count;
    }

    private Child getItem(int adapterPosition) {
        return childrenList.get(adapterPosition);
    }

    public void updateListing(TopListings posts) {
        childrenList.addAll(posts.getData().getChildren());
        this.data = posts.getData();
        notifyDataSetChanged();
    }

    public void setLoaded() {
        isLoading = false;

    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.onLoadMoreListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return childrenList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onLoadMore(final Data data) {

        childrenList.add(null);
        notifyItemInserted(childrenList.size() - 1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(data.getDist()%10 == 0) {
                    childrenList.remove(childrenList.size() - 1);
                    notifyItemRemoved(childrenList.size());
                    ((MainActivity) context).getViewModel().setAfterParamForAPICall(data.getAfter());
                    ((MainActivity) context).getViewModel().setNumberOfItemsSeenSoFar(data.getDist());
                    ((MainActivity) context).getViewModel().getPosts();
                }
            }
        }, 2000);
    }

}
