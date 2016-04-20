public class Tuple {

    public final int x;
    public final int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Tuple getDirection(Tuple src, Tuple dest){

        return new Tuple((src.x - dest.x < 0) ? -1 : 1, (src.y - dest.y < 0) ? -1 : 1);
    }
} 