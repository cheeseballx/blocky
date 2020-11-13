package de.cheeseball.blocky;

import de.cheeseball.blocky.game_engine.Render;
import de.cheeseball.blocky.mass.Block;
import de.cheeseball.blocky.mass.blocks.stones.Gravel;

public class App {
    public static void main(String[] args) {

        Block dude = new Gravel();
       
        Render g = new Render();
        g.run();
        
        System.out.println(dude);
      
        System.out.println(dude.isVisible());
        System.out.println(dude.getId());
        System.out.println("Hello, World!");
    }
}
