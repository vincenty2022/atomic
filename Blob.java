/* *****************************************************************************
 *  Name:    Vincent Yang
 *  NetID:   vincenty
 *  Precept: P10A
 *
 *  Partner Name:    Leonardo Espinoza
 *  Partner NetID:   lmzuniga
 *  Partner Precept: P11
 *
 *  Description: Creates Blob object that acts as helper object for use in
 *  BeadFinder.java
 *
 **************************************************************************** */

public class Blob {
    // queue that stores all points in the blob in order of adding
    //  private Queue<int[]> coords = new Queue<int[]>();

    // stores the mass of the blob
    private int mass = 0;

    // stores initial center of mass coordinate X
    private double cntrX = 0.0;
    // Stores initial center of mass coordinate y
    private double cntrY = 0.0;

    // empty blob object
    public Blob() {
    }

    // adds pixels to blob and modifies CoM and mass to account
    public void add(int x, int y) {
        // modifies center of mass of the blob
        cntrX = (cntrX * mass + x) / (mass + 1);
        cntrY = (cntrY * mass + y) / (mass + 1);

        // creates new coordinate array
        // int[] ptcoord = new int[2];
        // ptcoord[0] = x;
        // ptcoord[1] = y;
        // coords.enqueue(ptcoord);

        // increments mass
        mass++;
    }

    // returns mass
    public int mass() {
        return mass;
    }

    // returns distance between CoM of two blobs
    public double distanceTo(Blob that) {
        double dX = Math.abs(that.cntrX - cntrX);
        double dY = Math.abs(that.cntrY - cntrY);
        return (Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2)));
    }

    // Returns the Coordinates of center of mass in String form.
    public String toString() {
        String formX = String.format("%.4f", cntrX);
        String formY = String.format("%.4f", cntrY);
        return (mass() + " (" + formX + ", " + formY + ")");
    }

    public static void main(String[] args) {
        Blob test = new Blob();
        for (int i = -4; i <= 4; i++) {
            test.add(i, i);
        }

        // checks functionality of mass()
        System.out.print("mass(): ");
        if (test.mass == 9) {
            System.out.println("*");
        }
        else System.out.println();


        Blob test2 = new Blob();
        for (int i = -4; i <= 4; i++) {
            test2.add(i, i);
        }

        // checks functionality of distanceTo()
        System.out.print("distanceTo(): ");
        if (test2.distanceTo(test) == 0) {
            System.out.println("*");
        }
        else System.out.println();

        System.out.println(test.toString() + "\n" + test2.toString());
        StdOut.println(test.mass());
    }
}
