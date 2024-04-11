package lab1.tasks;

public class BaseTask extends AbstractTask {

    public BaseTask(TaskPriority priority, int laborIntensity) {
        super(priority, laborIntensity);
    }

    @Override
    public void run() {
        while (state != TaskState.SUSPENDED) {
            if (state == TaskState.RUNNING) {
                if (progress < laborIntensity) {
                    progress++;
                } else {
                    terminate();
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "BaseTask #" + id + " | Priority=" + priority + "(" + getPriority() + ") | State=" + state + " | Progress=" + progress + " | LaborIntensity=" + laborIntensity;
    }
}
