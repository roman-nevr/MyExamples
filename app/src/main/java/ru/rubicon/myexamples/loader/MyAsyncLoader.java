package ru.rubicon.myexamples.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import static ru.rubicon.myexamples.LoaderExample.VALUE;


public class MyAsyncLoader extends AsyncTaskLoader<String> {

    String arg;
    String result;

    public MyAsyncLoader(Context context, Bundle args) {
        super(context);
        arg = args.getString(VALUE);
    }

    @Override public String loadInBackground() {

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deliverResult("" + i);
        }
        result = arg + " loaded";
        return result;

    }

    @Override public void deliverResult(String data) {
        super.deliverResult(data);
    }

    @Override protected String onLoadInBackground() {
        return super.onLoadInBackground();
    }

    @Override protected void onStartLoading() {
        if(result != null){
            deliverResult(result);
        }else {
            forceLoad();
        }
    }
}
