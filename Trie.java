import java.util.LinkedList;
/*
	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2
	
	Course: CSE 2010
	Section: 02

	Description of the overall algorithm:		A basic Trie data structure for storing all the words of dictionary.
*/
public class Trie {
    private Characters[] rootList = new Characters[26];			//@rootList has a reference an array that stores the root Characters.
    public static LinkedList<LinkedList<Characters>> listsToSort = new LinkedList<LinkedList<Characters>>();		//@listsToSort has a reference an list that stores all the lists of the letters.

    /*
		Creates an Trie with an array of every letter of the alphabet as a root nodes.
	*/
    public Trie() {
        for (int i = 0; i < 26; i++) {
            //special root node constructor; root will hold 3 guesses for each root letter
            rootList[i] = new Characters((char)(97+i), new String[3]); 
        }
    }
	/*
		Adds string @word to Trie.
	*/
    public void addString(String word, boolean isDictionaryWord) {
        Characters parent = new Characters();
        Characters child = new Characters();
        int length = word.length();
        parent = getFirstChar(word);
        for (int i = 1; i < length; i++) {    									//iterates through all the letters of the word
            if (hasChar(word.charAt(i), parent.getList()) == null) {			//checks if the letter is in the trie
                child = new Characters(word.charAt(i));							//adds the letter to the trie
                listsToSort.add(child.getList());								//stores a copy of the Characters linkedlist.
                parent.addToList(child);										//adds the next letter to the list of the previous letter.
                parent = child;													//update location
            } else { //node existed already
                parent = hasChar(word.charAt(i), parent.getList());
                //Revisit an existing node, update frequency
                if (!isDictionaryWord) { //used word in the old file
                    parent.addPriority((float) 1);
                }
                child = parent;													//update location
            }
        }
        //marks last letter of word
        child.setLast();
        child.addFrequency((short) 1);
    }
	/*
		Returns the Characters that has as the letter the last character of the string @word.
	*/
	public Characters getChar(String word, boolean remove, short frequency) {
		Characters c = new Characters();
		int length = word.length();
		if (length == 1) {
			return getFirstChar(word);
		} else {
			c = getFirstChar(word);
			for (int i = 1; i < length; i++) {
				for (int j = 0; j < c.getList().size(); j++) {
					if (word.charAt(i) == c.getList().get(j).getLetter()) {
						c = c.getList().get(j);					//walks through the positions of a word
						if (remove) {
							c.removePriority(frequency);		//removes frequency from every Characters if the remove boolean is true
						} else {
							c.addPriority(frequency);			//adds frequency from every Characters if the remove boolean is false
						}	
						break;
					}
				}
			}
		}
		return c;
	}
	/*
		Returns the Characters if it is found in the list or else returns null.
	*/
    Characters hasChar(char c, LinkedList<Characters> list) {
        Characters pos = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLetter() == c) {
                return list.get(i);
            }
        }
        return pos;
    }
	/*
		Returns the Characters in the first level of the trie that has as the letter the first character of the string @word.
	*/
    public Characters getFirstChar(String word) {
        return rootList[((int) (word.charAt(0))) - 97];
    }
	/*
		Returns the array of Characters that are the root of the Trie.
	*/
    public Characters[] getRootList() {
        return rootList;
    }
	/*
		Returns a LinkedList with all the LinkedLists that are in the Trie.
	*/
    public static LinkedList<LinkedList<Characters>> getAllLists() {
        return listsToSort;
    }
}
