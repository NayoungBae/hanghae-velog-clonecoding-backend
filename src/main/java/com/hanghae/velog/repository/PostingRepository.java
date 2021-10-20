package com.hanghae.velog.repository;

import com.hanghae.velog.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
//    List<Posting> findAllByOrderByCreatedAtDesc();
    Posting findByPostingId(Long postingId);
    List<Posting> findByUserName(String userName);
}
