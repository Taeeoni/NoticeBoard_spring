package kr.ac.kopo11.noticeBoard.repository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.ac.kopo11.noticeBoard.domain.NoticeList;

@Repository
public interface NoticeListRepository extends JpaRepository<NoticeList, Long> {
	
	@Override
	@Query("select u from NoticeList u order by u.rootid desc")
	List<NoticeList> findAll();
	
	Page<NoticeList> findAll(Pageable pageable);
	
	Collection<NoticeList> findByRootid(Long id);
	
}
