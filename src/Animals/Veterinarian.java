package Animals;

public class Veterinarian {

    public void treatAnimal(Animal animal) {
        System.out.println("Приём у ветеринара: " + animal.getName());
        System.out.println("Описание: " + animal.getDescription());
        System.out.println();
    }

}
