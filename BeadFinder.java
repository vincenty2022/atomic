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

import java.awt.Color;

public class BeadFinder {
    // defensive copy
    private Picture pic;
    // tau
    private double T;
    // 1 for marked, 0 for unmarked
    private int[][] marked;
    // blob storage
    private Queue<Blob> blobStor = new Queue<Blob>();

    // creates BeadFinder ojbect
    public BeadFinder(Picture picture, double tau) {
        int w = picture.width();
        int h = picture.height();
        marked = new int[h][w];
        T = tau;

        // defensive copy
        pic = new Picture(w, h);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                pic.set(x, y, picture.get(x, y));
            }
        }

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                // if Lum is >= T, and unmarked, make a new blob and dfs
                if (!(getLum(pic.get(x, y)) < T) && !ynMarked(x, y)) {
                    Blob current = new Blob();
                    dfs(x, y, current);
                    blobStor.enqueue(current);
                }
                // if Lum < T and unmarked, mark it
                else if (getLum(pic.get(x, y)) < T && ynMarked(x, y)) {
                    marked[y][x] = 1;
                }
            }
        }
    }

    // recursive finding of pixels in a blob
    private void dfs(int x, int y, Blob current) {
        if ((getLum(pic.get(x, y)) < T) && !ynMarked(x, y)) {
            // add to the blob
            current.add(x, y);

            // recursion
            dfs(x, y-1, current);
            dfs(x, y+1, current);
            dfs(x-1, y, current);
            dfs(x+1, y, current);

            // mark this point
            marked[y][x] = 1;
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

    // get luminosity of given color
    private double getLum(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return (0.299*r + 0.587*g + 0.114*b);
    }

    // returns matrix containing blobs of at least 'min' size
    public Blob[] getBeads(int min) {
        // copy instance's queue
        Queue<Blob> defblobStor = new Queue<Blob>();
        for (int i = 0; i < blobStor.size(); i++) {
            Blob temp = blobStor.dequeue();
            defblobStor.enqueue(temp);
            blobStor.enqueue(temp);
        }

        int sizeInit = defblobStor.size();
        for (int i = 0; i < sizeInit; i++) {
            Blob temp = defblobStor.dequeue();
            if (!(temp.mass() < min)) {
                defblobStor.enqueue(temp);
            }
        }

        Blob[] blobArr = new Blob[defblobStor.size()];
        for (int i = 0; i < defblobStor.size(); i++) {
            blobArr[i] = defblobStor.dequeue();
        }
        return blobArr;
    }

    public static void main(String[] args) {

    }
}
