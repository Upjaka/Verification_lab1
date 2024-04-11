package lab1.scheduler;

import lab1.tasks.BaseTask;
import lab1.tasks.Task;
import lab1.tasks.TaskPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskQueueTest {
    TaskQueue queue;
    Task task;
    Task task1;

    @BeforeEach
    void setUp() {
        queue = new TaskQueue(1);
        task = new BaseTask(TaskPriority.LOW, 1);
        task1 = new BaseTask(TaskPriority.MEDIUM, 1);
    }

    @Test
    void addTask() {
        queue.addTask(task);
        assertEquals(1, queue.getLength());
    }

    @Test
    void getLength() {
        assertEquals(0, queue.getLength());
        queue.addTask(task);
        assertEquals(1, queue.getLength());
        queue.addTask(task1);
        assertEquals(1, queue.getLength());
    }

    @Test
    void getNextTask() {
        assertNull(queue.getNextTask());
        queue.addTask(task);
        assertEquals(task, queue.getNextTask());
    }

    @Test
    void peekNextTask() {
        assertNull(queue.peekNextTask());
        queue.addTask(task);
        assertEquals(task, queue.peekNextTask());
    }

    @Test
    void getNthLength() {
        assertEquals(0, queue.getNthLength(0));
        assertEquals(0, queue.getNthLength(1));
        assertEquals(0, queue.getNthLength(2));
        assertEquals(0, queue.getNthLength(3));
        queue.addTask(task);
        assertEquals(1, queue.getNthLength(0));
        assertEquals(0, queue.getNthLength(1));
        assertEquals(0, queue.getNthLength(2));
        assertEquals(0, queue.getNthLength(3));
        queue.getNextTask();
        queue.addTask(task1);
        assertEquals(0, queue.getNthLength(0));
        assertEquals(1, queue.getNthLength(1));
        assertEquals(0, queue.getNthLength(2));
        assertEquals(0, queue.getNthLength(3));
        queue.getNextTask();
        queue.addTask(new BaseTask(TaskPriority.HIGH, 1));
        assertEquals(0, queue.getNthLength(0));
        assertEquals(0, queue.getNthLength(1));
        assertEquals(1, queue.getNthLength(2));
        assertEquals(0, queue.getNthLength(3));
        queue.getNextTask();
        queue.addTask(new BaseTask(TaskPriority.HIGHEST, 1));
        assertEquals(0, queue.getNthLength(0));
        assertEquals(0, queue.getNthLength(1));
        assertEquals(0, queue.getNthLength(2));
        assertEquals(1, queue.getNthLength(3));
    }

    @Test
    void getAllTasks() {
        assertEquals(0, queue.getAllTasks().size());
        queue.addTask(task);
        assertEquals(1, queue.getAllTasks().size());
        assertEquals(task, queue.getAllTasks().get(0));
        TaskQueue queue1 = new TaskQueue(2);
        queue1.addTask(task);
        queue1.addTask(task1);
        assertEquals(2, queue1.getAllTasks().size());
        assertEquals(task1, queue1.getAllTasks().get(0));
        assertEquals(task, queue1.getAllTasks().get(1));
    }
}