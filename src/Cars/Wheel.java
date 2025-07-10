package Cars;

public class Wheel {

    private int size;
    private String wheelType;

    public Wheel(int size, String wheelType) {
        this.size = size;
        this.wheelType = wheelType;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setWheelType(String wheelType) {
        this.wheelType = wheelType;
    }

    public int getSize() {
        return size;
    }

    public String getWheelType() {
        return wheelType;
    }
}
