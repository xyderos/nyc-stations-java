import java.util.PriorityQueue;

public class UndirectedWeightedGraph<Item> {

    private int nofVertices;

    private Bag<WeightedEdge>[] edges;

    private IndexTable<Item> table;

    private int[] pathWeight;

    private int[] visitedBy;

    public UndirectedWeightedGraph(int nofVertices) {

        this.nofVertices = nofVertices;

        this.edges = (Bag<WeightedEdge>[]) new Bag[nofVertices];

        this.table = new IndexTable<>(nofVertices);

        this.pathWeight = new int[nofVertices];

        this.visitedBy = new int[nofVertices];

    }

    public void addPath(Item v1, Item v2, int w) {

        int ver1Index = table.add(v1);

        int ver2Index = table.add(v2);

        if (edges[ver1Index] == null) edges[ver1Index] = new Bag<>();

        edges[ver1Index].add(new WeightedEdge(ver2Index, w));

    }

    public Bag<Item> findPath(Item from, Item to) {

        return findPath(from, to, null);

    }

    public Bag<Item> findPath(Item from, Item to, Item via) {

        //get the index of the from Vertex
        int fromIndex = table.add(from);

        //get the index of the to vertex
        int toIndex = table.add(to);

        Bag<Item> path = new Bag<>();

        path.add(to);

        if (via != null) {

            //get the index of the via vertex
            int viaIndex = table.add(via);

            //apply diskstra on the first part of the problem
            traverse(viaIndex);

            if (visitedBy[toIndex] == -1) return new Bag<>();

            for (int current = visitedBy[toIndex]; current != viaIndex; current = visitedBy[current])path.add(table.getItem(current));

            path.add(via);

            //apply dijkstra on the second part of the problem
            traverse(fromIndex);

            if (visitedBy[viaIndex] == -1) return new Bag<>();

            for (int current = visitedBy[viaIndex]; current != fromIndex; current = visitedBy[current]) path.add(table.getItem(current));

        }
        else {

            //find the path without an intermediate station
            traverse(fromIndex);

            if (visitedBy[toIndex] == -1) return new Bag<>();

            for (int current = visitedBy[toIndex]; current != fromIndex; current = visitedBy[current]) path.add(table.getItem(current));

        }

        path.add(from);

        return path;

    }

    private void traverse(int fromIndex) {

        reset();

        PriorityQueue<WeightedEdge> priorityQueue = new PriorityQueue<>();

        // add the first edge in the pqueue
        priorityQueue.add(new WeightedEdge(fromIndex, 0));

        //0 means it is now visited with weight of 0 (self)
        pathWeight[fromIndex] = 0;

        //start checking the queue
        while (priorityQueue.peek() != null) {

            //get the vertex with the highest priority to be examined
            WeightedEdge cVertex = priorityQueue.poll();

            //where is it going
            int cVertexTo = cVertex.to;

            //its weight
            int cVertexW = cVertex.weight;

            //for all the edges adjacent to our vertex
            for (WeightedEdge weightedEdge : edges[cVertexTo]) {

                // edge to the adjacent vertex
                int adjEdgeTo = weightedEdge.to;

                //its weight
                int adjEdgeWeight = weightedEdge.weight;

                //check if we have been here
                boolean visited = pathWeight[adjEdgeTo] != Integer.MAX_VALUE;

                //if the newly calculated weight is smaller than the currently saved weight
                if ((adjEdgeWeight + cVertexW) < pathWeight[adjEdgeTo]) {

                    //update the new weight
                    pathWeight[adjEdgeTo] = adjEdgeWeight + cVertexW;

                    //mark the next vertex that we moved there from the current vertex we are examining
                    visitedBy[adjEdgeTo] = cVertexTo;

                }
                //if we havent been here, add the edge to the queue to be examined
                if (!visited) priorityQueue.add(new WeightedEdge(adjEdgeTo, pathWeight[adjEdgeTo]));

            }
        }
    }

    //reset the distances to the initial values of dijkstras algorithm
    private void reset() {

        for (int i = 0; i <  nofVertices; i++) {

            pathWeight[i] = Integer.MAX_VALUE;

            visitedBy[i] = -1;

        }

    }

    //helper class that represents the weighted edge
    private static class WeightedEdge implements Comparable {

        private int to;

        private int weight;

        public WeightedEdge(int to, int weight) {

            this.to = to;

            this.weight = weight;
        }

        @Override
        public int compareTo(Object o) {
            return Integer.valueOf(weight).compareTo(Integer.valueOf(((WeightedEdge)o).weight));
        }
    }
}
