package com.company;

import Models.Hlavolam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Hlavolam nacitaj() {
        int x, y;
        int[] array;
        Hlavolam h;
        System.out.printf("Zadaj rozmery(x y):\n");
        Scanner s = new Scanner(System.in);
        x = s.nextInt();
        y = s.nextInt();
        array = new int[x * y];
        h = new Hlavolam(x);
        System.out.printf("Zadaj tabulku:\n");
        for (int i = 0; i < x * y; i++) {
            array[i] = s.nextInt();
        }
        h.create(array);
        h.setVzdialenost(0);
        return h;
    }

    public static boolean areCompareable(Hlavolam a, Hlavolam b) {
        return a.getSirka() == b.getSirka() && a.getMapa().size() == b.getMapa().size();
    }

    public static List<Hlavolam> najdi_cestu(Hlavolam start, Hlavolam ciel) {
        List<Hlavolam> doSirky = new ArrayList<Hlavolam>();
        doSirky.add(start);
        List<Hlavolam> doHlbky = new ArrayList<Hlavolam>();
        doHlbky.add(ciel);
        pridaj_cestu(doSirky, doHlbky);

        return doSirky;
    }

    public static void prehladajDoHlbky(List<Hlavolam> doHlbky) {
        Random rand = new Random();
        boolean done = false;
        int n = rand.nextInt(4);
        Hlavolam start = doHlbky.get(doHlbky.size() - 1);
        while (!done) {
            if (n == 0) {
                if (start.getSpace() - start.getSirka() >= 0) {   //posun hore
                    Hlavolam temp = doHlbky.get(doHlbky.size() - 1).clonuj();
                    temp.swapHore();
                    doHlbky.add(temp);
                    done = true;
                } else
                    n++;
            }
            if (n == 1) {
                if (start.getSpace() + start.getSirka() < start.getMapa().size()) {   //posun dole
                    Hlavolam temp = doHlbky.get(doHlbky.size() - 1).clonuj();
                    temp.swapDole();
                    doHlbky.add(temp);
                    done = true;
                } else
                    n++;
            }
            if (n == 2) {
                if (start.getSpace() / start.getSirka() == (start.getSpace() + 1) / start.getSirka()) {   //posun vpravo
                    Hlavolam temp = doHlbky.get(doHlbky.size() - 1).clonuj();
                    temp.swapPravo();
                    doHlbky.add(temp);
                    done = true;
                } else
                    n++;
            }
            if (n == 3) {
                if (start.getSpace() / start.getSirka() == (start.getSpace() - 1) / start.getSirka() && start.getSpace() - 1 >= 0) {   //posun vlavo
                    Hlavolam temp = doHlbky.get(doHlbky.size() - 1).clonuj();
                    temp.swapLavo();
                    doHlbky.add(temp);
                    done = true;
                } else
                    n = 0;
            }
        }
    }

    public static void pridaj_cestu(List<Hlavolam> doSirky, List<Hlavolam> doHlbky) {
        boolean done = false;
        while (!done) {
            int size = doSirky.size();
            for (int i = 0; i < size; i++) {
                Hlavolam start = doSirky.get(i);
                if (!start.isFinal()) {
                    if (start.getSpace() - start.getSirka() >= 0) {   //posun hore
                        Hlavolam temp = doSirky.get(i).clonuj();
                        temp.swapHore();
                        if (!temp.isIn(doSirky)) {
                            doSirky.add(temp);
                            prehladajDoHlbky(doHlbky);
                            if (doHlbky.get(doHlbky.size() - 1).isIn(doSirky)) {
                                Hlavolam vHlbke = doHlbky.get(doHlbky.size() - 1);
                                temp = vHlbke.getFromList(doSirky);
                                System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do hlbky: %d\nVzdialenost prehladavanim do sirky: %d\n"
                                        , vHlbke.getVzdialenost() + temp.getVzdialenost(),
                                        vHlbke.getVzdialenost(),
                                        temp.getVzdialenost());
                                System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                done = true;
                                temp.printCesta();
                                vHlbke.printCestaBackw();
                                break;
                            } else {
                                if (temp.isIn(doHlbky)) {
                                    Hlavolam temp2 = temp.getFromList(doHlbky);
                                    System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do sirky: %d\nVzdialenost prehladavanim do hlbky: %d\n"
                                            , temp2.getVzdialenost() + temp.getVzdialenost(),
                                            temp.getVzdialenost(),
                                            temp2.getVzdialenost());
                                    System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                    done = true;
                                    System.out.printf("Prehladavanie do sirky:\n");
                                    temp.printCesta();
                                    System.out.printf("Prehladavanie do hlbky:\n");
                                    temp2.printCestaBackw();
                                    break;
                                }
                            }
                        }
                    }
                    if (start.getSpace() + start.getSirka() < start.getMapa().size()) {   //posun dole
                        Hlavolam temp = doSirky.get(i).clonuj();
                        temp.swapDole();
                        if (!temp.isIn(doSirky)) {
                            doSirky.add(temp);
                            prehladajDoHlbky(doHlbky);
                            if (doHlbky.get(doHlbky.size() - 1).isIn(doSirky)) {
                                Hlavolam vHlbke = doHlbky.get(doHlbky.size() - 1);
                                temp = vHlbke.getFromList(doSirky);
                                System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do hlbky: %d\nVzdialenost prehladavanim do sirky: %d\n"
                                        , vHlbke.getVzdialenost() + temp.getVzdialenost(),
                                        vHlbke.getVzdialenost(),
                                        temp.getVzdialenost());
                                System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                done = true;
                                temp.printCesta();
                                vHlbke.printCestaBackw();
                                break;
                            } else {
                                if (temp.isIn(doHlbky)) {
                                    Hlavolam temp2 = temp.getFromList(doHlbky);
                                    System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do sirky: %d\nVzdialenost prehladavanim do hlbky: %d\n"
                                            , temp2.getVzdialenost() + temp.getVzdialenost(),
                                            temp.getVzdialenost(),
                                            temp2.getVzdialenost());
                                    System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                    done = true;
                                    System.out.printf("Prehladavanie do sirky:\n");
                                    temp.printCesta();
                                    System.out.printf("Prehladavanie do hlbky:\n");
                                    temp2.printCestaBackw();
                                    break;
                                }
                            }
                        }
                    }
                    if (start.getSpace() / start.getSirka() == (start.getSpace() + 1) / start.getSirka()) {   //posun vpravo
                        Hlavolam temp = doSirky.get(i).clonuj();
                        temp.swapPravo();
                        if (!temp.isIn(doSirky)) {
                            doSirky.add(temp);
                            prehladajDoHlbky(doHlbky);
                            if (doHlbky.get(doHlbky.size() - 1).isIn(doSirky)) {
                                Hlavolam vHlbke = doHlbky.get(doHlbky.size() - 1);
                                temp = vHlbke.getFromList(doSirky);
                                System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do hlbky: %d\nVzdialenost prehladavanim do sirky: %d\n"
                                        , vHlbke.getVzdialenost() + temp.getVzdialenost(),
                                        vHlbke.getVzdialenost(),
                                        temp.getVzdialenost());
                                System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                done = true;
                                temp.printCesta();
                                vHlbke.printCestaBackw();
                                break;
                            } else {
                                if (temp.isIn(doHlbky)) {
                                    Hlavolam temp2 = temp.getFromList(doHlbky);
                                    System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do sirky: %d\nVzdialenost prehladavanim do hlbky: %d\n"
                                            , temp2.getVzdialenost() + temp.getVzdialenost(),
                                            temp.getVzdialenost(),
                                            temp2.getVzdialenost());
                                    System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                    done = true;
                                    System.out.printf("Prehladavanie do sirky:\n");
                                    temp.printCesta();
                                    System.out.printf("Prehladavanie do hlbky:\n");
                                    temp2.printCestaBackw();
                                    break;
                                }
                            }
                        }
                    }
                    if (start.getSpace() / start.getSirka() == (start.getSpace() - 1) / start.getSirka() && start.getSpace() - 1 >= 0) {   //posun vlavo
                        Hlavolam temp = doSirky.get(i).clonuj();
                        temp.swapLavo();
                        if (!temp.isIn(doSirky)) {
                            doSirky.add(temp);
                            prehladajDoHlbky(doHlbky);
                            if (doHlbky.get(doHlbky.size() - 1).isIn(doSirky)) {
                                Hlavolam vHlbke = doHlbky.get(doHlbky.size() - 1);
                                temp = vHlbke.getFromList(doSirky);
                                System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do hlbky: %d\nVzdialenost prehladavanim do sirky: %d\n"
                                        , vHlbke.getVzdialenost() + temp.getVzdialenost(),
                                        vHlbke.getVzdialenost(),
                                        temp.getVzdialenost());
                                System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                done = true;
                                temp.printCesta();
                                vHlbke.printCestaBackw();
                                break;
                            } else {
                                if (temp.isIn(doHlbky)) {
                                    Hlavolam temp2 = temp.getFromList(doHlbky);
                                    System.out.printf("Celkova vzdialenost: %d\nVzdialenost prehladavanim do sirky: %d\nVzdialenost prehladavanim do hlbky: %d\n"
                                            , temp2.getVzdialenost() + temp.getVzdialenost(),
                                            temp.getVzdialenost(),
                                            temp2.getVzdialenost());
                                    System.out.printf("Celkovo bolo vykonanych prehladavani: %d\n", doHlbky.size()+doSirky.size());
                                    done = true;
                                    System.out.printf("Prehladavanie do sirky:\n");
                                    temp.printCesta();
                                    System.out.printf("Prehladavanie do hlbky:\n");
                                    temp2.printCestaBackw();
                                    break;
                                }
                            }
                        }
                    }
                    start.setFinal(true);
                } else {

                }
            }
        }
    }

    public static void main(String[] args) {
        List<Hlavolam> cesta = new ArrayList<Hlavolam>();
        System.out.printf("Start:\n");
        Hlavolam start = nacitaj();
        System.out.printf("Ciel:\n");
        Hlavolam vysledok = nacitaj();
        if(areCompareable(start, vysledok))
            cesta = najdi_cestu(start, vysledok);
        else
            System.out.printf("Hlavolamy su nekompatibilne\n");
    }
}
