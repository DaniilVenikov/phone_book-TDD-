import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PhoneBookTest {

    static PhoneBook phoneBook;

    private static long suiteStartTime;
    private long testStartTime;

    @BeforeAll
    public static void started(){
        System.out.println("tests started");
        suiteStartTime = System.nanoTime();
        phoneBook = new PhoneBook();
    }

    @BeforeEach
    public void init(){
        System.out.println("test started");
        testStartTime = System.nanoTime();
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
        return Stream.of(Arguments.of("Sasha", "89203297536", 1),
                Arguments.of("Vasya","89193459090", 2),
                Arguments.of("Sasha", "89191237956", 2),
                Arguments.of("Petr", "89003876198", 3));
    }


    @ParameterizedTest
    @MethodSource("sourceForFindByNumberTest")
    void findByNumberTest(String number, String expected){
        assertThat(phoneBook.findByNumber(number), is(equalTo(expected)));
    }
    static Stream<Arguments> sourceForFindByNumberTest(){
        return Stream.of(Arguments.of("89193459090", "Vasya"),
                Arguments.of("89191237956", "Sasha"),
                Arguments.of("89003876198", "Petr"));
    }

}
