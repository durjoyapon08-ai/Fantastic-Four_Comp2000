import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Person> people = new ArrayList<>();

        System.out.println("-- LIFE ---");

        boolean addAnother = true;

        while (addAnother) {

            String name = readName(input,"Enter patient name: ");

            int age = readNumber(input,"Enter patient's age: ",1,120);

            String infectedAnswer = readYesNo(input,"Is the patient infected? (yes/no): ");

            int index = people.size();

            int column = index % 5;
            int row = index / 5;

            int x = 60 + (column * 110);
            int y = 120 + (row * 110);

            Person result;

            if (infectedAnswer.equalsIgnoreCase("yes")) {

                int percentage = readNumber(input,"Enter zombie virus percentage (0-100): ",0,100);

                Virus virus = new Virus("Zombie Virus",percentage);

                Infected infectedPerson = new Infected(name,age,x,y,virus);

                result = infectedPerson.checkCondition();

            } else {
        result = new Healthy(name,age,x,y);
            }

            people.add(result);

            System.out.println();
            System.out.println("=== RESULT ===");
            System.out.println(result);
            System.out.println();

            if (people.size() >= 15) {

                System.out.println("Maximum 15 patients reached.");

                addAnother = false;

            } else {
        String anotherAnswer =readYesNo(input,"Add another patient? (yes/no): ");

                addAnother =anotherAnswer.equalsIgnoreCase("yes");

                System.out.println();
            }
        }

        System.out.println("=== PEOPLE CHECKED ===");

        for (int i = 0;i < people.size(); i++) {

            System.out.println(people.get(i));
        }

        input.close();
    }


    public static String readName(Scanner input,String message) {

        while (true) {

            System.out.print(message);
                String name =input.nextLine();

            if (!name.trim().equals("")) {
                return name;
            }
        System.out.println("Name cannot be empty.");
        }
    }


    public static int readNumber(Scanner input,String message,int minimum,int maximum) {

        while (true) {

            try {

                System.out.print(message);

                int number = Integer.parseInt(input.nextLine() );

                if (number >= minimum && number <= maximum) {

                    return number;
                }

                System.out.println("Please enter a number between " +minimum +" and " +maximum +".");

            } catch (NumberFormatException e) {

                System.out.println("Invalid number. Please try again.");
            }
        }
    }


    public static String readYesNo(Scanner input,String message) {

        while (true) {

            System.out.print(message);

            String answer = input.nextLine();

            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {

                return answer;
            }
System.out.println("Please enter yes or no.");
        }
    }
}