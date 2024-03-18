package application;

import java.util.List;

public class Song {
	
	private int songDuration;
	private String name;
	private String album;
	private String artist;
	private String Danceability;
	private String Energy;
	private String Loudness;
	private List<String> genres;
	
	public Song(int songDuration, String name, String album, String artist, String danceability, String energy,
			String loudness, List<String> genres) {
		super();
		this.songDuration = songDuration;
		this.name = name;
		this.album = album;
		this.artist = artist;
		Danceability = danceability;
		Energy = energy;
		Loudness = loudness;
		this.genres = genres;
	}
	public int getSongDuration() {
		return songDuration;
	}
	public void setSongDuration(int songDuration) {
		this.songDuration = songDuration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getDanceability() {
		return Danceability;
	}
	public void setDanceability(String danceability) {
		Danceability = danceability;
	}
	public String getEnergy() {
		return Energy;
	}
	public void setEnergy(String energy) {
		Energy = energy;
	}
	public String getLoudness() {
		return Loudness;
	}
	public void setLoudness(String loudness) {
		Loudness = loudness;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
}
