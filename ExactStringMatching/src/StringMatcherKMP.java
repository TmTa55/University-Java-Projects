import java.util.Arrays;

public class StringMatcherKMP extends StringMatcher {

    String target;
    String pattern;
    int comp = 0;

    @Override
    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int match()
    {
        //pattern = "gogoggo"; //for testing out failure list
        comp = 0;
        int tLength = target.length();
        int pLength = pattern.length();
        int[] fList = new int[pLength];
        fList[0] = 0;
        int j = 0;
        int i = 1;
        while (i<pLength)
        {
            /*System.out.println("i"+i);
            System.out.println("j"+j);
            System.out.println("Pi "+pattern.charAt(i));
            System.out.println("Pj "+pattern.charAt(j));*/
            //comp++;
            if(pattern.charAt(i) == pattern.charAt(j))
            {
                fList[i] = j+1;
                i++;
                j++;
            }
            else if (j>0)
            {
                j = fList[j-1];
            }
            else
            {
                fList[i] = 0;
                i++;
            }

        } //code stolen from module 2b slide page 76
        /*for (int k = 0; k < pLength; k++)
        {
            System.out.println(fList[k]);
        } for testing out failure list
        */
        i=0;
        j=0;
        while (i<tLength)
        {
            comp++;
            if (pattern.charAt(j) == target.charAt(i))
            {
                if (j == pLength-1)
                {
                    return i-pLength+1;
                }
                i++;
                j++;
            }
            else if (j>0)
            {
                j = fList[j-1];
            }
            else
            {
                i++;
            }
        }//code stolen from module 2b slide 94
        return -1;

    }

    @Override
    public int getNumberOfComparisons() {
        return comp;
    }
}