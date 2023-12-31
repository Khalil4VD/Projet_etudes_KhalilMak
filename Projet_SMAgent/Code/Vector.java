//package src;
public abstract class Vector {
    protected double x, y, objX, objY;

    protected double vitesse;
    
    protected int sensX;
    protected int sensY;

    protected int timing;
    
    public Vector(double x, double y, double objX, double objY, double vitesse, int timing) {
        this.x = x;
        this.y = y;
        this.objX = objX;
        this.objY = objY;
        this.vitesse = vitesse;
        this.timing = timing;
        setSens();
    }

    Vector(Vector vect) {
        this(vect.x,vect.y,vect.objX,vect.objY,vect.vitesse,vect.timing);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Coordonnees getCoords() {
        return new Coordonnees(x, y);
    }

    public double getObjX() {
        return objX;
    }

    public double getObjY() {
        return objY;
    }

    public Coordonnees getCoordsObj() {
        return new Coordonnees(objX, objY);
    }

    public double getVitesse() {
        return vitesse;
    }

    public int getTiming() {
        return timing;
    }

    public void setCoords(Coordonnees coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }
    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordsObj(Coordonnees coords) {
        this.objX = coords.getX();
        this.objY = coords.getY();
    }
    public void setCoordsObj(double x, double y) {
        this.objX = x;
        this.objY = y;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public void setSens() {
        sensX = x > objX?-1:1;
        sensY = y > objY?-1:1;
    }

    public int getSensX() {
        return sensX;
    }

    public boolean isArrived() {
        return x == objX && y == objY;
    }

    public double[] isCroisement(Vector vect){
        double incX = -1;
        double incY = -1;
        if (this instanceof VectOblique && vect instanceof VectOblique) {
            VectOblique vect1 = (VectOblique)this;
            VectOblique vect2 = (VectOblique)vect;

            if (vect2.getM() - vect1.getM() == 0) {
                return new double[]{-1,-1}; // Les deux droites sont parallèles
            }

            double testX = (vect1.getP() - vect2.getP())/(vect2.getM() - vect1.getM());
            double testY = vect1.getM()*testX + vect1.getP();

            if (((vect1.objX >= testX && testX >= vect1.x) || (vect1.objX <= testX && testX <= vect1.x)) && ((vect1.objY >= testY && testY >= vect1.y) || (vect1.objY <= testY && testY <= vect1.y))) {
                incX = testX;
                incY = testY;
            }

        } else if (this instanceof VectVertical && vect instanceof VectVertical) {
            if (this.x == vect.x) {
                incX = x;
                incY = y;
            }

        } else if ((this instanceof VectOblique || vect instanceof VectOblique) && ((this instanceof VectVertical || vect instanceof VectVertical))) {
            VectOblique vectO;
            VectVertical vectV;

            if (this instanceof VectOblique) {
                vectO = (VectOblique)this;
                vectV = (VectVertical)vect;
            } else {
                vectV = (VectVertical)this;
                vectO = (VectOblique)vect;
            }

            double testY = vectV.x * vectO.getM() - vectO.getP();

            if ((vectO.objY >= testY && testY >= vectO.y) || (vectO.objY <= testY && testY <= vectO.y)) {
                incX = x;
                incY = testY;
            }

        }
        return new double[]{incX,incY};
    }

    public abstract void glissement();
    public abstract Vector copy();
    public abstract Coordonnees croisementRectangle(Rectangle rect); 

    public static boolean isCoordsNull(double[] coords) {
        return coords[0] == -1 && coords[1] == -1;
    }

    public static boolean isCoordsNull(Coordonnees coords) {
        return coords.getX() == -1 && coords.getY() == -1;
    }

    public static boolean isCollision(Vector vect1, Vector vect2) {
        boolean flag = false;
        while (!vect1.isArrived() && !vect2.isArrived() && !flag) {
            vect1.glissement();
            vect2.glissement();
            if (Math.sqrt(Math.pow(vect1.x - vect2.x, 2) + Math.pow(vect1.y - vect2.y,2)) < 1) {
                flag = true;
            }
        }
        return flag;
    }

    public static Vector choixVector(Coordonnees position, Coordonnees objPosition, double vitesse, int timing) {
        if (position.getX() == objPosition.getX()) {
            return new VectVertical(position.getX(), position.getY(), objPosition.getX(), objPosition.getY(), vitesse, timing);
        } else {
            return new VectOblique(position.getX(), position.getY(), objPosition.getX(), objPosition.getY(), vitesse, timing);
        }
    }

    public int getSensY() {
        return sensY;
    }

    public String toString() {
        return x+" "+y+" / "+objX+" "+objY;
    }
}


/* 
 * D = (0,0)
 * C = (1.7,0)
 * a(x - x1)(x - x2)
 * a(x)(x - 1.7) = y
 * ax(x-1.7) = y
 * ax^2 - 1.7ax = y
 * 
 * 
 * 
*/