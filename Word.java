/*
	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2

	Course: CSE 2010
	Section: 02

	Description of the overall algorithm: 	A word data structure.
*/
public class Word extends Characters{
    String word;					//@word has a reference point to string.
    float priority;					//@priority has a reference point to the frequency of a letter.
	/*
		Creates a Word when a string @word and priority @priority is given.
	*/
    public Word(String word, float priority) {
        this.word = word;
        this.priority = priority;
    }
	/*
		Returns priority.
	*/
    public float getPriority() {
        return priority;
    }
	/*
		Returns word.
	*/
    public String getWord() {
        return word;
    }
	/*
		Sets word.
	*/
    public void setWord(String s) {
        word = s;
    }
	/*
		Sets priority.
	*/
    public void setPriority(float p) {
    	priority = p;
    }
}
