package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import java.lang.Comparable ;

public class Label {

    private int sommetCourant ; //sommet associé au label
    private boolean marque ; //vrai lorsque coût min de ce sommet est définitivement connu par l'algorithme
    private double coutRealise ; //valeur courante du plus court chemin depuis l'origine vers le sommet
    private Arc pere ; //sommet précédent sur le chemin correspondant au plus court chemin

    public Label(int sommetCourant, boolean marque, double coutRealise, Arc pere) {
        this.sommetCourant = sommetCourant ;
        this.marque = marque ;
        this.coutRealise = coutRealise ;
        this.pere = pere ;
    }

    public int getSommetCourant(){ return this.sommetCourant; } 
    public boolean getMarque(){ return this.marque; } 
    public double getCoutRealise(){ return this.coutRealise; } 
    public Arc getPere(){ return this.pere; } 


    public double getCost() {
        return this.getCoutRealise() ;
    }

    public int compareTo(Label label) {
        double diff = this.getCost()-label.getCost() ;
        if(diff == 0) {
            return 0;
        }
        else if(diff > 0){
            return -1 ;
        }
        else {
            return 1 ;
        }
    }
}

