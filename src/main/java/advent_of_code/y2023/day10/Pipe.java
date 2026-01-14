package advent_of_code.y2023.day10;

import static advent_of_code.y2023.day10.Direction.*;

public enum Pipe {

    HOR('-', LEFT, RIGHT),
    VER('|', UP, DOWN),
    L('L', UP, RIGHT),
    J('J', UP, LEFT),
    _7('7', LEFT, DOWN),
    F('F', RIGHT, DOWN),
    GROUND('.', null, null),
    START('S', null, null);

    final char symbol;
    final Direction connection1;
    final Direction connection2;

    Pipe(char symbol, Direction connection1, Direction connection2) {
        this.symbol = symbol;
        this.connection1 = connection1;
        this.connection2 = connection2;
    }

    static Pipe fromSymbol(char symbol) {
        for (Pipe pipe : values()) {
            if (pipe.symbol == symbol) return pipe;
        }
        return null;
    }
}
