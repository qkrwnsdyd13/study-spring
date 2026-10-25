증상: 8090이 아니라 8080으로 켜짐
원인: application.properties에 설정을 안 넣었음
배운 점: 설정이 없으면 스프링은 기본값을 쓴다 (포트 기본값 8080)

## 에러 2
- 증상: /h2-console 접속 시 404
- 핵심 메시지: org.springframework.web.reactive...NoResourceFoundException
- 내 추측: (직접 적어보세요)
- 확인한 것: 에러 패키지 이름에 reactive가 있음 → pom.xml 확인
- 원인: Spring Web이 아니라 Spring Reactive Web(WebFlux)을 선택함
- 해결: starter-webflux → starter-webmvc 교체
- 배운 점: 에러 메시지의 패키지 이름만 봐도 어느 기술에서 났는지 알 수 있다

## 에러 3
- 증상: 서버 시작 실패 (APPLICATION FAILED TO START)
- 핵심 메시지: The bean 'passwordConfig' ... already been defined
- 확인한 것: Description을 읽고 같은 이름의 빈이 두 번 등록됐다는 걸 파악 → PasswordConfig 열어봄
- 원인: @Bean 메서드 이름을 클래스 이름과 같은 passwordConfig로 지음
- 해결: 메서드 이름을 passwordEncoder로 변경
- 배운 점: 클래스 빈은 클래스명(첫 글자 소문자), @Bean 빈은 메서드명이 이름이 된다

## 에러 4
- 증상: 서버 시작 실패
- 핵심 메시지: No property 'exexistsByUsername' found for type 'User'
- 처음 추측: User에 데이터 값이 없어서
- 틀린 이유: 서버가 켜지는 중이라 아직 데이터를 다루기 전이었음
- 진짜 원인: Repository 메서드 이름 오타 (ex가 중복)
- 해결: existsByUsername으로 수정
- 배운 점: JPA는 메서드 이름을 해석해서 SQL을 만들기 때문에, 이름 오타가 곧 실행 에러가 된다. 에러 메시지의 이름을 "원래 의도한 이름"과 비교해야 한다

## 실험: @ManyToOne 제거
- 핵심 메시지: MappingException: Incorrect use of entity type 'User'
               (possibly due to missing association mapping annotation)
- 해석: User 엔티티를 Post 필드로 썼는데 관계 어노테이션이 없음
- 배운 점: 엔티티를 필드로 쓰면 반드시 @ManyToOne 등으로 관계를 알려줘야 한다.
           에러 위치(User)와 고칠 위치(Post)가 다를 수 있다.