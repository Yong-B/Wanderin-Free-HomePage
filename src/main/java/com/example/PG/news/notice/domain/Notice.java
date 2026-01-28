package com.example.PG.news.notice.domain;

import com.example.PG.user.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "view_count")
    @ColumnDefault("0") // SQL의 DEFAULT 0과 매칭
    private Integer viewCount; // 기본값 적용을 위해 객체 타입(Integer) 권장

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_notice_member"))
    private Member member;

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @Builder
    public Notice(String title, String content, Member member, NoticeType type) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.type = type; // 추가
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.viewCount = 0; // 초기화
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void addViewCount() {
        if (this.viewCount == null) this.viewCount = 0;
        this.viewCount++;
    }
}
