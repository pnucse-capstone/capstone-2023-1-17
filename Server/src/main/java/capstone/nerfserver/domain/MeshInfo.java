package capstone.nerfserver.domain;

public class MeshInfo {
    private Long id;
    private Double xSize;
    private Double ySize;
    private Double zSize;

    public MeshInfo(Long id, Double xSize, Double ySize, Double zSize){
        this.id = id;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }
    public MeshInfo(MeshInfo meshInfo){
        this.id = id;
        this.xSize = meshInfo.getXSize();
        this.ySize = meshInfo.getYSize();
        this.zSize = meshInfo.getZSize();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getXSize() {
        return xSize;
    }

    public void setXSize(double xSize) {
        this.xSize = xSize;
    }

    public double getYSize() {
        return ySize;
    }

    public void setYSize(double ySize) {
        this.ySize = ySize;
    }

    public double getZSize() {
        return zSize;
    }

    public void setZSize(double zSize) {
        this.zSize = zSize;
    }
}
