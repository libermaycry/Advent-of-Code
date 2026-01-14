package advent_of_code.y2024.day17.part1;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChronospatialComputer1 extends AbstractRunnable {

    public static void main(String[] args) {
        new ChronospatialComputer1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    Program program;

    @Override
    protected void init() {
        Map<Register, Long> registers = new HashMap<>();
        List<List<String>> blocks = readBlocksOfLines();
        blocks.getFirst().forEach(line -> {
            String[] split = line.split(":");
            Register register = Register.valueOf(split[0].replace("Register ", ""));
            registers.put(register, Long.parseLong(split[1].trim()));
        });
        List<Instruction> instructions = new ArrayList<>();
        String[] programValues = blocks.getLast().getFirst().split(":")[1].trim().split(",");
        for (int i = 0; i < programValues.length - 1; i+=2) {
            int opcode = Integer.parseInt(programValues[i]);
            int operand = Integer.parseInt(programValues[i + 1]);
            instructions.add(new Instruction(new Opcode(opcode), new Operand(operand)));
        }
        program = new Program(registers, instructions);
    }

    @Override
    protected Object run() {
        while (program.pointer < program.instructions.size()) {
           program.instructions.get(program.pointer).execute(program);
        }
        return program.output;
    }

}
