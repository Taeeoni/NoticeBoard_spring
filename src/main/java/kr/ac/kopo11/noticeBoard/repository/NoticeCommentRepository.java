    package kr.ac.kopo11.noticeBoard.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import kr.ac.kopo11.noticeBoard.domain.NoticeComment;

@Repository
public interface NoticeCommentRepository extends JpaRepository<NoticeComment, Long> {

}
