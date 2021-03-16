package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Student;
import ssvv.example.repository.NotaXMLRepo;
import ssvv.example.repository.StudentXMLRepo;
import ssvv.example.repository.TemaXMLRepo;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.ValidationException;

import static org.junit.Assert.*;

public class AppTest 
{

    private Service service;

    @Before
    public void setup()
    {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/Studenti2.xml";
        String filenameTema = "fisiere/Teme2.xml";
        String filenameNota = "fisiere/Note2.xml";

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
    public void testAddStudentValidGrupa()
    {
        service.addStudent(new Student("1", "test", 937, "test@test.test"));
    }

    @Test
    public void testAddStudentInvalidGrupa()
    {
        try
        {
            service.addStudent(new Student("1", "test", -1, "test@test.test"));
        }
        catch(ValidationException e)
        {
            return;
        }
        fail();

    }

    @After
    public void teardown()
    {
        service = null;
    }
}
