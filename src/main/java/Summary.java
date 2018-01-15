import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 430 on 14.01.2018.
 */
public class Summary {
    private long startTime;
    private long endTime;
    private long totalTime;
    private List<Offer> offers;

    public void setCurrentTime(String flag, long time) {
        if (flag.equals("start")) {
            startTime = time;
        }
        if (flag.equals("end")) {
            endTime = time;
        }

        if (endTime != 0) {
            totalTime = endTime - startTime;
            printSummary();
        }
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    private void printSummary() {
        // print summary results
        // print run time
        printResults("Run-time", getRunTime());

        // print memory footprint in megabytes
        printResults("Memory Footprint", String.valueOf(getMemoryFootprint() + " MB"));

        // print amount of products
        printResults("Amount of products", String.valueOf(offers.size()));
    }

    public long getMemoryFootprint() {
        return MemoryFootprint.getResultInMegabytes(MemoryFootprint.countMemoryFootprint());
    }

    // count run-time
    private String getRunTime() {
        DateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(new Date(totalTime));
    }


    private void printResults(String flag, String data) {
        System.out.format("\n%s: %s", flag, data);
    }
}
