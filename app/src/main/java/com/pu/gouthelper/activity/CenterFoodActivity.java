package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.RecipeListAdapter;
import com.pu.gouthelper.bean.Recipe;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshBase;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshListView;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.GoutRecipeListRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 碱性食谱
 */
@ContentView(R.layout.activity_center_food)
public class CenterFoodActivity extends SwipeBackActivity {

    @ViewInject(R.id.food_ls_show)
    PullToRefreshListView listView;
    private Context mContext;
    private RecipeListAdapter adapter = null;
    private List<Recipe> mList = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutRecipeListRequest.SUCCESS:
                    List<Recipe> recipes = (List<Recipe>) msg.obj;
                    mList.clear();
                    mList.addAll(recipes);
                    adapter.notifyDataSetChanged();
                    break;
                case GoutRecipeListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
            }
            listView.onRefreshComplete();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        loadData();
    }

    private void initView() {
        adapter = new RecipeListAdapter(mContext, mList);
        listView.setAdapter(adapter);
        // 下拉刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        // 加载更多
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                loadData();
            }
        });
        // 点击事件
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Recipe recipe = mList.get(i - 1);
                Intent it = new Intent(mContext, RecipeDetailActivity.class);
                it.putExtra("id", recipe.getId());
                startActivity(it);

            }
        });
    }

    private void loadData() {
        new GoutRecipeListRequest(mHandler, "10");
    }


}
