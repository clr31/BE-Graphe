package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList ;
import java.util.Collections ;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    protected Label[] labels;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        System.out.println("start\n") ;

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // Initialize array of distances.
        labels = new Label[nbNodes];
        for(int i=0; i<nbNodes; i++) {
            labels[i] = new Label(graph.get(i)) ;
        }

        Node origin = data.getOrigin() ;
        labels[origin.getId()].setCout(0) ;
        System.out.println("cout origine" + labels[origin.getId()].getCost()) ;

        BinaryHeap<Label> tas = new BinaryHeap<>();
        tas.insert(labels[origin.getId()]) ;


        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm
        System.out.println("dans while\n") ;
        while(!tas.isEmpty()){
            Label min = tas.deleteMin() ;
            notifyNodeMarked(min.getSommetCourant());
            min.setMarque(true) ;
            for(Arc a : min.getSommetCourant().getSuccessors()) {
                Label labelY = labels[a.getDestination().getId()] ;
                if(!labelY.getMarque()) {
                    double oldCost = labelY.getCost() ;
                    double newCost = min.getCost() + data.getCost(a) ;
                    if( oldCost > newCost) {
                        labels[a.getDestination().getId()].setCout(newCost) ;
                        labels[a.getDestination().getId()].setPere(a) ;
                        if(Double.isInfinite(oldCost)) {
                            tas.insert(labels[a.getDestination().getId()]) ;
                            notifyNodeReached(a.getDestination()) ;
                        }
                        else {
                            tas.remove(labels[a.getDestination().getId()]) ;
                            tas.insert(labels[a.getDestination().getId()]) ;
                        }
                    }
                }
            }
        }
        if(labels[data.getDestination().getId()].getPere()==null) {
            solution = new ShortestPathSolution(data,Status.INFEASIBLE) ;
        }
        else {
            notifyDestinationReached(data.getDestination()) ;
            ArrayList<Arc> arcs = new ArrayList<>() ;
            Arc pere = labels[data.getDestination().getId()].getPere() ;
            while(pere!=null) {
                arcs.add(pere) ;
                pere = labels[pere.getDestination().getId()].getPere() ;
            }
            Collections.reverse(arcs) ;
            solution = new ShortestPathSolution(data,Status.OPTIMAL,new Path(graph,arcs)) ;
        }
        

        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
