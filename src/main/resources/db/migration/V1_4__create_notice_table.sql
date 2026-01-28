CREATE TABLE notice (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    member_id BIGINT,
    type VARCHAR(20) NOT NULL DEFAULT 'NOTICE', -- 공지/패치 구분용 컬럼 추가
    view_count BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_notice_member FOREIGN KEY (member_id) REFERENCES member(id)
);