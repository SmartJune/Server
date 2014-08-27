import java.io.Serializable;


public class DataObject implements Serializable {
	private String url;
	private String js;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getJs() {
		return js;
	}
	public void setJs(String js) {
		this.js = js;
	}
}
