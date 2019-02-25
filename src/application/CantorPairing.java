package application;

public class CantorPairing
{
    public static void main(String[] args)
    {
        int i = 5, j = 10;
        int k = CantorPair(i, j);
        
        System.out.println(k);
        
        int[] l = Reverse(k);
        
        System.out.println("[0]: " + l[0] + "\n[1]: " + l[1]);
    }
    
    public static int CantorPair(int x, int y)
    {
        return ((x + y) * (x + y + 1)) / 2 + y;
    }
    
    public static int[] Reverse(int z)
    {
        int[] pair = new int[2];
        int t = (int)Math.floor((-1D + Math.sqrt(1d + 8 * z))/2d);
        int x = t * (t + 3) / 2 - z;
        int y = z - t * (t + 1) / 2;
        pair[0] = (int) x;
        pair[1] = (int) y;
        return pair;
    }
}
