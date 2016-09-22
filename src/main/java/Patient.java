import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

  public class Patient {
    private String name;
    private String illness;
    private int doctor;
    private int id;

  public Patient(String name, String illness, int doctor) {
    this.name = name;
    this.illness = illness;
    this.doctor = doctor;
  }

  public String getName() {
    return name;
  }

  public String getIllness() {
    return illness;
  }

  public int getDoctor() {
    return doctor;
  }

  public int getId() {
    return id;
  }

  public static List<Patient> all() {
    String sql = "SELECT id, name, illness, doctor FROM patients";
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
      return this.getName().equals(newPatient.getName())
      && this.getId() == newPatient.getId()
      && this.getIllness().equals(newPatient.getIllness())
      && this.getDoctor() == newPatient.getDoctor();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients(name, illness, doctor) VALUES(:name, :illness, :doctor)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("illness", this.illness)
        .addParameter("doctor", this.doctor)
        .executeUpdate()
        .getKey();
      }
    }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients where id=:id";
      Patient patient = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patient.class);
        return patient;
      }
    }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM patients WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }

  public void update(String name, String illness) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE patients SET name = :name, illness = :illness WHERE id = :id";
    con.createQuery(sql)
      .addParameter("name", name)
      .addParameter("illness", illness)
      // .addParameter("doctor", doctor)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
}
