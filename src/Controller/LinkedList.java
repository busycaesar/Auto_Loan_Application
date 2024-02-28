package Controller;

import Model.Finance;

class Node {
    
	Finance loanDetails;
    Node next;

    Node(Finance _loanDetails) {
        this.loanDetails = _loanDetails;
        this.next = null;
    }
    
}

public class LinkedList {
	
	Node head;
	
	LinkedList(){ this.head = null; }
	
	public void insert(Finance _loanDetails) {
	       Node newNode = new Node(_loanDetails);

	        if (head == null) {
	            head = newNode;
	        } else {
	            Node current = head;
	            while (current.next != null) {
	                current = current.next;
	            }
	            current.next = newNode;
	        }
	}

}
