# 🌻 Sunflower 클론 코딩

Android Jetpack을 공부하기 위해서 Android 공식 Jetpack sample project인  
[Sunflower](https://github.com/android/sunflower) 앱을 클론 코딩하기로 했다.  
매일 clone coding으로 배운 내용을 README에 꾸준히 정리할 것이다.

> 기간: 2022-06-01 ~

---

<details>
<summary> <b> 2022-06-05 </b> </summary>
<div markdown="1">

### NoActionBar

```xml
<!-- themes.xml -->

<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Theme.Sunflower" parent="Theme.MaterialComponents.DayNight.NoActionBar">

		...

    </style>
</resources>
```

`Theme.MaterialComponents.DayNight.NoActionBar` 로 변경

### View Pager Structure

```xml
<CoordinatorLayout>

	<AppBarLayout>

		<CollapsingToolbarLayout>

			<ImageView>
			</ImageView>

			<Toolbar>
			</Toolbar>

		</CollapsingToolbarLayout>

	</AppBarLayout>
	
	<ViewPager2>
	</ViewPager2>

</CoordinatorLayout>
```

- CoordinatorLayout : FrameLayout 특징을 가지며, 자식뷰에 `behavior`을 설정하여 다양한 움직임 및 애니메이션 상호작용을 구현할 수 있음
- ImageView : 접히거나 나타날 이미지
- Toolbar : 접혔을 때도 남아있을 툴바
- ViewPager2 : 스크롤할 뷰 (NestedScrollView, RecyclerView 가능)

### Code

```xml
<!-- fragment_view_pager.xml -->

<layout>
	<androidx.coordinatorlayout.widget.CoordinatorLayout
        android:id="@+id/coordinator_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true">

				...

   </androidx.coordinatorlayout.widget.CoordinatorLayout>
</layout>
```

`fitsSystemWindows="true"` : 상태바(Statusbar)가 툴바(Toolbar)를 가리는 경우 뷰가 차지할 수 있는 영역을 상태바 및 소프트키 영역을 제외한 영역까지 확장해주는 역할

```xml
<layout>
	<CoordinatorLayout>

      <androidx.viewpager2.widget.ViewPager2
          android:id="@+id/view_pager"
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior"/>

		...

	</CoordinatorLayout>
</layout>
```

`app:layout_behavior="..$ScroolingViewBehavior"` : 스크롤 시 액션바를 줄어들게 함

```xml
<layout>
	<CoordinatorLayout>

		...

		<com.google.android.material.appbar.AppBarLayout
            android:id="@+id/app_bar_layout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:fitsSystemWindows="true"
            android:theme="@style/Theme.Sunflower_clone.AppBarOverlay">

            <com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/toolbar_layout"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_scrollFlags="scroll|snap"
                app:toolbarId="@id/toolbar">

            </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>
	</CoordinatorLayout>
</layout>
```

`app:layout_scrollFlags`

- scroll : 이 뷰가 화면에서 사라질 수 있다.
- snap : AppbarLayout size의 절반 크기 기준으로 아래 위로 달라붙는다.

`android:fitsSystemWindows="true"` : 스크롤 시 앱바가 상태바를 침범하기 때문에 필요

`android:theme="@style/Theme.Sunflower_clone.AppBarOverlay"`

```xml
<!-- themes.xml -->

<resource>

	...

	<style name="Theme.Sunflower_clone.AppBarOverlay" parent="ThemeOverlay.MaterialComponents.Dark.ActionBar"/>

</resource>
```

AppBar 내 Toolbar의 글자색을 흰색으로 변경 (Dark.ActionBar)

```xml
<com.google.android.material.appbar.MaterialToolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    app:contentInsetStart="0dp"
    app:layout_collapseMode="parallax">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:gravity="center"
        android:textAppearance="?attr/textAppearanceHeadline5"/>

</com.google.android.material.appbar.MaterialToolbar>
```

`app:contentInsetStart="0dp"` : 왼쪽 공백 없애기

`app:layout_collapseMode="parallax"`

- pin : CollapsingToolbarLayout 이 축소되면 툴바는 화면위에 고정됨
- parallax : 축소되면서 사라짐

`android:textAppearance` : 텍스트 크기 및 스타일 설정

### TabLayout

1. selector 추가

```xml
<!-- tab_icon_color_selector.xml -->

<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="?attr/colorPrimary" android:state_activated="true" />
    <item android:color="?attr/colorPrimaryDark"/>
</selector>
```

1. colors 선언

```xml
<!-- colors.xml -->

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="sunflower_black">#de000000</color>
    <color name="sunflower_gray_50">#fafafa</color>
    <color name="sunflower_gray_50_a600">#99fafafa</color>
    <color name="sunflower_green_300">#6dc790</color>
    <color name="sunflower_green_500">#49bb79</color>
    <color name="sunflower_green_700">#005d2b</color>
    <color name="sunflower_green_900">#1a231e</color>
    <color name="sunflower_white">#deffffff</color>
    <color name="sunflower_yellow_300">#f8f99f</color>
    <color name="sunflower_yellow_500">#ffff63</color>
</resources>
```

1. themes 수정

```xml
<style name="Theme.Sunflower_clone" parent="Theme.MaterialComponents.DayNight.NoActionBar">
        <item name="colorPrimary">@color/sunflower_green_500</item>
        <item name="colorOnPrimary">@color/sunflower_yellow_500</item>
        <item name="colorPrimaryDark">@color/sunflower_green_700</item>
        <item name="colorOnSurface">@color/sunflower_black</item>
        <item name="colorAccent">@color/sunflower_green_700</item>
        <item name="colorSurface">@color/sunflower_gray_50</item>
        <item name="colorSecondary">@color/sunflower_yellow_500</item>
        <item name="android:colorBackground">@color/sunflower_green_500</item>
</style>
```

1. TabLayout 추가

```xml
<com.google.android.material.tabs.TabLayout
    android:id="@+id/tabs"
    style="@style/Widget.MaterialComponents.TabLayout.Colored"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    app:tabIconTint="@drawable/tab_icon_color_selector"
app:tabTextColor="?attr/colorPrimaryDark"/>
```

`style="@style/Widget.MaterialComponents.TabLayout.Colored"` : TabLayout 스타일 설정 (배경색, 글자색, Indicator 색 등) → colorPrimary or colorOnPrimary 미리 설정 필요 (3번)
  
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
