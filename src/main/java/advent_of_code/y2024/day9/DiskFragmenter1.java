package advent_of_code.y2024.day9;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DiskFragmenter1 extends AbstractRunnable {

    public static void main(String[] args) {
        new DiskFragmenter1().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Integer> blocks;
    List<Integer> rearranged;

    @Override
    protected void init() {

        blocks = new ArrayList<>();

        char[] charArray = readLines().toList().getFirst().toCharArray();
        List<Integer> diskMap = IntStream
                .range(0, charArray.length)
                .mapToObj(i -> charArray[i])
                .map(Character::getNumericValue)
                .toList();

        boolean file = true;
        int fileId = 0;
        for (Integer size : diskMap) {
            for (int i = 0; i < size; i++) {
                if (file) blocks.add(fileId);
                else blocks.add(null);
            }
            if (file) fileId ++;
            file = !file;
        }
    }

    @Override
    protected Object run() {
        rearranged = rearrange();
        return IntStream.range(0, rearranged.size())
                .map(i -> rearranged.get(i) == null ? 0 : rearranged.get(i) * i)
                .mapToLong(Long::valueOf)
                .sum();
    }

    private List<Integer> rearrange() {
        List<Integer> newBlocks = new ArrayList<>(blocks);
        for (int i = newBlocks.size() - 1; i >= 0; i--) {
            Integer toMove = newBlocks.get(i);
            if (toMove != null) {
                int index = getFirstFreeSpaceIndex(newBlocks);
                if (index > i)
                    break;
                newBlocks.set(index, toMove);
                newBlocks.set(i, null);
            }
        }
        return newBlocks;
    }

    private int getFirstFreeSpaceIndex(List<Integer> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) == null) return i;
        }
        throw new IllegalStateException();
    }
}
