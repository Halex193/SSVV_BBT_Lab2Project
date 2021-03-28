package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ssvv.example.domain.Student;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

import java.io.File;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AppTest
{

    private Service service;
    private final String filenameStudent = "fisiere/TestStudenti.xml";
    private final String filenameTema = "fisiere/TestTeme.xml";
    private final String filenameNota = "fisiere/TestNote.xml";

    @Before
    public void setup()
    {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();

        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void testCase1()
    {
        assertNull(service.addStudent(new StudentBuilder().createStudent()));
    }

    @Test
    public void testCase2()
    {
        Student initialStudent = new StudentBuilder().createStudent();
        service.addStudent(initialStudent);
        Student student = service.addStudent(new StudentBuilder().createStudent());
        assertEquals(initialStudent.getID(), student.getID());
        assertEquals(initialStudent.getNume(), student.getNume());
        assertEquals(initialStudent.getGrupa(), student.getGrupa());
        assertEquals(initialStudent.getEmail(), student.getEmail());
    }

    @Test
    public void testCase3()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setGrupa(-1).createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase4()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setNume("").createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase5()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setNume(null).createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase6()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setIdStudent("").createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase7()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setIdStudent(null).createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase8()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setEmail("").createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase9()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setEmail(null).createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase10()
    {
        try
        {
            Student student = service.addStudent(new StudentBuilder().setGrupa(0).createStudent());
            fail("Output: " + student);
        } catch (ValidationException ignored)
        {
        }
    }

    @Test
    public void testCase11()
    {
        assertNull(service.addStudent(new StudentBuilder().setGrupa(1).createStudent()));
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @After
    public void teardown()
    {
        service = null;
        new File(filenameStudent).delete();
        new File(filenameNota).delete();
        new File(filenameTema).delete();
    }

    public static class StudentBuilder
    {
        private String idStudent = "1#927";
        private String nume = "Andrei";
        private int grupa = 937;
        private String email = "test@test.com";

        public StudentBuilder setIdStudent(String idStudent)
        {
            this.idStudent = idStudent;
            return this;
        }

        public StudentBuilder setNume(String nume)
        {
            this.nume = nume;
            return this;
        }

        public StudentBuilder setGrupa(int grupa)
        {
            this.grupa = grupa;
            return this;
        }

        public StudentBuilder setEmail(String email)
        {
            this.email = email;
            return this;
        }

        public Student createStudent()
        {
            return new Student(idStudent, nume, grupa, email);
        }
    }
}
