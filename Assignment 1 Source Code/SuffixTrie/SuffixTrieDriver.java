/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;

/**
 * This is an example class to driver the Suffix Trie project.  You can use this a starting point
 * to test your Suffix Trie implementation.
 *
 * It expects user input of the file to processes as the first line and then the subsequent lines are
 * the words/phrases/suffixes to search for with an empty line terminating the user input. For example:
 *
 * java cp3.ass01.suffixtrie.SuffixTrieDriver
 * data/Frank02.txt
 * and
 * the
 * , the
 * onster
 * ngeuhhh
 * ing? This
 *
 * @author lewi0146
 * data/mississippi.txt
 */
/*
data/mississippi.txt
m
i
s
p

data/FrankChap02.txt
and
the
, the
onster
ngeuhhh
, as we believed,
ing? This
(July 31st)

data/FrankChap02.txt
—
’
Æ
 */

public class SuffixTrieDriver {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();
        Queue<String> ss = new ArrayDeque<>();
        String suffix = in.nextLine();

        while (!suffix.equals(""))
        {
            ss.offer(suffix);
            suffix = in.nextLine();
        }

        //this Trie is for text matching
        SuffixTrie st = SuffixTrie.readInFromFile(fileName);
        //This Trie is for prefix wildcard matching
        Trie tn = Trie.readInDictionary(fileName);
        //This Trie is for suffix wildcard matching
        WildSuffixTrie wst = WildSuffixTrie.readInFromFile(fileName);

        while (!ss.isEmpty()) {
            String s = ss.poll();
            /*
            This checks if the search string starts with a wildcard.
            If it does then it will delete the wildcard and find all words with the prefix given.
             */
            if (s.charAt(s.length()-1) == '*')
            {
                s = s.replace("*","");
                s = s.toLowerCase(Locale.ROOT);
                List<String> wild = tn.getAlphabeticalListWithPrefix(s);
                System.out.println("Finding words starting with "+s);
                /*
                Once it has a list of words with the prefix it runs them through the suffix trie to figure
                out the position of each word.
                */
                for (String value : wild) {
                    SuffixTrieNode sn = st.get(value);
                    System.out.println("[" + value + "]: " + sn);
                    System.out.println(value+" appears in "+fileName+" "+sn.getFreq()+" times");
                }
                System.out.println("There are "+wild.size()+" words that start with "+s);
            }
            /*
            This checks if the search string ends with a wildcard.
            If it does then it will delete the wildcard and find all words with the suffix given.
             */
            else if (s.charAt(0) == '*')
            {
                s = s.replace("*","");
                s = s.toLowerCase(Locale.ROOT);
                SuffixTrieNode wild = wst.get(s);
                Set<String> words = wild.getWord();//Set to get rid of duplicates
                List<String> Swords = new ArrayList<>(words);//List to sort the given words
                Collections.sort(Swords);
                System.out.println("Finding words ending in "+s);
                /*
                Once it has a list of words with the suffix it runs them through the suffix trie to figure
                out the position of each word.
                */
                for (String value : Swords) {
                    SuffixTrieNode sn = st.get(value);
                    System.out.println("[" + value + "]: " + sn);
                    System.out.println(value+" appears in "+fileName+" "+sn.getFreq()+" times");
                }
                System.out.println("There are "+Swords.size()+" words that end in "+s);
            }
            else if (s.contains("*"))
            {
                s = s.replace("*"," ");
                s = s.toLowerCase(Locale.ROOT);
                String[] split = s.split(" ");
                List<String> wildPre = tn.getAlphabeticalListWithPrefix(split[0]);
                SuffixTrieNode wildSuff = wst.get(split[1]);
                Set<String> words = wildSuff.getWord();//Set to get rid of duplicates
                wildPre.retainAll(words);
                Collections.sort(wildPre);
                System.out.println("Finding words Starting with "+split[0]+" and ending in "+split[1]);
                /*
                Once it has a list of words with the suffix it runs them through the suffix trie to figure
                out the position of each word.
                */
                for (String value : wildPre) {
                    SuffixTrieNode sn = st.get(value);
                    System.out.println("[" + value + "]: " + sn);
                    System.out.println(value+" appears in "+fileName+" "+sn.getFreq()+" times");
                }
                System.out.println("There are "+wildPre.size()+" words that start with "+split[0]+" and ending in "+split[1]);
            }
            else {
                s = s.toLowerCase(Locale.ROOT);
                System.out.println("Finding position of substring "+s);
                SuffixTrieNode sn = st.get(s);
                System.out.println("[" + s + "]: " + sn);
                if (sn != null)
                    System.out.println(s+" appears in "+fileName+" "+sn.getFreq()+" times");

            }
            System.out.print("\n");
        }

    }
}
