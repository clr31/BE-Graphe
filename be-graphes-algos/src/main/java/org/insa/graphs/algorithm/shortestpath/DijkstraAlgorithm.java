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

        BinaryHeap<Label> tas = new BinaryHeap<>();
        tas.insert(labels[origin.getId()]) ;


        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra algorithm
        while(!tas.isEmpty() && !labels[data.getDestination().getId()].getMarque()){ //ou dest trouvee
    
            Label min = tas.deleteMin() ;
            notifyNodeMarked(min.getSommetCourant());
            min.setMarque(true) ;

            for(Arc a : min.getSommetCourant().getSuccessors()) {

                if(!data.isAllowed(a)){
                    continue ;
                }
                
                int destArc = a.getDestination().getId() ;
                Label labelY = labels[destArc] ;

                if(labelY.getMarque())
                    continue;

                double oldCost = labelY.getCost() ;
                double newCost = min.getCost() + data.getCost(a) ;

                if(Double.isInfinite(oldCost) && Double.isFinite(newCost)) {
                    notifyNodeReached(a.getDestination()) ;
                }
                if(oldCost > newCost) {

                    if (Double.isFinite(oldCost)) {
                        tas.remove(labels[destArc]) ;
                    }

                    labels[destArc].setCout(newCost) ;
                    labels[destArc].setPere(a) ;

                    tas.insert(labels[destArc]) ;
                }
            }
        }

        if(labels[data.getDestination().getId()].getPere()==null) {
            solution = new ShortestPathSolution(data,Status.INFEASIBLE) ;
            System.out.println("Dijkstra pas de solution\n") ;
        }
        else {
            notifyDestinationReached(data.getDestination()) ;
            ArrayList<Arc> arcs = new ArrayList<>() ;
            Arc pere = labels[data.getDestination().getId()].getPere() ;

            while(pere!=null) {
                arcs.add(pere) ;
                pere = labels[pere.getOrigin().getId()].getPere() ;
            }
            
            Collections.reverse(arcs) ;
            solution = new ShortestPathSolution(data,Status.OPTIMAL,new Path(graph,arcs)) ;
            System.out.println("Dijkstra solution ok\n") ;
        }
        
        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}
