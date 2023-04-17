package pick3D;

import wblut.geom.*;
import wblut.hemesh.HE_Mesh;
import wblut.processing.WB_Render;

import java.lang.reflect.Field;

/**
 * @program: Thesis
 * @description:
 * @author: Naturalpowder
 * @create: 2021-03-25 10:18
 **/
public class Unit {
    private WB_Polygon polygon;
    private WB_PolyLine polyLine;
    private WB_Segment segment;
    private HE_Mesh mesh;
    private WB_Point point;
    private WB_Circle circle;
    private WB_Ray ray;
    private int size;
    private Layer layer = Scene.ZERO;
    private Layer cache = Scene.ZERO;
    private Info info;

    public Unit(WB_Polygon polygon) {
        this.polygon = polygon;
        initialize();
    }

    public Unit(WB_PolyLine polyLine) {
        this.polyLine = polyLine;
        initialize();
    }

    public Unit(WB_Segment segment) {
        this.polyLine = new WB_PolyLine(segment.getOrigin(), segment.getEndpoint());
        initialize();
    }

    public Unit(HE_Mesh mesh) {
        this.mesh = mesh;
        initialize();
    }

    public Unit(WB_Point point) {
        this.point = point;
        initialize();
    }

    public Unit(WB_Circle circle) {
        this.circle = circle;
        initialize();
    }

    public Unit(WB_Ray ray) {
        this.ray = ray;
        initialize();
    }

    private void initialize() {
        size++;
        this.info = new Info();
    }

    public Object get() {
        if (polygon != null)
            return polygon;
        if (polyLine != null)
            return polyLine;
        if (mesh != null)
            return mesh;
        if (point != null)
            return point;
        if (circle != null)
            return circle;
        if (ray != null)
            return ray;
        return null;
    }

    public String type() {
        Object obj = get();
        return obj.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return get().toString();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void draw(WB_Render render) {
        Object obj = get();
        if (obj instanceof WB_Point)
            render.drawPoint(point, 0.5);
        else if (obj instanceof HE_Mesh) {
            render.drawMesh(mesh.toFacelistMesh());
        }
        else if (obj instanceof WB_Polygon)
            render.drawPolygonEdges(polygon);
        else if (obj instanceof WB_PolyLine)
            render.drawPolyLine(polyLine);
        else if (obj instanceof WB_Circle)
            render.drawCircle(circle);
        else if (obj instanceof WB_Ray)
            render.drawRay(ray, 1);
    }

    public WB_Point getCameraIntersection(WB_Ray ray) {
        if (polygon != null)
            return PickTool.checkPolygon(ray, polygon);
        if (circle != null)
            return PickTool.checkCircle(ray, circle);
        if (mesh != null)
            return PickTool.checkMesh(ray, mesh);
        if (point != null)
            return PickTool.checkPoint(ray, point);
        if (polyLine != null)
            return PickTool.checkPolyLine(ray, polyLine);
        return null;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    public Layer getCache() {
        return cache;
    }

    public void setCache(Layer cache) {
        this.cache = cache;
    }

    public int size() {
        Field[] fields = getClass().getDeclaredFields();
        int sum = 0;
        for (Field field : fields) {
            try {
                if (field.get(this) != null) {
                    sum++;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sum - 1;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public static void main(String[] args) {
        Unit unit = new Unit(new WB_Point());
        System.out.println(unit.type());
    }
}