import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;


public class Doctor {
  private int id;
  private String speciality;
  private String name;

public Doctor(String speciality, String name) {
  this.speciality = speciality;
  this.name = name;
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

  public static List<Doctor> all() {
    String sql = "SELECT id, speciality, name FROM doctors";
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
      return this.getName().equals(newDoctor.getName()) && this.getId() == newDoctor.getId();
    }
  }

  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO doctors (speciality, name) VALUES (:speciality, :name)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("speciality", this.speciality)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
  }
}

  public static Doctor find(int id) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "SELECT * FROM doctors where id=:id";
        Doctor doctor = con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetchFirst(Doctor.class);
        return doctor;
      }
    }


}
