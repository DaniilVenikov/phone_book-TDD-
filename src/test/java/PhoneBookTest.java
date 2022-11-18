import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PhoneBookTest {

    static PhoneBook phoneBook;
    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    private static long suiteStartTime;
    private long testStartTime;

    @BeforeAll
    public static void started(){
        System.out.println("tests started");
        suiteStartTime = System.nanoTime();
        phoneBook = new PhoneBook();
        phoneBook.add("Kolya","89193459290");
        phoneBook.add("Nika", "88005553535");
        phoneBook.add("Mag", "89003873198");

    }

    @BeforeEach
    public void init(){
        System.out.println("test started");
        testStartTime = System.nanoTime();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void finished(){
        System.out.println("test completed" + "\n"
                + "time test: " + (System.nanoTime() - testStartTime));
    }

    @AfterAll
    public static void finishedAll(){
        System.out.println("tests completed" + "\n"
                + "time tests: " + (System.nanoTime() - suiteStartTime));

    }

    @ParameterizedTest
    @MethodSource("sourceForAddTest")
    void addTest(String name, String phoneNumber, int expected){
        assertThat(phoneBook.add(name, phoneNumber), is(equalTo(expected)));
    }
    static Stream<Arguments> sourceForAddTest(){
        return Stream.of(Arguments.of("Sasha", "89203297536", 4),
                Arguments.of("Vasya","89193459090", 5),
                Arguments.of("Sasha", "89191237956", 5),
                Arguments.of("Petr", "89003876198", 6));
    }


    @ParameterizedTest
    @MethodSource("sourceForFindByNumberTest")
    void findByNumberTest(String number, String expected){
        assertThat(phoneBook.findByNumber(number), is(equalTo(expected)));
    }
    static Stream<Arguments> sourceForFindByNumberTest(){
        return Stream.of(Arguments.of("89193459290", "Kolya"),
                Arguments.of("88005553535", "Nika"),
                Arguments.of("89003873198", "Mag"),
                Arguments.of("89193459090", null));
    }


    @ParameterizedTest
    @MethodSource("sourceForFindByNameTest")
    void findByNameTest(String name, String expected){
        assertThat(phoneBook.findByName(name), is(equalTo(expected)));
    }
    static Stream<Arguments> sourceForFindByNameTest(){
        return Stream.of(Arguments.of("Kolya", "89193459290"),
                Arguments.of("Nika", "88005553535"),
                Arguments.of("Mag", "89003873198"),
                Arguments.of("Sema", null));
    }

    @Test
    void printAllNamesTest(){
        phoneBook.printAllNames();
        Assertions.assertEquals("Kolya" + '\n'
                + "Mag" + '\n'
                + "Nika" + '\n'
                + "Petr" + '\n'
                + "Sasha" + '\n'
                + "Vasya" + '\n'
                , output.toString());
    }
}
