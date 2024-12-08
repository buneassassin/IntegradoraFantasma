package com.primerp.integradora.Cosas.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Dialog.EditTinacoDialogActivity;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.R;
import com.primerp.integradora.ui.tinacoDetalle.TinacoDetalleActivity;

import java.util.List;

public class TinacoAdapter extends RecyclerView.Adapter<TinacoAdapter.TinacoViewHolder> {
    private List<Tinacos> tinacosList;

    public TinacoAdapter(List<Tinacos> tinacosList) {
        this.tinacosList = tinacosList;
    }

    public void updateTinacosList(List<Tinacos> newTinacosList) {
        this.tinacosList = newTinacosList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TinacoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tinaco, parent, false);
        return new TinacoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinacoViewHolder holder, int position) {
        if (tinacosList != null && !tinacosList.isEmpty()) {
            Tinacos tinaco = tinacosList.get(position);
            holder.setData(tinaco);
        }
    }

    @Override
    public int getItemCount() {
        return (tinacosList != null) ? tinacosList.size() : 0;
    }

    class TinacoViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final ImageView iconEditor;

        public TinacoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            iconEditor = itemView.findViewById(R.id.iconEdit);
        }

        public void setData(Tinacos tinaco) {
            tvTitle.setText(tinaco.getNombre());

            // Listener para editar
            iconEditor.setOnClickListener(view -> {
                Intent editIntent = createEditIntent(view, tinaco.getId());
                view.getContext().startActivity(editIntent);
            });

            // Listener para detalles
            itemView.setOnClickListener(view -> {
                Intent detailIntent = createDetailIntent(view, tinaco.getId());
                view.getContext().startActivity(detailIntent);
            });
        }

        private Intent createEditIntent(View view, int tinacoId) {
            Intent intent = new Intent(view.getContext(), EditTinacoDialogActivity.class);
            intent.putExtra("TINACO_ID", tinacoId);
            return intent;
        }

        private Intent createDetailIntent(View view, int tinacoId) {
            Intent intent = new Intent(view.getContext(), TinacoDetalleActivity.class);
            intent.putExtra("TINACO_ID", tinacoId);
            return intent;
        }
    }
}
