package com.example.wantedpreonboardingbackend.board.domain;

import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardFindingResponseDTO;
import com.example.wantedpreonboardingbackend.common.BaseTime;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@NoArgsConstructor
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @Column
    private String title;

    @Column
    private String content;

    public Board(Member author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public BoardFindingResponseDTO toResponseDTO(){
        return new BoardFindingResponseDTO(id,title,content,author.getMemberName(),getCreatedDate(),getLastModifiedDate());
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }
}
