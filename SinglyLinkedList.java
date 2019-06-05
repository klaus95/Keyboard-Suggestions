/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
//package net.datastructures;

/**
 * A basic singly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class SinglyLinkedList<E> implements Cloneable {
  //---------------- nested Node class ----------------
  /**
   * Node of a singly linked list, which stores a reference to its
   * element and to the subsequent node in the list (or null if this
   * is the last node).
   *

  // instance variables of the SinglyLinkedList
  /** The head node of the list */
  private Node<E> head = null;               // head node of the list (or null if empty)

  /** The last node of the list */
  private Node<E> tail = null;               // last node of the list (or null if empty)

  /** Number of nodes in the list */
  private int size = 0;                      // number of nodes in the list

  /** Constructs an initially empty list. */
  public SinglyLinkedList() { }              // constructs an initially empty list

  // access methods
  /**
   * Returns the number of elements in the linked list.
   * @return number of elements in the linked list
   */
  public int size() { return size; }

  /**
   * Tests whether the linked list is empty.
   * @return true if the linked list is empty, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns (but does not remove) the first element of the list
   * @return element at the front of the list (or null if empty)
   */
  public E first() {             // returns (but does not remove) the first element
    if (isEmpty()) return null;
    return head.getElement();
  }
  /**
   * Returns the element at a specific location (index) in the list
   */
  public E get(int index) {
      Node<E> iElement = head;
    for (int i = 0; i < index; i++) {
        iElement = iElement.next;
    }
    return iElement.getElement();
  }
  
  /*
   * Inputs:
   * Outputs:
   * Purpose:
  Node<E> pointer;
  public Characters getByPriority() {
      if (pointer == tail) {
          pointer = head; //go back to beginning
      } else {
          pointer = pointer.getNext();
      }
      return (Characters) pointer.g
  }*/

  /**
   * Returns the element at a specific location (index) in the list
   */
  public void remove(int index) {
      Node<E> previousElement = head;
      Node<E> nextElement = head;
      Node<E> tailReset = head;
      if (size == 1) {
          head = null;
          tail = null;
          size--;
      } else {
          if (index == 0) {
              removeFirst();
          } else {
            for (int i = 0; i < index - 1; i++) {
                previousElement = previousElement.next;
            }
            for (int i = 0; i <= index; i++) {
                nextElement = nextElement.next;
            }
            previousElement.next = nextElement;
            
            size--;
            
            for (int i = 0; i < size - 1; i++) {
                tailReset = tailReset.next;
            }
            tail = tailReset;
          }
      }
  }
  /**
   * Returns (but does not remove) the last element of the list.
   * @return element at the end of the list (or null if empty)
   */
  public E last() {              // returns (but does not remove) the last element
    if (isEmpty()) return null;
    return tail.getElement();
  }

  /*
   * Inputs: an element that needs to be added into a list.
   * Outputs: none
   * Purpose: used to order the lists by letter priority
   */
  public void add(E e) { //added this
      float priority = ((Characters) e).getPriority();
      Node<E> prev = head;
      Node<E> walk = head;

      if (size == 0) {
          addFirst(e);
          return;
      }

      for (int i = 0; i < size; i++) {
          //element is greater than all elements in list.
          if (((Characters) walk.getElement()).getPriority() < priority && i == 0) {
              addFirst(e);
              return;
          //element is smaller than all other elements in list.
          } else if (((Characters) walk.getElement()).getPriority() > priority && i == size - 1) {
              addLast(e);
              return;
          //element is greater than an element and less than an element.
          } else if (((Characters) prev.getElement()).getPriority() >= priority && ((Characters) walk.getElement()).getPriority() < priority) {
              Node<E> newest = new Node<>(e, prev.getNext());
              prev.setNext(newest);
              size++;
              return;
          } else {
              prev = walk;
              walk = walk.getNext();
          }
      }
  }
  
  // update methods
  /**
   * Adds an element to the front of the list.
   * @param e  the new element to add
   */
  public void addFirst(E e) {                // adds element e to the front of the list
    head = new Node<>(e, head);              // create and link a new node
    if (size == 0)
      tail = head;                           // special case: new node becomes tail also
    size++;
    pointer = head;
  }

  /**
   * Adds an element to the end of the list.
   * @param e  the new element to add
   */
  public void addLast(E e) {                 // adds element e to the end of the list
    Node<E> newest = new Node<>(e, null);    // node will eventually be the tail
    if (isEmpty())
      head = newest;                         // special case: previously empty list
    else
      tail.setNext(newest);                  // new node after existing tail
    tail = newest;                           // new node becomes the tail
    size++;
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeFirst() {                   // removes and returns the first element
    if (isEmpty()) return null;              // nothing to remove
    E answer = head.getElement();
    head = head.getNext();                   // will become null if list had only one node
    size--;
    if (size == 0)
      tail = null;                           // special case as list is now empty
    return answer;
  }

  @SuppressWarnings({"unchecked"})
  public boolean equals(Object o) {
    if (o == null) return false;
    if (getClass() != o.getClass()) return false;
    SinglyLinkedList<E> other = (SinglyLinkedList<E>) o;   // use nonparameterized type
    if (size != other.size) return false;
    Node<E> walkA = head;                               // traverse the primary list
    Node<E> walkB = other.head;                         // traverse the secondary list
    while (walkA != null) {
      if (!walkA.getElement().equals(walkB.getElement())) return false; //mismatch
      walkA = walkA.getNext();
      walkB = walkB.getNext();
    }
    return true;   // if we reach this, everything matched successfully
  }

  @SuppressWarnings({"unchecked"})
  public SinglyLinkedList<E> clone() throws CloneNotSupportedException {
    // always use inherited Object.clone() to create the initial copy
    SinglyLinkedList<E> other = (SinglyLinkedList<E>) super.clone(); // safe cast
    if (size > 0) {                    // we need independent chain of nodes
      other.head = new Node<>(head.getElement(), null);
      Node<E> walk = head.getNext();      // walk through remainder of original list
      Node<E> otherTail = other.head;     // remember most recently created node
      while (walk != null) {              // make a new node storing same element
        Node<E> newest = new Node<>(walk.getElement(), null);
        otherTail.setNext(newest);     // link previous node to this one
        otherTail = newest;
        walk = walk.getNext();
      }
    }
    return other;
  }

  public int hashCode() {
    int h = 0;
    for (Node<E> walk=head; walk != null; walk = walk.getNext()) {
      h ^= walk.getElement().hashCode();      // bitwise exclusive-or with element's code
      h = (h << 5) | (h >>> 27);              // 5-bit cyclic shift of composite code
    }
    return h;
  }

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("");
    Node<E> walk = head;
    while (walk != null) {
      sb.append(walk.getElement());
      if (walk != tail)
        sb.append(" ");
      walk = walk.getNext();
    }
    sb.append("");
    return sb.toString();
  }

  	/*
	 * Used to get next highest priority
	 */
	Node<E> pointer;
	public Node getNextListPriority() {
	    if (pointer == null || pointer.getNext() == null) {
	        pointer = head;
	    } else {
	        pointer = pointer.getNext();
	    }
	    return pointer;
	}
  	/*
	 * Used to get the first priority
	 */
	public Node firstPriority() {
	    return head;
	}
}
