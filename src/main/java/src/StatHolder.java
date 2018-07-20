package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    private HashMap<String, Timer> methodMethodExecTime = new HashMap<>();


    public StatHolder()
    {
        holders.add(this);
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

    public void endTime(String method)
    {
        methodMethodExecTime.get(method).stop();
    }


    static class Timer {

        long value = 0;

        long temp = 0;


        public Timer()
        {
        }

        public void start()
        {
            temp = System.currentTimeMillis();
        }

        public void stop()
        {
            value = System.currentTimeMillis() - temp;
            temp = 0;
        }
    }

}
