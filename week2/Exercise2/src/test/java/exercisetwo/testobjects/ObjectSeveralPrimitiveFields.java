package exercisetwo.testobjects;


public class ObjectSeveralPrimitiveFields {

    private short mshort;
    private long mlong;
    private float mfloat;
    private double mdouble;
    private byte mbyte;
    private char mchar;

    public ObjectSeveralPrimitiveFields() {
    }

    public ObjectSeveralPrimitiveFields(short mshort, long mlong, float mfloat, double mdouble, byte mbyte, char mchar) {
        this.mshort = mshort;
        this.mlong = mlong;
        this.mfloat = mfloat;
        this.mdouble = mdouble;
        this.mbyte = mbyte;
        this.mchar = mchar;
    }

    public short getMshort() {
        return mshort;
    }

    public void setMshort(short mshort) {
        this.mshort = mshort;
    }

    public long getMlong() {
        return mlong;
    }

    public void setMlong(long mlong) {
        this.mlong = mlong;
    }

    public float getMfloat() {
        return mfloat;
    }

    public void setMfloat(float mfloat) {
        this.mfloat = mfloat;
    }

    public double getMdouble() {
        return mdouble;
    }

    public void setMdouble(double mdouble) {
        this.mdouble = mdouble;
    }

    public byte getMbyte() {
        return mbyte;
    }

    public void setMbyte(byte mbyte) {
        this.mbyte = mbyte;
    }

    public char getMchar() {
        return mchar;
    }

    public void setMchar(char mchar) {
        this.mchar = mchar;
    }
}
