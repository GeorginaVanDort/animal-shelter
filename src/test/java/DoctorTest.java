import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
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
    Doctor testDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
    assertEquals(true, testDoctor instanceof Doctor);
  }

  @Test
  public void Doctor_instantiatesWithName_String() {
    Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
    assertEquals("Dr. Payne", myDoctor.getName());
  }

  @Test
  public void Doctor_instantiatesWithSpeciality_String() {
    Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
    assertEquals("Orthopedic Surgeon", myDoctor.getSpeciality());
  }

  @Test
   public void equals_returnsTrueIfNamesAretheSame() {
     Doctor firstDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
     Doctor secondDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
     assertTrue(firstDoctor.equals(secondDoctor));
   }

   @Test
    public void save_savesIntoDatabase_true() {
      Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      myDoctor.save();
      assertTrue(Doctor.all().get(0).equals(myDoctor));
    }

    @Test
    public void all_returnsAllInstancesOfDoctor_true() {
      Doctor firstDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      firstDoctor.save();
      Doctor secondDoctor = new Doctor("Patiatrist", "Dr. Ouch");
      secondDoctor.save();
      assertEquals(true, Doctor.all().get(0).equals(firstDoctor));
      assertEquals(true, Doctor.all().get(1).equals(secondDoctor));
    }

    @Test
    public void save_assignsIdToObject() {
      Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      myDoctor.save();
      Doctor savedDoctor = Doctor.all().get(0);
      assertEquals(myDoctor.getId(), savedDoctor.getId());
    }

    @Test
    public void getId_doctorsInstantiateWithAnId_1() {
      Doctor testDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      testDoctor.save();
      assertTrue(testDoctor.getId() > 0);
    }

    @Test
    public void find_returnsDoctorWithSameId_secondDoctor() {
      Doctor firstDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      firstDoctor.save();
      Doctor secondDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      secondDoctor.save();
      assertEquals(Doctor.find(secondDoctor.getId()), secondDoctor);
    }

    @Test
    public void getPatients_retrievesALlPatientsFromDatabase_patientsList() {
      Doctor myDoctor = new Doctor("Orthopedic Surgeon", "Dr. Payne");
      myDoctor.save();
      Patient firstPatient = new Patient("Fred", "Tuberculosis", myDoctor.getId());
      firstPatient.save();
      Patient secondPatient = new Patient("Fred", "Tuberculosis", myDoctor.getId());
      secondPatient.save();
      Patient[] patients = new Patient[] { firstPatient, secondPatient };
      assertTrue(myDoctor.getPatients().containsAll(Arrays.asList(patients)));
    }
}
