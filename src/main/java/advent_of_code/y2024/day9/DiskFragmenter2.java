package advent_of_code.y2024.day9;


import advent_of_code.y2024.AbstractRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class DiskFragmenter2 extends AbstractRunnable {

    public static void main(String[] args) {
        new DiskFragmenter2().start();
    }

    @Override
    protected String source() { return "input.txt"; }

    List<Integer> singleBlocks;

    List<Block> blocks;

    @Override
    protected void init() {

        singleBlocks = new ArrayList<>();

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
                if (file) singleBlocks.add(fileId);
                else singleBlocks.add(null);
            }
            if (file) fileId ++;
            file = !file;
        }

        initBlocksList();
    }

    void initBlocksList() {

        blocks = new ArrayList<>();

        Block currentBlock = new Block(0, 1, singleBlocks.getFirst());

        for (int i = 1; i < singleBlocks.size(); i++) {
            Integer fileId = singleBlocks.get(i);
            if (!Objects.equals(fileId, currentBlock.fileId)) {
                blocks.add(currentBlock);
                currentBlock = new Block(i, 1, fileId);
            } else {
                currentBlock.size ++;
            }
        }

        blocks.add(currentBlock);
    }

    @Override
    protected Object run() {
        return rearrange().stream().map(Block::checkSum).mapToLong(x -> x).sum();
    }

    private List<Block> rearrange() {
        List<Block> newBlocks = new ArrayList<>(blocks);
        for (int i = newBlocks.size() - 1; i >= 0; i--) {
            Block block = newBlocks.get(i);
            if (block.fileId != null) {
                Block emptyBlock = findFirstEmptyBlockThatFits(newBlocks, block);
                if (emptyBlock != null) {
                    place(block, emptyBlock, newBlocks);
                    i = newBlocks.size();
                }
            }
        }
        return newBlocks;
    }

    private void place(Block block, Block emptyBlock, List<Block> blocks) {

        int emptyBlockIndexList = -1;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).index == emptyBlock.index) {
                emptyBlockIndexList = i;
                break;
            }
        }

        if (emptyBlockIndexList == -1) throw new IllegalStateException();

        blocks.add(emptyBlockIndexList, new Block(emptyBlock.index, block.size, block.fileId));
        emptyBlock.index += block.size;
        emptyBlock.size -= block.size;
        block.fileId = null;

        blocks.removeIf(b -> b.size == 0);
    }

    private Block findFirstEmptyBlockThatFits(List<Block> blocks, Block toMove) {
        for (Block block : blocks) {
            if (block.fileId == null
                    && block.size >= toMove.size
                    && block.index < toMove.index)
                return block;
        }
        return null;
    }


    class Block {
        int index;
        int size;
        Integer fileId;

        public Block(int index, int size, Integer fileId) {
            this.index = index;
            this.size = size;
            this.fileId = fileId;
        }

        @Override
        public String toString() {
            String value = this.fileId == null ? "." : fileId.toString();
            return value.repeat(this.size);
        }

        public long checkSum() {
            if (fileId == null) return 0;
            long checksum = 0;
            for (int i = this.index; i < this.index + this.size; i++) {
                checksum += i * Long.valueOf(this.fileId);
            }
            return checksum;
        }
    }

}
