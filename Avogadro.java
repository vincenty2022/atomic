/* *****************************************************************************
 *  Name:    Leonardo Espinoza
 *  NetID:   lmzuniga
 *  Precept: P11
 *
 *  Partner Name:    Vincent Yang
 *  Partner NetID:   vincenty
 *  Partner Precept: P10A
 *
 *  Description:  Calculates Boltzmann and Avogadro's numbers by taking
 *  displacements as StdIn and going on from there.
 *
 **************************************************************************** */

public class Avogadro {
    public static void main(String[] args) {

        // Constants for Water at room temperature
        double ABSOLUTE_TEMP = 297.0;
        double VISCOSITY_WATER = 9.135e-4;
        double RADIUS = 0.5e-6;
        double CHANGE_TIME = 0.5;
        double GAS_CONSTANT = 8.31446;

        // Reading radial displacements from standard input & solve for sigma^2
        double squaredSum = 0;
        double n = 0;
        while (!StdIn.isEmpty()) {
            double rad = StdIn.readDouble() * (0.175e-6);
            squaredSum += (rad * rad);
            n++;
        }
        double sigmaSquared = squaredSum / (2 * n);

        // Solve for Boltsmann constant k
        double topPartofK = 6 * Math.PI * VISCOSITY_WATER *
                RADIUS * sigmaSquared;
        double bottomPartofK = 2 * CHANGE_TIME * ABSOLUTE_TEMP;
        double k = topPartofK / bottomPartofK;
        StdOut.print("Boltzmann = ");
        StdOut.printf("%1.4e\n", k);

        // Solve for Avogadro's Number
        double na = GAS_CONSTANT / k;
        StdOut.print("Avogadro = ");
        StdOut.printf("%1.4e\n", na);

    }
}
