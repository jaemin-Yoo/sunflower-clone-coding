# 🌻 Sunflower 클론 코딩

Android Jetpack을 공부하기 위해서 Android 공식 Jetpack sample project인  
[Sunflower](https://github.com/android/sunflower) 앱을 클론 코딩하기로 했다.  
매일 clone coding으로 배운 내용을 README에 꾸준히 정리할 것이다.

> 기간: 2022-06-01 ~

---

<details>
  <summary> <b> 2022-06-02 </b> </summary>
  <div markdown="1">
    
### Data binding Setup

```groovy
// build.gradle (:app)

android {
    ...
    buildFeatures {
        dataBinding true
    }
    ...
}
```

```kotlin
// GardenActivity.kt

import androidx.databinding.DataBindingUtil.setContentView

...

override fun onCreate(..) {
    ...
    setContentView<ActivityGardenBinding>(this, R.layout.activity_garden)
}
```
  

### build.gradle Setup

```kotlin
// build.gradle (:project)

buildScript {
    ext {
        // Sdk and tools
        compileSdkVersion = 32
        minSdkVersion = 21
        targetSdkVersion = 30
    
        // App dependencies
        appCompatVersion = '1.4.1'
        ...
    }
}
```

build.gradle 에 ext 변수를 이용하여 한 공간에 버전을 정리해둔다.
  
```kotlin
// build.gradle (:app)

dependencies {
    implementation "androidx.core:core-ktx:$rootProject.ktxVersion"
    ...
}
```
    
### Dagger Hilt

- Dependency Setup

```kotlin
// build.gradle (:project)

buildScript{
    ext {
        ...
        hiltVersion = '2.38.1'
    }

    dependencies {
        classpath "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    }
}
```

```kotlin
// build.gradle (:app)

plugins {
    ...
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin'
}

...

dependencies {
    ...
    implementation "com.google.dagger:hilt-android:$rootProject.hiltVersion"
    kapt "com.google.dagger:hilt-android-compiler:$rootProject.hiltVersion"
}
```

- `@HiltAndroidApp` 어노테이션 추가

```kotlin
// MainApplication.kt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()
```

의존성 주입의 **시작점**을 지정하고 Application의 생명주기를 따르며,

컴파일 단계에서 DI에 필요한 구성요소들을 **초기화**한다.

- `@AndroidEntryPoint` 어노테이션 추가

```kotlin
// GardenActivity.kt

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenActivity : AppCompatActivity() {
    ...
}
```

객체를 주입할 Android 클래스에 `@AndroidEntryPoint` 어노테이션 추가

자동으로 생명주기에 따라 적절한 시점에 Hilt 요소로 인스턴스화 되어 처리됨

<Hilt가 지원하는 Android Class>

- `Application` (`@HiltAndroidApp`)
- `Activity`
- `Fragment`
- `View`
- `Service`
- `BroadcastReceiver`
    
  </div>
</details>
