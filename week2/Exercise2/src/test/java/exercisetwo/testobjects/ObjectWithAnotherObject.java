package exercisetwo.testobjects;


public class ObjectWithAnotherObject {

    private Double y;
    private ObjectPrivateIntField obj;

    public ObjectWithAnotherObject(Double y, ObjectPrivateIntField obj) {
        this.y = y;
        this.obj = obj;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public ObjectPrivateIntField getObj() {
        return obj;
    }

    public void setObj(ObjectPrivateIntField obj) {
        this.obj = obj;
    }
}
