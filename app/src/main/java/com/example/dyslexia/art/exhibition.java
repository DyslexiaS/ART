package com.example.dyslexia.art;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class exhibition extends AppCompatActivity {
    FTPClient mFtpClient;
    String name;
    //EditText meetingpaper_year;
    int exibition_year;
    boolean FTP_file_exist;
    EditText exibition_work;
    EditText exibition_teacher;
    EditText exibition_other_teacher;
    EditText exibition_name;
    EditText exibition_unit;
    EditText exibition_dateend;
    EditText exibition_country;
    EditText exibition_startdate;
    EditText exibition_add;
    boolean iswrite=false;
    String file_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibition);
        FTP_file_exist=false;
        check_name();
        file_name=name+"_exhibition.txt";
        iswrite=false;
        new Thread(runnable).start();
        initial();
    }
    public void initial()
    {
        NumberPicker year_pick = (NumberPicker) findViewById(R.id.exhibition_year_pk);
        year_pick.setMinValue(99);
        year_pick.setMaxValue(120);
        year_pick.setValue(107);
        year_pick.setWrapSelectorWheel(true);
        year_pick.setOnLongPressUpdateInterval(1000);
        year_pick.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                exibition_year = newVal;
            }
        });
        exibition_unit=(EditText)this.findViewById((R.id.exhibition_unit_tb));
        exibition_work=(EditText)this.findViewById((R.id.exhibition_work_tb));
        exibition_teacher=(EditText)this.findViewById((R.id.exhibition_teacher_tb));
        exibition_other_teacher=(EditText)this.findViewById((R.id.exhibition_other_teacher_tb));
        exibition_name=(EditText)this.findViewById((R.id.exhibition_name_tb));
        exibition_startdate=(EditText)this.findViewById((R.id.exhibition_startdate_tb));
        exibition_dateend=(EditText)this.findViewById((R.id.exhibition_dateend_tb));
        exibition_country=(EditText)this.findViewById((R.id.exhibition_country_tb));
        exibition_add=(EditText)this.findViewById((R.id.exhibition_add_tb));
        //exibition_year.setText("107");
        exibition_year=107;
        exibition_work.setText("藝術研究所");
        exibition_teacher.setText(name);
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
            name=temp;
            fr.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void writefile()
    {
        File dir = this.getFilesDir();
        String path=dir.getPath();
        String filename=file_name;
        File file = new File(path, filename);
        try{
            FileOutputStream Output = new FileOutputStream(file, true);
            Output.write(Integer.toString(exibition_year).getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_work.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_teacher.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_other_teacher.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_name.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_unit.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_country.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_startdate.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_dateend.getText().toString().getBytes("Big5"));
            Output.write("\t".getBytes("Big5"));
            Output.write(exibition_add.getText().toString().getBytes("Big5"));
            Output.write("\n".getBytes("Big5"));
            Output.close();
        }catch (Exception e)
        {
            Log.e("err","cannot write file!!");
        }

    }
    public void check_file()
    {
        File dir = this.getFilesDir();
        String path=dir.getPath()+"/"+file_name;
        File file=new File(path);
        Log.d("file",file.getAbsolutePath());
        OutputStream outputStream = null;
        if((!file.exists())) {

            try{
                boolean result;
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        path));
                mFtpClient.setFileType(FTP.BINARY_FILE_TYPE);
                if(!FTP_file_exist) {
                     result= mFtpClient.retrieveFile("example3.txt", outputStream);
                }
                else
                {
                    result= mFtpClient.retrieveFile(file_name, outputStream);
                }
                if (result) Log.v("download result", "succeeded");
                outputStream.close();
            }catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),  "Error", Toast.LENGTH_SHORT).show();
            }


        }
        else
        {
            Log.d("e","file exist");
            try{
                boolean result;
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        path));
                mFtpClient.setFileType(FTP.BINARY_FILE_TYPE);
                if(FTP_file_exist){

                    result =mFtpClient.retrieveFile(file_name,outputStream);
                }
                else
                {
                    result=false;
                }

                if (result) Log.v("download result", "succeeded");
                outputStream.close();
            }catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),  "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void check_name()
    {
        File dir = this.getFilesDir();
        String path=dir.getPath()+"/name.txt";
        File file=new File(path);
        Log.d("file",file.getAbsolutePath());
        if((!file.exists()))
        {
            new AlertDialog.Builder(exhibition.this)
                    .setTitle("Error")
                    .setMessage("回前頁去設定姓名")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
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
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            if(!iswrite) {
                boolean isconnect = connnectingwithFTP("140.116.17.99", "administrator", "19940801#em52500@");
            }
            else
            {
                uploadFTP("140.116.17.99", "administrator", "19940801#em52500@");
                iswrite=true;
            }
        }
    };
    public boolean connnectingwithFTP(String ip, String userName, String pass) {
        boolean status = false;
        try {
            mFtpClient = new FTPClient();
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
                    if((file_name).equals(mFileArray[i].getName()))
                    {    Log.d("file","true");
                        FTP_file_exist=true;
                    }
                    else {
                        Log.d("file", "false");
                    }
                    Log.d("file",mFileArray[i].getName());
                }
                Log.d("Size", String.valueOf(mFileArray.length));
                check_file();
                try{
                    mFtpClient.disconnect();
                }catch (Exception e)
                {
                    Log.e("err","Log out error");
                }
            }
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean uploadFTP(String ip, String userName, String pass) {
        boolean status = false;
        try {
            mFtpClient = new FTPClient();
            mFtpClient.setConnectTimeout(10 * 1000);
            mFtpClient.connect(InetAddress.getByName(ip));
            status = mFtpClient.login(userName, pass);
            Log.e("isFTPConnected", String.valueOf(status));
            if (FTPReply.isPositiveCompletion(mFtpClient.getReplyCode())) {
                mFtpClient.setFileType(FTP.ASCII_FILE_TYPE);
                mFtpClient.enterLocalPassiveMode();
                File dir = this.getFilesDir();
                String path=dir.getPath()+"/"+file_name;
                try{
                    FileInputStream in = new FileInputStream(new File(path));
                    boolean result = mFtpClient.storeFile(file_name, in);
                    in.close();
                    if (result) Log.v("upload result", "succeeded");
                }
                catch (Exception e)
                {
                    Log.e("err","cannot do");
                }
                try{
                    mFtpClient.disconnect();
                }catch (Exception e)
                {
                    Log.e("err","Log out error");
                }
            }
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void OK_clk(View view)
    {
        if(TextUtils.isEmpty(exibition_work.getText()))
        {
            Toast.makeText(getApplicationContext(),"單位不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_teacher.getText()))
        {
            Toast.makeText(getApplicationContext(),"作者姓名不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_other_teacher.getText()))
        {
            Toast.makeText(getApplicationContext(),"其他作者姓名不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_name.getText()))
        {
            Toast.makeText(getApplicationContext(),"展演活動名稱不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty( exibition_unit.getText()))
        {
            Toast.makeText(getApplicationContext(),"展演主辦單位不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_country.getText()))
        {
            Toast.makeText(getApplicationContext(),"展演活動辦理國別/地區代碼不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_startdate.getText()))
        {
            Toast.makeText(getApplicationContext(),"展演活動開始日期不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_dateend.getText()))
        {
            Toast.makeText(getApplicationContext(),"展演活動結束日期不得為空", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(exibition_add.getText()))
        {
            Toast.makeText(getApplicationContext(),"補充說明不得為空", Toast.LENGTH_SHORT).show();
        }else
        {
            writefile();
            iswrite=true;
            new Thread(runnable).start();
            finish();
        }
    }
    public void cancel_clk(View view)
    {
        finish();
    }

}
