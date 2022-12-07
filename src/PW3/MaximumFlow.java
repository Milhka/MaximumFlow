package PW3;

import m1graf2022.Edge;
import m1graf2022.Graf;
import m1graf2022.Node;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.min;


//Algorithme d'Edmonds-Karp


public class MaximumFlow{
    /**
     * Graph where we compute the maximum flow
     */
    Graf g = new Graf();
    /**
     * The Step.
     */
    int step = 1;
    /**
     * a list of edge
     */
    List<Edge> lEdge;
    /**
     * For each edge of graph, a flow is assigned
     */
    HashMap<Edge,List<Integer>> flow = new HashMap<Edge, List<Integer>>();
    /**
     * The Flow pr.
     */
    HashMap<Node, List<Integer>> flowPR = new HashMap<Node, List<Integer>>();
    /**
     * The All path.
     */
    public List<List<Node>> AllPath = new ArrayList<>();

    /**
     * The G resi.
     */
    Graf gResi = new Graf();

    /**
     * The Value.
     */
    int value = 0;

    /**
     * Instantiates a new Maximum flow.
     *
     * @param args the args
     */
    public MaximumFlow(int ...args){

        lEdge = g.getAllEdges();

        //lEdge.add(new Edge(new Node()) );
    }

    /**
     * From dot file graf.
     *
     * @param filename  the filename
     * @param extension the extension
     * @return the graf
     */
    public static MaximumFlow fromDotFile(String filename, String extension) {
        Graf res = null;
        MaximumFlow maxflow = null;
        try {
            System.out.println("alors");

            BufferedReader br = new BufferedReader(new FileReader(filename+extension));
            //Compute the Graf considering its file
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                Pattern p1 = Pattern.compile("digraph");
                Pattern lineFormat;

                Matcher m1 = p1.matcher(line);
                Matcher lineMatcher;
                Matcher lineMatcherIsolate;
                Pattern lineFormatIsolate ;


                if(m1.find()) {

                    maxflow = new MaximumFlow();
                    res = maxflow.g;
                    lineFormat =  Pattern.compile("^([0-9]+|s)\\s->\\s([0-9]+|t)\\s*\\[.*=([0-9]+).*$");
                } else {
                    throw new RuntimeException();
                }


                while (line != null) {

                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine().trim();

                    lineMatcher = lineFormat.matcher(line);



                    if (lineMatcher.find()) {
                        System.out.println("match avec le pattern lineformat");

                        int groupe1 = -3;
                        int groupe2 = -3;
                        int groupe3 = -3;


                        if(lineMatcher.group(1).equals("s")){
                            res.addNode(-1);
                            groupe1 = -1;
                        }else if(lineMatcher.group(1).equals("t")){
                            res.addNode(0);
                            groupe1 = 0;
                        }else{
                            groupe1 = Integer.parseInt(lineMatcher.group(1));
                            res.addNode(Integer.parseInt(lineMatcher.group(1)));
                        }

                        if(lineMatcher.group(2).equals("s")){
                            res.addNode(-1);
                            groupe2 = -1;
                        }else if(lineMatcher.group(2).equals("t")){
                            res.addNode(0);
                            groupe2 = 0;
                        }else{
                            System.out.println(lineMatcher.group(2));
                            groupe2 = Integer.parseInt(lineMatcher.group(2));
                            res.addNode(Integer.parseInt(lineMatcher.group(2)));
                        }


                        Edge newEdge;
                        if (lineMatcher.group(3) != null) {
                            groupe3 = Integer.parseInt(lineMatcher.group(3));
                            newEdge = new Edge(groupe1, groupe2, groupe3);
                        } else {
                            newEdge = new Edge(groupe1, groupe2);
                        }
                        res.addEdge(newEdge);

                    }else{
//                        lineMatcherIsolate = lineFormatIsolate.matcher(line);
//                        if (lineMatcherIsolate.find()) {
//                            res.addNode(Integer.parseInt(lineMatcherIsolate.group(0)));
//                        }
                    }

                    //	}
                    //Check correct end of file
                    Pattern end = Pattern.compile("}");
                    Matcher m = end.matcher(line);
                    if (m.find()) {
                        return maxflow;
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
            System.out.println("probleme de lecture");
            //throw new RuntimeException();
        }

        return maxflow;
    }

    @Override
    public String toString() {
        return g.toDotString();
    }


    /**
     * To dot file.
     *
     * @param toPrint   the to print
     * @param fileName  the file name
     * @param extension the extension
     */
    public void toDotFile(String toPrint,String fileName, String extension) {
        try (PrintWriter out = new PrintWriter(fileName+"."+extension)) {
            out.println(toPrint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Min value int.
     *
     * @param l the l
     * @return the int
     */
    public int minValue(List<Node> l){
        int min = -1;
        for(int i = 0; i < l.size() ; i++ ){
            if(l.get(i) != null && l.get(i).getId() != 0 ){
                if(min == -1)
                    min = g.getEdge(l.get(i),l.get(i+1)).getWeight();
                int currentVal = g.getEdge(l.get(i),l.get(i+1)).getWeight();
                if(currentVal < min){
                    min = g.getEdge(l.get(i),l.get(i+1)).getWeight();
                }
            }


        }
        return min;
    }

    /**
     * Solution 1.
     *
     * @param name the name
     */
/*
    public void bfs(){
        int front = 0;
        int back = 0;

        while(front < back && ){

        }
    }

 */
    public void solution_1(String name){
        if(!checkFlowNetwork()){
            System.out.println("The graf is not OK");
        }else{
            System.out.println("The graf is OK");
        }
        FindAllPaths(-1,0);
        System.out.println("\n"+ AllPath.toString()) ;
        Collections.sort(AllPath,(l1,l2) -> {
            return getMinCheminFirst(l1) - getMinCheminFirst(l2);
        });
        int i = 0;
        toDotFile(flowInit(),name+"_flowInit","gv");

        for(List<Node> l : AllPath){
            System.out.println(i);
            int cap = residualCapacity(l) ;
            if(cap == -1){
                continue;
            }
            toDotFile(residualGraphAffiche(i,l,cap),name+"_residualGraph_"+i,"gv");
            flowSync(l,cap);
            toDotFile(flowSuivant(i,l,cap),name+"_flow_"+i,"gv");
            residualGraphSync(l, cap);
            i++;
        }

    }

    /**
     * Solution 1.
     */
    public void solution_1(){
        if(!checkFlowNetwork()){
            System.out.println("The graf is not OK");
        }else{
            System.out.println("The graf is OK");
        }
        FindAllPaths(-1,0);
        System.out.println("\n"+ AllPath.toString()) ;
        Collections.sort(AllPath,(l1,l2) -> {
            return getMinCheminFirst(l1) - getMinCheminFirst(l2);
        });
        int i = 0;
        System.out.println(flowInit());

        for(List<Node> l : AllPath){
            System.out.println(i);
            int cap = residualCapacity(l) ;
            if(cap == -1){
                continue;
            }
            System.out.println(residualGraphAffiche(i,l,cap));
            flowSync(l,cap);
            System.out.println(flowSuivant(i,l,cap));
            residualGraphSync(l, cap);
            i++;
        }

    }

    /**
     * Get min chemin first int.
     *
     * @param l the l
     * @return the int
     */
    public int getMinCheminFirst(List<Node> l){
        return l.size();
    }

    /**
     * Flow init string.
     *
     * @return the string
     */
    public String flowInit(){
        for (Node n : g.getAllNodes()){
            gResi.addNode(n);
        }
        for (Edge e : g.getAllEdges()){

            gResi.addEdge(new Edge(e.from(), e.to(),e.getWeight()));
        }

        String r = "";
        r += "digraph flow"+step+"_init  {";
        r += "\nrankdir=\"LR\";";
        r += "\nlabel=\"(1) Flow initial. Value: 0\";\n";
        for(Edge e : g.getAllEdges()) {
            if(e.from().getId() == -1){
                r += "s";
            }else if(e.from().equals(0)){
                //Erreur
            }else{
                System.out.println("-----"+e.from());
                r += e.from().getId();
            }
            r += " -> ";
            if(e.to().getId() == 0){
                r += "t";
            }else if(e.to().equals(-1)){
                //Erreur
            }else{
                r += e.to().getId();
            }
            r+= "[label=\""+e.getWeight()+"\", len="+e.getWeight()+"];\n";
        }
        r+= "\n}";
        return r;
    }


    /**
     * From dot file maximum flow.
     *
     * @param filename the filename
     * @return the maximum flow
     */
    public static MaximumFlow fromDotFile(String filename) {
        return fromDotFile(filename,".gv");
    }


    /**
     * Check flow network boolean.
     *
     * @return the boolean
     */
    public boolean checkFlowNetwork(){
        //the source s
        if(!g.existsNode(-1)){
            System.out.println("pas de -1 qui existe ");
            return false;
        }
        //the sink t
        if(!g.existsNode(0)){
            System.out.println("pas de 0 qui existe ");
            return false;
        }
        for(Edge e : g.getAllEdges()){
            if(!e.isWeighted()){
                System.out.println("pas de weight de "+e.toString());
                return false;
            }
            if(e.getWeight() < 1){
                System.out.println("le weight de "+e.toString()+" est égal à "+e.getWeight());
                return false;
            }
        }
        List<Node> nodesAccessibleFromSink = g.getDFS(g.getNode(-1));
        if(nodesAccessibleFromSink.size() != g.nbNodes() ){
            System.out.println("Le nb de noeud accessible:"+nodesAccessibleFromSink.size()+ " est différent du nb de noeud - 1 :"+(g.nbNodes() -1 ));
            return false;
        }


        for(Node n : g.getAllNodes()){
            if(n.getId() != 0 || n.getId() != -1 ){
                List<Node> nodesAccessibleFromCurrentNode = g.getDFS(g.getNode(-1));
                if(!nodesAccessibleFromCurrentNode.contains(g.getNode(0))){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Get all path list.
     *
     * @return the list
     */
    public  List<List<Integer>>  GetAllPath(){
        HashMap<Integer,List<Integer>> map = new HashMap<>();
        List<List<Integer>> l = new ArrayList<>();
        return  l;
    }


    private List<Integer> path;

    /**
     * Find all paths.
     *
     * @param src the src
     * @param dst the dst
     */
    public void FindAllPaths ( int src, int dst) {

        // Clear previously stored paths
        path = new ArrayList<Integer>();
        path.clear();

        System.out.print("Source : " + src + " Destination : " + dst);

        path.add(-1);

        DFS (src, dst, path);
    }

    /**
     * Dfs.
     *
     * @param src  the src
     * @param dst  the dst
     * @param path the path
     */
    public void DFS ( int src , int dst, List<Integer> path) {

        if (src == dst) {
            System.out.print("\nPath : " );
            List<Node> paths = new ArrayList<>();
            for (Integer node : path) {
                paths.add(g.getNode(node));
                System.out.print(node + " ");
            }
            AllPath.add(paths);
        } else {
            for (Node adjnode : g.getSuccessors(src)) {
                path.add(adjnode.getId());
                DFS ( adjnode.getId(), dst, path);
                path.remove(path.size()-1);
            }
        }
    }


    /**
     * Residual capacity int.
     *
     * @param nodes the nodes
     * @return the int
     */
    public int residualCapacity(List<Node> nodes){
        int i =0;

        int flowDeBase = g.getEdge(nodes.get(i), nodes.get(i+1)).getWeight();
        while(nodes.get(i).getId() != 0){
            if(gResi.getEdge(nodes.get(i), nodes.get(i+1)) == null){
                return -1;
            }
            int flowSuivant = gResi.getEdge(nodes.get(i), nodes.get(i+1)).getWeight();
            if (flowSuivant < flowDeBase){
                flowDeBase = flowSuivant;
            }
            i++;
        }
        return flowDeBase;
    }

    /**
     * Flow sync.
     *
     * @param nodes      the nodes
     * @param flowDeBase the flow de base
     */
    public void flowSync(List<Node> nodes, int flowDeBase){
        int i = 0;
        while (nodes.get(i).getId() != 0){
            Edge eG = g.getEdge(nodes.get(i), nodes.get(i+1));
            int weightDeBase = g.getEdge(nodes.get(i), nodes.get(i+1)).getWeight();
            if (flow.containsKey(eG)){
                flow.get(eG).set(0, flow.get(eG).get(0) + flowDeBase);
            }else {
                List<Integer> list = new ArrayList<>();
                list.add(flowDeBase);
                list.add(weightDeBase);
                flow.put(eG, list);
            }
            i++;
        }
    }

    /**
     * Residual graph sync.
     *
     * @param nodes      the nodes
     * @param flowDeBase the flow de base
     */
    public void residualGraphSync(List<Node> nodes, int flowDeBase){
        int i=0;
        while(nodes.get(i).getId() != 0){
            Edge e = gResi.getEdge(nodes.get(i), nodes.get(i+1));
            Edge e2 = g.getEdge(nodes.get(i), nodes.get(i+1));
            int reste = flow.get(e2).get(1)-flow.get(e2).get(0);
            e.setWeight(reste);
            if(reste != 0){
                e.setWeight(reste);
                if(gResi.existsEdge(nodes.get(i+1), nodes.get(i))){
                    gResi.getEdge(nodes.get(i+1), nodes.get(i)).setWeight(flow.get(e2).get(0));
                }
                else{
                    gResi.addEdge(nodes.get(i+1), nodes.get(i), flowDeBase);
                }
            }
            else{
                gResi.removeEdge(nodes.get(i), nodes.get(i+1));
                if(gResi.existsEdge(nodes.get(i+1), nodes.get(i))){
                    gResi.getEdge(nodes.get(i+1), nodes.get(i)).setWeight(flow.get(e2).get(0));
                }
                else{
                    gResi.addEdge(nodes.get(i+1), nodes.get(i), flowDeBase);
                }
            }
            i++;
        }
    }

    /*public int residualCapacity(List<Edge> edges){
        Edge edge = edges.iterator().next();
        int flowDeBase = flow.get(edge);
        for (Edge e: edges) {
            if (flow.get(e) < flowDeBase){
                flowDeBase = flow.get(e);
            }
        }
        return flowDeBase;
    }*/

    /**
     * Residual graph affiche string.
     *
     * @param it               the it
     * @param chemin           the chemin
     * @param residualCapacity the residual capacity
     * @return the string
     */
    public String residualGraphAffiche(int it, List<Node> chemin, int residualCapacity ){
        int i =0;
        StringBuilder r = new StringBuilder();
        r.append("digraph residualGraph").append(it).append(" {");
        r.append("\nrankdir=\"LR\";\n");
        r.append("label=\"("+ it + ") residual graph.\n");
        r.append("Augmenting path: [");
        for (Node n : chemin){
            if(n.getId() == -1){
                r.append("s,");
            } else if (n.getId() == 0) {
                r.append("t].\n");
            } else{
                r.append(n.getId() + ", ");
            }
        }
        r.append("Residual capacity: " + residualCapacity + "\";\n");
        for(Edge e : gResi.getAllEdges()) {
            if(e.from().getId() == (-1)){
                r.append("s");
            }else if(e.from().getId() == 0){
                r.append("t");
            }else{
                r.append(e.from().getId());
            }
            r.append(" -> ");
            if(e.to().getId() == 0){
                r.append("t");
            }else if(e.to().getId() == (-1)){
                r.append("s");
            }else{
                r.append(e.to().getId());
            }
            if (chemin.get(i).getId() != 0){
                if(chemin.get(i).equals(e.from()) && chemin.get(i+1).equals(e.to())){//Peut-être meilleur avec list Edge
                    if (e.getWeight() == residualCapacity){
                        r.append(" [label=\"" + e.getWeight() + "\", len=").append(e.getWeight()).append(", fontcolor=\"red\", penwidth=3, color=\"blue\"]" + ";\n");
                    }else{
                        r.append(" [label=\"" + e.getWeight() + "\", len=").append(e.getWeight()).append(", penwidth=3, color=\"blue\"]" + ";\n");
                    }
                    i++;
                }
                else{
                    r.append(" [label=\"").append(e.getWeight()).append("\", len=").append(e.getWeight()).append("];\n");
                }
            }
            else{
                r.append(" [label=\"").append(e.getWeight()).append("\", len=").append(e.getWeight()).append("];\n");
            }
        }
        r.append("}\n");
        return r.toString();
    }


    /**
     * Flow suivant string.
     *
     * @param it     the it
     * @param chemin the chemin
     * @param value  the value
     * @return the string
     */
    public String flowSuivant(int it, List<Node> chemin, int value){
        this.value += value;
        StringBuilder r = new StringBuilder();
        r.append("digraph flow").append(it).append(" {");
        r.append("\nrankdir=\"LR\";\n");
        int ancienit = it-1;
        int i=0;
        r.append("label=\"("+ it +") induced from residual graph " +
                ancienit + ". Value : " + this.value + "\";\n");
        for(Edge e : g.getAllEdges()) {
            if(e.from().getId() == -1){
                r.append("s");
            }else if(e.from().getId() == 0){
                r.append("t");
            }else{
                r.append(e.from().getId());
            }
            r.append(" -> ");
            if(e.to().getId() == 0){
                r.append("t");
            }else if(e.to().getId() == -1){
                r.append("s");
            }else {
                r.append(e.to().getId());
            }
            if (chemin.get(i).getId() != 0){
                if(chemin.get(i).equals(e.from()) && chemin.get(i+1).equals(e.to())){
                    r.append(" [label=\"" + flow.get(e).get(0) + "/" +  flow.get(e).get(1) + "\", len=").append(e.getWeight()).append("];\n");
                        //Peut-être meilleur avec list Edge
                    i++;
                }
                else{
                    if (flow.containsKey(e)){
                        r.append(" [label=\"").append(flow.get(e).get(0)).append("/" + flow.get(e).get(1) + "\", len=").append(e.getWeight()).append("];\n");
                    }else{
                        r.append(" [label=\"").append(e.getWeight()).append("\", len=").append(e.getWeight()).append("];\n");
                    }
                }
            }
            else{
                if (flow.containsKey(e)){
                    r.append(" [label=\"").append(flow.get(e).get(0)).append("/" + flow.get(e).get(1) + "\" len=").append(e.getWeight()).append("];\n");
                }else{
                    r.append(" [label=\"").append(e.getWeight()).append("\", len=").append(e.getWeight()).append("];\n");
                }
            }
        }
        r.append("}\n");
        return r.toString();
    }

    /**
     * Init pre flow.
     */
    void initPreFlow(){
        for (Node n :g.getAllNodes()) {
            if (n.getId() == -1) {
                n.seteFlow(0);
                n.setH(g.getAllNodes().size());
            }else{
                n.seteFlow(0);
                n.setH(0);
            }
        }
        for (Edge e: g.getAllEdges()) {
            e.setFlow(0);
        }
        for (Edge e: g.getOutEdges(-1)) {
            e.setFlow(e.getWeight());
            g.getNode(e.to().getId()).seteFlow(g.getNode(e.to().getId()).geteFlow() + e.getFlow());
            g.addEdge(e.to(), e.from(), 0);
            g.getEdge(e.to().getId(), e.from().getId()).setFlow(-e.getFlow());
        }
    }

    /**
     * Over flow node int.
     *
     * @param nodeList the node list
     * @return the int
     */
    int overFlowNode(List<Node> nodeList){
        for(Node n : nodeList){
            if (n.geteFlow() > 0 ){
                if (n.getId() != -1 && n.getId() != 0) return n.getId();
            }
        }
        return -2;
    }

    /**
     * Update reverse edge flow.
     *
     * @param e    the e
     * @param flow the flow
     */
    void updateReverseEdgeFlow(Edge e, int flow){
        for (Edge e2: g.getOutEdges(e.to().getId())) {
            if (e2.to().getId() == e.from().getId()){
                e2.setFlow(e2.getFlow() - flow);
                return;
            }
        }
            g.addEdge(g.getNode(e.to().getId()), g.getNode(e.from().getId()), 0);
            g.getEdge(g.getNode(e.to().getId()), g.getNode(e.from().getId())).setFlow(flow * (-1));
    }

    /**
     * Push boolean.
     *
     * @param n the n
     * @return the boolean
     */
    boolean push(int n){
        for (Edge e : g.getOutEdges(n)) {
            if ((g.getNode(n).getH() > g.getNode(e.to().getId()).getH()) && !e.getFlow().equals(e.getWeight())){
                int flow = min(e.getWeight() - e.getFlow(), g.getNode(e.from().getId()).geteFlow());
                g.getNode(n).seteFlow(g.getNode(n).geteFlow() - flow);
                g.getNode(e.to().getId()).seteFlow(g.getNode(e.to().getId()).geteFlow() + flow);
                e.setFlow(e.getFlow() + flow);
                updateReverseEdgeFlow(e, flow);
                return true;
            }
        }
        return false;
    }

    /**
     * Relabel.
     *
     * @param n the n
     */
    void relabel(int n){
        int minHeight = Integer.MAX_VALUE;
        for (Edge e : g.getOutEdges(n)){
            if (!e.getFlow().equals(e.getWeight()) && g.getNode(e.to().getId()).getH() < minHeight){
                minHeight = g.getNode(e.to().getId()).getH();
                g.getNode(n).setH(minHeight + 1);
            }
        }
    }

    /**
     * Get max flow int.
     *
     * @return the int
     */
    public int getMaxFlow(){
        initPreFlow();
        while (overFlowNode(g.getAllNodes()) != -2){
            int n = overFlowNode(g.getAllNodes());
            if (!push(n)) relabel(n);
        }

        return g.getNode(0).geteFlow();
    }

}
