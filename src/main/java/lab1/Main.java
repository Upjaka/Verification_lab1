package lab1;

import lab1.scheduler.RandomTaskGenerator;
import lab1.scheduler.Scheduler;

public class Main {
    private static final int NUM_TASKS = 5;
    private static final int QUEUE_SIZE = 10;
    private static final int MIN_DURATION = 2;
    private static final int MAX_DURATION = 5;
    private static final double FREQUENCY_OCCURRENCE = 0.5;
    private static final double FREQUENCY_EXTENDED = 0.5;

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(NUM_TASKS, QUEUE_SIZE, new RandomTaskGenerator(MIN_DURATION, MAX_DURATION, FREQUENCY_OCCURRENCE, FREQUENCY_EXTENDED));
        scheduler.run();
    }
}
