package platform.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class Springfactory implements BeanFactoryAware{

	private static BeanFactory beanFactory;
//	private static ApplicationContext context;
	
	public void setBeanFactory (BeanFactory factory)throws BeansException{
		this.beanFactory = factory;
	}
	
	public static BeanFactory getBeanFactory(){
		return beanFactory;
	}

//	
//	public void setApplicationContext(ApplicationContext context) throws BeansException {
//		// TODO Auto-generated method stub
//		this.context=context;  
//	}  
//	public static ApplicationContext getContext(){  
//		   return context;  
//	}
//	
	/**
	 * 根据beanName名字取得bean
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(String beanName) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}
	/**
	 * 根据service 名字取得实例
	 * @param serviceName
	 * @return
	 */
	public static Object getService(String serviceName){
		return Springfactory.getBean(serviceName);
	}
	
	/**
	 * 根据daoName 名字取得实例
	 * @param daoName
	 * @return
	 */
	public static Object getDao(String daoName){
		return Springfactory.getBean(daoName);
	}
	
}
