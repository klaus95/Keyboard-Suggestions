import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/*
  	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2

	Course: CSE 2010
	Section: 02

  	Description of the overall algorithm:		Letters are given a priority (based on how many times is that letter 
  												entered in the tire), so to find a guess we always pick 
  												the highest priority (based on previous input), and follow down 
  												the trie, picking the highest priority to the end of a word, till 
  												we get three guesses.
*/

public class SmartWord
{   
    String[] guesses = new String[3]; 		//@guesses has a reference an array of guesses.
    Trie trie = new Trie();					//@Trie has a reference a trie data structure.
    ArrayList<Word> alreadyGuesses = new ArrayList<Word>();			//@alreadyGuesses has a reference a to the guesses already made, so there are no duplicates.
    Characters marker; 						//@marker has a reference a Characters that marks where the last letter typed is
    String lettersRevealed = "";			//@lettersRevealed has a reference a string that keeps track of the letter revealed during the execution of the program.

    // initialize SmartWord with a file of English words    // initialize SmartWord with a file of English words
    /*
     * Inputs: dictionary of words
     * Outputs: none
     * Purpose: used to input the dictionary into our data sturcture.
     */public SmartWord(String wordFile) throws FileNotFoundException
    { 
        addWordsToTrie(new File(wordFile), true);
    }
     // process old messages from oldMessageFile
     /*
      * Inputs: the file that contains the old messages
      * Outputs: none
      * Purpose: used to input the words from the old file
      */
    public void processOldMessages(String oldMessageFile) throws FileNotFoundException
    {
        addWordsToTrie(new File(oldMessageFile), false);

        //all words are inputed at this point, so sort all lists.
        sortAllLists(Trie.getAllLists());
        //add guesses to root nodes during pre-processing
        giveRootGuesses();
    }
    /*
     * Inputs: a list that needs sorting
     * Outputs: none
     * Purpose: used to sort each child of a Character by priority for easy access.
     */
    public void sortAllLists(LinkedList<LinkedList<Characters>> listsToSort) {
        while (!listsToSort.isEmpty()) {
            Collections.sort(listsToSort.removeFirst(), new SortPriority());
        }

        listsToSort = null;
        //when empty set to null.
    }
    /*
     * Inputs: none
     * Outputs: none
     * Purpose: Used to give each root node letter three guesses when the first letter is revealed only.
     */
    private void giveRootGuesses() {
        //now that all words from dictionary and old are added, give each root letter,
        //an array of top 3 guesses.
    	short frequency = 0;
    	Characters c;
        Characters[] root = trie.getRootList();
        for (int i = 0; i < root.length; i++) {
            String[] guesses = preorderGuess(root[i], "", new SinglyLinkedList<Word>());
            for (int j = 0; j < guesses.length; j++) {
                root[i].addGuess(guesses[j]); 
                c = trie.getChar(guesses[j], false, (short) 1);			//saving the last position of the letter in the string from guesses.
                frequency = c.getFrequency();							//saving words frequency 
                trie.getChar(guesses[j], true, frequency);				//removing words frequency from the trie
                if (!c.getList().isEmpty()) {
                    c.removeEnd();										//removing the word from the trie
                }
            }
        }
    }

    // based on a letter typed in by the user, return 3 word guesses in an array
    // letter: letter typed in by the user
    // letterPosition:  position of the letter in the word, starts from 0
    // wordPosition: position of the word in a message, starts from 0

    /*
     * Inputs: a newly typed letter, the position of the letter, and the position of the word in the new file
     * Outputs: three guesses based on revealed letters
     * Purpose: to guess a word based on some revealed letters
     */
    public String[] guess(char letter,  int letterPosition, int wordPosition)
    {
        if (letterPosition == 0) {
        	for (int i = 0; i < alreadyGuesses.size(); i++) {
        		trie.getChar(alreadyGuesses.get(i).getWord(), false, (short) alreadyGuesses.get(i).getPriority()).setLast();     //reentering the words to the trie
        	}
        	alreadyGuesses = new ArrayList<Word>();			//updating the alreagyGuesses for the next interation
        	//System.out.println(alreadyGuesses.size());
            lettersRevealed = letter + ""; //start over with first letter of new word
            marker = trie.getFirstChar(letter + ""); //it is a new word, start back at root
            return marker.instantGuesses; //pre set guesses for first word
        } else {
            lettersRevealed += letter; //based your guess off of the revealed letters;
            //find the next revealed letter from previous revealed letter, and move the marker
            if (trie.hasChar(letter, marker.getList()) != null) { //if the newly revealed letter exists already in the previous letter
                marker = trie.hasChar(letter, marker.getList());
                //current guess always starts back at letters revealed, marker
                if (marker == null) {
                    return guesses;
                } else if (marker.getList().size() >= 3) {
                    //pick the highest 3 priority letters. and keep walking till we reach the end of a word.
                    for (int i = 0; i < guesses.length; i++) {   
                        String currentGuess = lettersRevealed;
                        Characters walk = marker.getList().get(i); //walk always starts at the last revealed letter.
                        currentGuess += walk.getLetter();
                        while (!walk.isLast()) {
                            walk = walk.getList().getFirst();
                            currentGuess += walk.getLetter();
                        }
                        guesses[i] = currentGuess;
                        alreadyGuesses.add(new Word(currentGuess, walk.getPriority()));		//saving guesses that are entered in the guesses array
                        if (!walk.getList().isEmpty()) {
                            walk.removeEnd();												//temporarily removing guessed words from the trie
                        }
                        trie.getChar(currentGuess, true, (short) walk.getPriority());		//temporarily removing guessed words frequency from the trie
                    }
                } else if (marker.getList().size() == 2) {
                	Characters savedSpot1 = null;
                	String saved = null;
                	boolean change = true;
                	for (int i = 0; i < guesses.length-1; i++) {   
                        String currentGuess = lettersRevealed;
                        Characters walk = marker.getList().get(i); //walk always starts at the last revealed letter.
                        currentGuess += walk.getLetter();
                        while (!walk.isLast()) {
                            if (change == true && walk.getList().size() >= 2) {
                            	savedSpot1 = walk.getList().get(1); //saved the next freqeunt letter
                                saved = currentGuess + savedSpot1.getLetter();
                            	change = false;
                            }
                            walk = walk.getList().getFirst();
                            currentGuess += walk.getLetter();
                        }
                        guesses[i] = currentGuess;
                    }
                	if (savedSpot1 != null) { //eventually found a starting point
                		while (!savedSpot1.isLast()) {
                			savedSpot1 = savedSpot1.getList().getFirst();
                            saved += savedSpot1.getLetter();
                		}
                        guesses[2] = saved;
                	}

                } else if (marker.getList().size() == 1) {
                	Characters savedSpot1 = null;
                	String saved = null;
                	boolean change = true;
                	for (int i = 0; i < guesses.length-2; i++) {   
                        String currentGuess = lettersRevealed;
                        Characters walk = marker.getList().get(i); //walk always starts at the last revealed letter.
                        currentGuess += walk.getLetter();
                        while (!walk.isLast()) {
                            if (change == true && walk.getList().size() >= 2) {
                            	savedSpot1 = walk.getList().get(1); //saved the next freqeunt letter
                                saved = currentGuess + savedSpot1.getLetter();
                            	change = false;
                            }
                            walk = walk.getList().getFirst();
                            currentGuess += walk.getLetter();
                        }

                        guesses[i] = currentGuess;
                    }
                	if (savedSpot1 != null) { //eventually found a starting point
                		while (!savedSpot1.isLast()) {
                			savedSpot1 = savedSpot1.getList().getFirst();
                            saved += savedSpot1.getLetter();
                		}

                        guesses[1] = saved;
                	}
                }
            }
        }
        return guesses;
    }
    // feedback on the 3 guesses from the user
    // isCorrectGuess: true if one of the guesses is correct
    // correctWord: 3 cases:
    // a.  correct word if one of the guesses is correct
    // b.  null if none of the guesses is correct, before the user has typed in 
    //            the last letter
    // c.  correct word if none of the guesses is correct, and the user has 
    //            typed in the last letter
    // That is:
    // Case       isCorrectGuess      correctWord   
    // a.         true                correct word        guess is correct
    // b.         false               null                continue to guess
    // c.         false               correct word        exhausted guesses
    public void feedback(boolean isCorrectGuess, String correctWord)
    {
    	//feedback needed only during testing
    }
    /*
     * Inputs: a file with words in it, and whether or not this file is a dictionary
     * Outputs: none
     * Purpose: used to add words into our trie data structure.
     */
    private void addWordsToTrie(File file, boolean isDictionaryWord) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String word = scanner.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (word.length() > 6 && isDictionaryWord || word.length() != 0 && !isDictionaryWord) {			//avoiding words from dictionary that are of length less than 6 so that all the none words do no enter the trie.
                trie.addString(word, isDictionaryWord);
            }
        }
        scanner.close();
    }
    /*
     * Inputs: takes in a starting position (node), and empty string that will be used to take in guesses, as well as a 
     * LinkedList to store possible guesses.
     * Outputs: the array of three guesses for a starting root node
     * Purpose: used to get the highest frequented words in the trie.
     */
    public String[] preorderGuess(Characters node, String build, SinglyLinkedList<Word> arrayList) { //returns a string of three words based on a preorder traversal
        build += node.getLetter();
        if (node.isLast()) {
            Word newWord = new Word(build, node.getPriority()); //stores word and frequency in parallel arrays. Removed after completion. (simpler than new data structure or priority queue IMO)
            arrayList.add(newWord);
        } //continue, as "last" nodes may not be leaves
        if (node.getList().size() != 0){ //if node is not leaf
            for (int i = 0; i < node.getList().size(); i++) {
                preorderGuess(node.getList().get(i), build, arrayList); //recursively call function on all children with strings in progress
            }
        }

        for (int i = 0, j = 0; i < 3 && i < arrayList.size(); i++) {
        	//removing old message words that are not actually words
            if (!arrayList.get(j).getWord().equals("don") && !arrayList.get(j).getWord().equals("hill") && !arrayList.get(j).getWord().equals("hi") && !arrayList.get(j).getWord().equals("ca") && !arrayList.get(j).getWord().equals("con") && !arrayList.get(j).getWord().equals("de") && !arrayList.get(j).getWord().equals("ri") && !arrayList.get(j).getWord().equals("realdonald")&& !arrayList.get(j).getWord().equals("real") && !arrayList.get(j).getWord().equals("qu") && !arrayList.get(j).getWord().equals("que") && !arrayList.get(j).getWord().equals("st") && !arrayList.get(j).getWord().equals("so") && !arrayList.get(j).getWord().equals("se") && !arrayList.get(j).getWord().equals("th") && !arrayList.get(j).getWord().equals("un")&& !arrayList.get(j).getWord().equals("vi")&& !arrayList.get(j).getWord().equals("su") && !arrayList.get(j).getWord().equals("pa") && !arrayList.get(j).getWord().equals("mi") && !arrayList.get(j).getWord().equals("rep") && !arrayList.get(j).getWord().equals("va") && !arrayList.get(j).getWord().equals("preside") && !arrayList.get(j).getWord().equals("lo") && !arrayList.get(j).getWord().equals("le") && !arrayList.get(j).getWord().equals("la") && !arrayList.get(j).getWord().equals("jo")) {
                guesses[i] = arrayList.get(j).getWord(); //move top three from arraylist to output array.
            } else {
                i--; //not skip a guess
            }
            if (j < arrayList.size() - 1) {
                j++;
            }
        }
        return guesses;
    }
}