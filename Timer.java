// A significant portion of this code was taken from or inspired by
// Stopwatch.java
public class Timer {
    Stopwatch timer1; // stopwatch used for this event
    double n; // number of seconds initially on timer

    // create a new timer given the initial time
    public Timer(double n) {
        this.timer1 = new Stopwatch();
        this.n = n;
    }

    // return whether the current time on the timer is an integer value
    // given the time limit
    public static boolean isIntValue(double currentTime, double timeLimit) {
        for (double i = timeLimit; i > -1; i--) {
            if (currentTime == i) return true;
        }
        return false;
    }

    // return the time remaining on timer as a String for output
    public String timeRemaining() {
        StringBuilder sb = new StringBuilder();
        return sb.append((int) (this.n - timer1.elapsedTime())).toString();
    }

    // return the time remaining on timer as a double
    public double timeRemainingDouble() {
        return this.n - timer1.elapsedTime();
    }

    // create a new timer with time limit of 30.0 seconds
    // while timer is running,
    // check if the number of seconds is an integer
    // and print it to stdout if so
    public static void main(String[] args) {
        double timeLimit = 30.0;
        Timer timer1 = new Timer(timeLimit);
        while (true) {
            if (isIntValue(timer1.timeRemainingDouble(), timeLimit)) {
                StdOut.println(timer1.timeRemaining());
            }
            if (timer1.timeRemainingDouble() < 0) {
                break;
            }
        }

    }

}
