# NeRF를 이용한 중고시장 플랫폼 개발

2023년 전기 졸업과제 17조 에듀윌의 **NeRF를 이용한 중고시장 플랫폼 개발**입니다.

<br>

## 1. 프로젝트 소개

### 과제 배경

- 중고 거래 시장이 커지면서, 4년 전에 비해 중고 거래 분쟁의 수가 8배 이상 증가했다.
- 제품의 사진만으로 파악했던 상태를 3D 모델을 통해 확인한다면, 분쟁의 수를 줄일 수 있을 것이다.

### 과제 목표

- **NeRF**를 사용하여 제품의 3D 모델을 AR을 통해 확인하도록 한다.
- 3D로 변환하는 과정을 서버를 통해 **자동화**한다.

<br>

## 2. 팀 소개

| 이름                                                                                                                                     | 이메일             | 역할                                                                                                   |
| ---------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ------------------------------------------------------------------------------------------------------ |
| <img src="https://github.com/aglra3.png" width="100"> <br> <div align="center"> [은승우](https://github.com/aglra3) </div>               | aglra3@pusan.ac.kr | - Unity AR Core 개발 <br>- Mesh 전처리<br>- Occlusion/택스쳐 개발                                      |
| <img src="https://github.com/JaesubKwon.png" width="100"> <br> <div align="center">[권재섭](https://github.com/JaesubKwon) </div>        | suba0507@gmail.com | - shell script를 통한 nerfstudio 자동화 <br>- spring boot를 사용해 서버 개발 <br>- 서버에 MariaDB 연동 |
| <img src="https://github.com/SeonghyeonKim.png" width="100"> <br> <div align="center"> [김성현](https://github.com/SeonghyeonKim) </div> | kkgojn@gmail.com   | - 안드로이드 프런트 개발 <br>- 안드로이드/유니티 연동 <br>- 입력 데이터 관리                           |

<br>

## 3. 구성도

### 구조

![구조_단순화](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/6b6aac58-7392-406b-9299-57760340d941)

<br>

### 판매자 로직

![판매자 로직](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/092c52d7-e3e9-431e-ae17-b4a884aec510)

<br>

### 구매자 로직

![구매자 로직](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/40d202cb-e6f2-47e2-b707-7cf18ce3fc8f)

<br>

## 4. 소개 및 시연 영상

### 추가 예정

<br>

## 5. 프로젝트 구조

<br>

## 6. 사용법

<details>
<summary>서버</summary>
<div>

### <<필요 소프트웨어>>

Docker and nvidia GPU drivers, capable of working with CUDA 11.8, must be installed.

### <<docker image를 사용해 server+nerfstudio docker container 생성>>

docker pull jaesubkwon/nerfserver:1.0.1

docker run --gpus all -v C:\d\nerfserver\data:/workspace/ ^
-v C:\d\nerfserver\.cache:/home/user/.cahce/ ^
-p 3389:3389 ^
--name nerfserver -it jaesubkwon/nerfserver:1.0.1

### <<docker image를 사용해 MariaDB docker container 생성>>

docker pull mariadb

docker run --name mariadb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mariadb

### <<docker network container 생성 후, 해당 network container에 'nerfserver', 'mariadb' container를 연결>>

docker network create internalNetwork

docker network connect internalNetwork nerfserver

docker network connect internalNetwork mariadb

### <<MariaDB에 DB 생성 후 table 생성>>

이름이 'nerf' 인 DB 생성.
아래 쿼리를 사용해 'nerf' DB에 테이블 'post', 'meshInfo' 생성.

```
CREATE TABLE `post` (
    `id`				bigint(20)    NOT NULL AUTO_INCREMENT,
    `userId`			bigint(20)    NOT NULL,
    `title`				varchar(3000) NOT NULL,
    `content`			varchar(3000) NOT NULL,
    `price`				bigint(20)    NOT NULL,
    `date`				datetime	  NOT NULL,
    `numberOfImages`		bigint(20)    NOT NULL,
    `state`				varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `meshInfo` (
    `id`		bigint(20)    NOT NULL,
    `xSize`		double,
    `ySize`		double,
    `zSize`		double,
    PRIMARY KEY (`id`)
);
```

### <<서버 실행 준비>>

서버파일 빌드 후, 생성된 .jar 파일을 'nerfserver' container의 /workspace/ 에 위치시키기.
test.sh 파일을 'nerfserver' container의 /workspace/ 에 위치시키기.

### <<'nerfserver' container에서 .jar 파일을 통해 서버 실행>>

예시: java -jar server.jar

</div>
</details>

<details>
<summary>프론트엔드</summary>
<div>

### 1. Android Stduio 설치하기

### 2. Unity 설치하기

### 3.

### 4.

</div>
</details>
