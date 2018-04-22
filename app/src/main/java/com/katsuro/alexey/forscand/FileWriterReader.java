package com.katsuro.alexey.forscand;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by alexey on 4/20/18.
 */

public class FileWriterReader {

    public static final String TAG = FileWriterReader.class.getSimpleName();

    private Context  mContext;

    public FileWriterReader(Context context) {
        mContext = context;
    }

    public void writeToFile(String data, String fileName) {
        Log.i(TAG, "writeToFile");

        try {
            File file = getStorageDir(mContext,fileName);
            BufferedWriter bufferedWriter =  new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data);
            bufferedWriter.close();
            Log.i(TAG, "File was saved in " + file.getAbsolutePath());
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
            e.printStackTrace();
        }
        }

    public String readFromFile(String fileName) {
        Log.i(TAG, "readFromFile");
        File file = getStorageDir(mContext,fileName);
        String result = "";
        try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                bufferedReader.close();
                result = stringBuilder.toString();
                Log.i(TAG, "Read: " + result);
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return result;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getStorageDir(Context context,String fileName) {

            File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (externalFilesDir == null) {
                return null;
            }
            return new File(externalFilesDir, fileName);
    }
}
