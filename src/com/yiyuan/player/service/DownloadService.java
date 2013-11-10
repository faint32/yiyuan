
package com.yiyuan.player.service;

import com.yiyuan.player.engine.DownloadManager;
import com.yiyuan.player.engine.MyIntents;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

public class DownloadService extends Service {

    private DownloadManager mDownloadManager;
    private String savePath;

    @Override
    public IBinder onBind(Intent intent) {

        return new DownloadServiceImpl();
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        // if (mDownloadManager == null) {
        // mDownloadManager = new DownloadManager(this);
        // }
    	savePath = intent.getStringExtra("savepath");
        if (intent.getAction().equals("com.yiyuan.player.service.IDownloadService")) {
        	mDownloadManager = new DownloadManager(this, savePath);
            int type = intent.getIntExtra(MyIntents.TYPE, -1);
            String url;

            switch (type) {
                case MyIntents.Types.START:
                    if (!mDownloadManager.isRunning()) {
                        mDownloadManager.startManage();
                    } else {
                        mDownloadManager.reBroadcastAddAllTask();
                    }
                    break;
                case MyIntents.Types.ADD:
                    url = intent.getStringExtra(MyIntents.URL);
                    if (!TextUtils.isEmpty(url) && !mDownloadManager.hasTask(url)) {
                        mDownloadManager.addTask(url, savePath);
                    }
                    break;
                case MyIntents.Types.CONTINUE:
                    url = intent.getStringExtra(MyIntents.URL);
                    if (!TextUtils.isEmpty(url)) {
                        mDownloadManager.continueTask(url);
                    }
                    break;
                case MyIntents.Types.DELETE:
                    url = intent.getStringExtra(MyIntents.URL);
                    if (!TextUtils.isEmpty(url)) {
                        mDownloadManager.deleteTask(url);
                    }
                    break;
                case MyIntents.Types.PAUSE:
                    url = intent.getStringExtra(MyIntents.URL);
                    if (!TextUtils.isEmpty(url)) {
                        mDownloadManager.pauseTask(url);
                    }
                    break;
                case MyIntents.Types.STOP:
                    mDownloadManager.close();
                    // mDownloadManager = null;
                    break;

                default:
                    break;
            }
        }

    }

    private class DownloadServiceImpl extends IDownloadService.Stub {

        @Override
        public void startManage() throws RemoteException {

            mDownloadManager.startManage();
        }

        @Override
        public void addTask(String url) throws RemoteException {

            mDownloadManager.addTask(url, savePath);
        }

        @Override
        public void pauseTask(String url) throws RemoteException {

        }

        @Override
        public void deleteTask(String url) throws RemoteException {

        }

        @Override
        public void continueTask(String url) throws RemoteException {

        }

    }

}
