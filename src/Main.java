import Animals.*;
import Bank.CheckingAccount;
import Bank.Client;
import Bank.SavingsAccount;
import Beverage.Beverage;
import Cars.Car;
import Cars.Engine;
import Cars.Wheel;
import DoubleInterface.Circle;
import Employee.Employee;
import Movie.Movie;
import Priority.Priority;
import Shop.Customer;
import Shop.Product;
import Song.Song;
import Students.Aspirant;
import Students.Student;
import Game.*;
import Beverage.*;
import Computer.*;
import Employee.*;
import Movie.*;
import Song.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Priority.*;

public class Main {
    public static void main(String[] args) {
        return;



//        Map<String, Integer> marks = new HashMap<>();
//        Map<String, Integer> ages = new HashMap<>();
//
//        marks.put("Влад", 100);
//        marks.put("Эдуард", 90);
//        marks.put("Пельмень", 20);
//
//        ages.put("Влад", 25);
//        ages.put("Эдуард", 26);
//        ages.put("Пельмень", 20);
//
//        List<Map<String, Object>> list = marks.entrySet().stream()
//                .filter(entry -> ages.containsKey(entry.getKey()))
//                .map(stringIntegerEntry -> {
//                    Map<String, Object> student = new HashMap<>();
//                    student.put("name", stringIntegerEntry.getKey());
//                    student.put("age", ages.get(stringIntegerEntry.getKey()));
//                    student.put("mark", stringIntegerEntry.getValue());
//                    return student;
//                })
//                .filter(student -> (Integer) student.get("mark") > 80 && (Integer) student.get("age") > 23)
//                .sorted((Comparator.comparingInt(s -> (Integer) s.get("mark"))))
//                .toList();
//
//        System.out.println(list);


    }



    public static void Song() {
        Set<Song> songs = new HashSet<>();

        songs.add(new Song("Back in Black", "AC/DC", Genre.ROCK));
        songs.add(new Song("Poker Face", "Lady Gaga", Genre.POP));
        songs.add(new Song("Lose Yourself", "Eminem", Genre.RAP));
        songs.add(new Song("Come Away With Me", "Norah Jones", Genre.JAZZ));
        songs.add(new Song("Back in Black", "AC/DC", Genre.ROCK));
        songs.add(new Song("Another Brick in the Wall", "Pink Floyd", Genre.ROCK));
        songs.add(new Song("Lose Yourself", "Eminem", Genre.RAP));

        Map<Genre, List<Song>> playlist = new HashMap<>();

        for (Song song : songs) {
            playlist
                    .computeIfAbsent(song.getGenre(), genre -> new ArrayList<>())
                    .add(song);
        }

        System.out.println("🎵 Музыкальный плейлист по жанрам:\n");
        for (Genre genre : Genre.values()) {
            List<Song> genreSongs = playlist.getOrDefault(genre, new ArrayList<>());
            System.out.println(genre.getEmoji() + " " + genre.name() + ":");
            for (Song s : genreSongs) {
                System.out.println(" - " + s);
            }
            System.out.println();
        }

    }

    public static void priority() {
        Set<WorkTask> taskSet = new HashSet<>();

        taskSet.add(new WorkTask("Сдать отчёт", Priority.HIGH, Status.NEW));
        taskSet.add(new WorkTask("Сдать отчёт", Priority.HIGH, Status.DONE)); //
        taskSet.add(new WorkTask("Починить баг", Priority.URGENT, Status.IN_PROGRESS));
        taskSet.add(new WorkTask("Обед", Priority.LOW, Status.NEW));
        taskSet.add(new WorkTask("Обед", Priority.LOW, Status.NEW)); //

        System.out.println("📋 Уникальные задачи:");
        for (WorkTask task : taskSet) {
            System.out.println(task.toString());
        }
    }

    public static void movie() {
        Movie[] movies = {
                new Movie("Начало", new Director("Нолан", 1970), 8.8),
                new Movie("Начало", new Director("Нолан", 1970), 8.8),
                new Movie("Интерстеллар", new Director("Нолан", 1970), 8.6),
                new Movie("Гарри Поттер", new Director("Роулинг", 1965), 9.1),
                new Movie("Гарри Поттер", new Director("Роулинг", 1965), 9.1),
                new Movie("Аватар", new Director("Кэмерон", 1954), 9.0)
        };

        int matchCount = 0;

        for (int i = 0; i < movies.length; i++) {
            for (int j = i + 1; j < movies.length; j++) {
                if (movies[i].equals(movies[j])) {
                    System.out.println("🎯 Совпадение: [" + i + "] и [" + j + "]");
                    matchCount++;
                }

            }
        }
        System.out.println("\nВсего совпадений: " + matchCount);


    }

    public static void doubleInterface() {
        Circle circle = new Circle("Круг", 25);

        circle.draw();
        circle.scale(2);
        circle.draw();
    }

    public static void shop() {

        Customer customer = new Customer("Эдуард");

        customer.addToCart(new Product("Хлеб", 50));
        customer.addToCart(new Product("Вода", 20));
        customer.addToCart(new Product("Соль", 10));
        customer.addToCart(new Product("Сахар", 15));

        customer.checkout();
    }

    public static void cars() {

        Car car = new Car ("Ferrari", new Engine("V6", 2500), new Wheel(30, "summer"));
        Car car1 = new Car ("Porsche", new Engine("V7", 3500), new Wheel(20, "winter"));
        Car car2 = new Car ("Audi", new Engine("V8", 2000), new Wheel(35, "semi"));
        Car car3 = new Car ("BMW", new Engine("V9", 4500), new Wheel(25, "diff seasons"));

        System.out.println(car.getInfo());
        System.out.println(car1.getInfo());
        System.out.println(car2.getInfo());
        System.out.println(car3.getInfo());
    }

    public static void employee() {

        Employee[] staff = {
                new FullTimeEmployee("Ольга", 120000),
                new PartTimeEmployee("Дмитрий", 80, 600),
                new FullTimeEmployee("Ирина", 95000),
                new PartTimeEmployee("Антон", 50, 750)
        };

        System.out.println("💰 Отчёт по зарплатам:");
        for (Employee emp : staff) {
            System.out.println(emp.getName() + ": " + emp.calculateSalary() + " ₽");
        }

    }


    public static void computer() {
        Computer computer = new Computer(new Processor("Intel i5"), new Memory(16));
        System.out.println(computer.spec());

    }

    public static void beverage() {
        Beverage[] menu = {new Tea("Мятный чай"),
                new Coffee("Americano") };

        for (Beverage beverage : menu) {
            System.out.println("• " + beverage.getInfo() + "₽");

        }
    }

    public static void bank() {

        Client eduard = new Client("Eduard");
        Client nadezhda = new Client("Nadezhda");

        CheckingAccount accEduard1 = new CheckingAccount("Eduard checking", 20000);
        SavingsAccount accEduard2 = new SavingsAccount("Eduard Savings", 100000, 3);

        CheckingAccount accNadezhda1 = new CheckingAccount("Nadezhda checking", 10000);
        SavingsAccount accNadezhda2 = new SavingsAccount("Nadezhda saving", 50000, 3);

        eduard.addAccount(accEduard1);
        eduard.addAccount(accEduard2);

        nadezhda.addAccount(accNadezhda1);
        nadezhda.addAccount(accNadezhda2);

        eduard.transfer(accEduard1, accNadezhda1, 1000);
        nadezhda.transfer(accNadezhda1, accNadezhda2, 1000);

        System.out.println(accEduard1.getInfo());
        System.out.println(accNadezhda1.getInfo());
        System.out.println(accNadezhda2.getInfo());
    }

    public static void animal() {
        Animal[] animals = {
                new Dog("Мурзик"),
                new Cat("Персик"),
                new Bear("Гризли")
        };

        Veterinarian vet = new Veterinarian();
        for (Animal animal : animals) {
            vet.treatAnimal(animal);

        }

        for (Animal animal : animals) {
            animal.makeNoise();
            animal.eat();
            System.out.println();
        }
    }

    public static void student() {

        Student[] students = new Student[2];
        students[0] = new Student("Edward", "Ezersky", 2014, 5);
        students[1] = new Aspirant("Vlad", "Ponomarenko", 1337, 2, "PussySlayer");

        System.out.println("У " + students[0].getFirstName() + " Стипендия составляет: " + students[0].getScholarship() + " рублей");
        System.out.println("У " + students[1].getFirstName() + " Стипендия составляет: " + students[1].getScholarship() + " рублей");
    }

    public static void game() {

        Player[] hero = { new Mage("Frozen", 100, 1000, 100, new Staff(20)),
                new Warrior("Arya", 100, 1000, 50, new Bow(20, 100)),
                new Healer("Quenn", 100, 1000, 200)
        };

        for (Player p : hero) {
            if (p instanceof Healer) {
                ((Healer) p).heal(30);
            }
        }
    }

}
