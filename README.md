# Epidemic Spread Simulation

This is a Java project for COMP2000.

The program checks a person's health condition based on a zombie virus percentage.

## How the Program Works

The user enters:

- Name
- Age
- Whether the person is infected
- Virus percentage

The result is:

- 0–20% → Recovered
- 21–70% → Infected
- Above 70% → Dead
- Not infected → Healthy

The program can store up to 15 people.

## Main Classes

### Person
Stores basic information such as:
- ID
- Name
- Age
- Position

### Healthy
Represents a healthy person.

### Infected
Represents an infected person and checks the virus percentage.

### Recovered
Represents a recovered person.

### Dead
Represents a dead person.

### Virus
Stores the virus name and percentage.

### Main
Runs the program, takes user input, and stores all people.

## OOP Concepts Used

This project uses:

- Inheritance
- Method overriding
- Encapsulation
- Polymorphism
- Generics
- Exception handling

For example, `Healthy`, `Infected`, `Recovered`, and `Dead` all extend the `Person` class.

## Exception Handling

The program uses `try-catch` when the user enters a number.

If the user enters something invalid, the program shows an error message and asks again.

## How to Run in VS Code

1. Open the project folder in VS Code.
2. Open `Main.java`.
3. Click **Run**.
4. Enter the information in the terminal.
5. The program will show the patient's result.

You can also run it from the terminal:

```bash
javac *.java
java Main