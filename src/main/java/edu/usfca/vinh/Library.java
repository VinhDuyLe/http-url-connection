package edu.usfca.vinh;//Student: Vinh Duy Le   Class: CS514

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Library {
    private ArrayList<Song> songs;

    public Library() {
        songs = new ArrayList<Song>();
    }

    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    private static void parseArtistXML(HashMap<Integer,Artist> hmArtist, NodeList nArtistList) {
        for (int i = 0; i < nArtistList.getLength(); i++) {
            Node node = nArtistList.item(0);
            NodeList nChildList = ((Element)node).getElementsByTagName("artist");
            for (int j = 0; j < nChildList.getLength(); j++) {
                Node nChildNode = nChildList.item(j);
                if (nChildNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nChildNode;

                    String artistID = element.getAttribute("id");
                    String artistName = element.getElementsByTagName("name").item(0).getTextContent().trim();
                    hmArtist.put(Integer.parseInt(artistID), new Artist(artistName));
                }
            }
        }
    }

    private static void parseAlbumXML(HashMap<Integer, Album> hmAlbum, NodeList nAlbumList) {
        for (int i = 0; i < nAlbumList.getLength(); i++) {
            Node node = nAlbumList.item(0);
            NodeList nChildList = ((Element)node).getElementsByTagName("album");
            for (int j = 0; j < nChildList.getLength(); j++) {
                Node nChildNode = nChildList.item(j);
                if (nChildNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nChildNode;

                    String idAlbum = element.getAttribute("id");
                    String nameAlbum = element.getElementsByTagName("title").item(0).getTextContent().trim();
                    hmAlbum.put(Integer.parseInt(idAlbum), new Album(nameAlbum));

                }
            }
        }
    }

    private static List<Song> parseSongXML(HashMap<Integer,Artist> hmArtist, HashMap<Integer, Album> hmAlbum, NodeList nSongList) {
        List<Song> listSong = new ArrayList<>();
        for (int i = 0; i < nSongList.getLength(); i++) {
            Node node = nSongList.item(0);
            NodeList nChildList = ((Element)node).getElementsByTagName("song");
            for (int j = 0; j < nChildList.getLength(); j++) {
                Node nChildNode = nChildList.item(j);
                if (nChildNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nChildNode;

                    String songID = element.getAttribute("id");
                    String songTitle = element.getElementsByTagName("title").item(0).getTextContent().trim();
                    String artistID = ((Element)element.getElementsByTagName("artist").item(0)).getAttribute("id");
                    String artistName = element.getElementsByTagName("artist").item(0).getTextContent().trim();
                    String albumID = ((Element)element.getElementsByTagName("album").item(0)).getAttribute("id");
                    String albumName = element.getElementsByTagName("album").item(0).getTextContent().trim();

                    Song song1 = new Song(songTitle);
                    listSong.add(song1);
                    if (hmArtist.containsKey(Integer.parseInt(artistID))) {
                        song1.setPerformer(hmArtist.get(Integer.parseInt(artistID)));
                    } else {
                        Artist artistNew = new Artist(artistName);
                        song1.setPerformer(artistNew);
                        hmArtist.put(Integer.parseInt(artistID), artistNew);
                    }
                    if (hmAlbum.containsKey(Integer.parseInt(albumID))) {
                        song1.setAlbum(hmAlbum.get(Integer.parseInt(albumID)));
                    } else {
                        Album albumNew = new Album(albumName);
                        song1.setAlbum(albumNew);
                        hmAlbum.put(Integer.parseInt(albumID),albumNew);
                    }
                }
            }
        }
        return listSong;
    }

    public void parseXMLAddSongs(String filename) {
        HashMap<Integer,Artist> hmArtist = new HashMap<>();
        HashMap<Integer, Album> hmAlbum = new HashMap<>();

        try {
            File file = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nArtistList = doc.getElementsByTagName("artists");
            NodeList nAlbumList = doc.getElementsByTagName("albums");
            NodeList nSongList = doc.getElementsByTagName("songs");

            parseArtistXML(hmArtist, nArtistList);
            parseAlbumXML(hmAlbum,nAlbumList);
            List<Song> listSong = parseSongXML(hmArtist,hmAlbum,nSongList);

            for (Song song : listSong) {
                addSong(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseArtistJSON(HashMap<Integer,Artist> hmArtist, JSONArray artistsArray) {
        for (int i = 0; i < artistsArray.size(); i++) {
            String id = (String)((JSONObject)artistsArray.get(i)).get("id");
            String name = (String)((JSONObject)artistsArray.get(i)).get("name");

            hmArtist.put(Integer.parseInt(id), new Artist(name));
        }
    }

    private static void parseAlbumJSON(HashMap<Integer, Album> hmAlbum, JSONArray albumsArray) {
        for (int i = 0; i < albumsArray.size(); i++) {
            String id = (String)((JSONObject)albumsArray.get(i)).get("id");
            String name = (String)((JSONObject)albumsArray.get(i)).get("name");

            hmAlbum.put(Integer.parseInt(id), new Album(name));
        }
    }

    private static List<Song> parseSongJSON (HashMap<Integer,Artist> hmArtist, HashMap<Integer, Album> hmAlbum, JSONArray songsArray) {
        List<Song> listSong = new ArrayList<>();
        for (int i = 0; i < songsArray.size(); i++) {
            String idSong = (String)((JSONObject)songsArray.get(i)).get("id");
            String title = (String)((JSONObject)songsArray.get(i)).get("title");
            String idArtist = (String)((JSONObject)((JSONObject)songsArray.get(i)).get("artist")).get("id");
            String nameArtist = (String)((JSONObject)((JSONObject)songsArray.get(i)).get("artist")).get("name");
            String idAlbum = (String)((JSONObject)((JSONObject)songsArray.get(i)).get("album")).get("id");
            String nameAlbum = (String)((JSONObject)((JSONObject)songsArray.get(i)).get("album")).get("name");

            Song song2 = new Song(title);
            listSong.add(song2);
            if (hmArtist.containsKey(Integer.parseInt(idArtist))) {
                song2.setPerformer(hmArtist.get(Integer.parseInt(idArtist)));
            } else {
                Artist artistNew = new Artist(nameArtist);
                song2.setPerformer(artistNew);
                hmArtist.put(Integer.parseInt(idArtist), artistNew);
            }
            if (hmAlbum.containsKey(Integer.parseInt(idAlbum))) {
                song2.setAlbum(hmAlbum.get(Integer.parseInt(idAlbum)));
            } else {
                Album albumNew = new Album(nameAlbum);
                song2.setAlbum(albumNew);
                hmAlbum.put(Integer.parseInt(idAlbum),albumNew);
            }
        }
        return listSong;
    }

    public void parseJSONAddSongs(String filename) {
        HashMap<Integer,Artist> hmArtist = new HashMap<>();
        HashMap<Integer, Album> hmAlbum = new HashMap<>();

        JSONParser parser = new JSONParser();
        try {
            File file = new File(filename);
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray artistsArray = (JSONArray) jsonObject.get("artists");
            JSONArray albumsArray = (JSONArray) jsonObject.get("albums");
            JSONArray songsArray = (JSONArray) jsonObject.get("songs");

            parseArtistJSON(hmArtist, artistsArray);
            parseAlbumJSON(hmAlbum, albumsArray);
            List<Song> listSong = parseSongJSON(hmArtist, hmAlbum, songsArray);

            for (Song song : listSong) {
                addSong(song);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findDefinitelyDuplicate() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < songs.size(); i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                if (songs.get(i).equals(songs.get(j))) {
                    System.out.println("This song is definitely duplicate: " + "\n" + songs.get(i) + "\n" + songs.get(j) + "\n" + "Input Y/N to delete this song:");
                    if (sc.nextLine().equals("Y")) {
                        songs.remove(songs.get(i));
                    }
                }
            }
        }
    }

    public void findPossiblyDuplicate() {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < songs.size(); i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                if ((songs.get(i).getName().equals(songs.get(j).getName())
                        && (songs.get(i).getPerformer().equals(songs.get(j).getPerformer())
                        || songs.get(i).getAlbum().equals(songs.get(j).getAlbum())  ))
                    || (songs.get(i).getPerformer().equals(songs.get(j).getPerformer())
                        && songs.get(i).getAlbum().equals(songs.get(j).getAlbum())
                        && songs.get(i).getName().toLowerCase().replaceAll("\\p{Punct}","").trim()
                            .equals(songs.get(j).getName().toLowerCase().replaceAll("\\p{Punct}","").trim())    )
                    ) {

                    System.out.println("This two songs is possibly duplicate: " + "\n" + songs.get(i) + "\n" + songs.get(j) + "\n" + "Input Y/N to delete this song:");
                    if (sc.nextLine().equals("Y")) {
                        songs.remove(songs.get(i));
                    }
                }
            }
        }
    }


}
