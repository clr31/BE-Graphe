package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.ArcInspectorFactory ;

import java.util.List;

public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     *
     * @return The created drawing.
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        // visit these directory to see the list of available files on commetud.
        final String mapINSA =
                "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String pathName =
                "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";
        final String mapMidi = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/midi-pyrenees.mapgr" ;
        final String mapTLS = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/toulouse.mapgr" ;
        final String mapMad = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/madagascar.mapgr" ;
        final String mapBord = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/bordeaux.mapgr" ;



        //test 1 dijkstra mode voiture en distance toulouse
        final Graph graph;
        int mode = 2 ;
        String map = mapTLS ;
        //String map = mapBord ;
        try (final GraphReader reader = new BinaryGraphReader(new DataInputStream(
                new BufferedInputStream(new FileInputStream(map))))) {
            graph = reader.read();
        }
        final Drawing drawing = createDrawing();
        drawing.drawGraph(graph) ;


        Path pathB, pathD, pathA ;
        ShortestPathData data ;
        final ArcInspectorFactory Ainspect = new ArcInspectorFactory() ;
        data = new ShortestPathData(graph, graph.get(5889), graph.get(5660), Ainspect.getAllFilters().get(mode)) ;
        //data = new ShortestPathData(graph, graph.get(3067), graph.get(3625), Ainspect.getAllFilters().get(mode)) ;
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(data) ;
        pathD = dijkstra.run().getPath() ;
        drawing.drawPath(pathD,Color.cyan) ;
        BellmanFordAlgorithm bellman = new BellmanFordAlgorithm(data) ;
        pathB = bellman.run().getPath() ;
        drawing.drawPath(pathB, Color.green) ;
        AStarAlgorithm aStar = new AStarAlgorithm(data) ;
        pathA = aStar.run().getPath() ;
        drawing.drawPath(pathA, Color.yellow) ;
        System.out.println("test Bellman (1) Dijkstra (2)\n") ;
        test(mode, pathB, pathD) ;
        System.out.println("test Dijkstra (1) AStar (2)\n") ;
        test(mode, pathD, pathA) ;
    }

    static void test(int mode, Path path1, Path path2) {
        
        if(mode==0 || mode==1) {
            //distance
            float dist1, dist2 ;
            dist1 = path1.getLength() ;
            System.out.println("distance 1 : " + dist1) ;
            dist2 = path2.getLength() ;
            System.out.println("distance 2 : " + dist2) ;
            if((dist1 - dist2 < 0.01) && (dist1 - dist2 > -0.01)) {
                System.out.println("Test distance ok\n") ;
            }
            else {
                System.out.println("Test distance invalide\n") ;
            }
        }
        else {
            //temps
            double time1, time2 ;
            time1 = path1.getMinimumTravelTime() ;
            System.out.println("temps 1 : " + time1) ;
            time2 = path2.getMinimumTravelTime() ;
            System.out.println("temps 2 : " + time2) ;
            if((time1 - time2 < 0.01) && (time1 - time2 > -0.01)) {
                System.out.println("Test temps ok\n") ;
            }
            else {
                System.out.println("Test temps invalide\n") ;
            }
        }
    }

}
