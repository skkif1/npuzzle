package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatHolder
{

    private static List<StatHolder> holders = new ArrayList<>();

    static synchronized void addHolder(StatHolder holder)
    {
        holders.add(holder);
    }

    static List<StatHolder> getHolders()
    {
        return holders;
    }

    private String filePath;

    private HashMap<String, Timer> methodMethodExecTime = new HashMap<>();

    private HashMap<String, Long> methodIterator = new HashMap<>();

    private HashMap<String, Object> methodInfo = new HashMap<>();


    public String getFilePath() {
        return filePath;
    }

    public StatHolder(String filePath)
    {
        addHolder(this);
        this.filePath = filePath;
    }


    public void startTime(String method)
    {
        if (methodMethodExecTime.containsKey(method))
        {
            methodMethodExecTime.get(method).start();
        }
        else
        {
            Timer t = new Timer();
            methodMethodExecTime.put(method, t);
            t.start();
        }
    }

    public void putInfo (String method, Object info)
    {
        methodInfo.put(method, info);
    }

    public Object getMethodInfo(String method)
    {
        return methodInfo.get(method);
    }

    public void endTime(String method)
    {
        methodMethodExecTime.get(method).stop();
    }

    public void iterate(String method)
    {
        if (methodIterator.containsKey(method))
        {
            Long temp = methodIterator.get(method);
            temp++;
            methodIterator.put(method, temp);

        }
        else
        {
            methodIterator.put(method, 1L);
        }
    }

    static class Timer {

        long value = 0;

        long temp = 0;


        Timer()
        {
        }

        void start()
        {
            temp = System.nanoTime();
        }

        void stop()
        {
            value = System.nanoTime() - temp;
            temp = 0;
        }
    }

    void printStat(ResultLogger logger)
    {

        for (Map.Entry<String, Timer> stringTimerEntry : methodMethodExecTime.entrySet())
        {
            System.out.println(stringTimerEntry.getKey() + " : " + timeValueToString(stringTimerEntry.getValue().value));
            logger.logResult(stringTimerEntry.getKey() + " : " + timeValueToString(stringTimerEntry.getValue().value) + "\n");
        }

        for (Map.Entry<String, Long> stringTimerEntry : methodIterator.entrySet())
        {
            System.out.println(stringTimerEntry.getKey() + " : " + stringTimerEntry.getValue());
            logger.logResult(stringTimerEntry.getKey() + " : " + stringTimerEntry.getValue() + "\n");
        }
    }

    private String timeValueToString(long value)
    {
        long seconds = value / 1000 / 1000 / 1000 / 1000;
        long milliseconds = value / 1000 / 1000;

        if (seconds != 0)
            return seconds + " seconds";
        if (milliseconds != 0)
            return milliseconds + " milliseconds";
        return value + " nanosecods";
    }
}
