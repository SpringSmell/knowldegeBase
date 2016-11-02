/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.yidingliu.dev.knowldegelibrary.b.BaseActivity;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowledgebase.R;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/8
 * @modifyInfo1 chriszou-16/10/8
 * @modifyContent
 */
public class TestNIOActivity extends BaseActivity {

    public static final String TAG="TestNIOActivity";

    @Override public int onResultLayoutResId () {

        return R.layout.activity_nio;
    }

    @Override public void onInitView ( BaseParentViewHolder holder ) {

    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {

        holder.setOnClickListener ( R.id.actionBtn, new View.OnClickListener () {

            @Override public void onClick ( View v ) {
//                CharBuffer buffer = CharBuffer.allocate ( 100 );
//                while ( fillBuffer ( buffer ) ) {
//                    buffer.flip ();
//                    drainBuffer ( buffer );
//                    buffer.clear ();
//                }
                    test ();

            }
        } );

    }

    private void test(){

        try {
//            writeFile("test");

            String filePath=Environment.getExternalStorageDirectory ().getAbsolutePath ()+File.separatorChar+"test-1609268045.txt";
            File file=new File ( filePath );
            if(!file.exists ()){
                file.createNewFile ();
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile ( file,"rw" );
            FileChannel channel=randomAccessFile.getChannel ();
            long total=channel.size ();
            String result=readData ( total,channel.map ( FileChannel.MapMode.READ_ONLY ,0, total));
            Log.e ( TAG, "read result: "+ result ,new RuntimeException ( "test........" ));
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }

    private void writeFile ( String fileName ) throws IOException {

        File file=File.createTempFile ( "test",".txt",Environment.getExternalStorageDirectory () );
        RandomAccessFile randomAccessFile=new RandomAccessFile ( file, "rw");
        randomAccessFile.seek ( 100 );
        FileChannel fileChannel=randomAccessFile.getChannel ();
        ByteBuffer buffer=ByteBuffer.allocateDirect ( 100 );
        putData ( buffer,fileChannel );

        randomAccessFile.seek ( 50 );
        putData ( buffer,fileChannel );

        fileChannel.position (20);
        putData ( buffer,fileChannel );

        putData ( 0,buffer,fileChannel );
        putData ( 100000,buffer,fileChannel );
        fileChannel.close ();
    }

    private void putData(ByteBuffer buffer,FileChannel channel) throws IOException {
        String str="*<--location "+channel.position ();
        buffer.clear ();
        buffer.put ( str.getBytes ("US-ASCII") );
        buffer.flip ();
        channel.write ( buffer );
    }

    private void putData(long position ,ByteBuffer buffer,FileChannel channel) throws IOException{
        String string=" *<--location "+position;
        buffer.clear ();
        buffer.put ( string.getBytes ( "US-ASCII" ) );
        buffer.flip ();
        channel.position (position);
        channel.write ( buffer );
    }

    private String readData(long total, MappedByteBuffer buffer) throws UnsupportedEncodingException {
        byte[] bytes=new byte[( int ) total];

        for(int i=0;i<total;i++){
            bytes[i]=buffer.get (i);
        }
        return new String (bytes, "utf-8");
    }

    private static void drainBuffer ( CharBuffer buffer ) {

        while ( buffer.hasRemaining () ) {
            System.out.print ( buffer.get () );
        }

        System.out.println ( "" );
    }

    private static boolean fillBuffer ( CharBuffer buffer ) {

        if ( index >= strings.length ) {
            return ( false );
        }
        String string = strings[index++];
        for ( int i = 0 ; i < string.length () ; i++ ) {
            buffer.put ( string.charAt ( i ) );
        }
        return ( true );
    }

    private static int index = 0;
    private static String[] strings = {
            "A random string value" ,
            "The product of an infinite number of monkeys" ,
            "Hey hey we're the Monkees" ,
            "Opening act for the Monkees: Jimi Hendrix" ,
            "'Scuse me while I kiss this fly" , // Sorry Jimi ;-)
            "Help Me! Help Me!" ,};
}
