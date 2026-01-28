CREATE TABLE IF NOT EXISTS member (
    id         BIGSERIAL PRIMARY KEY,
    login_id   VARCHAR(50) UNIQUE NOT NULL,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(100) UNIQUE NOT NULL,
    role       VARCHAR(20) DEFAULT 'USER', -- 추가: 회원 권한 (USER, ADMIN)
    has_game       BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 테이블 코멘트
COMMENT ON COLUMN member.role IS '회원 권한 (USER: 일반유저, ADMIN: 관리자)';
-- 컬럼 코멘트
COMMENT ON COLUMN member.login_id IS '회원 ID (로그인용)';
COMMENT ON COLUMN member.password IS '비밀번호 (해시 암호화)';
COMMENT ON COLUMN member.name IS '회원 이름';
COMMENT ON COLUMN member.email IS '이메일';
COMMENT ON COLUMN member.created_at IS '회원 생성 시각';
COMMENT ON COLUMN member.updated_at IS '회원 정보 수정 시각';