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

public class BeadFinder {
    // defensive copy
    private final Picture pic;
    // tau
    private final double t;
    // 1 for marked, 0 for unmarked
    private int[][] marked;
    // // pixel height in the picture
    // private final int h;
    // // pixel width in the picture
    // private final int w;

    // blob storage
    private final Queue<Blob> blobStor = new Queue<Blob>();

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

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                double intensity = Luminance.intensity(pic.get(x, y));

                if (!(intensity < t) && !ynMarked(x, y)) {
                    Blob current = new Blob();
                    dfs(x, y, current);
                    blobStor.enqueue(current);
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

            // StdDraw.point(x, y);
            // StdDraw.show();
            dfs(x, y + 1, current);
            dfs(x, y - 1, current);

            dfs(x + 1, y, current);
            dfs(x - 1, y, current);

            // mark this point
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
        int initSize = blobStor.size();
        // copy instance's queue
        Queue<Blob> defblobStor = new Queue<Blob>();
        for (int i = 0; i < initSize; i++) {
            Blob temp = blobStor.dequeue();
            defblobStor.enqueue(temp);
            blobStor.enqueue(temp);
        }

        int sizeInit = defblobStor.size();
        for (int i = 0; i < sizeInit; i++) {
            Blob temp = defblobStor.dequeue();
            if (temp.mass() >= min) {
                defblobStor.enqueue(temp);
            }
        }
        Blob[] blobArr = new Blob[defblobStor.size()];
        // StringBuilder sb = new StringBuilder();

        int size = defblobStor.size();
        for (int i = 0; i < size; i++) {
            blobArr[i] = defblobStor.dequeue();
            // StdOut.println(blobArr[i]);
            // sb.append(blobArr[i]);
            // sb.append("\n");
        }

        //  StdOut.println(sb.toString());
        return blobArr;
    }

    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture pic = new Picture(args[2]);

        // StdDraw.setXscale(0, pic.width());
        // StdDraw.setYscale(0, pic.height());


        BeadFinder test = new BeadFinder(pic, tau);
        test.getBeads(min);

        // StdOut.println(Arrays.toString(test.getBeads(min)));

    }
}
