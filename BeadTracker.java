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
public class BeadTracker {
    // returns distance between each blob in picture t1 and the nearest blob in t0
    public static void printDist (Picture t0, Picture t1, int min, double tau, double delta) {
        BeadFinder t1BF = new BeadFinder(t1, tau);
        BeadFinder t0BF = new BeadFinder(t0, tau);

        Blob[] t1Blob = t1BF.getBeads(min);
        Blob[] t0Blob = t0BF.getBeads(min);

        for (int i = 0; i < t1Blob.length; i++) {
            double minDist = delta;
            boolean lsDelta = false;
            for (int j = 0; j < t0Blob.length; j++) {
                double currDist = t1Blob[i].distanceTo(t0Blob[j]);
                if(currDist < minDist) {
                    lsDelta = true;
                    minDist = currDist;
                }
            }
            if (lsDelta) System.out.print(String.format("%.4f",minDist) + "\n");
        }
    }

    public static void main(String[] args) {
        Stopwatch test = new Stopwatch();
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);

        for (int i = 4; i < args.length; i++) {
            Picture t1 = new Picture(args[i]);
            Picture t0 = new Picture(args[i-1]);
            printDist(t0, t1, min, tau, delta);
        }
        System.out.println("time: "+ test.elapsedTime());
    }
}
