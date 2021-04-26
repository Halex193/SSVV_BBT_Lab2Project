package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
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
import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class IntegrationAddTest {
    private final String filenameStudent = "fisiere/TestStudenti.xml";
    private final String filenameTema = "fisiere/TestTeme.xml";
    private final String filenameNota = "fisiere/TestNote.xml";
    private Service service;

    @Before
    public void setup() {
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @After
    public void teardown() {
        service = null;
        new File(filenameStudent).delete();
        new File(filenameNota).delete();
        new File(filenameTema).delete();
    }

    @Test
    public void testAddStudent() {
        assertNull(service.addStudent(new StudentBuilder().createStudent()));
    }

    @Test
    public void testAddTema() {
        assertNull(service.addTema(new TemaBuilder().createTema()));
    }

    @Test
    public void testAddStudentTema() {
        service.addStudent(new StudentBuilder().createStudent());
        service.addTema(new TemaBuilder().createTema());
    }

    @Test
    public void testAddGrade() {
        try {
            service.addNota(new NotaBuilder().createNota(), "bine bos");
            fail();
        } catch (ValidationException e) {
            assertEquals("Studentul nu exista!", e.getMessage());
        }
    }

    @Test
    public void testAddStudentTemaGrade() {
        service.addStudent(new StudentBuilder().createStudent());
        service.addTema(new TemaBuilder().createTema());
        service.addNota(new NotaBuilder().createNota(), "bine ghe ghe");
    }

    public static class TemaBuilder {
        private String nrTema = "123";
        private String descriere = "testDescriere";
        private int deadline = 6;
        private int primire = 5;

        public TemaBuilder setNrTema(String nrTema) {
            this.nrTema = nrTema;
            return this;
        }

        public TemaBuilder setDescriere(String descriere) {
            this.descriere = descriere;
            return this;
        }

        public TemaBuilder setDeadline(int deadline) {
            this.deadline = deadline;
            return this;
        }

        public TemaBuilder setPrimire(int primire) {
            this.primire = primire;
            return this;
        }

        public Tema createTema() {
            return new Tema(nrTema, descriere, deadline, primire);
        }
    }

    public static class StudentBuilder {
        private String idStudent = "1#927";
        private String nume = "Andrei";
        private int grupa = 937;
        private String email = "test@test.com";

        public StudentBuilder setIdStudent(String idStudent) {
            this.idStudent = idStudent;
            return this;
        }

        public StudentBuilder setNume(String nume) {
            this.nume = nume;
            return this;
        }

        public StudentBuilder setGrupa(int grupa) {
            this.grupa = grupa;
            return this;
        }

        public StudentBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Student createStudent() {
            return new Student(idStudent, nume, grupa, email);
        }
    }

    public static class NotaBuilder {
        private String id = "1";
        private String idStudent = "1#927";
        private String idTema = "123";
        private double nota = 10;
        private LocalDate data = LocalDate.of(2021, 4, 1);

        public NotaBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public NotaBuilder setIdStudent(String idStudent) {
            this.idStudent = idStudent;
            return this;
        }

        public NotaBuilder setIdTema(String idTema) {
            this.idTema = idTema;
            return this;
        }

        public NotaBuilder setNota(double nota) {
            this.nota = nota;
            return this;
        }

        public NotaBuilder setData(LocalDate data) {
            this.data = data;
            return this;
        }

        public Nota createNota() {
            return new Nota(id, idStudent, idTema, nota, data);
        }
    }
}
