package lab1.tasks;

public abstract class AbstractTask implements Task {
    protected final int id;
    protected final TaskPriority priority;
    protected final int laborIntensity;

    protected TaskState state;
    protected int progress;

    public AbstractTask(TaskPriority priority, int laborIntensity) {
        this.id = TaskIdGenerator.getNextId();
        this.priority = priority;
        this.laborIntensity = laborIntensity;
        this.state = TaskState.SUSPENDED;
    }

    public void activate() {
        if (state != TaskState.SUSPENDED) {
            throw new InvalidTaskTransitionException("Task is already active");
        }
        state = TaskState.READY;
    }

    public void start() {
        if (state != TaskState.READY) {
            throw new InvalidTaskTransitionException("Task is not ready");
        }
        state = TaskState.RUNNING;
    }

    public void preempt() {
        if (state != TaskState.RUNNING) {
            throw new InvalidTaskTransitionException("Task is not running");
        }
        progress = 0;
        state = TaskState.READY;
    }

    public void terminate() {
        if (state != TaskState.RUNNING) {
            throw new InvalidTaskTransitionException("Task is not running");
        }
        state = TaskState.SUSPENDED;
    }

    @Override
    public int getPriority() {
        return priority.ordinal();
    }

    public TaskState getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    private static class TaskIdGenerator {
        private static int id = 0;

        public static synchronized int getNextId() {
            return ++id;
        }
    }
}
