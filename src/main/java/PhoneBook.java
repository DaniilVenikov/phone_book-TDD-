import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PhoneBook {
    private Map<String, String> phoneBook;

    public PhoneBook(){
        phoneBook = new HashMap<>();
    }

    public int add(String name, String phoneNumber){
        phoneBook.put(name, phoneNumber);
        return phoneBook.size();
    }

    public String findByNumber(String number){
        Optional<String> optional = phoneBook.entrySet()
                .stream()
                .filter(entry -> number.equals(entry.getValue()))
                .map(Map.Entry :: getKey)
                .findFirst();
        return optional.orElse(null);
    }

    public String findByName(String name){
        return phoneBook.getOrDefault(name, null);
    }

    public void printAllNames(){
        phoneBook.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey()));
    }
}
