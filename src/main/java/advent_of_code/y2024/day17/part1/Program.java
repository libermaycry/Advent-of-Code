package advent_of_code.y2024.day17.part1;

import advent_of_code.utils.Strings;

import java.util.List;
import java.util.Map;

public class Program {

    Map<Register, Long> registers;
    List<Instruction> instructions;
    int pointer;
    String output;

    Program(Map<Register, Long> registers, List<Instruction> instructions) {
        this.registers = registers;
        this.instructions = instructions;
        this.pointer = 0;
        this.output = "";
    }

    void addOutput(String s) {
        if (Strings.isBlank(this.output)) this.output = s;
        else this.output += "," + s;
    }
}