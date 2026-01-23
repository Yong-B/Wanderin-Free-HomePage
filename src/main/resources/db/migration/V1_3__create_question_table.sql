CREATE TABLE IF NOT EXISTS question_entity (
    id BIGSERIAL PRIMARY KEY,           -- Auto Increment (IDENTITY)
    title VARCHAR(255) NOT NULL,        -- 제목
    content TEXT NOT NULL,              -- 문의 내용
    created_at TIMESTAMP NOT NULL,      -- 작성일
    member_id BIGINT,                   -- 외래키 (Member 테이블의 ID)
    answer TEXT,                        -- 답변 내용
    is_answered BOOLEAN DEFAULT FALSE,  -- 답변 여부
    
    -- 외래키 제약 조건 (Member 테이블 이름과 ID 컬럼명을 확인해주세요)
    CONSTRAINT fk_question_member 
        FOREIGN KEY (member_id) 
        REFERENCES member(id)           -- 만약 회원 테이블명이 다르면 수정 필요
        ON DELETE CASCADE               -- 회원 탈퇴 시 문의글도 삭제
);