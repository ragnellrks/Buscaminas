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

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements DialogoDificultad.OnSeleccionDificultad{


    public int[][] tablero;
    public int minasTotales = 10;
    public int dificulty = 0;

    //Layouts
    GridLayout g;
    ConstraintLayout clayout;
    LinearLayout.LayoutParams layoutParams;

    GridLayout.LayoutParams param;


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


        clayout= findViewById(R.id.layoutPrincipal);
        clayout.post(new Runnable() {
            @Override
            public void run() {

                altura = clayout.getHeight();
                ancho = clayout.getWidth();
                param=new GridLayout.LayoutParams();
                param.setMargins(0,0,0,0);
                param.height= ViewGroup.LayoutParams.MATCH_PARENT;
                param.width= ViewGroup.LayoutParams.MATCH_PARENT;

                pintarTablero();
            }
        });

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

    //TO-DO: CREAR UN DIALOG FRAGMENT PARA LA DIFICULTAD Y LAS BOMBAS.
    
    //Método que crea el constructor de dialogo del menu de configuracion.
    private void crearMenuConfig() {
        //Anotación: se que esto no lo hemos visto en clase, lo he sacado de la documentación de DialogFragment_AlertDialog en Android developer.
        //Básicamente crea una instancia estática (Como un constructor pero accesible sin crear la clase, ese constructor crea el dialogo y puede pasar argumentos).
        //Esto lo he hecho porque quería que el selector de la dificultad se posara sobre la dificultad actual marcada.
        DialogoDificultad miDialogo = DialogoDificultad.newInstance(dificulty);
        miDialogo.show(getSupportFragmentManager(), "Dialogo Dificultad");
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

       //Es importante borrar la instancia de G en el ConstraintLayout cada vez que se llame a esta función para repintar el tablero.
        clayout.removeView(g);
        //Crear el gridlayout y añadirselo como hijo al ConstraintLayout
        //Añadir filas, columnas y asignarle los parámetros
        g=new GridLayout(getApplicationContext());
        clayout.addView(g);
        g.setRowCount(filaColumna);
        g.setColumnCount(filaColumna);
        g.setLayoutParams(param);
        //Parametros de botones
        layoutParams = new LinearLayout.LayoutParams(ancho/filaColumna,altura/filaColumna);
        layoutParams.setMargins(0,0,0,0);

        crearCampoMinas();
        //Creacion de Botones
        //TO-DO: Diferenciar las filas y columnas para sacar boton o imagebutton con bomba.
        for(int i = 0; i<filaColumna; i++){
            for(int j = 0; j < filaColumna; j++){

                if(tablero[i][j] == -1){
                    ImageButton ib = new ImageButton(this);
                    ib.setLayoutParams(layoutParams);
                    ib.setBackgroundColor(Color.RED);
                    g.addView(ib);
                }else{
                    Button b = new Button(this);
                    b.setLayoutParams(layoutParams);
                    g.addView(b);
                }

            }
        }

    }

    private void crearCampoMinas() {
        tablero = new int[filaColumna][filaColumna];


        //Rellenar la matriz con 0;
        for(int i = 0; i<filaColumna; i++){
            for(int j = 0; j<filaColumna; j++){
                tablero[i][j] = 0;
            }
        }
        Random rand = new Random();
        //Poner minas
        for(int i = 0; i<minasTotales; i++){
            int col;
            int fila;
            //Repetimos esto en caso de que le valor que se genere en los números aleatorios sea -1 para que no se repitan las minas.
            do{
                col = rand.nextInt(filaColumna-1);
                fila = rand.nextInt(filaColumna-1);
            }while(tablero[col][fila] == -1);
            tablero[col][fila] = -1;
        }
    }

    @Override
    public void onSeleccion(int dificultad, int minas, int filaColumn ) {
        dificulty = dificultad;
        minasTotales = minas;
        filaColumna = filaColumn;
    }
}