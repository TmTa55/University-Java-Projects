package cp3.lab04.expandablearray;

/**
 *
 */
public class ExpandableArrayDriver
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        ExpandableArray ea = new ExpandableArray(1);
        ExpandableArrayThreads thread1 = new ExpandableArrayThreads(ea, "1");
        ExpandableArrayThreads thread2 = new ExpandableArrayThreads(ea, "2");
        ExpandableArrayThreads thread3 = new ExpandableArrayThreads(ea, "3");
        ExpandableArrayThreads thread4 = new ExpandableArrayThreads(ea, "4");
        System.out.println("size: " + ea.size());

        ea.add(1);

/*
        System.out.println("size: " + ea.size());

        for (int i = 0; i < ea.size(); i++)
        {
            System.out.print(ea.get(i) + " ");
        }
        System.out.println();*/


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        for (int i = 0; i < ea.size(); i++)
        {
            System.out.print(ea.get(i) + " ");
        }
        System.out.println();
        System.out.println("size: " + ea.size());
    }

}
