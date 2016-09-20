import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesCorrectly_true() {
    Doctor testDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
    assertEquals(true, testDoctor instanceof Doctor);
  }

  @Test
  public void Doctor_instantiatesWithName_String() {
    Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
    assertEquals("Dr. Payne", myDoctor.getName());
  }

  @Test
  public void Doctor_instantiatesWithSpeciality_String() {
    Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
    assertEquals("Orthopedic Surgeon", myDoctor.getSpeciality());
  }

  @Test
  public void Doctor_instantiatesWithPatientId_Int() {
    Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
    assertEquals(1, myDoctor.getPatientId());
  }

  @Test
   public void equals_returnsTrueIfNamesAretheSame() {
     Doctor firstDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
     Doctor secondDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
     assertTrue(firstDoctor.equals(secondDoctor));
   }

   @Test
    public void save_savesIntoDatabase_true() {
      Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne", 1);
      myDoctor.save();
      assertTrue(Doctor.all().get(0).equals(myDoctor));

    }

}
