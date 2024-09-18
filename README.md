# Baekjoon Online Judge Plugin for IntelliJ IDEA
![Static Badge](https://img.shields.io/badge/Intellij%20IDEA-plugin-gray?style=plastic&logo=IntellijIDEA&color=%236699CC)
<a href="https://plugins.jetbrains.com/plugin/25310-wij" target="_blank" rel="noopener noreferrer">
![Static Badge](https://img.shields.io/badge/WangJun%20Intellij%20Judge-v1.0.2-good?style=plastic&color=%236699CC)
</a>
<a href="https://www.acmicpc.net/" target="_blank" rel="noopener noreferrer">
![Static Badge](https://img.shields.io/badge/BaeJoon%20Online%20Judge-%238E8E93?style=plastic)
</a>

##### 백준의 문제 코드를 작성하고 쉽게 테스트해볼 수 있는 플러그인 입니다.
- 크롤링을 통한 테스트 지원 
- 커스텀 데이터 테스트 지원
- 사용하기 쉬운 UI
- 테스트 모두 통과시 클립보드 자동 복사

<br>

## How to use?

##### [백준 Java 제출 규칙](https://help.acmicpc.net/language/info)에 맞는 코드를 작성하세요.
##### package는 추가해도 괜찮습니다!

##### Main class, main Method 옆에 Run 아이콘이 활성화 됩니다.
| Run                       | Description                        |
|:--------------------------|:-----------------------------------|
| Judge                     | 가장 최근 Update 된 Input으로 테스트를 시작합니다. |
| Update Input File & Judge | 새롭게 Input을 Update하고 테스트를 시작합니다.    |
| Delete All                | Input과 Output 파일을 모두 삭제합니다.        |
| Go to I/O Directory       | io Directory 를 엽니다.                |
| Open First File           | 첫번째 i/o 파일을 엽니다.                   |

##### Update Input & Judge 를 선택한 경우 문제 번호를 입력해주세요.
<img src="/src/main/resources/readme/run.gif" width="476" alt="Run Image"/>
<br>

##### Compile 하고 실행하여 Output 과 비교합니다.
<img src="/src/main/resources/readme/process.gif" width="500" alt="Process Image"/>
<br>

##### 모든 테스트 케이스를 통과하면 클립보드에 복사됩니다.
<img src="/src/main/resources/readme/result.gif" width="500" />
<br>

## Keymap

#### How to Set Up a Keymap?
- Intellij -> Setting -> Keymap -> Wij 검색
  
<img src="/src/main/resources/readme/keymap.png" width="500" alt="Keymap Image"/>

<br>

#### Default Keymap

##### Window
| Run                      | Keymap                        |
|:--------------------------|:-----------------------------------|
| Judge                      | Control + Alt + J    |
| Update Input File & Judge  | Control + Alt + U |
| Delete                   | Control + Alt + D |

##### Mac
| Run                      | Keymap                        |
|:--------------------------|:-----------------------------------|
| Judge                      | Control + Option + J    |
| Update Input File & Judge  | Control + Option + U |
| Delete | Control + Option + D |

<br>

## General Information

##### 플러그인은 wij Directory를 생성하고 관리합니다.
<img src="/src/main/resources/readme/folder.png" width="250" alt="Directory Arch Image"/>

| Directory                 | Description                        |
|:--------------------------|:-----------------------------------|
| class                     | compile 된 .class 파일을 저장합니다    |
| io                        | Input & Output을 저장합니다 |

<br>

##### Work Flow

<img src="/src/main/resources/readme/flow.png" width = "80%" alt="WorkFlow Image"/>
<br>

##### Crawling
- `Update Input File & Judge` 를 실행한 경우에만 실행 됩니다.
- 문제번호를 입력 받아서 Input & Output 을 `wij/io/your_java_package/` 에 저장합니다.

##### Compile 
- 실행중인 intellij 의 java 를 활용하여 진행합니다.
- `java --version`으로 확인해보세요.

##### Run
- `wij/io/your_java_package/` 의 Input & Output File 을 사용하여 실행합니다.
- 최대 10개 `input0 ~ input9`, `output0 ~ output9` 테스트 가능합니다.
- 같은 번호의 input 과 output 이 없으면 테스트를 진행하지 않습니다.

##### Judge
- `Actual` 은 실행중인 프로세스의 출력 입니다. 
- `Expected` 는 output 파일의 출력 입니다.

##### Copy to Clipboard
- 모든 테스트 케이스를 통과했을 경우 클립보드에 복사 합니다.
- package와 관련된 코드는 복사하지 않습니다.
