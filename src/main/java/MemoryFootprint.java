import java.util.ArrayList;
import java.util.List;

/**
 * Created by 430 on 13.01.2018.
 */
public class MemoryFootprint {

    private static long bytesToMegabytes(long bytes) {
        final long MEGABYTE = 1024L * 1024L;
        return bytes / MEGABYTE;
    }

    public static long countMemoryFootprint() {

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();

        // Calculate the used memory
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static long getResultInMegabytes(long memory) {
        return bytesToMegabytes(memory);
    }
}
