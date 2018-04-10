
/** 
 *  @(#) BeansUtils.java      1.0  2015年11月18日  {author name}
 * 
 * Copyright  (c)  2015 	fcuh.com. All Rights Reserved.
 */

package platform.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 简要描述本类的主要模块、函数及功能的说明
 * 
 * @author : {author name}
 * @date : 2015年11月18日 下午4:33:11
 * @version : 2015年11月18日 {author name} TODO简要描述修改内容，修改原因
 */

public class BeansUtils {

  /**
   * Map转换层Bean，使用泛型免去了类型转换的麻烦。
   * 
   * @param <T>
   * @param map
   * @param class1
   * @return
   */
  public static <T> T map2Bean( Map<String, ?> map, Class<T> class1 ) {
    T bean = null;
    try {
      bean = class1.newInstance();
      BeanUtils.populate(bean, map);
    }
    catch(InstantiationException e) {
      e.printStackTrace();
    }
    catch(IllegalAccessException e) {
      e.printStackTrace();
    }
    catch(InvocationTargetException e) {
      e.printStackTrace();
    }
    return bean;
  }
}
