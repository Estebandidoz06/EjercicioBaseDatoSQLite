package com.example.ejerciciobasedatosqlite.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejerciciobasedatosqlite.R;
import com.example.ejerciciobasedatosqlite.VerActivity;
import com.example.ejerciciobasedatosqlite.entidades.Contactos;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<Contactos> listaContactos;

    public ListaContactosAdapter(ArrayList<Contactos> listaContactos){
        this.listaContactos=listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto,null,false);
       return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.txtNombreView.setText(listaContactos.get(position).getNombre());
        holder.txtEdadView.setText(String.valueOf(listaContactos.get(position).getEdad()));
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public  class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreView,txtEdadView;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreView= itemView.findViewById(R.id.txtNombreView);
            txtEdadView=itemView.findViewById(R.id.txtEdadView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent (context, VerActivity.class);
                    intent.putExtra("ID",listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });


        }
    }
}
