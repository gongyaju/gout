/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pu.gouthelper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.MainActivity;
import com.pu.gouthelper.base.SDCardUtils;
import com.pu.gouthelper.base.ScreenUtils;

import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by drakeet on 8/17/15.
 */
public class ShareUtils {

    public static void share(Context context, String extraText, String url, String filepath) {
        if (TextUtils.isEmpty(url)) {
            url = "http://www.tfzs999.com/wap.html?from=timeline&isappinstalled=1";
        }
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("痛风助手-" + extraText);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("痛风助手-" + extraText);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(filepath);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("痛风助手-" + extraText);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("痛风助手-" + extraText);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        oks.show(context);
    }

}
