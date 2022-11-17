package PW3;

import m1graf2022.Edge;
import m1graf2022.Graf;
import m1graf2022.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaximumFlow {
    Graf g = new Graf();
    List<Edge> lEdge;
    HashMap<Edge,Integer> flow = new HashMap<Edge, Integer>();

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

    public static MaximumFlow fromDotFile(String filename) {
        return fromDotFile(filename,".gv");
    }


    public boolean checkFlowNetwork(){
        //the source s
        if(!g.existsNode(-1)){
            return false;
        }
        //the sink t
        if(!g.existsNode(0)){
            return false;
        }
        for(Edge e : g.getAllEdges()){
            if(!e.isWeighted()){
                return false;
            }
            if(e.getWeight() < 1){
                return false;
            }
        }
        List<Node> nodesAccessibleFromSink = g.getDFS(g.getNode(-1));
        //- 1 car on enleve la source
        if(nodesAccessibleFromSink.size() != g.nbNodes() - 1){
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

    public void residualGraph(){};


}
