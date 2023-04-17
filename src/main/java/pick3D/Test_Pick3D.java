package pick3D;

import guo_cam.CameraController;
import processing.core.PApplet;
import wblut.geom.WB_Circle;
import wblut.geom.WB_Point;
import wblut.geom.WB_PolyLine;
import wblut.processing.WB_Render;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;

/**
 * @program: Thesis
 * @description:
 * @author: Naturalpowder
 * @create: 2021-03-24 16:11
 **/
public class Test_Pick3D extends PApplet {
    private CameraController camera;
    private WB_Render render;
    private Scene scene;
    private Layer layer;

    @Override
    public void settings() {
        size(800, 600, P3D);
    }

    @Override
    public void setup() {
        camera = new CameraController(this);
        render = new WB_Render(this);
        scene = new Scene(this);
        scene.setCamera(camera);
        layer = new Layer(Color.BLUE, Color.WHITE, 3, "Tools/geometry");
        WB_PolyLine polyLine = new WB_PolyLine(
                new WB_Point(0, 0, 10),
                new WB_Point(200, 0, 20),
                new WB_Point(200, 500, 30),
                new WB_Point(0, 500, 20),
                new WB_Point(-200, 200, 20)
        );
        WB_Circle circle = new WB_Circle(new WB_Point(20, 20, 50), 100);
        scene.add(circle, layer);
        scene.add(polyLine, layer);

        System.out.println("ss " + scene.getLayers().size());
    }

    @Override
    public void draw() {
        background(255);
        camera.drawSystem(100);
        scene.draw(this);
    }

    @Override
    public void mousePressed() {
        if (mousePressed && mouseButton == LEFT) {

            Map<WB_Point, Unit> map = scene.getIntersectedUnitMap(this, unit -> Arrays.asList(layer, Scene.SELECT).contains(unit.getLayer()));

            if (map.keySet().size() != 0) {
                scene.add((WB_Point) map.keySet().toArray()[0]);
            }
            try {
                scene.drawSelect((Unit) map.values().toArray()[0]);
            } catch (Exception e) {
//                throw new RuntimeException(e);
                System.out.println("wrong");
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("pick3D.Test_Pick3D");
    }
}