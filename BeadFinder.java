/* *****************************************************************************
 *  Name:    Vincent Yang
 *  NetID:   vincenty
 *  Precept: P10A
 *
 *  Partner Name:    Leonardo Espinoza
 *  Partner NetID:   lmzuniga
 *  Partner Precept: P11
 *
 *  Description: (i) read an image, (ii) classify the pixels as foreground or
 *  background, and (iii) find the disc-shaped clumps of foreground pixels that
 *  constitute each bead.
 *
 **************************************************************************** */

public class BeadFinder {
    // stores original picture
    private final Picture pic;
    // tau
    private final double t;
    // 1 for marked, 0 for unmarked
    private int[][] marked;
    // blob storage
    private final ST<Integer, Blob> blobStor = new ST<Integer, Blob>();

    // creates BeadFinder ojbect
    public BeadFinder(Picture picture, double tau) {
        int h = picture.height();
        int w = picture.width();
        marked = new int[h][w];
        t = tau;

        // defensive copy
        pic = new Picture(w, h);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pic.set(x, y, picture.get(x, y));
            }
        }

        int count = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                double intensity = Luminance.intensity(pic.get(x, y));

                if (!(intensity < t) && !ynMarked(x, y)) {
                    Blob current = new Blob();
                    dfs(x, y, current);
                    blobStor.put(count, current);
                    count++;
                }
                // if Lum < t and unmarked, mark it
                else if (intensity < t && ynMarked(x, y)) {
                    marked[y][x] = 1;
                }
            }
        }
    }

    // recursive finding of pixels in a blob
    private void dfs(int x, int y, Blob current) {
        if (y < 0 || y >= pic.height() || x < 0 || x >= pic.width()
                || ynMarked(x, y)) {
            return;
        }
        double intensity = Luminance.intensity(pic.get(x, y));
        if (intensity < t) {
            return;
        }
        if (!(intensity < t)) {
            // add to the blob
            current.add(x, y);
            marked[y][x] = 1;

            // recursion
            dfs(x, y + 1, current);
            dfs(x, y - 1, current);
            dfs(x + 1, y, current);
            dfs(x - 1, y, current);
        }

    }

    // checks to see if a given point has been marked
    private boolean ynMarked(int x, int y) {
        // unmarked
        if (marked[y][x] == 0) {
            return false;
        }
        // marked
        return true;
    }

    // returns matrix containing blobs of at least 'min' size
    public Blob[] getBeads(int min) {
        // creates defensive copy
        ST<Integer, Blob> defblobStor = new ST<Integer, Blob>();
        for (int key: blobStor.keys()) {
            Blob temp = blobStor.get(key);
            defblobStor.put(key, temp);
        }

        // removes blobs that do not meet minimum
        for (int key: blobStor.keys()) {
            if (blobStor.get(key).mass() < min) {
                defblobStor.remove(key);
            }
        }

        // storage blob array
        Blob[] blobArr = new Blob[defblobStor.size()];

        int i = 0;
        for (int key: defblobStor.keys()) {
            blobArr[i] = defblobStor.get(key);
            i++;
        }
        return blobArr;
    }

    // returns printablle string given a min value
    private String toString(int min) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < getBeads(min).length; i++) {
            string.append(getBeads(min)[i] + "\n");
        }
        return string.toString();
    }

    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture pic = new Picture(args[2]);

        BeadFinder test = new BeadFinder(pic, tau);
        test.getBeads(min);

        System.out.println(test.toString(min));
    }
}
