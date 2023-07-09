package LabExercises.Audicija;

import java.util.*;


class Participant {
    String city;
    String code;
    String name;
    int age;


    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }

}

class Audition {
    Map<String, List<Participant>> participantsByCity;
    Map<String,Set<String>> uniqueCodesByCity;


    public Audition() {
        uniqueCodesByCity = new HashMap<>();
        participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant participant = new Participant(city, code, name, age);

        if (uniqueCodesByCity.get(city)!=null && uniqueCodesByCity.get(city).contains(code))
            return;

        uniqueCodesByCity.putIfAbsent(city,new HashSet<>());
        uniqueCodesByCity.get(city).add(code);

        participantsByCity.putIfAbsent(city, new ArrayList<>());
        participantsByCity.get(city).add(participant);
    }

    public void listByCity(String city) {
        List<Participant> participantsOfACity = participantsByCity.get(city);

        participantsOfACity.stream().sorted(Comparator.comparing(Participant::getName)
                        .thenComparing(Participant::getAge).thenComparing(Participant::getCode))
                .forEach(System.out::println);
    }
}


public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}