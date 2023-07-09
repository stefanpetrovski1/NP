package RandomExercises.MP3Player;

import java.util.ArrayList;
import java.util.List;


class Song {
    private String title;
    private String artist;
    private boolean isPlaying;


    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.isPlaying = false;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title=" + title +
                ", artist=" + artist +
                '}';
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}


class MP3Player {
    private List<Song> songs;
    static int currentSongIndex = 0;

    public MP3Player(List<Song> songs) {
        this.songs = songs;
    }

    public void printCurrentSong() {
        System.out.println(songs.get(currentSongIndex));
    }


    public void pressPlay() {
        if (songs.get(currentSongIndex).getIsPlaying()) {
            System.out.println("Song is already playing");

        } else {
            songs.get(currentSongIndex).setIsPlaying(true);
            System.out.println("Song " + currentSongIndex + " is playing");
        }
    }


    public void pressStop() {

        if (!songs.get(currentSongIndex).getIsPlaying()) {
            currentSongIndex = 0;
            System.out.println("Songs are stopped");
        } else {
            songs.get(currentSongIndex).setIsPlaying(false);
            System.out.println("Song " + currentSongIndex + " is paused");
        }

    }

    public void pressFWD() {
        songs.get(currentSongIndex).setIsPlaying(false);

        if (currentSongIndex + 1 == songs.size()) {
            currentSongIndex = 0;
        } else {
            currentSongIndex++;
        }
        songs.get(currentSongIndex).setIsPlaying(true);
        System.out.println("Forward...");
    }

    public void pressREW() {
        songs.get(currentSongIndex).setIsPlaying(false);

        if (currentSongIndex - 1 < 0) {
            currentSongIndex = songs.size()-1;
        } else {
            currentSongIndex--;
        }
        songs.get(currentSongIndex).setIsPlaying(true);
        System.out.println("Reward...");
    }

    @Override
    public String toString() {
        return "RandomExercises.MP3Player{" +
                "currentSong=" + currentSongIndex +
                ", songList=" + songs +
                '}';
    }
}


public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde