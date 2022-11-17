package m1graf2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UndirectedGraf extends Graf {

	private final List<Edge> implicitEdge = new ArrayList<>();

	public UndirectedGraf() {
		super();
	}





	 UndirectedGraf(int ... args) {
			 super.adjEdList = new HashMap<>();
			 //this.nodes = new ArrayList <Node>();
			 int cpt = 1;
			 for (int arg : args){
				 if(arg == 0){
					 cpt++;
					 //adjEdList.put(new Node(cpt), new TreeSet<Edge>() );
					 continue;
				 }
				 if(getNode(arg) == null){
					 adjEdList.put(new Node(arg), new ArrayList<>() );
				 }
				 if(getNode(cpt) == null){
					 adjEdList.put(new Node(cpt), new ArrayList<>() );

				 }

				 adjEdList.get(getNode(cpt)).add(new Edge( getNode(cpt),getNode(arg) ));
				// adjEdList.get(getNode(arg)).add(new Edge( getNode(arg),getNode(cpt) ));
			 }
			 for(Edge e : getAllEdges()){
				 if( !existsEdge(e.to(),e.from()) ){
					 implicitEdge.add(new Edge(e.to(),e.from()));
				 }
			 }
		 }




	@Override
	public void addEdge(Node from, Node to){
		if(!existsNode(from) ){
			addNode(from);
		}
		if(!existsNode(to) ){
			addNode(to);
		}
		adjEdList.get(from).add(new Edge(from,to));
		if( !existsEdge(to,from) ) {
			implicitEdge.add(new Edge(to, from));
		}
	}

	@Override
	public void addEdge(int from,int to){
		if(!existsNode(getNode(from)) ){
			addNode(from);
		}
		if(!existsNode(getNode(to)) ){
			addNode(to);
		}
		adjEdList.get(getNode(from)).add(new Edge(getNode(from),getNode(to)));
		if( !existsEdge(to,from) ) {
			implicitEdge.add(new Edge(getNode(to), getNode(from)));
		}
	}

	@Override
	public void addEdge(Node from, Node to,int weight){
		if(!existsNode(from) ){
			addNode(from);
		}
		if(!existsNode(to) ){
			addNode(to);
		}
		adjEdList.get(from).add(new Edge(from,to,weight));
		if( !existsEdge(to,from,weight) ) {
			implicitEdge.add(new Edge(to, from,weight));
		}
	}

	@Override
	public void addEdge(int from,int to,int weight){
		if(!existsNode(from) ){
			addNode(from);
		}
		if(!existsNode(to) ){
			addNode(to);
		}
		adjEdList.get(getNode(from)).add(new Edge(getNode(from),getNode(to),weight));
		if( !existsEdge(getNode(to),getNode(from),weight) ) {
			implicitEdge.add(new Edge(getNode(to), getNode(from),weight));
		}
	}


	private boolean existsEdge(Node u,Node v,int weight){
		if(u == null || v == null){
			return false;
		}
		List<Edge> l =getAllEdges();
		for(Edge e : l){
			if( (e.from().equals(u) && e.to().equals(v)  )    ){
				if(e.isWeighted() && e.getWeight().equals(weight))
				return true;
			}
		}
		return false;
		}

	@Override
	public void addEdge(Edge e){
		if(!existsNode(e.from()) ){
			addNode(e.from());
		}
		if(!existsNode(e.to()) ){
			addNode(e.to());
		}
		adjEdList.get(e.from()).add(e);
		if( !existsEdge(e.to(),e.from()) ) {

			if (e.isWeighted()) {
				implicitEdge.add(new Edge(e.to, e.from, e.weight));

			} else {
				implicitEdge.add(new Edge(e.to, e.from));

			}
		}
	}

	@Override
	public boolean removeEdge(Node from, Node to){
		if(!existsEdge(from,to)){
			return false;
		}
		for(Edge e : adjEdList.get(from) ){
			if(e.from().equals(from) && e.to.equals(to) ){
				adjEdList.get(from).remove(e);
				if(implicitEdge.contains(e.getSymetric())){
					implicitEdge.remove(e.getSymetric());
				}

//				for(Edge etemp : implicitEdge){
//
//				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeEdge(int from, int to) {
		if (!existsEdge(from, to)) {

			return false;
		}
		for (Edge e : adjEdList.get(getNode(from))) {
			if (e.from().equals(getNode(from) ) && e.to.equals(getNode(to))) {
				adjEdList.get(getNode(from)).remove(e);
				if(implicitEdge.contains(e.getSymetric())){
					implicitEdge.remove(e.getSymetric());
				}
				return true;
			}
		}
		return false;
	}








	@Override
	public List<Edge> getOutEdges(Node n){

		List<Edge> lOutEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lOutEdge;
		}

//		List<Edge> l = getAllEdges();
		for(Edge e : getAllEdges()){
			if(e.from().getId() == n.getId() ){
				lOutEdge.add(e);
			}
//			if(e.to().getId() == n.getId()){
//				lOutEdge.add(e);
//			}
		}
		for(Edge e : implicitEdge){
			if(e.from().getId() == n.getId() ){
				lOutEdge.add(e);
			}
		}
		Collections.sort(lOutEdge);
		return lOutEdge;
	}
	@Override
	public List<Edge> getInEdges(Node n){

		List<Edge> lInEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lInEdge;
		}
		List<Edge> l = getAllEdges();
		for(Edge e : l){
			if(e.from().equals(n)){
				lInEdge.add(e);
			}
//			if(e.from.getId() == n.getId()){
//				lInEdge.add(e);
//			}
		}
		for(Edge e : implicitEdge){
			if(e.from().getId() == n.getId() ){
				lInEdge.add(e);
			}
		}
		Collections.sort(lInEdge);

		return lInEdge;
	}

	@Override
	public List<Edge> getOutEdges(int n){
		List<Edge> lOutEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lOutEdge;
		}

//		List<Edge> l = getAllEdges();
		for(Edge e : getAllEdges()){
			if(e.from().getId() == getNode(n).getId() ){
				lOutEdge.add(e);
			}
//			if(e.to().getId() == getNode(n).getId()){
//				lOutEdge.add(e);
//			}
		}
		for(Edge e : implicitEdge){
			if(e.from().getId() == n ){
				lOutEdge.add(e);
			}
		}
		Collections.sort(lOutEdge);

		return lOutEdge;
	}
	@Override
	public int inDegree(Node n){
		int sum = 0;
		for (Edge e : getInEdges(n)){
			sum += 1;
			if(e.isSelfLoop()){
				sum+= 1;
			}
		}
		return sum;
	}
	@Override
	public int outDegree(Node n){
		int sum = 0;
		for (Edge e : getOutEdges(n)){
			sum += 1;
			if(e.isSelfLoop()){
				sum+= 1;
			}
		}
		return sum;
	}
	@Override
	public int degree(Node n){
		int sum = 0;
		for (Edge e : getIncidentEdges(n)){
			sum += 1;
			if(e.isSelfLoop()){
				sum+= 1;
			}
		}
		return sum;
	}
	@Override
	public int inDegree(int n){
		int sum = 0;
		for (Edge e : getInEdges(n)){
			sum += 1;
			if(e.isSelfLoop()){
				sum+= 1;
			}
		}
		return sum;
	}
	@Override
	public int outDegree(int n){
		int sum = 0;
		for (Edge e : getOutEdges(n)){
			sum += 1;
			if(e.isSelfLoop()){
				sum+= 1;
			}
		}
		return sum;
	}
	@Override
	public int degree(int n){
		int sum = 0;
		for (Edge e : getIncidentEdges(n)){
			sum += 1;
			if(e.isSelfLoop()){
				sum+= 1;
			}
		}
		return sum;
	}

	@Override
	public List<Edge> getInEdges(int n){
		List<Edge> lInEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lInEdge;
		}
		List<Edge> l = getAllEdges();
		for(Edge e : l){
			if(e.from().equals(getNode(n)) ){

				lInEdge.add(e);
			}
//			if(e.from.getId() == getNode(n).getId()){
//				lInEdge.add(e);
//			}

		}
		for(Edge e : implicitEdge){
			if(e.from().getId() == n ){
				lInEdge.add(e);
			}
		}
		Collections.sort(lInEdge);

		return lInEdge;
	}

	@Override
	public List<Node> getSuccessors(Node n){
		List<Node> l = new ArrayList<>();
//		for(Edge e : adjEdList.get(n) ){
//			if(!l.contains(e.to)){
//				l.add(e.to);
//			}
//		}
		for(Edge e : getAllEdges()){
			if(e.from().equals(n) ){
				if(!l.contains(e.to())){
					l.add(e.to());
				}
			}
//			if(e.to().equals(n) ){
//				if(!l.contains(e.from())) {
//					l.add(e.from());
//				}
//
//			}
		}
		for(Edge e : implicitEdge){
			if(e.from().equals(n) ){
				if(!l.contains(e.to())) {
					l.add(e.to());
				}
			}
		}
		Collections.sort(l);
		return l;
	}
	@Override
	public List<Node> getSuccessors(int n){
		List<Node> l = new ArrayList<>();
//		for(Edge e : adjEdList.get(n) ){
//			if(!l.contains(e.to)){
//				l.add(e.to);
//			}
//		}
		for(Edge e : getAllEdges()){
			if(e.from().getId() == n ){
				if(!l.contains(e.to())){
					l.add(e.to());
				}
			}
//			if(e.to().equals(n) ){
//				if(!l.contains(e.from())) {
//					l.add(e.from());
//				}
//
//			}
		}
		for(Edge e : implicitEdge){
			if(e.from().equals(getNode(n)) ){
				l.add(e.to());
			}
		}
		Collections.sort(l);
		return l;
	}
	@Override
	public List<Node> getSuccessorsMulti(int n){
		List<Node> l = new ArrayList<>();
//		for(Edge e : adjEdList.get(n) ){
//			if(!l.contains(e.to)){
//				l.add(e.to);
//			}
//		}
		for(Edge e : getAllEdges()){
			if(e.from().equals(getNode(n)) ){
					l.add(e.to());

			}
//			if(e.to().equals(n) ){
//				if(!l.contains(e.from())) {
//					l.add(e.from());
//				}
//
//			}
		}
//		for(Edge e : implicitEdge){
//			if(e.from().equals(getNode(n)) ){
//				l.add(e.to());
//			}
//		}
		Collections.sort(l);
		return l;
	}
	@Override
	public List<Node> getSuccessorsMulti(Node n){
		List<Node> l = new ArrayList<>();
//		for(Edge e : adjEdList.get(n) ){
//			if(!l.contains(e.to)){
//				l.add(e.to);
//			}
//		}
		for(Edge e : getAllEdges()){
			if(e.from().equals(n) ){
				l.add(e.to());

			}
//			if(e.to().equals(n) ){
//				if(!l.contains(e.from())) {
//					l.add(e.from());
//				}
//
//			}
		}
		for(Edge e : implicitEdge){
			if(e.from().equals(n) ){
				l.add(e.to());
			}
		}
		Collections.sort(l);
		return l;
	}


	@Override
	public List<Edge> getIncidentEdges(Node n){
		List<Edge> l = getInEdges(n);
		if(!existsNode(n)){
			return l;
		}
		l.contains(getOutEdges(n));

		Collections.sort(l);
		return(l);
	}

	@Override
	public List<Edge> getIncidentEdges(int n){
		List<Edge> l = getInEdges(n);
		if(!existsNode(n)){
			return l;
		}
		//l.contains(getOutEdges(n));

		Collections.sort(l);
		return(l);
	}

	@Override
	public UndirectedGraf getReverse(){
		return this;
	}


	private List<Edge> getOutEdgesClosure(Node n){
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
	private List<Edge> getInEdgesClosure(Node n){
		List<Edge> lOutEdge = new ArrayList<>();
		if(!existsNode(n)){
			return lOutEdge;
		}
		List<Edge> l = getAllEdges();
		for(Edge e : l){
			if(e.to().equals(n)){
				lOutEdge.add(e);
			}

		}
		return lOutEdge;
	}


	@Override
	public UndirectedGraf getTransitiveClosure(){
		UndirectedGraf graf = new UndirectedGraf();
		for(Node n : this.getAllNodes()){
			graf.addNode(n);
		}
		for(Edge e : this.getAllEdges()){
			graf.addEdge(e);
		}

		removeSelfLoops(graf);


		removeMultiEdges(graf);



		for(Node u : graf.getAllNodes()){

			List<Node> l = getDFS(u);

			for(Node n : l){
				if(!graf.existsEdge(u,n) && !graf.existsEdge(n,u) ){
					if(!u.equals(n)){
						graf.addEdge(u,n);
					}
				}
			}
		}

		return graf;
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


	private void removeSelfLoops(Graf g){
		for(Node n : g.getAllNodes()){
			for(Edge e : g.getOutEdges(n)){
				if(e.isSelfLoop()){
					g.removeEdge(e.from,e.to);
				}
			}
		}
	}

	private HashMap<Node,Integer> getAllMultipleEdge(Graf g, Node n){
		HashMap<Node,Integer> multipleEdge = new HashMap<>();
		List<Node> delete = new ArrayList<>();
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
	public static UndirectedGraf fromDotFile(String filename) {
		return fromDotFile(filename,".gv");
	}

	/**
	 * From dot file graf.
	 *
	 * @param filename  the filename
	 * @param extension the extension
	 * @return the graf
	 */
	public static UndirectedGraf fromDotFile(String filename, String extension) {
		UndirectedGraf res = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename+extension));

			//Compute the Graf considering its file
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();


				Pattern p2 = Pattern.compile("graph");
				Pattern lineFormat;

				Matcher m2 = p2.matcher(line);
				Matcher lineMatcher;
				Matcher lineMatcherIsolate;
				Pattern lineFormatIsolate ;


				if(m2.find()) {
					res = new UndirectedGraf();
					lineFormat = Pattern.compile("^([0-9]+)\\s--\\s([0-9]+)\\s*(\\[label=[ -~]+,\\slen=(:?[0-9]+)+\\])?\\s*;?$");
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



}