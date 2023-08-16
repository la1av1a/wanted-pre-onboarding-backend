package com.example.wantedpreonboardingbackend.board.domain;

import com.example.wantedpreonboardingbackend.member.domain.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long>{

    Optional<Board> findBoardByIdAndAuthorMemberId(Long id,Long memberId);

    Page<Board> findAll(Pageable pageable);
}
