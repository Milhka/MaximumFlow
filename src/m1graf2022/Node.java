package m1graf2022;

/**
 * The type Node.
 */
public class Node implements Comparable<Node>{
	private int number;
	/**
	 * The Name.
	 */
	String name;

	/**
	 * Instantiates a new Node with a id node
	 *
	 * @param nb id of the node
	 */
	public Node(int nb) {
		 number = nb;
	 }

	/**
	 * Instantiates a new Node with a id node and a name
	 *
	 * @param nb   id of the node
	 * @param name the name
	 */
	public Node(int nb,String name) {
		 number = nb;
		 this.name = name;
	 }

	/**
	 * Sets number.
	 *
	 * @param number the number
	 */
	public void setNumber(int number) {
		 this.number = number;
	 }

	/**
	 * Gets id node.
	 * @return the id
	 */
	public int getId() {
		 return number ;
	 }

	@Override
	public String toString(){
		return ""+number;
	}


	 @Override
	 public boolean equals(Object o) {
		 if(o == this) return true;
		 if(! (o instanceof Node)) {
			 return false;
		 }
		 Node node = (Node) o;
		 if (this.getId() == node.getId() ) {
			 return true;
		 }
		 return false;
	 }
	 
	 @Override
	 public int hashCode() {
		 return number;
	 }
	 public int compareTo(Node node) {
	    if (node.number < number ) {
	      return 1;
	    }
	    if (node.number == number ) {
	       return 0;
	    }
	       return -1;
	    }

}
