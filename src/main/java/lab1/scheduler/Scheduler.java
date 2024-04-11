package lab1.scheduler;

import lab1.tasks.Task;
import lab1.tasks.TaskState;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);


    private static int ticks = 0;
    private final TaskQueue taskQueue;
    private final List<Task> waitingBuffer;
    private final TaskGenerator taskGenerator;
    private final List<Task> overflowBuffer;
    private final List<Task> completedTasks;
    private int numTasks;
    private Task runningTask;


    public Scheduler(int numTasks, int queueSize, TaskGenerator taskGenerator) {
        this.numTasks = numTasks;
        taskQueue = new TaskQueue(queueSize);
        waitingBuffer = new ArrayList<>(numTasks);
        overflowBuffer = new ArrayList<>(numTasks);
        completedTasks = new ArrayList<>(numTasks);
        this.taskGenerator = taskGenerator;
    }

    @Override
    public void run() {
        while (numTasks != 0 || taskQueue.getLength() != 0 || !waitingBuffer.isEmpty() || runningTask != null) {
            Task task = taskGenerator.nextTask();
            if (task != null && numTasks != 0) {
                if (taskQueue.addTask(task)) {
                    task.activate();
                } else {
                    overflowBuffer.add(task);
                }
                numTasks--;
            }
            if (runningTask == null) {
                if (taskQueue.peekNextTask() != null) {
                    runningTask = taskQueue.getNextTask();
                    runningTask.start();
                    Thread thread = new Thread(runningTask);
                    thread.start();
                }
            } else {
                switch (runningTask.getState()) {
                    case SUSPENDED:
                        completedTasks.add(runningTask);
                        runningTask = taskQueue.getNextTask();
                        if (runningTask != null) {
                            runningTask.start();
                            Thread thread = new Thread(runningTask);
                            thread.start();
                        }
                        break;
                    case WAITING:
                        waitingBuffer.add(runningTask);
                        runningTask = taskQueue.getNextTask();
                        if (runningTask != null) {
                            runningTask.start();
                            Thread thread = new Thread(runningTask);
                            thread.start();
                        }
                        break;
                    default:
                        if (taskQueue.peekNextTask() != null) {
                            if (runningTask.getPriority() < taskQueue.peekNextTask().getPriority()) {
                                runningTask.preempt();
                                taskQueue.addTask(runningTask);
                                runningTask = taskQueue.getNextTask();
                                runningTask.start();
                                Thread thread1 = new Thread(runningTask);
                                thread1.start();
                            }
                        }
                        break;
                }
            }

            List<Task> removeBuffer = new ArrayList<>();
            for (Task waitingTask : waitingBuffer) {
                if (waitingTask.getState() == TaskState.READY) {
                    taskQueue.addTask(waitingTask);
                    removeBuffer.add(waitingTask);
                }
            }
            waitingBuffer.removeAll(removeBuffer);

            if (runningTask == null) {
                if (taskQueue.peekNextTask() != null) {
                    runningTask = taskQueue.getNextTask();
                    runningTask.start();
                    Thread thread = new Thread(runningTask);
                    thread.start();
                }
            }

            logger.info("                TICKS: {}", ++ticks);
            logger.info("RUNNING TASK: {}", (runningTask == null) ? "none" : runningTask.toString());
            logger.info("QUEUE LENGTH: {}", taskQueue.getLength());
            List<Task> tasks = taskQueue.getAllTasks();
            for (int i = 0; i < tasks.size(); i++) {
                logger.info("QUEUE[{}]: {}", i, tasks.get(i).toString());
            }
            logger.info("WAITING BUFFER SIZE: {}", waitingBuffer.size());
            for (int i = 0; i < waitingBuffer.size(); i++) {
                logger.info("WAITING BUFFER[{}]: {}", i, waitingBuffer.get(i).toString());
            }
            logger.info("COMPLETED TASKS: {}", completedTasks.size());
            for (int i = 0; i < completedTasks.size(); i++) {
                logger.info("COMPLETED TASKS[{}]: {}", i, completedTasks.get(i).toString());
            }
            logger.info("OVERFLOW BUFFER SIZE: {}\n", overflowBuffer.size());


            try {
                Thread.sleep(202);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
