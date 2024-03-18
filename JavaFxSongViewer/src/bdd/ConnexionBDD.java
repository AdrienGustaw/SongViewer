package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConnexionBDD {
	
	private final static String URL_BDD = "jdbc:postgresql://localhost:5432/projetBiblio";
	private final static String USERNAME_BDD = "postgres";
	private final static String PASSWORD_BDD = "postgres";
	private static Connection con;
	
	public static void openConnexion()
	{
	try
		{
	      //étape 1: charger la classe de driver
	      Class.forName("org.postgresql.Driver");
	      //étape 2: créer l'objet de connexion
	      con = DriverManager.getConnection(URL_BDD,USERNAME_BDD,PASSWORD_BDD);
	    }
	    catch(Exception e){ 
	      System.out.println(e);
	    }
	}
	
	public static void closeConnexion() {
		try {
			//fermez l'objet de connexion
			con.close();
		}
		catch(Exception e){ 
		    System.out.println(e);
		}
	}
	
	public static List<String> getSongInfo(String query) {
		// Creation du statement
		try (Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String namechanson = rs.getString("namechanson");
				String namealbum = rs.getString("namealbum");
			    String nameartiste = rs.getString("nameartiste");
				String namegenre = rs.getString("namegenre");
				String danceability = rs.getString("danceability");
				String energy = rs.getString("energy");
				String loudness = rs.getString("loudness");
			    //System.out.println(namechanson + ", " + namealbum + ", " + nameartiste + ", " + namegenre + ", " + danceability + ", " + energy + ", " + loudness);
			    return Arrays.asList(namechanson, namealbum, nameartiste, namegenre, danceability, energy, loudness);
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return new ArrayList<>();
	}
	
	public static List<String> getSongGenres(String songName) {
		List<String> genres = new ArrayList<>();
		// Creation du statement
		songName = songName.replaceAll("'", "''");
		try (Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT g.namegenre FROM genre AS g, chanson as c, apourgenre AS apg WHERE g.idgenre = apg.idgenre AND apg.idchanson = c.idchanson AND c.namechanson = '" + songName + "';");
			while (rs.next()) {
				genres.add(rs.getString("namegenre"));
			}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return genres;
	}
	
	public static String getRequest(String filter, List<String> previousSongInfo, List<String> previousSongGenres) {
		String query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre ORDER BY RANDOM() LIMIT 1";
		switch(filter) {
			case "Album": 
				String nameAlbum = previousSongInfo.get(1);
				nameAlbum = nameAlbum.replaceAll("'", "''");
				query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre AND a.namealbum = '" +  nameAlbum +"' ORDER BY RANDOM() LIMIT 1";
				//System.out.println("Filter by Album");
				break;
			case "Artiste":
				String nameArtiste = previousSongInfo.get(2);
				nameArtiste = nameArtiste.replaceAll("'", "''");
				query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre AND at.nameartiste = '" +  nameArtiste + "' ORDER BY RANDOM() LIMIT 1";
				//System.out.println("Filter by Artiste");
				break;
	        case "Danceability":
	        	String danceability = previousSongInfo.get(4);
	        	query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre AND c.danceability = '" +  danceability + "' ORDER BY RANDOM() LIMIT 1";
	        	//System.out.println("Filter by Danceability");
	        	break;
	        case "Energy":
	        	String energy = previousSongInfo.get(5);
	        	query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre AND c.energy = '" +  energy + "' ORDER BY RANDOM() LIMIT 1";
	        	//System.out.println("Filter by Energy");
	        	break;
	        case "Loudness":
	        	String loudness = previousSongInfo.get(6);
	        	query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre AND c.loudness = '" +  loudness + "' ORDER BY RANDOM() LIMIT 1";
	        	//System.out.println("Filter by Loudness");
	        	break;
	        case "Genres":
	        	String genres = previousSongGenres.get(ThreadLocalRandom.current().nextInt(0, previousSongGenres.size()));
	        	query = "SELECT c.namechanson, a.namealbum, at.nameartiste, g.namegenre, c.danceability, c.energy, c.loudness FROM chanson AS c, album AS a, artiste AS at, apourgenre AS apg, genre AS g WHERE c.idalbum = a.idalbum  AND c.idartiste = at.idartiste AND c.idchanson = apg.idchanson AND apg.idgenre = g.idgenre AND g.namegenre = '" +  genres + "' ORDER BY RANDOM() LIMIT 1";
	        	//System.out.println("Filter by Genres : " + genres);
	        	break;
		}
		return query;
	}

}
