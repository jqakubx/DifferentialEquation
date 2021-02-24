public class Function {
    double left;
    double mid;
    double right;
    double valueLeft;
    double valueRight;
    String type;
    double value;
    boolean isStart = false;
    public Function(double left, double mid, double right) {
        this.left = left;
        this.mid = mid;
        this.right = right;
        this.valueLeft = 1/(this.mid-this.left);
        this.valueRight = -1/(this.right - this.mid);
        this.type = "1";
    }

    public Function(Function function1, Function function2) {
        if(function1.isStart && function2.isStart)
            this.isStart = true;
        if(function1.left == function2.left && function1.right == function2.right) {
            this.valueLeft = function1.valueLeft * function1.valueLeft;
            this.valueRight = function1.valueRight * function1.valueRight;
            this.left = function1.left;
            this.right = function1.right;
            this.mid = function1.mid;
            this.type = "1";
        }
        else if(function1.right == function2.mid) {
            this.left = function1.mid;
            this.right = function2.mid;
            this.value = function1.valueRight * function2.valueLeft;
            this.type = "2";
        }
        else if(function1.left == function2.mid) {
            this.left = function2.mid;
            this.right = function1.mid;
            this.value = function1.valueLeft * function2.valueRight;
            this.type = "2";
        }
        else this.type = "3";
    }

    // used method - Gauss–Legendre quadrature
    public double countIntegralOfDerivative() {
        if(this.type == "1") {
            // two points of quadrature
            double point1 = -1 / Math.sqrt(3);
            double point2 = 1 / Math.sqrt(3);

            // two weights of quadrature
            double weight1 = 1;
            double weight2 = 1;
            double sum = 0;
            double left = this.left;
            double right = this.mid;
            double val1 = this.getValue((right - left) / 2 * point1 + (right + left) / 2);
            double val2 = this.getValue((right - left) / 2 * point2 + (right + left) / 2);
            sum += (right - left) / 2 * (weight1 * val1 + weight2 * val2);
            left = this.mid;
            right = this.right;
            val1 = this.getValue((right - left) / 2 * point1 + (right + left) / 2);
            val2 = this.getValue((right - left) / 2 * point2 + (right + left) / 2);
            if(this.isStart) {
                sum = 0.0;
            }
            sum += (right - left) / 2 * (weight1 * val1 + weight2 * val2);
            return sum;
        }
        if(this.type == "2"){
            double point1 = -1 / Math.sqrt(3);
            double point2 = 1 / Math.sqrt(3);
            double weight1 = 1;
            double weight2 = 1;
            double sum = 0;
            double left = this.left;
            double right = this.right;
            double val1 = this.getValue2((right - left) / 2 * point1 + (right + left) / 2);
            double val2 = this.getValue2((right - left) / 2 * point2 + (right + left) / 2);
            sum += (right - left) / 2 * (weight1 * val1 + weight2 * val2);
            return sum;
        }
        return 0;
    }

    // function to count value for integral
    public double getValue(double x) {
        if(x < this.left || x > this.right)
            return 0;
        if(x <= this.mid) return this.valueLeft;
        return this.valueRight;
    }

    // function to count value for integral
    public double getValue2(double x) {
        if(x < this.left || x > this.right)
            return 0;
        return this.value;
    }

    // return value of function in a point x
    public double getValNormal(double x) {
        if(x < this.left || x > this.right)
            return 0;
        if(x <= this.mid) return (x-this.left)/(this.mid-this.left);
        return (this.right-x)/(this.right-this.mid);
    }

}
