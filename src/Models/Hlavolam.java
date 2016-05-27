package Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lukas on 12-Mar-16.
 */
public class Hlavolam {
    private List<Integer> mapa;
    private int sirka;
    private boolean isFinal;
    private int vzdialenost;
    private Hlavolam parent;

    public Hlavolam(List mapa, int sirka, boolean isFinal, int vzdialenost, Hlavolam parent) {
        this.mapa = new ArrayList<Integer>(mapa);
        this.sirka = sirka;
        this.isFinal = isFinal;
        this.vzdialenost = vzdialenost;
        this.parent = parent;
    }

    public Hlavolam clonuj() {
        return new Hlavolam(mapa, sirka, isFinal, vzdialenost, this);
    }

    public Hlavolam(int sirka) {
        mapa = new ArrayList<>();
        this.sirka = sirka;
        isFinal = false;
        parent=null;
    }

    public Hlavolam getFromList(List<Hlavolam> zoznam) {
        for (int i = 0; i < zoznam.size(); i++) {
            if (zoznam.get(i).getMapa().equals(mapa))
                return zoznam.get(i);
        }
        return null;
    }

    public int getSirka() {
        return sirka;
    }

    public List getMapa() {
        return mapa;
    }

    public void create(int[] array) {
        for (int anArray : array) {
            mapa.add(anArray);
        }
    }

    public int getSpace() {
        return mapa.indexOf(0);
    }

    public void printCestaBackw(){
        this.print();
        if(this.parent != null)
            this.parent.printCestaBackw();
    }

    public void printCesta(){
        if(this.parent != null)
            this.parent.printCesta();
        this.print();
    }

    private void print() {
        System.out.printf("Iteracia: %d\n", vzdialenost);
        for (int i = 0; i < mapa.size() / sirka; i++) {
            for (int j = 0; j < sirka; j++) {
                System.out.printf("%d ", mapa.get(i * sirka + j));
            }
            System.out.printf("\n");
        }
    }

    public boolean isIn(List<Hlavolam> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMapa().equals(mapa))
                return true;
        }
        return false;
    }

    public void swapHore() {
        Collections.swap(mapa, getSpace(), getSpace() - sirka);
        vzdialenost++;
    }

    public void swapDole() {
        Collections.swap(mapa, getSpace(), getSpace() + sirka);
        vzdialenost++;
    }

    public void swapPravo() {
        Collections.swap(mapa, getSpace(), getSpace() + 1);
        vzdialenost++;
    }

    public void swapLavo() {
        Collections.swap(mapa, getSpace(), getSpace() - 1);
        vzdialenost++;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public int getVzdialenost() {
        return vzdialenost;
    }

    public void setVzdialenost(int vzdialenost) {
        this.vzdialenost = vzdialenost;
    }

}
