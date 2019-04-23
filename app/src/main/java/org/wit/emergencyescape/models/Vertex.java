package org.wit.emergencyescape.models;
import java.util.*;
public class Vertex implements Comparable<Vertex> {
    public int id;
    public Vertex parent=null;
    public double d_value=Double.POSITIVE_INFINITY;
    public LinkedList<Edge> edges=new LinkedList<Edge>();
    public boolean discovered = false;//found true ie vertex is in SET (Dijkstra)
    //grid coordinates
    public double x,y;

    public Vertex(int id){
        this.id=id;
    }
    public Vertex(int id,double x,double y){
        this.id=id;
        this.x=x;
        this.y=y;
    }

    @Override
    public int compareTo(Vertex o) {
        return Double.compare(this.d_value,o.d_value);
    }




}


