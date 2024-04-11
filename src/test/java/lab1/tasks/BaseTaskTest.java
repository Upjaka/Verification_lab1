package lab1.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseTaskTest {
    Task task;

    @BeforeEach
    void setUp() {
        task = new BaseTask(TaskPriority.LOW, 5);
    }

    @Test
    void activate() {
        task.activate();
        assertEquals(TaskState.READY, task.getState());
        try {
            task.activate();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is already active", e.getMessage());
        }
        task.start();
        try {
            task.activate();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is already active", e.getMessage());
        }
    }

    @Test
    void start() {
        try {
            task.start();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not ready", e.getMessage());
        }
        task.activate();
        task.start();
        assertEquals(TaskState.RUNNING, task.getState());
        try {
            task.start();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not ready", e.getMessage());
        }
    }

    @Test
    void preempt() {
        try {
            task.preempt();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.activate();
        try {
            task.preempt();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.start();
        task.preempt();
        assertEquals(TaskState.READY, task.getState());
    }

    @Test
    void terminate() {
        try {
            task.terminate();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.activate();
        try {
            task.terminate();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.start();
        task.terminate();
        assertEquals(TaskState.SUSPENDED, task.getState());
    }

    @Test
    void run() throws InterruptedException {
        task = new BaseTask(TaskPriority.LOW, 3);
        task.activate();
        task.start();
        Thread thread = new Thread(task);
        thread.start();
        Thread.sleep(100);
        assertEquals(TaskState.RUNNING, task.getState());
        assertEquals(1, task.getProgress());
        Thread.sleep(200);
        assertEquals(TaskState.RUNNING, task.getState());
        assertEquals(2, task.getProgress());
        Thread.sleep(200);
        assertEquals(TaskState.RUNNING, task.getState());
        assertEquals(3, task.getProgress());
        Thread.sleep(200);
        assertEquals(TaskState.SUSPENDED, task.getState());
    }

    @Test
    void getPriority() {
        assertEquals(0, task.getPriority());
        task = new BaseTask(TaskPriority.MEDIUM, 3);
        assertEquals(1, task.getPriority());
        task = new BaseTask(TaskPriority.HIGH, 3);
        assertEquals(2, task.getPriority());
        task = new BaseTask(TaskPriority.HIGHEST, 3);
        assertEquals(3, task.getPriority());
    }
}