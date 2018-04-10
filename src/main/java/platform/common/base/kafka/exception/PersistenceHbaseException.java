package platform.common.base.kafka.exception;


/***
 * 持久化hbase异常
 * 
 * @author : muck
 * @date : 2015年10月15日 上午11:35:58
 * @version : 1.0
 */
public class PersistenceHbaseException extends RuntimeException {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6558907110683314465L;

  public PersistenceHbaseException() {

    super();

  }

  public PersistenceHbaseException( String msg ) {

    super(msg);

  }

  public PersistenceHbaseException( String msg, Throwable cause ) {

    super(msg, cause);

  }

  public PersistenceHbaseException( Throwable cause ) {

    super(cause);

  }

}
