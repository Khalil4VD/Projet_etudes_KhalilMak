//package src;

public class Sauveteur extends Personne {

    private Personne sauve = null;

    public Sauveteur(int id, Coordonnees position, int vent, Rectangle poste) {
        super(id, position, vent, 1);
        positionPlage = poste;
        etat = Etat.REPOS;
        vitesseNage = 0.02133;
    }

    public void setAge() {
        this.age = (int)Math.floor(18 + Math.random() * 8);
    }

    public Personne getSauve() {
        return sauve;
    }

    public void sauvetage(Coordonnees position, Personne sauve) {
        etat = Etat.PATH;
        objectif = Objectif.EVACUATION;
        objPosition = position;
        this.sauve = sauve;
    }
    
    public void run() {

        alive = true;
        int iterStatique = 0;
        int tempsPatrouille = 0;

        while (!Thread.interrupted()) {
            int sleeper = 10;
            if (etat == Etat.MOUVEMENT) {
                //System.out.println(position[0]+" "+objPosition[0]+" "+position[1]+" "+objPosition[1]);
                if (position.equals(objPosition)) {
                    stackMove.remove(0);
                    if (stackMove.size() == 0) {
                        vecteurCourant = null;
                        if (objectif == Objectif.PATROUILLE) {
                            etat = Etat.ATTENTE;
                        } else if (objectif == Objectif.EVACUATION) {
                            etat = Etat.EVACUATION;
                        } else if (objectif == Objectif.REPOS) {
                            etat = Etat.REPOS;
                        } else if (objectif == Objectif.SAUVETAGE) {
                            etat = Etat.SAUVETAGE;
                        }

                        vecteurCourant = null;
                        objPosition = null;
                    } else {
                        vecteurCourant = stackMove.get(0);
                        objPosition = vecteurCourant.getCoordsObj();
                        sleeper = vecteurCourant.getTiming();
                    } 

                } else {
                    vecteurCourant.glissement();
                    position = vecteurCourant.getCoords();
                    if (objectif == Objectif.SAUVETAGE) {
                        sauve.position = vecteurCourant.getCoords();
                    }
                }
                
            } else if (etat == Etat.ATTENTE) {
                iterStatique += 10*Coeff.getCoeff();
                if (iterStatique >= 60000) {
                    tempsPatrouille += 1;
                    if (tempsPatrouille == 10) {
                        objectif = Objectif.REPOS;
                        objPosition = positionPlage.getCentre();
                        tempsPatrouille = 0;
                    } else {
                        objectif = Objectif.PATROUILLE;
                    }
                    etat = Etat.PATH;
                    iterStatique = 0;
                }

            } else if (etat == Etat.REPOS) {
                iterStatique += 10*Coeff.getCoeff();
                if (iterStatique >= 1200000) {
                    objectif = Objectif.PATROUILLE;
                    etat = Etat.PATH;
                    iterStatique = 0;
                }

            } else if (etat == Etat.PATH && vecteurCourant != null) {
                etat = Etat.MOUVEMENT;
            } else if (etat == Etat.EVACUATION) {
                objectif = Objectif.SAUVETAGE;
                etat = Etat.PATH;
            } else if (etat == Etat.SAUVETAGE) {
                if (position.getX() < 0) {
                    sauve = null;
                    objectif = Objectif.REPOS;
                    etat = Etat.PATH;
                } else {
                    iterStatique += 10*Coeff.getCoeff();
                    if (iterStatique >= 60000) {
                        if (sauve.stamina > 0) {
                            sauve.objectif = Objectif.REPOS;
                            sauve.objPosition = sauve.positionPlage.getCentre();
                            sauve.etat = Etat.PATH;
                            sauve = null;
                            objectif = Objectif.REPOS;
                            objPosition = positionPlage.getCentre();
                            etat = Etat.PATH;
                        } else {
                            System.out.println("DEADGE");
                            objectif = Objectif.SAUVETAGE;
                            objPosition = new Coordonnees(-5, position.getY());
                            etat = Etat.PATH;
                        }

                        iterStatique = 0;
                    }
                }
                
            }

            oath = false;
            try {
                Thread.sleep(sleeper);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    
    }

}
