package PW3;

import m1graf2022.Edge;
import m1graf2022.Graf;
import m1graf2022.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaximumFlow {
    Graf g = new Graf();
    int step = 1;
    List<Edge> lEdge;
    HashMap<Edge,Integer> flow = new HashMap<Edge, Integer>();
    public List<List<Node>> AllPath = new ArrayList<>();

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

                Pattern p1 = Pattern.compile("digraph flowNetwork");
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

    public String flowInit(){
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


    public String getDFSfromSource(){
        String res = "";
        List<Node> dfs = new ArrayList<>();
        ArrayDeque<Node> pile = new ArrayDeque<>();
        List<Node> allNodes = g.getAllNodes();
        Collections.sort(allNodes);
        for(Node n : allNodes){

            pile.add(g.getNode(-1));
//            if(!dfs.contains(n)){
//                pile.addFirst(n);
//            }

            while(!pile.isEmpty()){

                Node currentN = pile.pollFirst();

                if(!dfs.contains(currentN) || (currentN.getId() == 0 )  ){
                    res += ""+currentN;
                    dfs.add(currentN);
                }
                List<Node> NodesDesc = g.getSuccessors(currentN);

                Collections.sort(NodesDesc, Collections.reverseOrder());
                for(Node nodeDesc : NodesDesc){
                    if(!dfs.contains(nodeDesc) || nodeDesc.getId() == 0 ){
                        pile.addFirst(nodeDesc);
                    }
                }
            }
        }

        return res;

    }




    public void residualGraph(){};


}
