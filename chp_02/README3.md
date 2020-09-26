# 04 여러 화면 간 전환하기
20200921-20200926

안드로이드 앱의 기본 구조는 **화면**과 **레이아웃**이다. 화면은 **액티비티**로 만든다. 그 위에 XML 파일로 작성한 **레이아웃**을 올리고 **소스코드**를 이용하여 **이벤트**를 처리한다.

- [04 여러 화면 간 전환하기](#04-여러-화면-간-전환하기)
  - [레이아웃 인플레이션 이해하기](#레이아웃-인플레이션-이해하기)
    - [부분 레이아웃(sub1.xml)의 내용을 메모리에 객체화하기](#부분-레이아웃sub1xml의-내용을-메모리에-객체화하기)
  - [여러 화면 만들고 화면 간 전환하기](#여러-화면-만들고-화면-간-전환하기)
    - [액티비티를 새로 만들어 추가하고 서로 간에 상태 코드나 데이터를 주고 받는 방법](#액티비티를-새로-만들어-추가하고-서로-간에-상태-코드나-데이터를-주고-받는-방법)
  - [인텐트 살펴보기](#인텐트-살펴보기)
    - [Category](#category)
    - [Type](#type)
    - [Component](#component)
    - [Extra Data](#extra-data)
  - [플래그와 부가 데이터 사용하기](#플래그와-부가-데이터-사용하기)
    - [부가 액티비티](#부가-액티비티)
  - [태스크 관리 이해하기](#태스크-관리-이해하기)
  - [액티비티의 수명주기와 SharedPreferences 이해하기](#액티비티의-수명주기와-sharedpreferences-이해하기)

## 레이아웃 인플레이션 이해하기
`setContentView()`메서드가 XML 레이아웃 파일을 연결한다</br>
--> 입력인자로는 `R.layout.레이아웃파일명`으로 지정해줘야 한다. (R : 프로젝트 창에 보이는 res 폴더)
* inflation(인플레이션) : XML 레이아웃의 내용이 메모리에 객체화되는 과정

```java
button.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        Toast.makeText(getApplicationContext(), "버튼이 눌렸어요", Toast.LENGTH_LONG).show();
    }
});
```
거의 다 외워간다!<br>
`setContentView(R.layout.activity_main);` -> 이게 항상 `super.onCreate(saveInstanceState);` 밑에 있는 이유는 소스 코드에서 레이아웃을 객체화하기 전에 레이아웃 버튼을 참조하는 일을 없애기 위함.(널 포인터 예외 문제)

`setContentView`는 액티비티 화면 전체(메인 레이아웃)를 설정하는 역할만 수행하기 때문에 부분 레이아웃을 메모리에 객체화하려면 인플레이터를 사용해야 한다.

그래서 안드로이드는 시스템 서비스로 LayoutInflater을 제공한다. --> `getSystemService(Context.LAYOUT_INFLATER_SERVICE)`


```java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sub1, container, true);
        //container = findViewById(R.id.container);
        CheckBox checkBox = container.findViewById(R.id.checkBox);
        checkBox.setText("로딩되었어요.");
    }
});
```

### 부분 레이아웃(sub1.xml)의 내용을 메모리에 객체화하기
`getSystemService()` 메서드를 통해 LayoutInflater 객체를 참조한다. 참조한 인플레이터의 `inflate()` 메서드의 파라미터로 `R.layout.sub1`,`R.layout.container` 객체를 전달한다. 이를 통해 container을 id로 갖는 리니어 레이아웃 객체에 `sub1.xml` 파일의 레이아웃을 설정하면서 부분 레이아웃(sub1.xml)에 정의된 뷰들이 메모리에 로딩되며 객체화 과정을 거치게 된다.

이 과정을 통해 객체화되면 부분 레이아웃에 들어있던 텍스트뷰와 체크 박스를 `findViewById()`메서드로 참조할 수 있다. 다만 부분 레이아웃이 container 객체에 설정되어 있으므로 접근할 때 container을 거쳐야 한다 -> `container.findViewById()`

## 여러 화면 만들고 화면 간 전환하기
* 안드로이드 앱
  * 액티비티; Activity
  * 서비스; Service
  * 브로드캐스트 수신자; Broadcast Receiver
  * 내용 제공자; Content Provider

액티비티에서 새 액티비티를 띄우기만 하는 것은 `startActivity()` 메서드로 구현해도 상관 없다. 하지만 새 액티비티에서 원래의 액티비티로 돌아오면서 새 액티비티의 응답을 받아 처리하는 경우 어떤 액티비티로 돌아온 응답인지 구분해야 응답을 처리할 수 있기 때문에 정수로 된 requestCode를 받는 `startActivityForResult(Intent intent, int requestCode)` 함수를 쓴다.

* android:label -> 화면의 제목을 설정할 때
* android:theme -> 테마를 설정할 때

```java
button.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("name", "mike");
        setResult(RESULT_OK, intent);
        finish();
    }
});
```

인텐트 객체를 생성하고 name의 값을 부가 데이터로 넣음. 그러고 `RESULT_OK`의 응답을 보냄. 마지막으로 현재 액티비티 없애는 것까지.

```java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MENU);
    }
});
```

인텐트 객체는 액티비티를 띄울 목적으로 사용되며 액티비티 간에 데이터를 전달하는 데에도 사용될 수 있다. 첫번째 파마리터로는 context 객체가 전달되는데 액티비티 객체는 컨텍스트가 될 수 있기 때문에 일반적으로 `this`변수를 사용할 수 잇으나 이벤트 처리 메서드 안에서는 `this`로 참조할 수 없으므로 `getApplicationContext()` 메서드를 사용해 앱의 Context 객체를 참조한 후 전달한다.

### 액티비티를 새로 만들어 추가하고 서로 간에 상태 코드나 데이터를 주고 받는 방법
1. 새로운 액티비티 만들기
2. 새로운 액티비티의 XML 레이아웃 정의하기
3. 메인 액티비티에서 새로운 액티비티 씌우기 -> `startActivityForResult()`로 새로운 액티비티를 띄운다
4. 새로운 액티비티에서 응답 보내기 -> `setResult()`메서드로 응답을 보낸다
5. 응답 처리하기 -> `onActivityResult()`를 **재정의**하여 새로 띄웠던 액티비티에서 보내오는 응답을 처리한다.
   
## 인텐트 살펴보기
인텐트는 앱 구성 요소간에 작업 수행을 위한 정보를 전달하는 역할을 한다. `인텐트 = 액션(수행할 기능) + 데이터(액션이 수행될 대상의 데이터)` 로 묶여 있다.

* ACTION_DIAL
* ACTION_VIEW -> URI값 유형에 따라 VIEW 액션이 다른 기능을 수행함
* ACTION_EDIT

암시적 인텐트는 MIME 타입에 따라 시스템에서 적절한 다른 앱의 액티비티를 찾은 후 띄우는 방식을 사용한다.

### Category
액션이 실행되는 데 필요한 추가적인 정보를 제공한다
### Type
인텐트에 들어가는 데이터의 MIME 타입을 명시적으로 지정한다. 보통은 데이터만으로도 구별이 가능하지만 명시적으로 지정할 필요가 있는 경우도 있음
### Component
인텐트에 사용될 컴포넌트 클래스 이름을 명시적으로 지정한다. 새로운 액티비티를 정의하고 그 액티비티의 클래스 객체를 인텐트에게 전달하여 실행하는 방법도 컴포넌트를 지정하는 방식과 같다
### Extra Data
인텐트는 추가적인 정보를 넣을 수 있도록 Bundle 객체를 담고 있다. 

## 플래그와 부가 데이터 사용하기
* FLAG_ACTIVITY_SINGLE_TOP
* FLAG_ACTIVITY_NO_HISTORY -> 처음 이후에 실해된 액티비티는 액티비티 스택에 추가되지 않는다. ; 이벤트 알람 띄울 때
* FLAG_ACTIVITY_CLEAR_TOP -> 액티비티 위에 있는 다른 액티비티를 모두 종료시키게 된다. 홈 화면과 같이 다른 액티비티보다 항상 우선하는 액티비티를 만들 때 유용하다.

### 부가 액티비티

```java
public SimpleData(Parcel src) {
    number = src.readInt();
    message = src.readString();
}

public static final Creator CREATOR = new Creator() {

  public SimpleData createFromParcel(Parcel in) {
      return new SimpleData(in);
  }

  public SimpleData[] newArray(int size) {
      return new SimpleData[size];
  }
};

public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(number);
    dest.writeString(message);
}
```

## 태스크 관리 이해하기
태크스는 앱이 어떻게 동작할지 결정하는 데 사용됩니다. 즉, 태스크를 이용하면 프로세스처럼 독립적인 실행 단위와 상관없이 어떤 화면들이 같이 동작해야 하는지 흐름을 관리할 수 있다.

경우에 따라 액티비티를 띄우면서 태스크를 새로 만들도록 설정해야한다는 것.

## 액티비티의 수명주기와 SharedPreferences 이해하기
