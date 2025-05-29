package org.insa.graphs.algorithm.shortestpath ;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData.Mode;


public class LabelStar extends Label {

    private double coutEstime ;

    public LabelStar(Node sommetCourant, Node dest, Mode mode){
        super(sommetCourant) ; 
        //definir coutEstime
        if(mode==Mode.TIME){
            double vitesse = 130 ; //en km/h
            this.coutEstime = (sommetCourant.getPoint().distanceTo(dest.getPoint())*3600.0) / (vitesse * 1000.0);
        } 
        else { 
            this.coutEstime = sommetCourant.getPoint().distanceTo(dest.getPoint()) ;
        }
    } 

    @Override
    public double getTotalCost() {
        return this.getCoutRealise() + coutEstime ;
    }

}