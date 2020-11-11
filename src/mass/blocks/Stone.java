package mass.blocks;

import mass.Block;

public class Stone extends Block {

    protected Stone(int id, String hr){
        super(id,hr);
    }

    @Override
    public String toString() {
        return "Stone-" + super.toString();
    }

}