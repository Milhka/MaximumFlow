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


//Algorithme d'Edmonds-Karp

public class MaximumFlow{
    Graf g = new Graf();
    int step = 1;
    List<Edge> lEdge;
    HashMap<Edge,Integer> flow = new HashMap<Edge, Integer>();
    public List<List<Node>> AllPath = new ArrayList<>();

    Graf gResi = new Graf();

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


    public void toDotFile(String toPrint,String fileName, String extension) {


        try (PrintWriter out = new PrintWriter(fileName+"."+extension)) {
            out.println(toPrint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    public void solution_1(){
        if(checkFlowNetwork()){
            System.out.println("The graf is not OK");
        }
        FindAllPaths(-1,0);
        System.out.println("\n"+ AllPath.toString()) ;
        int i = 0;
        System.out.println(flowInit());
        List<Node> list1 = new ArrayList<>();
        List<Node> list2 = new ArrayList<>();
        List<Node> list3 = new ArrayList<>();
        List<Node> list4 = new ArrayList<>();
        list1.add(g.getNode(-1));
        list1.add(g.getNode(1));
        list1.add(g.getNode(3));
        list1.add(g.getNode(4));
        list1.add(g.getNode(0));

        list2.add(g.getNode(-1));
        list2.add(g.getNode(1));
        list2.add(g.getNode(3));
        list2.add(g.getNode(0));

        list3.add(g.getNode(-1));
        list3.add(g.getNode(2));
        list3.add(g.getNode(3));
        list3.add(g.getNode(0));

        list4.add(g.getNode(-1));
        list4.add(g.getNode(3));
        list4.add(g.getNode(4));
        list4.add(g.getNode(0));

        int cap = residualCapacity(list1)  ;
        System.out.println(residualGraphAffiche(i,list1,cap));
        System.out.println(flowSuivant(1,list1,cap));
        residualGraphSync(list1, cap);

        cap = residualCapacity(list2)  ;
        System.out.println(residualGraphAffiche(i,list2,cap));
        System.out.println(flowSuivant(2,list2,cap));
        residualGraphSync(list2, cap);

        cap = residualCapacity(list3)  ;
        System.out.println(residualGraphAffiche(i,list3,cap));
        System.out.println(flowSuivant(3,list3,cap));
        residualGraphSync(list3, cap);

        cap = residualCapacity(list4)  ;
        System.out.println(residualGraphAffiche(i,list4,cap));
        System.out.println(flowSuivant(4,list4,cap));
        residualGraphSync(list4, cap);

        /*for(List<Node> l : AllPath){
            int cap = residualCapacity(l)  ;
            System.out.println(residualGraphAffiche(i,l,cap));
            System.out.println(flowSuivant(i,l,cap));
            residualGraphSync(l, cap);
            i++;
        }*/
    }

    public void solution_1(boolean saveFile){
        if(checkFlowNetwork()){
            System.out.println("The graf is not OK");
        }
        FindAllPaths(-1,0);
        System.out.println("\n"+ AllPath.toString()) ;
        int i = 0;
        String residualName = "residualGraph";
        String flowName = "flow";

        for(List<Node> l : AllPath){
            int cap = residualCapacity(l)  ;
            residualName = "residualGraph"+i;
            flowName = "flow"+i;
            toDotFile( residualGraphAffiche(i,l,cap),residualName,"gv");
            toDotFile( flowSuivant(i,l,cap),flowName,"gv");
            System.out.println(residualGraphAffiche(i,l,cap));
            System.out.println(flowSuivant(i,l,cap));

            i++;
        }
    }


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
            if(e.from().equals(-1)){
                r += "s";
            }else if(e.from().equals(0)){
                //Erreur
            }else{
                r += e.from().getId();
            }
            r += " -> ";
            if(e.to().equals(0)){
                r += "t";
            }else if(e.to().equals(-1)){
                //Erreur
            }else{
                r += e.to().getId();
            }
            r+= "[label=\""+e.getWeight()+"\", len="+e.getWeight()+"];\n";
        }
        return r;
    }


    public static MaximumFlow fromDotFile(String filename) {
        return fromDotFile(filename,".gv");
    }


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

    public  List<List<Integer>>  GetAllPath(){
        HashMap<Integer,List<Integer>> map = new HashMap<>();
        List<List<Integer>> l = new ArrayList<>();
        return  l;
    }


    private List<Integer> path;

    public void FindAllPaths ( int src, int dst) {

        // Clear previously stored paths
        path = new ArrayList<Integer>();
        path.clear();

        System.out.print("Source : " + src + " Destination : " + dst);

        path.add(-1);

        DFS (src, dst, path);
    }

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


    public int residualCapacity(List<Node> nodes){
        int i =0;
        int flowDeBase = g.getEdge(nodes.get(i), nodes.get(i+1)).getWeight();
        while(nodes.get(i).getId() != 0){
            int flowSuivant = g.getEdge(nodes.get(i), nodes.get(i+1)).getWeight();
            if (flowSuivant < flowDeBase){
                flowDeBase = flowSuivant;
            }
            i++;
        }
        return flowDeBase;
    }

    public void residualGraphSync(List<Node> nodes, int flowDeBase){
        int i=0;
        while(nodes.get(i).getId() != 0){
            int weightDeBase = gResi.getEdge(nodes.get(i), nodes.get(i+1)).getWeight();
            int reste = weightDeBase-flowDeBase;
            gResi.getEdge(nodes.get(i), nodes.get(i+1)).setWeight(reste);
            if(reste != 0){
                gResi.addEdge(nodes.get(i+1), nodes.get(i), flowDeBase);
            }
            else{
                gResi.removeEdge(nodes.get(i), nodes.get(i+1));
                gResi.addEdge(nodes.get(i+1), nodes.get(i), flowDeBase);
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

    public String residualGraphAffiche(int it, List<Node> chemin, int residualCapacity ){
        StringBuilder r = new StringBuilder();
        r.append("digraph residualGraph").append(it).append(" {");
        r.append("\nrankdir=\"LR\";");
        r.append("label=\"(1) residual graph.\n");
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
        r.append("Residual capacity: " + residualCapacity + ";\n");
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
            r.append("[label=\"").append(e.getWeight()).append("\", len=").append(e.getWeight()).append("];\n");
        }
        return r.toString();
    }


    public String flowSuivant(int it, List<Node> chemin, int residualCapacity){
        StringBuilder r = new StringBuilder();
        r.append("digraph flow").append(it).append(" {");
        r.append("\nrankdir=\"LR\";");
        int ancienit = it-1;
        int i=0;
        r.append("label=\"("+ it +") induced from residual graph " +
                ancienit + ". Value : " + residualCapacity + ";.\n");
        for(Edge e : g.getAllEdges()) {
            if(e.from().getId() == -1){
                r.append("s");
            }else if(e.from().getId() == 0){
                //Erreur
            }else{
                r.append(e.from().getId());
            }
            r.append(" -> ");
            if(e.to().getId() == 0){
                r.append("t");
            }else if(e.to().getId() == -1){
                //Erreur
            }else {
                r.append(e.to().getId());
            }
            System.out.println(chemin.get(i) + " c le from apres : " + e.from() + " c le to mais chemin : " + chemin.get(i+1) + " c le to : " + e.to());
            if(chemin.get(i).equals(e.from()) && chemin.get(i+1).equals(e.to())){//Peut-être meilleur avec list Edge
                r.append("[label=\"" + residualCapacity + "/" +  e.getWeight() + "\", len=").append(e.getWeight()).append("];\n");
                i++;
            }
            else{
                r.append("[label=\"").append(e.getWeight()).append("\", len=").append(e.getWeight()).append("];\n");
            }
        }
        return r.toString();
    }


}
