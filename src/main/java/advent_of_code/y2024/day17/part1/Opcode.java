package advent_of_code.y2024.day17.part1;

import advent_of_code.utils.Binary;
import advent_of_code.utils.Numbers;

public class Opcode {

    int value;

    public Opcode(int value) {
        this.value = value;
    }

    void run(Operand operand, Program program) {
        switch (this.value) {
            case 0: adv(operand, program); break;
            case 1: bxl(operand, program); break;
            case 2: bst(operand, program); break;
            case 3: jnz(operand, program); break;
            case 4: bxc(program); break;
            case 5: out(operand, program); break;
            case 6: bdv(operand, program); break;
            case 7: cdv(operand, program); break;
            default: throw new IllegalStateException("Unexpected opcode: " + this.value);
        }
    }

    private void adv(Operand operand, Program program) {
        program.registers.put(Register.A, dv(operand, program));
        program.pointer ++;
    }

    private void bxl(Operand operand, Program program) {
        long result = Binary.xor(program.registers.get(Register.B), operand.get(Operand.Type.LITERAL, program));
        program.registers.put(Register.B, result);
        program.pointer ++;
    }

    private void bst(Operand operand, Program program) {
        long combo = operand.get(Operand.Type.COMBO, program);
        program.registers.put(Register.B, combo % 8);
        program.pointer ++;
    }

    private void jnz(Operand operand, Program program) {
        if (program.registers.get(Register.A) == 0) {
            program.pointer ++;
        } else {
            program.pointer = (int) operand.get(Operand.Type.LITERAL, program) / 2;
        }
    }

    private void bxc(Program program) {
        long bXOR = Binary.xor(program.registers.get(Register.B), program.registers.get(Register.C));
        program.registers.put(Register.B, bXOR);
        program.pointer ++;
    }

    private void out(Operand operand, Program program) {
        program.addOutput(String.valueOf(operand.get(Operand.Type.COMBO, program) % 8));
        program.pointer ++;
    }

    private void bdv(Operand operand, Program program) {
        program.registers.put(Register.B, dv(operand, program));
        program.pointer ++;
    }

    private void cdv(Operand operand, Program program) {
        program.registers.put(Register.C, dv(operand, program));
        program.pointer ++;
    }

    private long dv(Operand operand, Program program) {
        long numerator = program.registers.get(Register.A);
        long denominator = Numbers.pow(2, operand.get(Operand.Type.COMBO, program));
        return (long) Math.floor((double) numerator / denominator);
    }


}
