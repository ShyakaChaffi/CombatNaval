package Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Sea {

    private int size = 5;
    private final Map<Position, Case> boardGame = new HashMap<>();

    public Map<Position, Case> getBoardGame() {
        return this.boardGame;
    }

    public Sea() {
    }

    public Sea(int x) {
        this.size = x;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int x) {
        this.size = x;
    }

    public ArrayList<Position> getListPositionsOfMine() {
        ArrayList<Position> positionMine = new ArrayList();
        for (int i = 0; i < this.getSize(); ++i) {
            for (int c = 0; c < this.getSize(); ++c) {
                if (this.getBoardGame().containsKey(new Position(i, c))) {
                    if (this.checkIfMineInPosition(new Position(i, c))) {
                        positionMine.add(new Position(i, c));
                    }
                }
            }
        }
        return positionMine;
    }

    public Boat getBoatInPosition(Position currentPosition) {
        return this.boardGame.get(currentPosition).getBoat();
    }

    public Mine getMineInPosition(Position currentPosition) {
        return this.boardGame.get(currentPosition).getMine();
    }

    public Case getCaseInPosition(Position currentPosition) {
        return this.boardGame.get(currentPosition);
    }

    public boolean checkIfMineInPosition(Position currentPosition) {
        if (boardGame.containsKey(currentPosition)) {
            return this.boardGame.get(currentPosition).getMine() != null;
        }
        return false;
    }

    public boolean checkIfBoatInPosition(Position currentPosition) {
        if (boardGame.containsKey(currentPosition)) {
            return this.boardGame.get(currentPosition).getBoat() != null;
        }
        return false;
    }

    public boolean checkIfCaseIsRadioactive(Position currentPosition) {
        if (this.checkIfMineInPosition(currentPosition)) {
            return this.boardGame.get(currentPosition).getMine().getRadio();
        }
        return false;
    }

    public void setBoatsInSea(Army army) {
        for (Boat bat : army.getListBoatOfArmy()) {
            boolean placer = false;
            do {
                Position p = new Position(probability(), probability());
                if (!this.boardGame.containsKey(p)) {
                    Case c = new Case(p);
                    c.setBat(bat);
                    this.boardGame.put(p, c);
                    bat.setPos(p);
                    placer = true;
                }
            } while (!placer);
        }
    }

    public void setOneBoatsInSea(Boat boat, Position p) {
        if (!this.boardGame.containsKey(p)) {
            Case c = new Case(p);
            c.setBat(boat);
            this.boardGame.put(p, c);
            boat.setPos(p);
        }
    }

    public void setMineInSea() {
        for (int i = 0; i < this.getSize(); ++i) {
            for (int x = 0; x < this.getSize(); ++x) {
                if (!this.boardGame.containsKey(new Position(i, x))) {
                    if (Mine.placeMine()) {
                        Case c = new Case(new Position(i, x));
                        c.setMine(Mine.typeMine());
                        this.boardGame.put(new Position(i, x), c);
                    }
                }
            }
        }
    }

    public Case removeCaseInPosition(Position currentPosition) {
        return this.boardGame.remove(currentPosition);
    }

    public Position convertStringPositionIntoObjectPosition(String currentPosition) {
        currentPosition = currentPosition.toUpperCase();
        char a = 'A';
        char c1 = currentPosition.charAt(0);
        int col = (int) c1 - (int) a;
        int ligne = Integer.parseInt(String.valueOf(currentPosition.charAt(1)));
        return new Position(ligne - 1, col);
    }

    // Deplacement d'un bateau //
    public void moveBoatInNewPosition(Position currentPosition, Position nextPosition) {
        Case c = this.removeCaseInPosition(currentPosition);
        c.setPosition(nextPosition);
        this.boardGame.put(nextPosition, c);
    }

    public ArrayList getPositionsForMove(Case c) {
        ArrayList<Position> res = new ArrayList<>();
        Position act = c.getPosition();
        res.add(act);
        res.addAll(getPositionsWest(act.getLine() + 1, act.getColumn(), c.getBoat().getCapacityMove()));
        res.addAll(getPositionsEast(act.getLine() + 1, act.getColumn(), c.getBoat().getCapacityMove()));
        res.addAll(GetPositionsNorth(act.getLine() + 1, act.getColumn(), c.getBoat().getCapacityMove()));
        res.addAll(getPositionsSouth(act.getLine() + 1, act.getColumn(), c.getBoat().getCapacityMove()));
        return res;
    }

    private List getPositionsWest(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = 0; i < step; ++i) {
            x += 1;
            if (x == this.getSize() + 1) {
                x = 1;
            }
            if (!checkIfBoatInPosition(new Position(x - 1, y)) && !checkIfCaseIsRadioactive(new Position(x - 1, y))) {
                res.add(new Position(x - 1, y));
            }
        }
        return res;
    }

    private List getPositionsEast(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = step; i > 0; --i) {
            x -= 1;
            if (x == 0) {
                x = this.getSize();
            }
            if (!checkIfBoatInPosition(new Position(x - 1, y)) && !checkIfCaseIsRadioactive(new Position(x - 1, y))) {
                res.add(new Position(x - 1, y));
            }
        }
        return res;
    }

    private List GetPositionsNorth(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = 0; i < step; ++i) {
            y += 1;
            if (y == this.getSize()) {
                y = 0;
            }
            if (!checkIfBoatInPosition(new Position(x - 1, y)) && !checkIfCaseIsRadioactive(new Position(x - 1, y))) {
                res.add(new Position(x - 1, y));
            }
        }
        return res;
    }

    private List getPositionsSouth(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = 0; i < step; ++i) {
            y -= 1;
            if (y < 0) {
                y = this.getSize() - 1;
            }
            if (!checkIfBoatInPosition(new Position(x - 1, y)) && !checkIfCaseIsRadioactive(new Position(x - 1, y))) {
                res.add(new Position(x - 1, y));
            }
        }
        return res;
    }

    //TIR D'UN BATEAU
    public void setDegatOfShoot(String p) {
        Position pos = convertStringPositionIntoObjectPosition(p);
        if (!checkIfBoatInPosition(pos) && boardGame.containsKey(pos)) {
            this.boardGame.get(pos).getBoat().setResistance(-1);
            if (this.boardGame.get(pos).getBoat().getResistance() == 0) {
                boardGame.remove(pos);
            }
        }
    }

    public Position getRandomPositionForAShoot(List<Position> tir) {
        Random rand = new Random();
        return tir.get(rand.nextInt(tir.size()));
    }

    public ArrayList getPositionsInRangeShooting(Position p, int tir) {
        ArrayList<Position> res = new ArrayList<>();
        Position act = p;
        res.addAll(getPositionsInRangeShootingWest(act.getLine() + 1, act.getColumn(), tir));
        res.addAll(getPositionsInRangeShootingEast(act.getLine() + 1, act.getColumn(), tir));
        res.addAll(getPositionsInRangeShootingNorth(act.getLine() + 1, act.getColumn(), tir));
        res.addAll(getPositionsInRangeShootingSouth(act.getLine() + 1, act.getColumn(), tir));
        return res;
    }

    private List<Position> getPositionsInRangeShootingWest(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = 0; i < step; ++i) {
            x += 1;
            if (x == this.getSize() + 1) {
                x = 1;
            }
            res.add(new Position(x - 1, y));
        }
        return res;
    }

    private List<Position> getPositionsInRangeShootingEast(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = step; i > 0; --i) {
            x -= 1;
            if (x == 0) {
                x = this.getSize();
            }
            res.add(new Position(x - 1, y));
        }
        return res;
    }

    private List<Position> getPositionsInRangeShootingNorth(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = 0; i < step; ++i) {
            y += 1;
            if (y == this.getSize()) {
                y = 0;
            }
            res.addAll(getPositionsInRangeShootingWest(x, y, step));
            res.addAll(getPositionsInRangeShootingEast(x, y, step));
            res.add(new Position(x - 1, y));

        }
        return res;
    }

    private List<Position> getPositionsInRangeShootingSouth(int x, int y, int step) {
        List<Position> res = new ArrayList<>();
        for (int i = 0; i < step; ++i) {
            y -= 1;
            if (y < 0) {
                y = this.getSize() - 1;
            }
            res.addAll(getPositionsInRangeShootingWest(x, y, step));
            res.addAll(getPositionsInRangeShootingEast(x, y, step));
            res.add(new Position(x - 1, y));
        }
        return res;
    }

    public int probability() {
        int res = new Random().nextInt();
        while (res <= 0) {
            res = new Random().nextInt();
        }
        return (res % this.size);
    }
}
