package kr.ac.kopo11.noticeBoard.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import kr.ac.kopo11.noticeBoard.domain.NoticeComment;
import kr.ac.kopo11.noticeBoard.domain.NoticeList;
import kr.ac.kopo11.noticeBoard.repository.Pagination;
import kr.ac.kopo11.noticeBoard.service.NoticeListService;

@Controller
public class NoticeListController {

	@Autowired
	private NoticeListService noticeListService;

	@RequestMapping(value = "/home")
	public String NoticeListAll(Model model,
			@PageableDefault(page = 0, size = 10) @SortDefault.SortDefaults({
				@SortDefault(sort = "rootid", direction = Sort.Direction.DESC),
				@SortDefault(sort = "recnt"),
			}) Pageable pageable) {

		List<NoticeList> noticeListAll = noticeListService.findAll();
		List<NoticeList> noticeList = noticeListService.findListsByPageRequest(pageable).getContent();

		Pagination p = null;
		try {
			p = noticeListService.getPagination(pageable.getPageNumber() + 1, 10, pageable.getPageSize(),
					noticeListAll.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("noticeListAll", noticeList);
		model.addAttribute("p", p);

		return "home";
	}

	@RequestMapping(value = "/home/insert")
	public String NoticeListAdd(Model model) {

		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		NoticeList noticeList = new NoticeList();
		noticeList.setDate(date);
		noticeList.setViews(0);
		noticeList.setRelevel(0);
		noticeList.setRecnt(0);


		model.addAttribute("noticeList", noticeList);

		return "noticeListAdd";
	}
	


	@RequestMapping(value = "/home/save")
	public String NoticeListSave(Model model, @ModelAttribute NoticeList noticeList) {
		
		
		noticeListService.save(noticeList);
		noticeList.setRootid(noticeList.getId());
		noticeListService.save(noticeList);

		return "redirect:/home";
	}
	
	@RequestMapping(value = "/home/save/re")
	public String reNoticeListSave(Model model, @ModelAttribute("noticeList") NoticeList noticeList) {
		
		String re = ">[RE]";
		for(int i = 0; i < noticeList.getRelevel(); i++) {
			re = "-" + re;
		}
		
		noticeList.setTitle(re + noticeList.getTitle());
		
		noticeListService.findByRootid(noticeList.getRootid()).stream()
		.filter(list -> list.getRecnt() >= noticeList.getRecnt())
		.forEach(c -> {
			c.setRecnt(c.getRecnt() + 1);
			noticeListService.save(c);
		});
		
		
		noticeListService.save(noticeList);

		return "redirect:/home";
	}

	@RequestMapping(value = "/home/noticeOne")
	public String NoticeListOne(Model model, @RequestParam(value = "id") Long id) {

		List<NoticeComment> comments = new ArrayList<>();
		noticeListService.findById(id).ifPresent(m -> {
			m.setViews(m.getViews() + 1);
			noticeListService.save(m);
			model.addAttribute("noticeList", m);

			m.getNoticeComments().stream().forEach(c -> {
				comments.add(c);
			});
		});

		model.addAttribute("noticeComment", comments);

		return "noticeOne";
	}
	
	
	@RequestMapping(value = "/home/reInsert") // 답글달기 버튼 클릭 시 
	public String NoticeListReAdd(Model model, @ModelAttribute("noticeList") NoticeList noticeList) { // 답글달기 

		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		NoticeList reNoticeList = new NoticeList();
		reNoticeList.setDate(date);
		reNoticeList.setViews(0);
		reNoticeList.setRelevel(noticeList.getRelevel() + 1);
		reNoticeList.setRootid(noticeList.getRootid());
		reNoticeList.setRecnt(noticeList.getRecnt() + 1);
		
		model.addAttribute("noticeList", reNoticeList);

		return "reNoticeListAdd";
	}

	@RequestMapping(value = "/home/revise")
	public String NoticeListRevise(Model model, @ModelAttribute("noticeList") NoticeList noticeList) {

		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);
		noticeListService.findById(noticeList.getId()).ifPresent(m -> {
			m.setTitle(noticeList.getTitle());
			m.setContent(noticeList.getContent());
			m.setDate(date);
			noticeListService.save(m);
		});

		return "redirect:/home";
	}

	@RequestMapping(value = "/home/delete")
	public String NoticeListDelete(Model model, @ModelAttribute("noticeList") NoticeList noticeList) {

		noticeListService.findById(noticeList.getId()).ifPresent(m -> {
			noticeListService.delete(m);
		});

		return "redirect:/home";
	}

	@RequestMapping(value = "/home/addComment")
	public String AddComment(Model model, @ModelAttribute("noticeList") NoticeList noticeList,
			@ModelAttribute("noticeComment") NoticeComment noticeComment) {

		noticeListService.findById(noticeList.getId()).ifPresent(m -> {
			noticeComment.setNoticeList(m);
			m.addNoticeComment(noticeComment);
			noticeListService.save(m);
		});

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://localhost:8889/home/noticeOne?id=" + noticeList.getId());
		return "redirect:/home/noticeOne?id=" + noticeList.getId();
	}

	@RequestMapping(value = "/home/deleteComment")
	public String DeleteComment(@ModelAttribute NoticeList noticeList, @ModelAttribute NoticeComment noticeComment) {

		noticeListService.findById(noticeList.getId()).ifPresent(m -> {

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
