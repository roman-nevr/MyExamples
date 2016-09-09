package ru.rubicon.myexamples;

/**
 * Created by Витя on 08.09.2016.
 */
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import ru.rubicon.myexamples.adapters.HeroAdapter;
import ru.rubicon.myexamples.entities.HeroData;


public class Hero extends AppCompatActivity {
    private static ArrayList<HeroData> heroes;
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_main);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fetchHeroes();
        adapter = new HeroAdapter(Hero.this,heroes);
        recyclerView.setAdapter(adapter);

    }

    public void fetchHeroes(){
        heroes = new ArrayList<HeroData>();

        heroes.add(new HeroData("Зелёный Фонарь","DC comics","greenlantern"));
        heroes.add(new HeroData("Джокер","DC comics","joker"));
        heroes.add(new HeroData("Джона Хекс","DC comics","jonah-hex"));
        heroes.add(new HeroData("Папа Миднайт","DC comics","glav"));
        heroes.add(new HeroData("Ворона","DC comics","raven"));
        heroes.add(new HeroData("Чёрная Вдова","Marvel","glavnaya"));
        heroes.add(new HeroData("Капитан Америка","Marvel","cap_america"));
        heroes.add(new HeroData("Космический Халк","Marvel","cosmic_hulk"));
        heroes.add(new HeroData("Призрачный Гонщик","Marvel","ghost_rider"));


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hero_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
