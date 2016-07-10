package com.pu.gouthelper.common;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;


public class AppContext extends Application {

    private static AppContext app;

    public AppContext() {
        app = this;
    }

    private DbManager.DaoConfig daoConfig;

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        daoConfig = new DbManager.DaoConfig()
                .setDbName("gout_db")//创建数据库的名称
                .setDbVersion(1)//数据库版本号
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });//数据库更新操作
    }




}