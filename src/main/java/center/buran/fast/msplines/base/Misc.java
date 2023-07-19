package center.buran.fast.msplines.base;

import java.util.concurrent.ThreadLocalRandom;

public class Misc {

    /**
     * Получить случайное вещественное число из заданного диапазона
     *
     * @param lower_bound - нижняя граница
     * @param upper_bound - верхняя граница
     * @return случайное вещественное число
     */
    public static double randDouble(double lower_bound, double upper_bound) {
        // Using random function to
        // get random double value
        return lower_bound + (upper_bound - lower_bound) * ThreadLocalRandom.current().nextDouble();
    }

    /**
     * Получить случайное целое число из заданного диапазона
     *
     * @param lower_bound - нижняя граница
     * @param upper_bound - верхняя граница
     * @return случайное целое число
     */
    public static int randInt(int lower_bound, int upper_bound) {
        return lower_bound + (ThreadLocalRandom.current().nextInt(upper_bound - lower_bound));
    }


}
