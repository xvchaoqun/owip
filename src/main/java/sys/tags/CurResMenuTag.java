package sys.tags;

import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class CurResMenuTag extends BodyTagSupport {
	
	private int level;
	
	@Override
	public int doStartTag(){
         //HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		/*
		 WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		 SysResourceService sysResourceService = (SysResourceService) wac.getBean("sysResourceService");
		 SysResourceMapper sysResourceMapper = (SysResourceMapper) wac.getBean("sysResourceMapper");
		 
		 String resourceString = (String) request.getAttribute("CurRes");
		 SysResource sysResource = sysResourceService.getSysResource(resourceString, SysResource.RESOURCE_TYPE_URL);
		
		 JspWriter writer = pageContext.getOut(); 
		 if(level==2){
			 writer.write(sysResource.getName());
		 }else{
			 
			 SysResource sysResource2 = sysResourceMapper.selectByPrimaryKey(sysResource.getFid());
			 if(null == sysResource2){
				 
				 writer.write("");
			 }else{
				 
				 writer.write(sysResource2.getName());
			 }
		 }*/
		return EVAL_PAGE;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
