module com.ivantsov.diskanalizer {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens com.ivantsov.diskanalizer to javafx.fxml;
    exports com.ivantsov.diskanalizer;
}
