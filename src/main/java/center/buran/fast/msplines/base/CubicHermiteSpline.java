package center.buran.fast.msplines.base;

import lombok.Getter;

/**
 * Класс кубических сплайнов
 */
public class CubicHermiteSpline {
    /**
     * координаты интерполируемых точек по оси X
     */
    @Getter
    private final double[] xVals;
    /**
     * интерполируемые значения
     */
    @Getter
    private final double[] yVals;
    /**
     * интерполируемые первые производные
     */
    @Getter
    private final double[] dyVals;
    /**
     * кол-во точек интерполяции
     */
    @Getter
    private final int size;

    /**
     * Конструктор
     *
     * @param xVals  - указатель на x - координаты
     * @param yVals  - указатель на y - координаты
     * @param dyVals - указатель на dy - координаты
     * @param size   - количество опорных точек траектории
     */
    public CubicHermiteSpline(double[] xVals, double[] yVals, double[] dyVals, int size) {
        this.xVals = xVals;
        this.yVals = yVals;
        this.dyVals = dyVals;
        this.size = size;
    }

    /**
     * Получить интерполированные значения
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    double getInterpolatedValue(double x) {
        int idx = binarySearch(x);
        if (idx == size - 1)
            return yVals[size - 1];
        double t = (x - xVals[idx]) / (xVals[idx + 1] - xVals[idx]);
        return interpFunc(t, idx);
    }

    /**
     * Получить интерполированные первые производные
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    double getInterpolatedSpeed(double x) {
        int idx = binarySearch(x);
        if (idx == size - 1)
            return yVals[size - 1];
        double t = (x - xVals[idx]) / (xVals[idx + 1] - xVals[idx]);
        return interpDFunc(t, idx);
    }

    /**
     * Получить интерполированные вторые производные
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    double getInterpolatedAcceleration(double x) {
        int idx = binarySearch(x);
        if (idx == size - 1)
            return yVals[size - 1];
        double t = (x - xVals[idx]) / (xVals[idx + 1] - xVals[idx]);
        return interpD2Func(t, idx);
    }

    /**
     * Получить интерполированные третьи производные
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    double getInterpolatedJerk(double x) {
        int idx = binarySearch(x);
        if (idx == size - 1)
            return yVals[size - 1];
        double t = (x - xVals[idx]) / (xVals[idx + 1] - xVals[idx]);
        return interpD3Func(t, idx);
    }

    /**
     * Бинарный поиск диапазона интер, в котором содержится
     * заданная координата x
     *
     * @param x - координата по оси обцисс
     * @return индекс левой границы интервала интерполирования
     */
    int binarySearch(double x) {
        assert ((xVals[0] <= x) && (x <= xVals[size - 1]));
        int idx_l = 0, idx_r = size - 1, idx = size / 2;
        while (true) {
            if (idx_r - idx_l == 1) {
                if (isXinBoundary(idx, x))
                    return idx;
                else
                    return (idx + 1);
            }
            if (isXinBoundary(idx, x))
                return idx;
            else if (xVals[idx + 1] <= x) {
                idx_l = idx;
                idx = (idx_r - idx_l) / 2 + idx_l;
            } else {
                idx_r = idx;
                idx = (idx_r - idx_l) / 2 + idx_l;
            }
        }
    }

    /**
     * Функция интерполирования
     *
     * @param t   - время
     * @param idx - индекс левой границы диапазона интерполирования
     * @return интерполированное значение, соответствующее времени t
     */
    double interpFunc(double t, int idx) {
        return (2 * Math.pow(t, 3) - 3 * Math.pow(t, 2) + 1) * yVals[idx] +
                (Math.pow(t, 3) - 2 * Math.pow(t, 2) + t) * (xVals[idx + 1] - xVals[idx]) * dyVals[idx] +
                (-2 * Math.pow(t, 3) + 3 * Math.pow(t, 2)) * yVals[idx + 1] +
                (Math.pow(t, 3) - Math.pow(t, 2)) * (xVals[idx + 1] - xVals[idx]) * dyVals[idx + 1];
    }

    /**
     * Функция интерполирования
     *
     * @param t   - время
     * @param idx - индекс левой границы диапазона интерполирования
     * @return интерполированное значение, соответствующее времени t
     */
    double interpDFunc(double t, int idx) {
        return (6 * Math.pow(t, 2) - 6 * t) * yVals[idx] +
                (3 * Math.pow(t, 2) - 4 * t + 1) * (xVals[idx + 1] - xVals[idx]) * dyVals[idx] +
                (-6 * Math.pow(t, 2) + 6 * t) * yVals[idx + 1] +
                (3 * Math.pow(t, 2) - 2 * t) * (xVals[idx + 1] - xVals[idx]) * dyVals[idx + 1];
    }

    /**
     * Функция интерполирования
     *
     * @param t   - время
     * @param idx - индекс левой границы диапазона интерполирования
     * @return интерполированное значение, соответствующее времени t
     */
    double interpD2Func(double t, int idx) {
        return (12 * t - 6) * yVals[idx] +
                (6 * t - 4) * (xVals[idx + 1] - xVals[idx]) * dyVals[idx] +
                (-12 * t + 6) * yVals[idx + 1] +
                (6 * t - 2) * (xVals[idx + 1] - xVals[idx]) * dyVals[idx + 1];
    }

    /**
     * Функция интерполирования
     *
     * @param t   - время
     * @param idx - индекс левой границы диапазона интерполирования
     * @return интерполированное значение, соответствующее времени t
     */
    double interpD3Func(double t, int idx) {
        return 12 * yVals[idx] +
                6 * (xVals[idx + 1] - xVals[idx]) * dyVals[idx] +
                -12 * yVals[idx + 1] +
                6 * (xVals[idx + 1] - xVals[idx]) * dyVals[idx + 1];
    }

    /**
     * Проверка, что x попадает в диапазон интерполирования
     *
     * @param idx - индекс левой границы интерполирования
     * @param x   - значение, которое нужно проверить
     * @return флаг, попадает ли x в диапазон интерполирования
     */
    boolean isXinBoundary(int idx, double x) {
        return (this.xVals[idx] <= x) && (x < this.xVals[idx + 1]);
    }


};