package lab1.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtendedTaskTest {
    ExtendedTask task;

    @BeforeEach
    void setUp() {
        task = new ExtendedTask(TaskPriority.HIGHEST, 5, 3, 0);
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
        task._wait();
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
        task._wait();
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
        task.start();
        task._wait();
        try {
            task.preempt();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
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
        task._wait();
        try {
            task.terminate();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.release();
        task.start();
        task.terminate();
        assertEquals(TaskState.SUSPENDED, task.getState());
    }

    @Test
    void _wait() {
        try {
            task._wait();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.activate();
        try {
            task._wait();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
        task.start();
        task._wait();
        assertEquals(TaskState.WAITING, task.getState());
        try {
            task._wait();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not running", e.getMessage());
        }
    }

    @Test
    void release() {
        try {
            task.release();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not waiting", e.getMessage());
        }
        task.activate();
        try {
            task.release();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not waiting", e.getMessage());
        }
        task.start();
        try {
            task.release();
        } catch (InvalidTaskTransitionException e) {
            assertEquals("Task is not waiting", e.getMessage());
        }
        task._wait();
        task.release();
        assertEquals(TaskState.READY, task.getState());
    }

//    @Test
//    void run() {
//    }
}