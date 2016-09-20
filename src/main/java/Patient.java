import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

  public class Patient {
    private String name;
    private String illness;
    private String doctor;
    private int id;

  public Patient(String name, String illness, String doctor, int id) {
    this.name = name;
    this.illness = illness;
    this.doctor = doctor;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getIllness(){
    return illness;
  }

  public String getDoctor(){
    return doctor;
  }

  public int getId() {
    return id;
  }

  public static List<Patient> all(){
    String sql = "SELECT name, illness, doctor FROM patients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getName().equals(newPatient.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name) VALUES (:name)";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .executeUpdate();
    }
  }
}
