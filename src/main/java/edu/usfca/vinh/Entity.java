package edu.usfca.vinh;

import java.util.Date;

public class Entity {
    protected String name;
    protected static int counter = 300;
    protected int entityID;
    protected Date dateCreated;

    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    public boolean equals(Entity otherEntity) {
        return entityID == otherEntity.entityID;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Entity.counter = counter;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getEntityID() {
        return entityID;
    }

    public String toString() {
        return "Name: " + this.name + " ID: " + this.entityID;
    }
    public String toHTML() {
        return "<b>" + this.name + "</b><i> " + this.entityID + "</i>";
    }
    public String toXML() {
        return "<entity><name>" + this.name + "</name><ID> " + this.entityID + "</ID></entity>";
    }
}
