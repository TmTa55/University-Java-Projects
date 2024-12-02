import java.util.*;

public class CPriorityQ
{
    List<Integer> heap;
    List<Vertex> vertices;

    public CPriorityQ()
    {
        heap = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public void add(Vertex v,int weight)
    {
        heap.add(weight);
        vertices.add(v);
        shiftU(heap.size()-1);
    }

    void swap(int pos1, int pos2)
    {
        int temp = heap.get(pos1);
        heap.set(pos1, heap.get(pos2));
        heap.set(pos2, temp);
        Vertex vTemp = vertices.get(pos1);
        vertices.set(pos1,vertices.get(pos2));
        vertices.set(pos2,vTemp);
    }

    int lChildget(int pos)
    {
        return (2*pos+1);
    }

    int rChildget(int pos)
    {
        return (2*pos+2);
    }

    int getParent(int pos)
    {
        return ((pos-1)/2);
    }

    void shiftU(int pos)
    {
        while(pos > 0 && heap.get(getParent(pos)) > heap.get(pos))
        {
            swap(getParent(pos),pos);
            pos = getParent(pos);
        }
    }

    void shiftD(int pos)
    {
        int mIndex = pos;
        int lChild = lChildget(pos);
        int rChild = rChildget(pos);

        if (lChild <= heap.size()-1 && heap.get(lChild) < heap.get(mIndex))
        {
            mIndex = lChild;
        }
        if (rChild <= heap.size()-1 && heap.get(rChild) < heap.get(mIndex))
        {
            mIndex = rChild;
        }
        if (mIndex != pos)
        {
            swap(pos,mIndex);
            shiftD(mIndex);
        }
    }

    public Vertex pop()
    {
        Vertex v = vertices.get(0);
        heap.remove(0);
        vertices.remove(0);
        shiftD(0);
        return v;
    }

    public int size()
    {
        return heap.size();
    }
}
