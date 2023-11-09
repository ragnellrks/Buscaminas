package es.riberadeltajo.buscaminas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoBomba extends DialogFragment {

    int drawableBomba;
    OnSeleccionBomba selectBomb;
    //Escribe una interfaz que se implementa desde una actividad.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectBomb=(DialogoBomba.OnSeleccionBomba) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.selecBomba);
        TipoBombas[] objetoBombas = {new TipoBombas(R.drawable.bomba, "Bomba Clásica"),
                new TipoBombas(R.drawable.dinamita, "Dinamita"),
                new TipoBombas(R.drawable.molotov,"Molotov")};

        //miAdaptador.setDropDownViewResource(R.layout.dropdownmodel);
        Spinner miSpinner = new Spinner(getActivity());
        miSpinner.setAdapter(new MiAdaptadorBombas(getActivity(), R.layout.spinneritems , objetoBombas));;
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TipoBombas seleccionado = (TipoBombas) miSpinner.getSelectedItem();
               drawableBomba = seleccionado.imagen;
                selectBomb.onSeleccionDrawable(drawableBomba);
            }
        });
        builder.setView(miSpinner);
        return builder.create();
    }

    public class MiAdaptadorBombas extends ArrayAdapter<TipoBombas>{
        TipoBombas[] misObjetos;
        public MiAdaptadorBombas(@NonNull Context context, int resource, @NonNull TipoBombas[] objects) {
            super(context, resource, objects);
            misObjetos = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position,convertView,parent);
        }


        //EN CASO DE REALIZARSE CON UN SPINNER
        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return crearFila(position, convertView, parent);
        }

        private View crearFila(int position, View convertView, ViewGroup parent) {
            //Obtenemos el layout inflater
            LayoutInflater miInflador = getLayoutInflater();
            //Creamos una vista con el inflado del layout inflater, donde elegimos el layout que hemos creado.
            View vistaFila = miInflador.inflate(R.layout.spinneritems, parent, false);

            //Dentro de esta vista, encontramos las referencias de los objetos de cada fila inflada.
            TextView nombre = vistaFila.findViewById(R.id.nomBomba);
            ImageView imagen = vistaFila.findViewById(R.id.icoBomba);

            //Rellenamos los datos con el objeto position del array de objeto.
            nombre.setText(misObjetos[position].nombre);
            imagen.setImageResource(misObjetos[position].imagen);

            //Devolvemos la vista con los datos.
            return vistaFila;
        }
    }
    public interface OnSeleccionBomba{
        public void onSeleccionDrawable(int drawable);
    }
}
