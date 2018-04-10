package platform.common.utils;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


/**
 * Generate unique IDs using IDs are 64 bit positive longs composed of: - 41
 * bits time stamp - 10 bits machine id - 12 bits sequence number
 */
public class IDGenerator {
  private static Logger      log                = Logger.getLogger("IDGenerator");

  private static final long  datacenterIdBits   = 10L;
  private final long         maxDatacenterId    = -1L ^ ( -1L << datacenterIdBits );
  private final long         sequenceBits       = 12L;
  private final long         datacenterIdShift  = sequenceBits;
  private final long         timestampLeftShift = sequenceBits + datacenterIdBits;
  private final long         sequenceMask       = -1L ^ ( -1L << sequenceBits );

  private static IDGenerator iDGenerator        = null;
  private final long         twepoch            = 1439543514L;
  private long               datacenterId;

  private volatile long      lastTimestamp      = -1L;
  private volatile long      sequence           = 0L;

  /**
   * TODO
   * 
   * @param datacenterId
   */
  private IDGenerator( long datacenterId ) {
    if(datacenterId == 0) {
      try {
        this.datacenterId = getDatacenterId();
      }
      catch(SocketException e) {
        log.warning("IDGenerator: could not determine machine address; using random datacenter ID");
        Random rnd = new Random();
        this.datacenterId = rnd.nextInt((int) maxDatacenterId) + 1;
      }
      catch(UnknownHostException e) {
        log.warning("IDGenerator: could not determine machine address; using random datacenter ID");
        Random rnd = new Random();
        this.datacenterId = rnd.nextInt((int) maxDatacenterId) + 1;
      }
      catch(NullPointerException e) {
        log.warning("IDGenerator: could not determine machine address; using random datacenter ID");
        Random rnd = new Random();
        this.datacenterId = rnd.nextInt((int) maxDatacenterId) + 1;
      }
    }
    else {
      this.datacenterId = datacenterId;
    }

    if(this.datacenterId > maxDatacenterId || datacenterId < 0) {
      log.warning("IDGenerator: datacenterId > maxDatacenterId; using random datacenter ID");
      Random rnd = new Random();
      this.datacenterId = rnd.nextInt((int) maxDatacenterId) + 1;
    }
    log.info("IDGenerator: initialised with datacenter ID " + this.datacenterId);
  }

  public static synchronized IDGenerator getInstance() {
    if(iDGenerator == null) {
      iDGenerator = new IDGenerator(datacenterIdBits);
    }
    return iDGenerator;
  }

  protected long tilNextMillis( long lastTimestamp ) {
    long timestamp = System.currentTimeMillis();
    while(timestamp <= lastTimestamp) {
      timestamp = System.currentTimeMillis();
    }
    return timestamp;
  }

  protected long getDatacenterId() throws SocketException, UnknownHostException {
    NetworkInterface network = null;

    Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
    while(en.hasMoreElements()) {
      NetworkInterface nint = en.nextElement();
      if(!nint.isLoopback() && nint.getHardwareAddress() != null) {
        network = nint;
        break;
      }
    }

    byte[] mac = network.getHardwareAddress();

    Random rnd = new Random();
    byte rndByte = (byte) ( rnd.nextInt() & 0x000000FF );

    // take the last byte of the MAC address and a random byte as datacenter
    // ID
    long id = ( ( 0x000000FF & (long) mac[mac.length - 1] ) | ( 0x0000FF00 & ( ( (long) rndByte ) << 8 ) ) ) >> 6;

    return id;
  }

  /**
   * Return the next unique id for the type with the given name using the
   * generator's id generation strategy.
   *
   * @return
   */
  public synchronized long getId() {
    long timestamp = System.currentTimeMillis();
    if(timestamp < lastTimestamp) {
      log.warning("Clock moved backwards. Refusing to generate id for {} milliseconds.");
      try {
        Thread.sleep(( lastTimestamp - timestamp ));
      }
      catch(InterruptedException e) {}
    }
    if(lastTimestamp == timestamp) {
      sequence = ( sequence + 1 ) & sequenceMask;
      if(sequence == 0) {
        timestamp = tilNextMillis(lastTimestamp);
      }
    }
    else {
      sequence = 0;
    }
    lastTimestamp = timestamp;
    long id = ( ( timestamp - twepoch ) << timestampLeftShift ) | ( datacenterId << datacenterIdShift ) | sequence;

    if(id < 0) {
      log.warning("ID is smaller than 0: {}");
    }
    return id;
  }

  /**
   * Shut down this id generator, performing any cleanups that might be
   * necessary.
   */
  public void shutdown() {
    // To change body of implemented methods use File | Settings | File
    // Templates.
  }

  private final static Set<Long>       set         = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());
  private final static ExecutorService exe         = Executors.newCachedThreadPool();
  final static IDGenerator             idGenerator = new IDGenerator(1);

  public static void main( String[] args ) {
    try {
      int threadNum = 0;
      for( int i = 1; i <= 100; i++ ) {
        threadNum++;
        final int currentThreadNum = threadNum;
        exe.execute(new Runnable() {
          public void run() {
            try {
              for( int k = 1; k <= 10000; k++ ) {
                set.add(idGenerator.getId());
              }
            }
            finally {
              System.out.println("子线程[" + currentThreadNum + "]结束");
            }
          }
        });
      }
      System.out.println("已经开启所有的子线程------------------------------");
      exe.shutdown();
      System.out.println("shutdown()：启动一次顺序关闭，执行以前提交的任务，但不接受新任务。");
      while(true) {
        if(exe.isTerminated()) {
          System.out.println("所有的子线程都结束了！");
          break;
        }
        Thread.sleep(1000);
      }

    }
    catch(InterruptedException e) {
      e.printStackTrace();
    }
    finally {
      System.out.println("主线程结束");
      System.out.println(set.size());
    }
  }
}
