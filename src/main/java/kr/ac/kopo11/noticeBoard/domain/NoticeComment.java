package kr.ac.kopo11.noticeBoard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name = "noticeComment") //table화 시키겠다.
public class NoticeComment {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	private String user;

	@Column
	private String comment;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="noticeListId")
	private NoticeList noticeList;
	

	public NoticeList getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(NoticeList noticeList) {
		this.noticeList = noticeList;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	
}
