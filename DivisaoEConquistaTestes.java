import java.util.Random;

public class DivisaoEConquistaTestes {

    static long mergeIterations = 0;

    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            mergeIterations++;
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            mergeIterations++;
            if (L[i] <= R[j]) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    static long maxVal1Iterations = 0;

    public static long maxVal1(long[] A, int n) {
        long max = A[0];
        for (int i = 1; i < n; i++) {
            maxVal1Iterations++;
            if (A[i] > max) max = A[i];
        }
        return max;
    }

    static long maxVal2Iterations = 0;

    public static long max(long a, long b) {
        maxVal2Iterations++;
        return (a > b) ? a : b;
    }

    public static long maxVal2(long A[], int init, int end) {
        maxVal2Iterations++;
        if (end - init <= 1)
            return max(A[init], A[end]);
        else {
            int m = (init + end) / 2;
            long v1 = maxVal2(A, init, m);
            long v2 = maxVal2(A, m + 1, end);
            return max(v1, v2);
        }
    }

    static long multiplyIterations = 0;

    public static long multiply(long x, long y, int n) {
        multiplyIterations++;
        if (n == 1)
            return x * y;
        else {
            int m = (int) Math.ceil(n / 2.0);

            long a = x >> m;
            long b = x & ((1L << m) - 1);
            long c = y >> m;
            long d = y & ((1L << m) - 1);

            long e = multiply(a, c, m);
            long f = multiply(b, d, m);
            long g = multiply(b, c, m);
            long h = multiply(a, d, m);

            return (e << (2 * m)) + ((g + h) << m) + f;
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int runs = 10;

        // MERGE SORT
        int[] sizes = {32, 2048, 1048576};
        System.out.println("==== MERGE SORT ====");
        for (int size : sizes) {
            long totalTime = 0;
            long totalIterations = 0;
            for (int r = 0; r < runs; r++) {
                int[] arr = new int[size];
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt();

                mergeIterations = 0;
                long start = System.nanoTime();
                mergeSort(arr, 0, arr.length - 1);
                long end = System.nanoTime();

                totalTime += (end - start);
                totalIterations += mergeIterations;
            }
            System.out.println("Tamanho: " + size);
            System.out.println("Média Iterações: " + (totalIterations / runs));
            System.out.println("Tempo médio: " + (totalTime / runs) / 1_000_000.0 + " ms\n");
        }

        System.out.println("==== MAX VALUE (Linear) ====");
        for (int size : sizes) {
            long totalTime = 0;
            long totalIterations = 0;
            for (int r = 0; r < runs; r++) {
                long[] arr = new long[size];
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt();

                maxVal1Iterations = 0;
                long start = System.nanoTime();
                long max = maxVal1(arr, arr.length);
                long end = System.nanoTime();

                totalTime += (end - start);
                totalIterations += maxVal1Iterations;
            }
            System.out.println("Tamanho: " + size);
            System.out.println("Média Iterações: " + (totalIterations / runs));
            System.out.println("Tempo médio: " + (totalTime / runs) / 1_000_000.0 + " ms\n");
        }

        System.out.println("==== MAX VALUE (Divisão e Conquista) ====");
        for (int size : sizes) {
            long totalTime = 0;
            long totalIterations = 0;
            for (int r = 0; r < runs; r++) {
                long[] arr = new long[size];
                for (int i = 0; i < size; i++) arr[i] = rand.nextInt();

                maxVal2Iterations = 0;
                long start = System.nanoTime();
                long max = maxVal2(arr, 0, arr.length - 1);
                long end = System.nanoTime();

                totalTime += (end - start);
                totalIterations += maxVal2Iterations;
            }
            System.out.println("Tamanho: " + size);
            System.out.println("Média Iterações: " + (totalIterations / runs));
            System.out.println("Tempo médio: " + (totalTime / runs) / 1_000_000.0 + " ms\n");
        }

        int[] bitSizes = {4, 16, 64};
        System.out.println("==== MULTIPLICAÇÃO ====");
        for (int bits : bitSizes) {
            long totalTime = 0;
            long totalIterations = 0;
            for (int r = 0; r < runs; r++) {
                long maxVal = (1L << bits) - 1;
                long x = rand.nextLong() & maxVal;
                long y = rand.nextLong() & maxVal;

                multiplyIterations = 0;
                long start = System.nanoTime();
                long result = multiply(x, y, bits);
                long end = System.nanoTime();

                totalTime += (end - start);
                totalIterations += multiplyIterations;
            }
            System.out.println("Bits: " + bits);
            System.out.println("Média Iterações: " + (totalIterations / runs));
            System.out.println("Tempo médio: " + (totalTime / runs) / 1_000_000.0 + " ms\n");
        }
    }
}
