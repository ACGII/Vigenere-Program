
/**
 * Write a description of Tester here.
 * 
 * @author (ACGII)
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import java.io.*;

public class Tester {
    public void testSliceString(){
        VigenereBreaker v = new VigenereBreaker();
        String str = "abcdefghijklm";
        for(int i=3;i<=5;i++){
            for(int j=0;j<i;j++){
                System.out.println(v.sliceString(str,j,i));
            }
        }
    }
    public void testtryKeyLength(){
        FileResource resource = new FileResource("../VigenereTestData/secretmessage1.txt");
        String contents = resource.asString();
       
        VigenereBreaker vb = new VigenereBreaker();
        String as = Arrays.toString(vb.tryKeyLength(contents,4,'e'));
        
        System.out.println("END "+as);
    }
    
    public void testMostCommonCharIn(){
        FileResource fr = new FileResource();
        VigenereBreaker vb = new VigenereBreaker();
        HashSet<String> dict = vb.readDictionary( fr);
        char mc = vb.mostCommonCharIn(dict);
        System.out.println("Most Common "+mc);
    }
    
    public void testDirRes(){
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) { // process each file in turn
            System.out.println("name "+f.getName());
        }
    }
}
