# ClockView

[![](https://jitpack.io/v/tiagohm/ClockView.svg)](https://jitpack.io/#tiagohm/ClockView)

<img src="https://raw.githubusercontent.com/tiagohm/ClockView/master/1.png" width="300">
<img src="https://raw.githubusercontent.com/tiagohm/ClockView/master/2.png" width="300">

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

Add the dependency:
```gradle
	dependencies {
	        compile 'com.github.tiagohm:ClockView:0.1.0'
	}
```

### USING

```xml
<br.tiagohm.clockview.ClockView
        android:id="@+id/relogio"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="15dp"
        app:circleColor="#EEE"
        app:digitalClockTextColor="#aa126cd2"
        app:dotColor="#7f418616"
        app:hour="9"
        app:hourHandColor="#FFF"
        app:minute="45"
        app:minuteHandColor="#FFF"
        app:numeralColor="#AFFF"
        app:second="0"
        app:secondHandColor="#2F00"
        app:showDigitalClock="true"
        app:showDots="true"
        app:showNumerals="true"
        app:showSecondHand="true"/>
```
Or

```java
ClockView cv = (ClockView)findViewById( R.id.relogio );
cv.setDigitalClockTextColor( Color.MAGENTA );
cv.setDotColor( Color.WHITE );
cv.setHour( 12 );
cv.setHourHandColor( Color.GREEN );
cv.setMinute( 45 );
cv.setMinuteHandColor( Color.BLUE );
cv.setNumeralColor( Color.WHITE );
cv.setSecond( 56 );
cv.setSecondHandColor( Color.RED );
cv.setShowDigitalClock( false );
cv.setShowDots( true );
cv.setShowNumerals( true );
cv.setShowSecondHand( true );
cv.setCircleColor( COLOR.DKGRAY );
```
