package es.andresp.prueba1sb;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PrimaryController {
    private EntityManager entityManager;
    @FXML
    private TableColumn<Jugadores, String> columnaNombre;
    @FXML
    private TableColumn<Jugadores, String> columnaApellido;
    @FXML
    private TableColumn<Jugadores, String> columnaNacionalidad;
    @FXML
    private TableView<Jugadores> tableviewJugadores;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellido;
    
    private Jugadores jugadorSeleccionado;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        
        tableviewJugadores.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            System.out.println("Entra Initialize");
            jugadorSeleccionado = newValue;
             if (jugadorSeleccionado != null) {
                campoNombre.setText(jugadorSeleccionado.getNombre());
                campoApellido.setText(jugadorSeleccionado.getApellidos());
            } else {
                campoNombre.setText("");
                campoApellido.setText("");
            }
        });
}    
    
    
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cargarTodosJugadores() {
        Query queryJugadoresFindAll = entityManager.createNamedQuery("Jugadores.findAll");
        List<Jugadores> listJugadores = queryJugadoresFindAll.getResultList();
        System.out.println(listJugadores.get(0).getNombre());
        tableviewJugadores.setItems(FXCollections.observableArrayList(listJugadores));
}  

    @FXML
    private void guardarJugador(ActionEvent event) {
        if (jugadorSeleccionado != null) {
            System.out.println("Está entrando");
            jugadorSeleccionado.setNombre(campoNombre.getText());
            jugadorSeleccionado.setApellidos(campoApellido.getText());
            entityManager.getTransaction().begin();
            entityManager.merge(jugadorSeleccionado);
            entityManager.getTransaction().commit();
            int numFilaSeleccionada = tableviewJugadores.getSelectionModel().getSelectedIndex();
            tableviewJugadores.getItems().set(numFilaSeleccionada, jugadorSeleccionado);
            TablePosition pos = new TablePosition(tableviewJugadores, numFilaSeleccionada, null);
            tableviewJugadores.getFocusModel().focus(pos);
            tableviewJugadores.requestFocus();
        }    
    }
}

