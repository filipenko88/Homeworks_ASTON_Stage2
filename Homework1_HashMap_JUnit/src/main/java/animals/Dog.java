package animals;

public class Dog extends Animal{
    public Dog(double age, double height, double weight, String name){
        setAge(age);
        setHeight(height);
        setWeight(weight);
        setName(name);
    }
    public Dog(String name){
        this.name = name;
    }
    @Override
    public void sound() {
        System.out.println("Гав!");
    }

}
