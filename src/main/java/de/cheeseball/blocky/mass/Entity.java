package de.cheeseball.blocky.mass;

public class Entity {

    protected int id;
    protected String hr_ident;

    //Nobody can create Entitys (exept subclasses)
    protected Entity(int id, String hr_ident){
        this.id = id;
        this.hr_ident = hr_ident;
    }

    public int getId() {
        return id;
    }

    public String getHr_ident() {
        return hr_ident;
    }

    @Override
    public String toString() {
        return getHr_ident() + " (" + getId() + ")";
    }
}
