package shiro;

import domain.sys.SysRole;
import domain.sys.SysUserView;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.sys.SysRoleService;
import service.sys.SysUserService;
import sys.shiro.BaseShiroHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShiroHelper extends BaseShiroHelper{

	private final static Logger logger = LoggerFactory.getLogger(ShiroHelper.class);

	private static SysUserService userService;
	private static SysRoleService roleService;


	public static Boolean hasAnyRoles(String roleIds){

		if(StringUtils.isBlank(roleIds)) return false;
		String[] roleIdStrs = roleIds.split(",");
		Map<Integer, SysRole> roleMap = roleService.findAll();
		List<String> roles = new ArrayList<>();
		for (String roleIdStr : roleIdStrs) {
			SysRole sysRole = roleMap.get(Integer.valueOf(roleIdStr));
			roles.add(sysRole.getRole());
		}

		String[] _roles = new String[roles.size()];
		return hasAnyRoles(roles.toArray(_roles));
	}


	public static SysUserView getCurrentUser() {

		if (!hasAuthenticated()) {
			logger.error("unAuthenticated, getCurrentUser is null.");
			return null;
		}
		return userService.findByUsername(getCurrentUsername());
	}

	public static ShiroUser getShiroUser(){
		return (ShiroUser)getSubject().getPrincipal();
	}

	/*
	 * 获得当前用户名
	 * 
	 * @return
	 */
	public static String getCurrentUsername() {

		ShiroUser shiroUser = getShiroUser();
		return (shiroUser!=null)?shiroUser.getUsername():null;
	}

	public static Integer getCurrentUserId() {

		ShiroUser shiroUser = getShiroUser();
		return (shiroUser!=null)?shiroUser.getId():null;
	}

	// 重新加载当前登陆用户权限
	public static void refreshRoles() {

		ShiroUser shiroUser = getShiroUser();
		shiroUser.setRoles(null);
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
		if(null != session && !StringUtils.equals(String.valueOf(session.getId()), ShiroHelper.getSessionId())){
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

	/** 删除某个用户多余的session，只保留一个
	 * @param username
	 * @param currentSessionId
	 */
	public static void removeAll(String username, String currentSessionId){

		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for(Session session : sessions){
			Serializable sessionId = session.getId();
			if(StringUtils.equals(sessionId.toString(), currentSessionId)) continue;
			PrincipalCollection principals = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(principals==null || principals.isEmpty()) continue;
			ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
			if(null != session && StringUtils.equals(username, shiroUser.getUsername())){
				session.setTimeout(0);//设置session立即失效，即将其踢出系统
				logger.info("############## remove session 【{}, {}】 ------ #################", shiroUser.getUsername(), sessionId);
				sessionDAO.delete(session);
			}
		}
	}

	/**
	 * @param userService
	 * @param roleService
	 * @param sessionDAO
	 */
	public static void initStaticField(SysUserService userService,
									   SysRoleService roleService,
									   SessionDAO sessionDAO){
		ShiroHelper.userService = userService;
		ShiroHelper.roleService = roleService;
		ShiroHelper.sessionDAO = sessionDAO;
	}
}