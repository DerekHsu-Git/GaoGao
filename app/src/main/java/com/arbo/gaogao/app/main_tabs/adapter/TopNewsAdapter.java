package com.arbo.gaogao.app.main_tabs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arbo.gaogao.Config;
import com.arbo.gaogao.R;
import com.arbo.gaogao.app.main_tabs.activity.TopNewsDetailActivity;
import com.arbo.gaogao.model.toutiao.NewsBean;
import com.arbo.gaogao.util.DBUtils;
import com.arbo.gaogao.util.DensityUtil;
import com.arbo.gaogao.widget.BadgedFourThreeImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by Administrator on 2017/2/1.
 */

public class TopNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IBaseAdapter {
    private static final int MORE_ITEM = -1;
    private static final int NORMAL_ITEM = 1;
    private ArrayList<NewsBean> newsItemList = new ArrayList<>();
    boolean showLoadingMore;
    float width;
    int widthPx;
    int heighPx;
    private Context mContext;


    public TopNewsAdapter(Context context) {
        this.mContext = context;
        width = mContext.getResources().getDimension(R.dimen.image_width);
        widthPx = DensityUtil.dip2px(mContext, width);
        heighPx = widthPx * 3 / 4;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case NORMAL_ITEM:
                return new NormalViewHolder(LayoutInflater.from(mContext).inflate(R.layout.topnews_layout_item,parent,false));
            case MORE_ITEM:
                return new LodingMoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.loadding_layout,parent,false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case NORMAL_ITEM:
                normalBindViewHolder((NormalViewHolder) holder,position);
                break;
            case MORE_ITEM:
                moreBindVIewHolder((LodingMoreViewHolder) holder);
                break;
        }
    }

    private void moreBindVIewHolder(LodingMoreViewHolder holder) {
        holder.progressBar.setVisibility(showLoadingMore?View.VISIBLE:View.INVISIBLE);
    }

    private void normalBindViewHolder(final NormalViewHolder holder, int position) {
        final NewsBean newsItem = newsItemList.get(holder.getAdapterPosition());
        //如果此条数据已经阅读过了，那么在加载这数据的时候，将标题设置为灰色，否则黑色(default)
        if(DBUtils.getDB(mContext).isRead(Config.TOPNEWS,newsItem.getDocid(),1)){
            holder.title.setTextColor(Color.GRAY);
            holder.time.setTextColor(Color.GRAY);
            holder.source.setTextColor(Color.GRAY);
        }else{
            holder.title.setTextColor(Color.BLACK);
            holder.time.setTextColor(Color.BLACK);
            holder.source.setTextColor(Color.BLACK);
        }
        holder.title.setText(newsItem.getTitle());
        holder.time.setText(newsItem.getTime());
        holder.source.setText(newsItem.getSource());

        Glide.with(mContext)
                .load(newsItem.getImgsrc())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(widthPx,heighPx)
                .into(holder.imageView);
        //设置图片的点击事件：打开详情页面
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDescribeActivity(holder,newsItem);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDescribeActivity(holder,newsItem);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if(position < getDataItemCount() && getDataItemCount()>0){
            return NORMAL_ITEM;
        }else{
            return MORE_ITEM;
        }
    }

    private int getDataItemCount() {
        return newsItemList==null?0:newsItemList.size();
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    @Override
    public void loadingStart() {
        if(showLoadingMore)return;
        showLoadingMore = true;
        notifyItemInserted(getLodingMoreItemPosition());    //在当前位置后面插入新的数据
    }

    private int getLodingMoreItemPosition() {
        return showLoadingMore?getDataItemCount()-1:NO_POSITION;
    }

    @Override
    public void loadingFinish() {
        if(!showLoadingMore)return;
        showLoadingMore = false;
        final int loadingPos = getLodingMoreItemPosition();//获取最后一个Item的位置
        notifyItemRemoved(loadingPos);
    }

    @Override
    public void clearList() {
        if(newsItemList!=null){
            newsItemList.clear();
            notifyDataSetChanged();
        }
    }

    public void addItem(ArrayList<NewsBean> list){
        newsItemList.addAll(list);
        notifyDataSetChanged();
    }


    public class NormalViewHolder extends RecyclerView.ViewHolder{

        final TextView title;
        final TextView time;
        final TextView source;
        final LinearLayout linearLayout;
        BadgedFourThreeImageView imageView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title_id);
            time = (TextView) itemView.findViewById(R.id.item_time_id);
            source = (TextView)itemView.findViewById(R.id.item_source_id);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.news_item_layout);
            imageView = (BadgedFourThreeImageView) itemView.findViewById(R.id.item_image_id);
        }
    }

    public class LodingMoreViewHolder extends RecyclerView.ViewHolder{
        final ProgressBar progressBar;
        public LodingMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView;
        }
    }

    /**
     * 打开详情页面
     * @param holder
     * @param newsBean
     */
    private void goDescribeActivity(TopNewsAdapter.NormalViewHolder holder, NewsBean newsBean){
        //该条目设为已读
        DBUtils.getDB(mContext).insertHasRead(Config.TOPNEWS, newsBean.getDocid(), 1);
        holder.title.setTextColor(Color.GRAY);
        holder.time.setTextColor(Color.GRAY);
        holder.source.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, TopNewsDetailActivity.class);
        //传递参数：文章id，标题，顶部图片链接
        intent.putExtra("id", newsBean.getDocid());
        intent.putExtra("title", newsBean.getTitle());
        intent.putExtra("image",newsBean.getImgsrc());
        mContext.startActivity(intent);
    }

}
