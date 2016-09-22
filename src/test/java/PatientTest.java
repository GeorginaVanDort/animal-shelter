import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class PatientTest{
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
    public void Patient_instantiatesCorrectly_true() {
      Patient testPatient = new Patient("Fred", "Tuberculosis", 1);
      assertEquals(true, testPatient instanceof Patient);
    }

    @Test
    public void Patient_instantiatesWithName_String() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      assertEquals("Fred", myPatient.getName());
    }

    @Test
    public void Patient_instantiatesWithIllness_String() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      assertEquals("Tuberculosis", myPatient.getIllness());
    }

    @Test
    public void Patient_instantiatesWithDoctor_int() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      assertEquals(1, myPatient.getDoctor());
    }

    @Test
    public void Patient_instantiatesWithId() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      myPatient.save();
      assertTrue(myPatient.getId() > 0);
    }

    @Test
    public void equals_returnsTrueIfDescriptionsAretheSame() {
      Patient onePatient = new Patient("Fred", "Tuberculosis", 1);
      onePatient.save();
      Patient twoPatient = new Patient("Fred", "Tuberculosis", 1);
      twoPatient.save();
      assertEquals(true, Patient.all().get(0).equals(onePatient));
      assertEquals(true, Patient.all().get(1).equals(twoPatient));
    }

    @Test
    public void save_returnsTrueIfDescriptionsAretheSame() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      myPatient.save();
      assertTrue(Patient.all().get(0).equals(myPatient));
    }

    @Test
    public void save_assignsIdToObject() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      myPatient.save();
      Patient savedPatient = Patient.all().get(0);
      assertEquals(myPatient.getId(), savedPatient.getId());
    }

    @Test
    public void find_returnsPatientWithSameId_secondPatient() {
      Patient firstPatient = new Patient("Fred", "Tuberculosis", 1);
      firstPatient.save();
      Patient secondPatient = new Patient("Fred", "Tuberculosis", 1);
      secondPatient.save();
      assertEquals(Patient.find(secondPatient.getId()), secondPatient);
    }

    @Test
    public void save_savesDoctorIdIntoDB_true() {
      Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      myDoctor.save();
      Patient myPatient = new Patient("Fred", "Tuberculosis", myDoctor.getId());
      myPatient.save();
      Patient savedPatient = Patient.find(myPatient.getId());
      assertEquals(savedPatient.getDoctor(), myDoctor.getId());
    }

    @Test
    public void update_updatesPatientDescription_true() {
      Patient myPatient = new Patient("Fred", "Tuberculosis", 1);
      myPatient.save();
      myPatient.update("Jim", "Tuberculosis", 1);
      assertEquals("Jim", Patient.find(myPatient.getId()).getName());
    }

}
