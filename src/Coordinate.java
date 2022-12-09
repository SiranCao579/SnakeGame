public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isIntersect(Coordinate other) {
        // for the original rectangle obj
        // (x1, y1) is the lower left corner
        int x1 = this.x;
        int y1 = this.y;
        // (x2, y2) is the upper right corner
        int x2 = this.x + 20;
        int y2 = this.y + 20;

        // for the other rectangle obj
        // (m1, n1) is the lower left corner
        int m1 = other.x;
        int n1 = other.y;
        // (m2, n2) is the upper right corner
        int m2 = other.x + 20;
        int n2 = other.y + 20;

        // for the overlap section
        // p1 q1 is the lower left corner
        // p2 q2 is the upper right corner
        int p1 = Math.max(x1, m1);
        int q1 = Math.max(y1, n1);
        int p2 = Math.min(x2, m2);
        int q2 = Math.min(y2, n2);

        // if the overlap exist, difference between p1 p2 and q1 q2 should all be positive
        int diff1 = p2 - p1;
        int diff2 = q2 - q1;

        return (diff1 > 0 && diff2 > 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate other = (Coordinate) obj;
            return other.x == this.x && other.y == this.y;
        }
        return false;
    }
}
