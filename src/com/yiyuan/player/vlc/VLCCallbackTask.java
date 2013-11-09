/*****************************************************************************
 * VLCCallbackTask.java
 *****************************************************************************
 * Copyright © 2012 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/
package com.yiyuan.player.vlc;

import com.yiyuan.player.ui.VideoLoadingActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * A small callback helper class to make running callbacks in threads easier
 */
public abstract class VLCCallbackTask extends AsyncTask<Void, Void, Void> {

    private Context context;
    
    private Intent mIntent;

    /**
     * Runs a callback in a background thread
     */
    public VLCCallbackTask() {
    }

    /**
     * Runs a callback in a background thread, and display a ProgressDialog until it's finished
     */
    public VLCCallbackTask(Context context) {
        this.context = context;
    }

    @Override
    /* Runs on the UI thread */
    protected void onPreExecute() {
    	Intent mIntent = new Intent(context, VideoLoadingActivity.class);
    	context.startActivity(mIntent);
        super.onPreExecute();
    }

    public abstract void run();

    @Override
    /* Runs on a background thread */
    protected Void doInBackground(Void... params) {
        run();
        return null;
    }

    @Override
    /* Runs on the UI thread */
    protected void onPostExecute(Void result) {
    	
        super.onPostExecute(result);
    }
}
