# msa_toy
 :eyes: Apply MSA for backend project(eureka-server/client, Spring security)
 
 진행 단계
 - auth 리팩토링
 - MSA 구조 잡기  
 ![image](https://user-images.githubusercontent.com/90509229/146050903-03fe84de-69ae-40a1-869e-ca747ce24b93.png)  

 - api 명세 문서 띄울 서버 구축(완) [api 명세](https://celebrity-apidoc.herokuapp.com/)
 
 개발 고민 포인트  
 1. 도메인 쪼개기(어느 단위로?)  
 - 회원가입, 로그인, 아이디 찾기, 비밀번호 찾기, 아이디 중복체크 등은 모두 동일 도메인인가?
 2. Mybatis vs JPA  
 - Mybatis : 쿼리에 익숙한 사람들 사이에서 쓰이기 좋음. 프로젝트에 새로 들어온 사람도 쿼리만 익숙하다면 쉽게 협업이 가능 / 객체지향 언어와 관계형 데이터베이스 패러다임의 불일치
 - JPA : 패러다임 일치, 관계형 데이터베이스 외에 다른 저장소로 쉽게 교체하기에 용이(Spring Data JPA 내부에서 구현체 매핑 지원)
 3. test code 작성  
 - MSA) gw에서 인증을 담당하고 서비스 a에서는 a와 관련된 로직만 처리한다고 할 때, 서비스 a의 테스트코드는 gw 부분까지 포함되어야 하는가?
