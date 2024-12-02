package cp3.lab04.expandablearray;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ExpandableArrayThreads extends Thread
{
    private ExpandableArray ExpandArr;
    private String name;
    private Thread t;

    ExpandableArrayThreads(ExpandableArray ea, String name) {
        ExpandArr = ea;
        this.name = name;
        System.out.println("Creating " +  name );
    }
    public void run() {
        try{
            for (int i = 0; i < 100; i++) {
                ExpandArr.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void start () {
        if (t == null)
        {
            t = new Thread(this);
            t.start();
        }
    }
}
