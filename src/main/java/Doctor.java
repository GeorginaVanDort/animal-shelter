import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;


public class Doctor {
  private int id;
  private String speciality;
  private String name;
  private int patientid;

public Doctor(String speciality, String name, int patientid) {
  this.speciality = speciality;
  this.name = name;
  this.patientid = patientid;
  }

  public int getId() {
    return id;
  }

  public String getSpeciality() {
    return speciality;
  }

  public String getName() {
    return name;
  }

  public int getPatientId() {
    return patientid;
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, speciality, name, patientid FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName());
    }
  }

  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO doctors (speciality, name, patientid) VALUES (:speciality, :name, :patientid)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("speciality", this.speciality)
      .addParameter("name", this.name)
      .addParameter("patientid", this.patientid)
      .executeUpdate()
      .getKey();
  }
}




}
