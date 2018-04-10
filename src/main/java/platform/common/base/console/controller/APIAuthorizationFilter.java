package platform.common.base.console.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.app.server.cache.RedisKeyConstant;
import org.app.server.cache.ShardedJedisUtils;

import platform.common.utils.HMACSHA1;
import platform.common.utils.StringUtil;

public class APIAuthorizationFilter implements Filter {

	private static final Logger logger = Logger.getLogger(APIAuthorizationFilter.class);
	private FilterConfig config = null;
	// 不需要校验的URL
	private String[] excludedUrls;
	private String maxAge = "3628800";

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String age = config.getInitParameter("AccessControlMaxAge");
		if (StringUtil.isNotEmpty(age)) {
			maxAge = age ;
		}
		String excludes = config.getInitParameter("excludedUrls");
		logger.info("excludes=" + excludes);
		if (excludes != null) {
			this.excludedUrls = excludes.split(",");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse,
			FilterChain paramFilterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) paramServletRequest;
		HttpServletResponse response = (HttpServletResponse) paramServletResponse;
		// 表明在xxx秒内，不需要再发送预检验请求，可以缓存该结果
        response.setHeader("Access-Control-Max-Age",maxAge);
        // 表明它允许跨域请求包含xxx头
		response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,OPTIONS,DELETE");
		response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错 
    	response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Authorization"); 
    	
		if ("OPTIONS".equals(request.getMethod())){
        	response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,OPTIONS,DELETE");
        	response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With");
        	return;
	    }
       
		ServletRequest requestWrapper = null;  
        if(request instanceof HttpServletRequest) {  
            requestWrapper = new MAPIHttpServletRequestWrapper((HttpServletRequest) request);  
        }
        
		String authorization = request.getHeader("Authorization");
		logger.debug("Authorization : " + authorization);
		int index1 = authorization.indexOf(" ");
		int index2 = authorization.indexOf(":");
		String secretAccessKeyID = authorization.substring(index1+1, index2);
		String reqSign = authorization.substring(index2+1);
		String reqURL = request.getRequestURL().toString();
		//校验Authorization    
		/**
		 *  其中， Authorization = "OGC" + " " + SecretAccessKeyID + ":" + sign; 
			OGC:由平台侧统一生成
			sign：由SecretAccessKey+请求体+请求URL
		 */
		InputStream inputStream = requestWrapper.getInputStream();
        String requestString = IOUtils.toString(inputStream, "utf-8");
		//根据 SecretAccessKeyID 取  SecretAccessKey 
		ShardedJedisUtils shardJedis = new ShardedJedisUtils();
		String secretAccessKey=shardJedis.getValueByeKey(RedisKeyConstant.API_SECRETACCESSKEYID_+secretAccessKeyID);
		if (StringUtil.isEmpty(secretAccessKey)) {
			logger.error("SecretAccessKeyID:"+secretAccessKeyID+"  can't find key ");
			String resp = "{\"code\":\"999999\",\"msg\":\" SecretAccessKeyID error \"}";// SecretAccessKeyID有误返回码
			response.getWriter().write(resp);
			response.getWriter().flush();
			return;
		}
		//查询token
		String token = shardJedis.getValueByeKey(RedisKeyConstant.API_SECRETACCESSKEYID_TOKEN_+secretAccessKeyID);
		if (StringUtil.isEmpty(token)) {
			logger.error("SecretAccessKeyID:"+secretAccessKeyID+"  can't find key ");
			String resp = "{\"code\":\"999996\",\"msg\":\" token error \"}";// SecretAccessKeyID有误返回码
			response.getWriter().write(resp);
			response.getWriter().flush();
			return;
		}
		StringBuffer  sb = new StringBuffer();
		sb.append(requestString).append(reqURL).append(token);
		String sign=null;
		try {
			logger.info("before sign str :"+sb.toString()+" ,secretAccessKey="+secretAccessKey);
			sign = new sun.misc.BASE64Encoder().encode( HMACSHA1.HmacSHA1Encrypt(secretAccessKey, sb.toString()).getBytes());
			logger.info("after sign  :"+sign);
		} catch (Exception e) {
			logger.error(" HmacSHA1Encrypt Exception !");
			String resp = "{\"code\":\"999998\",\"msg\":\" Verify signature failed  \"}";// SecretAccessKeyID有误返回码
			response.getWriter().write(resp);
			response.getWriter().flush();
			return;
		}
		if (!reqSign.equals(sign)) {
			logger.error("request sign:"+reqSign+",\n platform sign:"+sign+",Verify signature failed ");
			String resp = "{\"code\":\"999998\",\"msg\":\" Verify signature failed  \"}";// SecretAccessKeyID有误返回码
			response.getWriter().write(resp);
			response.getWriter().flush();
			return;
		}
		// 取得缓存中的sign是否有一致，一致则为重复请求
		String cacheSign = shardJedis.getValueByeKey(sign);
		if (StringUtil.isNotEmpty(cacheSign)) {
			logger.error("request sign:"+reqSign+",sign:"+sign+",Repeat request ");
			String resp = "{\"code\":\"999997\",\"msg\":\" Repeat request  \"}";// SecretAccessKeyID有误返回码
			response.getWriter().write(resp);
			response.getWriter().flush();
			return;
		}else{
			// 缓存10分钟
			shardJedis.set(sign, sign, 60*10);
		}
		if(null == requestWrapper) {
			paramFilterChain.doFilter(request, response);  
        } else {  
        	paramFilterChain.doFilter(requestWrapper, response);  
        }  
		return;
	}
}
