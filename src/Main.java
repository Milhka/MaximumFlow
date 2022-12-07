import PW3.MaximumFlow;
import m1graf2022.Edge;
import m1graf2022.Graf;
import m1graf2022.Node;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {


//        Graf g = new Graf(2, 4, 0, 0, 6, 0, 2, 3, 5, 8, 0, 0, 4, 7, 0, 3, 0, 7, 0);
//        System.out.println(g.toDotString());
//        List<Node> lBFS = g.getBFS();
//        List<Node> lDFS = g.getDFS();
//        System.out.println("--Bfs--");
//        for(Node n : lBFS){
//            System.out.print(n.getId()+" ");
//        }
//        System.out.println("");
//
//        System.out.println("--Dfs--");
//        for(Node n : lDFS){
//            System.out.print(n.getId()+" ");
//        }
//        System.out.println("");
//
//        System.out.println("Remove node 4");
//
//        g.removeNode(g.getNode(4));
//
//
//        System.out.println(g.toDotString());
//        System.out.println("Add node 4");
//        g.addNode(4);
//        System.out.println(g.toDotString());
//        Edge e1 = new Edge(4,5,2);
//        System.out.println("Add edge from 4 to 5 with a weight of 2");
//        g.addEdge(e1);
//        System.out.println(g.toDotString());
//        System.out.println("Add some edges from 1 to 3 , from 1 to 7 , from 1 to 5 ");
//        g.addEdge(1,3);
//        g.addEdge(1,7);
//        g.addEdge(1,5);
//        System.out.println("Add edge by integer with two nonexisting Node ");
//        g.addEdge(9,10);
//        System.out.println(g.toDotString());
//        System.out.println("Get node 9 and 10 ");
//        Node n9 =  g.getNode(9);
//        Node n10 =  g.getNode(10);
//        System.out.println("Delete Edge from 9 to 10");
//        g.removeEdge(n9,n10);
//        System.out.println(g.toDotString());
//        System.out.println("Add edge by nodes");
//        g.addEdge(n9,n10);
//        System.out.println(g.toDotString());
//        System.out.println("Add the same Edges twice from 1 to 2 ");
//        g.addEdge(1,2);
//        System.out.println("Add the a self loop edge from 1 to 1 ");
//        g.addEdge(1,1);
//        System.out.println(g.toDotString());
//        System.out.println("All successor of node 1");
//        System.out.print(g.getSuccessors(1));
//        System.out.println("\nAll successor with multi of node 1");
//        System.out.print(g.getSuccessorsMulti(1));
//        System.out.println("\nThe smallest node id of the graf is "+g.smallestNodeId());
//        System.out.println("The largest node id of the graf is "+g.largestNodeId());
//        System.out.println("Get All out edges of node 1");
//        System.out.print(g.getOutEdges(1));
//        System.out.print("  --> outDegree = "+g.outDegree(1)+"\n");
//        System.out.println("Get All in edges of node 1");
//        System.out.print(g.getInEdges(1));
//        System.out.print("  --> inDegree = "+g.inDegree(1)+"\n");
//        System.out.println("Get All incident(in and out) edges of node 1");
//        System.out.print(g.getIncidentEdges(1));
//        System.out.print("  --> outDegree = "+g.degree(1)+"\n");
//        System.out.println("SucessorArray representation : ");
//
//        for (int i = 0; i < g.toSuccessorArray().length; i++){
//            System.out.print(g.toSuccessorArray()[i]+" ");
//        }
//        System.out.println("");
//        System.out.println("AdjencyMatrix representation : ");
//        for (int i = 0; i < g.toAdjMatrix().length; i++){
//            for (int j = 0; j < g.toAdjMatrix().length; j++) {
//
//                System.out.print(g.toAdjMatrix()[i][j] + " ");
//            }
//            System.out.println("");
//
//        }
//        Graf s = Graf.fromDotFile("simpleGraph");
//        System.out.println(s.getAllEdges().size());
//        s.toDotString();
        //MaximumFlow m = MaximumFlow.fromDotFile("src/PW3/test");
        //System.out.println(m.toString());

        //m.solution_1();

        //MaximumFlow m1 = MaximumFlow.fromDotFile("src/PW3/test2");
        //m1.solution_1();

        MaximumFlow m2 = MaximumFlow.fromDotFile("src/PW3/simpleGraph3");
        int i = m2.getMaxFlow();
        System.out.println(i + "dsfsf" );

//        m.FindAllPaths(-1,0);
//        System.out.println("\n"+ m.AllPath.toString()) ;
//        int i = 0;
//        for(List<Node> l : m.AllPath){
//            int cap = m.residualCapacityNodes(l)  ;
//            System.out.println(m.residualGraph(i,l,cap));
//            System.out.println(m.flowSuivant(i,l,cap));
//
//            i++;
//        }




//
//        File file = new File("test");
//
//        Path currentRelativePath = Paths.get(".");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + file.getAbsolutePath());
//
//        System.out.println(System.getProperty("user.dir"));
    }

}
