package com.bingbong.consult.apply.domain.repo;

import com.bingbong.consult.apply.domain.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepo extends JpaRepository<Apply, Long> {
}
