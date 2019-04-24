package org.wit.emergencyescape.models;

public class Edge {
    public Vertex source;
    public Vertex destination;
    public double weight;
    //edge constructor
    public Edge(Vertex source,Vertex destination,double weight){
        this.source=source;
        this.destination=destination;
        this.weight=weight;
    }
}

