/*
  	Authors (group members): Klaus Cipi, Grace Dolphy, Alekzander Bollig, Brannon Blair
	Email addresses of group members: kcipi2015@my.fit.edu, blairb2014@my.fit.edu, gdolphy2015@my.fit.edu, abollig2014@my.fit.edu
	Group name: A2

	Course: CSE 2010
	Section: 02

  	Description of the overall algorithm:
*/
public class Node<E> {

    /** The element stored at this node */
    private E element;            // reference to the element stored at this node

    /** A reference to the subsequent node in the list */
    Node<E> next;         // reference to the subsequent node in the list
    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> n) {
      element = e;
      next = n;
    }

    // Accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    public E getElement() { return element; }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() { return next; }

    // Modifier methods
    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) { next = n; }
  } 