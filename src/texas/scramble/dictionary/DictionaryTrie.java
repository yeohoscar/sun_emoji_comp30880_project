package texas.scramble.dictionary;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


// Dictionary of valid words in Trie format

public class DictionaryTrie {
    private final Node root;

    public DictionaryTrie(String pathToDictionary) {
        root = new Node('^', false, new ArrayList<>());
        createDictionary(pathToDictionary);
    }

    // Adds words from given word list file to tree

    private void createDictionary(String pathToDictionary) {
        try (Stream<String> stream = Files.lines(Paths.get(pathToDictionary))) {
            stream.forEach(this::add);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Node getRoot() {
        return root;
    }

    public void add(String word) {
        Node curr = root;

        for (char letter : word.toCharArray()) {
            Node n = curr.children.stream()
                    .filter(node -> letter == node.getLetter())
                    .findFirst()
                    .orElse(null);

            if (n == null) {
                n = new Node(letter, false, new ArrayList<>());
                curr.addChild(n);
            }

            curr = n;
        }
        curr.setEndOfWord(true);
    }

    public boolean isValidWord(String word) {
        Node curr = root;

        for (char letter : word.toCharArray()) {
            curr = curr.children.stream()
                    .filter(node -> letter == node.getLetter())
                    .findAny()
                    .orElse(null);

            if (curr == null) return false;
        }
        return curr.isEndOfWord();
    }

    // Represents a letter in the tree

    private static class Node {
        char letter;
        boolean endOfWord;
        List<Node> children;

        public Node(char letter, boolean isEndOfWord, List<Node> children) {
            this.letter = letter;
            this.endOfWord = isEndOfWord;
            this.children = children;
        }

        public char getLetter() {
            return letter;
        }

        public boolean isEndOfWord() {
            return endOfWord;
        }

        public void setEndOfWord(boolean endOfWord) {
            this.endOfWord = endOfWord;
        }

        public void addChild(Node newNode) {
            children.add(newNode);
        }
    }

    /************* find all words contain community letters and those not contain community letters **************/
    public boolean containBlank(String[] letters){
        for(String letter: letters){
            if(letter.equals(" ")){
                return true;
            }
        }
        return false;
    }
    public String[] removeBlank(String[] letters){
//		ArrayList<String> availableLetters = findAvailableLetters(letters);
//		HashMap<String, Integer> temp = new HashMap<>(deckOfTiles.getAllTiles());
//		for(int i=0; i<letters.length; i++){
//			if(letters[i].equals(" ")){
//				letters[i]=findHighestScoreLetter(availableLetters, temp);
//				availableLetters = updateAvailableLetters(temp);
//			}
//		}
        ArrayList<String> temp = new ArrayList<>();
        for(String letter: letters){
            if(!letter.equals(" ")){
                temp.add(letter);
            }
        }
        return temp.toArray(new String[0]);
    }
    public List<String> findAllWords(String[] letters){
        Node current;
        HashMap<String, Integer> lettersContained = new HashMap<>();
        if(containBlank(letters)){
            letters = removeBlank(letters);
        }
//        for(String letter: letters){
//            System.out.println("removed blank letter = "+letter);
//        }
        List<List<String>> allWords = new ArrayList<>();
        current = root;
        for(Node child: current.children){
//            Node n = current.children.stream()
//                    .filter(node -> letter.charAt(0) == node.getLetter())
//                    .findFirst()
//                    .orElse(null);
//            if(n==null){
//                return new ArrayList<>();
//            }
            //current = n;
            String str = String.valueOf(child.letter);
            //System.out.println("root children's letter = "+str);
            List<String> words = dfs(child, str, letters, new ArrayList<>());
            allWords.add(words);
        }
        ArrayList<String> result = new ArrayList<>();
        if(allWords.isEmpty()){
            return result;
        }
        for(List<String> word: allWords){
            result.addAll(word);
        }
        return result;
        /*for(String letter: letters){
            if(lettersContained.containsKey(letter)){
                lettersContained.put(letter, lettersContained.get(letter)+1);
            }else {
                lettersContained.put(letter, 1);
            }
        }
        List<List<String>> allWords = new ArrayList<>();
        for(String letter: letters){
            current = root;
            Node n = current.children.stream()
                    .filter(node -> letter.charAt(0) == node.getLetter())
                    .findFirst()
                    .orElse(null);
            if(n==null){
                return new ArrayList<>();
            }
            current = n;
            allWords.add(dfs(current, letter, lettersContained, new ArrayList<>()));
        }

        List<String> result = new ArrayList<>();
        for(List<String> words: allWords){
            result.addAll(words);
        }
        return result;*/
    }
    private boolean wordContainsLetters(String prefix, String[] letters){
//        String[] strArray = prefix.split("");
//        boolean contained = true;
//        HashMap<String, Integer> prefixHash = new HashMap<>();
//        for(String letter: strArray){
//            if(prefixHash.containsKey(letter)){
//                prefixHash.put(letter, prefixHash.get(letter)+1);
//            }else {
//                prefixHash.put(letter, 1);
//            }
//        }
        //compare if both key and value of prefixHash are subset of lettersContaine
//        System.out.println("prefix = "+prefix);
//        exit(0);
        int countNumberOfLettersContained = 0;
        for(String letter: letters){
            if(prefix.contains(letter)){
                countNumberOfLettersContained++;
            }
        }
        if(letters.length==1){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 1) && prefix.length() <= (countNumberOfLettersContained + 6);
        }else if(letters.length==2){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 2) && prefix.length() <= (countNumberOfLettersContained + 5);
        }
        else if(letters.length==3){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 3) && prefix.length() <= (countNumberOfLettersContained + 4);
//            if(countNumberOfLettersContained==1 && prefix.length()<=5){
//                return true;
//            }else if(countNumberOfLettersContained==2 && prefix.length()<=6){
//                return true;
//            }else return countNumberOfLettersContained == 3 && prefix.length() <= 7;
        }else if(letters.length==4){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 4) && prefix.length() <= (countNumberOfLettersContained + 3);
//            if(countNumberOfLettersContained==1 && prefix.length()<=4){
//                return true;
//            }else if(countNumberOfLettersContained==2 && prefix.length()<=5){
//                return true;
//            }else if(countNumberOfLettersContained==3 && prefix.length()<=6) {
//                return true;
//            }else return countNumberOfLettersContained == 4 && prefix.length() <= 7;
        }else if(letters.length==5){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 5) && prefix.length() <= (countNumberOfLettersContained + 2);
//            if(countNumberOfLettersContained==1 && prefix.length()<=3){
//                return true;
//            }else if(countNumberOfLettersContained==2 && prefix.length()<=4){
//                return true;
//            }else if(countNumberOfLettersContained==3 && prefix.length()<=5){
//                return true;
//            }else if(countNumberOfLettersContained==4 && prefix.length()<=6){
//                return true;
//            }else return countNumberOfLettersContained == 5 && prefix.length() <= 7;
        }else if(letters.length==6){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 6) && prefix.length() <= (countNumberOfLettersContained + 1);
//            if(countNumberOfLettersContained==1 && prefix.length()<=2){
//                return true;
//            }else if(countNumberOfLettersContained==2 && prefix.length()<=3){
//                return true;
//            }
        }else if(letters.length==7){
            return (countNumberOfLettersContained >= 0 && countNumberOfLettersContained <= 7) && prefix.length() <= (countNumberOfLettersContained);
        }
        return false;
    }
    /*

        private boolean wordIsFormedByLettersContained(String prefix, HashMap<String, Integer> lettersContained){
            //System.out.println("PPrefix = "+prefix);
            String[] strArray = prefix.split("");
            boolean contained = true;
            HashMap<String, Integer> prefixHash = new HashMap<>();
            for(String letter: strArray){
                if(prefixHash.containsKey(letter)){
                    prefixHash.put(letter, prefixHash.get(letter)+1);
                }else {
                    prefixHash.put(letter, 1);
                }
            }
            //compare if both key and value of prefixHash are subset of lettersContained
            for(Map.Entry<String, Integer> entry: prefixHash.entrySet()){
                if(!(lettersContained.containsKey(entry.getKey()) && (entry.getValue()<=lettersContained.get(entry.getKey())))){
                    contained=false;
                }
            }
            return contained;
        }
    */
    private List<String> dfs(Node node, String prefix, String[] lettersContained, List<String> results) {
        if (node.isEndOfWord() && wordContainsLetters(prefix, lettersContained)) {
            results.add(prefix);
        }
        for (int i = 0; i < node.children.size(); i++) {
            Node child = node.children.get(i);
            if (child != null) {
                dfs(child, prefix + child.letter, lettersContained, results);
            }
        }
        return results;
    }
    /************* find all words that formed by player's current letters **************/
    public List<String> findAllWordsFormedByLetters(String[] letters){
        Node current;
//        if(containBlank(letters)){
//			letters = removeBlank(letters);
//		}
//        for(String letter: letters){
//            System.out.println("letter = "+letter);
//        }
        HashMap<String, Integer> lettersContained = new HashMap<>();
        for(String letter: letters){
            if(lettersContained.containsKey(letter)){
                lettersContained.put(letter, lettersContained.get(letter)+1);
            }else {
                lettersContained.put(letter, 1);
            }
        }
//        for(Map.Entry<String, Integer> entry: lettersContained.entrySet()){
//            System.out.println("lettersContained = "+entry.getKey());
//        }
        List<List<String>> allWords = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: lettersContained.entrySet()){
            if(!entry.getKey().equals(" ")){
                String letter = entry.getKey();
                current = root;
                Node n = current.children.stream()
                        .filter(node -> letter.charAt(0) == node.getLetter())
                        .findFirst()
                        .orElse(null);
                if(n==null){
                    return new ArrayList<>();
                }
                current = n;
                allWords.add(dfs(current, letter, lettersContained, new ArrayList<>()));
            }
        }

        List<String> result = new ArrayList<>();
        for(List<String> words: allWords){
            result.addAll(words);
        }
        return result;
    }
    public boolean wordIsFormedByLettersContained(String prefix, HashMap<String, Integer> letters){
        HashMap<String, Integer> lettersContained = new HashMap<>(letters);
        //System.out.println("PPrefix = "+prefix);
        String[] strArray = prefix.split("");
        boolean contained = true;
        HashMap<String, Integer> prefixHash = new HashMap<>();
        for(String letter: strArray){
            if(prefixHash.containsKey(letter)){
                prefixHash.put(letter, prefixHash.get(letter)+1);
            }else {
                prefixHash.put(letter, 1);
            }
        }
//        for(Map.Entry<String, Integer> entry: prefixHash.entrySet()){
//            System.out.println("getKey = "+entry.getKey());
//            System.out.println("getValue = "+entry.getValue());
//        }
        if(lettersContained.containsKey(" ")){
            //System.out.println("true");
            //exit(0);
            //compare if both key and value of prefixHash are subset of lettersContained
            for(Map.Entry<String, Integer> entry: prefixHash.entrySet()){
                if(!lettersContained.containsKey(entry.getKey()) && (entry.getValue()<=lettersContained.get(" "))){
                    lettersContained.put(" ", lettersContained.get(" ")-entry.getValue());
                }else if(!lettersContained.containsKey(entry.getKey()) && (entry.getValue()>lettersContained.get(" "))){
                    contained = false;
                }
                if(lettersContained.containsKey(entry.getKey())){
                    if(entry.getValue()>lettersContained.get(entry.getKey())){
                        if((entry.getValue()-lettersContained.get(entry.getKey()))<=lettersContained.get(" ")){
                            lettersContained.put(" ", lettersContained.get(" ")-(entry.getValue()-lettersContained.get(entry.getKey())));
                        }else {
                            contained=false;
                        }
                    }
                }
            }
        }else {
            //compare if both key and value of prefixHash are subset of lettersContained
            for(Map.Entry<String, Integer> entry: prefixHash.entrySet()){
                if(!(lettersContained.containsKey(entry.getKey()) && (entry.getValue()<=lettersContained.get(entry.getKey())))){
                    contained=false;
                }
            }
        }
        return contained;
    }
    private List<String> dfs(Node node, String prefix, HashMap<String, Integer> lettersContained, List<String> results) {
        if (node.isEndOfWord() && wordIsFormedByLettersContained(prefix, lettersContained)) {
            results.add(prefix);
            //System.out.println("results = "+results);
        }
        for (int i = 0; i < node.children.size(); i++) {
            Node child = node.children.get(i);
            if (child != null) {
                //System.out.println("prefix + child.letter = "+(prefix+child.letter));
                dfs(child, prefix + child.letter, lettersContained, results);
            }
        }
        return results;
    }
}
