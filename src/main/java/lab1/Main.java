package lab1;

import lab1.scheduler.RandomTaskGenerator;
import lab1.scheduler.Scheduler;

public class Main {

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(5, 10, new RandomTaskGenerator(2, 5, 0.5, 1));
        scheduler.run();
    }
}
