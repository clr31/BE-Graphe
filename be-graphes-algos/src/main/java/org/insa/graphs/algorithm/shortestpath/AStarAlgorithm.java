package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    protected LabelStar[] labels ; 

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {

        System.out.println("start *\n") ;

        // retrieve data from the input problem (getInputData() is inherited from the
        // parent class ShortestPathAlgorithm)
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();

        final int nbNodes = graph.size();

        // Initialize array of distances.
        labels = new LabelStar[nbNodes];
        for(int i=0; i<nbNodes; i++) {
            labels[i] = new LabelStar(graph.get(i),data.getDestination()) ;
        }

        Node origin = data.getOrigin() ;
        labels[origin.getId()].setCout(0) ;
        System.out.println("cout origine" + labels[origin.getId()].getCost()) ;

        BinaryHeap<Label> tas = new BinaryHeap<>();
        tas.insert(labels[origin.getId()]) ;


        // variable that will contain the solution of the shortest path problem
        ShortestPathSolution solution = null;

        // TODO: implement the Dijkstra Astar algorithm


        while(!tas.isEmpty() && !labels[data.getDestination().getId()].getMarque()){ //ou dest trouvee
    
            //System.out.println("dans while\n") ;
            Label min = tas.deleteMin() ;
            notifyNodeMarked(min.getSommetCourant());
            min.setMarque(true) ;
            //System.out.println("cout : " + min.getCost()) ;

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

        //System.out.println("fini while\n") ;
        if(labels[data.getDestination().getId()].getPere()==null) {
            //System.out.println("debut if\n") ;
            solution = new ShortestPathSolution(data,Status.INFEASIBLE) ;
            //System.out.println("pas de solution\n") ;
        }
        else {
            //System.out.println("debut else\n") ;
            notifyDestinationReached(data.getDestination()) ;
            ArrayList<Arc> arcs = new ArrayList<>() ;
            Arc pere = labels[data.getDestination().getId()].getPere() ;

            while(pere!=null) {
                //System.out.println("dans while pere\n") ;
                arcs.add(pere) ;
                pere = labels[pere.getOrigin().getId()].getPere() ;
            }
            
            //System.out.println("fin while pere\n") ;
            Collections.reverse(arcs) ;
            solution = new ShortestPathSolution(data,Status.OPTIMAL,new Path(graph,arcs)) ;
            System.out.println("solution ok\n") ;
        }
        
        System.out.println("fin retourne path\n") ;
        // when the algorithm terminates, return the solution that has been found
        return solution;
    }

}