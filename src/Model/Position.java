package Model;

public class Position {

    private int column;
    private int line;

    public Position(int i, int x) {
        this.line = i;
        this.column = x;
    }

    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public void setColumn(int colonne) {
        this.column = colonne;
    }

    public void setLine(int ligne) {
        this.line = ligne;
    }

    public String columnToChar() {
        int c = 65 + this.column;
        char x = (char) c;
        String res = Character.toString(x);
        return res;
    }

    public String columnToChar(int col) {
        int c = 65 + col;
        char x = (char) c;
        String res = Character.toString(x);
        return res;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.column;
        hash = 83 * hash + this.line;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.column != other.column) {
            return false;
        }
        if (this.line != other.line) {
            return false;
        }
        return true;
    }

}
