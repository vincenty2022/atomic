/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

public class Test {
    public static void main(String[] args) {
        Queue<Blob> blobStor = new Queue<Blob>();
        Blob test1 = new Blob();
        for (int i = 0; i < 20; i++) {
            test1.add(0, 0);
        }

        Blob test2 = new Blob();
        for (int i = 0; i < 30; i++) {
            test2.add(0, 0);
        }

        Blob test3 = new Blob();
        for (int i = 0; i < 15; i++) {
            test3.add(0, 0);
        }

        blobStor.enqueue(test1);
        blobStor.enqueue(test2);
        blobStor.enqueue(test3);


        for (int i = 0; i < blobStor.size(); i++){
            System.out.println("Before: " + blobStor.size());
            Blob temp = blobStor.dequeue();
            if (!(temp.mass() < 20)) {
                blobStor.enqueue(temp);
            }
            System.out.println("After: " + blobStor.size());
        }
    }
}
