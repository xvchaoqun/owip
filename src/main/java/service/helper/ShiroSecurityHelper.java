package service.helper;

import domain.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.sys.SysUserService;
import shiro.ShiroUser;

import java.util.Collection;
import java.util.Set;

public class ShiroSecurityHelper {

	private final static Logger logger = LoggerFactory.getLogger(ShiroSecurityHelper.class);

	private static SessionDAO sessionDAO;
	private static SysUserService userService;

	public static SysUser getCurrentUser() {

		if (!hasAuthenticated()) {
			return null;
		}
		return userService.findByUsername(getCurrentUsername());
	}

	/*
	 * 获得当前用户名
	 * 
	 * @return
	 */
	public static String getCurrentUsername() {

		Subject subject = getSubject();
		ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
		return (shiroUser!=null)?shiroUser.getUsername():null;
	}

	public static Integer getCurrentUserId() {

		Subject subject = getSubject();
		ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
		return (shiroUser!=null)?shiroUser.getId():null;
	}

	/**
	 * 
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 
	 * @return
	 */
	public static String getSessionId() {
		Session session = getSession();
		if (null == session) {
			return null;
		}
		return getSession().getId().toString();
	}
	
	/**
	 * @param username
	 * @return
	 */
	public static Session getSessionByUsername(String username){
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session : sessions){
			PrincipalCollection principals = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principals==null || principals.isEmpty()) continue;
			ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
			if(null != session && StringUtils.equals(shiroUser.getUsername(), username)){
				return session;
			}
		}
		return null;
	}

	public static Session getSessionBySessionId(String sessionId){

		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session : sessions){
			if(null != session && StringUtils.equals(sessionId, session.getId().toString())){
				return session;
			}
		}
		return null;
	}

	public static String getUsername(String sessionId){

		Session session = getSessionBySessionId(sessionId);
		PrincipalCollection principals = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		if(principals==null || principals.isEmpty()) return null;
		ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();

		return (shiroUser!=null)?shiroUser.getUsername():null;
	}
	
	/**踢除用户
	 * @param username
	 */
	public static void kickOutUser(String username){
		Session session = getSessionByUsername(username);
		if(null != session && !StringUtils.equals(String.valueOf(session.getId()), ShiroSecurityHelper.getSessionId())){
			session.setTimeout(0);//设置session立即失效，即将其踢出系统
			logger.info("############## success kick out user 【{}】 ------ #################", username);
			sessionDAO.delete(session);
		}
	}
	/**踢除多个用户
	 * @param usernames
	 */
	public static void kickOutUser(Set<String> usernames){

		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session : sessions){
			PrincipalCollection principals = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principals==null || principals.isEmpty()) continue;
			ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
			if(null != session && usernames.contains(shiroUser.getUsername())){
				session.setTimeout(0);//设置session立即失效，即将其踢出系统
				logger.info("############## success kick out user 【{}】 ------ #################", shiroUser.getUsername());
				sessionDAO.delete(session);
			}
		}
	}

	/**
	 * @param userService
	 * @param sessionDAO
	 */
	public static void initStaticField(SysUserService userService,SessionDAO sessionDAO){
		ShiroSecurityHelper.userService = userService;
		ShiroSecurityHelper.sessionDAO = sessionDAO;
	}
	
	/**
	 * 判断当前用户是否已通过认证
	 * @return
	 */
	public static boolean hasAuthenticated() {
		return getSubject().isAuthenticated();
	}

	private static Subject getSubject() {
		return SecurityUtils.getSubject();
	}


}