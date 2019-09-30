package View;

import java.util.Scanner;
import Control.Interaction;
import Model.Army;
import Model.BoatType;
import Model.Boat;
import Model.Color;
import Model.MineType;
import Model.SeaFacade;
import Model.Position;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    // Change attribut showMine pour montrer les mines ou non
    private static boolean showMine = true;
    private static final View VUE = new View();

    public static void montrerGagnant(SeaFacade facade) {
        if (facade.getSizeOfArmy(facade.getArmyOne()) > 0) {
            System.out.println("Le gagnant est : " + facade.getArmyOne().getName());
        } else {
            System.out.println("Le gagnant est : " + facade.getArmyTwo().getName());
        }
    }

    Scanner scan = new Scanner(System.in);

    public static View getInstance() {
        return VUE;
    }

    public static String askName(String numero) {
        System.out.println("Nom armee " + numero);
        return Interaction.entrer();
    }

    // Modifier createTab et decouper en plusieurs fonctions
    public static void createTab(SeaFacade facade) {
        printLetter(facade);
        printLine(facade);
        System.out.println("");
        printState();
        printArmy(facade.getArmyOne());
        printArmy(facade.getArmyTwo());
    }

    static void printLetter(SeaFacade facade) {
        System.out.print("  ");
        for (int col = 0; col < facade.getSize(); ++col) {
            System.out.print(" " + printChar(col) + " ");
        }
        System.out.println("");
    }

    private static char printChar(int col) {
        int c = 65 + col;
        char res = (char) c;
        return res;
    }

    private static void printNumber(int ligne) {
        System.out.print(ligne + 1 + " ");
    }

    private static void printLine(SeaFacade facade) {
        for (int ligne = 0; ligne < facade.getSize(); ++ligne) {
            printNumber(ligne);
            printColumn(facade, ligne);
            System.out.println("");
        }

    }

    private static void printColumn(SeaFacade facade, int ligne) {
        for (int col = 0; col < facade.getSize(); ++col) {
            if (facade.checkIfMineInPosition(new Position(ligne, col)) || facade.checkIfBoatInPosition((new Position(ligne, col)))) {
                printCaseWithObject(facade, new Position(ligne, col));
            } else {
                printCaseEmpty();
            }
        }
    }

    private static void printCaseWithObject(SeaFacade facade, Position p) {
        if (facade.getBoatInPosition(p) != null) {
            System.out.print(Color.RESET + "|");
            if (facade.getBoatInPosition(p).getType() == BoatType.BIG) {
                System.out.print(facade.getBoatInPosition(p).getCol());
                System.out.print("B");
            } else {
                System.out.print(facade.getBoatInPosition(p).getCol());
                System.out.print("S");
            }
            System.out.print(Color.RESET + "|");
        } else {
            if (!showMine) {
                printCaseEmpty();
            } else {
                printMine(facade, p);
            }
        }
    }

    private static void printMine(SeaFacade facade, Position p) {
        if (facade.getMineInPosition(p).getType() == MineType.NORMAL) {
            System.out.print(Color.RESET + "|" + "M" + "|");
        } else {
            System.out.print(Color.RESET + "|" + "R" + "|");
        }
    }

    private static void printCaseEmpty() {
        System.out.print("|" + " " + "|");
    }

    public static void printState() {
        System.out.println("Etat des armées");
    }

    public static void printArmy(Army armee) {
        System.out.print(armee.getCol());
        System.out.println(armee.getName());
        printPositionBoatIntegrityTitle();
        for (Boat bat : armee.getListBoatOfArmy()) {
            System.out.print(armee.getCol());
            printPosition(bat.getPos());
            printBoat(bat);
        }
        System.out.print(Color.RESET);
    }

    public static void printPositionBoatIntegrityTitle() {
        System.out.println("Position - Bateaux - Integrité");
    }

    public static void printBoat(Boat bat) {
        if (bat.getType() == BoatType.BIG) {
            System.out.println("BIG" + "\t" + "\t" + bat.getIntegrity());
        } else {
            System.out.println("SMALL" + "\t" + bat.getIntegrity());
        }
    }

    public static void printPosition(Position pos) {
        System.out.print(pos.columnToChar() + (pos.getLine() + 1) + "\t" + "   ");
    }

    public static String selectBoat(Army armee) {
        System.out.println("Deplacement Bateau");
        System.out.print(armee.getName() + " , à vous de bouger. Position du bateau à déplacer ([A-Ea-e][1-5]) :");
        return Interaction.entrer();
    }

    public static String selectPosition() {
        System.out.print("Sélectionner sa nouvelle position : ");
        return Interaction.entrer();
    }

//    public static String selectionCible() {
//        System.out.println("selectionner la cible");
//        return Interaction.entrer();
//
//    }
    public static void printMineRadio() {
        System.out.println("Vous êtes sur une mine radioactive !");
    }

    public static void printMine() {
        System.out.println("Vous êtes sur une mine !");
    }

    public static String selectBoatToShoot(Army armee) {
        System.out.println("Tir d'un bateau");
        System.out.print(armee.getName() + " , à vous de tirer. Position du bateau tireur ([A-Ea-e][1-5]) :");
        return Interaction.entrer();
    }

//    public static void printPossiblePosition(List<Position> pos) {
//        if (!pos.isEmpty()) {
//            for (Position p : pos) {
//                System.out.println(p);
//            }
//        }
//    }
    public static void shootingRange(int range) {
        System.out.println("Portée du tir : " + range);
    }

    public static void showPositionPossible(ArrayList<Position> checkDistance) {
        String res = "Position(s) possible : ";
        for (Position p : checkDistance) {

            res += p.columnToChar(p.getColumn()) + (p.getLine() + 1) + " - ";
        }
        System.out.println(res);
    }

    @Override
    public void update(Observable obs, Object arg) {
        SeaFacade facade = (SeaFacade) obs;
        createTab(facade);
    }

}
