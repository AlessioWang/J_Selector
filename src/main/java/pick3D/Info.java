package pick3D;

import wblut.geom.WB_Point;

/**
 * @program: CountrySewage-FX
 * @author: Donggeng
 * @create: 2021-03-28 19:53
 */
public class Info {
    //管道信息
    private int id;
    private double diameter;
    private double weight;
    private double originZ;
    private double endZ;
    private double originDepth;
    private double endDepth;
    private double length;
    private double angle;
    private boolean needEnhance;
    private boolean inRoad;
    private double inRoadLength;
    private double maxZ;
    private double minZ;
    private double maxWeight;

    //井信息
    private String wellIndex;
    private double wellBottomAltitude;
    private double wellDepth;
    private String wellForm;
    private String wellPicIndex;

    //排水点信息
    private int nodeId;
    private double nodeWeight;
    private String nodeLayer;


    public double getInRoadLength() {
        return inRoadLength;
    }

    public void setInRoadLength(double inRoadLength) {
        this.inRoadLength = inRoadLength;
    }

    public boolean isNeedEnhance() {
        return needEnhance;
    }

    public void setNeedEnhance(boolean needEnhance) {
        this.needEnhance = needEnhance;
    }

    public boolean isInRoad() {
        return inRoad;
    }

    public void setInRoad(boolean inRoad) {
        this.inRoad = inRoad;
    }

    public double getOriginDepth() {
        return originDepth;
    }

    public void setOriginDepth(double originDepth) {
        this.originDepth = originDepth;
    }

    public double getEndDepth() {
        return endDepth;
    }

    public void setEndDepth(double endDepth) {
        this.endDepth = endDepth;
    }

    public WB_Point getPosition() {
        return position;
    }

    public void setPosition(WB_Point position) {
        this.position = position;
    }

    private WB_Point position;

    public double getDiameter() {
        return diameter;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getOriginZ() {
        return originZ;
    }

    public void setOriginZ(double originZ) {
        this.originZ = originZ;
    }

    public double getEndZ() {
        return endZ;
    }

    public void setEndZ(double endZ) {
        this.endZ = endZ;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWellIndex() {
        return wellIndex;
    }

    public void setWellIndex(String wellIndex) {
        this.wellIndex = wellIndex;
    }

    public double getWellBottomAltitude() {
        return wellBottomAltitude;
    }

    public void setWellBottomAltitude(double wellBottomAltitude) {
        this.wellBottomAltitude = wellBottomAltitude;
    }

    public double getWellDepth() {
        return wellDepth;
    }

    public void setWellDepth(double wellDepth) {
        this.wellDepth = wellDepth;
    }

    public String getWellForm() {
        return wellForm;
    }

    public void setWellForm(String wellForm) {
        this.wellForm = wellForm;
    }

    public String getWellPicIndex() {
        return wellPicIndex;
    }

    public void setWellPicIndex(String wellPicIndex) {
        this.wellPicIndex = wellPicIndex;
    }

    public double getNodeWeight() {
        return nodeWeight;
    }

    public void setNodeWeight(double nodeWeight) {
        this.nodeWeight = nodeWeight;
    }

    public String getNodeLayer() {
        return nodeLayer;
    }

    public void setNodeLayer(String nodeLayer) {
        this.nodeLayer = nodeLayer;
    }

    public int getId() {
        return id;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return "pick3D.Info{" +
                "radius=" + diameter +
                ", originZ=" + originZ +
                ", endZ=" + endZ +
                ", length=" + length +
                ", angle=" + angle +
                ", weight=" + weight +
                '}';
    }
}
