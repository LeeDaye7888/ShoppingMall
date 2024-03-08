# ShoppingMall
Spring Boot로 만든 쇼핑몰 프로젝트입니다. <br>
어느 곳에서든 사용하며 접할 수 있는 인터넷 쇼핑몰들을 모티브하여 설계하였습니다.
사용자, 판매자, 관리자의 권한이 있는 쇼핑몰 API입니다.

# REST API 문서
- [ShoppingMall API 문서](https://github.com/LeeDaye7888/ShoppingMall/issues/26)

# 기술 스택
- Language: Java
- Framework: Spring Boot 2.7.16
- JDK: 17
- BuildTool: Gradle
- DB: MySQL, S3
- Server: AWS EC2
- Development Environment: IntelliJ, Postman, Github


# 기능 설명
( ▶ 를 누르면 간략한 기능 리스트가 나옵니다. )
<details>
<summary>회원</summary>
  
- Spring Security 회원가입 및 로그인
- (관리자) 회원 정보 전체 조회
  + 이메일 중복 체크
  + JWT 토큰
- (사용자) 자신의 회원 정보 조회
- 회원 정보 수정
- 회원 탈퇴
  + 전체 사용자: 회원의 장바구니 삭제, 회원의 refresh token 삭제, 권한 삭제, 리뷰 작성한 회원명을 null로 변경
  + 판매자: 사용자의 장바구니에 존재하는 판매자 판매 상품 삭제
  + 관리자: 해당 없음
- 비밀번호 변경
  
</details>

<details>
<summary>카테고리</summary>

  - 카테고리 생성(관리자)
  - 카테고리 수정(관리자)
  - 카테고리 조회(전체 사용자)
  - 카테고리 삭제(관리자)
</details>

<details>
<summary>장바구니</summary>

  - 장바구니 생성
  - 장바구니 수정
  - 회원에 해당하는 장바구니 전체 조회
  - 선택한 장바구니들 삭제
</details>

<details>
<summary>리뷰</summary>

  - 리뷰 등록
  - 리뷰 수정
  - (상품 상세조회) 리뷰 조회
  - (마이페이지) 리뷰 조회
</details>

<details>
<summary>상품</summary>

  - 상품 등록(판매자)
    + 상품 이미지는 1장 이상 필수 등록
    + 상품 이미지들은 AWS S3에 저장
    + 이미 존재하는 동일한 이름으로 상품 등록 불가
    + 상품 옵션 추가는 필수 X
      
  - 상품 수정(판매자)
    + 사이트에 이미 존재하는 상품명으로 상품 수정 불가
  - 상품 전체 조회(판매자)
  - 상품 삭제(판매자)
  - 상품 상세 조회(전체 사용자)
  - 상품 전체 조회(전체 사용자)
</details>

<details>
<summary>주문</summary>
  
  - 주문번호 생성 (UUID)
  - 주문 등록
    + 주문 수량 > 주문하려는 상품 재고 시, 주문 불가
    + 품절/판매중단인 상품 주문 불가
    + 주문 수량만큼 해당 상품 재고 감소
    + 총 주문 금액의 1% 적립금 부여
    + 주문 상품이 장바구니에 존재할 경우, 장바구니 DB에서 삭제 
  - 주문 취소(결제 취소)
    + 이미 취소한 결제 다시 취소 불가
    + 상품이 배송 중일 경우 취소 불가
    + 결제 회원과 다른 회원이 대신 결제 취소 불가
  - 주문 전체 조회
  - 주문 상세 조회
</details>


# 키워드
- REST API
- Spring Security
- HTTP 통신
- JPA
- DTO 객체화
- 페이징
- 예외처리
- 트랜잭션
- 객체지향
