package org.wit.emergencyescape.activities;
//https://stackoverflow.com/questions/42527994/android-studio-load-bitmap-from-file-into-canvas

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import org.wit.emergencyescape.R;
import org.wit.emergencyescape.models.Edge;
import org.wit.emergencyescape.models.Graph;
import org.wit.emergencyescape.models.Vertex;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.PriorityQueue;


public class BuildingPlanView extends View{
    int rows=30;
    int cols=30;
    float tileWidth;
    float tileHeight;
    float width;
    float height;
    int[][] grid = new int[rows][cols]; //0-empty 1-start 2-end 3-fire 4-located in set 5-located on queue 6-tile path
    int startClicked = 0;
    int start_x,start_y;
    int stopClicked = 0;
    int stop_x,stop_y;
    final int animationtime=01;
    Graph graph;
    Paint paint = new Paint();
    async dijkstrathread=new async();
    Bitmap bmp;

// iinitialization of canvas
    public void init(AttributeSet attrs,int defStyle){
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.second);
        }

    public BuildingPlanView(Context context){
        super(context);
        init(null,0);
    }

    public BuildingPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BuildingPlanView(Context context,AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    //canvas drawing
        public void onDraw(Canvas canvas){


            super.onDraw(canvas);
            //canvas.setBitmap(bmp);
            tileWidth = width / cols;
            tileHeight = height / rows;
            int border = 0;

            // Stroke
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawBitmap(bmp,0,0,null);

            //
            // draw each tile according to grid matrix content
            for (int row = 0; row < rows; row++)
                for (int column = 0; column < cols; column++) {
                    paint.setStyle(Paint.Style.FILL);
                    int value = grid[row][column];
                    switch (value) {
                        case 0:
                            paint.setStyle(Paint.Style.STROKE);
                            paint.setColor(Color.BLACK);
                            break;
                        case 1:
                            paint.setColor(Color.BLUE);
                            break;
                        case 2:
                            paint.setColor(Color.GREEN);
                            break;
                        case 3:
                            paint.setColor(Color.RED);
                            break;
                        //Discovery
                        case 4:
                            paint.setColor(Color.TRANSPARENT);
                            border = 1;
                            break;
                        case 5:
                            paint.setColor(Color.LTGRAY);
                            border = 1;
                            break;
                        case 6:
                            paint.setColor(Color.YELLOW);
                            border = 1;
                            break;
                    }
                    canvas.drawRect(column * tileWidth, row * tileHeight, column * tileWidth + tileWidth, row * tileHeight + tileHeight, paint);
                    if (border == 1) {
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.BLACK);
                        canvas.drawRect(column * tileWidth, row * tileHeight, column * tileWidth + tileWidth, row * tileHeight + tileHeight, paint);
                    }
                }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);
    }

    //method when tile is touched
    public boolean onTouchEvent(MotionEvent motionEvent){
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int column = (int) (x / tileWidth);
            int row = (int) (y / tileHeight);
            if(startClicked==1){
                grid[start_y][start_x]=0; // set start to be empty grid
                grid[row][column]=1;
                start_x=column;
                start_y=row;
                startClicked=0;
            }
            else if(stopClicked==1){
                grid[stop_y][stop_x]=0;
                grid[row][column]=2;
                stop_x=column;
                stop_y=row;
                stopClicked=0;
            }
            else if (grid[row][column]!=1&&grid[row][column]!=2) {
                if (grid[row][column] != 3)
                    grid[row][column] = 3;
                else
                    grid[row][column] = 0;
            }
        }
        if(motionEvent.getAction()==MotionEvent.ACTION_MOVE){
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int column = (int) (x / tileWidth);
            int row = (int) (y / tileHeight);
            if(x>width || y>height||x<0||y<0)
                return false; // false if out of bounds
            if(grid[row][column]!=1&&grid[row][column]!=2)
                if (grid[row][column] != 3)
                    grid[row][column] = 3;
        }
        invalidate();
        return true;
    }

    //responsible for the results printed above the graph
    public String Dijkstra(){
        System.gc(); // garbage collector
        getGraph();
        dijkstrathread = new async();
        dijkstrathread.execute();
        getGraph();
        String text=graph.Dijkstra(graph.getV(start_x, start_y), graph.getV(stop_x, stop_y)); // text for statistics above
        return text;
    }


    public void getGraph(){
        //we get grid, compute graph, remove those tiles that have been processed by any of the algorithms before
        //3-fire 4-located in set 5-located on queue 6-tile path
       // add vertex id x y
        Graph g = new Graph();
        int counter=0;
        for(int i=0;i<rows;i++) {
            for (int j = 0; j < cols; j++) {
                if(grid[i][j]!=3)
                    g.addVertex(counter, j, i);
                if(grid[i][j]==4||grid[i][j]==5||grid[i][j]==6)
                    grid[i][j]=0;
                // increment counter
                counter++;
            }
        }

        // x y
        int x1,x2,x3,x4,x5,x6,x7,x8;
        int y1,y2,y3,y4,y5,y6,y7,y8;

        for(int i=0;i<rows;i++) {
            for (int j = 0; j < cols; j++){
                if(grid[i][j]==3)
                    continue;

                x1=j-1;
                y1=i-1;
                x2=j;
                y2=i-1;
                x3=j+1;
                y3=i-1;
                x4=j-1;
                y4=i;
                x5=j+1;
                y5=i;
                x6=j-1;
                y6=i+1;
                x7=j;
                y7=i+1;
                x8=j+1;
                y8=i+1;

                if(x1>=0&&x1<cols&&y1>=0&&y1<rows)
                    if(grid[y1][x1]!=3)
                        //src destination weight
                        g.addEdgeGrid(g.getV(j,i),g.getV(x1,y1),1.4);
                if(x2>=0&&x2<cols&&y2>=0&&y2<rows)
                    if(grid[y2][x2]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x2,y2),1);
                if(x3>=0&&x3<cols&&y3>=0&&y3<rows)
                    if(grid[y3][x3]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x3,y3),1.4);
                if(x4>=0&&x4<cols&&y4>=0&&y4<rows)
                    if(grid[y4][x4]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x4,y4),1);
                if(x5>=0&&x5<cols&&y5>=0&&y5<rows)
                    if(grid[y5][x5]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x5,y5),1);
                if(x6>=0&&x6<cols&&y6>=0&&y6<rows)
                    if(grid[y6][x6]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x6,y6),1.4);
                if(x7>=0&&x7<cols&&y7>=0&&y7<rows)
                    if(grid[y7][x7]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x7,y7),1);
                if(x8>=0&&x8<cols&&y8>=0&&y8<rows)
                    if(grid[y8][x8]!=3)
                        g.addEdgeGrid(g.getV(j,i),g.getV(x8,y8),1.4);
            }
        }
        graph = g;
       invalidate();
    }


//The AsyncTask executes everything in doInBackground() inside of another thread, which does not have access to the GUI where your views are.
    public class async extends AsyncTask<Void,Void,Void>{
        protected Void doInBackground(Void... params) {
            // the part below needs to be graphed
            Vertex start=graph.getV(start_x, start_y);
            Vertex destination = graph.getV(stop_x, stop_y);
            //Initialization
            // d[start]=0 (other vertex's d_value is infinity by default), S={0} , Q = vertex
            LinkedList<Vertex> set = new LinkedList<Vertex>();
            PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
            queue.add(start);
            start.d_value=0;

            //cycle until queue is empty or destination has been inserted into set
            while(!queue.isEmpty()) {
                if (isCancelled()) break;
                Vertex extracted = queue.poll();
                extracted.discovered = true;// when it is in set and now d_value is defined
                set.add(extracted);
                if(grid[(int) extracted.y][(int) extracted.x]!=1&&grid[(int) extracted.y][(int) extracted.x]!=2)
                    grid[(int) extracted.y][(int) extracted.x] = 4;
                publishProgress();
                try {
                    Thread.sleep(animationtime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (extracted == destination) {
                    break;
                }


                //for each vertex into the adj list of extracted -> relax.
                for (int i = 0; i < extracted.edges.size(); i++) {
                    //edge examined
                    Edge edge = extracted.edges.get(i);
                    //get neighbour vertex and relax
                    Vertex neighbour = edge.destination;
                    if (neighbour.discovered == false) {
                        //Relaxation
                        if(grid[(int) neighbour.y][(int) neighbour.x]!=1&&grid[(int) neighbour.y][(int) neighbour.x]!=2)
                            grid[(int) neighbour.y][(int) neighbour.x] = 5;
                        publishProgress();
                        try {
                            Thread.sleep(animationtime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (neighbour.d_value > extracted.d_value + edge.weight)
                        {
                            neighbour.d_value = extracted.d_value + edge.weight;
                            neighbour.parent = extracted;
                            //insert neighbours in queue so we can choose the min one
                            queue.remove(neighbour);
                            queue.add(neighbour);
                        }
                    }//if discovered
                }
            }

            Vertex current = destination;
            while (current != null) {
                if (isCancelled()) break;
                if (grid[(int) current.y][(int) current.x] != 1 && grid[(int) current.y][(int) current.x] != 2)
                    grid[(int) current.y][(int) current.x] = 6;
                publishProgress();
                try {
                    Thread.sleep(animationtime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                current = current.parent;
            }
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            invalidate();
        }
    }


}

