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
import com.arbo.gaogao.app.main_tabs.activity.ZhihuStoryActivity;
import com.arbo.gaogao.model.zhihu.ZhihuDailyItem;
import com.arbo.gaogao.util.DBUtils;
import com.arbo.gaogao.util.DensityUtil;
import com.arbo.gaogao.widget.BadgedFourThreeImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.NO_POSITION;


/**
 * Created by Administrator on 2017/1/29.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IBaseAdapter{
    private static final int MORE_ITEM = -1;
    private static final int NORMAL_ITEM = 1;
    private ArrayList<ZhihuDailyItem> zhihuDailyItems = new ArrayList<>();
    boolean showLoadingMore;
    float width;
    int widthPx;
    int heighPx;
    private Context mContext;

    public ZhihuAdapter(Context context) {
        this.mContext = context;
        width = mContext.getResources().getDimension(R.dimen.image_width);
        widthPx = DensityUtil.dip2px(mContext, width);
        heighPx = widthPx * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case NORMAL_ITEM:
                return new ZhihuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item,parent,false));
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
                normalBindViewHolder((ZhihuViewHolder) holder,position);
                break;
            case MORE_ITEM:
                moreBindVIewHolder((LodingMoreViewHolder) holder);
                break;
        }
    }



    private void normalBindViewHolder(final ZhihuViewHolder holder, final int position){
        final ZhihuDailyItem zhihuDailyItem = zhihuDailyItems.get(holder.getAdapterPosition());

        //如果此条数据已经阅读过了，那么在加载这数据的时候，将标题设置为灰色，否则黑色(default)
        if(DBUtils.getDB(mContext).isRead(Config.ZHIHU,zhihuDailyItem.getId(),1)){
            holder.textView.setTextColor(Color.GRAY);
        }else{
            holder.textView.setTextColor(Color.BLACK);
        }
        holder.textView.setText(zhihuDailyItem.getTitle());
        Glide.with(mContext)
                .load(zhihuDailyItem.getImages()[0])
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop().override(widthPx,heighPx)
                .into(holder.imageView);
        //设置图片的点击事件：打开详情页面
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDescribeActivity(holder,zhihuDailyItem);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDescribeActivity(holder,zhihuDailyItem);
            }
        });

    }


    private void moreBindVIewHolder(LodingMoreViewHolder holder ){
        holder.progressBar.setVisibility(showLoadingMore?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public int getItemViewType(int position) {
        if(position < getDataItemCount() && getDataItemCount()>0){
            return NORMAL_ITEM;
        }else{
            return MORE_ITEM;
        }
    }

    private int getLodingMoreItemPosition(){
        return showLoadingMore?getDataItemCount()-1:NO_POSITION;
    }


    private int getDataItemCount() {
        if(zhihuDailyItems!=null){
            return zhihuDailyItems.size();
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return zhihuDailyItems.size();
    }

    /**
     * 开始加载
     */
    @Override
    public void loadingStart() {
        if(showLoadingMore)return;
        showLoadingMore = true;
        notifyItemInserted(getLodingMoreItemPosition());    //在当前位置后面插入新的数据
    }

    /**
     * 加载结束
     */
    @Override
    public void loadingFinish() {
        if(!showLoadingMore)return;
        showLoadingMore = false;
        final int loadingPos = getLodingMoreItemPosition();//获取最后一个Item的位置
        notifyItemRemoved(loadingPos);
    }

    /**
     * 清除列表
     */
    @Override
    public void clearList() {
        if(zhihuDailyItems!=null){
            zhihuDailyItems.clear();
            notifyDataSetChanged();
        }
    }

    public void addItems(ArrayList<ZhihuDailyItem> list) {
        zhihuDailyItems.addAll(list);
        notifyDataSetChanged();
    }

    class ZhihuViewHolder extends RecyclerView.ViewHolder{

        final TextView textView;
        final LinearLayout linearLayout;
        BadgedFourThreeImageView imageView;

        public ZhihuViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text_id);
            imageView = (BadgedFourThreeImageView) itemView.findViewById(R.id.item_image_id);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.zhihu_item_layout);
        }
    }

    class LodingMoreViewHolder extends RecyclerView.ViewHolder {
        final ProgressBar progressBar;
        public LodingMoreViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    /**
     * 打开详情页面
     * @param holder
     * @param zhihuDailyItem
     */
    private void goDescribeActivity(ZhihuViewHolder holder,ZhihuDailyItem zhihuDailyItem){
        //该条目设为已读
        DBUtils.getDB(mContext).insertHasRead(Config.ZHIHU, zhihuDailyItem.getId(), 1);
        holder.textView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, ZhihuStoryActivity.class);
        //传递参数：文章id，标题，顶部图片链接
        intent.putExtra("id", zhihuDailyItem.getId());
        intent.putExtra("title", zhihuDailyItem.getTitle());
        mContext.startActivity(intent);
    }

}
