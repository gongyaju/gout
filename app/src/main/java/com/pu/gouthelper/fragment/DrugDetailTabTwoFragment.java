package com.pu.gouthelper.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.DrugDetailActivity;
import com.pu.gouthelper.adapter.Callback;
import com.pu.gouthelper.adapter.CommentAdapter;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.ActionItem;
import com.pu.gouthelper.bean.Comment;
import com.pu.gouthelper.bean.UserEntity;
import com.pu.gouthelper.common.CommentFun;
import com.pu.gouthelper.dialog.TitlePopup;
import com.pu.gouthelper.ui.MyListView;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.utils.Utils;
import com.pu.gouthelper.webservice.CommentAddRequest;
import com.pu.gouthelper.webservice.CommentListRequest;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;


public class DrugDetailTabTwoFragment extends BaseFragment implements Callback, CommentFun.CommentDialogListener {


    @ViewInject(R.id.purin_ls_detail)
    private ListView purin_ls_detail;
    private DrugDetailActivity mActivity;
    private List<Comment> mList = new ArrayList<>();
    private CommentAdapter adapter = null;
    private Context mContext;

    @ViewInject(R.id.rl_bottom)
    private RelativeLayout rl_bottom;
    @ViewInject(R.id.group_discuss)
    private EditText group_discuss;
    private DynamicBox box;

    private String tid = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CommentListRequest.SUCCESS:
                    mList.clear();
                    mList.addAll((List<Comment>) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
                case CommentListRequest.ERROR:
                    box.showCustomView("hint_no_message");
                    break;
                case CommentAddRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    mActivity.endLoading();
                    break;
                case CommentAddRequest.SUCCESS:
                    setData(tid);
                    group_discuss.setText("");
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    mActivity.endLoading();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_tab2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mActivity = (DrugDetailActivity) mContext;
        initView();

    }

    private void initView() {
        box = new DynamicBox(mContext, purin_ls_detail);
        View customView = getActivity().getLayoutInflater().inflate(R.layout.hint_no_message, null, false);
        box.addCustomView(customView, "hint_no_message");
        adapter = new CommentAdapter(mContext, mList, this, replyToReplyListener);
        purin_ls_detail.setAdapter(adapter);

        purin_ls_detail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                group_discuss.clearFocus();
                return false;
            }
        });
        group_discuss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mActivity.isShowLiner(hasFocus);
            }
        });
        group_discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                boolean isOpen = imm.isActive();
                mActivity.isShowLiner(isOpen);
            }
        });
    }

    /**
     * 互相回复的监听（楼中楼）
     */
    private View.OnClickListener replyToReplyListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                UserEntity userEntity = (UserEntity) v.getTag();
                CommentFun.inputComment(getActivity(), purin_ls_detail, v, userEntity, DrugDetailTabTwoFragment.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void setData(String id) {
        this.tid = id;
        new CommentListRequest(mHandler, "3", id, "" + F.PAGE_SIZE);
    }


    @Event(value = {R.id.group_discuss_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.group_discuss_submit:
                String content = group_discuss.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "先说点什么再发表吧~");
                    return;
                }
                if (StringUtils.isEmpty(tid)) {
                    UIHelper.ToastMessage(mContext, "出错啦！稍等一会再评论吧~");
                    return;
                }
                new CommentAddRequest(mHandler, "3", tid, "", "", content);
                mActivity.showLoading(mContext);
                break;

        }
    }

    @Override
    public void click(View v) {
//        UIHelper.ToastMessage(mContext, "评论~" + v.getTag());
//        titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
//        titlePopup.show(v);
//        String content = mList.get((int) v.getTag()).getContent();
//        group_discuss.requestFocus();
        try {
            Comment comment = mList.get(Integer.parseInt(v.getTag() + ""));
            comment.getUser().setCid(comment.getId());
            CommentFun.inputComment(getActivity(), purin_ls_detail, v, comment.getUser(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickPublish(Dialog dialog, EditText input, TextView btn, UserEntity userEntity) {
        final String content = input.getText().toString();
        if (content.trim().equals("")) {
            UIHelper.ToastMessage(mContext, "评论不能为空");
            return;
        } else {
            new CommentAddRequest(mHandler, "3", tid, userEntity.getCid(), userEntity.getId(), content);
        }
        dialog.dismiss();
    }

    @Override
    public void onShow(int[] inputViewCoordinatesOnScreen) {

    }

    @Override
    public void onDismiss() {

    }
}
