import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class ShortestPath {

    public static void main(String args[]) throws Exception {

        //main graph data structure
        UndirectedWeightedGraph<Integer> graph;

        Scanner scanner = new Scanner(new File(args[0]));

        //read and store the number of vertices
        String verticesFromFile = scanner.nextLine();

        int nofVertices = Integer.parseInt(verticesFromFile);

        graph = new UndirectedWeightedGraph<>(nofVertices);

        // ingore number of edges
        scanner.nextLine();

        while (scanner.hasNextLine()) {

            String path = scanner.nextLine();

            String[] vertices = path.split(" ");

            //buidl the graph while reading
            graph.addPath(Integer.valueOf(vertices[0]), Integer.valueOf(vertices[1]), Integer.valueOf(vertices[2]));
        }

        int from = Integer.valueOf(args[1]);

        int to = Integer.valueOf(args[2]);

        int via = Integer.valueOf(args[3]);

        //return an iterator of the results, if any
        Iterator<Integer> path = graph.findPath(from, to, (via == 0 ? null : via)).iterator();

        if (!path.hasNext()) System.out.printf("There is no path from %d to %d via %d . \n",from,to,via);

        else {

            System.out.printf("The shortest path from %d to %d via %d is:\n", from, to, via);

            while (path.hasNext()) System.out.print(String.format("%s ", path.next()));
        }
    }
}
