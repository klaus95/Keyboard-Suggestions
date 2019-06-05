import java.util.Comparator;
/*
	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2

	Course: CSE 2010
	Section: 02
	
	Description of the overall algorithm:   A class to override the default comparator.
*/
public class SortPriority implements Comparator<Characters> {

    @Override
    public int compare(Characters o1, Characters o2) {
        if (o1.getPriority() > o2.getPriority()) {
            return -1; //greatest priority will be first
        } else if (o1.getPriority() < o2.getPriority()) {
            return 1; //lest priority will be second
        } else {
            return 0; //same
        }
    }

}
