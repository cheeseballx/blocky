package de.cheeseball.blocky.mass.blocks;

import de.cheeseball.blocky.mass.Block;

public class Stone extends Block {

    protected Stone(int id, String hr){
        super(id,hr);
    }

    @Override
    public String toString() {
        return "Stone-" + super.toString();
    }

}