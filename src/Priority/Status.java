package Priority;

public enum Status {NEW("🆕"), IN_PROGRESS("🛠️"), DONE("✅"), CANCELLED("❌");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
