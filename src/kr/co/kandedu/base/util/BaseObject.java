package kr.co.kandedu.base.util;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public String toString() {
		return ToStringBuilder.reflectionToString( this,  ToStringStyle.MULTI_LINE_STYLE );
	}

	public boolean equals( Object o ) {
		return EqualsBuilder.reflectionEquals( this, o );
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode( this );
	}
}
