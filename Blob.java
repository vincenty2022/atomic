/* *****************************************************************************
 *  Name:    Vincent Yang
 *  NetID:   vincenty
 *  Precept: P10A
 *
 *  Partner Name:    Leonardo Espinoza
 *  Partner NetID:   lmzuniga
 *  Partner Precept: P11
 *
 *  Description:  ADD ACCURATE STUFF
 *
 **************************************************************************** */

public class Blob {
    // queue that stores all points in the blob in order of adding
    private Queue<int[]> coords = new Queue<int[]>();

    // stores the mass of the blob
    private int mass = 0;

    // stores initial center of mass coordinates
    private int[] cntrMass = {0,0};

    // empty blob object
    public Blob() {
    }

    public void add(int x, int y) {
        // modifies center of gravity of the blob
        cntrMass[0] = cntrMass[0] * mass + x / (mass+ 1);
        cntrMass[1] = cntrMass[1] * mass + y / (mass+ 1);

        // creates new coordinate array
        int[] ptcoord = new int[2];
        ptcoord[0] = x;
        ptcoord[1] = y;
        coords.enqueue(ptcoord);

        // increments mass
        mass++;
    }

    public int mass() {
        return mass;
    }

    public double distanceTo(Blob that) {

    }

    public String toString() {

    }

    public static void main(String[] args) {

    }
}
