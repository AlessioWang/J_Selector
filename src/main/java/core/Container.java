package core;

import wblut.geom.WB_Polygon;

import java.util.LinkedList;
import java.util.List;

/**
 * @auther Alessio
 * @date 2023/4/14
 **/
public class Container {
    private List<WB_Polygon> polygons;

    public Container() {
        polygons = new LinkedList<>();
    }

    public Container(List<WB_Polygon> polygons) {
        this.polygons = polygons;
    }

    public void add(WB_Polygon p) {
        polygons.add(p);
    }

    public List<WB_Polygon> getPolygons() {
        return polygons;
    }

}
