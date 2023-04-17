package pick3D;

import guo_cam.CameraController;
import processing.core.PApplet;
import wblut.geom.*;
import wblut.hemesh.HE_Mesh;
import wblut.processing.WB_Render;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @program: Thesis
 * @description:
 * @author: Naturalpowder
 * @create: 2021-03-24 15:42
 **/
public class Scene {
    private final List<Layer> layers;
    private CameraController camera;
    private WB_Render render;
    public final static Layer ZERO = new Layer(Color.BLACK, Color.WHITE, 1, "0");
    public final static Layer SELECT = new Layer(Color.RED, Color.BLUE, 2, "Select");

    public Unit select = null;

    public Scene(PApplet applet) {
        render = new WB_Render(applet);
        layers = new CopyOnWriteArrayList<>();
        layers.add(ZERO);
        layers.add(SELECT);
    }

    public void add(HE_Mesh mesh) {
        layers.get(0).add(new Unit(mesh));
    }

    public void add(HE_Mesh mesh, Layer layer) {
        addLayer(layer);
        layer.add(new Unit(mesh));
    }

    public void add(Unit unit) {
        layers.get(0).add(unit);
    }

    public void add(Unit unit, Layer layer) {
        addLayer(layer);
        layer.add(unit);
    }

    public void add(WB_Ray ray) {
        layers.get(0).add(new Unit(ray));
    }

    public void add(WB_Ray ray, Layer layer) {
        addLayer(layer);
        layer.add(new Unit(ray));
    }

    public void add(WB_Circle circle) {
        layers.get(0).add(new Unit(circle));
    }

    public void add(WB_Circle circle, Layer layer) {
        addLayer(layer);
        layer.add(new Unit(circle));
    }

    public void add(WB_PolyLine polyLine) {
        layers.get(0).add(new Unit(polyLine));
    }

    public void add(WB_PolyLine polyLine, Layer layer) {
        addLayer(layer);
        layer.add(new Unit(polyLine));
    }

    public void add(WB_Polygon polygon) {
        layers.get(0).add(new Unit(polygon));
    }

    public void add(WB_Polygon polygon, Layer layer) {
        addLayer(layer);
        layer.add(new Unit(polygon));
    }

    public void add(WB_Point point) {
        layers.get(0).add(new Unit(point));
    }

    public void add(WB_Point point, Layer layer) {
        addLayer(layer);
        layer.add(new Unit(point));
    }

    private void addLayer(Layer layer) {
        if (!layers.contains(layer))
            layers.add(layer);
    }

    public Map<WB_Point, Unit> getIntersectedUnitMap(PApplet applet, Filter filter) {
        WB_Ray ray = PickTool.getCameraRay(applet, camera);

        List<Map<WB_Point, Unit>> list = new CopyOnWriteArrayList<>();
        for (Layer layer : layers) {
            for (Unit unit : layer.getUnits()) {
                if (filter.filter(unit)) {
                    WB_Point intersect = unit.getCameraIntersection(ray);
                    if (intersect != null) {
                        Map<WB_Point, Unit> map = new HashMap<>();
                        map.put(intersect, unit);
                        list.add(map);
                    }
                }
            }
        }

        System.out.println(list.size());

        return list.stream()
                .min(Comparator.comparingDouble(e -> ((WB_Point) e.keySet().toArray()[0]).getDistance(ray.getOrigin())))
                .orElse(new HashMap<WB_Point, Unit>());
    }


    public void drawSelect(Unit unit) {
        if (!SELECT.getUnits().isEmpty()) {
            select = SELECT.getUnits().get(0);
            toLayer(select, select.getCache());
            select = null;
        }
        if (unit != null) {
            select = unit;
            toLayer(unit, SELECT);
        }
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void draw(PApplet app) {
        layers.forEach(e -> e.draw(app));
//        drawInfo2D();
    }

    public void draw(String layer, PApplet app) {
        layers.stream().filter(e -> e.getName().equalsIgnoreCase(layer)).forEach(e -> e.draw(app));
    }

    public void clear() {
        for (Layer layer : layers) {
            layer.clear();
        }
    }

    public void setCamera(CameraController camera) {
        this.camera = camera;
    }

    public void toLayer(Unit unit, Layer layer) {
        layers.forEach(e -> e.remove(unit));
        layer.add(unit);
    }

    public CameraController getCamera() {
        return camera;
    }

    @Override
    public String toString() {
        String s = "\n";
        String info = layers.stream().map(Layer::toString).collect(Collectors.joining(s));
        return "{\n" +
                info +
                "\n}";
    }

    public interface Filter {
        boolean filter(Unit unit);
    }
}