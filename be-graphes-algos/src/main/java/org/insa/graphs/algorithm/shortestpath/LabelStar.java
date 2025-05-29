package org.insa.graphs.algorithm.shortestpath ;
import org.insa.graphs.model.Node;


public class LabelStar extends Label {

    private double coutEstime ;

    public LabelStar(Node sommetCourant, Node dest){
        super(sommetCourant) ; 
        //definir coutEstime
        this.coutEstime = sommetCourant.getPoint().distanceTo(dest.getPoint()) ;
    } 

    @Override
    public double getTotalCost() {
        return this.getCoutRealise() + coutEstime ;
    }

}