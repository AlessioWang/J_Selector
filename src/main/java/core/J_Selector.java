package core;

import guo_cam.CameraController;
import guo_cam.Vec_Guo;
import processing.core.PApplet;
import wblut.geom.*;
import wblut.hemesh.HEC_Creator;
import wblut.hemesh.HEC_FromPolygons;
import wblut.hemesh.HE_Mesh;

import java.util.*;

/**
 * @auther Alessio
 * @date 2023/4/12
 **/
public class J_Selector {

    private Container container;

    private CameraController camera;

    public J_Selector(Container container, CameraController camera) {
        this.container = container;
        this.camera = camera;
    }

    public WB_Polygon getSelectedPolygon(PApplet applet) {
        WB_Ray ray = getCameraRay(applet, camera);

        List<WB_Polygon> polygons = container.getPolygons();

        return pickNearestPolygon(ray, polygons);
    }

    public WB_Polygon getSelected(PApplet applet) {
        WB_Ray ray = getCameraRay(applet, camera);

        List<WB_Polygon> polygons = container.getPolygons();
        Map<HE_Mesh, WB_Polygon> map = new HashMap<>();

        polygons.forEach(e -> map.put(createMeshFromPolygon(e), e));

        HE_Mesh pickedMesh = pickNearestMesh(ray, new LinkedList<>(map.keySet()));

        return map.get(pickedMesh);
    }

    /**
     * 由polygon创建mesh
     *
     * @param polygon
     * @return
     */
    private HE_Mesh createMeshFromPolygon(WB_Polygon polygon) {
        WB_Polygon[] ps = new WB_Polygon[]{polygon};
        HEC_Creator hec_creator = new HEC_FromPolygons(ps);
        return new HE_Mesh(hec_creator);
    }

    /**
     * 检测ray与mesh的AABB是否相交
     *
     * @return
     */
    private boolean checkMeshByAABB(WB_Ray ray, HE_Mesh mesh) {
        WB_AABB aabb = mesh.getAABB();
        return WB_GeometryOp.checkIntersection3D(ray, aabb);
    }

    /**
     * @param app
     * @param camera
     * @return
     */
    private WB_Ray getCameraRay(PApplet app, CameraController camera) {
        Vec_Guo[] vectors = camera.pick3d(app.mouseX, app.mouseY);

        return new WB_Ray(
                new WB_Point(vectors[0].x, vectors[0].y, vectors[0].z),
                new WB_Vector(vectors[1].x, vectors[1].y, vectors[1].z)
        );
    }

    /**
     * 获取与射线相交的若干mesh中，aabb中心与射线原点距离最近的
     *
     * @param ray
     * @param meshes
     * @return
     */
    private HE_Mesh pickNearestMesh(WB_Ray ray, List<HE_Mesh> meshes) {
        HashMap<WB_AABB, HE_Mesh> map = new HashMap<>();
        meshes.forEach(e -> map.put(e.getAABB(), e));

        PriorityQueue<WB_AABB> queue = new PriorityQueue<>((p1, p2) ->
                (int) (p1.getCenter().getDistance3D(ray.getOrigin()) -
                        p2.getCenter().getDistance3D(ray.getOrigin()))
        );

        for (var aabb : map.keySet()) {
            if (WB_GeometryOp.checkIntersection3D(ray, aabb)) {
                queue.add(aabb);
            }
        }

        return map.get(queue.poll());
    }

    /**
     * 获取最近的polygon
     * @param ray
     * @param polygons
     * @return
     */
    private WB_Polygon pickNearestPolygon(WB_Ray ray, List<WB_Polygon> polygons) {
        Map<WB_Polygon, WB_Point> map = new HashMap<>();

        PriorityQueue<Map.Entry<WB_Polygon, WB_Point>> queue = new PriorityQueue<>((p1, p2) ->
                (int) (p1.getValue().getDistance3D(ray.getOrigin()) - p2.getValue().getDistance3D(ray.getOrigin()))
        );

        for (var p : polygons) {
            WB_IntersectionResult intersection3D = WB_GeometryOp.getIntersection3D(ray, p);
            if (intersection3D.intersection) {
                map.put(p, (WB_Point) intersection3D.object);
            }
        }

        map.entrySet().forEach(queue::add);

        if (queue.size() != 0) {
            return queue.poll().getKey();
        }

        return null;
    }


}


