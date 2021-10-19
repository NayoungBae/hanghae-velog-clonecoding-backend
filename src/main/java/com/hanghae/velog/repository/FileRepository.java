package com.hanghae.velog.repository;

import com.hanghae.velog.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
