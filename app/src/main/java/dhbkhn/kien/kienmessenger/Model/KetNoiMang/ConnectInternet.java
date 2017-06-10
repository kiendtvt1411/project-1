package dhbkhn.kien.kienmessenger.Model.KetNoiMang;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kiend on 10/9/2016.
 */
public class ConnectInternet extends AsyncTask<String, Void, String> {
    String path;
    List<HashMap<String,String>>attrs;
    StringBuffer dulieu;
    boolean method = true;

    public ConnectInternet(String path, List<HashMap<String, String>> attrs) {
        this.path = path;
        this.attrs = attrs;
        method = false;
    }

    public ConnectInternet(String path) {
        this.path = path;
        method = true;
    }

    private String methodGet(HttpURLConnection connection) {
        String data = "";
        try {
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            dulieu = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                dulieu.append(line);
                line = reader.readLine();
            }
            data = dulieu.toString();
            reader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String methodPost(HttpURLConnection connection) {
        String key = "";
        String value = "";
        String data = "";
        try {
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();
            int count = attrs.size();
            for(int i = 0;i<count;i++) {
                for (Map.Entry<String, String> entry : attrs.get(i).entrySet()) {
                    key = entry.getKey();
                    value = entry.getValue();
                }
                builder.appendQueryParameter(key, value);
            }
            String query = builder.build().getEncodedQuery();

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter writer = new BufferedWriter(outputStreamWriter);

            writer.write(query);
            writer.flush();
            writer.close();
            outputStreamWriter.close();
            outputStream.close();

            data = methodGet(connection);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected String doInBackground(String... params) {
        String data = "";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (method) {
                data = methodGet(connection);
            }else {
                data = methodPost(connection);
                Log.d("ktraa", data);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
