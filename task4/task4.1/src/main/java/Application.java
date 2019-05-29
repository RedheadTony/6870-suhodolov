import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Application {

    public static void main(String[] args){
        int threadsCount = 10;
        int step = 10_000_000 / threadsCount;

        ExecutorService threadPool = Executors.newFixedThreadPool(threadsCount);

        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 1; i < 10_000_000; i += step) {
            futures.add(CompletableFuture.supplyAsync(new Task(i, i + step - 1), threadPool));
        }

        long result = 0;
        for (Future<Long> future : futures) {
            try {
                result += future.get();
            } catch (InterruptedException e) {
                System.out.println("Occupied thread was interrupted");
            } catch (ExecutionException e) {
                System.out.println("Task aborted. Can't retrieve the result.");
            }
        }

        System.out.println(result);

        threadPool.shutdown();
    }
}
