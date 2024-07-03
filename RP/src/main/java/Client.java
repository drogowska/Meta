import java.util.Objects;

public class Client {

    int id;

    double x;
    double y;

    double demand;
    double startTime;
    double endTime;

    private Client() {
    }

    public Client(int id, double x, double y, double demand, double startTime, double endTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.demand = demand;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
