import java.util.LinkedList;
/*
	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2
	
	Course: CSE 2010
	Section: 02
	
	Description of the overall algorithm:     A Characters data structure that serves as the node for the trie and stores
												a letter, letter's priority, whether or not that letter is an end node and
												a LinkedList of below letters (children).
*/
public class Characters {
	/*
		Creates an empty Characters.
	*/
    public Characters() {}
    
    short frequency = 0;						//@frequency has a reference point to the frequency of the word.
    float priority = (float) 0;					//@priority has a reference point to the frequency of this character.
    char letter;							//@letter has a reference point to the letter stored in this character.
    boolean isLast;							//@isLast tells if this character is an ending character or not.

    /*
		Creates an Characters when his letter @letter is given.
    */
    public Characters(char letter) {
        this.letter = letter;
    }
    
    String[] instantGuesses;				//@instantGuesses has a reference to an array that contains 3 guesses.
    int size = 0;
    /*
		Creates an Characters when his letter @letter and guesses @guesses are given.
    */
    public Characters(char letter, String[] guesses) {
        this.letter = letter;
        instantGuesses = guesses;
    }
	/*
		Adds guesses to the data structure.
	*/
    public void addGuess (String guess) {
        instantGuesses[size] = guess;
        size++;
    }
	/*
		Returns the number of guesses stored.
	*/
    public int getSize () {
        return size;
    }
	/*
		Sets priority.
	*/
    public void addPriority(float additional) {
        priority += additional;
    }
    /*
		Removes priority.
    */
	public void removePriority(short p) {
	    priority -= p;
	}
	/*
		Returns the priority of the Characters.
	*/
    public float getPriority() {
        return priority;
    }
	/*
		Adds children to the LinkedList of this Characters.
	*/
    LinkedList<Characters> nextLetter = new LinkedList<Characters>();		//@nextLetter has a reference point to the next letters stored in a LinkedList.
    public void addToList (Characters letter) {
        nextLetter.add(letter);
    }
	/*
		Returns the list that contains all the letters of the LinkedList.
	*/
    public LinkedList<Characters> getList() {
        return nextLetter;
    }
	/*
		Sets the end letter.
	*/
    public void setLast() {
        isLast = true;
    }
	/*
		Removes the end letter.
	*/
    public void removeEnd() {
        isLast = false;
    }
	/*
		Returns true if this Characters's letter is an end letter.
	*/
    public boolean isLast() {
        return isLast;
    }
	/*
		Returns the letter of the Characters.
	*/
    public char getLetter() {
        return letter;
    }
	/*
		Returns the frequency of the word.
	*/
    public short getFrequency() {
    	return frequency;
    }
	/*
		Sets the frequency of the word.
	*/
    public void addFrequency(short f) {
    	frequency += f;
    }
	/*
		Removes the frequency of the word.
	*/
    public void removeFrequency(short f) {
    	frequency -= f;
    }
}
