import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Main <input_filename> <target_month> <output_filename>");
            return;
        }

        String inputFilename = args[0];
        int targetMonth = Integer.parseInt(args[1]);
        String outputFilename = args[2];

        List<Person> people = readPeopleFromFile(inputFilename);
        List<Person> filteredPeople = filterByBirthMonth(people, targetMonth);
        List<Person> sortedPeople = sortByName(filteredPeople);

        writePeopleToFile(sortedPeople, outputFilename);
    }

    private static List<Person> readPeopleFromFile(String filename) {
        List<Person> people = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            people = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            String firstName = parts[0].trim();
                            String lastName = parts[1].trim();
                            LocalDate birthDate = LocalDate.parse(parts[2].trim());
                            return new Person(firstName, lastName, birthDate);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return people;
    }

    private static List<Person> filterByBirthMonth(List<Person> people, int targetMonth) {
        return people.stream()
                .filter(person -> person.getBirthDate().getMonthValue() == targetMonth)
                .collect(Collectors.toList());
    }

    private static List<Person> sortByName(List<Person> people) {
        people.sort(Comparator.comparing(Person::getFirstName).thenComparing(Person::getLastName));
        return people;
    }

    private static void writePeopleToFile(List<Person> people, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Person person : people) {
                writer.write(person.getFirstName() + " " + person.getLastName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
