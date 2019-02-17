import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder result = new StringBuilder();
        for(int i=whichSlice;i<message.length();i += totalSlices){
            result.append(message.substring(i,i+1));
            
        }
        return result.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker ceCr = new CaesarCracker(mostCommon);
        //WRITE YOUR CODE HERE
        for(int i=0;i<klength;i++){
            String s = sliceString(encrypted,i,klength);
            int kk = ceCr.getKey(s);
            //System.out.println("kk "+kk);
            key[i] = kk;
        }
        //System.out.println("Key "+key.toString());
        return key;
    }

    public void breakVigenereA () {
        //WRITE YOUR CODE HERE
        FileResource fr = new FileResource();
        String contents = fr.asString();
        int[] keys = tryKeyLength(contents,4,'e');
        VigenereCipher vc = new VigenereCipher(keys);
        String decryp = vc.decrypt(contents);
        System.out.println("Message:  "+decryp.substring(0,300));
    }
    
    public void breakVigenere () {
        //WRITE YOUR CODE HERE
        DirectoryResource dr = new DirectoryResource();
        HashMap<String, HashSet<String>> langs = new HashMap<String, HashSet<String>>();
        for (File f : dr.selectedFiles()) { // process each file in turn
            System.out.println("name "+f.getName());
            langs.put(f.getName(),readDictionary(new FileResource(f)));
        }
        FileResource fr = new FileResource();
        String contents = fr.asString();
        breakForAllLangs(contents,langs);
        //System.out.println("Message:  "+decM.substring(0,100));
    }
    
    public void breakVigenereB () {
        //WRITE YOUR CODE HERE
        
        FileResource fr = new FileResource();
        String contents = fr.asString();
        FileResource dicD = new FileResource("./dictionaries/English");
        HashSet<String> dict = readDictionary(dicD);
        String decM = breakForLanguage(contents,dict);
        System.out.println("Message:  "+decM.substring(0,100));
    }
    
    public HashSet<String> readDictionary( FileResource fr){
        HashSet<String> wordL = new HashSet<String>();
        for (String line : fr.lines()) {   // process each line in turn
            wordL.add(line.toLowerCase());
        }
        return wordL;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        String[] list = message.split("\\W+");
        int cnt=0;
        for(int i=0;i<list.length;i++){
            if(dictionary.contains(list[i].toLowerCase())){
                cnt++;
            } else {
                //System.out.println("Not in Dict "+list[i]);
            }
        }
        return cnt;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        int maxx=0;
        int len = 0;
        String result="";
        char mc = mostCommonCharIn(dictionary);
        for(int i=1;i<101;i++){
            int[] keys = tryKeyLength(encrypted,i,mc);
            VigenereCipher vc = new VigenereCipher(keys);
            String decryp = vc.decrypt(encrypted);
            int j = countWords(decryp, dictionary);
            if(j > maxx){
                maxx = j;
                result = decryp;
                len = i;
            }
            if (i==38){
                //System.out.println(i+" encrypted words "+j);
            }
        }
        System.out.println("maxx "+maxx+" len "+len);
        return result;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        String dictL = "";
        int[] counts = new int[26];
        CaesarCracker cc = new CaesarCracker();
        for (String message : dictionary) {  // process each item in turn 
            for(int k=0; k < message.length(); k++){
                int dex = alph.indexOf(Character.toLowerCase(message.charAt(k)));
                if (dex != -1){
                    counts[dex] += 1;
                }
            }
        }
        
        int mi = cc.maxIndex(counts);
        return alph.charAt(mi);
            
    }
    
    public  void breakForAllLangs(String encrypted,HashMap<String, HashSet<String>> languages){
        String result = "";
        String lang="";
        int maxx = 0;
        for (String s : languages.keySet()) {   // process each key in turn 
           String decryp = breakForLanguage(encrypted, languages.get(s)); 
           int j = countWords(decryp, languages.get(s));
           if(j > maxx){
               maxx = j;
               result = decryp;
               lang = s;
           }
        }
        System.out.println("maxx "+maxx+" decryp "+result.substring(0,50)+" lang "+lang);
    }
    
    
}
