public class StrategyChecks {

    public static void main(String[] args) {
        Virus virus = new Virus("Test virus", 45);

        Healthy younger = person(29);
        InfectionStrategy lowRoll = new AgeBasedInfectionStrategy(() -> 0.24);
        check(!lowRoll.shouldInfect(younger, virus),
                "The first contact must not infect a person under 30.");
        check(lowRoll.shouldInfect(younger, virus),
                "The second contact can infect a person under 30.");
        InfectionStrategy higherRoll = new AgeBasedInfectionStrategy(() -> 0.26);
        check(!higherRoll.shouldInfect(younger, virus),
                "A 26% roll must miss the reduced 25% infection chance.");

        InfectionStrategy sameRoll = new AgeBasedInfectionStrategy(() -> 0.60);
        check(!sameRoll.shouldInfect(person(50), virus),
                "Age 50 must use the base infection chance.");
        check(sameRoll.shouldInfect(person(51), virus),
                "Age 51 must use the increased infection chance.");
        check(lowRoll.shouldInfect(person(30), virus),
                "Age 30 must not require a second contact.");

        InfectionStrategy upperLimit = new AgeBasedInfectionStrategy(() -> 0.90);
        check(!upperLimit.shouldInfect(person(51), new Virus("Upper limit", 100)),
                "A roll equal to the 90% cap must not infect.");
        InfectionStrategy lowerLimit = new AgeBasedInfectionStrategy(() -> 0.04);
        check(lowerLimit.shouldInfect(person(30), new Virus("Lower limit", 0)),
                "A 4% roll must infect at the age-based rule's 5% floor.");

        System.out.println("All 8 infection strategy checks passed.");
    }

    private static Healthy person(int age) {
        return new Healthy("Test person", age, 0, 150, 0, 0);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
