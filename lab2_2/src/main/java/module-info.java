module io.jfxdevelop.lab2_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.jfxdevelop.lab2_2 to javafx.fxml;
    exports io.jfxdevelop.lab2_2;
}