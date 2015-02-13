package MorpheusAutoTesting.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A wait condition to check when jQuery processing stops
 * <p/>
 * Created by oleg.leontyev on 12.02.2015.
 */
public class WaitForScriptsCondition implements ExpectedCondition<Boolean> {

    private static final int DEFAULT_INITIAL_WAIT_SECONDS = 0;
    private static final int DEFAULT_WAIT_MILLISECONDS_PER_CYCLE = 500;
    private static final int DEFAULT_WAIT_CYCLES = 4;

    private static final String IS_JQUERY_STOPPED_SCRIPT = "return window.jQuery != undefined && jQuery.active == 0";

    private int initialTimeoutInSeconds;
    private int timeoutWithinCycle;
    private int maxWaitCycles;

    public WaitForScriptsCondition(int initialTimeoutInSeconds, int timeoutWithinCycle, int maxWaitCycles) {
        this.initialTimeoutInSeconds = initialTimeoutInSeconds;
        this.timeoutWithinCycle = timeoutWithinCycle;
        this.maxWaitCycles = maxWaitCycles;
    }

    public WaitForScriptsCondition(int initialTimeoutInSeconds) {
        this(initialTimeoutInSeconds, DEFAULT_WAIT_MILLISECONDS_PER_CYCLE, DEFAULT_WAIT_CYCLES);
    }

    public WaitForScriptsCondition() {
        this(DEFAULT_INITIAL_WAIT_SECONDS, DEFAULT_WAIT_MILLISECONDS_PER_CYCLE, DEFAULT_WAIT_CYCLES);
    }


    @Override
    public Boolean apply(WebDriver input) {

        boolean completed;

        // initial wait
        if (initialTimeoutInSeconds > 0) {
            new WebDriverWait(input, initialTimeoutInSeconds)
                    .until(new ExpectedCondition<Boolean>() {
                        @Override
                        public Boolean apply(WebDriver driverObject) {
                            return (Boolean) ((JavascriptExecutor) driverObject).executeScript(IS_JQUERY_STOPPED_SCRIPT);
                        }
                    });
        }

        // Check several times to be sure that we don't fall into delay between executions
        int i = 0;
        do {
            try {
                Thread.sleep(timeoutWithinCycle);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            completed = (Boolean) ((JavascriptExecutor) input).executeScript(IS_JQUERY_STOPPED_SCRIPT);
            if (completed) i++;
        } while (i < maxWaitCycles);

        // Check once, finally
        completed = (Boolean) ((JavascriptExecutor) input).executeScript(IS_JQUERY_STOPPED_SCRIPT);

        return completed;

    }

}
