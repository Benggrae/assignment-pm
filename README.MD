# 이벤트 수집 과제

## 1. 개요 
> 수집한 개인정보를 전달 받을 API 서비스 구현 
* 수집 정보
- 이름
- 이메일
- 국가코드+전화번호
- 개인정보 수집 동의 여부 

## 2. 기능 목록 
- 개인 정보 수집 
- 개인 정보 조회
- 오늘 수집한 정보 조회

## 3. Article 
* <img width="1392" alt="image" src="https://user-images.githubusercontent.com/26699273/174006756-c4f3194f-312e-4c39-8f86-c4b1fc8dbde3.png">. 

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
    
### 3.3 개발 언어 및 프레임 워크
  * JAVA (11)
  * Spring boot (2.7)
  * Gradle (7.4)


### 3.4 프로그램 환경
> 로컬에서도 실 AWS 서비스를 생성안하고 개발하겠끔 개발환경을 구성하였습니다.
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
 

## 배포 방법
* master branch push
```bash
  git push origin master
```










