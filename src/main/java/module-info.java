module com.example.naplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence; // Ha Hibernate és JPA szükséges
    requires org.hibernate.orm.core; // Ha Hibernate-et használsz
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;

    exports com.example.naplo;
}
