package edu.usfca.vinh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    public Album() {};

    public Album(String name) {
        super(name);
    }

    public String getName() {
        return name;
    }

    public boolean equals(Album otherAlbum) {
        return this.name.equals(otherAlbum.getName());
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String toSQL() {
        return "insert into albums (id, name, artist) values (" + this.entityID + ", '" + this.name + "', " + artist.entityID + ");";
    }

    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
