package lab1.scheduler;

import lab1.tasks.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TaskQueue {
    private final Queue<Task>[] taskQueues;
    private final int capacity;
    private int length;

    public TaskQueue(int capacity) {
        this.capacity = capacity;
        this.taskQueues = new Queue[4];
        for (int i = 0; i < 4; i++) {
            this.taskQueues[i] = new LinkedList<>();
        }
    }

    public boolean addTask(Task task) {
        if (length < capacity) {
            taskQueues[task.getPriority()].offer(task);
            length++;
            return true;
        } else return false;
    }

    public Task getNextTask() {
        for (int i = 3; i >= 0; i--) {
            if (!taskQueues[i].isEmpty()) {
                length--;
                return taskQueues[i].poll();
            }
        }
        return null;
    }

    public Task peekNextTask() {
        for (int i = 3; i >= 0; i--) {
            if (!taskQueues[i].isEmpty()) {
                return taskQueues[i].peek();
            }
        }
        return null;
    }

    public int getLength() {
        return length;
    }

    public int getNthLength(int n) {
        return taskQueues[n].size();
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new LinkedList<>();
        for (int i = 3; i != 0; i--) {
            tasks.addAll(taskQueues[i]);
        }
        return tasks;
    }

}
