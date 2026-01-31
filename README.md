# 🚀 Wanderin' Free HomePage

**"자유로운 퍼즐의 세계, 당신만의 모험을 시작하세요"**

📌 **프로젝트 설명**
Wanderin' Free HomePage 는 퍼즐 게임 공식 홈페이지입니다. 
게임 소식 확인, 상세 정보 조회, 1:1 고객지원 및 다양한 결제 시스템을 제공하여 사용자에게 완성도 높은 게임 경험을 지원합니다.


## 🛠 기술 스택

### 🔹 백엔드
<p align="left"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/> 
  <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white"/> </p>
  
### 🔹 프론트엔드
<p align="left"> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"/> 
  <img src="https://img.shields.io/badge/Tailwind%20CSS-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white"/> 
  <img src="https://img.shields.io/badge/JavaScript%20ES6+-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white"/> </p>

## 🌐 인프라 및 배포 (Deployment)

<p align="left"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/> 
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white"/> </p>
  
* **Containerization:** **Docker**를 활용하여 어플리케이션과 DB 환경을 컨테이너화
  - 환경에 구애받지 않는 일관된 개발 및 배포 환경(Standardized Environment) 구축
  - `Docker Compose`를 통한 Spring Boot와 PostgreSQL의 유기적 서비스 오케스트레이션
* **Database:** Docker 컨테이너 기반의 PostgreSQL 운영
* **Server:** 현재 배포 중인 서버 환경 - AWS EC2
## 🧩 ERD


## 🔥 주요 기능

### 1️⃣ 회원
* **회원가입 & 로그인**
  - 일반 회원가입 및 세션 기반 로그인 시스템
  - 사용자별 커스텀 환영 메시지 제공

### 2️⃣ 게임 서비스 및 결제
* **결제 시스템**
  - Portone API 연동을 통한 다양한 결제 수단 지원 (카카오페이, KG이니시스, 이지페이)
  
* **게임 콘텐츠 정보**
  - 게임 사양 정보 및 다운로드 가이드 제공
  - 최신 소식 및 공지사항 실시간 조회

### 3️⃣ 고객지원
* **FAQ (자주 묻는 질문)**
  - 아코디언 UI를 활용하여 게임 이용 관련 반복 질문을 효율적으로 제공
* **문의하기**
  - 사용자가 직접 문의를 등록하고 관리할 수 있는 전용 게시판
  - 자신이 작성한 문의만 조회
 
### 4️⃣ 서비스 운영 및 인프라
* **Docker를 이용한 데이터베이스 관리**
  - **환경 격리:** PostgreSQL을 Docker 컨테이너로 실행하여 PC 환경에 구애받지 않는 독립적인 개발 환경을 구축
  - **데이터 영속성:** Docker Volume 설정을 통해 컨테이너 재시작 시에도 데이터가 보존되도록 안정성을 확보
* **데이터베이스 직접 설계 및 관리**
  - JPA의 자동 생성 기능(`ddl-auto`)을 사용하지 않고, 직접 **DDL(SQL)**을 작성하여 테이블 스키마를 정교하게 관리
* **보안 및 HTTPS (예정)**
  - 사용자 데이터 보호를 위해 향후 SSL 인증서를 적용한 HTTPS 보안 통신을 구축할 계획


---
## 🛠 커밋 컨벤션

타입은 **태그**와 **제목**으로 구성되며, 태그는 영어로 쓰되 첫 문자는 대문자로 시작합니다.  
`태그: 제목` 형태를 유지하며, 콜론(`:`) 뒤에만 공백을 삽입합니다.

| 태그 (Tag) | 설명 (Description) |
| :--- | :--- |
| **Feat** | 새로운 기능을 추가할 경우 |
| **Fix** | 버그를 고친 경우 |
| **Design** | CSS 등 사용자 UI 디자인을 변경할 경우 |
| **Style** | 코드 포맷 변경, 세미콜론 누락 등 코드 수정이 없는 경우 |
| **Refactor** | 프로덕션 코드 리팩토링 |
| **Docs** | 문서를 수정한 경우 (README 등) |
| **Rename** | 파일 또는 폴더명을 수정하거나 옮기는 경우 |
