import mass.Block;
import mass.blocks.stones.Gravel;

public class App {
    public static void main(String[] args) {

        Block dude = new Gravel();
       
        System.out.println(dude);
      
        System.out.println(dude.isVisible());
        System.out.println(dude.getId());
        System.out.println("Hello, World!");
    }
}
