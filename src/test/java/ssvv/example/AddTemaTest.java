package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ssvv.example.domain.Tema;
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
public class AddTemaTest
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
        assertNull(service.addTema(new TemaBuilder().createTema()));
    }

    @Test
    public void testCase2() {
        Tema initialTema = new TemaBuilder().createTema();
        service.addTema(initialTema);
        Tema tema = service.addTema(new TemaBuilder().createTema());
        assertEquals(initialTema.getID(), tema.getID());
        assertEquals(initialTema.getPrimire(), tema.getPrimire());
        assertEquals(initialTema.getDeadline(), tema.getDeadline());
        assertEquals(initialTema.getDescriere(), tema.getDescriere());
    }

    @Test
    public void testCase3() {
        try {
            service.addTema(new TemaBuilder().setNrTema("").createTema());
            fail();
        } catch (ValidationException e) {
            assertEquals("Numar tema invalid!", e.getMessage());
        }
    }

    @Test
    public void testCase4() {
        try {
            service.addTema(new TemaBuilder().setDescriere("").createTema());
            fail();
        } catch (ValidationException e) {
            assertEquals("Descriere invalida!", e.getMessage());
        }
    }

    @Test
    public void testCase5() {
        try {
            service.addTema(new TemaBuilder().setDeadline(-1).createTema());
            fail();
        } catch (ValidationException e) {
            assertEquals("Deadlineul trebuie sa fie intre 1-14.", e.getMessage());
        }
    }

    @Test
    public void testCase6() {
        try {
            service.addTema(new TemaBuilder().setPrimire(-1).createTema());
            fail();
        } catch (ValidationException e) {
            assertEquals("Saptamana primirii trebuie sa fie intre 1-14.", e.getMessage());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @After
    public void teardown() {
        service = null;
        new File(filenameStudent).delete();
        new File(filenameNota).delete();
        new File(filenameTema).delete();
    }

    public static class TemaBuilder
    {
        private String nrTema = "123";
        private String descriere = "testDescriere";
        private int deadline = 6;
        private int primire = 5;

        public TemaBuilder setNrTema(String nrTema)
        {
            this.nrTema = nrTema;
            return this;
        }

        public TemaBuilder setDescriere(String descriere)
        {
            this.descriere = descriere;
            return this;
        }

        public TemaBuilder setDeadline(int deadline)
        {
            this.deadline = deadline;
            return this;
        }

        public TemaBuilder setPrimire(int primire)
        {
            this.primire = primire;
            return this;
        }

        public Tema createTema()
        {
            return new Tema(nrTema, descriere, deadline, primire);
        }
    }
}
