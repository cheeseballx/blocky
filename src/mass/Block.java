package mass;

import world.WorldLocation;

public class Block extends Entity{
    protected boolean visible;
    protected boolean solid;
    
    //Nobody outside package and subclasses can create A block
    protected Block(){

        //set some standards
        this.visible = true;
        this.solid = true;
    }

    //Most Important step is draw
    public void draw(WorldLocation coord){
        
    }



    //To be vied from the outside
    public boolean isVisible(){ return visible; }
    public boolean isSolid(){ return visible; }
}
