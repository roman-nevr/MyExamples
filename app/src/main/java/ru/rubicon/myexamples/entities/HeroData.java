package ru.rubicon.myexamples.entities;

/**
 * Created by AwesomeDevelop on 30.01.2015.
 */
public class HeroData {
    String name;
    String image;
    String universe;


    public HeroData(String name, String universe, String image) {
        this.name = name;
        this.universe = universe;
        this.image = image;
    }


    public String getName() {return name;}
    public String getUniverse(){return universe;}
    public String getImage() {return image;}

}