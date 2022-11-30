package PW3;

import m1graf2022.Graf;

public class Main {
    public static void main(String[] args) {
    MaximumFlow m = MaximumFlow.fromDotFile("C:/Users/Skender/Desktop/S7/Graph/TP2/test");
    System.out.println(m.flowInit());
//    Graf g = Graf.fromDotFile("simpleGraph","gv");
//    System.out.println(g.toDotString());

    }
}
