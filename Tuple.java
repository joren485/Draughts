public class Tuple {

    public final int x;
    public final int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Tuple getDirection(Tuple src, Tuple dest){

        return new Tuple((src.x < dest.x) ? 1 : -1, (src.y < dest.y) ? 1 : -1);
    }

    public static Tuple add(Tuple p, Tuple q) {
        return new Tuple(p.x + q.x, p.y + q.y);
    }

    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}