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

        //Permet d'avoir le processus du MaximumFlow dans la console en partant du fichier mis en paramètre.
        MaximumFlow m = MaximumFlow.fromDotFile("src/PW3/simpleGraph3");
        m.solution_1();

        //Permet d'avoir le processus du MaximumFlow dans des fichiers. Le nom est celui mis en paramètre.
        MaximumFlow m2 = MaximumFlow.fromDotFile("src/PW3/simpleGraph3");
        m2.solution_1("MaximumFlow");

        //Permet d'avoir le résultat du MaximumFlow dans la console.
        MaximumFlow m3 = MaximumFlow.fromDotFile("src/PW3/simpleGraph3");
        System.out.println("Le résultat du MaximumFLow du graph est : " + m3.getMaxFlow());

    }

}
