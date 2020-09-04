# 첫째마당 Hello! 안드로이드
20200924

### 안드로이드 이해하기
* 안드로이드는 리눅스 기반이라 새로운 하드웨어의 기능을 쉽게 연동할 수 있음
* ART라는 런타임(runtime)이 탐재되어 있음. 자바는 성능이 느리다는 고질적인 문제점을 갖고 있는데 런타임은 덕분에 성능 저하 없이 빠른 안드로이드 앱 실행이 가능함
* ABI(application binary interface) : 안드로이드 CPU칩과 시스템 사이의 상호 작용 방법을 기술한 인터페이스
* 안드로이드에서는 main함수가 아닌 `onCreate()`함수가 시작점 역할을 한다
* `setContentView()` 함수는 화면에 무엇을 보여줄 것인지 `R.layout.activity_main`은 화면 구성 요소에 대한 정보. 즉 함수 이름은 직관적이다

### 프로젝트 하나씩 바꿔보기
#### 버튼에서 발생한 클릭 이벤트를 처리할 때
1. XML 레이아웃 파일(in activity_main.xml)의 버튼에 onClick 속상 값 넣기 
2. 소스 파일(in MainActivity.java)에 이벤트 처리 함수 추가하기 

```java
public void onButton_whenClicked(View v) {
        Toast.makeText(this,"CHECK button clicked", Toast.LENGTH_LONG).show();
    }
    
public void onButton_toWebSite(View v){
    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
    startActivity(myIntent);
}

public void onButton_toMakeCall(View v){
    Intent myIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:010-0101-1010"));
    startActivity(myIntent);
    }
```
`toast`는 작고 간단한 메세지를 잠깐 보여주는 역할
`intent`는 내가 하고자 하는 행위 + 내가 하고 싶은 행위를 먼저 intent 객체 속에 넣어주고 `startActivity`안에 인자로 넣어주기