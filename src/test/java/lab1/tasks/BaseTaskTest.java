package lab1.tasks;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class BaseTaskTest {
    Task task;

    @BeforeEach
    void setUp() {
        task = new BaseTask(TaskPriority.HIGHEST, 5);
    }

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

//    @org.junit.jupiter.api.Test
//    void run() {
//
//    }
}