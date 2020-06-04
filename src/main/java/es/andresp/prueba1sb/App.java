package es.andresp.prueba1sb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JavaFX App
 */
public class App extends Application {

        private EntityManagerFactory emf;
        private EntityManager em;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BDCartasFifa.fxml"));
        Parent root = fxmlLoader.load();

        emf = Persistence.createEntityManagerFactory("es.andresp_CartasFifa_jar_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();

        PrimaryController primaryController = (PrimaryController) fxmlLoader.getController();                
        primaryController.setEntityManager(em);
        primaryController.cargarTodasCartas();
        
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Cartas Fifa");
        primaryStage.setScene(scene);
        primaryStage.show();                
    }

    @Override
    public void stop() throws Exception {
        em.close(); 
        emf.close(); 
        try { 
            DriverManager.getConnection("jdbc:derby:BDCartasFifa;shutdown=true"); 
        } catch (SQLException ex) { 
        }        
    }

    public static void main(String[] args) {
        launch(args);
    }   
}
       
