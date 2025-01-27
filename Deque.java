import tester.Tester;

//represents a Deque
class Deque<T> {
  Sentinel<T> header;
  
  //default constructor
  Deque() {
    this.header = new Sentinel<T>();
  }
  
  //constructor with header input
  Deque(Sentinel<T> header) {
    this.header = header;
  }
  
  
  //counts the number of nodes in the deque, not including the sentinel
  int size() {
    return this.header.sizeHelp(false);
  }
  
  //adds an element to the head of the deque
  //where the sentinel's next field is
  void addAtHead(T data) {
    this.header.addAtHead(data);
  }
  
  //adds an element to the tail of the deque
  //where the sentinel's prev field is
  void addAtTail(T data) {
    this.header.addAtTail(data);
  }
  
  //removes an element from the head of the deque
  T removeFromHead() {
    return this.header.removeFromHead();
  }
}

//represents an abstract ANode<T> class
abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;
  
  ANode() {
    this.next = this;
    this.prev = this;
  }
  
  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }
  
  //helper method to find the size of the deque
  abstract int sizeHelp(boolean seenSentinelBefore);
  
  //adds the data point to the head of the deque
  abstract void addAtHead(T data);
  
  //adds the data point to the tail of the deque
  abstract void addAtTail(T data);
  
  //removes an element from the head of the deque
  abstract T removeFromHead();
  
  //helper method to remove an element from the head of the deque
  abstract T removeFromHeadHelp(Sentinel<T> sent);
  
}

//represents a Sentinel<T> class
class Sentinel<T> extends ANode<T> {
  
  //constructor for Sentinel class
  Sentinel() {
    super();
  }

  //helper method that returns the size of the sentinel
  int sizeHelp(boolean seenSentinelBefore) {
    if (seenSentinelBefore) {
      return 0;
    }
    else {
      return 0 + this.next.sizeHelp(true); 
    }
  }
  
  
  //adds the data point to the head of the deque
  void addAtHead(T data) {
    new Node<T>(data, this.next, this);
  }
  
  //adds the data point to the tail of the deque
  void addAtTail(T data) {
    new Node<T>(data, this, this.prev);
  }
  
  //removes an element from the head of the deque
  T removeFromHead() {
    return this.next.removeFromHeadHelp(this);
  }
  
  //helper method for removeFromHead sentinel
  T removeFromHeadHelp(Sentinel<T> sent) {
    throw new RuntimeException("Cannot remove head from Sentinel");
  }
  
}

//represents a Node<T> class
class Node<T> extends ANode<T> {
  T data;
  
  Node(T data) {
    super(null, null);
    this.data = data;
  }
  
  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Not allowed to define a node with null inputs");
    }
    
    this.data = data;
    this.next.prev = this;
    this.prev.next = this;
  }
  
  //helper method that returns the size of the node
  
  int sizeHelp(boolean seenSentinelBefore) {
    return 1 + this.next.sizeHelp(seenSentinelBefore);
  }
  
  //adds the data point to the head of the deque
  void addAtHead(T data) {
    throw new RuntimeException("This method should only be called on a Sentinel");
  }
  
  //adds the data point to the tail of the deque
  void addAtTail(T data) {
    throw new RuntimeException("This method should only be called on a Sentinel");
    
  }

  //removes the element of the deque at the head
  T removeFromHead() {
    // throw error here because this should never be called return null;
    throw new RuntimeException("This method should only be called on a Sentinel");
  }

  
  T removeFromHeadHelp(Sentinel<T> sent) {
    // TODO Auto-generated method stub
    return null;
  }
  
  
}

class ExamplesDeque {
  
  //test for exceptions too
  
  //question: are we supposed to make a new sentinel for every list
  //size help isn't giving us what we expect, why?
  Sentinel<String> sentinelString1;
  Deque<String> deque1;
  Sentinel<String> sentinelString2;
  Node<String> s1;
  Node<String> s2;
  Node<String> s3;
  Node<String> s4;
  Deque<String> deque2;
  Sentinel<String> sentinelString3;
  Node<String> s5;
  Node<String> s6;
  Node<String> s7;
  Node<String> s8;
  Node<String> s9;
  Deque<String> deque3;
  
  void initData() {
    this.sentinelString1 = new Sentinel<String>();
    this.deque1 = new Deque<String>(this.sentinelString1);
    
    this.sentinelString2 = new Sentinel<String>();
    this.s1 = new Node<String>("abc", this.sentinelString2, this.sentinelString2);
    this.s2 = new Node<String>("bcd", this.sentinelString2, this.s1);
    this.s3 = new Node<String>("cde", this.sentinelString2, this.s2);
    this.s4 = new Node<String>("def", this.sentinelString2, this.s3);
    
    this.deque2 = new Deque<String>(this.sentinelString2);
    
    this.sentinelString3 = new Sentinel<String>();
    this.s5 = new Node<String>("dog", this.sentinelString3, this.sentinelString3);
    this.s6 = new Node<String>("sheep", this.sentinelString3, this.s5);
    this.s7 = new Node<String>("cat", this.sentinelString3, this.s6);
    this.s8 = new Node<String>("ant", this.sentinelString3, this.s7);
    this.s9 = new Node<String>("mouse", this.sentinelString3, this.s8);
    
    this.deque3 = new Deque<String>(this.sentinelString3);
  }
  
  //Tests that all the links for one examples are correct
  void testData(Tester t) {
    this.initData();
    t.checkExpect(this.sentinelString2.next, this.s1);
    t.checkExpect(this.s1.prev, this.sentinelString2);
    t.checkExpect(this.s1.next, this.s2);
    t.checkExpect(this.s2.prev, this.s1);
    t.checkExpect(this.s4.next, this.sentinelString2);
    t.checkExpect(this.sentinelString2.prev, this.s4);
    
  }
  
  //tests the size method
  void testSize(Tester t) {
    this.initData();
    t.checkExpect(this.deque1.size(), 0);
    t.checkExpect(this.deque2.size(), 4);
    t.checkExpect(this.deque3.size(), 5);
  }
  
  
  //tests the sizeHelp method
  void testSizeHelp(Tester t) {
    this.initData();
    t.checkExpect(this.sentinelString1.sizeHelp(false), 0);
    t.checkExpect(this.sentinelString1.sizeHelp(true), 0);
    t.checkExpect(this.s1.sizeHelp(false), 8);  //counts the list twice until you read the sentinel twice
    t.checkExpect(this.sentinelString2.sizeHelp(false), 4);
    t.checkExpect(this.s1.sizeHelp(true), 4);
    t.checkExpect(this.s2.sizeHelp(true), 3);
    t.checkExpect(this.s3.sizeHelp(true), 2);
    t.checkExpect(this.s4.sizeHelp(true), 1);
  } 
  
  //tests the addAtHead method
  void testAddAtHead(Tester t) {
    this.initData();
    this.deque1.addAtHead("abc");
    t.checkExpect(this.deque1.header.next, new Node<String>("abc", this.sentinelString1, this.sentinelString1));
    t.checkExpect(this.deque1.header.next.prev, this.sentinelString1);
    t.checkExpect(this.deque1.header.next.next, this.sentinelString1);
    
    this.deque2.addAtHead("cat");
    t.checkExpect(this.sentinelString2.next, new Node<String>("cat", this.s1, this.sentinelString2));
    t.checkExpect(this.sentinelString2.prev, this.s4);
    t.checkExpect(this.sentinelString2.next.next, this.s1);
    t.checkExpect(this.sentinelString2.next.next.next, this.s2);
    
    this.deque3.addAtHead("");
    t.checkExpect(this.sentinelString3.next, new Node<String>("", this.s5, this.sentinelString3));
    t.checkExpect(this.sentinelString3.prev, this.s9);
    t.checkExpect(this.sentinelString3.next.next, this.s5);
    t.checkExpect(this.sentinelString3.next.next.next, this.s6);
  }
  
  //tests the addAtTail method
  void testAddAtTail(Tester t) {
    this.initData();
    this.deque1.addAtTail("abc");
    t.checkExpect(this.deque1.header.prev, new Node<String>("abc", this.sentinelString1, this.sentinelString1));
    t.checkExpect(this.deque1.header.next.prev, this.sentinelString1);
    t.checkExpect(this.deque1.header.next.next, this.sentinelString1);
    
    this.deque2.addAtTail("cat");
    t.checkExpect(this.sentinelString2.prev, new Node<String>("cat", this.sentinelString2, this.s4));
    t.checkExpect(this.sentinelString2.next, this.s1);
    t.checkExpect(this.sentinelString2.prev.prev, this.s4);
    t.checkExpect(this.sentinelString2.prev.prev.prev, this.s3);
    
    this.deque3.addAtTail("");
    t.checkExpect(this.sentinelString3.prev, new Node<String>("", this.sentinelString3, this.s9));
    t.checkExpect(this.sentinelString3.next, this.s5);
    t.checkExpect(this.sentinelString3.prev.prev, this.s9);
    t.checkExpect(this.sentinelString3.prev.prev.prev, this.s8);
  }
  
}