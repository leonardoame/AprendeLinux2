package mx.edu.utng.aprendelinux;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.androidplot.Plot;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepFormatter;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import java.text.DecimalFormat;
import java.util.Arrays;

import mx.edu.utng.aprendelinux.util.DBAdapter;



public class GraficaMenuActiviry extends ActionBarActivity {
    private XYPlot myxyPlot;
    private DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica_menu_layout);
        initComponents();
    }

    private void initComponents() {
        dbAdapter=new DBAdapter(this);
        dbAdapter.open();
        myxyPlot=(XYPlot)findViewById(R.id.xyp_grafica);

        int mod1,mod2,mod3,mod4,mod5;
        mod1=dbAdapter.traerCalificacion(1,FormLoginActivity.ID_USU_LOGEADO);
        mod2=dbAdapter.traerCalificacion(2,FormLoginActivity.ID_USU_LOGEADO);
        mod3=dbAdapter.traerCalificacion(3,FormLoginActivity.ID_USU_LOGEADO);
        mod4=dbAdapter.traerCalificacion(4,FormLoginActivity.ID_USU_LOGEADO);
        mod5=dbAdapter.traerCalificacion(5,FormLoginActivity.ID_USU_LOGEADO);
        dbAdapter.close();
        Log.e("Calificacion por modulo", "initComponents: "+mod1 );
        Log.e("Calificacion por modulo", "initComponents: "+mod2 );
        Log.e("Calificacion por modulo", "initComponents: "+mod3 );
        Log.e("Calificacion por modulo", "initComponents: "+mod4 );
        Number[] series1Numbers = {5, mod1, mod2, mod3, mod4, mod5, 5};

        XYSeries series2 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Calificacion");

        myxyPlot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.BLACK);//Borde interior
        myxyPlot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.BLACK);//Borde interior

        myxyPlot.setBorderStyle(Plot.BorderStyle.SQUARE, null, null);//grafiac de barras
        myxyPlot.getBorderPaint().setStrokeWidth(1);
        myxyPlot.getBorderPaint().setAntiAlias(false);
        myxyPlot.getBorderPaint().setColor(Color.BLACK);//Borde de toda la grafica

        Paint lineFill = new Paint();
        lineFill.setAlpha(200);
        lineFill.setShader(new LinearGradient(0, 0, 0, 250, Color.WHITE, Color.RED, Shader.TileMode.MIRROR));

        StepFormatter stepFormatter  = new StepFormatter(Color.rgb(0, 0,0), Color.RED);
        stepFormatter.getLinePaint().setStrokeWidth(1);//Grosor del borde de las lineas de las barras

        stepFormatter.getLinePaint().setAntiAlias(false);
        stepFormatter.setFillPaint(lineFill);
        myxyPlot.addSeries(series2, stepFormatter);
        //Numeraciones
        myxyPlot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
        myxyPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
        myxyPlot.setTicksPerRangeLabel(1);
        myxyPlot.setTicksPerDomainLabel(1);

        myxyPlot.setDomainLabel("Examenes");
        myxyPlot.setRangeLabel("Calificaciónes");

        myxyPlot.setDomainValueFormat(new DecimalFormat("0"));//Para que solo aparescan numeros enteros


        myxyPlot.setTitle("Grafica de examnes");

    }
}
