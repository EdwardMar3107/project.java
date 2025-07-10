package Priority;

public enum Priority {LOW("🟢"), MEDIUM("🟡"), HIGH("🔴"), URGENT("🔥");

    private final String icon;

    Priority(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
