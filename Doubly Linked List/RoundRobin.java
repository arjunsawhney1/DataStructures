/*
 * NAME: Arjun Sawhney
 */

/**
 * A class that implements a Round-Robin Task Scheduler using DoublyLinkedLists.
 *
 * @author Arjun Sawhney
 * @since 06/03/2020
 */
public class RoundRobin {

    // constants
    private static final int DEFAULT_QUANTUM =  3;
    private static final int ARROW_LENGTH =  4;
    private static final String TASK_HANDLED = "All tasks are already handled.";

    // instance variables
    private DoublyLinkedList<Task> waitlist, finished;
    private int quantum, burstTime, waitTime;

    /**
     * This constructor will call the constructor below with the default quantum declared as a
     * constant named DEFAULT_QUANTUM.
     *
     * @param toHandle: The list of Tasks to be handled by the Round Robin Scheduler
     */
    public RoundRobin(Task[] toHandle) {
        // Calling second constructor with default value of quantum
        this(DEFAULT_QUANTUM, toHandle);
    }

    /**
     * This constructor initializes the instance variables. It assigns a doubly linked list to
     * the waitlist variable populated with the tasks that are to be handled, then initializes
     * the rest of the variables as necessary.
     *
     * @param quantum: This stores the quantum time unit
     * @param toHandle: The list of Tasks to be handled by the Round Robin Scheduler
     * @throws IllegalArgumentException - if quantum is less than 1
     * @throws IllegalArgumentException - if toHandle is null or if no tasks have been passed in
     */
    public RoundRobin(int quantum, Task[] toHandle) {
        // throws IllegalArgumentException - if quantum is less than 1
        if (quantum < 1) {
            throw new IllegalArgumentException();
        } else if (toHandle == null || toHandle.length == 0) {
            // throws IllegalArgumentException - if toHandle is null or if no tasks have been
            // passed in
            throw new IllegalArgumentException();
        }

        // Initialize waitlist and finished as empty DLLs
        waitlist = new DoublyLinkedList<>();
        finished = new DoublyLinkedList<>();

        // Fill waitlist with Tasks from toHandle. All tasks are initially in the waitlist. Zero
        // tasks are finished at initialization
        for (Task task : toHandle) {
            waitlist.add(task);
        }

        // Store quantum in the instance variable. Set burstTime and waitTime to 0 initially
        this.quantum = quantum;
        burstTime = 0;
        waitTime = 0;
    }

    /**
     * This method goes through the tasks in the waitlist, schedules them in order for one
     * quantum period and then returns it to the queue or marks it completed as necessary. It
     * keeps track of the burst and wait times. It loops through the waitlist until no more
     * tasks need to be scheduled. If there are no tasks in the waitlist, it returns
     * TASK_HANDLED, otherwise it returns the order in which Tasks were completed as specified in
     * the PA4 writeup
     *
     * @return String: This stores either TASK_HANDLED or the order in which Tasks were completed
     * as specified in the PA4 writeup
     */
    public String handleAllTasks() {
        // StringBuilder to append finished Tasks to
        StringBuilder s = new StringBuilder();

        // In the case that the wait list is empty, we return TASK_HANDLED
        if (waitlist.isEmpty()) {
            return TASK_HANDLED;
        } else {
            // If the wait list is not empty, we begin RoundRobin Scheduling till it is
            while (!waitlist.isEmpty()) {
                for (int j = 0; j < quantum; j++) {
                    // Handle each task at the beginning of the list for one quantum period. If
                    // task is handled, increase the burstTime by 1 and waitTime by the number of
                    // other tasks in the wait list
                    if (waitlist.get(0).handleTask()) {
                        burstTime++;
                        waitTime += waitlist.size() - 1;
                    } else {
                        // If a task could not be handled, break from the loop. We will deal with
                        // the finished task below
                        break;
                    }
                }

                // If the task is finished, we add the first task in the list to the finished
                // list and remove it from start the wait list
                if (waitlist.get(0).isFinished()) {
                    finished.add(waitlist.get(0));
                } else {
                    // If the task is not finished by the end of one quantum period, we add it to
                    // the back of the wait list for it to wait for it's next turn
                    waitlist.add(waitlist.get(0));
                }

                // We remove the task from the start of the wait list regardless of whether it is
                // completed or not
                waitlist.remove(0);
            }

            // Initial String in the format specified in PA4 writeup
            s.append("All tasks are handled within ").append(burstTime).append(" "
                    + "units of burst time and ").append(waitTime).append(" "
                    + "units of wait time. The tasks are finished in this order:").append("\n");

            // Appending each finished task in order as specified in the writeup
            for (Task task: finished) {
                s.append(task).append(" -> ");
            }
        }

        // Returning the final String without the last -> to match required specifications
        return s.toString().substring(0, s.length() - ARROW_LENGTH);
    }

    /**
     * Main method for testing.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Task[] test1 = {new Task("A",  3), new Task("B", 4),
                        new Task("C", 4), new Task("D", 12),
                        new Task("E", 6), new Task("F", 3)};
        RoundRobin rr1 = new RoundRobin(test1);     // Quantum: 3, ToHandle: test1
        System.out.println(rr1.handleAllTasks());   // Burst: 32, Wait: 86, Order: AFBCED
        System.out.println();
        System.out.println(rr1.handleAllTasks());   // TASK_HANDLED
        System.out.println();

        Task[] test2 = {new Task("A", 9), new Task("B", 8),
                        new Task("C", 6), new Task("D", 4),
                        new Task("E", 4), new Task("F", 3)};
        RoundRobin rr2 = new RoundRobin(4, test2);  // Quantum: 4, ToHandle: test2
        System.out.println(rr2.handleAllTasks());   // Burst: 34, Wait: 123, Order: DEFBCA
        System.out.println();
        System.out.println(rr2.handleAllTasks());   // TASK_HANDLED
        System.out.println();

        Task[] test3 = {new Task("A", 7), new Task("B", 5),
                        new Task("C", 3), new Task("D", 1),
                        new Task("E", 2), new Task("F", 4),
                        new Task("G", 6), new Task("H", 8)};
        RoundRobin rr3 = new RoundRobin(test3);     // Quantum: 3, ToHandle: test3
        System.out.println(rr3.handleAllTasks());   // Burst: 36, Wait: 148, Order: CDEBFGAH
        System.out.println();
        System.out.println(rr3.handleAllTasks());   // TASK_HANDLED
        System.out.println();
    }
}
