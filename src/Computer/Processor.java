package Computer;

public class Processor {
    private String model;

    public Processor(String model) {
        setModel(model);
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}
