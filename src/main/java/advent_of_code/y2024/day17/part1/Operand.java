package advent_of_code.y2024.day17.part1;

public class Operand {

    int value;

    public enum Type {LITERAL, COMBO}

    public Operand(int value) {
        this.value = value;
    }

    public long get(Type type, Program program) {
        return switch (type) {
            case LITERAL -> (long) this.value;
            case COMBO -> {
                if (this.value <= 3)
                    yield get(Type.LITERAL, program);
                yield switch(this.value) {
                    case 4: yield program.registers.get(Register.A);
                    case 5: yield program.registers.get(Register.B);
                    case 6: yield program.registers.get(Register.C);
                    default: throw new IllegalStateException("get combo operand 7 not supported");
                };
            }
        };
    }

}
