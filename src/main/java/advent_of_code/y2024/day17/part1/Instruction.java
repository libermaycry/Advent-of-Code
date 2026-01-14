package advent_of_code.y2024.day17.part1;

public class Instruction {

    Opcode opcode;
    Operand operand;

    public Instruction(Opcode opcode, Operand operand) {
        this.opcode = opcode;
        this.operand = operand;
    }

    public void execute(Program program) {
        opcode.run(operand, program);
    }


}
