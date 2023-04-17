package pick3D;

import processing.core.PApplet;
import wblut.processing.WB_Render;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @program: Thesis
 * @description:
 * @author: Naturalpowder
 * @create: 2021-03-25 15:30
 **/
public class Layer {
    private Color strokeColor;
    private Color fillColor;
    private float strokeWeight;
    private String name;
    private final List<Unit> units;
    private boolean visible;

    public Layer(Color strokeColor, Color fillColor, float strokeWeight, String name) {
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.strokeWeight = strokeWeight;
        this.name = name;
        visible = true;
        units = new CopyOnWriteArrayList<>();
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public float getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(float strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Unit unit) {
        units.add(unit);
        unit.setLayer(this);
    }

    public boolean remove(Unit unit) {
        boolean remove = units.remove(unit);
        if (remove) {
            unit.setCache(unit.getLayer());
            unit.setLayer(null);
        }
        return remove;
    }

    public void clear() {
        for (Unit unit : units) {
            unit.setCache(unit.getLayer());
            unit.setLayer(null);
        }
        units.clear();
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public List<Unit> getUnits() {
        return units;
    }

    WB_Render render;

    public void draw(PApplet g) {
        if (visible) {
            if (render == null)
                render = new WB_Render(g);
            g.pushStyle();
            g.fill((int) (fillColor.getRed() * 255), (int) (fillColor.getGreen() * 255), (int) (fillColor.getBlue() * 255));
            g.stroke((int) (strokeColor.getRed() * 255), (int) (strokeColor.getGreen() * 255), (int) (strokeColor.getBlue() * 255));
            g.strokeWeight(strokeWeight);
            units.forEach(e -> e.draw(render));
            g.popStyle();
        }
    }

    @Override
    public String toString() {
        return "sample.pick3D.Layer{" +
                "color=" + strokeColor +
                ", strokeWeight=" + strokeWeight +
                ", name='" + name + '\'' +
                ", units=" + units.size() +
                '}';
    }
}