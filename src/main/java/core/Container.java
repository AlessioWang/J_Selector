package core;

import wblut.geom.WB_Polygon;
import wblut.hemesh.HE_Mesh;

import java.util.LinkedList;
import java.util.List;

/**
 * @auther Alessio
 * @date 2023/4/14
 **/
public class Container {
    private List<WB_Polygon> polygons;

    private List<HE_Mesh> meshes;

    public Container() {
        initVar();
    }

    private void initVar() {
        polygons = new LinkedList<>();
        meshes = new LinkedList<>();
    }

    public void add(WB_Polygon p) {
        polygons.add(p);
    }

    public void addPolygons(List<WB_Polygon> ps) {
        polygons.addAll(ps);
    }

    public void add(HE_Mesh m) {
        meshes.add(m);
    }

    public void addMeshes(List<HE_Mesh> ms) {
        meshes.addAll(ms);
    }

    public List<WB_Polygon> getPolygons() {
        return polygons;
    }

    public List<HE_Mesh> getMeshes() {
        return meshes;
    }
}
