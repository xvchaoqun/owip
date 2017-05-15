package sys.spring;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;

public class Base64File {

	private String imageType;
	private byte[] content;
	
	public Base64File() {
	}
	
	public Base64File(String imageType, byte[] content) {
		super();
		this.imageType = imageType;
		this.content = content;
	}

	public boolean isEmpty() {
		return ArrayUtils.isEmpty(content);
	}

	public long getSize() {
		return content != null? content.length:0L;
	}

	public byte[] getBytes() throws IOException {
		return content;
	}
	
	public String getImageType() {
		return imageType;
	}

	public InputStream getInputStream() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		return bais;
	}

	public void transferTo(File dest) throws IOException, IllegalStateException {
		if(isEmpty()){
			return;
		}
		try(FileOutputStream fos = new FileOutputStream(dest);){
			fos.write(content, 0, content.length);
		}
	}

}