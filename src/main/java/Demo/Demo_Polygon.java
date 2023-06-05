
package Demo;

import core.Container;
import core.J_Selector;
import guo_cam.CameraController;
import processing.core.PApplet;
import wblut.geom.WB_Point;
import wblut.geom.WB_Polygon;
import wblut.processing.WB_Render;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther Alessio
 * @date 2023/4/12
 **/
public class Demo_Polygon extends PApplet {

    private CameraController cameraController;

    private WB_Render render;

    private Container container;

    private J_Selector selector;

    private WB_Polygon polygon1;

    private WB_Polygon polygon2;

    private WB_Polygon polygon3;

    private WB_Polygon selectedShape;

//    private WB_Point result;

    public static void main(String[] args) {
        PApplet.main(Demo_Polygon.class.getName());
    }

    public void settings() {
        size(800, 800, P3D);
    }

    public void setup() {
        cameraController = new CameraController(this, 1000);

        initShapes();

        selector = new J_Selector(container, cameraController);

        render = new WB_Render(this);
    }

    /**
     * 向container添加图元
     */
    private void initShapes() {
        List<WB_Polygon> polygons = new ArrayList<>();

        polygon1 = new WB_Polygon(
                new WB_Point(0, 0, 0),
                new WB_Point(500, 0, 0),
                new WB_Point(200, 500, 0),
                new WB_Point(0, 500, 0),
                new WB_Point(-200, 200, 0));

        polygon2 = new WB_Polygon(
                new WB_Point(0, 0, 500),
                new WB_Point(500, 0, 200),
                new WB_Point(200, 500, 600),
                new WB_Point(0, 500, 200),
                new WB_Point(-200, 200, 200));

        polygon3 = new WB_Polygon(
                new WB_Point(-200, 0, 1000),
                new WB_Point(500, 0, 1200),
                new WB_Point(200, 500, 1300),
                new WB_Point(0, 500, 1200),
                new WB_Point(-200, 200, 1000));

        polygons.add(polygon1);
        polygons.add(polygon2);
        polygons.add(polygon3);



        container = new Container();
        container.addPolygons(polygons);
    }

    /**
     * 通过鼠标点选获取polygon
     */
    @Override
    public void mousePressed() {
        if (mousePressed && mouseButton == LEFT) {
            selectedShape = selector.getSelectedPolygon(this);
        }
    }

    @Override
    public void draw() {
        background(255, 255, 255);
        cameraController.drawSystem(100);

        fill(0, 50, 50, 50);
        render.drawPolygonEdges(polygon1);
        render.drawPolygonEdges(polygon2);
        render.drawPolygonEdges(polygon3);

        pushStyle();
        stroke(0, 0, 200);
        strokeWeight(5);
        if (selectedShape != null) {
            render.drawPolygonEdges(selectedShape);
        }
        popStyle();
    }

}
