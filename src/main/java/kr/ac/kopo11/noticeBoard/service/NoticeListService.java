package kr.ac.kopo11.noticeBoard.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.ac.kopo11.noticeBoard.domain.NoticeList;
import kr.ac.kopo11.noticeBoard.repository.NoticeCommentRepository;
import kr.ac.kopo11.noticeBoard.repository.NoticeListRepository;
import kr.ac.kopo11.noticeBoard.repository.Pagination;

@Service
public class NoticeListService {

	@Autowired
	private NoticeListRepository noticeListRepository;
	

	public void setNoticeListRepository(NoticeListRepository noticeListRepository) {
		this.noticeListRepository = noticeListRepository;
	}
	

	public Page<NoticeList> findListsByPageRequest(Pageable pageable) {
		Page <NoticeList> noticeList = noticeListRepository.findAll(pageable);
		return noticeList;
	}

	public Optional<NoticeList> findById(Long id) {
		// TODO Auto-generated method stub
		return noticeListRepository.findById(id);
	}

	public List<NoticeList> findAll() {
		// TODO Auto-generated method stub
		return noticeListRepository.findAll();
	}

	public void save(NoticeList noticeList) {
		noticeListRepository.save(noticeList);
	}
	
	public void delete(NoticeList noticeList) {
		
		noticeListRepository.delete(noticeList);
	}
	
	public Collection<NoticeList> findByRootid(Long id){
		return noticeListRepository.findByRootid(id);
	}
	

	// 현재페이지, 한줄에 보이는 페이지 수, 페이지당글, 총글수
	public Pagination getPagination(int currPage, int countPerPage, int pageSize, int totalCount) throws Exception {

		Pagination p = new Pagination();
		p.setCountPerPage(countPerPage);
		p.setPageSize(pageSize);
		p.setPpPage(1);
		p.setNnPage((totalCount - 1) / pageSize + 1);
		p.setIsLast(0);

		// set 현재페이지 
		if(currPage < 0) {
			p.setcPage(1);
		} else if(currPage > p.getNnPage()) {
			p.setcPage(p.getNnPage());
		} else {
			p.setcPage(currPage);
		}
		
		// set 이전페이지 
		
		if (p.getcPage() - countPerPage  < 1) {
			p.setpPage(1);
		} 
		else {
			p.setpPage(p.getcPage() - countPerPage);
		}

		// set 다음페이지
		if (p.getcPage() + countPerPage > p.getNnPage()) {
			p.setNpage(p.getNnPage());
		} 
		else {
			p.setNpage(p.getcPage() + countPerPage);
		}
		
		p.setPageStart(((Integer) (p.getcPage() - 1) / countPerPage ) * countPerPage + 1);
		
		if(p.getPageStart() + p.getCountPerPage() > p.getNnPage()) {
			p.setIsLast(1); // 안보이게
		}
		
		return p;
	}



	

}
