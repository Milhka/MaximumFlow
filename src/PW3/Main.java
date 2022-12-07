package PW3;

import m1graf2022.Graf;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    MaximumFlow m = MaximumFlow.fromDotFile("C:/Users/Skender/Desktop/S7/Graph/TP2/PW3/test");
    System.out.println(m.flowInit());
        System.out.println(m.getMaxFlow());
//    Graf g = Graf.fromDotFile("simpleGraph","gv");
//    System.out.println(g.toDotString());

    }
}
