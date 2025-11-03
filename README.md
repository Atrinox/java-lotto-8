# 로또

간단한 로또 발매기 프로그램. 구매 → 당첨 입력 → 결과 집계(등수/수익률)까지 수행한다. 모든 핵심 로직은 단위 테스트(JUnit5 + AssertJ)로 검증한다.

---

## 기능 목록

* 구입 금액 입력

  * 1,000원 단위의 양의 정수만 허용
  * 1,000으로 나누어 떨어지지 않으면 예외
* 로또 발행

  * 장수 = `구입금액 / 1000`
  * 각 장은 1~45 범위의 **중복 없는 6개 숫자**
  * `Randoms.pickUniqueNumbersInRange(1, 45, 6)` 사용
  * 출력 시 번호 **오름차순** 표시
* 당첨 번호 입력

  * 쉼표(,)로 구분된 6개 정수
  * 1~45 범위, 중복 불가
* 보너스 번호 입력

  * 1개 정수
  * 1~45 범위, 당첨 번호와 중복 불가
* 당첨 결과 계산

  * 등수 판정

    * 1등: 6개 일치 / 2,000,000,000원
    * 2등: 5개 일치 + 보너스 / 30,000,000원
    * 3등: 5개 일치 / 1,500,000원
    * 4등: 4개 일치 / 50,000원
    * 5등: 3개 일치 / 5,000원
  * 등수별 개수 집계
* 수익률 계산/출력

  * 총 상금 / 구입 금액 × 100
  * 소수점 둘째 자리에서 반올림하여 한 자리까지 출력(예: 62.5%)
* 예외 처리

  * 잘못된 입력 시 `IllegalArgumentException` 발생
  * 메시지는 `"[ERROR]"`로 시작
  * 예외 발생 구간부터 재입력

---

## 설계 개요

```
/src/main/java
└─ lotto
   ├─ Application.java           // main
   ├─ controller
   │   └─ LottoController.java   // 입출력 흐름 제어
   ├─ domain
   │   ├─ Lotto.java             // 번호 6개 보유(불변)
   │   ├─ LottoTicket.java       // 한 장의 로또
   │   ├─ LottoBundle.java       // 여러 장 묶음
   │   ├─ WinningNumbers.java    // 당첨 6개 + 보너스, 검증/매칭
   │   ├─ Rank.java              // 등수, 상금, 판정 로직
   │   └─ Result.java            // 집계/총상금/수익률
   ├─ service
   │   └─ LottoMachine.java      // 난수 발행
   └─ view
       ├─ InputView.java         // Console.readLine() 래핑
       └─ OutputView.java        // 출력 포맷
```

* `Rank`는 Enum으로 구현하며 정적 팩토리 `of(int matchCount, boolean bonusMatch)`로 등수 판정
* 도메인 로직은 UI(System.in/out) 로직과 분리하여 테스트 가능하도록 구성
* 메서드는 단일 책임, 조기 반환으로 `else`/`switch` 미사용, 인덴트 depth ≤ 2, 메서드 길이 ≤ 15라인 준수

---

## 입출력 형식

### 입력

1. 구입 금액(예: `8000`)
2. 당첨 번호(쉼표 구분 6개, 예: `1,2,3,4,5,6`)
3. 보너스 번호(예: `7`)

### 출력 예시

```
8개를 구매했습니다.
[8, 21, 23, 41, 42, 43]
[3, 5, 11, 16, 32, 38]
[7, 11, 16, 35, 36, 44]
[1, 8, 11, 31, 41, 42]
[13, 14, 16, 38, 42, 45]
[7, 11, 30, 40, 42, 43]
[2, 13, 22, 32, 38, 45]
[1, 3, 5, 14, 22, 45]

당첨 통계
---
3개 일치 (5,000원) - 1개
4개 일치 (50,000원) - 0개
5개 일치 (1,500,000원) - 0개
5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
6개 일치 (2,000,000,000원) - 0개
총 수익률은 62.5%입니다.
```

---

## 예외 및 검증 규칙

* 모든 예외 메시지는 `"[ERROR] "`로 시작
* 구입 금액: 1,000원 단위 아님/0 또는 음수 → 예외
* 당첨 번호: 개수 6 아님/숫자 아님/범위(1~45) 위반/중복 → 예외
* 보너스 번호: 숫자 아님/범위(1~45) 위반/당첨 번호와 중복 → 예외
* 예외 발생 시 해당 입력부터 재요청

---

## 프로그래밍 요구사항 준수

* JDK 21
* 시작점: `Application.main()`
* `build.gradle` 변경 불가, 외부 라이브러리 추가 금지
* `System.exit()` 호출 금지
* 패키지/파일명 변경 금지(명시된 경우 제외)
* Java Style Guide 준수
* 인덴트 depth ≤ 2, 메서드 길이 ≤ 15라인, 삼항 연산자 금지, `else`/`switch` 금지
* Enum 활용
* camp.nextstep.edu.missionutils 사용

  * 난수: `Randoms.pickUniqueNumbersInRange(1, 45, 6)`
  * 입력: `Console.readLine()`
* UI 로직을 제외한 단위 테스트를 JUnit5 + AssertJ로 작성

---

## 테스트

* Java 버전 확인

  ```
  java -version
  ```
* 테스트 실행

  * Mac/Linux

    ```
    ./gradlew clean test
    ```
  * Windows

    ```
    gradlew.bat clean test
    ```
* 성공 메시지 예시

  ```
  BUILD SUCCESSFUL in 0s
  ```

---

## 실행

```
./gradlew run
```

---

## 커밋 컨벤션(AngularJS)

* 타입: `feat`, `fix`, `refactor`, `test`, `docs`, `style`, `perf`, `chore`
* 예시

  ```
  feat: 로또 발행 도메인 추가 (LottoMachine, LottoTicket)
  fix: 보너스 번호 중복 검증 추가
  test: Rank 판정 및 수익률 계산 테스트
  refactor: Result의 집계 로직 메서드 분리
  docs: README 입출력 형식 보강
  ```

---

 둘째 자리에서 반올림하여 한 자리까지 출력하도록 서식화한다.
