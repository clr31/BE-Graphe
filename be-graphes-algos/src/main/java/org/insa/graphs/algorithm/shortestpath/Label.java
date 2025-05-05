package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {

    private Node sommetCourant ; //sommet associé au label
    private boolean marque ; //vrai lorsque coût min de ce sommet est définitivement connu par l'algorithme
    private double coutRealise ; //valeur courante du plus court chemin depuis l'origine vers le sommet
    private Arc pere ; //sommet précédent sur le chemin correspondant au plus court chemin

    public Label(Node sommetCourant, boolean marque, double coutRealise, Arc pere) {
        this.sommetCourant = sommetCourant ;
        this.marque = marque ;
        this.coutRealise = coutRealise ;
        this.pere = pere ;
    }

    public Node getSommetCourant(){ return this.sommetCourant; } 
    public boolean getMarque(){ return this.marque; } 
    public double getCoutRealise(){ return this.coutRealise; } 
    public Arc getPere(){ return this.pere; } 


    public void setMarque(boolean Marque){ this.marque = Marque; } 
    public void setCout(double Cout){ this.coutRealise = Cout; }
    public void setPere(Arc Pere){ this.pere = Pere; } 

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

