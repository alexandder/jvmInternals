package exercisetwo.testobjects;

/**
 * Created by aleksander on 13.03.15.
 */
public class ObjectSeveralWrapperFields {

    private Character c;
    private Float f;
    private Byte b;
    private Boolean t;

    public ObjectSeveralWrapperFields() {
    }

    public Character getC() {
        return c;
    }

    public void setC(Character c) {
        this.c = c;
    }

    public Float getF() {
        return f;
    }

    public void setF(Float f) {
        this.f = f;
    }

    public Byte getB() {
        return b;
    }

    public void setB(Byte b) {
        this.b = b;
    }

    public Boolean getT() {
        return t;
    }

    public void setT(Boolean t) {
        this.t = t;
    }

    public ObjectSeveralWrapperFields(Character c, Float f, Byte b, Boolean t) {
        this.c = c;
        this.f = f;
        this.b = b;
        this.t = t;
    }
}
