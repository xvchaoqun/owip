<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>     
<%@page import="java.io.*,org.springframework.web.multipart.commons.CommonsMultipartResolver"%>     
<%@page import="org.springframework.web.multipart.MultipartHttpServletRequest"%>     
<%@page import="java.util.concurrent.locks.*"%>     
<%@page import="org.springframework.web.multipart.MultipartFile"%>     
<%     
    // 请求 包装过滤器     
    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
	MultipartHttpServletRequest muHttpServletRequest = commonsMultipartResolver.resolveMultipart(request);
	MultipartFile file = muHttpServletRequest.getFile("imgFile");
    // 获得上传的文件名      
    String fileName = file.getOriginalFilename();     
    //获得未见过滤器      
    //----------- 重新构建上传文件名----------------------     
    final Lock lock = new ReentrantLock();     
    String newName = null;     
    lock.lock();     
   try {     
        //加锁为防止文件名重复      
        newName = System.currentTimeMillis()     
                + fileName.substring(fileName.lastIndexOf("."),     
                        fileName.length());     
    }finally {     
        lock.unlock();     
    }     
    //------------ 锁结束 -------------     
    //获取文件输出流      
    FileOutputStream fos = new FileOutputStream(request.getSession()     
            .getServletContext().getRealPath("/")     
            + "/upload/" + newName);
    //设置 KE 中的图片文件地址      
    String newFileName =  request.getContextPath() + "/upload/" + newName;     
    //获取内存中当前文件输入流      
    
		try {
			byte[] bytes = file.getBytes();
			fos.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			fos.close();
		}
    
    //发送给KE      
    out.println("<html><head><title>Insert Image</title><meta http-equiv='content-type' content='text/html; charset=gbk'/></head><body>");     
    out.println("<script type='text/javascript'>");     
    out.println("parent.parent.KE.plugin['image'].insert('"     
            + muHttpServletRequest.getParameter("id") + "','" + newFileName + "','"     
            + muHttpServletRequest.getParameter("imgTitle") + "','"     
            + muHttpServletRequest.getParameter("imgWidth") + "','"     
            + muHttpServletRequest.getParameter("imgHeight") + "','"     
            + muHttpServletRequest.getParameter("imgBorder") + "','"     
            + muHttpServletRequest.getParameter("align") + "');</script>");     
    out.println("</body></html>");     
%>    