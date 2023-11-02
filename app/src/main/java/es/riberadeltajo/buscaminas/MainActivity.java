package es.riberadeltajo.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public int[][] tablero;
    public Button[] botones;
    public int minasTotales = 10;
    public int dificultad = 0;

    //Layouts
    GridLayout g;
    ConstraintLayout clayout;
    LinearLayout.LayoutParams layoutParams;


    int altura;
    int ancho;

    int filaColumna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filaColumna = 8;




    }

    @Override
    protected void onStart() {
        super.onStart();
        g=new GridLayout(this);
        clayout= findViewById(R.id.layoutPrincipal);
        altura = clayout.getHeight();
        ancho = clayout.getWidth();
        pintarTablero();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buscaminas_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuInstrucciones){
            crearDialogoInstrucciones();
        }else if(item.getItemId()==R.id.menuConfig){
            crearMenuConfig();
        }else if(item.getItemId()==R.id.menuRestart){
            pintarTablero();
            Toast.makeText(this, "El juego se ha reiniciado!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Método que crea el constructor de dialogo del menu de configuracion.
    private void crearMenuConfig() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.configmenu);
        String[] items = {getString(R.string.principiante),getString(R.string.amateur),getString(R.string.experto)};
        int seleccion = dificultad;
        builder.setSingleChoiceItems(items, seleccion, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:

                            dificultad = 0;
                            minasTotales = 10;
                            break;
                        case 1:
                            dificultad = 1;
                            minasTotales = 30;
                            break;
                        case 2:
                            dificultad = 2;
                            minasTotales = 60;
                            break;
                    }

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        mostrarDialogo(builder);
    }

    //Método para crear el constructor de dialogo de las Instrucciones.
    private void crearDialogoInstrucciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.instrucciones);
        builder.setTitle(R.string.instmenu);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        mostrarDialogo(builder);

    }

    //Método que pinta el dialogo que se pase como Builder.
    private void mostrarDialogo(AlertDialog.Builder builder){
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void pintarTablero(){
        //Esto iniciará/reseteará el juego





        //Parametros del layout
        GridLayout.LayoutParams param=new GridLayout.LayoutParams();
        param.setMargins(0,0,0,0);
        param.height= ViewGroup.LayoutParams.MATCH_PARENT;
        param.width= ViewGroup.LayoutParams.MATCH_PARENT;
        g.setRowCount(filaColumna);
        g.setColumnCount(filaColumna);
        g.setLayoutParams(param);

        Log.d("ASASDASDASSD", "Clayout: "+clayout.getHeight()+" : "+clayout.getWidth());
        Log.d("ASASDASDASSD", "GRID: "+g.getHeight()+" : "+g.getWidth());
        //Parametros de botones
        layoutParams = new LinearLayout.LayoutParams(ancho/filaColumna,altura/filaColumna);
        layoutParams.setMargins(0,0,0,0);

        for(int i = 0; i<8; i++){
            for(int j = 0; j < 8; j++){
                Button b = new Button(this);
                b.setLayoutParams(layoutParams);
                b.setBackgroundColor(Color.RED);

                g.addView(b);
            }
        }

    }
}