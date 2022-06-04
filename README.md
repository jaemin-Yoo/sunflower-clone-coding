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

<details>
<summary> <b> 2022-06-04 </b> </summary>
<div markdown="1">
  
### Sunflower 구조

- SPA(Single-Page-Application) 구조
- 하나의 `Activity`와 여러 개의 `Fragment`
- Jetpack Navigation에서 제공하는 `Bottom Navigation`, `Toolbar`사용 x
- `Toolbar + ViewPager2 + TabLayout` in `MainActivity`

## Jetpack Navigation

> Navigation은 Android 애플리케이션 내에서 `대상` 사이를 탐색하는 프레임워크로,  
> 대상이 Fragment, Activity 또는 기타 구성요소로 구현되었는지에 관계없이 일관된 API 제공  
> → 즉, 화면 이동을 쉽고 편리하게 해줌  

```kotlin
// build.gradle (:app)

implementation "androidx.navigation:navigation-fragment-ktx:$rootProject.navigationVersion"
implementation "androidx.navigation:navigation-ui-ktx:$rootProject.navigationVersion"
```

### 구성 요소

1. Navigation graph (xml resource)

각 fragment를 navigation으로 연결해준다.

```xml
// res/navigation/nav_graden.xml

<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    app:startDestination="@id/view_pager_fragment">

    <fragment
        android:id="@+id/view_pager_fragment"
        android:name="com.jaemin.sunflower_clone.HomeViewPagerFragment"
        tools:layout="@layout/fragment_view_pager">

        <action
            android:id="@+id/action_view_pager_fragment_to_plant_detail_fragment"
            app:destination="@id/plant_detail_fragment"/>
    </fragment>

    <fragment
        android:id="@+id/plant_detail_fragment"
        android:name="com.jaemin.sunflower_clone.PlantDetailFragment"
        android:label="@string/plant_details_title"
        tools:layout="@layout/fragment_plant_detail">

        <action
            android:id="@+id/action_plant_detail_fragment_to_gallery_fragment"
            app:destination="@id/gallery_fragment"/>
        <argument
            android:name="plantId"
            app:argType="string"/>
    </fragment>

    <fragment
        android:id="@+id/gallery_fragment"
        android:name="com.jaemin.sunflower_clone.GalleryFragment"
        android:label="@string/plant_details_title"
        tools:layout="@layout/fragment_gallery">
        <argument
            android:name="plantName"
            app:argType="string"/>
    </fragment>

</navigation>
```
  
2. NavHost
- frgment destinations을 전환해주는 역할

```xml
<!-- activity_graden.xml -->

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/nav_host"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graden" />

</layout>
```

- `app:navGraph` : NavHostFragment를 탐색 그래프와 연결
- `app:defaultNavHost` : true로 설정 시 NavHostFragment가 시스템 Back 버튼을 가로챔
  
</div>
</details>
