# java-convenience-store-precourse

## 목차

1. [학습 목표](#학습-목표)
2. [과제 요구 사항](#과제-요구-사항)
3. [패키지 구조](#패키지-구조)
4. [테스트 코드](#테스트-코드)

## 학습 목표

- 관련 함수를 묶어 클래스를 만들고, 객체들이 협력하여 하나의 큰 기능을 수행하도록 한다.
- 클래스와 함수에 대한 단위 테스트를 통해 의도한 대로 정확하게 작동하는 영역을 확보한다.
- 3주 차 공통 피드백을 최대한 반영한다.
- 비공개 저장소 과제 진행 가이드를 참고하여 새로운 방식으로 과제 제출물을 제출한다.

## 과제 요구 사항

### 주의 사항

> 1. 기능을 구현하기 전 README.md에 구현할 기능 목록을 정리해 **추가**한다.
> 2. **Git의 커밋 단위**는 앞 단계에서 README.md에 정리한 **기능 목록 단위로 추가**한다.

**과제 진행 요구 사항**에 따라 첫 Commit(docs: 요구사항 명세 및 Docs 작성) 이후,<br>
후술할 기능 목록 각각을 단위로 Commit한다.

> 📝 **과제에 대한 자세한 설명은 [ASSIGNMENT.md](docs/ASSIGNMENT.md)에서 확인할 수 있습니다.**

### 📝 기능 목록

프리코스 4주차 과제는 "편의점"입니다.<br>
해당 과제의 기능을 분석한 "기능 목록"은 다음과 같습니다.<br>
기능 목록은 다음과 같이 프로젝트 마일스톤을 기반으로 나누어 작성했습니다.

**Milestone 1: 재고 확인 📦**

- [x] | 저장소로부터 `재고` 데이터 로드
- [x] | 저장소 데이터 형식 검증
- [x] | 저장소로부터 `프로모션` 데이터 로드
- [x] | `재고` 목록 출력
- [x] | 구매할 상품 이름과 수량 입력
- [x] | 입력된 정보를 `쇼핑 목록`으로 저장

**Milestone 2: 구매 시스템 🖥️**

- [x] | `쇼핑 목록`의 상품 구매
- [ ] | `영수증` 출력
- [ ] | 추가 구매 로직 구현

**Milestone 3: 멤버십&프로모션 할인 🔖**

- [ ] | 멤버십 안내메세지 출력
- [ ] | Y/N 선택 입력 처리
- [ ] | 멤버십 적용 구현
- [ ] | `프로모션`의 N+1 기능 구현
- [ ] | `프로모션` 상품 부족에 대한 로직 구현

**Milestone 4: 재고 저장📦**

- [ ] | `재고` 변동 사항을 파일에 저장
- [ ] | 통합 테스트 코드 작성

### ⚠️ 예외 처리 상세

예외 처리는 기능 요구 사항에 따라 다음 기준을 준수합니다.
> 사용자가 잘못된 값을 입력할 경우 **IllegalArgumentException**을 발생시키고,<br>
> "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 **입력을 다시 받는다.**<br>
> Exception이 아닌 IllegalArgumentException, IllegalStateException 등과 같은 **명확한 유형**을 처리한다.

#### 커스텀 Exception

- ResourceNotFoundException
    - 저장소를 찾을 수 없을 때 예외 처리를 발생시킵니다.
- ResourceReadException
    - 저장소의 데이터를 읽을 수 없을 때 예외 처리를 발생시켰습니다.
        - 본래, 저장소에서 로드된 데이터를 Factory 통해 모델로 변환하는 과정에서 검증하고자 했습니다.
        - 하지만, 모델로 변환하는 과정에서 오류가 발생했다면, 이것은 저장소에 저장된 데이터의 형식 문제입니다.
        - 저장소 데이터 각각에 대한 검증의 책임을 세분화해서 다루는 것은, 저장소 데이터를 관리하는 책임입니다.
        - 때문에, 저장소 외에서 이를 세분화해서 다루는 것은 다소 과한 책임을 부여하는 것이라 판단했습니다.
        - 이를 감안해, 통ResourceReadException 를 통해, 저장소 데이터를 읽는 과정에 문제가 있음을 표시합니다.

## 📦 패키지 구조

```
 store/
    ├── common/
    │   ├── config/
    │   ├── constant/
    │   ├── exception/
    │   └── util/
    │
    ├── controller/
    ├── domain/
    │   ├── stock/
    │   ├── promotion/
    │   └── receipt/
    │
    ├── dto/
    ├── repository/
    ├── view/
    │   ├── impl/
    │   └── interfaces/
    └── Application.java
```

### 패키지 구조에 대해 고려한 점

1. 개발 형식이 고정될 수 밖에없는 과제이지만, 유지 보수 관점도 고려해야 한다 생각합니다.
2. 하지만, 오버엔지니어링에 대해서도 고민해보았습니다.
3. 전통적인 MVC 패턴에서 Model에 대해 도메인 주도적으로 접근하고자 했습니다.
4. MVC 패턴에서 Repository가 추가됨에 따라, Controller들을 중재하는 StoreController를 두었습니다.

> 📝 **자세한 설명은 [PACKAGE_STRUCTURE.md](docs/PACKAGE_STRUCTURE.md)에서 확인할 수 있습니다.**

## 테스트 코드

> - 클래스와 함수에 대한 단위 테스트를 통해 의도한 대로 정확하게 작동하는 영역을 확보한다.
> - 3주 차 공통 피드백을 최대한 반영한다.

두 학습 목표를 달성하기 위해 피드백을 반영하여, 다음 테스트 코드 작성 기준을 준수합니다.

- 실제 클래스와 **1:1로 대응되는 테스트 클래스**를 만들어 해당 클래스의 모든 **기능과 예외 상황을 테스트**한다.<br>
- 또한, 이와 별도로 **학습 테스트 패키지를 분리**하여 프로젝트에서 사용할 기술이나 라이브러리의 동작을<br>
  이해하고 검증하기 위한 테스트 코드를 기술/라이브러리 종류별로 패키지화하여 관리한다.

이때 **프로그래밍 요구 사항 3**에 따라 UI 로직에 대한 검증은 제외됩니다.
> ⚠️ 구현한 기능에 대한 단위 테스트를 작성한다. **단, UI(System.out, System.in, Scanner) 로직은 제외한다.**

지난 과제에서는, 기능 목록의 마일스톤 마무리마다 단위 테스트 코드를 작성했습니다.<br>
이번 과제에서는, 좀 더 직관적인 피드백을 위해, 각 기능 목록에 대해 단위 테스트 코드를 함께 작성했습니다.

---
[🔝 맨 위로 돌아가기](#java-convenience-store-precourse)