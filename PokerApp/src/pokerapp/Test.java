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
    
    public boolean runTest(int test_num, int test) {
        switch(test_num) {
            case 0:
                return test0(test);
//            case 1:
//                test1();
//                break;
//            case 2:
//                test2();
//                break;
        }
        
        return false;
    }
    
    // Check pairs
    private boolean test0(int test) {
        
        switch (test) {
            case 0:
                {
                    int n = 3;
                    int[][] p = {{0, 13}, {3, 13}, {3, 5}, {0, 2}, {2, 8}};
                    int[][][] c = {{{2, 13}, {1, 13}}, {{2, 11}, {1, 11}}, {{3, 8}, {3, 2}}};
                    num = n;
                    pot = p;
                    cards = c;
                    break;
                }
            case 1:
                {
                    // Pair
                    int n = 3;
                    int[][] p = {{2, 13}, {0, 6}, {3, 10}, {1, 7}, {2, 14}};
                    int[][][] c = {{{1, 13}, {2, 11}}, {{3, 11}, {0, 13}}, {{3, 5}, {0, 5}}};
                    num = n;
                    pot = p;
                    cards = c;
                    break;
                }
            case 2:
                {
                    // Two Pair
                    int n = 4;
                    int[][] p = {{3, 13}, {0, 11}, {3, 6}, {0, 6}, {0, 14}};
                    int[][][] c = {{{2, 13}, {1, 11}}, {{0, 5}, {1, 5}}, {{3, 14}, {1, 10}}, {{1, 9}, {2, 14}}};
                    num = n;
                    pot = p;
                    cards = c;
                    break;
                }
            default:
                return false;
        }
        
        return true;
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
