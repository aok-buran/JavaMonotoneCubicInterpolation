import center.buran.fast.msplines.MonotoneCubicInterpolation;
import center.buran.fast.msplines.base.CubicInterpolation;
import center.buran.fast.msplines.base.Misc;
import org.junit.jupiter.api.Test;

public class InterpolationTest {

    void testInterpolation(CubicInterpolation ci, double[] x, double[] y) {

        // проверяем интерполяцию опорных точек
        for (int i = 0; i < x.length; i++) {
            assert (Math.abs(ci.getInterpolatedValue(x[i]) - y[i]) < 0.0001);
        }

        // проверяем монотонность в промежуточных значениях
        for (int i = 0; i < x.length - 1; i++) {
            //    std::cout << x[i] << " " << x[i + 1] << std::endl;
            for (double j = x[i]; j < x[i + 1]; j += 0.001) {

                double d1 = ci.getInterpolatedValue(j) - y[i];
                if (Math.abs(d1) < 0.01)
                    d1 = 0;

                double d2 = ci.getInterpolatedValue(j) - y[i + 1];
                if (Math.abs(d2) < 0.01)
                    d2 = 0;

                // проверяем, что интерполированные промежуточные значения
                // лежат между y_i и _y{i-1}, это будет выполнено тогда
                // и только тогда, когда разности будут иметь разный знак
                if (d1 * d2 > 0) {
                    System.out.println(i + " not monotone [" + y[i] + " " + ci.getInterpolatedValue(j) +
                            " " + y[i + 1] + "]\n" +
                            "[" + x[i] + " " + j + " " + x[i + 1] + "]\n");
                    System.out.println(
                            (ci.getInterpolatedValue(j) - y[i]) + " " +
                                    (ci.getInterpolatedValue(j) - y[i + 1]) + "\n"
                    );

                    for (double v : x)
                        System.out.print(v + " ");

                    System.out.println();
                    for (double v : y)
                        System.out.print(v + " ");

                    System.out.println();

                    assert (false);
                }
            }
        }
    }

    @Test
    void test1() {

        // 13 not monotone [0.15751 0.167726 0.157695]
        // [3.94554 4.01354 4.24789]
        double[] x = {0, 0.302899, 0.606508, 0.908134, 1.21395, 1.51935, 1.81996, 2.12524, 2.42706, 2.73156, 3.03559,
                3.33916, 3.63975, 3.94554, 4.24789};
        double[] y = {0.089955, 0.00997, 0.139505, 0.067155, 0.020865, 0.02872, 0.03142, 0.00528, 0.1077, 0.05238,
                0.000255, 0.02873, 0.00501, 0.15751, 0.157695};

        MonotoneCubicInterpolation tester = new MonotoneCubicInterpolation(x, y, x.length);
        testInterpolation(tester, x, y);

    }

    @Test
    void test2() {

        // 13 not monotone [0.15751 0.167726 0.157695]
        // [3.94554 4.01354 4.24789]
        double[] x = {0, 0.300501, 0.603501, 0.904478, 1.20483, 1.50886, 1.81487, 2.1194, 2.4232, 2.72523, 3.02532,
                3.32891, 3.63402, 3.93523, 4.23689, 4.54277};
        double[] y = {0.11389, 0.13829, 0.09084, 0.10592, 0.105005, 0.162845, 0.16277, 0.018305, 0.12811, 0.13143,
                0.11418, 0.083345, 0.12726, 0.001105, 0.14365, 0.14388};

        MonotoneCubicInterpolation tester = new MonotoneCubicInterpolation(x, y, x.length);
        testInterpolation(tester, x, y);

    }

    @Test
    void test3() {

        // 13 not monotone [0.15751 0.167726 0.157695]
        // [3.94554 4.01354 4.24789]
        double[] x = {0, 0.303555, 0.608064, 0.910488, 1.21585, 1.51868, 1.82434, 2.12519, 2.42842, 2.73416, 3.03837,
                3.34, 3.64154, 3.94706, 4.2535, 4.55791, 4.86059, 5.16446, 5.46927};
        double[] y = {0.120465, 0.04902, 0.079875, 0.04896, 0.05328, 0.02693, 0.14311, 0.050515, 0.081645, 0.148125,
                0.11132, 0.08838, 0.02314, 0.10573, 0.15767, 0.11982, 0.15482, 0.157135, 0.00408};

        MonotoneCubicInterpolation tester = new MonotoneCubicInterpolation(x, y, x.length);
        testInterpolation(tester, x, y);

    }


    @Test
    void test4() {

        // 13 not monotone [0.15751 0.167726 0.157695]
        // [3.94554 4.01354 4.24789]
        double[] x = {0, 0.304698, 0.60859, 0.915105, 1.21965, 1.52405, 1.83045, 2.13267, 2.43548, 2.73558, 3.03785,
                3.34159, 3.64426, 3.94823, 4.25365};
        double[] y = {0.141935, 0.05861, 0.130945, 0.00348, 0.121755, 0.024305, 0.085175, 0.05605, 0.108705, 0.0602,
                0.11156, 0.06305, 0.066095, 0.059835, 0.159445};
        MonotoneCubicInterpolation tester = new MonotoneCubicInterpolation(x, y, x.length);
        testInterpolation(tester, x, y);

    }

    @Test
    void test5() {

        for (int testNum = 0; testNum < 10000; testNum++) {
            int dataSize = Misc.randInt(10, 20);

            System.out.println("test " + testNum + " " + dataSize);

            double[] x = new double[dataSize];
            double[] y = new double[dataSize];

            x[0] = 0;
            y[0] = Misc.randDouble(0, 5);
            for (int i = 1; i < dataSize; i++) {
                y[i] = Misc.randDouble(0, 5);
                x[i] = Misc.randDouble(0.3, 0.5) + x[i - 1];
            }

            MonotoneCubicInterpolation tester = new MonotoneCubicInterpolation(x, y, x.length);
            testInterpolation(tester, x, y);

        }
    }
}
