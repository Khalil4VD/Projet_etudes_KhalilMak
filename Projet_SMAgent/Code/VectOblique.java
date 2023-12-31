//package src;
public class VectOblique extends Vector {
    private double m, p;

    public VectOblique(double x, double y, double objX, double objY, double vitesse, int timing) {
        super(x, y, objX, objY, vitesse,timing);
        setM();
        setP();

        //System.out.println(x+" "+y+" "+objX+" "+objY+" V:"+vitesse+" "+m+" "+p+" "+sensX+" "+sensY);
    }

    VectOblique(VectOblique vect) {
        this(vect.x,vect.y,vect.objX,vect.objY,vect.vitesse,vect.timing);
    }

    public VectOblique copy() {
        return new VectOblique(this);
    } 

    public double getM() {
        return m;
    }

    public double getP() {
        return p;
    }

    public void setM() {
        this.m = objX-x==0?0:(objY-y)/(objX-x);
    }

    public void setP() {
        this.p = y-m*x;
    }


    public void glissement() {
        x += sensX*Math.sqrt(Math.pow(vitesse*Coeff.getCoeff(),2)/(Math.pow(m,2)+1));
        y = m*x + p;
        if ((sensX == 1 && sensY == 1 && x >= objX && y >= objY) || (sensX == 1 && sensY == -1 && x >= objX && y <= objY) || (sensX == -1 && sensY == 1 && x <= objX && y >= objY) || (sensX == -1 && sensY == -1 && x <= objX && y <= objY)) {
            x = objX;
            y = objY;
        }
    }

    public Coordonnees croisementRectangle(Rectangle rect) {
        boolean flag = false;
        for (double i=rect.getA().getX();i<=rect.getB().getX() && !flag;i+=0.1) {
            if (m*i+p <= rect.getA().getY() && m*i+p >= rect.getD().getY()) {
                flag = true;
            }
        }

        if (flag) {
            Vector vectCop = copy();
            double colX, colY;
            do {   
                colX = vectCop.x;
                colY = vectCop.y;
                vectCop.glissement();
            } while ((sensY == 1 && vectCop.y < rect.getD().getY()) || (sensY == -1 && vectCop.y > rect.getA().getY()));

            return new Coordonnees(colX, colY);
            
        }
        return new Coordonnees(-1,-1);
    }
}
