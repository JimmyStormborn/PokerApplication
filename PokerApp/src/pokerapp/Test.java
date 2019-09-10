package pokerapp;

/**
 *
 * @author kylar
 */
public class Test {
    
    public int num;
    public int[][] pot;
    public int[][][] cards;
    
    
    public Test() {}
    
    public void runTest(int test_num) {
        switch(test_num) {
            case 0:
                test0();
                break;
            case 1:
                test1();
                break;
            case 2:
                test2();
                break;
        }
    }
    
    // Check pairs
    private void test0() {
        int n = 3;
        int[][] p = {{0, 13}, {3, 13}, {3, 5}, {0, 2}, {2, 8}};
        int[][][] c = {{{2, 13}, {1, 13}}, {{2, 11}, {1, 11}}, {{3, 8}, {3, 2}}};
        
        num = n;
        pot = p;
        cards = c;
    }
    
    // Check flush
    private void test1() {
        int n = 3;
        int[][] p = {{1, 13}, {1, 5}, {1, 6}, {3, 10}, {2, 8}};
        int[][][] c = {{{3, 5}, {2, 14}}, {{1, 2}, {1, 11}}, {{3, 3}, {3, 12}}};
        
        num = n;
        pot = p;
        cards = c;
    }
    
    private void test2() {
        int n = 3;
        int[][] p = {{1, 13}, {1, 5}, {1, 6}, {3, 10}, {1, 8}};
        int[][][] c = {{{3, 5}, {2, 14}}, {{3, 2}, {1, 11}}, {{3, 3}, {3, 12}}};
        
        num = n;
        pot = p;
        cards = c;
    }
}
