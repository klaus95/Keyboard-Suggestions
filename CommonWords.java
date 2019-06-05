import java.util.ArrayList;
import java.util.Arrays;
/*
	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2

	Course: CSE 2010
	Section: 02
	
	Description of the overall algorithm:	A class with a list of common 2-3 letter words.
*/
public class CommonWords {
	
    static ArrayList<String> duplicates = new ArrayList<String>(
            Arrays.asList("as" , "in", "to", "just", "he", "for", "on", "out", "you", "no", "we", "be", "im", "go", "an", "me", "or", "va", "fi", "under"));
	/*
		Returns true if the string @word is found in the array @duplicates.
	*/
    public static Boolean wordFound(String word) {
        for(int i = 0; i < duplicates.size(); i++) {
            if(word.equals(duplicates.get(i))) {
                return true;
            }
        }
        return false;
    }
}
