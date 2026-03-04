module org.example.animacao_metodos {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.animacao_metodos to javafx.fxml;
    exports org.example.animacao_metodos;
}