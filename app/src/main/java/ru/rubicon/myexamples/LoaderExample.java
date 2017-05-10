package ru.rubicon.myexamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import ru.rubicon.myexamples.loader.MyAsyncLoader;


public class LoaderExample extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public static final int LOADER_ID = 42;
    public static final String VALUE = "value";
    private TextView loaderText;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_loader);
        loaderText = (TextView) findViewById(R.id.loader_text);

        Bundle bundle = new Bundle();
        bundle.putString(VALUE, "test");

        getSupportLoaderManager().initLoader(LOADER_ID, bundle, this);
    }

    @Override public Loader<String> onCreateLoader(int id, Bundle args) {
        // Создаем новый CursorLoader с нужными параметрами
        MyAsyncLoader mLoader = null;
        // условие можно убрать, если вы используете только один загрузчик
        if (id == LOADER_ID) {
            mLoader = new MyAsyncLoader(this, args);
            Log.d("myTag", "onCreateLoader");
            mLoader.startLoading();
        }
        return mLoader;
    }

    @Override public void onLoadFinished(Loader<String> loader, final String data) {
        int a = 0;
        if(loader.getId() == LOADER_ID){
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    loaderText.setText(data);
                }
            });
        }
    }

    @Override public void onLoaderReset(Loader<String> loader) {
        int a = 0;
    }
}
