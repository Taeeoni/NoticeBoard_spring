package kr.ac.kopo11.noticeBoard.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.kopo11.noticeBoard.domain.NoticeComment;
import kr.ac.kopo11.noticeBoard.domain.NoticeList;
import kr.ac.kopo11.noticeBoard.service.NoticeListService;

@RestController
@RequestMapping("postapi")
public class PostController {

	@Autowired
	NoticeListService noticeListService;

	@GetMapping(value = "/home", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<NoticeList>> noticeListAll(Pageable pageable) {

		Page<NoticeList> noticeList = noticeListService.findListsByPageRequest(pageable);

		return new ResponseEntity<Page<NoticeList>>(noticeList, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/home/findOne", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoticeList> findNoticeOne(Long id) {
		
		NoticeList noticeList = noticeListService.findById(id).get();
		noticeList.setViews(noticeList.getViews() + 1);
		noticeListService.save(noticeList);

		return new ResponseEntity<NoticeList>(noticeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/home/findComments", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<NoticeComment>> findNoticeComments(Long id) {
		
		List<NoticeComment> noticeComment = noticeListService.findById(id).get().getNoticeComments().stream().toList();

		return new ResponseEntity<List<NoticeComment>>(noticeComment, HttpStatus.OK);
	}
	
	

	@GetMapping(value = "/home/new", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoticeList> noticeNew() {

		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		NoticeList noticeList = new NoticeList();
		noticeList.setDate(date);
		noticeList.setViews(0);
		noticeList.setRelevel(0);
		noticeList.setRecnt(0);


		return new ResponseEntity<NoticeList>(noticeList, HttpStatus.OK);
	}

	@PostMapping(value = "/home/save")
	public String noticeListSave(NoticeList noticeList) {

		noticeListService.save(noticeList);
		noticeList.setRootid(noticeList.getId());
		noticeListService.save(noticeList);

		return null;
	}
	
	
	@RequestMapping(value = "/home/save/re")
	public String reNoticeListSave(NoticeList noticeList) { // 답글 저장 
		
//		String re = ">[RE]";
//		for(int i = 0; i < noticeList.getRelevel(); i++) {
//			re = "-" + re;
//		}
//		
//		noticeList.setTitle(re + noticeList.getTitle());
		
		
		
		noticeListService.findByRootid(noticeList.getRootid()).stream()
		.filter(list -> list.getRecnt() >= noticeList.getRecnt())
		.forEach(c -> {
			c.setRecnt(c.getRecnt() + 1);
			noticeListService.save(c);
		});
		
		
		noticeListService.save(noticeList);

		return "redirect:/home";
	}
	
	@RequestMapping(value = "/home/reNew") // 답글달기 버튼 클릭 시 
	public ResponseEntity<NoticeList> NoticeListReAdd(Long id) { // 답글달기 
		
		NoticeList noticeList = noticeListService.findById(id).get();
		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		NoticeList reNoticeList = new NoticeList();
		reNoticeList.setDate(date);
		reNoticeList.setViews(0);
		reNoticeList.setRelevel(noticeList.getRelevel() + 1);
		reNoticeList.setRootid(noticeList.getRootid());
		reNoticeList.setRecnt(noticeList.getRecnt() + 1);
		

		return new ResponseEntity<NoticeList>(reNoticeList, HttpStatus.OK);
	}



	@RequestMapping(value = "/home/revise")
	public String noticeListRevise(NoticeList noticeList) {

		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		noticeListService.findById(noticeList.getId()).ifPresent(m -> {
			m.setTitle(noticeList.getTitle());
			m.setContent(noticeList.getContent());
			m.setDate(date);
			m.setViews(noticeList.getViews());
			noticeListService.save(m);
		});

		return null;
	}

	@PostMapping(value = "/home/delete")
	public String noticeListDelete(NoticeList noticeList) {

		noticeListService.findById(noticeList.getId()).ifPresent(m -> {
			noticeListService.delete(m);
		});

		return null;
	}

	@PostMapping(value = "/home/addComment")
	public String addComment(NoticeList noticeList, NoticeComment noticeComment) {

		noticeListService.findById(noticeList.getId()).ifPresent(m -> {
			noticeComment.setNoticeList(m);
			m.addNoticeComment(noticeComment);
			noticeListService.save(m);
		});

		return null;
	}

	@PostMapping(value = "/home/deleteComment")
	public String deleteComment(Long noticeListId, NoticeComment noticeComment) {

		noticeListService.findById(noticeListId).ifPresent(m -> {

			m.getNoticeComments().stream().filter(comment -> comment.getId().equals(noticeComment.getId())).findAny()
					.ifPresent(c -> {
						c.setNoticeList(null);
						m.getNoticeComments().remove(c);
						noticeListService.save(m);
						
					});

		});

		return null;
	}

}
