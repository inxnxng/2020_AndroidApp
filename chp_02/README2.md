# 둘째마당
20200914-20200918

## 기본 위젯과 드로어블 사용하기
### 텍스트뷰
* text 속성을 반드시 지정해야 함 -> 문자열이 없으면 텍스트뷰가 차지하는 영역도 알 수 없기 때문임
  * text 속성 값으로 직접 문자열을 넣거나
  * /app/res/values 폴더에서 strings.xml 파일에 작성한 문자열을 저장 <-- 이것을 더 추천함; 다양한 언어 버전의 XML 레이아웃을 만드는 것보다 strings.xml 파일을 언어별로 만드는 것이 더 효율적!
    1. strings.xml 에서 `<string name="person_name">INK</string>`와 같이 resource안에 넣을 요소를 입력
    2. activity_main.xml로 돌아와 text 속성에 `@string/person_name` 입력
* textColor
* textSize : `sp`단위 권장
* textStyle : normal, bold, italic, bolditalic
* typeFace : 폰트
* maxLines : 표시하는 문자열의 최대 줄 수

### 버튼
버튼 작업이 가능하도록 CompoundButton class는 다음과 같은 method를 포함하고 있다
```
public boolean isChecked()
public void setChecked (boolean checked)
public void toggle ()
void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
```

### 에디트텍스트
* `android:inputType = "Text"` 로 해놓으면 **글자 키패드**가 나타난다

### 이미지뷰와 이미지 버튼
* `android:src` 또는 `app:srcCompate` 속성으로 이미지를 설정할 수 있음
* maxHeight, maxWidth
* tint : 이미지의 색상을 결정할 수 있음 like 반투명
* scaleType : 이미지 뷰의 크기에 맞게 원본 이미지의 크기를 자동으로 늘리거나 줄여서 보여줄 때!


### 뷰의 속성
* cursorVisible : true/false
  * ```java
    public int getSelectionStart()
    public int getSelectionEnd()
    public void setSelection(int start, int end)
    public void setSelection(int index)
    public void selectAll()
    public void extendSelection(int index)
    ```
* autoLink : true/false
* lineSpacingMultiplier/lineSpacingExtra
* capitalize
* ellipsize : 줄임 표시 none/start/end/middle
* editable, hint
* 문자열이 사용자의 입력에 의해 바꿀때 마다 확인하는 기능 --> `TextChangedListner`
  * ```java
    public void addTextChangedListenr(TextWatcher watcher)
    ```
    -> 이 객체는 텍스트가 변경될 때마다 발생하는 이벤트를 처리한다
    * TextWatcher 인터페이스는 다음과 같다
      ```java
      public void beforeTextChanged(CharSequence s, int start, int count, int after)
      public void afterTextChanged(Editable s)
      public void onTextChanged(CharSequence s, int start, int before, int count)
      ```
    * 입력된 문자열의 길이 값을 확인할 때는 setFilter() -> InputFilter객체를 파라미터로 전달 -> 객체의 LengthFilter() 호출하여 입력될 문자열의 길이 값 설정

### 드로어블
* 상태에 따라 그래픽이나 이미지가 선택적으로 보이게 할 수 있음
* 뷰에 설정할 수 있는 객체이며 이 위에 그래픽을 그릴 수 있음

결국... 둘 다...
1. \[파일명.xml]을 생성한다. 여기에는 어떻게 들어갈 것인지 구현되어 있고, 파일은 최대한 쪼개진 채로 있는게 좋다
2. activity_main.xml에서 뷰를 설정하고 `background`로 drawable을 설정해준다

#### 상태 드로어블(StateListDrawable) 
: 상태별로 다른 비트맵 그래픽을 참조함
뷰의 상태에 따라 뷰에 보여줄 그래픽을 다르게 지정할 수 있음
* \<selector> \</selector>은 큰 상자임
  * 그 안에 \<item>을 넣는 것
    * state_로 시작하는 속성은 상태를 나타낸다
      * state_pressed : 눌린 상태
      * state_focused : 포커스를 받은 상태

#### 셰이프 드로어블(ShapeDrawable) 
: 색상과 그라데이션을 포함하여 도형 모양을 정의할 수 있음

### 이벤트 처리 이해하기
* Touch Event > Click Event
* Key Event
* Delegation Model(위임 모델) : 화면에서 발생하는 이벤트를 버튼과 가튼 위젯 객체에 전달한 후 그 이후 처리 과정을 버튼에 위임한다. 각각의 뷰마다 하나의 이벤트 처리 루틴을 할당해준다 --> 객체 지향 코드를 만들 수 있다

메서드들은 뷰를 상속하여 새로운 클래스를 정의할 때 **재정의**할 수 있다. 
```java
View.OnTouchListener : boolean onTouch(View v, MotionEvent event)
View.OnKeyListener : boolean onKey(View v, int keyCode, KeyEvent event)
View.OnClickListener : void onClick(View v)
View.OnFocusChangeListener : void onFocusChange(View v, boolean hasFocus)
```

```java
view.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        ...
        ...
        ...
        }
        return true;
    }
});
```
뷰가 터치되었을 때 리스터 객체의 onTouch() 메소드가 자동으로 호출된다. 

```java
view2.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        detector.onTouchEvent(motionEvent);
        return true;
    }
});
```

### 키 이벤트 처리하기

```java
public void showToast(String data) {
    Toast.makeText(this, data, Toast.LENGTH_LONG).show();
}
```

> 액티비티는 화면에 보이기 전에 메모리에 만들어져야 하는데 그 시점에 onCreate 메서드가 호출된다. 그리고 화면에 보이기 전에 onStart 메서드가 호출된다. 화면이 보이다가 없어지면 onStop이 호출될 수 있으며 메모리에서 없어지는 경우 onDestroy가 호출된다

>> 단말의 방향을 바꾸게 되면 액티비티가 메모리에서 없어졌다가 새로 만들어지는데 이 경우 액티비티 안에 선언해 두었던 변수 값이 사라지므로 변수 값을 저장했다가 다시 복원하는 onSaveInstanceState 콜백 메서드가 제공된다. 액티비티가 종료되기 전 상태를 저장하고 이 저장된 상태는 onCreate 메서드가 호출되리 때 전달되는 번들 객체로 복원할 수 있다

```java
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        name = editText.getText().toString();
        Toast.makeText(getApplicationContext(),
                "입력된 값을 변수에 저장했습니다 : " + name,
                Toast.LENGTH_LONG).show();
    }
});

if (savedInstanceState != null) {
    name = savedInstanceState.getString("name");
    Toast.makeText(getApplicationContext(), "값을 복원했습니다 : " + name, Toast.LENGTH_LONG).show();
}

@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putString("name", name);
}
```

configChanges 속성값이 설정되면 시스템은 액티비티의 상태 변화를 액티비티 쪽으로 알려주기만 하면 된다.
```xml
<activity android:name=".MainActivity"
    android:configChanges="orientation|screenSize|keyboardHidden">
```
액티비티에게 넘겨주는 것이므로

```java

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("방향 : ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("방향 : ORIENTATION_PORTRAIT");
        }
    }
    public void showToast(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}
```
* instanceState를 계속 Bundle로 받고 그것을 저장하고 있다는 것을 잊지 말자

### 토스트
* 각각 x축 y축 입력창을 객체로 생각하여 `EditText editText;`, `EditText editText2;`로 선언하고 `findViewById`로 값을 할당해주는 것 잊지 말자.
* 토스트 뷰를 위한 레이아웃은 항상 `TextView`(텍스트뷰) 태그로 정의해야 한다

```java
public void onButton2Clicked(View v) {
    LayoutInflater inflater = getLayoutInflater();

    View layout = inflater.inflate(
            R.layout.toastborder,
            (ViewGroup) findViewById(R.id.toast_layout_root));

    TextView text = layout.findViewById(R.id.text);

    Toast toast = new Toast(this);
    text.setText("모양 바꾼 토스트");
    toast.setGravity(Gravity.CENTER, 0, -100);
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.setView(layout);

    toast.show();

}
```
참고 :  https://soo0100.tistory.com/1017

* inflate -> xml에 표기된 레이아웃들을 메모리에 객체화시키기 위함.
* ```java
  LayoutInflater inflater = getLayoutInflater();
  View layout = inflater.inflate(
            R.layout.toastborder,
            (ViewGroup) findViewById(R.id.toast_layout_root));
  ```

#### 알림대화상자
1. `showMessage()`안에는 `AlertDialog.Builder builder = new AlertDialog.Builder(this);` 로 알람을 생성한다. -> 대화 상자를 만들기 위한 빌더 객체를 생성
2. set함수를 통해 onClick시 어떠한 메세지가 갈지 설정하고 `AlertDialog dialog = builder.create();` -> `dialog.show();`를 통해 메세지를 보여준다 ; 대화 상자 객체 생성후 보여주기

결과 : 큰 안내 상자가 나온다

### 프로그레스 바 사용하기
`<ProgressBar>` 태그를 이용한다.
```java
Button button = findViewById(R.id.button);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("데이터를 확인하는 중입니다.");
        dialog.show();
    }
});

Button button2 = findViewById(R.id.button2);
button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
});
```
`this`를 통해객체 전달을 했다는 것 -> `dialog = new ProgressDialog(MainActivity.this);`

## DoitMission
#### 05
둥근 모서리 참고 : https://wimir-dev.tistory.com/37
#### 06
참고 : 