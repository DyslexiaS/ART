package com.example.dyslexia.art;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class fileupdate extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileupdate);
        checkfile(false);
        new Thread(runnable).start();
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            connnectingwithFTP("140.116.17.99", "administrator", "19940801#em52500@");
        }
    };
    public void checkfile(boolean opendialog)
    {
        File dir = this.getFilesDir();
        String path=dir.getPath()+"/name.txt";
        File file=new File(path);
        Log.d("file",file.getAbsolutePath());
        if((!file.exists())||opendialog)
        {
            Log.d("file","file not exist");
            final View item = LayoutInflater.from(fileupdate.this).inflate(R.layout.filedialog, null);
            new AlertDialog.Builder(fileupdate.this)
                    .setView(item)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = (EditText) item.findViewById(R.id.username);
                            String name = editText.getText().toString();
                            if(TextUtils.isEmpty(name)){
                                Toast.makeText(getApplicationContext(),"空的未儲存", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),  name+"已儲存", Toast.LENGTH_SHORT).show();
                                writename(name);
                            }
                        }
                    })
                    .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),  "取消", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        }
        else
        {
            Log.d("file","file  exist");
            readname();
        }
    }
    public void writename(String name)
    {
        File dir = this.getFilesDir();
        String path=dir.getPath();
        String filename="name.txt";
        File file = new File(path, filename);
        try{
            FileOutputStream Output = new FileOutputStream(file, false);
            Output.write(name.getBytes("UTF-8"));
            Output.close();
        }catch (Exception e)
        {
            Log.e("err","cannot write file!!");
        }

    }
    public void readname()
    {
        File dir = this.getFilesDir();
        String path=dir.getPath();
        String filename="name.txt";
        File file = new File(path, filename);

        try{
            //建立FileReader物件，並設定讀取的檔案為CheckFlie.txt
            FileReader fr = new FileReader(file);
            //將BufferedReader與FileReader做連結
            BufferedReader bufFile = new BufferedReader(fr);
            String readData = "";
            String temp = bufFile.readLine(); //readLine()讀取一整行
            Log.d("file",temp);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void connnectingwithFTP(String ip, String userName, String pass) {
        boolean status = false;
        try {
            FTPClient mFtpClient = new FTPClient();
            mFtpClient.setConnectTimeout(10 * 1000);
            mFtpClient.connect(InetAddress.getByName(ip));
            status = mFtpClient.login(userName, pass);
            Log.e("isFTPConnected", String.valueOf(status));
            if (FTPReply.isPositiveCompletion(mFtpClient.getReplyCode())) {
                mFtpClient.setFileType(FTP.ASCII_FILE_TYPE);
                mFtpClient.enterLocalPassiveMode();
                FTPFile[] mFileArray = mFtpClient.listFiles();
                for (int i=0;i<mFileArray.length;i++)
                {
                    Log.d("file",mFileArray[i].getName());
                }
                Log.d("Size", String.valueOf(mFileArray.length));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void rename_clk(View view)
    {
        checkfile(true);
    }
    public void delete_clk(View view)
    {
        File dir = this.getFilesDir();
        String path=dir.getPath()+"/name.txt";
        File file=new File(path);
        Log.d("file",file.getAbsolutePath());
        if((!file.exists()))  {
        }
        else
            {
                file.delete();
                Toast.makeText(getBaseContext(),
                        "已刪除",
                        Toast.LENGTH_SHORT).show();
            }
    }
}

