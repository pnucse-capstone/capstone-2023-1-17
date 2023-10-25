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

```
capstone-2023-1-17
 ┣ 📂docs               // 졸업과제 제출용 문서
 ┣ 📂FrontEnd           // 안드로이드 프로젝트
 ┃ ┗ 📂unity_export    // Unity export 경로
 ┣ 📂Server             // SpringBoot 프로젝트
 ┗ 📂Unity              // Unity3d 프로젝트
```

<br>

## 6. 사용법

capstone-2023-1-17 프로젝트를 클론한다.

이후 로컬 서버와 프론트엔드 부분을 시행한다.

<br>

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

<br>

</div>
</details>

<details>
<summary>프론트엔드</summary>
<div>

### 0. 의존성

**유니티**

- Unity 2022.3.4f1
- Unity XR Plugin Manager 4.4.0
- AR Foundation 5.0.7
- Google ARCore XR Plugin 5.0.7

<br>

**안드로이드**

- minSdk 24
- compileSDK 33
- targetSDK 33
- NDK 25.2.9519653
- Android Gradle plugin 7.4.2
- Volley 1.2.1
- gson 2.10.1

<br>

### 1. Unity 설정하기

- 서버 설정하기

  Assets/\_Scripts 폴더의 connection 스크립트에서
  URL 변수에 서버의 주소를 입력한다.

  ```
  public class Connection : MonoBehaviour
  {
      ...
      private string URL = "http://172.22.61.33:3389";
      ...
  }
  ```

<br>

### 2. Unity 프로젝트를 안드로이드 플랫폼으로 export 하기

- 안드로이드 플랫폼으로 변경하기

  Android로 플랫폼을 변경한 후, Switch Platform을 선택한다.

<br>

- export 하기

  export 위치는 FrontEnd의 unity_export 폴더로 설정한다.

<br>

### 3. 안드로이드 설정 변경하기

Gradle 파일이 변경되는 경우 **Sync Now**를 클릭하며 최신화해야 한다.

현재는 졸업과제 발표를 위해, 서버에 3개의 전처리된 결과를 입력한 후 시작한다.

현재 사진을 추가하는 기능은 막아놓은 상태이다.

<br>

- **xrmanifest.androidlib 주석처리하기**

  현재 프로젝트의 **Unity XR Plugin Manager**가 4.4.0 버전으로 설치되어있어, export 시 xrmanifest.androidlib가 생긴다. 이전 버전으로 설치되어 있는 경우, 파일이 없을 수 있다.

  ![의존성](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/16c6f5cf-00b8-41f8-9fb3-a1b3115406c3)

  ```
  // unityLibrary build.gradle
  dependencies {
      ...
      // implementation project('xrmanifest.androidlib')
  }
  ```

  <br>

- **NDK 경로 수정하기**

  NDK가 설치되어 있지 않다면,

  **File - Settings - Appearance & Behavior - System Settings - Android SDK**

  에서 NDK를 설치한 후,
  android.ndkDirectory를 설치된 NDK 위치로 변경한다.

  ![ndk](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/36cb62ac-62d7-4565-8637-0f3d92498e32)

  윈도우 기준, 대부분

  **"C:/Program Files/Unity/Hub/Editor/2022.3.4f1/Editor/Data/PlaybackEngines/AndroidPlayer/NDK"**

  로 변경하면 된다.

  ```
  // unityLibrary build.gradle
  def BuildIl2Cpp(String workingDir, String configuration, String architecture, String abi, String[] staticLibraries) {
      ...
      commandLineArgs.add("--tool-chain-path=" + android.ndkDirectory) // android.ndkDirectory 변경
      ...
  }
  ```

  <br>

- **unityLibrary Manifests 수정하기**

  intent-filter는 안드로이드의 진입점을 의미한다.
  unity 어플과 안드로이드 어플이 각각 설치되기를 원하지 않는다면,
  intent-filter를 삭제해야한다.

  ![캡처](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/011cfd9a-d51c-4086-82ab-d1f714704635)

  ```
  <activity android:name="com.unity3d.player.UnityPlayerActivity" android:theme="@style/UnityThemeSelector" android:screenOrientation="fullUser" android:launchMode="singleTask" android:configChanges="mcc|mnc|locale|touchscreen|keyboard|keyboardHidden|navigation|orientation|screenLayout|uiMode|screenSize|smallestScreenSize|fontScale|layoutDirection|density" android:resizeableActivity="false" android:hardwareAccelerated="false" android:exported="true">
    <!-- 삭제
    <intent-filter>
      <category android:name="android.intent.category.LAUNCHER" />
      <action android:name="android.intent.action.MAIN" />
    </intent-filter>
    -->
    <meta-data android:name="unityplayer.UnityActivity" android:value="true" />
    <meta-data android:name="notch_support" android:value="true" />
  </activity>
  ```

<br>

### 4. 빌드하기

- 서버 주소 입력하기

  app/assets/values/string.xml에 서버 주소를 입력한다.

  ![서버](https://github.com/pnucse-capstone/capstone-2023-1-17/assets/48244988/8ca4396e-a432-4527-ae72-9e74d77f952c)

현재는 로컬 주소가 입력되어 있다.

  <br>

- 빌드하기

  **Run 'app'**을 통해 바로 실행하거나 apk 파일로 추출하여 실행한다.

</div>
</details>
