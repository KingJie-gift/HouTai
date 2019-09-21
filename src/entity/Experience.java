package entity;

public class Experience {
private int id;
private String date;
private String title;
private String content;
private String smallContent;
private String img;
private int subjectId;
private Subject subject;

public Subject getSubject() {
	return subject;
}
public void setSubject(Subject subject) {
	this.subject = subject;
}
public int getSubjectId() {
	return subjectId;
}
public void setSubjectId(int subjectId) {
	this.subjectId = subjectId;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getSmallContent() {
	return smallContent;
}
public void setSmallContent(String smallContent) {
	this.smallContent = smallContent;
}
public String getImg() {
	return img;
}
public void setImg(String img) {
	this.img = img;
}


}
