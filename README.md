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
    
  </div>
</details>
