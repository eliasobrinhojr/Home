package br.com.arduino.home.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.arduino.home.R;
import br.com.arduino.home.api.ApiService;
import br.com.arduino.home.bean.RowBean;
import br.com.arduino.home.dao.RowsDAO;
import br.com.arduino.home.ui.MainActivity;

import java.util.List;

public class ListaRowAdapter extends RecyclerView.Adapter<ListaRowAdapter.LineHolder> {

    public List<RowBean> mRows;
    private Context context;
    private RowsDAO rowsDAO;
    private MainActivity activity;
    private ApiService apiService;
    private ListaRowAdapter adapter;

    public ListaRowAdapter(List<RowBean> beans, Context context, RowsDAO rowsDAO, MainActivity activity) {
        this.mRows = beans;
        this.context = context;
        this.rowsDAO = rowsDAO;
        this.activity = activity;
        this.adapter = this;

    }

    @Override
    public LineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_rows, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final LineHolder lineHolder, final int i) {

        lineHolder.label.setText(mRows.get(i).getDescricao());

        ((LinearLayout) lineHolder.linear).removeAllViews();
        final Button button = new Button(lineHolder.linear.getContext());

        if (mRows.get(i).getAtivo().equals("0")) {
            button.setText("OFF");
        } else {
            button.setText("ON");
        }


        button.setId(mRows.get(i).getId());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                apiService = new ApiService(context, activity);
                final String[] arr = {mRows.get(i).getUrl()};
                apiService = new ApiService(rowsDAO, mRows.get(i));
                apiService.execute(arr);

               // String novaURL = string.replace("on", "off");

                RowBean objBean = mRows.get(i);
                if (objBean.getAtivo().equals("0")) {
                    objBean.setUrl(objBean.getUrl().replace("off", "on"));
                    objBean.setAtivo("1");
                } else {
                    objBean.setUrl(objBean.getUrl().replace("on", "off"));
                    objBean.setAtivo("0");
                }

                rowsDAO.updateRow(objBean);
                activity.atualizaLista();

            }
        });

        lineHolder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alert(mRows.get(i));
                return false;
            }
        });


        ((LinearLayout) lineHolder.linear).addView(button);

        lineHolder.itemView.setLongClickable(true);
    }

    @Override
    public int getItemCount() {
        return mRows != null ? mRows.size() : 0;
    }

    public void alert(final RowBean obj) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context).create();
        alertDialog.setTitle("Excluir o Registro ?");
        alertDialog.setMessage("\n" + obj.getDescricao() + "\nurl: " + obj.getUrl() + "\nativo: " + obj.getAtivo());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (rowsDAO.excluir(obj)) {
                            Toast.makeText(context, "Excluído !", Toast.LENGTH_SHORT).show();
                            activity.atualizaLista();
                        } else {
                            Toast.makeText(context, "ERROR !", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    class LineHolder extends RecyclerView.ViewHolder {

        public TextView URL, label;
        public View linear;
        public CardView card;

        public LineHolder(View itemView) {
            super(itemView);
            // URL = (TextView) itemView.findViewById(R.id.item_lista_url);
            label = (TextView) itemView.findViewById(R.id.item_lista_descricao);
            linear = (View) itemView.findViewById(R.id.viewLinear);
            card = (CardView) itemView.findViewById(R.id.item_lista_row_cardview);
        }

    }
}
