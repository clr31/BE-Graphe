package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList ;
import java.util.Arrays;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // Initialize array of distances.
        Label[] labels = new Label[nbNodes];
        for(int i=0; i<nbNodes; i++) {
            labels[i].setCout(Double.POSITIVE_INFINITY) ;
            labels[i].setMarque(false) ;
            labels[i].setPere(0) ;
        }

        Node origin = data.getOrigin() ;
        labels[origin.getId()].setCout(0) ;

        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        tas.insert(labels[origin.getId()]) ;


        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm
        

        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
