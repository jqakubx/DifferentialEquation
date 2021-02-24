import org.ejml.simple.SimpleMatrix;

public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // number of intervals
        int n = 1000;
        double[] intervals = linespace(n);

        // create array of functions
        Function[] functions = new Function[n + 1];
        functions[0] = new Function(-1, 0, intervals[1]);
        functions[0].isStart = true;
        functions[n] = new Function(intervals[n - 1], 2, 3);
        for (int i = 1; i < n; i++) {
            functions[i] = new Function(intervals[i - 1], intervals[i], intervals[i + 1]);
        }

        // create matrix with value of integrals
        double[][] matrixB = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(Math.abs(i-j) >= 2)
                    matrixB[i][j] = 0;
                else {
                    Function newFunc = new Function(functions[i], functions[j]);
                    matrixB[i][j] = newFunc.countIntegralOfDerivative() - functions[i].getValNormal(0) * functions[j].getValNormal(0);
                }
            }
        }
        double[] matrixL = new double[n];
        for (int i = 0; i < n; i++) {
            matrixL[i] = -20 * functions[i].getValNormal(0);
        }

        SimpleMatrix A = new SimpleMatrix(n, n);
        SimpleMatrix B = new SimpleMatrix(n, 1);
        for (int i = 0; i < n; i++) {
            A.setRow(i, 0, matrixB[i]);
        }
        B.setColumn(0, 0, matrixL);
        SimpleMatrix C = A.solve(B);
        double[] eTable = new double[n];
        for (int i = 0; i < n; i++) {
            eTable[i] = C.get(i, 0);
            //System.out.println(eTable[i]);
        }
        double part = 2.0/50;
        double[] xToPlot = new double[50];
        double[] yToPlot = new double[50];
        int j = 0;
        for(double i = 0.0; i < 2.0; i+= part, j++) {
            xToPlot[j] = i;
            yToPlot[j] = countY(eTable, functions, i);
        }
        for(int i = 0; i < 50; i++) {
            System.out.println(xToPlot[i]);
        }
        System.out.println("*****");
        for(int i = 0; i < 50; i++) {
            System.out.println(yToPlot[i]);
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish-start;
        System.out.println(timeElapsed);
    }

    public static double countY(double[] eTable, Function[] functions, double x) {
        double sum = 0.0;
        for(int i = 0; i < eTable.length; i++) {
            sum += eTable[i] * functions[i].getValNormal(x);
        }
        return sum;
    }

    public static double[] linespace(int n) {
        double[] linespaces = new double[n+1];

        // domain of function
        linespaces[0] = 0;
        linespaces[n] = 2;

        double partition = 2.0 / n;
        for(int i = 1; i < n; i++) {
            linespaces[i] = linespaces[i-1] + partition;
        }
        return linespaces;
    }
}
