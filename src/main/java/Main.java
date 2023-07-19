import center.buran.fast.msplines.HermiteCubicInterpolation;
import center.buran.fast.msplines.MonotoneCubicInterpolation;
import center.buran.fast.msplines.base.Misc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {

        // размер выборки данных
        int dataSize = 15;
        // аргументы функции
        double[] x = new double[dataSize];
        // значения функции
        double[] y = new double[dataSize];
        x[0] = 0;
        y[0] = Misc.randDouble(0, 5);
        for (int i = 1; i < dataSize; i++) {
            y[i] = Misc.randDouble(0, 3);
            x[i] = Misc.randDouble(0.3, 1.5) + x[i - 1];
        }

        // строим монотонный интерполятор
        MonotoneCubicInterpolation monotoneInterpolation = new MonotoneCubicInterpolation(x, y, x.length);

        // выводим в файл интерполированные значения функции с аргументами от нуля до последнего x
        // с равным шагом, количество значений задаётся в переменной nPoints
        // для каждого аргумента построчно выводятся сам аргумент, значение функции в точке
        // и первые три производных
        int nPoints = 100;
        try (PrintStream printStream = new PrintStream("data/cubic/test-monotone.out")) {
            for (int i = 0; i <= nPoints; ++i) {
                double xVal = x[x.length - 1] * i / nPoints;
                printStream.println(xVal + " "
                        + monotoneInterpolation.getInterpolatedValue(xVal) + " "
                        + monotoneInterpolation.getInterpolatedSpeed(xVal) + " "
                        + monotoneInterpolation.getInterpolatedAcceleration(xVal) + " "
                        + monotoneInterpolation.getInterpolatedJerk(xVal) + "\n"
                );
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }


        // во второй файл выводятся опорные точки интерполяции: x, f(x), f'(x)
        try (PrintStream printStream = new PrintStream("data/cubic/test-monotone.in")) {
            for (int i = 0; i < x.length; ++i)
                printStream.println(x[i] + " " + y[i] + " "
                        + monotoneInterpolation.getSpline().getDyVals()[i] + "\n"
                );
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        // строим обычный интерполятор
        HermiteCubicInterpolation hermitCubicInterpolation = new HermiteCubicInterpolation(x, y, x.length);

        // выводим в файл интерполированные значения функции с аргументами от нуля до последнего x
        // с равным шагом, количество значений задаётся в переменной nPoints
        // для каждого аргумента построчно выводятся сам аргумент, значение функции в точке
        // и первые три производных
        try (PrintStream printStream = new PrintStream("data/cubic/test-hermit.out")) {
            for (int i = 0; i <= nPoints; ++i) {
                double xVal = x[x.length - 1] * i / nPoints;
                printStream.println(xVal + " "
                        + hermitCubicInterpolation.getInterpolatedValue(xVal) + " "
                        + hermitCubicInterpolation.getInterpolatedSpeed(xVal) + " "
                        + hermitCubicInterpolation.getInterpolatedAcceleration(xVal) + " "
                        + hermitCubicInterpolation.getInterpolatedJerk(xVal) + "\n"
                );
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }


        // во второй файл выводятся опорные точки интерполяции: x, f(x), f'(x)
        try (PrintStream printStream = new PrintStream("data/cubic/test-hermit.in")) {
            for (int i = 0; i < x.length; ++i)
                printStream.println(x[i] + " " + y[i] + " "
                        + hermitCubicInterpolation.getSpline().getDyVals()[i] + "\n"
                );
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
