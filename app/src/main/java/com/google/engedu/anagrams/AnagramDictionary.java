/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<String>();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<String,ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>> SizeToWords = new HashMap<Integer, ArrayList<String>>();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            if(SizeToWords.containsKey(word.length())){
                SizeToWords.get(word.length()).add(word);
            }
            else{
                SizeToWords.put(word.length(), new ArrayList());
                SizeToWords.get(word.length()).add(word);
            }


            String check = sortLetters(word);

            if(lettersToWord.containsKey(check)){
                lettersToWord.get(check).add(word);
            }
            else{
                lettersToWord.put(check, new ArrayList());
                lettersToWord.get(check).add(word);
            }
        }
    }
    
    //sort letters function
    public static String sortLetters(String S){
    	char[] chars;
        chars = S.toCharArray();
        Arrays.sort(chars);
    	String A = new String(chars);
    	return A;
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !(word.contains(base));
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sort = sortLetters(targetWord);
        if(lettersToWord.containsKey(sort)){
            result.addAll(lettersToWord.get(sort)) ;
        }
        return result;
    }
    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        int a = (int)'a';
        for(int i=a;i<(a+27);i++){
            result.addAll(getAnagrams(word+(char)i));
        }
        return result;
    }

    public String pickGoodStarterWord() {
        String word;

        int n = SizeToWords.get(wordLength).size();
        while(true){
            word = SizeToWords.get(wordLength).get((random.nextInt(n)));
            if(getAnagramsWithOneMoreLetter(word).size() > MIN_NUM_ANAGRAMS){
                wordLength++;
                return word;
            }
        }
    }

    public List<String> getAnagramsWithTwoMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        int a = (int)'a';
        for(int i=a;i<(a+27);i++){
            result.addAll(getAnagramsWithOneMoreLetter(word+(char)i));
        }
        return result;
    }
}
