import java.util.Arrays;
import java.util.Locale;

public class StringMatcherBoyerMoore extends StringMatcher{

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
        comp = 0;
        int tLength = target.length();
        int pLength = pattern.length();
        int[] L = new int[126];
        for (int i = 0; i < L.length; i++)
        {
            L[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++)
        {
            L[pattern.charAt(i)] = i;
        }
        for (int i=pLength-1; i<tLength; i++)
        {
            comp++;
            if (target.charAt(i) == pattern.charAt(pLength-1))
            {
                int pattMatch = 0;
                int j = 1;
                for (int k = i-1; k > i-pLength; k--)
                {
                    comp++;
                    if (target.charAt(k) == pattern.charAt(pLength-1-j))
                    {
                        pattMatch++;
                    }
                    else
                    {
                        k = 0;
                    }
                    j++;
                }
                j--;
                if (pattMatch == pLength-1)
                {
                    return i-pLength+1;
                }
                if ((L[target.charAt(i-j)]) == -1)
                {
                    i+=pLength-j;
                }
                else if ((L[target.charAt(i-j)]) < i)
                {
                    i+=j;
                }
            }
            else if ((L[target.charAt(i)]) == -1)
            {
                i+=pLength;
            }
            else if ((L[target.charAt(i)])<i&&(L[target.charAt(i)])>=0)
            {
                i+=pLength - (1+L[target.charAt(i)]);
            }
            else
            {
                i++;
            }
            i--;
        }
        //code mostly comes from the diagram on module 2b of the lecture slides
        //pg 34 diagram till 37
        return -1;
    }

    @Override
    public int getNumberOfComparisons() {
        return comp;
    }
}


