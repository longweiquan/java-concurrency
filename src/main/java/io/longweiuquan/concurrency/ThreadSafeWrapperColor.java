package io.longweiuquan.concurrency;

/**
 * Thread safe implementation of RGB Color using the wrapper approach.
 *
 */
public class ThreadSafeWrapperColor implements Color {

	private NotThreadSafeColor color;
    
	public ThreadSafeWrapperColor(int r, int g, int b) {
        color = new NotThreadSafeColor(r, g, b);
    }
	
    public synchronized int[] getColor() {
        return color.getColor();
    }
    
    public synchronized Color invert() {
        return color.invert();
    }
    
}
