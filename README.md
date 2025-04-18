# Team2 TodoLIst

Spring Boot, 바닐라 HTML, CSS, JS 기반의 TodoList 팀 프로젝트입니다.
JWT 인증을 이용한 로그인 기능과 카테고리 별로 Todo 관리, 좋아요 기능을 구현했습니다.

## 프로젝트 개요
- 개발 기간: 2025.03 ~ 2025.04
- 팀구성: 프론트엔드 2명, 백엔드 1명
- 주요 기능: 로그인/회원가입, Todo CURD, 좋아요 기능, 카테고리 별 조회

## 기술 스택
- **언어**: HTML, CSS, JS, JAVA 17
- **Framwork**: SPring Boot, Spring Security, JPA
- **Database**: MySQL
- **Infra & DevOps**: Docker, Amazon EC2, Amazon RDS
- **API 문서화**: Swagger API

## 주요 기능
- JWT 로그인/회원가입
- Todo 등록, 조회, 수정, 삭제
- Todo 좋아요 기능 (토글 및 카운트)
- 카테고리별 필터링 및 최신순 정렬
- Swagger 기반 API 문서 자동화
- Docker Compose 기반 로컬 실행
= EC2 + RDS 배포

## 프로젝트 구조

``` plaintext
📁 com.team2.TodoList
 ┣ 📂 common
 ┃ ┣ 📂 domain/exception – 에러 코드 및 예외 처리
 ┃ ┣ 📂 security/jwt – JWT 필터, 프로바이더
 ┃ ┣ 📂 swagger – Swagger 설정
 ┃ ┗ 📂 ui – 공통 응답 포맷
 ┣ 📂 todo
 ┃ ┣ 📂 application – TodoService, DTO, Repository 인터페이스
 ┃ ┣ 📂 domain – Todo, Category, Content 등 도메인 모델
 ┃ ┣ 📂 repository – JPA 구현체, Entity, 커스텀 쿼리
 ┃ ┗ 📂 ui – Controller, 응답 DTO
 ┗ 📂 user
   ┣ 📂 application – UserService, Login/Signup DTO
   ┣ 📂 domain – User, UserInfo, Password 값 객체
   ┣ 📂 repository – UserRepository 구현 및 Entity, JPA
   ┣ 📂 security – CustomUserDetail, Service
   ┗ 📂 ui – UserController
``` 

<details>
<summary>📁 전체 프로젝트 구조 펼치기</summary>

``` plaintext
📁 src
 ┗ 📁 main
    ┗ 📁 java
       ┗ 📁 com.team2.TodoList
          ┣ 📂 common
          ┃  ┣ 📂 domain
          ┃  ┃  ┣ 📄 PositiveIntegerCount.java
          ┃  ┃  ┗ 📂 exception
          ┃  ┃     ┗ 📄 ErrorCode.java
          ┃  ┣ 📂 security
          ┃  ┃  ┣ 📄 SecurityConfig.java
          ┃  ┃  ┗ 📂 jwt
          ┃  ┃     ┣ 📄 JwtAuthenticationFilter.java
          ┃  ┃     ┗ 📄 JwtProvider.java
          ┃  ┣ 📂 swagger
          ┃  ┃  ┗ 📄 SwaggerConfig.java
          ┃  ┗ 📂 ui
          ┃     ┗ 📄 Response.java
          ┣ 📂 todo
          ┃  ┣ 📂 application
          ┃  ┃  ┣ 📄 LikeService.java
          ┃  ┃  ┣ 📄 TodoService.java
          ┃  ┃  ┣ 📂 dto
          ┃  ┃     ┣ 📄 CreateTodoRequestDto.java
          ┃  ┃     ┗ 📄 UpdateTodoRequestDto.java
          ┃  ┃  ┗ 📂 interface
          ┃  ┃     ┣ 📄 LikeRepository.java
          ┃  ┃     ┗ 📄 TodoRepository.java          
          ┃  ┣ 📂 domain
          ┃  ┃  ┣ 📄 Todo.java
          ┃  ┃  ┣ 📄 TodoCategory.java
          ┃  ┃  ┣ 📂 common
          ┃  ┃     ┗ 📄 DateTimeInfo.java
          ┃  ┃  ┗ 📂 content
          ┃  ┃     ┣ 📄 Content.java
          ┃  ┃     ┗ 📄 TodoContent.java
          ┃  ┣ 📂 repository
          ┃  ┃  ┣ 📄 LikeRepositoryImpl.java
          ┃  ┃  ┣ 📄 TodoRepositoryImpl.java
          ┃  ┃  ┣ 📂 entity
          ┃  ┃     ┗ 📄 DateTimeInfo.java
          ┃  ┃  ┗ 📂 jpa
          ┃  ┃     ┣ 📄 JpaLikeRepository.java
          ┃  ┃     ┗ 📄 JpaTodoRepository.java
          ┃  ┗ 📂 ui
          ┃     ┣ 📄 LikeController.java
          ┃     ┣ 📄 TodoController.java
          ┃     ┗ 📂 dto
          ┃        ┗ 📄 GetTodoResponseDto.java
          ┗ 📂 user
             ┣ 📂 application
             ┃  ┣ 📄 UserService.java
             ┃  ┣ 📂 dto
             ┃     ┣ 📄 LoginRequestDto.java
             ┃     ┗ 📄 SignupRequestDto.java
             ┃  ┗ 📂 interface
             ┃     ┗ 📄 UserRepository.java          
             ┣ 📂 domain
             ┃  ┣ 📄 Password.java
             ┃  ┣ 📄 User.java
             ┃  ┗ 📄 UserInfo.java        
             ┣ 📂 repository
             ┃  ┣ 📄 UserRepositoryImpl.java
             ┃  ┣ 📂 entity
             ┃  ┃  ┣ 📄 UserEntity.java
             ┃  ┃  ┗ 📄 UserInfoEntity.java
             ┃  ┗ 📂 jpa
             ┃     ┗ 📄 JpaUserRepository.java
             ┣ 📂 security
             ┃  ┣ 📄 CustomUserDetail.java
             ┃  ┗ 📄 CustomUserDetailService.java            
             ┗ 📂 ui
                ┗  📄 UserController.java
```
</details>

## 트러블 슈팅
- **EC2 프리티어의 리소스 한계로 인한 서버 다운**
  - Dokcer + Spring Boot + MySQl을 EC2 하나에 올리자 메모리 사용량이 약 90%까지 상승하여 서버가 자주 중단돼서 프론트와의 API 연결에 어려움이있었음
  - 해결 방안으로 MySQL을 Amazon RDS로 분리하여 배포, EC2의 부하를 줄였고 메모리 사용량이 약 60%로 안정화됨
  - 프리티어 사용 경험을 통해 **서비스 안정성 확보의 중요성**을 체감함

## 담당 열할(백엔드)
- JWT 로그인 / 회원가입 구현 (Spring Security + JWT)
- Todo CRUD 기능 전반 구현 (좋아요 포함)
- Docker Compose 환경 구성, EC2 + RDS 배포
- Swagger API 문서 자동화 작성
- 패키지 구조 및 계층 설계 (DDD 스타일 기반 설계와 SOLID 원칙 준수)
