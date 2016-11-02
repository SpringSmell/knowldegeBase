package com.yidingliu.dev.knowldegelibrary.b;

import android.view.View;

import com.yidingliu.dev.knowldegelibrary.b.adapter.RecyclerViewBaseParentAdapter;

import java.util.List;

/**
 * 基础适配器,继承便能快速生成adapter
 * 其他列表控件适配器可继承，但不建议继承，比如：listView、GridView
 * Created by chris Zou on 2016/6/12.
 *
 * @author chris Zou
 * @Date 2016/6/12
 */
public abstract class BaseRecyclerViewAdapter < M > extends RecyclerViewBaseParentAdapter {//<M extends BaseRecyclerViewAdapter.BaseBean>
    private List< M > dataList;

    @Override
    public int getItemCount () {

        return dataList == null ? 0 : dataList.size ();
    }

    @Override
    public void onBindViewHolder ( BaseViewHolder holder, final int position ) {

        if ( mOnItemClickListener != null ) {
            holder.itemView.setOnClickListener ( new View.OnClickListener () {

                @Override
                public void onClick ( View v ) {

                    mOnItemClickListener.onClick ( v, position );
                }

            } );
        }
        if ( mOnItemLongClickListener != null ) {
            holder.itemView.setOnLongClickListener ( new View.OnLongClickListener () {

                @Override
                public boolean onLongClick ( View v ) {


                    mOnItemLongClickListener.onClick ( v, position );

                    return true;
                }
            } );
        }
        onBindData ( holder, position, dataList.get ( position ) );
    }

    public abstract void onBindData ( BaseViewHolder holder, int position, M itemData );

    public void setDatas ( List< M > datas ) {

        if ( datas != null ) {
            this.dataList = datas;
            notifyDataSetChanged ();
        } else {
            throw new NullPointerException ( "The data is null,please check" );
        }
    }

    public List< M > getDatas () {

        return dataList;
    }

    public M getItem ( int position ) {

        return getDatas ().get ( position );
    }

}
