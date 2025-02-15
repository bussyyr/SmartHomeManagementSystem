package infrastructure.api.dto;

public class DeviceInput {

    private String name;
    private Float totalConsumptionPerHour;
    private Long roomId;

    public DeviceInput() {}

    public DeviceInput(String name, Float totalConsumptionPerHour, Long roomId) {

        this.name = name;
        this.totalConsumptionPerHour = totalConsumptionPerHour;
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getTotalConsumptionPerHour() {
        return totalConsumptionPerHour;
    }

    public void setTotalConsumptionPerHour(Float totalConsumptionPerHour) {
        this.totalConsumptionPerHour = totalConsumptionPerHour;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
