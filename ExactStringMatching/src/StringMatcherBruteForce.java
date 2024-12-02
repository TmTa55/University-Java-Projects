public class StringMatcherBruteForce extends StringMatcher {

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
        for (int i=0; i<=(tLength-pLength); i++)
        {
            int j = 0;
            comp++;
                    while ((j<pLength)&&(target.charAt(i+j) == pattern.charAt(j)))
                    {
                        j++;
                        comp++;
                    }
                    if (j == pLength)
                    {
                        comp--;
                        return i;
                    }
        }
        return -1;
    }

    @Override
    public int getNumberOfComparisons() {
        return comp;
    }

}
