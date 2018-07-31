package com.example.ridko.warehousepda.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.ridko.warehousepda.anim.AlphaInAnimation;
import com.example.ridko.warehousepda.anim.BaseAnimation;
import com.example.ridko.warehousepda.anim.CustomAnimation;
import com.example.ridko.warehousepda.anim.ScaleInAnimation;
import com.example.ridko.warehousepda.anim.SlideInBottomAnimation;
import com.example.ridko.warehousepda.anim.SlideInLeftAnimation;
import com.example.ridko.warehousepda.anim.SlideInRightAnimation;
import com.example.ridko.warehousepda.second.development.RecyclerHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mumu on 2018/6/5.
 */

public abstract class BRecyclerAdapter<T>
        extends RecyclerView.Adapter<RecyclerHolder> implements View.OnClickListener{

    private boolean mOpenAnimationEnable = false;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;
    private boolean mFirstOnlyEnable = true;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int ALPHAIN = 0x00000001;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SCALEIN = 0x00000002;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_LEFT = 0x00000004;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_RIGHT = 0x00000005;

    public static final int SLIDEIN_CUSTOM = 0x00000006;

    protected List<T> realDatas;
    protected final int mItemLayoutId;
    protected Context cxt;
    private OnItemClickListener listener;

    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT, SLIDEIN_CUSTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public BRecyclerAdapter(RecyclerView v, Collection<T> datas, int itemLayoutId) {
        if (datas == null) {
            realDatas = new ArrayList<>();
        } else if (datas instanceof List) {
            realDatas = (List<T>) datas;
        } else {
            realDatas = new ArrayList<>(datas);
        }
        mItemLayoutId = itemLayoutId;
        cxt = v.getContext();
    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder viewholder
     * @param item   javabean
     */
    public abstract void convert(RecyclerHolder holder, T item, int position);

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cxt);
        View root = inflater.inflate(mItemLayoutId, parent, false);
        root.setOnClickListener(this);
        return new RecyclerHolder(root);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                convert(holder, realDatas.get(holder.getLayoutPosition()
                        - 0), position);
                addAnimation(holder);
                holder.itemView.setOnClickListener(getOnClickListener(position));
                break;
            default:
                convert(holder, realDatas.get(position), position);
                holder.itemView.setOnClickListener(getOnClickListener(position));
                break;
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public int getItemCount() {
        return realDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, realDatas.get(position), position);
                }
            }
        };
    }
//自定义onTouch事件
   /* private OnItemTouchListener onItemTouchListener;

    public void setOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.onItemTouchListener = onItemTouchListener;
    }

    public interface OnItemTouchListener {
        void onTouchClick(View view, int position);
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }*/

    public BRecyclerAdapter<T> refresh(Collection<T> datas) {
        if (realDatas != null && !realDatas.isEmpty()) {
            //去重
            for (T data : datas) {
                if (!realDatas.contains(data)) realDatas.add(data);
            }
        } else {
            if (datas == null) {
                return this;
            } else if (datas instanceof List) {
                realDatas = (List<T>) datas;
            } else {
                realDatas = new ArrayList<>(datas);
            }
        }
        notifyDataSetChanged();
        return this;
    }

    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * Set the view animation type.
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM}, {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            case SLIDEIN_CUSTOM:
                mSelectAnimation = new CustomAnimation();
                break;
            default:
                break;
        }
    }
}
