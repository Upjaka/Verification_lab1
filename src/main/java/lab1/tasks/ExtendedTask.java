package lab1.tasks;

public class ExtendedTask extends AbstractTask{
    private final int interruptTick;
    private int timeout;

    public ExtendedTask(TaskPriority priority, int laborIntensity, int interruptTick, int timeout) {
        super(priority, laborIntensity);
        this.interruptTick = interruptTick;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        while (state!= TaskState.SUSPENDED) {
            if (state == TaskState.RUNNING) {
                if (progress == interruptTick && timeout != 0) {
                    _wait();
                } else if (progress < laborIntensity) {
                    progress++;
                } else {
                    terminate();
                }
            } else if (state == TaskState.WAITING) {
                timeout--;
                if (timeout == 0) {
                    release();
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

    public void _wait() {
        if (state != TaskState.RUNNING) {
            throw new InvalidTaskTransitionException("Task is not running");
        }
        state = TaskState.WAITING;
    }

    public void release() {
        if (state != TaskState.WAITING) {
            throw new InvalidTaskTransitionException("Task is not waiting");
        }
        state = TaskState.READY;
    }

    @Override
    public String toString() {
        return "ExtendedTask #" + id + " | Priority=" + priority + "(" + getPriority() + ") | State=" + state + " | Progress=" + progress + " | LaborIntensity=" + laborIntensity + " | InterruptTick=" + interruptTick + " | Timeout=" + timeout;    }
}
