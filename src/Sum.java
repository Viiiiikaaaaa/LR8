import java.math.BigInteger;
import java.util.stream.LongStream;

public class Sum {
    public static void main(String[] args) {
        BigInteger start = BigInteger.ZERO;
        BigInteger end = new BigInteger("10000000000");

        calculateSum(start, end, 1);
        calculateSum(start, end, 2);
        calculateSum(start, end, 4);
        calculateSum(start, end, 8);

    }

    private static void calculateSum(BigInteger start, BigInteger end, int numThreads) {
        long startTime = System.currentTimeMillis();
        BigInteger sum;

        if (numThreads == 1){
            sum = singleThreadSummation(start, end);
        }
        else{
            sum = multiThreadSummation(start, end, numThreads);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Сумма чисел с количеством поток - " + numThreads + ": " + sum + ". Время выполнения задачи: " + (endTime - startTime) + "мс.");
    }

    private static BigInteger singleThreadSummation(BigInteger start, BigInteger end) {
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger i = start; i.compareTo(end) <= 0; i = i.add(BigInteger.ONE)) {
            sum = sum.add(i);
        }
        return sum;
    }

    private static BigInteger multiThreadSummation(BigInteger start, BigInteger end, int numThreads) {
        return LongStream.rangeClosed(start.longValue(), end.longValue())
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }
}
