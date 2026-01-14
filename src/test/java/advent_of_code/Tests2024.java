//package advent_of_code;
//
//import advent_of_code.y2024.day1.HistorianHysteria1;
//import advent_of_code.y2024.day1.HistorianHysteria2;
//import advent_of_code.y2024.day10.HoofIt1;
//import advent_of_code.y2024.day10.HoofIt2;
//import advent_of_code.y2024.day11.PlutonianPebbles1;
//import advent_of_code.y2024.day11.PlutonianPebbles2;
//import advent_of_code.y2024.day12.GardenGroups1;
//import advent_of_code.y2024.day12.GardenGroups2;
//import advent_of_code.y2024.day13.ClawContraption1;
//import advent_of_code.y2024.day13.ClawContraption2;
//import advent_of_code.y2024.day14.RestroomRedoubt1;
//import advent_of_code.y2024.day14.RestroomRedoubt2;
//import advent_of_code.y2024.day15.WarehouseWoes1;
//import advent_of_code.y2024.day15.WarehouseWoes2;
//import advent_of_code.y2024.day16.ReindeerMaze1;
//import advent_of_code.y2024.day16.ReindeerMaze2;
//import advent_of_code.y2024.day17.part1.ChronospatialComputer1;
//import advent_of_code.y2024.day17.part2.ChronospatialComputer2;
//import advent_of_code.y2024.day18.RamRun1;
//import advent_of_code.y2024.day18.RamRun2;
//import advent_of_code.y2024.day19.LinenLayout1;
//import advent_of_code.y2024.day19.LinenLayout2;
//import advent_of_code.y2024.day2.RedNosedReports1;
//import advent_of_code.y2024.day2.RedNosedReports2;
//import advent_of_code.y2024.day20.RaceCondition1;
//import advent_of_code.y2024.day20.RaceCondition2;
//import advent_of_code.y2024.day21.KeypadConundrum1;
//import advent_of_code.y2024.day21.KeypadConundrum2;
//import advent_of_code.y2024.day22.MonkeyMarket1;
//import advent_of_code.y2024.day22.MonkeyMarket2;
//import advent_of_code.y2024.day23.LanParty1;
//import advent_of_code.y2024.day23.LanParty2;
//import advent_of_code.y2024.day24.CrossedWires1;
//import advent_of_code.y2024.day24.CrossedWires2;
//import advent_of_code.y2024.day25.CodeChronicle;
//import advent_of_code.y2024.day3.MullItOver1;
//import advent_of_code.y2024.day3.MullItOver2;
//import advent_of_code.y2024.day4.CeresSearch1;
//import advent_of_code.y2024.day4.CeresSearch2;
//import advent_of_code.y2024.day5.PrintQueue1;
//import advent_of_code.y2024.day5.PrintQueue2;
//import advent_of_code.y2024.day6.GuardGallivant1;
//import advent_of_code.y2024.day6.GuardGallivant2;
//import advent_of_code.y2024.day7.BridgeRepair1;
//import advent_of_code.y2024.day7.BridgeRepair2;
//import advent_of_code.y2024.day8.ResonantCollinearity1;
//import advent_of_code.y2024.day8.ResonantCollinearity2;
//import advent_of_code.y2024.day9.DiskFragmenter1;
//import advent_of_code.y2024.day9.DiskFragmenter2;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.parallel.Execution;
//import org.junit.jupiter.api.parallel.ExecutionMode;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Execution(ExecutionMode.CONCURRENT)
//public class Tests2024 {
//
//    @Test
//    public void day1() {
//        assertEquals(2192892, new HistorianHysteria1().start());
//        assertEquals(22962826, new HistorianHysteria2().start());
//    }
//    @Test
//    public void day2() {
//        assertEquals(242L, new RedNosedReports1().start());
//        assertEquals(311L, new RedNosedReports2().start());
//    }
//    @Test
//    public void day3() {
//        assertEquals(179571322L, new MullItOver1().start());
//        assertEquals(103811193L, new MullItOver2().start());
//    }
//    @Test
//    public void day4() {
//        assertEquals(2557, new CeresSearch1().start());
//        assertEquals(1854, new CeresSearch2().start());
//    }
//    @Test
//    public void day5() {
//        assertEquals(6051, new PrintQueue1().start());
//        assertEquals(5093, new PrintQueue2().start());
//    }
//    @Test
//    public void day6() {
//        assertEquals(4883, new GuardGallivant1().start());
//        assertEquals(1655, new GuardGallivant2().start());
//    }
//    @Test
//    public void day7() {
//        assertEquals(28730327770375L, new BridgeRepair1().start());
//        assertEquals(424977609625985L, new BridgeRepair2().start());
//    }
//    @Test
//    public void day8() {
//        assertEquals(301, new ResonantCollinearity1().start());
//        assertEquals(1019, new ResonantCollinearity2().start());
//    }
//    @Test
//    public void day9() {
//        assertEquals(6200294120911L, new DiskFragmenter1().start());
//        assertEquals(6227018762750L, new DiskFragmenter2().start());
//    }
//    @Test
//    public void day10() {
//        assertEquals(552, new HoofIt1().start());
//        assertEquals(1225, new HoofIt2().start());
//    }
//    @Test
//    public void day11() {
//        assertEquals(199946, new PlutonianPebbles1().start());
//        assertEquals(237994815702032L, new PlutonianPebbles2().start());
//    }
//    @Test
//    public void day12() {
//        assertEquals(1424006, new GardenGroups1().start());
//        assertEquals(858684, new GardenGroups2().start());
//    }
//    @Test
//    public void day13() {
//        assertEquals(30973, new ClawContraption1().start());
//        assertEquals(95688837203288L, new ClawContraption2().start());
//    }
//    @Test
//    public void day14() {
//        assertEquals(222062148, new RestroomRedoubt1().start());
//        assertEquals(7520, new RestroomRedoubt2().start());
//    }
//    @Test
//    public void day15() {
//        assertEquals(1430439, new WarehouseWoes1().start());
//        assertEquals(1458740, new WarehouseWoes2().start());
//    }
//    @Test
//    public void day16() {
//        assertEquals(147628L, new ReindeerMaze1().start());
//        assertEquals(670, new ReindeerMaze2().start());
//    }
//    @Test
//    public void day17() {
//        assertEquals("4,1,5,3,1,5,3,5,7", new ChronospatialComputer1().start());
//        assertEquals(164542125272765L, new ChronospatialComputer2().start());
//    }
//    @Test
//    public void day18() {
//        assertEquals(284L, new RamRun1().start());
//        assertEquals("51,50", new RamRun2().start());
//    }
//    @Test
//    public void day19() {
//        assertEquals(315L, new LinenLayout1().start());
//        assertEquals(625108891232249L, new LinenLayout2().start());
//    }
//    @Test
//    public void day20() {
//        assertEquals(1372, new RaceCondition1().start());
//        assertEquals(979014, new RaceCondition2().start());
//    }
//    @Test
//    public void day21() {
//        assertEquals(107934L, new KeypadConundrum1().start());
//        assertEquals(130470079151124L, new KeypadConundrum2().start());
//    }
//    @Test
//    public void day22() {
//        assertEquals(17005483322L, new MonkeyMarket1().start());
//        assertEquals(1910L, new MonkeyMarket2().start());
//    }
//    @Test
//    public void day23() {
//        assertEquals(1119L, new LanParty1().start());
//        assertEquals("av,fr,gj,hk,ii,je,jo,lq,ny,qd,uq,wq,xc", new LanParty2().start());
//    }
//    @Test
//    public void day24() {
//        assertEquals(52038112429798L, new CrossedWires1().start());
//        assertEquals("cph,jqn,kwb,qkf,tgr,z12,z16,z24", new CrossedWires2().start());
//    }
//    @Test
//    public void day25() {
//        assertEquals(3269, new CodeChronicle().start());
//    }
//}
