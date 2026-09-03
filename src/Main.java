import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);

        System.out.println("--Life--");

        System.out.println("Enter patient name: ");
        String name = input.nextLine();

        System.out.println("Enter patient's age: ");
        int age = input.nextInt();
        input.nextLine();


        System.out.println("Are you infected? (yes/no): ");
        String infectedAnswer = input.nextLine();

        if(infectedAnswer.equalsIgnoreCase("no")){
            Healthy Person  = new Healthy(name, age);

            System.out.println();
            System.out.println("--Result__");
            System.out.println(Person);
        }
        else if (infectedAnswer.equalsIgnoreCase("yes")){
            System.out.print("Enter zoombie virus percentage: ");
            int percentage = input.nextInt();

            Virus zombieVirus = new Virus("Zombie Virus", percentage);
            
            Infected infectedPerson = new Infected(name, age, zombieVirus);

            Person result = infectedPerson.checkCondition();

            System.out.println();
            System.out.println("--Result--");
            System.out.println(result);
        }
        else {
            System.out.println("Please enter yes or no.");
        }
        input.close();
    }
}
