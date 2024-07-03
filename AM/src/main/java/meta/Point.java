package meta;

public class Point implements Cloneable {
    private int x;
    private int y;
    private int number;
    private boolean visited;

    public Point(int x, int y, int number) {
        this.x = x;
        this.y = y;
        this.number = number;
        visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Point(x, y, number);
    }
}
