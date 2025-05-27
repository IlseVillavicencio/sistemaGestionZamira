module main.sistemagestionzamira {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;
    requires java.sql;

    // Para que el FXMLLoader pueda acceder por reflexión a los controladores:
    opens main.sistemagestionzamira.controller to javafx.fxml;

    // Solo exporta el paquete principal si otro módulo lo necesita (no siempre es necesario)
    exports main.sistemagestionzamira.controller; 
    exports main.sistemagestionzamira;
}

