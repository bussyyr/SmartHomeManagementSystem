package infrastructure.api.dto;

public class RoomInput {
    private String name;

    public RoomInput() {}

    public RoomInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
