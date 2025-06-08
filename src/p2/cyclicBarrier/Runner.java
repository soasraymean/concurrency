package p2.cyclicBarrier;

import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Runner {

    public static void main(String[] args) {

        int nSubtasksInMainTask = 2;
        CyclicBarrier barrier = new CyclicBarrier(nSubtasksInMainTask,
                () -> System.out.println("********************"));
        LeafTask leafTask0 = new LeafTask(0, 5, barrier);
        LeafTask leafTask1 = new LeafTask(1, 3, barrier);
        LeafTask leafTask2 = new LeafTask(2, 1, barrier);

        Subtask subTask0 = new Subtask(0, List.of(leafTask0, leafTask1, leafTask2));

        LeafTask leafTask3 = new LeafTask(3, 6, barrier);
        LeafTask leafTask4 = new LeafTask(4, 4, barrier);
        LeafTask leafTask5 = new LeafTask(5, 2, barrier);

        Subtask subTask1 = new Subtask(1, List.of(leafTask3, leafTask4, leafTask5));

        MainTask mainTask = new MainTask(0, List.of(subTask0, subTask1));

        mainTask.perform();


    }

}
