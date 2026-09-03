public class Person{
    private static int nextId =1;

    private int id;
    private String name;
    private int age;
    private int x;
    private int y;

    public Person(String name, int age, int x, int y){
        this.id = nextId;
        nextId++;

        this.name = name;
        this.age = age;
        this.x = x;
        this.y = y;
    }

    public Person(int id, String name, int age, int x, int y){
        this.id = id;
        this.name = name;
        this.age = age;
        this.x = x;
        this.y = y;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getStatus(){
        return "Person";
    }


    @Override
    public String toString(){
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Status: " + getStatus();
    }
}