# Royale (주짓수대회 자동화 서비스)

[TOC]

📆 **프로젝트 진행기간 : 2022/10/10 - 2022/11/25** 

🎈 **팀원소개**

| 이름  | 역할                 |
| --- | ------------------ |
| 조찬홍 | Backend & Frontend |
| 안세영 | Backend & Frontend |
| 민경대 | Backend & Frontend |
| 이태엽 | UCC 제작 & 서류정리|


![logo2](README.assets/logo2.png)

---

## 01 서비스 소개

> #### 주짓수 대회 자동화 서비스
> 
> "Royale"은 아날로그로 진행되면서 발생하는 불편함들을 자동화한 서비스이다.

---

## 02 주요 기능

✔ 애니메이션 추천

✔ 애니메이션에 대한 정보와 리뷰, 톡톡을 통한 소통공간

✔ 유저가 본 애니메이션을 분석하여 만든 일본어 단어장

✔ 취향이 비슷한 사람들과 소통의 공간, 오픈채팅

---

## 03 담당 기능

✔ 회원가입시 메일 인증 구현

✔ Spring Security + JWT를 활용한 회원 인증 및 관리

✔ Jenkins를 활용한 CI, CD

✔ Docker와 Docker-compose로 컨테이너화

---

## 04 "Royale" 서비스 화면

#### **회원가입 및 로그인**

✨ **회원가입**



✨ **로그인**


---

#### 메인페이지

![메인](README.assets/메인.gif)

---


## 05 개발환경

📌 **Backend**
- spring-boot 2.7.3
- spring-security 2.7.3
- docker 20.10.18
- docker-compose 2.1.0
- mysql 5.7
- nginx 1.23.1

📌 **Frontend**
- "npm": "^8.19.3",
- "react": "^18.2.0",
- "zustand": "^4.1.4"
- "socket.io": "^4.5.3",

---

## 06 서비스 아키텍처

![image-20221006111907348](README.assets/image-20221006111907348.png)

---


## 07 협업 툴

- **Gitlab**

![image-20221006112132474](README.assets/image-20221006112132474.png)

- **Notion**

![image-20221006112354591](README.assets/image-20221006112354591.png)

---

## 08 서비스 설계

- **ERD**
  
  ![img](https://cdn.discordapp.com/attachments/1012637429376753677/1027439856579776543/unknown.png)

- **기능 명세서**

![image-20221006112549162](README.assets/image-20221006112549162.png)

- **Figma**

![image-20221006112716610](README.assets/image-20221006112716610.png)

- **컴포넌트 구조 설계**

![image-20221006112759543](README.assets/image-20221006112759543.png)

- **Rest API**

![image-20221006112833140](README.assets/image-20221006112833140.png)