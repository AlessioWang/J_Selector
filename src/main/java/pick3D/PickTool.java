package pick3D;

import guo_cam.CameraController;
import guo_cam.Vec_Guo;
import processing.core.PApplet;
import wblut.geom.*;
import wblut.hemesh.HE_Mesh;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: Thesis
 * @description:
 * @author: Naturalpowder
 * @create: 2021-03-26 14:59
 **/
public class PickTool {

    public static double EPSILON = 1;

    /**
     * 检测ray与mesh的AABB是否相交
     *
     * @return
     */
    public static boolean checkMeshEasy(WB_Ray ray, HE_Mesh mesh) {
        WB_AABB aabb = mesh.getAABB();
        return WB_GeometryOp.checkIntersection3D(ray, aabb);
    }

    /**
     * 获取与射线相交的若干mesh中，aabb中心与射线原点距离最近的
     * @param ray
     * @param meshes
     * @return
     */
    public static HE_Mesh pickNearestMesh(WB_Ray ray, List<HE_Mesh> meshes) {
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

    public static WB_Point checkMesh(WB_Ray ray, HE_Mesh mesh) {
        WB_AABB aabb = mesh.getAABB();
        if (!WB_GeometryOp.checkIntersection3D(ray, aabb)) {
            return null;
        } else {
            List<WB_Polygon> polygons = mesh.getPolygonList();
            List<WB_Point> points = polygons.stream()
                    .map(e -> checkPolygon(ray, e))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (points.isEmpty()) return null;
            if (points.size() == 1) return points.get(0);
            Optional<WB_Point> optional = points.stream().min(Comparator.comparingDouble(e -> e.getDistance(ray.getOrigin())));
            return optional.orElse(null);
        }
    }

    public static WB_Point checkMeshTest(WB_Ray ray, HE_Mesh mesh) {
        WB_AABB aabb = mesh.getAABB();
        if (!WB_GeometryOp.checkIntersection3D(ray, aabb)) {
            System.out.println("not aabb");
            return null;
        } else {
            List<WB_Polygon> polygons = mesh.getPolygonList();
//            System.out.println(Arrays.toString(polygons.get(0).getPoints().toArray()));

            List<WB_Point> points = polygons.stream()
                    .map(e -> checkPolygon(ray, e))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (points.isEmpty()) return null;
            if (points.size() == 1) return points.get(0);
            System.out.println(Arrays.toString(points.toArray()));
            Optional<WB_Point> optional = points.stream().min(Comparator.comparingDouble(e -> e.getDistance(ray.getOrigin())));
            return optional.orElse(null);
        }
    }

    public static boolean isPointInTriangle(WB_Point a, WB_Point b, WB_Point c, WB_Point p) {
        WB_Vector bc = c.subToVector3D(b);
        WB_Vector ba = a.subToVector3D(b);
        WB_Vector bp = p.subToVector3D(b);
        boolean judge_1 = bc.cross(ba).dot(bc.cross(bp)) > 0;
        if (!judge_1) return false;
        WB_Vector ac = c.subToVector3D(a);
        WB_Vector ab = ba.mul(-1);
        WB_Vector ap = p.subToVector3D(a);
        boolean judge_2 = ac.cross(ab).dot(ac.cross(ap)) > 0;
        if (!judge_2) return false;
        return ab.cross(ac).dot(ab.cross(ap)) > 0;
    }

    public static WB_Point checkCircle(WB_Ray ray, WB_Circle circle) {
        WB_Plane plane = circle.getPlane();
        Object object = WB_GeometryOp.getIntersection3D(ray, plane);
        if (!(object instanceof WB_Point)) return null;
        else {
            WB_Point p = (WB_Point) object;
            if (!circle.contains(p)) return null;
            return p;
        }
    }

    public static WB_Point checkPoint(WB_Ray ray, WB_Point point) {
        double dis = WB_GeometryOp.getDistanceToRay3D(point, ray);
        if (dis > EPSILON) return null;
        else return point;
    }

    public static WB_Point checkPolyLine(WB_Ray ray, WB_PolyLine polyLine) {
        List<WB_Point> points = new ArrayList<>();
        for (int i = 0; i < polyLine.getNumberSegments(); i++) {
            WB_Segment segment = polyLine.getSegment(i);
            WB_IntersectionResult result = WB_GeometryOp.getClosestPoint3D(ray, segment);
            Object object = result;
            WB_Point p;
            if (object instanceof WB_Point) {
                p = (WB_Point) object;
            } else if (object instanceof WB_Line) {
                p = new WB_Point(((WB_Line) object).getOrigin());
            } else continue;
            WB_Point closeP = WB_GeometryOp.getClosestPoint3D(p, segment);
            if (closeP.getDistance(p) < EPSILON)
                points.add(closeP);
        }

        return points.stream()
                .min(Comparator.comparingDouble(e -> e.getDistance(ray.getOrigin())))
                .orElse(null);
    }

    public static WB_Point checkPolygon(WB_Ray ray, WB_Polygon polygon) {
        Set<WB_Point> points = new HashSet<>();
        WB_AABB aabb = polygon.getAABB();
        if (!WB_GeometryOp.checkIntersection3D(ray, aabb)) return null;
        else {
            int[] faces = polygon.getTriangles();
            for (int i = 0; i < faces.length; i += 3) {
                WB_Point a = polygon.getPoint(faces[i]);
                WB_Point b = polygon.getPoint(faces[i + 1]);
                WB_Point c = polygon.getPoint(faces[i + 2]);
                WB_Triangle t = new WB_Triangle(a, b, c);
                WB_Plane plane = t.getPlane();
                Object object = WB_GeometryOp.getIntersection3D(ray, plane);
                if ((object instanceof WB_Point)) {
                    WB_Point p = (WB_Point) object;
                    if (isPointInTriangle(a, b, c, p))
                        points.add(p);
                }
            }
        }

        if (points.isEmpty()) return null;
        List<WB_Point> results = new ArrayList<>(points);
        if (points.size() == 1) return results.get(0);
        Optional<WB_Point> optional = points.stream().min(Comparator.comparingDouble(e -> e.getDistance(ray.getOrigin())));
        return optional.orElse(null);
    }

    public static WB_Ray getCameraRay(PApplet app, CameraController camera) {
        Vec_Guo[] vectors = camera.pick3d(app.mouseX, app.mouseY);

        return new WB_Ray(
                new WB_Point(vectors[0].x, vectors[0].y, vectors[0].z),
                new WB_Vector(vectors[1].x, vectors[1].y, vectors[1].z)
        );
    }

}