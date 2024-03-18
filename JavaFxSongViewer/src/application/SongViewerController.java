package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class SongViewerController {
	
	private SongPlayerThread player;
	
	@FXML
	Label durationLabel;
	@FXML
	Label titleLabel;
	@FXML
	Label albumLabel;
	@FXML
	Label artistLabel;
	@FXML
	Label genresLabel;
	@FXML
	Label danceabilityLabel;
	@FXML
	Label energyLabel;
	@FXML
	Label loudnessLabel;
	@FXML
	ComboBox filter;
	
	ObservableList<String> filtersList = FXCollections.observableArrayList("Random", "Album", "Artiste", "Danceability", "Energy", "Loudness", "Genres");
	
	@SuppressWarnings("unchecked")
	public void initComboBox() {
		filter.setItems(filtersList);
	}
	
	public String getComboBoxValue() {
		String selectedItem;
		Object selectedObject = filter.getValue();
		if(selectedObject == null) {
			selectedItem = "Random";
		} else {
			selectedItem = selectedObject.toString();
		}
		return selectedItem;
	}
	
	public void updateTimer(int updatedDurationLabel) {
		durationLabel.setText(Integer.toString(updatedDurationLabel));
	}
	
	public String getTimer() {
		return durationLabel.getText();
	}
	
	public void updateTitle(String updatedTitle) {
		titleLabel.setText(updatedTitle);
	}
	
	public void updateAlbum(String updatedAlbum) {
		albumLabel.setText(updatedAlbum);
	}

	public void updateArtist(String updatedArtist) {
		artistLabel.setText(updatedArtist);
	}

	public void updateGenre(List<String> updatedGenres) {
		String genres = "";
		for (int i=0; i<updatedGenres.size(); i++) {
			genres = genres+","+updatedGenres.get(i);
		}
		genresLabel.setText(genres.substring(1));
	}

	public void updateDanseability(String updatedDanseability) {
		danceabilityLabel.setText(updatedDanseability);
	}

	public void updateEnergy(String updatedEnergy) {
		energyLabel.setText(updatedEnergy);
	}

	public void updateLoudness(String updatedLoudness) {
		loudnessLabel.setText(updatedLoudness);
	}
	
	public void precedentClicked() {
		player.previousSong();
	}
	
	public void suivantClicked() {
		player.nextSong();
	}

	public SongPlayerThread getPlayer() {
		return player;
	}

	public void setPlayer(SongPlayerThread player) {
		this.player = player;
	}
	
}
