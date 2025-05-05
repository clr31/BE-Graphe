package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList ;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    //ajout d'un tableau de label indicé par le numéro du noeud
    private ArrayList<Label> labels ;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();

        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm

        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
