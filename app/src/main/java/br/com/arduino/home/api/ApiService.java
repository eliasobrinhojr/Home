package br.com.arduino.home.api;

import android.os.AsyncTask;
import android.util.Log;
import br.com.arduino.home.bean.RowBean;
import br.com.arduino.home.dao.RowsDAO;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService extends AsyncTask<String, Integer, Integer> {

    private RowsDAO rowsDAO;
    private RowBean rowBean;

    public ApiService(RowsDAO rowsDAO, RowBean bean) {
        this.rowsDAO = rowsDAO;
        this.rowBean = bean;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Integer doInBackground(String... params) {
        String urlobj;

        try {
            String url = params[0];
            urlobj = get(url);


            if (!urlobj.equals("net_fail")) {
                JSONObject obj = new JSONObject(urlobj);
                if (!obj.has("erro")) {
                    Log.d("asynctask", obj.getString("msg"));
                    Log.d("asynctask", obj.getString("cod"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer numero) {
        ;
    }

    protected void onProgressUpdate(Integer... params) {

    }

    public final String get(String url_lida) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(url_lida);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            conn.disconnect();
        } catch (Exception e) {
            // e.printStackTrace();
            result.append("net_fail");
        }
        return result.toString();
    }
}
