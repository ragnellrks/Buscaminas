package es.riberadeltajo.buscaminas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
        TipoBombas[] objetoBombas = {new TipoBombas(R.drawable.bomba, "Bomba Clásica"), new TipoBombas(R.drawable.dinamita, "Dinamita"), new TipoBombas(R.drawable.molotov,"Molotov")};
        ArrayAdapter<TipoBombas> miAdaptador = new ArrayAdapter<TipoBombas> ( getContext (), android.R.layout.simple_spinner_item, objetoBombas  );
        /*miAdaptador.setDropDownViewResource(R.layout.dropdownmodel);
        View miVistaSpinner = getLayoutInflater().inflate(R.layout.spinnerlayout, null);
        Spinner miSpinner = (Spinner) miVistaSpinner.findViewById(R.id.spinnerBomba);*/
        //miSpinner.setAdapter(miAdaptador);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TipoBombas seleccionado = (TipoBombas) miSpinner.getSelectedItem();
               // drawableBomba = seleccionado.imagen;
                //selectBomb.onSeleccionDrawable(drawableBomba);
            }
        });
        //builder.setView(miVistaSpinner);
        return builder.create();
    }

    public interface OnSeleccionBomba{
        public void onSeleccionDrawable(int drawable);
    }
}
