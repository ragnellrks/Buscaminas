package es.riberadeltajo.buscaminas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoDificultad extends DialogFragment {
    //Un fragmento tiene dos métodos importantes
    //onCreateDialog -> primero de ciclo de vida del fragmento

    OnSeleccionDificultad seleccionDif;
    int minasTotales;
    int filaColumna;
    int dificultad;

    public static DialogoDificultad newInstance(int diff) {
        DialogoDificultad frag = new DialogoDificultad();
        Bundle args = new Bundle();
        args.putInt("Dificultad", diff);
        frag.setArguments(args);
        return frag;
    }
    //Escribe una interfaz que se implementa desde una actividad.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        seleccionDif=(OnSeleccionDificultad) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dificultad = getArguments().getInt("Dificultad");
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
                        filaColumna = 8;
                        break;
                    case 1:
                        dificultad = 1;
                        minasTotales = 30;
                        filaColumna = 12;
                        break;
                    case 2:
                        dificultad = 2;
                        minasTotales = 60;
                        filaColumna = 16;
                        break;
                }

            }
        });
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                seleccionDif.onSeleccion(dificultad, minasTotales, filaColumna);
            }
        });
        return builder.create();
    }

    public interface OnSeleccionDificultad{
        public void onSeleccion(int dificultad, int minas, int filaColumn);
    }

}
