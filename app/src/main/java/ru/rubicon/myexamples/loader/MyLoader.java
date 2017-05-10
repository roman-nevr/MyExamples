package ru.rubicon.myexamples.loader;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;


public class MyLoader extends CursorLoader {
    public MyLoader(Context context) {
        super(context);
    }

    public MyLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }


}
