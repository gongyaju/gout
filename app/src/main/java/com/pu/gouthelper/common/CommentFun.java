package com.pu.gouthelper.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.bean.UserEntity;
import com.pu.gouthelper.ui.MyListView;


/**
 * 评论相关方法
 */
public class CommentFun {

    public static void inputComment(final Activity activity, final MyListView listView,
                                    final View btnComment, final UserEntity userEntity,
                                    final CommentDialogListener listener) {

        String hint = "";
        if (userEntity != null && userEntity.getId().equals(SharedPreferences.getInstance().getString("userid", ""))) {
            hint = "我也说一句";
        } else {
            hint = "回复 " + userEntity.getNickname();
        }

        // 获取评论的位置,不要在CommentDialogListener.onShow()中获取，onShow在输入法弹出后才调用，
        // 此时btnComment所属的父布局可能已经被ListView回收
        final int[] coord = new int[2];
        if (listView != null) {
            btnComment.getLocationOnScreen(coord);
        }
        showInputComment(activity, hint, listener, userEntity);

    }

    public static void inputComment(final Activity activity, final ListView listView,
                                    final View btnComment, final UserEntity userEntity,
                                    final CommentDialogListener listener) {

        String hint = "";
        if (userEntity != null && userEntity.getId().equals(SharedPreferences.getInstance().getString("userid", ""))) {
            hint = "我也说一句";
        } else {
            hint = "回复 " + userEntity.getNickname();
        }

        // 获取评论的位置,不要在CommentDialogListener.onShow()中获取，onShow在输入法弹出后才调用，
        // 此时btnComment所属的父布局可能已经被ListView回收
        final int[] coord = new int[2];
        if (listView != null) {
            btnComment.getLocationOnScreen(coord);
        }
        showInputComment(activity, hint, listener, userEntity);

    }

    private static Dialog showInputComment(Activity activity, CharSequence hint, final CommentDialogListener listener, final UserEntity userEntity) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.view_input_comment);
        dialog.findViewById(R.id.input_comment_dialog_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        final EditText input = (EditText) dialog.findViewById(R.id.input_comment);
        input.setHint(hint);
        final TextView btn = (TextView) dialog.findViewById(R.id.btn_publish_comment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickPublish(dialog, input, btn, userEntity);
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    int[] coord = new int[2];
                    dialog.findViewById(R.id.input_comment_container).getLocationOnScreen(coord);
                    listener.onShow(coord);
                }
            }
        }, 300);
        return dialog;
    }

    public interface CommentDialogListener {
        void onClickPublish(Dialog dialog, EditText input, TextView btn, UserEntity userEntity);

        void onShow(int[] inputViewCoordinatesOnScreen);

        void onDismiss();
    }

}
