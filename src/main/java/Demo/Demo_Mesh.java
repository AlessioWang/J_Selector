package Demo;

import core.Container;
import core.J_Selector;
import guo_cam.CameraController;
import processing.core.PApplet;
import wblut.geom.WB_Point;
import wblut.geom.WB_Polygon;
import wblut.hemesh.HEC_FromPolygons;
import wblut.hemesh.HE_Mesh;
import wblut.processing.WB_Render;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther Alessio
 * @date 2023/6/5
 **/
public class Demo_Mesh extends PApplet {
    private CameraController cameraController;

    private WB_Render render;

    private Container container;

    private J_Selector selector;

    private HE_Mesh mesh1;

    private HE_Mesh mesh2;

    private HE_Mesh mesh3;

    private HE_Mesh selectedMesh;

    public static void main(String[] args) {
        PApplet.main(Demo_Mesh.class.getName());
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

    private void initShapes() {
        List<HE_Mesh> meshes = new ArrayList<>();

        WB_Polygon polygon1 = new WB_Polygon(
                new WB_Point(0, 0, 0),
                new WB_Point(500, 0, 0),
                new WB_Point(200, 500, 0),
                new WB_Point(0, 500, 0),
                new WB_Point(-200, 200, 0));

        WB_Polygon polygon2 = new WB_Polygon(
                new WB_Point(0, 0, 500),
                new WB_Point(500, 0, 200),
                new WB_Point(200, 500, 600),
                new WB_Point(0, 500, 200),
                new WB_Point(-200, 200, 200));

        WB_Polygon polygon3 = new WB_Polygon(
                new WB_Point(-200, 0, 1000),
                new WB_Point(500, 0, 1200),
                new WB_Point(200, 500, 1300),
                new WB_Point(0, 500, 1200),
                new WB_Point(-200, 200, 1000));

        mesh1 = new HEC_FromPolygons(new WB_Polygon[]{
                polygon1
        }).create();

        mesh2 = new HEC_FromPolygons(new WB_Polygon[]{
                polygon2
        }).create();

        mesh3 = new HEC_FromPolygons(new WB_Polygon[]{
                polygon3
        }).create();

        meshes.add(mesh1);
        meshes.add(mesh2);
        meshes.add(mesh3);

        container = new Container();
        container.addMeshes(meshes);
    }

    /**
     * 通过鼠标点选获取mesh
     */
    @Override
    public void mousePressed() {
        if (mousePressed && mouseButton == LEFT) {
            selectedMesh = selector.getSelectedMesh(this);
        }
    }


    @Override
    public void draw() {
        background(255, 255, 255);
        cameraController.drawSystem(100);

        fill(0, 50, 50, 50);
        render.drawMesh(mesh1.toFacelistMesh());
        render.drawMesh(mesh2.toFacelistMesh());
        render.drawMesh(mesh3.toFacelistMesh());

        pushStyle();
        stroke(0, 200, 200);
        strokeWeight(5);
        if (selectedMesh != null) {
            render.drawMesh(selectedMesh.toFacelistMesh());
        }
        popStyle();
    }
}
