package m1graf2022;

import java.util.Objects;

/**
 * The type Edge.
 */
public class Edge implements Comparable<Edge>{
	/**
	 * From node of the edge(from,to)
	 */
	Node from;
	/**
	 * To node of the edge(from,to)
	 */
	Node to;
	/**
	 * The Weight of the edge
	 */
	Integer weight = null;

	/**
	 * Instantiates a new Edge with two nodes (from,to)
	 *
	 * @param from the first node
	 * @param to   the second node
	 */
	public Edge(Node from,Node to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Instantiates a new Edge with two id node
	 *
	 * @param from the first id node
	 * @param to   the second id node
	 */
	public Edge(int from , int to) {
		this.from = new Node(from);
		this.to =  new Node(to);  
	}

	/**
	 * Instantiates a new Edge with two nodes (from,to) and a weight
	 *
	 * @param from   the from node of an edge
	 * @param to     the to node of an edge
	 * @param weight the weight
	 */
	public Edge(Node from,Node to,int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	/**
	 * Instantiates a new Edge with two id node and a weight
	 *
	 * @param from   the first id node
	 * @param to     the second id node
	 * @param weight the weight
	 */
	public Edge(int from , int to,int weight) {
		this.from = new Node(from);
		this.to = new Node(to);
		this.weight = weight;
	}

	@Override
	public String toString(){
		return from+"->"+to;
	}

	@Override
	 public boolean equals(Object o) {
		 if(o == this) return true;
		 if(! (o instanceof Edge)) {
			 return false;
		 }
		 Edge edge = (Edge) o;
//		 if ( (this.from.getId() == edge.from.getId()) && (this.to.getId() == edge.to.getId()) ) {
//			 return true;
//		 }
		return this.hashCode() == edge.hashCode();
	}
	 
	 @Override
	 public int hashCode() {
		 return Objects.hash(from,to,weight);
//		 return Integer.parseInt(Integer.toString(from.getNumber()) + Integer.toString(y));
	}

		@Override
		public int compareTo(Edge o) {
			if( o.hashCode() > this.hashCode()){
				return -1;
			}
			if( o.hashCode() < this.hashCode()){
				return 1;
			}


			// TODO Auto-generated method stub
			return 0;
		}

	/**
	 * Gets from node.
	 *
	 * @return the node
	 */
	public Node from() {
		return from;
	}

	/**
	 * Gets to node
	 *
	 * @return the node
	 */
	public Node to() {
		return to;
	}

	/**
	 * Gets the symmetric edge.
	 *
	 * @return a symmetric new edge  of the edge
	 */
	public Edge getSymetric() {
		return new Edge(to,from);
	}

	/**
	 * Gets if the from and to of the edge are the same node
	 *
	 * @return a boolean
	 */
	public boolean isSelfLoop(){
		return to.equals(from);
	}

	/**
	 * Gets if the edge is weighted
	 *
	 * @return a boolean
	 */
	public boolean isWeighted(){
		return weight != null;
	}

	/**
	 * Get the weight value
	 *
	 * @return the integer
	 */
	public Integer getWeight(){
		return weight;
	}

	/**
	 * Set the weight value
	 *
	 * @return the integer
	 */
	public void setWeight(int weight){
		 this.weight = weight;
	}

	
}
