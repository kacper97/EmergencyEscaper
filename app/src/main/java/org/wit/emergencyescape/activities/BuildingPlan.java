package org.wit.emergencyescape.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.wit.emergencyescape.R;
import org.wit.emergencyescape.models.*;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;

public class  BuildingPlan extends AppCompatActivity {

    private Map map;
    private mxGraph graph = null;
    File imgFile = new File("Resources/plan.png");
    ImageView buildingplan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildingplan);
        MainMap mainMap = new MainMap();
        mainMap.showMap();
        buildingplan = (ImageView) findViewById(R.id.buildingPlan);
    }


    public BuildingPlan(Map m) {
        map = m;
    }

    public void updateBuildingPlan(Map m) {
        map = m;
        drawPlan();
    }


    public void drawPlan() {
        graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        Iterator<java.util.Map.Entry<Integer, Room>> roomIterator = map.getRooms().entrySet().iterator();
        Iterator<java.util.Map.Entry<Integer, Room>> roomIterator2 = map.getRooms().entrySet().iterator();

        graph.getModel().beginUpdate();
        try {

            Hashtable<Integer, Object> rooms = new Hashtable<>();

            //createVertices
            while (roomIterator.hasNext()) {
                Room room = roomIterator.next().getValue();

                Object o = graph.insertVertex(parent, null, room.getName() + ";" + room.getID(),
                        room.getCoordinates().getX(), room.getCoordinates().getY(), 20, 20);

                rooms.put(room.getID(), o);
            }


            //CreateEdges

            while (roomIterator2.hasNext()) {
                Room room = roomIterator2.next().getValue();
                Iterator<java.util.Map.Entry<Integer, Corridor>> corridorIterator = room.getCorridors().entrySet().iterator();

                while (corridorIterator.hasNext()) {
                    Corridor corridor = corridorIterator.next().getValue();
                    Iterator<java.util.Map.Entry<Integer, Room>> neighbourIterator = corridor.getRooms().entrySet().iterator();

                    while (neighbourIterator.hasNext()) {
                        Room neighbour = neighbourIterator.next().getValue();

                        if (room.getID() != neighbour.getID()) {

                            graph.insertEdge(parent, null, "", rooms.get(room.getID()), rooms.get(neighbour.getID()));
                        }
                    }
                }
            }

        } finally {
            graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        buildingplan.setImageBitmap(myBitmap);

    }

        // Mouse listener
        //graphComponent.getGraphControl().addMouseListener(new MouseAdapter()

           /* {
                public void mouseReleased(MouseEvent e)
                {
                    Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                    //Get id
                    if (cell != null)
                    {
                        try {
                            RouteCalculator computer = new RouteCalculator(map);
                            int id = getIDFromLabel(graph.getLabel(cell));
                            EscapeRoute escapeRoute = computer.computeRoute(id);
                            drawRoute(escapeRoute);

                        } catch(Exception ex) {}
                    }
                }
            });


            frame.getContentPane().add(graphComponent);
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);


        }
        */
        private void drawRoute (EscapeRoute route){
            // TODO replace graph showing map with one showing only the route
            graph = new mxGraph();
            Object parent = graph.getDefaultParent();
            Room currentRoom = route.getRoute().get(0);

            graph.getModel().beginUpdate(); //This is necessary for adding things to the graph
            try {
                Object previousRoomNode = graph.insertVertex(parent, null, currentRoom.getName() + ";" + currentRoom.getID(),
                        currentRoom.getCoordinates().getX(), currentRoom.getCoordinates().getY(), 20, 20);

                for (int i = 1; i < route.getRoute().size(); i++) {
                    currentRoom = route.getRoute().get(i);
                    Object currentRoomNode = graph.insertVertex(parent, null, currentRoom.getName() + ";" + currentRoom.getID(),
                            currentRoom.getCoordinates().getX(), currentRoom.getCoordinates().getY(), 20, 20);
                    // Link two following room by an edge
                    graph.insertEdge(parent, null, " ", previousRoomNode, currentRoomNode);
                    previousRoomNode = currentRoomNode;
                }


            } finally {
                graph.getModel().endUpdate();
            }

            mxGraphComponent graphComponent = new mxGraphComponent(graph);
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            buildingplan.setImageBitmap(myBitmap);

        }

        private int getIDFromLabel (String labelText){
            String[] parsed = labelText.split(";");
            return Integer.parseInt(parsed[1]);
        }

}



