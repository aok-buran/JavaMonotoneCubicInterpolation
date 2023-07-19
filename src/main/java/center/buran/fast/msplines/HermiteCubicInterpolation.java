package center.buran.fast.msplines;

import center.buran.fast.msplines.base.CubicHermiteSpline;
import center.buran.fast.msplines.base.CubicInterpolation;


/**
 * Класс кубической интерполяции Эрмита, скорости
 * в опорных точках рассчитываются методом трёхточечной разности
 */
public class HermiteCubicInterpolation extends CubicInterpolation {

    /**
     * Конструктор
     *
     * @param x_ptr - указатель на x - координаты
     * @param y_ptr - указатель на y - координаты
     * @param size  - количество опорных точек траектории
     */
    public HermiteCubicInterpolation(double[] x_ptr, double[] y_ptr, int size) {
        double[] dy = new double[size];
        calculateDY(dy, x_ptr, y_ptr, size);
        spline = new CubicHermiteSpline(x_ptr, y_ptr, dy, size);
    }

    /**
     * Вычислить первые производные в опорных точках методом трёхточечной разности
     *
     * @param dy    - вектор, в который нужно записать первые производные
     * @param x_ptr - указатель на x - координаты
     * @param y_ptr - указатель на y - координаты
     * @param size  - количество опорных точек траектории
     */
    private static void calculateDY(double[] dy, double[] x_ptr, double[] y_ptr, int size) {
        // рассчитываем dy методом трёхточечной разности
        double[] tangents = new double[size];

        for (int i = 0; i < size - 1; ++i) tangents[i] = (y_ptr[i + 1] - y_ptr[i]) / (x_ptr[i + 1] - x_ptr[i]);
        for (int i = 1; i < size - 1; ++i) dy[i] = (tangents[i - 1] + tangents[i]) / 2;

        dy[0] = 0;
        dy[size - 1] = 0;
    }

}

