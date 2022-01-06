package edu.usfca.vinh;//Student: Vinh Duy Le   Class: CS514

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@XmlRootElement
public class Playlist {
    private ArrayList<Song> listOfSongs;

    public Playlist() {
        listOfSongs = new ArrayList<Song>();
    }

    //TASK 4

    public void addSong(Song s) {
        listOfSongs.add(s);
    }
    public void deleteSong(Song s) {
        listOfSongs.remove(s);
    }
    @XmlElement(name = "song")
    public ArrayList<Song> getListOfSongs() {
        return listOfSongs;
    }

    public ArrayList<Song> mergeTwoArrayList(ArrayList<Song> otherListOfSongs) {
        for (Song song : otherListOfSongs) {
            if (!this.listOfSongs.contains(song))
                listOfSongs.add(song);
        }
        return listOfSongs;
    }

    public ArrayList<Song> sortPlayList() {
        ArrayList<Song> sortListOfSongs = (ArrayList<Song>) listOfSongs.clone();
        Collections.sort(sortListOfSongs, (o1, o2) -> o2.getLiked() - o1.getLiked());

        return sortListOfSongs;
    }

    public ArrayList<Song> randomShuffle() {
        Random r = new Random();
        Collections.shuffle(this.listOfSongs, r);
        return listOfSongs;
    }

    //TASK 5
    public Playlist randomFeaturePlaylist() {
        Playlist randomPlaylist = new Playlist();
        Random r = new Random();
        for (Song song : this.listOfSongs) {
            if(song.getGenre().equals("jazz") || song.getBPM() > 120) {
                randomPlaylist.getListOfSongs().add(song);
            }
        }
        Collections.shuffle(randomPlaylist.getListOfSongs(), r);
        return randomPlaylist;
    }

    //TASK 6
    public void exportToXML(String fileName) {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Playlist.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            File file = new File(fileName);
            jaxbMarshaller.marshal(this, file);

        }catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void exportToJSON(String filename) {
        JSONObject obj = new JSONObject();
        JSONArray listArtists = new JSONArray();
        JSONArray listSongs = new JSONArray();
        JSONArray listAlbums = new JSONArray();

        for (Song song : this.listOfSongs) {
            JSONObject childAlbumObject = new JSONObject();
            childAlbumObject.put("id", song.getAlbum().getEntityID());
            childAlbumObject.put("name", song.getAlbum().getName());

            listAlbums.add(childAlbumObject);

            JSONObject childArtistObject = new JSONObject();
            childArtistObject.put("id", song.getPerformer().getEntityID());
            childArtistObject.put("name", song.getPerformer().getName());

            listArtists.add(childArtistObject);

            JSONObject childSongObject = new JSONObject();
            childSongObject.put("id", song.getEntityID());
            childSongObject.put("title", song.getName());
            childSongObject.put("artist", childArtistObject);
            childSongObject.put("album", childAlbumObject);

            listSongs.add(childSongObject);

        }

        obj.put("albums", listAlbums);
        obj.put("artists", listArtists);
        obj.put("songs", listSongs);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
