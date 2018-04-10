package platform.common.base.kafka.exception;


/***
 * 持久化mysql异常
 * 
 * @author : muck
 * @date : 2015年10月15日 上午11:35:58
 * @version : 1.0
 */
public class PersistenceMySqlException extends RuntimeException {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6558907110683314465L;

  public PersistenceMySqlException() {

    super();

  }

  public PersistenceMySqlException( String msg ) {

    super(msg);

  }

  public PersistenceMySqlException( String msg, Throwable cause ) {

    super(msg, cause);

  }

  public PersistenceMySqlException( Throwable cause ) {

    super(cause);

  }

}
