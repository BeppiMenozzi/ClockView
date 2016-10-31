package br.tiagohm.clockview.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tiagohm.clockview.ClockView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

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
    }
}
