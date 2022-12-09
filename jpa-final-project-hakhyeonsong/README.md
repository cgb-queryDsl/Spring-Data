# 평가 과제
데이터베이스 평가 과제였던 **"주민등록등본, 가족관계증명서, 출생신고서, 사망신고서"** 를 기반으로

## Database
- MySQL 사용

## ERD
![image](https://user-images.githubusercontent.com/60968342/206707993-b1d6e6df-848a-4a1d-98fe-dc85f93bbe0c.png)

## DDL 및 기반 데이터 등록 스크립트
제공받은 주민관리 스크립트를 mysql 서버에서 실행한 뒤 과제를 수행한다.

# 구현 요구 사항
요구사항은 다음과 같으며 수행했던 항목에 체크한다.

## data access layer 구현
- [X] entity 맵핑 - ERD 상에 표현된 컬럼들을 모두 맵핑한다
- [X] 연관관계 맵핑 - ERD 상에 표현된 relation은 모두 맵핑한다

## Repository
- [X] REST API와 웹페이지를 위한 쿼리들을 Repository로 구현한다
- 아래 기능들을 모두 이용해서 Repository를 구현해야 한다
  - [X] JpaRepository에 정의된 메서드
  - [X] Spring Data JPA Repository에서 제공하는 메서드 이름 규칙을 이용한 쿼리 생성
  - [X] @Query를 이용한 JPQL 수행
  - [X] Querydsl을 이용한 Custom Repository 구현
  - 너무 한 가지 기능에 의존해서 개발한 경우 감점한다
- [X] Dto Project 기능을 최대한 활용한다
- [X] Pageable 을 이용해 페이징 기능을 구현해야 한다


## REST API로 구현
- [X] 주민(등록/수정)
- [X] 가족관계(등록/수정/삭제)
- [X] 출생신고(등록/수정/삭제)
- [X] 사망신고(등록/수정/삭제)
- [X] 세대(등록/삭제)
- [X] 세대 전입 주소(등록/수정/삭제)


## 웹
- [X] Thymeleaf를 이용해서 HTML로 응답
- [X] 주민목록
- [X] 가족곤계증명서
- [X] 주민등록등본
- [X] 출생신고서
- [X] 사망신고서
- [X] 증명서 발급 목록
- [ ] 주민 삭제
