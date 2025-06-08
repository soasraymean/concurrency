package p2.cyclicBarrier;

import java.util.List;

public class Subtask extends CompositeTask<LeafTask> {

    public Subtask(long id, List<LeafTask> subtasks) {
        super(id, subtasks);
    }

    @Override
    protected void perform(LeafTask subtask) {
        subtask.perform();
    }
}
