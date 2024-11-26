package com.primerp.integradora.Cosas.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.R;
import com.primerp.integradora.ui.tinacoDetalle.TinacoDetalleActivity;

import java.util.List;

public class TinacoAdapter extends RecyclerView.Adapter<TinacoAdapter.TinacoViewHolder> {
    private List<Tinacos> tinacoslist;

    public TinacoAdapter(List<Tinacos> tinacoslist) {
        this.tinacoslist = tinacoslist;
    }

    public void updateTinacosList(List<Tinacos> newTinacosList) {
        this.tinacoslist = newTinacosList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TinacoAdapter.TinacoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tinaco, parent, false);
        return new TinacoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinacoViewHolder holder, int position) {
        Tinacos tinaco = tinacoslist.get(position);
        Log.d("DEBUG", "Asignando nombre: " + tinaco.getNombre());
        holder.setData(tinaco);
    }


    @Override
    public int getItemCount() {
        return tinacoslist.size();
    }

    static class TinacoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public TinacoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        public void setData(Tinacos tinacos) {
            tvTitle.setText(tinacos.getNombre());

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), TinacoDetalleActivity.class);

                intent.putExtra("TINACO_ID", tinacos.getId());

                view.getContext().startActivity(intent);
            });
        }
    }
}

