package com.woosiyuan.faceattendance.basis;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class BaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    /**
     * ViewDataBinding
     */
    private B mBinding;

  
    public BaseViewHolder(B binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    /**
     * @return viewDataBinding
     */
    public B getBinding() {
        return mBinding;
    }

}
