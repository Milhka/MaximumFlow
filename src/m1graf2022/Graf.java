package m1graf2022;


import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.*;
import java.io.*;


/**
 * The type Graf.
 */
public class Graf {


	/**
	 * The Adj ed list.
	 */
	protected Map<Node, ArrayList<Edge>> adjEdList;

	/**
	 * Instantiates a new Graf.
	 */
	Graf() {
		this.adjEdList = new HashMap<>();

	}

	/**
	 * Constructor for a Graf with a Successor array
	 *
	 * @param args a Successor Array representation of a graf
	 */
	public Graf(int ... args){
		this.adjEdList = new HashMap<>();
		int cpt = 1;
		for (int arg : args){
			if(arg == 0){
				cpt++;
				continue;
			}
			if(getNode(arg) == null){
				adjEdList.put(new Node(arg), new ArrayList<>() );
			}
			if(getNode(cpt) == null){
				adjEdList.put(new Node(cpt), new ArrayList<>() );

			}

			adjEdList.get(getNode(cpt)).add(new Edge( getNode(cpt),getNode(arg) ));


		}
	}

	/**
	 * Gets the number of node of the graf
	 *
	 * @return the number of node
	 */
	public int nbNodes(){
		return adjEdList.size();
	}


	/**
	 * Check if a node is existing in the graf
	 *
	 * @param n the node
	 * @return a boolean
	 */
	public boolean existsNode(Node n){
		return adjEdList.containsKey(n);
	}

	/**
	 * Check if a node is existing in the graf
	 *
	 * @param n id of the node
	 * @return a boolean
	 */
	public boolean existsNode(int n){
		Node node = getNode(n);
		if(node == null){
			return false;
		}
		return adjEdList.containsKey(node);
	}

	/**
	 * Gets a node of the graf by the id node
	 *
	 * @param id of the node
	 * @return a node
	 */
	public Node getNode(int id){
		for(Node n : adjEdList.keySet()){

			if(n.getId() == id){
				return n;
			}
		}
		return null;
	}

	/**
	 * Add a node if is not already existing in the graf
	 *
	 * @param n the node to add
	 * @return a boolean who indicate if the node was adding or not
	 */
	public boolean addNode(Node n){
		if(! existsNode(n)){
			adjEdList.put(n, new ArrayList<>());
			return true;
		}
		return false;
	}

	/**
	 * Add a node to the graf if no already existing
	 *
	 * @param id the id of node to add
	 * @return a boolean who indicate if the node was added or not
	 */
	public boolean addNode(int id){
		if(! existsNode(getNode(id))){
			adjEdList.put(new Node(id), new ArrayList<>());
			return true;
		}
		return false;
	}

	/**
	 * Remove a node to the graf
	 *
	 * @param n the node to remove
	 * @return a boolean who indicate if the node was removed or not
	 */
	public boolean removeNode(Node n){
		if(!existsNode(n)){
			return false;
		}else{
			adjEdList.remove(n);

			for(ArrayList<Edge> h : adjEdList.values()){
				List<Edge> rEdge = new ArrayList<>();
				for(Edge e : h){
					if(e.to.equals(n)){
						rEdge.add(e);
					}
				}

				for(Edge eToRemove : rEdge){
					h.remove(eToRemove);
				}


			}
//			nodes.remove(n);
			return true;
		}
	}

	/**
	 * Remove a node to the graf
	 *
	 * @param id the id of node to remove
	 * @return a boolean who indicate if the node was removed or not
	 */
	public boolean removeNode(int id) {
		return removeNode(getNode(id));
	}

	/**
	 * Gets a list of every direct successor for one node
	 *
	 * @param n a node
	 * @return the list of successor of n
	 */
//a voir
	public List<Node> getSuccessors(Node n){
		List<Node> l = new ArrayList<>();
		for(Edge e : adjEdList.get(n) ){
			if(!l.contains(e.to)){
				l.add(e.to);
			}
		}
		return l;
	}

	/**
	 * Gets a list of every direct successor for one node
	 *
	 * @param n the id of a node
	 * @return the list of successor of n
	 */
	public List<Node> getSuccessors(int n){
		List<Node> l = new ArrayList<>();
		for(Edge e : adjEdList.get(getNode(n)) ){
			if(!l.contains(e.to)){
				l.add(e.to);
			}
		}
		return l;
	}

	/**
	 * Gets a list of every direct successor for one node,
	 * accept multiple edge (with same vertices several times)
	 *
	 *
	 * @param n a node
	 * @return the list of successor of n
	 */
//a voir
	public List<Node> getSuccessorsMulti(Node n){
		List<Node> l = new ArrayList<>();
		for(Edge e : adjEdList.get(n) ){
				l.add(e.to);

		}
		return l;
	}

	/**
	 * Get a list of every direct successor for one node,
	 * accept multiple edge ( with same vertices) several times
	 *
	 * @param n a node id
	 * @return the list of successor of n
	 */
	public List<Node> getSuccessorsMulti(int n){
		List<Node> l = new ArrayList<>();
		for(Edge e : adjEdList.get(getNode(n)) ){
				l.add(e.to);
		}
		return l;
	}

	/**
	 * Indicate if two nodes of the graf are adjacent,
	 * if exist edges between those two nodes
	 *
	 * @param u a first node
	 * @param v a second node
	 * @return a boolean who indicate if the two nodes are adjacent
	 */
	public boolean adjacent(Node u, Node v){
		for(Edge e : adjEdList.get(u) ){
			if(e.to().equals(v)){
				return true;
			}
		}
		for(Edge e : adjEdList.get(v) ){
			if(e.to().equals(u)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicate if two nodes of the graf are adjacent,
	 * if exist edges between those two nodes
	 *
	 * @param u a first id node
	 * @param v a second id node
	 * @return a boolean who indicate if the two nodes are adjacent
	 */
	public boolean adjacent(int u, int v){
		for(Edge e : adjEdList.get(getNode(u)) ){
			if(e.to.equals(getNode(v))){
				return true;
			}
		}
		for(Edge e : adjEdList.get(getNode(v)) ){
			if(e.to.equals(getNode(u))){
				return true;
			}
		}
		return false;
	}


	/**
	 * Get all nodes of the graf
	 *
	 * @return a list of all nodes
	 */
	public List<Node> getAllNodes(){
		List<Node> l = new ArrayList<Node>();
		for(Node n : adjEdList.keySet()){
			l.add(n);

		}
		return l;
	}

	/**
	 * Gets the largest id node of the graf.
	 *
	 * @return the largest id node
	 */
	public int largestNodeId(){
		List<Node> l = getAllNodes();
		Collections.sort(l);
		return l.get(l.size() - 1).getId();
	}

	/**
	 * Gets the smallest id node of the graf.
	 *
	 * @return the smallest id node
	 */
	public int smallestNodeId(){
		List<Node> l = getAllNodes();
		Collections.sort(l);
		return l.get(0).getId();
	}

	/**
	 * Gets the number of edge of the graf
	 *
	 * @return the number of edge
	 */
	public int nbEdges(){
		int nb = 0;
		for( Entry<Node, ArrayList<Edge>> e : adjEdList.entrySet()){
			nb  += e.getValue().size();
		}
		return nb;
	}

	/**
	 * Indicate if an edge(u,v) exists in the graf,
	 *
	 * @param u a node
	 * @param v a node
	 * @return  boolean who indicate if the edge exists
	 */
	public boolean existsEdge(Node u, Node v) {
		if(u == null || v == null){
			return false;
		}
		List<Edge> l =getAllEdges();
		for(Edge e : l){
			if( (e.from().equals(u) && e.to().equals(v)  )  ){
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicate if an edge(u,v) exists in the graf,
	 *
	 * @param u a id node
	 * @param v a id node
	 * @return  boolean who indicate if the edge exists
	 */
	public boolean existsEdge(int u,int v) {
		if(getNode(u) == null || getNode(v) == null){
			return false;
		}
		List<Edge> l =getAllEdges();
		for(Edge e : l){
			if( (e.from().equals(getNode(u)) && e.to().equals(getNode(v))  )   ){
				return true;
			}
		}
		return false;
	}

	/**
	 * Add an edge(from,to) to the graf
	 *
	 * @param from a node
	 * @param to a node
	 */
	public void addEdge(Node from, Node to){
		if(!existsNode(from) ){
			addNode(from);
		}
		if(!existsNode(to) ){
			addNode(to);
		}
		adjEdList.get(from).add(new Edge(from,to));

	}

	/**
	 * Add an edge(from,to) to the graf
	 *
	 * @param from a id node
	 * @param to a  id node
	 */
//a voir creation edge avec deux integers
	public void addEdge(int from,int to){
		if(!existsNode(getNode(from)) ){
			addNode(from);
		}
		if(!existsNode(getNode(to)) ){
			addNode(to);
		}
		adjEdList.get(getNode(from)).add(new Edge(getNode(from),getNode(to)));

	}

	/**
	 * Add an edge(from,to) with a weight to the graf
	 *
	 * @param from   a node
	 * @param to     a node
	 * @param weight the weight
	 */
	public void addEdge(Node from, Node to,int weight){
		if(!existsNode(from) ){
			addNode(from);
		}
		if(!existsNode(to) ){
			addNode(to);
		}
		adjEdList.get(from).add(new Edge(from,to,weight));

	}

	/**
	 * Add an edge(from,to) with a weight to the graf
	 *
	 * @param from a id node
	 * @param to a  id node
	 * @param weight the weight
	 */
	public void addEdge(int from,int to,int weight){
		if(!existsNode(from) ){
			addNode(from);
		}
		if(!existsNode(to) ){
			addNode(to);
		}
		adjEdList.get(from).add(new Edge(from,to,weight));
	}

	/**
	 * Add an edge e to the graf
	 *
	 * @param e a edge
	 */
	public void addEdge(Edge e){
		if(!existsNode(e.from()) ){
			addNode(e.from());
		}
		if(!existsNode(e.to()) ){
			addNode(e.to());
		}
		adjEdList.get(e.from()).add(e);

	}

	/**
	 * Remove an edge(from,to) if exists in the graf
	 *
	 * @param from a node
	 * @param to   a node
	 * @return a boolean who indicate if the edge was removed
	 */
	public boolean removeEdge(Node from, Node to){
		if(!existsEdge(from,to)){
			return false;
		}
		for(Edge e : adjEdList.get(from) ){
			if(e.from().equals(from) && e.to.equals(to) ){
				 adjEdList.get(from).remove(e);
				 return true;
			}
		}
		return false;
	}

	/**
	 * Remove an edge(from,to) if exists in the graf
	 *
	 * @param from the id node
	 * @param to the id node
	 * @return a boolean who indicate if the edge was removed
	 */
	public boolean removeEdge(int from, int to) {
		if (!existsEdge(from, to)) {
			return false;
		}
		for (Edge e : adjEdList.get(getNode(from))) {
			if (e.from().equals(getNode(from) ) && e.to.equals(getNode(to))) {
				adjEdList.get(getNode(from)).remove(e);
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets all edges start from node n
	 *
	 * @param n a node
	 * @return a list of edges
	 */
	public List<Edge> getOutEdges(Node n){
		List<Edge> lOutEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lOutEdge;
		}
		List<Edge> l = getAllEdges();
		for(Edge e : l){
			if(e.from().equals(n)){
				lOutEdge.add(e);
			}
		}
		return lOutEdge;
	}

	/**
	 * Gets all edges end to node n
	 *
	 * @param n a node
	 * @return a list of edges
	 */
	public List<Edge> getInEdges(Node n){

		List<Edge> lInEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lInEdge;
		}
		List<Edge> l = getAllEdges();
		for(Edge e : l){
			if(e.to().equals(n)){
				lInEdge.add(e);
			}
		}
		return lInEdge;
	}

	/**
	 * Gets all edges start or end at node n
	 *
	 * @param n a node
	 * @return a list of edges
	 */
	public List<Edge> getIncidentEdges(Node n){
		List<Edge> l = getInEdges(n);
		if(!existsNode(n)){
			return l;
		}
		l.addAll(getOutEdges(n));
		return(l);
	}

	/**
	 * Gets all edges start from node with id node at n
	 *
	 * @param n a id node
	 * @return a list of edges
	 */
	public List<Edge> getOutEdges(int n){
		List<Edge> lOutEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lOutEdge;
		}

		for(Edge e : getAllEdges()){
			if(e.from().getId() == getNode(n).getId()){
				lOutEdge.add(e);
			}
		}
		return lOutEdge;
	}

	/**
	 * Gets all edges end to node with id node at n
	 *
	 * @param n a id node
	 * @return a list of edges
	 */
	public List<Edge> getInEdges(int n){
		List<Edge> lInEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lInEdge;
		}
		List<Edge> l = getAllEdges();
		for(Edge e : l){
			if(e.to().equals(getNode(n)) ){

				lInEdge.add(e);
			}
		}
		return lInEdge;
	}

	/**
	 * Gets all edges start or end at node with id node at n
	 *
	 * @param n a id node
	 * @return a list of edges
	 */
	public List<Edge> getIncidentEdges(int n){
		List<Edge> l = getInEdges(n);
		if(!existsNode(n)){
			return l;
		}

		l.addAll(getOutEdges(n));
		return(l);
	}

	/**
	 * Gets all edges.
	 *
	 * @return list of all edges
	 */
	public List<Edge> getAllEdges() {
		List<Edge> l = new ArrayList<>();
		for(Entry<Node, ArrayList<Edge>> e :  adjEdList.entrySet() ){
			for( Edge edge : e.getValue()){
				l.add(edge);
			}
		}
		return l;
	}

	/**
	 * Gets the number of node end to the node n
	 *
	 * @param n the n
	 * @return a number of nodes
	 */
	public int inDegree(Node n){
		return getInEdges(n).size();
	}

	/**
	 * Gets the number of node start from the node n
	 *
	 * @param n the n
	 * @return a number of nodes
	 */
	public int outDegree(Node n){
		return getOutEdges(n).size();
	}

	/**
	 * Gets the number of edge start or end at node with id node at n
	 *
	 * @param n an id node
	 * @return a number of nodes
	 */

	public int degree(Node n){
		return getInEdges(n).size() + getOutEdges(n).size();
	}

	/**
	 * Gets the number of node end to the node with id node at n
	 *
	 * @param n an id node
	 * @return a number of nodes
	 */
	public int inDegree(int n){
		return getInEdges(n).size();
	}

	/**
	 * Gets the number of node start from the node with id node at n
	 *
	 * @param n an id node
	 * @return a number of nodes
	 */
	public int outDegree(int n){
		return getOutEdges(n).size();
	}

	/**
	 * Gets the number of edge start or end at node with id node at n
	 *
	 * @param n an id node
	 * @return a number of nodes
	 */
	public int degree(int n){
		return getInEdges(n).size() + getOutEdges(n).size();
	}

	/**
	 * Return an successor array representation of the graf
	 *
	 * @return an array
	 */
	public int[] toSuccessorArray(){
		int[] SuccessorArray = new int[getAllNodes().size() + getAllEdges().size()];
		int index = 0;
		Map<Node, ArrayList<Edge>>  m = this.adjEdList;
		//Collections.sort(m);
		for(Map.Entry<Node, ArrayList<Edge>> e : m.entrySet()) {
			for(Edge edge : e.getValue()){
				SuccessorArray[index] = edge.to().getId();
				index++;
			}
			SuccessorArray[index] = 0;
			index++;
		}
		return SuccessorArray;
	}

	/**
	 * Return an adjency matrix representation of the graf
	 *
	 * @return an double entry array
	 */
	public int[][] toAdjMatrix(){
		int sizeMatrix = this.largestNodeId();
		int[][] adjMatrix = new int [sizeMatrix][sizeMatrix];

		for(int i = 0; i<sizeMatrix; i++) {
			for(int j = 0; j<sizeMatrix; j++) {
				adjMatrix[i][j] = 0;
			}
		}

		for(Map.Entry<Node, ArrayList<Edge>> entry : this.adjEdList.entrySet()) {
			List<Edge> outNodes = this.getOutEdges(entry.getKey());
			for(Edge edge : outNodes) {
				adjMatrix[entry.getKey().getId()-1][edge.to().getId()-1]++;
			}
		}

		return adjMatrix;
	}


	/**
	 * Compute the transitive closure of the graf.
	 *
	 * @return the new graf
	 */
//dans le dot pas obligé dans l'ordre
//implémenter si pas fais interface comparable pour node etedge
	public Graf getTransitiveClosure(){
		Graf graf = new Graf();
		for(Node n : this.getAllNodes()){
			graf.addNode(n);
		}
		for(Edge e : this.getAllEdges()){
			graf.addEdge(e);
		}

		removeSelfLoops(graf);
		removeMultiEdges(graf);

		for(Node u : graf.getAllNodes()){
			for(Edge edgeIn : graf.getInEdges(u)){
				for( Edge edgeOut : graf.getOutEdges(u) ){
					if(edgeIn.from().getId() != edgeOut.to().getId()){
						if(!graf.existsEdge(edgeIn.from(),edgeOut.to())) {
							graf.addEdge(edgeIn.from(), edgeOut.to());
						}
					}
				}
			}
		}



		return graf;
	}

	private void removeSelfLoops(Graf g){
		for(Node n : g.getAllNodes()){
			for(Edge e : g.getOutEdges(n)){
				if(e.isSelfLoop()){
					g.removeEdge(e.from,e.to);
				}
			}
		}
	}

	private HashMap<Node,Integer> getAllMultipleEdge(Graf g,Node n){
		HashMap<Node,Integer> multipleEdge = new HashMap<Node,Integer>();
		List<Node> delete = new ArrayList<Node>();
		for (Node suc : g.getSuccessorsMulti(n) ){
			if(multipleEdge.containsKey(suc)){
				multipleEdge.replace(suc,multipleEdge.get(suc) + 1);
			}else{
				multipleEdge.put(suc,1);
			}
		}
		for(Map.Entry<Node, Integer> e : multipleEdge.entrySet()) {
			if(e.getValue() == 1){
				delete.add(e.getKey());
			}
		}
		for(Node nDelete : delete){
			multipleEdge.remove(nDelete);
		}
		return multipleEdge;
	}

	private void removeMultiEdges(Graf g){
		for(Node n : getAllNodes()){
			boolean workToDo = true;
			while(workToDo ) {
				HashMap<Node,Integer> allMultiEdges = getAllMultipleEdge(g,n);
				workToDo = false;
				for (Map.Entry<Node, Integer> e : allMultiEdges.entrySet()) {
					int nbCopie = e.getValue();
					if (nbCopie > 1) {
						workToDo = true;
						g.removeEdge(n, e.getKey());

					}
				}

			}
		}
	}


	/**
	 * Compute the reverse algorithm(All edges are unversed) on a copy of the graf
	 *
	 * @return the reversed graf
	 */
	public Graf getReverse(){
		Graf f = new Graf();
		List<Node> allNodes = getAllNodes();
		List<Edge> allEdges = getAllEdges();

		for(Node n : allNodes){
			f.addNode(n);
		}
		for(Edge e : allEdges){
			if(e.isWeighted()){
				f.addEdge(new Edge(e.to(),e.from(),e.getWeight()));
			}else{
				f.addEdge(new Edge(e.to,e.from));
			}
		}

		return f;

	}

	/**
	 * Compute the depth first search algorithm on the graf
	 *
	 * @return the list of each node read through the graf during the DFS
	 */
	public List<Node> getDFS(){
		List<Node> dfs = new ArrayList<>();
		ArrayDeque<Node> pile = new ArrayDeque<>();
		List<Node> allNodes = getAllNodes();
		Collections.sort(allNodes);
		for(Node n : allNodes){

			if(!dfs.contains(n)){
				pile.addFirst(n);
			}

			while(!pile.isEmpty()){

				Node currentN = pile.pollFirst();
				if(!dfs.contains(currentN)){
					dfs.add(currentN);
				}
				List<Node> NodesDesc = getSuccessors(currentN);

				Collections.sort(NodesDesc, Collections.reverseOrder());
				for(Node nodeDesc : NodesDesc){
					if(!dfs.contains(nodeDesc) ){
						pile.addFirst(nodeDesc);
					}
				}
			}
		}

		return dfs;

	}


	public List<Node> getDFS(Node u){
		List<Node> dfs = new ArrayList<>();
		ArrayDeque<Node> pile = new ArrayDeque<>();
		List<Node> allNodes = getAllNodes();
		Collections.sort(allNodes);

		pile.addFirst(u);

		while(!pile.isEmpty()){

			Node currentN = pile.pollFirst();
			if(!dfs.contains(currentN)){
				dfs.add(currentN);
			}
			List<Node> NodesDesc = getSuccessors(currentN);

			Collections.sort(NodesDesc, Collections.reverseOrder());
			for(Node nodeDesc : NodesDesc){
				if(!dfs.contains(nodeDesc) ){
					pile.addFirst(nodeDesc);
				}
			}

		}

		return dfs;
	}





	/**
	 * Compute the breath first search algorithm on the graf
	 *
	 * @return the list of each node read through the graf during the BFS
	 */
	public List<Node> getBFS(){
		List<Node> bfs = new ArrayList<>();
		ArrayDeque<Node> pile = new ArrayDeque<>();
		List<Node> allNodes = getAllNodes();

		for(Node n : allNodes){

			if(!bfs.contains(n)){
				pile.addLast(n);
			}

			while(!pile.isEmpty()){

				Node currentN = pile.pollFirst();
				if(!bfs.contains(currentN)){
					bfs.add(currentN);
				}
				List<Node> NodesDesc = getSuccessors(currentN);
				for(Node nodeDesc : NodesDesc){
					if(!bfs.contains(nodeDesc) ){
						pile.addLast(nodeDesc);
					}
				}
			}
		}

		return bfs;

	}


	/**
	 * Print array.
	 *
	 * @param array the array
	 */
	public void printArray(int[][] array) {
		for(int i = 0; i<array.length; i++) {
			for(int j = 0; j<array[i].length; j++) {
				System.out.print(array[i][j]+" ");
			}
			System.out.print("\n");
		}
	}


	/**
	 * From dot file graf.
	 *
	 * @param filename the filename
	 * @return the graf
	 */
	public static Graf fromDotFile(String filename) {
		return fromDotFile(filename,".gv");
	}


	/**
	 * From dot file graf.
	 *
	 * @param filename  the filename
	 * @param extension the extension
	 * @return the graf
	 */
	public static Graf fromDotFile(String filename, String extension) {
		Graf res = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename+extension));

			//Compute the Graf considering its file
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();


				Pattern p1 = Pattern.compile("digraph");
				Pattern p2 = Pattern.compile("graph");
				Pattern lineFormat;

				Matcher m1 = p1.matcher(line);
				Matcher m2 = p2.matcher(line);
				Matcher lineMatcher;
				Matcher lineMatcherIsolate;
				Pattern lineFormatIsolate ;


				if(m1.find()) {
					res = new Graf();
					lineFormat =  Pattern.compile("^([0-9]+|s|t)\\s->\\s([0-9]+)\\s*(\\[label=[ -~]+,\\slen=(:?[0-9]+|s|t)+\\])?\\s*;?$");
					lineFormatIsolate =  Pattern.compile("^[0-9]+\\s*;?$");
				}
				else if(m2.find()) {
					res = new UndirectedGraf();
					lineFormat = Pattern.compile("^([0-9]+|s|t)\\s--\\s([0-9]+)\\s*(\\[label=[ -~]+,\\slen=(:?[0-9]+|s|t)+\\])?\\s*;?$");
					lineFormatIsolate =  Pattern.compile("^[0-9]+\\s*;?$");
				}
				else {
					throw new RuntimeException();
				}

				String lineCpy = "";

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine().trim();

					lineMatcher = lineFormat.matcher(line);
//					lineMatcherIsolate = lineFormatIsolate.matcher(line);
//					if (lineMatcherIsolate.find()) {
//						res.addNode(Integer.parseInt(lineMatcher.group(1)));
//
//					} else {


						if (lineMatcher.find()) {

							if( lineMatcher.group(1).equals("s") ){
								
							}
							res.addNode(Integer.parseInt(lineMatcher.group(1)));

							res.addNode(Integer.parseInt(lineMatcher.group(2)));

							Edge newEdge;
							if (lineMatcher.group(3) != null) {
								newEdge = new Edge(Integer.parseInt(lineMatcher.group(1)), Integer.parseInt(lineMatcher.group(2)), Integer.parseInt(lineMatcher.group(4)));
							} else {
								newEdge = new Edge(Integer.parseInt(lineMatcher.group(1)), Integer.parseInt(lineMatcher.group(2)));
							}
							res.addEdge(newEdge);

						}else{
							lineMatcherIsolate = lineFormatIsolate.matcher(line);
							if (lineMatcherIsolate.find()) {
								res.addNode(Integer.parseInt(lineMatcherIsolate.group(0)));
							}
						}

				//	}
						//Check correct end of file
						Pattern end = Pattern.compile("}");
						Matcher m = end.matcher(line);
						if (m.find()) {
							return res;
						}

				}

			}
			catch(Exception e) {
				System.out.println("Unable to read the file : "+e.getMessage());
			}
			finally {
				br.close();
			}
		}
		catch(Exception e) {
			throw new RuntimeException();
		}

		return res;
	}


	/**
	 * To dot string string.
	 *
	 * @return the string
	 */
	public String toDotString() {
		String res = "";

		if(this.getClass() == Graf.class) {
			res += "digraph {\n";
		}
		else {
			res += "graph {\n";
		}

		String graphSeparator = this.getClass() == Graf.class ? "->" : "--";
		for(Entry<Node, ArrayList<Edge>> e : adjEdList.entrySet()){
			Collections.sort(e.getValue());
			for(Edge h : e.getValue()){

				res += "\t" + e.getKey().getId()+" "+graphSeparator+" "+h.to.getId();

				if(h.weight != null) {
					res += " [label="+h.weight+", len="+h.weight+"]";
				}

				res+= ";\n";
			}
			if( getIncidentEdges(e.getKey().getId()).isEmpty() ) {
				res +="\t" + e.getKey() +"\n";
			}
		}

		res += "}";
		return res;
	}

	/**
	 * To dot file.
	 *
	 * @param fileName the file name
	 */
	public void toDotFile(String fileName) {
		this.toDotFile(fileName,"gv");
	}

	/**
	 * To dot file.
	 *
	 * @param fileName  the file name
	 * @param extension the extension
	 */
	public void toDotFile(String fileName, String extension) {

		String res = this.toDotString();

		try (PrintWriter out = new PrintWriter(fileName+"."+extension)) {
		    out.println(res);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

//	public UndirectedGraf getTransitiveClosure() {
//		return null;
//	}

//	public long[] toSuccessorArray() {
//		return new long[10];
//	}
}