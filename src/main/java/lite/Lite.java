package lite;

public class Lite {
	private int id;
	private String title;
	private String author;
	private String corp;
	private String book;
	private String issn;
	private String page;
	private String _abstract;
	private String keyword;
	private String doi;
	private String localurl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String get_abstract() {
		return _abstract;
	}
	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getLocalurl() {
		return localurl;
	}
	public void setLocalurl(String localurl) {
		this.localurl = localurl;
	}
	public Lite() {
	}
	
	public Lite(int id, String title, String author, String corp, String book, String issn, 
			String page, String _abstract, String keyword, String doi, String localurl){
		this.id=id;
		this.title=title;
		this.author=author;
		this.corp=corp;
		this.book=book;
		this.issn=issn;
		this.page=page;
		this._abstract=_abstract;
		this.keyword=keyword;
		this.doi=doi;
		if (localurl==null) this.localurl=title+".pdf";
		else this.localurl=localurl;
	}
}
