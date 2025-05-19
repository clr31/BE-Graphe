package org.insa.graphs.algorithm.shortestpath ;
import org.insa.graphs.model.Node;


public class LabelStar extends Label {

    private double coutEstime ;
    private double borneinf ;

    public LabelStar(Node sommetCourant){
        super(sommetCourant) ; 
    } 

// Borne inférieure = cout estimé entre un sommet et la destination
/*borneinf = this.getSommetCourant().getPoint().distance(this.getSommetCourant().getPoint(), this.getPere().getDestination().getPoint()) ;*/


@Override
public double getTotalCost() {
    return this.getCoutRealise() + coutEstime ;
}

/*public void setTotalCost() {
}*/


}