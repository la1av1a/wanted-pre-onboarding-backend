지원자 : 이현재

애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)<p>
Endpoint : http://ec2-54-180-35-193.ap-northeast-2.compute.amazonaws.com:8080/

또는 
```
 ./gradlew build && java -jar build/libs/board-0.0.1-SNAPSHOT.jar 
```

<h2>API 명세(request/response 포함)</h2>

http://ec2-54-180-35-193.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html

<h2>데이터베이스 테이블 구조</h2>
<img src=https://velog.velcdn.com/images/rossy029/post/39001619-cb70-449b-bcd7-6632c7f79392/image.png>

<h2>구현한 API의 동작을 촬영한 데모 영상 링크</h2>
https://youtu.be/6XT7HJsCzWI

<h2>구현 방법 및 이유에 대한 간략한 설명</h2>

<h3>과제 1. 사용자 회원가입 엔드포인트</h3>
@RequestBody로 Json을 응답받아 DTO에 @Pattern 어노테이션을 달아, 이메일, 비밀번호에 정규식으로 유효성 검사를 진행하였습니다.
일치하지 않을시 전역으로 Exception을 관리하는 클래스에서 BadRequest 상태코드로 응답하게 구현하여 한 클래스에서 예외를 관리할 수 있게 하였습니다.

해당 email이 존재할시 409 Conflict 상태코드로 응답하게 구현하였으며
스프링 시큐리티에서 제공하는 BCryptPasswordEncoder를 사용하여 비밀번호를 암호화하여 저장하였습니다.

<h3>과제 2. 사용자 로그인 엔드포인트</h3>
사용자가 입력한 email으로 계정을 조회해서 계정이 존재하는지 확인하고, 암호화된 비밀번호와 입력받은 평문 비밀번호를 비교하여 불일치시 예외를 발생시켰습니다.
로그인 성공시 JWT 토큰을 발급하여 응답하였습니다.

유효성은 회원가입과 동일하게 체크하였습니다.

매 요청시 발급받은 JWT 토큰을 Authorization 헤더에 담아서 요청하게 하였으며, 토큰이 유효하지 않을시 예외를 발생시켰습니다.
한 번의 요청마다 한 번 실행하는 OnceperRequestFilter를 구현해 JWT 토큰을 검증하였습니다.

유효한 토큰일경우 UsernamePasswordAuthenticationFilter를 통해 SecurityContextHolder에 인증정보를 저장하였습니다.
토큰 정보는 UserDetails를 구현한 CustomUserDetails를 통해 저장하여 개발자가 @AuthorizationPrincipal 어노테이션을 통해 사용자 정보를 가져올 수 있게 하였습니다.

repository 단위테스트와 통합테스트를 진행하여 동작을 보증하였습니다.

<h3>과제 3. 새로운 게시글을 생성하는 엔드포인트</h3>

SecurityFilter로 로그인된 사용자만 접근할 수 있게 하였습니다.<br>
POST 요청 시 @AuthorizationPrincipal 어노테이션을 통해 CustomUserDetails에서 사용자 정보를 가져와 게시글 작성자로 저장하였습니다.

repository 단위테스트와 통합테스트를 진행하여 동작을 보증하였습니다.

<h3>과제 4. 게시글 목록을 조회하는 엔드포인트</h3>

Pageable을 통해 페이징 처리를 하였습니다.<br>
size, offset 파라미터를 통해 페이징 처리를 하였으며, size, offset 파라미터가 없을시 기본값을 설정하였습니다.<br>
보통 최신 글부터 보길 선호하기 때문에 게시글 id를 기준으로 내림차순 정렬하였습니다.

<h3>과제 5. 특정 게시글을 조회하는 엔드포인트</h3>
PathVariable을 통해 게시글 id를 받아서 조회하였습니다.<br>

repository 단위테스트와 통합테스트를 진행하여 동작을 보증하였습니다.
단위테스트시 인증정보는 UserDetailsService를 구현한 CustomUserDetailsService를 통해 가져오는 @WithMockUser를 사용하였습니다.

<h3>과제 6. 특정 게시글을 수정하는 엔드포인트</h3>

마찬가지로, SecurityFitler로 로그인된 사용자만 본인 게시글에 접근할 수 있게 하였습니다.<br>
PathVariable을 통해 Patch 메소드로 게시글 id를 받아서 조회하였으며 수정할 내용을 @RequestParam으로 받아서 수정하였습니다.<br>
수정할 내용이 없을시 UNPROCESSABLE_ENTITY 상태코드로 응답하게 구현하였습니다.

repository 단위테스트와 통합테스트를 진행하여 동작을 보증하였습니다.

<h3>과제 7. 특정 게시글을 삭제하는 엔드포인트</h3>
SecurityFitler로 로그인된 사용자만 본인 게시글에 대해서만 요청할 수 있게 하였습니다.<br>
PathVariable을 통해 게시글 id를 받아서 삭제하였습니다.

repository 단위테스트와 통합테스트를 진행하여 동작을 보증하였습니다.

