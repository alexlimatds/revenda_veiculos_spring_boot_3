package net.revenda.dominio;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;

@Embeddable
public class Foto {
    
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] bytes;

	private String mimeType;
	
	public Foto() {}
	
	public Foto(byte[] bytes, String mimeType) {
		this.bytes = bytes;
		this.mimeType = mimeType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
