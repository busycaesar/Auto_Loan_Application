package Controller;

import Model.Finance;

// Node to store the finance objects.
class Node {
    
	// Node stores the loan details and the address of the next linked node.
	Finance loanDetails;
    Node 	next;

    Node(Finance _loanDetails) {
        this.loanDetails = _loanDetails;
        this.next 		 = null;
    }
    
}

// List to link the nodes storing finance object.
public class LinkedList {
	
	Node head;
	
	LinkedList(){ this.head = null; }
	
	// Insert a new node at the end of the link.
	public void insert(Finance _loanDetails) {
		
	    Node newNode = new Node(_loanDetails);

	    if (head == null) { head = newNode; }
	    else {
	    	Node current = head;
	        while (current.next != null) { current = current.next; }
	        current.next = newNode;
	        }

	}
	
	// Return an array of the finance object from node.
	public Finance[] getAllData() {
		
		int 	  totalData  = this.count(),
				  index 	 = 0;
		Node 	  current    = head;
		Finance[] storedData = new Finance[totalData];
		
		while(current != null && index < totalData) {
			storedData[index++] = current.loanDetails;
			current 			= current.next;
		}
		
		return storedData;

	}
	
	// Return the finance object at the passed index.
	public Finance getDataAt(int _index) {
		
		Finance _storedData = null;
		Node 	_current    = head;
		int 	_iteration  = 0;
		
		while(_current != null) {
	
			if(_iteration == _index) {				
				_storedData = _current.loanDetails;
				break;
			}
			_current = _current.next;
			_iteration++;
	
		}
	
		return _storedData;
		
	}
	
	// Count the total nodes in the list.
	public int count() {
		
		Node current   = head;
		int totalNodes = 0;
	
		while(current != null) { 
			totalNodes++; 
			current = current.next;
		}
		
		return totalNodes;
	
	}
	
}