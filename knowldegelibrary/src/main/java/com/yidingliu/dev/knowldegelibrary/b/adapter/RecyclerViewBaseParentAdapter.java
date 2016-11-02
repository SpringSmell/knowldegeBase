package com.yidingliu.dev.knowldegelibrary.b.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yidingliu.dev.knowldegelibrary.b.BaseRecyclerViewAdapter;
import com.yidingliu.dev.knowldegelibrary.tools.ImageUtils;


/**
 * Created by Administrator on 2016/7/7.
 */

public abstract class RecyclerViewBaseParentAdapter extends RecyclerView.Adapter< RecyclerViewBaseParentAdapter.BaseViewHolder > {

    public OnItemClickListener mOnItemClickListener;
    public OnItemLongClickListener mOnItemLongClickListener;
    private Context mContext;

    @Override
    public RecyclerViewBaseParentAdapter.BaseViewHolder onCreateViewHolder ( ViewGroup parent, int viewType ) {

        mContext=parent.getContext ();
        View view = LayoutInflater.from ( parent.getContext () )
                                  .inflate ( resultLayoutResId (), parent, false );
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, 0);
                }
            }
        });
        BaseViewHolder viewHolder = new BaseViewHolder ( view );
        return viewHolder;
    }

    public Context getContext(){
        return mContext;
    }
    /**
     * 布局文件
     *
     * @return
     */
    public abstract int resultLayoutResId ();

    public void setOnItemClickListener ( BaseRecyclerViewAdapter.OnItemClickListener onItemClickListener ) {

        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener ( BaseRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener ) {

        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray< View > mViews;
        public View rootView;

        public BaseViewHolder ( View rootView ) {

            super ( rootView );
            this.rootView = rootView;
            this.mViews = new SparseArray<> ();
        }

        public < T extends View > T getView ( int id ) {

            View view = mViews.get ( id );
            if ( view == null ) {
                view = itemView.findViewById ( id );
                mViews.put ( id, view );
            }
            return ( T ) view;
        }

        public BaseViewHolder setText ( int id, CharSequence content ) {

            return setText ( id, content, null );
        }

        public BaseViewHolder setText ( int id, CharSequence content, View.OnClickListener onClickListener ) {

            TextView textView = getView ( id );
            textView.setText ( content );
            if(onClickListener!=null){
                textView.setOnClickListener ( onClickListener );
            }

            return this;
        }

        public BaseViewHolder setImgRes ( int id, int iconId ) {

            return setImg ( id, "", iconId, null );
        }

        public BaseViewHolder setImgRes ( int id, int iconId, View.OnClickListener onClickListener ) {

            return setImg ( id, "", iconId, onClickListener );
        }

        public BaseViewHolder setImgUrl ( int id, CharSequence url ) {

            return setImg ( id, url, 0, null );
        }

        public BaseViewHolder setImgUrl ( int id, CharSequence url, View.OnClickListener onClickListener ) {

            return setImg ( id, url, 0, onClickListener );
        }

        public BaseViewHolder setOnClickListener ( int id, View.OnClickListener onClickListener ) {

            getView ( id ).setOnClickListener ( onClickListener );
            return this;
        }

        public BaseViewHolder setBackgroundResource ( int id, int bgResId ) {

            getView ( id ).setBackgroundResource ( bgResId );
            return this;
        }

        @TargetApi ( Build.VERSION_CODES.JELLY_BEAN )
        public BaseViewHolder setBackground ( int id, Drawable background ) {

            getView ( id ).setBackground ( background );
            return this;
        }

        private BaseViewHolder setImg ( int id, CharSequence url, int iconId, View.OnClickListener onClickListener ) {

            ImageView img = getView ( id );
            if ( !TextUtils.isEmpty ( url ) ) {
                ImageUtils.displayImg ( img, url + "" );
            }
            if ( iconId != 0 ) {
                img.setImageResource ( iconId );
            }
            if(onClickListener!=null) {
                img.setOnClickListener ( onClickListener );
            }
            return this;
        }
    }

    public interface OnItemClickListener {

        void onClick ( View v, int position );
    }

    public interface OnItemLongClickListener {

        void onClick ( View v, int position );
    }
}
