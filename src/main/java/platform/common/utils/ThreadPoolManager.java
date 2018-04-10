package platform.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang.StringUtils;

public class ThreadPoolManager {

    private static final Map<String, ThreadPoolExecutor> manager           = new ConcurrentHashMap<String, ThreadPoolExecutor> ();

    private static final String                          DEFAULT_POOL_NAME = "default";
    private static final int                             POOL_SIZE         = 5;

    public final static ThreadPoolExecutor getThreadPool(String poolName,int poolCount){
        if (StringUtils.isEmpty (poolName)) {
            poolName = DEFAULT_POOL_NAME;
        }
        ThreadPoolExecutor exec = manager.get (poolName);
        if (exec == null) {
            synchronized (manager) {
                exec = manager.get (poolName);
                if (exec == null) {
                    exec = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//                    exec = new ThreadPoolExecutor(20, 1000, 1, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());
                    manager.put (poolName, exec);
                }
            }
        }

        return exec;
    }

    public final static void excutePool(String poolName,Runnable task){
        ThreadPoolExecutor exec = getThreadPool (poolName, POOL_SIZE);
        // 执行线程池中的线程
        exec.submit (task);
    }

    public final static void excutePool(String poolName,Runnable task,int count){
        ThreadPoolExecutor exec = getThreadPool (poolName, count);
        // 执行线程池中的线程
        exec.submit (task);
    }

    public static void main(String args[]){
        ThreadPoolExecutor mytest = getThreadPool ("test", POOL_SIZE);
        System.out.println (mytest.getCompletedTaskCount ());
    }
}
