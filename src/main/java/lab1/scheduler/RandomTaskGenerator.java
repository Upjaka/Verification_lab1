package lab1.scheduler;

import lab1.tasks.BaseTask;
import lab1.tasks.ExtendedTask;
import lab1.tasks.Task;
import lab1.tasks.TaskPriority;

import java.util.Random;

public class RandomTaskGenerator implements TaskGenerator {
    private static final Random random = new Random();
    private final int maxDuration;
    private final int minDuration;
    private final double frequencyOccurrence;
    private final double frequencyExtended;


    public RandomTaskGenerator(int minDuration, int maxDuration, double frequencyOccurrence, double frequencyExtended) {
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.frequencyOccurrence = frequencyOccurrence;
        this.frequencyExtended = frequencyExtended;
    }

    @Override
    public Task nextTask() {
        if (random.nextDouble() < frequencyOccurrence) {
            if (random.nextDouble() < frequencyExtended) {
                TaskPriority priority = TaskPriority.values()[random.nextInt(4)];
                int duration = random.nextInt(maxDuration - minDuration) + minDuration;
                return new ExtendedTask(priority, duration, random.nextInt(duration - 1) + 1,
                        random.nextInt(maxDuration) + 1);
            } else {
                TaskPriority priority = TaskPriority.values()[random.nextInt(4)];
                int duration = random.nextInt(maxDuration - minDuration) + minDuration;
                return new BaseTask(priority, duration);
            }
        }
        return null;
    }
}
