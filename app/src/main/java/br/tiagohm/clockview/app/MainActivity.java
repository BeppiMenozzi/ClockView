package br.tiagohm.clockview.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.tiagohm.clockview.ClockView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        getSupportActionBar().setElevation( 0 );

        ClockView cv = (ClockView)findViewById( R.id.relogio );
    }
}
