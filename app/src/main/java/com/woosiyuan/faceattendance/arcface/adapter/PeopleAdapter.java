package com.woosiyuan.faceattendance.arcface.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.ViewGroup;
import com.woosiyuan.faceattendance.R;
import com.woosiyuan.faceattendance.BR;
import com.woosiyuan.faceattendance.arcface.main.modelview.RegisterVM;
import com.woosiyuan.faceattendance.arcface.util.FaceRegist;
import com.woosiyuan.faceattendance.basis.BaseAdapter;
import com.woosiyuan.faceattendance.basis.BaseViewHolder;
import com.woosiyuan.faceattendance.basis.entity.FaceMessage;
import com.woosiyuan.faceattendance.databinding.ItemManageBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 *
 * @version V1.0
 * @FileName: com.woosiyuan.faceattendance.arcface.adapter.PeopleAdapter.java
 * @author: so
 * @date: 2018-01-03 11:17
 */

public class PeopleAdapter  extends BaseAdapter<FaceMessage, BaseViewHolder> {

    private List<FaceMessage> mList;
    private Context           mContext;
    private ViewDataBinding   dataBinding;
    private List<FaceRegist>  mFaceRegists;
    private RegisterVM        mRegisterMV;
    private ItemManageBinding mItemManageBinding;
    private boolean           showDelete;

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
        dataBinding.invalidateAll();
        notifyDataSetChanged();
    }

    public PeopleAdapter(Context context) {
        super(context);
        mContext = context;
        mList = new ArrayList<>();
        mRegisterMV=new RegisterVM(context);
        mList=mRegisterMV.getAllFaceMessage();
    }


    public void deleteOnclick(final FaceMessage mUser, final int position){
        new AlertDialog.Builder(mContext)
                .setTitle("确认删除吗")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("deleteOnclick","deleteOnclick"+position);
                        mList.remove(position);
                        mRegisterMV.deleteFace(mUser.getMName());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_manage, parent,
                false);
        mItemManageBinding=DataBindingUtil.getBinding(dataBinding.getRoot());
        mItemManageBinding.setAdapter(this);
        return new BaseViewHolder(dataBinding);
    }

    @Override public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        dataBinding = baseViewHolder.getBinding();
        dataBinding.setVariable(BR.bean, mList.get(position));
        dataBinding.setVariable(BR.position,position);
        dataBinding.setVariable(BR.adapter,this);
        dataBinding.executePendingBindings(); //防止闪烁
    }

    @Override public int getItemCount() {
        return mList.size();
    }


}
