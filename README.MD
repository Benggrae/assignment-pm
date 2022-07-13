# 이벤트 수집 과제

## 1. 개요 
> 수집한 개인정보를 전달 받을 API 서비스 구현 
* 수집 정보
- 이름
- 이메일
- 국가코드+전화번호
- 개인정보 수집 동의 여부 

## 2. 기능 목록 
### 개인 정보 수집 
  * API 명세서  
    - Method : POST 
    - URI : /api/v1/collection/member
    - Content-Type: application/json
    - Request Body 
       - |필드명|내용|예시|제약조건|
          |------|---|---|---|
          |email  |이메일주소     |kbh0055@naver.com|공백이 올 수 없음|
          |hp     |전화번호       |+8201055550440|공백이 올 수 없음|
          |name   |이름          |kim|공백이 올 수 없음|
          |agreeYn|개인수집동의여부 | Y| Y,N만 가능 Y일때만 실제 DB에 수집|
       - 
    - Response
      - 성공시 201 코드 반환 : Header location 값에 저장된 값 조회할 수 있는 API 주소 반환
      - 실패시 body 값에 error message 반환
      
    * 예시
    ```http request
       POST http://elb-ecs-832940157.ap-northeast-2.elb.amazonaws.com/api/v1/collection/member
       
       Content-Type: application/json
    
      {
            "email" : "kbh0055@naver.com",
            "hp" : "+8201055550440",
            "name" :"kim",
            "agreeYn" : "Y"
      }
    ```
    
    * 성공 
    ```text
        header: Location: /api/v1/collection/members/4a643867-6c5f-47ec-82f5-4e86e9796b62
    ```
      
    * 실패
    ```json
      {
        message:"오류입니다."
      }
    ```
    
    

### 개인 정보 조회
  * API 명세서
    - Method : GET
    - URI : /api/v1/collection/members/{memberId}
    - Params
        - |필드명|내용|예시|제약조건|
           |------|---|---|---|
           |memberId  |유저ID |kbh0055@naver.com|공백이 올 수 없음|
      
    - Response
        - 성공시 200 코드 반환 및 사용자 정보 반환
        - 실패시 body 값에 error message 반환

    * 예시
    ```http request
       GET /api/v1/collection/members/4a643867-6c5f-47ec-82f5-4e86e9796b62
    ```

    * 성공
    ```json
    {
     "id": "4a643867-6c5f-47ec-82f5-4e86e9796b62",
     "name": "kim",
     "email": "kbh0551@naver.com",
     "hp": "+82010525250440"
    }
    ```

    * 실패
    ```json
      {
        message:"오류입니다."
      }
    ```

### 오늘 수집한 정보 조회
 * API 명세서
   - Method : GET
   - URI : /api/v1/collection/members/today


   - Response
       - 성공시 200 코드 반환
       - 유저 정보들을 반환
       - 실패시 body 값에 error message 반환

   * 예시
    ```http request
       GET /api/v1/collection/members/today
    ```

    * 성공
    ```json
    [{
      "id": "4a643867-6c5f-47ec-82f5-4e86e9796b62",
      "name": "kim",
      "email": "kbh0551@naver.com",
      "hp": "+82010525250440"
      },
      {
      "id": "f00ecbe3-cc88-450e-9fb8-eb2aee44f0b4",
      "name": "kim",
      "email": "kbh055@naver.com",
      "hp": "+82010525550440"
      }
    ]
    ```

    * 실패
    ```json
    {
      message:"오류입니다."
    }
    ```

## 3. Article 
 <img width="1392" alt="image" src="https://user-images.githubusercontent.com/26699273/174006756-c4f3194f-312e-4c39-8f86-c4b1fc8dbde3.png">

### 3.1 AWS 서비스 목록
  * ECS 클러스터  
    * 오토스케일링의 강점과 리소스를 효율적으로 쓸 수 있는 EC2보다 ECS의 강점이 있어 ECS를 선택하였습니다.  
    프리티어 제약 조건으로 EC2 단일 서버로 현재는 운영환경에 배포 되어 있습니다. 실서비스 단계에서는 특정 기간에 트래픽이 몰릴것으로 예상되어 Fargate로 선택하는 것이 좋아보입니다.
  * DynamoDB
    * RDS 관계형 DB보다 읽기, 쓰기 능력이 우월하고 많은 트래픽을 받기 위한 이번 과제의 적합하여 선택 하였습니다.
  * ElastiCache
    * DynamoDB의 읽기에 대한 비용이 있어 이 부분의 비용 절감과 조회 영역으 트래픽 분산을 위해 ElastiCache를 두어 데이터를 케시하여 보완하였습니다.

### 3.2 CI / CD
  1. Github(Master Branch Push)
  2. Gihub Action 
   1. 배포할 이미지 생성
   2. ECR 이미지 푸쉬
   3. ECS 배포로 작업 정의로 작업 생성
   4. ECS -> EC2 배포 

 ![image](https://user-images.githubusercontent.com/26699273/174077714-3a922cac-1dfa-42c9-9f19-e0b504325d00.png)

    
### 3.3 개발 언어 및 프레임 워크
  * JAVA (11)
  * Spring boot (2.7)
  * Gradle (7.4)


### 3.4 프로그램 환경
> 로컬에서도 실 AWS 서비스를 생성하지 않고 개발할 수 있도록 환경을 구성하였습니다
  - 개발(로컬) : localStack (도커 이미지 - DynamoDB), 임베디드 Redis 
  - 테스트 : 임베디드 Dynamo, 임베디드 Redis
  - 로컬 QA : localStack (도커이미지 - DynamDB, Redis)
  - 운영 : ECS, ECR, Dynamo, Elastic Chache

--- 

## 4. 개발환경 구동 방법
 * 로컬 개발 
  ```bash
     docker-compose -f /Users/Simple/Documents/study/next/assignment-pm/docker-compose.yml up -d localstack
  ```
  후 개발 진행 
  
 * 로컬 Q/A
  ```bash
     docker-compose up
  ```
 

## 5. 배포 방법
* master branch push
```bash
  git push origin master
```










