package com.troffo.pdfwriter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    public void download(View view) {
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "PDFDushyant");
        if (!directory.exists())
            directory.mkdir();

        File filename = new File(directory, ("PDF_" + Calendar.getInstance().getTimeInMillis() + ".pdf"));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(textView.getWidth(), textView.getHeight(), 0).create();

            // start a page
            PdfDocument.Page page = document.startPage(pageInfo);
            textView.draw(page.getCanvas());

            document.finishPage(page);
            try {
                FileOutputStream outputStream = new FileOutputStream(filename);
                document.writeTo(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            document.close();
        }
    }
}
