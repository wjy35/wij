# Baekjoon Algorithm Problem Judge Plugin for IntelliJ IDEA

---

백준의 문제 코드를 작성하고 쉽게 테스트해볼 수 있는 플러그인 입니다.
- 크롤링을 통한 테스트
- 커스텀 데이터 테스트
- 사용하기 쉬운 UI 제공
- 테스트 통과시 클립보드 복사

## How to use?

--- 

1. [백준 Java 제출 규칙](https://help.acmicpc.net/language/info)에 맞는 코드를 작성하세요.<br> 

2. Main class, main Method 옆에 Run 아이콘이 활성화 됩니다. <br>
   <img src="/src/main/resources/readme/icon.png" width="400"/>
3. 아이콘을 클릭하여 Run Option을 설정합니다. <br>
   <img src="/src/main/resources/readme/run.png" width="400"/>

- Judge: 가장 최근 Update 된 Input으로 테스트를 시작합니다.
- Update Input & Judge: 새롭게 Input을 Update하고 테스트를 시작합니다.

4. Update Input & Judge 를 선택한 경우 문제 번호를 입력해주세요.<br>
   <img src="/src/main/resources/readme/dialog.png" width="400"/>


- 해당 문제 번호를 통해 Input을 크롤링하여 root/wij/io/ 에 저장합니다.

5. Compile하고 실행하여 Output과 비교합니다. <br>
   <img src="/src/main/resources/readme/compile.png" width="400"/>

6. 모든 테스트 케이스를 통과하면 클립보드에 복사됩니다. <br>
   <img src="/src/main/resources/readme/accepted.png" width="400"/>

## How It Works?

---

