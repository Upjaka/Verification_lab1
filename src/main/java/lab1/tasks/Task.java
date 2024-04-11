package lab1.tasks;

public interface Task extends Runnable {
    void activate();

    void start();

    void preempt();

    void terminate();

    int getPriority();

    TaskState getState();
}
