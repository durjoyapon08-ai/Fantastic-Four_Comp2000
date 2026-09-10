# Epidemic Spread Simulation


## Project Overview

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

## Simulation Features

The simulation includes a total population of 100 people, with 5 people beginning in the infected state. Every person has their own ID, name, age, position, and movement speed. People move continuously around the simulation area and can change direction when they reach the simulation boundaries.

Healthy people can become infected when they come into close contact with infected people. Infection probability is affected by the age of the person. Infected people may recover naturally or recover by using Recovery Tents or the Hospital. Recovered people later return to the Healthy state with immunity. People who remain infected for too long may die. The simulation also displays live statistics and uses different colours to make each health state easy to identify.

## Infection Rules

The simulation uses a virus with a base spread percentage of 45%. Two people must be within the defined close-contact distance before infection can be attempted. The infection behaviour also changes depending on age. People under 30 require repeated exposure before infection can occur and have a reduced infection probability. People between the ages of 30 and 50 use the normal infection probability. People over 50 have an increased chance of becoming infected. People who have previously recovered become immune and cannot become infected again. When a healthy person becomes infected, their ID, name, age, position, and movement speed are preserved. Only their health state changes.

## Recovery System

### Natural Recovery

Natural recovery becomes possible after a person has been infected for at least 15 seconds. The chance of natural recovery depends on age. People under 30 have a higher recovery chance, people between 30 and 50 have a normal recovery chance, and people over 50 have a lower recovery chance.

### Recovery Tents

Three Recovery Tents appear at random locations in the simulation. The tents remain visible for 5 seconds, disappear for 5 seconds, and then reappear at new random locations. An infected person must remain inside a Recovery Tent for 2 continuous seconds to recover. If they leave before completing the required time, the treatment timer resets.

### Hospital

The Hospital also appears at a random location. The Hospital remains visible for 3 seconds and returns every 15 seconds. An infected person must remain inside the Hospital for 1 continuous second to recover. If they leave before completing treatment, the timer resets.

### Recovered and Immune People

When an infected person successfully recovers, their state changes to Recovered and they are displayed in blue. The simulation records the time at which recovery occurred. After 10 seconds, the recovered person returns to the Healthy state. However, the person becomes immune after recovery. This means they cannot become infected again during the simulation.

### Death System

If an infected person remains infected for 50 seconds without recovering, they are changed to the Dead state. When a person dies, their ID, name, age, and final position remain the same. Their movement speed becomes zero, so they stop moving permanently. Dead people remain visible in the simulation and are displayed in black.

## OOP Concepts Used

This project demonstrates several Object-Oriented Programming concepts:

- **Inheritance** – Healthy, Infected, Recovered, and Dead inherit from the Person class.
- **Encapsulation** – Person and other classes use private fields with public getter methods.
- **Polymorphism** – Different Person subclasses override methods such as `getStatus()`.
- **Generics** – `ArrayList<Person>` and `ArrayList<RecoveryTent>` are used to manage objects.
- **Exception Handling** – `InterruptedException` is handled in the simulation thread.

## How to Run

Compile all Java files:

```bash
javac *.java