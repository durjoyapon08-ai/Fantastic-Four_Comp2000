# Epidemic Spread Simulation

This project is a simple Java-based Epidemic Spread Simulation developed for COMP2000. The main goal of the project is to demonstrate how an infection can spread through a moving population while using Object-Oriented Programming concepts.

The simulation contains a fixed population of 100 people, with a small number of people starting as infected. Each person moves randomly around the simulation area. When an infected person comes very close to a healthy person, there is a chance that the healthy person will become infected.

Age also affects the infection risk. People over 50 have a higher chance of becoming infected, while people under 30 have a lower chance and need repeated close contact before infection is more likely to occur.

The simulation also includes Recovery Tents and a Hospital. Infected people can recover by staying inside these areas for a certain amount of time. Some infected people can also recover naturally. Recovered people are shown separately before eventually becoming healthy again. If an infected person does not recover for a long period of time, they may die.

The program displays live statistics showing the number and percentage of Healthy, Infected, Recovered, and Dead people.

## Color Guide

- Green = Healthy
- Red = Infected
- Blue = Recovered
- Black = Dead
- Orange = Recovery Tent
- White = Hospital

## OOP Concepts Used

This project demonstrates several Object-Oriented Programming concepts:

- **Inheritance** – Healthy, Infected, Recovered, and Dead inherit from the Person class.
- **Encapsulation** – Person and other classes use private fields with public getter methods.
- **Polymorphism** – Different Person subclasses override methods such as `getStatus()`.
- **Generics** – `ArrayList<Person>` and `ArrayList<RecoveryTent>` are used to manage objects.
- **Exception Handling** – `InterruptedException` is handled in the simulation thread.

## Main Classes

- `Main` – Starts the program and opens the simulation window.
- `SimulationPanel` – Controls movement, infection, recovery, buildings, statistics, and drawing.
- `Person` – Parent class for all people in the simulation.
- `Healthy` – Represents a healthy person.
- `Infected` – Represents an infected person.
- `Recovered` – Represents a recovered person.
- `Dead` – Represents a dead person.
- `Virus` – Stores virus information and infection rate.
- `Building` – Parent class for treatment buildings.
- `Hospital` – Helps infected people recover.
- `RecoveryTent` – Provides another recovery area.

## How to Run

Compile all Java files:

```bash
javac *.java