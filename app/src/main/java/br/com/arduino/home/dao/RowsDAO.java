package br.com.arduino.home.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.arduino.home.bean.RowBean;
import br.com.arduino.home.connection.ConnectionFactory;

import java.util.ArrayList;
import java.util.List;

public class RowsDAO {


    private ConnectionFactory connection;
    private List<RowBean> list;

    public RowsDAO(Context context) {
        connection = new ConnectionFactory(context);
    }

    public boolean insert(RowBean bean) {
        try {
            ContentValues vls = new ContentValues();
            vls.put("url", bean.getUrl());
            vls.put("descricao", bean.getDescricao());
            vls.put("ativo", bean.getAtivo());
            connection.getWritableDatabase().insert("controls", null, vls);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        connection.close();
        return true;
    }


    public List<RowBean> getRows() {
        list = new ArrayList<>();
        String sql = "select * from controls;";
        Cursor crs = connection.getWritableDatabase().rawQuery(sql, null);
        while (crs.moveToNext()) {
            RowBean h = new RowBean(0, "", "", "");
            h.setId(crs.getInt(0));
            h.setUrl(crs.getString(1));
            h.setDescricao(crs.getString(2));
            h.setAtivo(crs.getString(3));
            list.add(h);
        }
        crs.close();
        connection.close();
        return list;
    }

    public void updateRow(RowBean bean) {
        ContentValues vls = new ContentValues();
        vls.put("id", bean.getId());
        vls.put("url", bean.getUrl());
        vls.put("descricao", bean.getDescricao());
        vls.put("ativo", bean.getAtivo());
        String[] params = {String.valueOf(bean.getId())};
        connection.getWritableDatabase().update("controls", vls, "id=?", params);
        connection.close();
    }

    public boolean excluir(RowBean bean) {
        String[] id = {String.valueOf(bean.getId())};
        try {
            connection.getWritableDatabase().delete("controls", "id=?", id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
