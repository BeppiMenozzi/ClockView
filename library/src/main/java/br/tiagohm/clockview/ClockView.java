package br.tiagohm.clockview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class ClockView extends View {

    private static final float SEC_RAD = 2 * (float)Math.PI / 60;
    private static final float MIN_RAD = 2 * (float)Math.PI / 60;
    private static final float HOUR_RAD = 2 * (float)Math.PI / 12;
    private static final int SEC_DEG = 360 / 60;
    private static final int MIN_DEG = 360 / 60;
    private static final int HOUR_DEG = 360 / 12;
    private Paint secPaint, minPaint, hourPaint, dotPaint, circlePaint;
    private TextPaint numeralsPaint, digitalClockPaint;
    private RectF mRectF;
    private Rect mRect;
    //Segundos, minutos e horas
    private int mSecond = 0, mMinute = 0, mHour = 0;
    //
    private float radius;
    private Path secHand, minHand, hourHand;
    private boolean mShowDigitalClock = true;
    private boolean mShowSecondHand = true;
    private boolean mShowDots = true;
    private boolean mShowNumerals = false;
    private String[] mNumerals = new String[]{"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    private float numeralTextHeight = 0;

    public ClockView( Context context )
    {
        this( context, null );
    }

    public ClockView( Context context, AttributeSet attrs )
    {
        super( context, attrs );
        init( attrs );
    }

    private void init( AttributeSet attrs )
    {
        //Obtém os atributos XML
        final TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ClockView,
                0, 0 );

        //Inicializa os objetos.
        mRect = new Rect();
        mRectF = new RectF();
        secHand = new Path();
        minHand = new Path();
        hourHand = new Path();

        //Obtém os valores iniciais dos segundos, minutos e horas, etc.
        final Calendar c = Calendar.getInstance();
        mShowDigitalClock = a.getBoolean( R.styleable.ClockView_showDigitalClock, true );
        mShowSecondHand = a.getBoolean( R.styleable.ClockView_showSecondHand, true );
        mShowNumerals = a.getBoolean( R.styleable.ClockView_showNumerals, false );
        mShowDots = a.getBoolean( R.styleable.ClockView_showDots, true );
        mSecond = a.getInt( R.styleable.ClockView_second, c.get( Calendar.SECOND ) );
        mMinute = a.getInt( R.styleable.ClockView_minute, c.get( Calendar.MINUTE ) );
        mHour = a.getInt( R.styleable.ClockView_hour, c.get( Calendar.HOUR ) );

        //Fundo
        circlePaint = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        circlePaint.setColor( a.getColor( R.styleable.ClockView_circleColor, 0 ) );
        circlePaint.setStyle( Paint.Style.FILL_AND_STROKE );

        numeralsPaint = new TextPaint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        numeralsPaint.setColor( a.getColor( R.styleable.ClockView_numeralColor, Color.WHITE ) );
        numeralsPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        numeralsPaint.setTextAlign( Paint.Align.CENTER );
        numeralsPaint.setStrokeCap( Paint.Cap.ROUND );

        //Pintura do relógio digital.
        digitalClockPaint = new TextPaint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        digitalClockPaint.setColor( a.getColor( R.styleable.ClockView_digitalClockTextColor, Color.WHITE ) );
        digitalClockPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        digitalClockPaint.setTextAlign( Paint.Align.CENTER );
        digitalClockPaint.setStrokeCap( Paint.Cap.ROUND );

        //Pintura dos pontos.
        dotPaint = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        dotPaint.setColor( a.getColor( R.styleable.ClockView_dotColor, Color.WHITE ) );
        dotPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        dotPaint.setStrokeWidth( 3 );
        dotPaint.setStrokeCap( Paint.Cap.ROUND );

        //Pintura do ponteiro dos segundos
        secPaint = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        secPaint.setColor( a.getColor( R.styleable.ClockView_secondHandColor, Color.RED ) );
        secPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        secPaint.setStrokeWidth( 2 );
        secPaint.setStrokeCap( Paint.Cap.ROUND );

        //Pintura do ponteiro dos minutos
        minPaint = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        minPaint.setColor( a.getColor( R.styleable.ClockView_minuteHandColor, Color.WHITE ) );
        minPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        minPaint.setStrokeWidth( 2 );

        //Pintura do ponteiro das horas
        hourPaint = new Paint( Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG );
        hourPaint.setColor( a.getColor( R.styleable.ClockView_hourHandColor, Color.WHITE ) );
        hourPaint.setStyle( Paint.Style.FILL_AND_STROKE );
        hourPaint.setStrokeWidth( 2 );
    }

    /**
     * Está a exibir o relógio digital.
     */
    public boolean isShowDigitalClock()
    {
        return mShowDigitalClock;
    }

    /**
     * Define a exibição do relógio digital.
     */
    public void setShowDigitalClock( boolean showDigitalClock )
    {
        this.mShowDigitalClock = showDigitalClock;
        invalidate();
        requestLayout();
    }

    /**
     * Está a exibir os pontos.
     */
    public boolean isShowDots()
    {
        return mShowDots;
    }

    /**
     * Define a exibição dos pontos.
     */
    public void setShowDots( boolean showDots )
    {
        this.mShowDots = showDots;
        invalidate();
        requestLayout();
    }

    /**
     * Está a exibir os numerais.
     */
    public boolean isShowNumerals()
    {
        return mShowNumerals;
    }

    /**
     * Define a exibição dos numerais.
     */
    public void setShowNumerals( boolean showNumerals )
    {
        this.mShowNumerals = showNumerals;
        invalidate();
        requestLayout();
    }

    /**
     * Está a exibir o ponteiro dos segundos.
     */
    public boolean isShowSecondHand()
    {
        return mShowSecondHand;
    }

    /**
     * Define a exibição do ponteiro dos segundos.
     */
    public void setShowSecondHand( boolean showSecondHand )
    {
        this.mShowSecondHand = showSecondHand;
        invalidate();
        requestLayout();
    }

    /**
     * Obtém os segundos.
     */
    public int getSecond()
    {
        return mSecond;
    }

    /**
     * Define os segundos.
     */
    public void setSecond( int second )
    {
        this.mSecond = second % 60;
        invalidate();
        requestLayout();
    }

    /**
     * Obtém os minutos.
     */
    public int getMinute()
    {
        return mMinute;
    }

    /**
     * Define os minutos.
     */
    public void setMinute( int minute )
    {
        this.mMinute = minute % 60;
        invalidate();
        requestLayout();
    }

    /**
     * Obtém as horas.
     */
    public int getHour()
    {
        return mHour;
    }

    /**
     * Define as horas.
     */
    public void setHour( int hour )
    {
        this.mHour = hour % 12;
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor do ponteiro dos segundos.
     */
    public int getSecondHandColor()
    {
        return secPaint.getColor();
    }

    /**
     * Define a cor do ponteiro dos segundos.
     */
    public void setSecondHandColor( int color )
    {
        secPaint.setColor( color );
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor do ponteiro dos minutos.
     */
    public int getMinuteHandColor()
    {
        return minPaint.getColor();
    }

    /**
     * Define a cor do ponteiro dos minutos.
     */
    public void setMinuteHandColor( int color )
    {
        minPaint.setColor( color );
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor do ponteiro das horas.
     */
    public int getHourHandColor()
    {
        return hourPaint.getColor();
    }

    /**
     * Define a cor do ponteiro das horas.
     */
    public void setHourHandColor( int color )
    {
        hourPaint.setColor( color );
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor do pontos.
     */
    public int getDotColor()
    {
        return dotPaint.getColor();
    }

    /**
     * Define a cor dos pontos.
     */
    public void setDotColor( int color )
    {
        dotPaint.setColor( color );
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor dos números.
     */
    public int getNumeralColor()
    {
        return numeralsPaint.getColor();
    }

    /**
     * Define a cor dos números.
     */
    public void setNumeralColor( int color )
    {
        numeralsPaint.setColor( color );
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor dos números do relógio digital.
     */
    public int getDigitalClockTextColor()
    {
        return digitalClockPaint.getColor();
    }

    /**
     * Define a cor dos números do relógio digital.
     */
    public void setDigitalClockTextColor( int color )
    {
        digitalClockPaint.setColor( color );
        invalidate();
        requestLayout();
    }

    /**
     * Obtém a cor do fundo.
     */
    public int getCircleColor()
    {
        return circlePaint.getColor();
    }

    /**
     * Define a cor do fundo.
     */
    public void setCircleColor( int color )
    {
        circlePaint.setColor( color );
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw( Canvas canvas )
    {
        desenharFundo( canvas );
        desenharPontos( canvas );
        desenharRelogioDigital( canvas );
        desenharPonteiros( canvas, getHour(), getMinute(), getSecond() );
    }

    @Override
    protected void onLayout( boolean changed, int left, int top, int right, int bottom )
    {
        super.onLayout( changed, left, top, right, bottom );

        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();
        float realWidth = getWidth() - (paddingLeft + paddingRight);
        float realHeight = getHeight() - (paddingTop + paddingBottom);

        float width = Math.min( realWidth, realHeight );
        float height = Math.min( realWidth, realHeight );

        radius = width / 2;

        digitalClockPaint.setTextSize( (float)Math.sqrt( radius ) * 2.5f );
        numeralsPaint.setTextSize( (float)Math.sqrt( radius ) * 2 );
        numeralsPaint.setFakeBoldText( true );
        numeralsPaint.setTextAlign( Paint.Align.CENTER );
        numeralsPaint.getTextBounds( "0", 0, 1, mRect );
        numeralTextHeight = mRect.height() / 2f - mRect.bottom;

        float rectLeft = realWidth / 2 - radius + paddingLeft;
        float rectTop = realHeight / 2 - radius + paddingTop;
        float rectRight = realWidth / 2 - radius + paddingRight + width;
        float rectBottom = realHeight / 2 - radius + paddingBottom + height;

        mRectF.set( rectLeft, rectTop, rectRight, rectBottom );

        secHand.reset();
        secHand.moveTo( mRectF.centerX(), mRectF.centerY() );
        secHand.lineTo( mRectF.centerX(), mRectF.centerY() - (radius - numeralTextHeight * 1.41f) );
        secHand.close();

        minHand.reset();
        minHand.moveTo( mRectF.centerX(), mRectF.centerY() );
        minHand.lineTo( mRectF.centerX() + radius * 0.025f, mRectF.centerY() );
        minHand.lineTo( mRectF.centerX() + radius * 0.0015625f, mRectF.centerY() - (radius - numeralTextHeight * 1.41f) );
        minHand.lineTo( mRectF.centerX() - radius * 0.0015625f, mRectF.centerY() - (radius - numeralTextHeight * 1.41f) );
        minHand.lineTo( mRectF.centerX() - radius * 0.025f, mRectF.centerY() );
        minHand.close();

        hourHand.reset();
        hourHand.moveTo( mRectF.centerX(), mRectF.centerY() );
        hourHand.lineTo( mRectF.centerX() + radius * 0.025f, mRectF.centerY() );
        hourHand.lineTo( mRectF.centerX() + radius * 0.0015625f, mRectF.centerY() - radius * 0.7f );
        hourHand.lineTo( mRectF.centerX() - radius * 0.0015625f, mRectF.centerY() - radius * 0.7f );
        hourHand.lineTo( mRectF.centerX() - radius * 0.025f, mRectF.centerY() );
        hourHand.close();
    }

    private void desenharFundo( Canvas canvas )
    {
        canvas.save();
        canvas.drawCircle( mRectF.centerX(), mRectF.centerY(),
                radius + 5,
                circlePaint );
        canvas.restore();
    }

    private void desenharRelogioDigital( Canvas canvas )
    {
        if( isShowDigitalClock() )
        {
            canvas.save();
            canvas.drawText( String.format( Locale.ENGLISH, "%02d:%02d:%02d", getHour(), getMinute(), getSecond() ),
                    mRectF.centerX(), mRectF.centerY() + radius * 0.5f,
                    digitalClockPaint );
            canvas.restore();
        }
    }

    private void desenharPontos( Canvas canvas )
    {
        canvas.save();

        for( int i = 0; i < 12; i++ )
        {
            final float angulo = i * HOUR_RAD - HOUR_RAD * 3;

            for( int k = 0; k <= 4; k++ )
            {
                if( k == 0 && isShowNumerals() )
                {
                    canvas.drawText( mNumerals[i],
                            mRectF.centerX() + (float)Math.cos( angulo ) * (radius - numeralTextHeight * 1.41f),
                            mRectF.centerY() + (float)Math.sin( angulo ) * (radius - numeralTextHeight * 1.41f) + numeralTextHeight,
                            numeralsPaint );
                }
                else if( isShowDots() )
                {
                    canvas.drawCircle( mRectF.centerX() + (float)Math.cos( angulo + k * MIN_RAD ) * (radius - numeralTextHeight),
                            mRectF.centerY() + (float)Math.sin( angulo + k * MIN_RAD ) * (radius - numeralTextHeight),
                            k == 0 ? radius * 0.02f : radius * 0.005f,
                            dotPaint );
                }
            }
        }
        canvas.restore();
    }

    private void desenharPonteiros( Canvas canvas, int hour, int min, int sec )
    {
        canvas.save();
        canvas.rotate( MIN_DEG * min + 0.1f * sec, mRectF.centerX(), mRectF.centerY() );
        canvas.drawPath( minHand, minPaint );
        canvas.restore();

        canvas.save();
        canvas.rotate( HOUR_DEG * hour + 0.5f * min + 0.1f * sec, mRectF.centerX(), mRectF.centerY() );
        canvas.drawPath( hourHand, hourPaint );
        canvas.restore();

        if( isShowSecondHand() )
        {
            canvas.save();
            canvas.rotate( SEC_DEG * sec, mRectF.centerX(), mRectF.centerY() );
            canvas.drawPath( secHand, secPaint );
            canvas.restore();
        }
    }
}
