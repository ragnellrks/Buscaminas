package es.riberadeltajo.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public int [][] tablero;
    public int dificultad = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();


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
    }
}