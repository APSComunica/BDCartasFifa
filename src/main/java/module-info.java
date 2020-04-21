module es.andresp.prueba1sb {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.andresp.prueba1sb to javafx.fxml;
    exports es.andresp.prueba1sb;
}