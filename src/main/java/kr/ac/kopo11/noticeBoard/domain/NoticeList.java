package kr.ac.kopo11.noticeBoard.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class NoticeList {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	

	@Column
	private String title;
	

	@Column
	private Date date;
	
	@Column
	private String content;
	
	@Column
	private int views;
	
	@Column
	private Long rootid; // 원글번호
	
	@Column
	private int relevel; // 답글레벨 (본글이면 0, 답글 1, 답글의 답글 2....) 
	
	@Column
	private int recnt; // 답글들 내 글 표시 순서 
	


	public int getRecnt() {
		return recnt;
	}


	public void setRecnt(int recnt) {
		this.recnt = recnt;
	}


	@JsonManagedReference
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="noticeList", orphanRemoval = true) // fetch - eager, lazy 
	private Collection<NoticeComment> noticeComments;
	

	public Collection<NoticeComment> getNoticeComments() {
		if( noticeComments == null) {
			noticeComments = new ArrayList<NoticeComment>();
		}
		return noticeComments;
	}
	

	public void setNoticeComments(Collection<NoticeComment> noticeComments) {
		this.noticeComments = noticeComments;
	}
	
	public void addNoticeComment (NoticeComment item) {
		Collection<NoticeComment> noticeComments = getNoticeComments();
		noticeComments.add(item);
	}
	
	public void deleteNoticeComment (NoticeComment item) {
		Collection<NoticeComment> noticeComments = getNoticeComments();
		
		noticeComments.remove(item);
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	

	public Long getRootid() {
		return rootid;
	}


	public void setRootid(Long rootid) {
		this.rootid = rootid;
	}


	public int getRelevel() {
		return relevel;
	}


	public void setRelevel(int relevel) {
		this.relevel = relevel;
	}
	





}
