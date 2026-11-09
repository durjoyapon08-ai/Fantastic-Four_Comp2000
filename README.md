# Epidemic Spread Simulation

**COMP2000 · Fantastic Four · Week 13 Upgrade**  
**Durjoy Paul Apon**

Our team built this Java desktop simulation to explore how an infection
spreads through a moving population. As the simulation runs, you can watch
people become infected or recover, see who has immunity, and follow the
population counts on screen.

## How to run it

You need a Java Development Kit (JDK) and a graphical desktop. The project
does not require any external libraries.

Open a terminal in the project folder and run:

```bash
mkdir -p bin
javac -d bin src/*.java && java -cp bin Main
```

By default, the simulation uses an age-based infection rule and counts
health statuses sequentially. You can change either setting independently:

| Command | Infection rule | Status counting |
| --- | --- | --- |
| `java -cp bin Main` | Age-based | Sequential |
| `java -cp bin Main fixed` | Fixed chance | Sequential |
| `java -cp bin Main parallel` | Age-based | Parallel |
| `java -cp bin Main fixed parallel` | Fixed chance | Parallel |

### What the colours mean

| Colour | Meaning |
| --- | --- |
| Green | Healthy |
| Cyan | Healthy with immunity; included in the Healthy count |
| Red | Infected |
| Blue | Recovered |
| Black | Dead |
| White | Hospital |
| Orange | Recovery tent |

## What I changed in Week 13

- **Switchable infection rules.** I used the Strategy pattern to separate
  the infection rule from the simulation loop. `Main` chooses between the
  age-based rule and a fixed-chance rule written as a lambda. The same
  simulation loop works with either rule.
- **Input validation and movement.** I added checks for invalid values when
  creating viruses, people, and buildings. I also fixed the movement logic
  so people stay inside the simulation area when they reach an edge.
- **Safer status counting.** I kept simulation updates and drawing on the
  UI thread. For parallel counting, worker threads read an immutable copy of
  the status strings without accessing the live `Person` objects.

## Sequential vs. parallel counting

I tested both counting modes on the same list of 100 statuses: 70 Healthy,
15 Infected, 10 Recovered, and 5 Dead. Both modes returned the expected counts.

To run the benchmark from the project folder:

```bash
mkdir -p bin
javac -d bin src/*.java tools/StatisticsBenchmark.java &&
java -cp bin StatisticsBenchmark
```

| Mode | Median time to count all 100 statuses |
| --- | ---: |
| Sequential | 0.71 µs |
| Parallel | 18.91 µs |

On my Mac, sequential counting was faster for this input. For a small list,
splitting the work and combining the results can take more time than counting
the whole list in one stream. I kept sequential counting as the default and
left parallel counting as an option for comparison. The benchmark measures
status counting only, so these results do not show how fast the full simulation
runs.