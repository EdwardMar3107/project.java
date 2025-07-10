package Priority;

import java.util.Objects;

public class WorkTask {

    private String title;
    private Priority priority;
    private Status status;

    public WorkTask(String title, Priority priority, Status status) {
        this.title = title;
        this.priority = priority;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status.getLabel() + " " + title + " — приоритет: " +
                priority.getIcon() + " " + priority.name();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof WorkTask)) return false;
        WorkTask other = (WorkTask) obj;
        return title.equals(other.title) && priority.equals(other.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPriority());
    }


}
