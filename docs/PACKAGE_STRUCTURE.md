# 📦 패키지 별 역할

## 패키지 구조에 대해 고려한 점

1. 개발 형식이 고정될 수 밖에없는 과제이지만, 유지 보수 관점도 고려해야 한다 생각합니다.

    - 개발에서 가장 힘들게 하는 것이 유지 보수라 생각하기에, 확장성을 고려했습니다.


2. 하지만, YAGNI (You Aren't Gonna Need It) 원칙, 오버엔지니어링에 대해서도 고민해보았습니다.

    - 인터페이스 적용은 캡슐화의 장점을 가집니다.<br>
      하지만, 인터페이스과 구현체가 1:1로 구성되면 오히려 복잡도만 높힌다는 피드백이 있었습니다.<br>
      때문에, 이러한 경우엔 다음과 같은 근거가 있는 경우에만 적용했습니다.<br>
      (ex. View는 외부 시스템과의 연관되기 때문에 인터페이스를 도입했습니다.)


3. 전통적인 MVC 패턴을 기반하되, Model에 대해 도메인 주도적으로 접근하고자 했습니다.
    - 과제 학습 목표를 수행하기 위해, 세분화된 도메인마다 관심있는 작업을 스스로 처리하는 객체로 구성합니다.
    - 도메인 패키지 내 각각은 해당 클래스에 관심있는 내용만 다루도록 직관적으로 책임을 분리합니다.


4. MVC 패턴에서 Repository가 추가됨에 따라, Controller들을 중재하는 StoreController를 두었습니다.
    - Repository, View, Model을 모두 조율하는 것은 하나의 Controller에서 너무 큰 책임이라 판단했습니다.
    - 하지만, 별도의 서비스를 추가하기엔 분리할 비즈니스 로직이 없었습니다.
    - 때문에, StoreController를 두어, 나머지 두 컨트롤러를 중재하도록 설계하였습니다.

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

각 패키지 별 역할과 특징은 다음과 같습니다.

## 🗂️ Common/

- 프로젝트 전반에서 사용되는 클래스의 집합입니다.
- config/DependingConfig는 의존성 주입을 관리합니다.

## 📂 Controller/

- 호출과 실행의 흐름 제어가 주된 목적으로, 비즈니스 로직을 포함하지 않습니다.
- PurchaseController는 Model과 View 사이 중재자 역할을 수행합니다.
- ResourceController는 Model과 Repository 사이 중재자 역할을 수행합니다.
- Controller들은 StoreController에 의해 제어되며, DTO로만 데이터를 주고받습니다.

## 📂 Domain/

- 비즈니스 로직의 핵심이 되는 도메인 모델들이 위치합니다.
- 각 핵심 모델들은 스스로 행위를 할 수 있는 객체로서 정의합니다.
- 모델은 정적 메소드 사용을 최소화합니다. 정적 생성기 호출이 필요한 경우에는 Factory를 사용합니다.

## 📂 DTO/

- Controller 계층 간 데이터 전송에 사용되는 클래스들이 위치합니다.
- 데이터는 전달 목적으로만 사용되어, 불변 객체인 Record 클래스로 선언합니다.

## 📂 Repository/

- resources 폴더의 md 파일에 접근해 데이터를 조회, 수정합니다.
- ~~해당 클래스들은 추후 데이터 저장소 변경 등을 고려하여, Interface를 상속받습니다.~~
    - 현재 Stcok과 Promotion의 Repository의 유사성이 크기에, 추상 클래스를 통해 레포지를 구현하여 코드 복잡성을 낮췄습니다.

## 📂 View/

- View는 사용자 인터페이스로 IO 제어 및 관리를 담당합니다.
- 이때, InputView는 Console이라는 별도의 라이브러리에 의존니다.
- OutputView는 개발 환경에 따라 변화할 여지가 있습니다.
- 따라서, 외부 시스템이 변경되더라도 핵심 비즈니스 로직에는 영향을 주지 않도록 Interface로 추상화했습니다.

---

## ⚠️ (!삭제한 패키지)Service/

- 기존 MVC 패턴에서, Repository 추가로 Controller의 책임이 더 커지는 것을 방지하기 위해 추가되었습니다.
- Repository에 대한 데이터 접근 로직을 Controller에서 분리하여 단일 책임 원칙 준수합니다.
- Controller는 요청/응답 처리에만 집중합니다.

- 이러한 구조를 그리면 다음과 같습니다.

```
User <- View <- Controller -> Model (기존 MVC 패턴)
                  |
                  v
                Service -> Repository (새롭게 추가된 계층)
```

> 분리된 Service는 비즈니스 로직을 포함하지 않았고, Repository는 이미 추상화되어 있습니다.<br>
> 때문에, Service를 제거해 복잡도를 낮추고, Repository을 담당할 ResourceController를 추가했습니다.
