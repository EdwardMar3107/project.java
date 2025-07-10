package Computer;

public class Computer {

    private Processor processor;
    private Memory memory;

    public Computer(Processor processor, Memory memory) {
        this.processor = processor;
        this.memory = memory;
    }


    public String spec() {
        return "Процессор: " + processor.getModel() + " | Память: " + memory.getSize();
    }
}
