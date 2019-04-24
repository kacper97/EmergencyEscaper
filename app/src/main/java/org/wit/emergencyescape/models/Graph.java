package org.wit.emergencyescape.models;

import java.util.*;
public class Graph {

    public ArrayList<Vertex> vertex=new ArrayList<Vertex>();

    public void addVertex(int id,double x,double y){
        Vertex v1 = new Vertex(id,x,y);
        vertex.add(v1);
    }

    public void addEdgeGrid(Vertex source,Vertex destination,double weight){
        //for grid we will initialize its vertex edges
        source.edges.add(new Edge(source,destination,weight));
    }


    public Vertex getV(double x,double y){
        for(int i=0;i<vertex.size();i++)
            if(vertex.get(i).x==x&&vertex.get(i).y==y)
                return vertex.get(i);
        //if not found
        return null;
    }

    // This is the thing that prints out on top o the screen
    public String Dijkstra(Vertex start,Vertex destination){
        String text="Dijkstra "; //we will keep the processing results here
        //Initialization
        //t d[sart]=0 (other vertex's d_value is infinity by default), S={0} , Q = vertex
        long startTime = System.nanoTime();
        LinkedList<Vertex> set = new LinkedList<Vertex>();
        PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
        queue.add(start);
        start.d_value=0;

        //cycle until queue is empty or destination has been inserted into s
        while(!queue.isEmpty()){

            Vertex extracted = queue.poll();
            extracted.discovered=true;//when it is in set and now d_value is defined
            set.add(extracted);
            if(extracted==destination){
                break;
            }
            //for each vertex into dhe adj list of extracted -> relax.
            for(int i=0;i<extracted.edges.size();i++){
                //edge examined
                Edge edge = extracted.edges.get(i);
                //get neighbour vertex and relax
                Vertex neighbour = edge.destination;
                //if no neighbour
                if(!neighbour.discovered){
                    //Relaxation
                    if(neighbour.d_value>extracted.d_value+edge.weight){
                        neighbour.d_value=extracted.d_value+edge.weight;
                        neighbour.parent=extracted;
                        //insert neighbours in queue so we can choose the min one
                        queue.remove(neighbour);
                        queue.add(neighbour);
                    }
                }//if discovered
            }
        }
        long stopTime = System.nanoTime();
        if(destination.parent==null)
            text="There is no route found";
        else{
            text+=" Vertex num set: "+set.size();
            //Dijkstra process finished, now we will take our path and print it
            Stack<Vertex> stack = new Stack<Vertex>();
            Vertex current = destination;
            while(current!=null){
                stack.push(current);
                current = current.parent;
            }
            text+=" Nr.Hops:"+(stack.size()-1)+" Time: "+(stopTime-startTime)+" ns";
        }//end else for the existence of the path
        return text;
    }
}


