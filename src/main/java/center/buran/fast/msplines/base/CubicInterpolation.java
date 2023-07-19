package center.buran.fast.msplines.base;

import lombok.Getter;

/**
 * Базовый класс для всех интерполяций
 */
public class CubicInterpolation {
    /**
     * Сплайн, с которым работает интерполяция
     */
    @Getter
    protected CubicHermiteSpline spline;

    /**
     * Получить интерполированные значения
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    public double getInterpolatedValue(double x) {
        return spline.getInterpolatedValue(x);
    }

    /**
     * Получить интерполированные первые производные
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    public double getInterpolatedSpeed(double x) {
        return spline.getInterpolatedSpeed(x);
    }

    /**
     * Получить интерполированные вторые производные
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    public double getInterpolatedAcceleration(double x) {
        return spline.getInterpolatedAcceleration(x);
    }

    /**
     * Получить интерполированные третьи производные
     *
     * @param x - координата
     * @return значение интерполяционного полинома в заданной точке
     */
    public double getInterpolatedJerk(double x) {
        return spline.getInterpolatedJerk(x);
    }

}