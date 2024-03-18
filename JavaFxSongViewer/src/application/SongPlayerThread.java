package application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import bdd.ConnexionBDD;
import javafx.application.Platform;

public class SongPlayerThread extends Thread {
	
	private volatile boolean running;
	private SongViewerController controller;
	
	private int songDuration = 0;
	List<String> songInfo = new ArrayList<String>();
	List<String> songGenres = new ArrayList<String>();
	
	List<String> previousSongInfo = songInfo;
	List<String> previousSongGenres = new ArrayList<String>();
	
	Song currentSong;
	List<Song> previousSongs = new ArrayList<Song>();
	
	private boolean previousSongSelected;
	private int firstLaunch;

	public SongPlayerThread(SongViewerController controller) {
		super();
		this.running = true;
		this.controller = controller;
		this.previousSongSelected = false;
		this.firstLaunch = 2;
    	Platform.runLater(() -> controller.updateTimer(songDuration));
	}

	public void stopPlayer() {
		this.running = false;
	}
	
	public void nextSong() {
		this.songDuration = 0;
	}
	
	public void previousSong() {
		if(firstLaunch<1) {
			System.out.println(previousSongSelected);
			previousSongSelected = true;
			this.songDuration = 0;
		}
	}
	
	@Override
	public void run() {
		
		while (running) {
		    try {
		    	
		    	if(songDuration < 1) {
		    		
	    			this.previousSongInfo = songInfo;
		    		this.previousSongGenres = songGenres;
		    		
		    		if (!previousSongSelected || firstLaunch>=1) {
		    			this.songInfo = ConnexionBDD.getSongInfo(ConnexionBDD.getRequest(controller.getComboBoxValue(), previousSongInfo, previousSongGenres));
			    		this.songGenres = ConnexionBDD.getSongGenres(songInfo.get(0));
			    		this.songDuration = ThreadLocalRandom.current().nextInt(3, 8 + 1);
			    		
			    		currentSong = new Song(songDuration, songInfo.get(0), songInfo.get(1), songInfo.get(2), songInfo.get(4), songInfo.get(5), songInfo.get(6), songGenres);
			    		previousSongs.add(currentSong);
			    		
			    		// Mise à jour de l'affichage
			    		Platform.runLater(() -> controller.updateTitle(currentSong.getName())); 
			    		Platform.runLater(() -> controller.updateAlbum(currentSong.getAlbum())); 
			    		Platform.runLater(() -> controller.updateArtist(currentSong.getArtist())); 
			    		Platform.runLater(() -> controller.updateGenre(currentSong.getGenres())); 
			    		Platform.runLater(() -> controller.updateDanseability(currentSong.getDanceability())); 
			    		Platform.runLater(() -> controller.updateEnergy(currentSong.getEnergy())); 
			    		Platform.runLater(() -> controller.updateLoudness(currentSong.getLoudness()));
			    		
		    		} else {
		    			songDuration = previousSongs.get(previousSongs.size()-1).getSongDuration();
			    		// Mise à jour de l'affichage
			    		Platform.runLater(() -> controller.updateTitle(previousSongs.get(previousSongs.size()-1).getName())); 
			    		Platform.runLater(() -> controller.updateAlbum(previousSongs.get(previousSongs.size()-1).getAlbum())); 
			    		Platform.runLater(() -> controller.updateArtist(previousSongs.get(previousSongs.size()-1).getArtist())); 
			    		Platform.runLater(() -> controller.updateGenre(previousSongs.get(previousSongs.size()-1).getGenres())); 
			    		Platform.runLater(() -> controller.updateDanseability(previousSongs.get(previousSongs.size()-1).getDanceability())); 
			    		Platform.runLater(() -> controller.updateEnergy(previousSongs.get(previousSongs.size()-1).getEnergy())); 
			    		Platform.runLater(() -> controller.updateLoudness(previousSongs.get(previousSongs.size()-1).getLoudness()));
			    		previousSongs.removeLast();
		    			previousSongSelected = false;
		    		}
		    		if (this.firstLaunch > 0) {
			    		this.firstLaunch--;	
		    		}
		    	}
		    	Thread.sleep(1000);
		    	songDuration--;
		    	Platform.runLater(() -> controller.updateTimer(songDuration));
		    } catch (InterruptedException ex) {
		    	ex.printStackTrace();
		    }
		}
	}
	
}
