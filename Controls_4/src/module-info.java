module Controls_4{
    requires javafx.fxml;
    requires javafx.controls;
    requires graphic1; // Include the JAR file.

    opens sample; // sample is the package name.
}