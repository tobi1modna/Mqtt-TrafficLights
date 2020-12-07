package mqtt.trafficlights.model;

public class TrafficLights {

    private boolean red;
    private boolean green;
    private boolean yellow;

    public TrafficLights(){
        this.green = false;
        this.yellow = false;
        this.red = true;
    }

    public void setRedState() {
        this.red = true;
        this.green = false;
        this.yellow = false;
    }

    public void setGreenState() {
        this.green = true;
        this.red = false;
        this.yellow = false;
    }

    public void setYellowState() {
        this.yellow = true;
        this.green = true;
        this.red = false;
    }
    public void setYellowBlink() {
        this.yellow = true;
        this.green = false;
        this.red = false;
    }
    public void setOff() {
        this.yellow = false;
        this.green = false;
        this.red = false;
    }

    @Override
    public String toString() {
        return "TrafficLights{" +
                "red=" + red +
                ", green=" + green +
                ", yellow=" + yellow +
                '}';
    }
}
