package application;
	
import bdd.ConnexionBDD;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Connecting to database
			ConnexionBDD.openConnexion();
			
			// Loading scene
			FXMLLoader loader = new FXMLLoader(getClass().getResource("JavaFx.fxml"));
			//Parent root = loader.load();
			VBox root = (VBox) loader.load();
			SongViewerController controller = loader.getController();
			
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			controller.initComboBox();
			
			SongPlayerThread player = new SongPlayerThread(controller);
			controller.setPlayer(player);
			player.setDaemon(true);
			player.start();
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        System.out.println("Hello, World!");
        //Lancement de l'interface
        launch(args);
        ConnexionBDD.closeConnexion();
        System.out.println("Goodbye, World!");
    }
	
}
