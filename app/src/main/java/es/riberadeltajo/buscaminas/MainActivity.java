package es.riberadeltajo.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    public int aciertos;

    public boolean derrota;

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
        derrota = false;
        aciertos = 0;
       //Es importante borrar la instancia de G en el ConstraintLayout cada vez que se llame a esta función para repintar el tablero.
        clayout.removeView(g);
        //Crear el gridlayout y añadirselo como hijo al ConstraintLayout
        //Añadir filas, columnas y asignarle los parámetros
        g=new GridLayout(getApplicationContext());
        clayout.addView(g);
        g.setRowCount(filaColumna);
        g.setColumnCount(filaColumna);
        g.setLayoutParams(param);
        g.setBackgroundColor(Color.BLACK);
        //Parametros de botones
        layoutParams = new LinearLayout.LayoutParams(ancho/filaColumna,altura/filaColumna);
        layoutParams.setMargins(0,0,0,0);


        //Listener para los botones
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!derrota || aciertos == g.getChildCount() - minasTotales) {
                    boolean found = false;
                    int columna = 0;
                    int fila = 0;
                    //Si encuentra un hijo en la posicion I que concuerde con el source de la View, se ejectura.
                    for(int i = 0;!found || i != g.getChildCount();i++){
                        if(g.getChildAt(i) == v){
                            found = true;
                            Log.d("Ribera del tajo", "Encontrada la vista en posición"+i);
                            //Sacamos la posicion a la que equivale el botón en la matriz del campo.
                            if(i==0){
                                fila = 0;
                                columna = 0;
                            }else {
                                fila = i / filaColumna;
                                columna = i % filaColumna;
                            }
                        }
                    }
                    switch(tablero[fila][columna]){
                        case -1:
                            v.setBackgroundColor(Color.RED);
                            Toast.makeText(getApplicationContext(), "HAS PERDIDO!", Toast.LENGTH_LONG).show();
                            derrota = true;
                            break;
                        default:
                            Button b = (Button) v;
                            if (tablero[fila][columna] == 0){
                                revealSurroundings(fila, columna);
                            }else{
                                b.setText(Integer.toString(tablero[fila][columna]));
                                aciertos++;
                            }
                            checkForWin();
                            b.setClickable(false);
                            break;
                    }
                }
            }
        };

        //creamos el Tablero
        crearCampoMinas();


        //Creacion de Botones
        for(int i = 0; i<filaColumna; i++){
            for(int j = 0; j < filaColumna; j++){
                //PROBAR A DIVIDIR EL INT QUE SE OBTIENE ELIGIENDO EL HIJO DEL GRIDLAYOUT ENTRE FILACOLUMNAS

                if(tablero[i][j] == -1){
                    ImageButton ib = new ImageButton(this);
                    ib.setLayoutParams(layoutParams);
                    ib.setOnClickListener(listener);
                    ib.setBackgroundColor(Color.BLUE);
                    g.addView(ib);
                }else{
                    Button b = new Button(this);
                    b.setLayoutParams(layoutParams);
                    b.setOnClickListener(listener);
                    g.addView(b);
                }

            }
        }

    }

    private void checkForWin() {
        if(aciertos == g.getChildCount() - minasTotales){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("GANADOR!");
            builder.setTitle("FIN DE PARTIDA!");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            mostrarDialogo(builder);
        }
    }

    private void revealSurroundings(int i, int j) {
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k >= 0 && k < filaColumna && l >= 0 && l < filaColumna) {
                    int posicion = k*filaColumna+l;
                    Button b = (Button) g.getChildAt(posicion);
                    if(b.getText().equals("")) {
                        b.setClickable(false);
                        b.setText(Integer.toString(tablero[k][l]));
                        aciertos++;
                    }
                }
            }
        }

    }


    private void crearCampoMinas() {
        tablero = new int[filaColumna][filaColumna];

        Random rand = new Random();
        //Poner minas
        for(int i = 0; i<minasTotales; i++){
            int col;
            int fila;
            //Repetimos esto en caso de que le valor que se genere en los números aleatorios sea -1 para que no se repitan las minas.
            do{
                col = rand.nextInt(filaColumna-1);
                fila = rand.nextInt(filaColumna-1);
            }while(tablero[fila][col] == -1);
            tablero[fila][col] = -1;
        }
        //Rellenar la matriz con números respecto a las minas adyacentes
        for(int i = 0; i<filaColumna; i++){
            for(int j = 0; j<filaColumna; j++){
                if(tablero[i][j] != -1) {
                    tablero[i][j] = checkForMina(i,j);
                }
            }
        }


    }

    private int checkForMina(int i, int j) {
        int contador = 0;
        for (int k = i - 1; k <= i + 1; k++) {
            for (int l = j - 1; l <= j + 1; l++) {
                if (k >= 0 && k < filaColumna && l >= 0 && l < filaColumna && tablero[k][l] == -1) {
                    contador++;
                }
            }
        }
        return contador;
    }

    @Override
    public void onSeleccion(int dificultad, int minas, int filaColumn ) {
        dificulty = dificultad;
        minasTotales = minas;
        filaColumna = filaColumn;
    }
}