package com.katsuro.alexey.forscand;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by alexey on 4/20/18.
 */

public class FileWriterReader {

    public static final String TAG = FileWriterReader.class.getSimpleName();
    private static final String MAPS_FOLDER = "saved_maps";
    private AssetManager mAssets;

    private Context  mContext;

    public FileWriterReader(Context context) {
        mContext = context;
        mAssets = mContext.getAssets();
    }

    public void writeToFile(String data, String fileName) {
        Log.i(TAG, "writeToFile");

        try {
            File file = getStorageDir(mContext,fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();

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
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
            e.printStackTrace();
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

    public String readFromAssets(String filename) {
        Log.d(TAG, "readFromAssets");
        String assetPath = MAPS_FOLDER + "/" + filename;
        Log.d(TAG, "assetPath: " + assetPath);
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
        StringBuilder out = new StringBuilder();
        try {
            InputStream mapData = mAssets.open(assetPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(mapData));
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public File getStorageDir(Context context,String fileName) {

        String root = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString();
        File myDir = new File(root + "/" + MAPS_FOLDER);
        myDir.mkdirs();
            if (myDir == null) {
                return null;
            }
            return new File(myDir, fileName);
    }
}
