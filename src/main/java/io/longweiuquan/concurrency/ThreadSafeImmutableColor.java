package io.longweiuquan.concurrency;

/**
 * Thread safe implementation of RGB Color using the immutable approach.
 *
 */
public class ThreadSafeImmutableColor implements Color {

	private final int r;
    private final int g;
    private final int b;
    
    public ThreadSafeImmutableColor(int r, int g, int b) {
        checkRGBVals(r, g, b);
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public int[] getColor() {
        int[] retVal = new int[3];
        retVal[0] = r;
        retVal[1] = g;
        retVal[2] = b;
        return retVal;
    }
    
    public ThreadSafeImmutableColor invert() {
        return new ThreadSafeImmutableColor(255 - r, 255 - g, 255 - b);
    }
    
    private static void checkRGBVals(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
    }
}
