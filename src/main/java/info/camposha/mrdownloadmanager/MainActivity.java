package info.camposha.mrdownloadmanager;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private DownloadManager downloadManager;
    private String fileName=null;
    private long downLoadId;


    /**
     * Инициализация менеджера загрузок
     */
    private void initializeDownloadManager() {
        downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        fileName="Voyager";
    }

    /**
     * Задайте название и описание загружаемого файла, которое будет отображаться в уведомлениях.
     */
    private void downloadFile(){
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse("https://raw.githubusercontent.com/Oclemy/SampleJSON/master/spacecrafts/voyager.jpg"));
        request.setTitle("Voyager")
                .setDescription("File is downloading...")
                .setDestinationInExternalFilesDir(this,
                        Environment.DIRECTORY_DOWNLOADS,fileName)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        /** Поставьте загрузку в очередь.Загрузка начнется автоматически, как только менеджер загрузок будет готов
            к ее выполнению и появится возможность подключения.
         */
        downLoadId=downloadManager.enqueue(request);
    }

    /**
     * Отмените загрузки и удалите их из диспетчера загрузок.
     * Если есть загруженный файл, частичный или полный, он удаляется.
     */
    private void deleteDownloadedFile(){
        downloadManager.remove(downLoadId);
    }
    private void openOurDownload(){
        try {
            downloadManager.openDownloadedFile(downLoadId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "The File is Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Просмотр всех загрузок в менеджере загрузки
     */
    private void viewAllDownloads(){
        Intent intent=new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }

    /**
     * Обработка нажатий кнопок - Handle button clicks
     * @param view
     */
    public void clickView(View view){
        switch (view.getId()){
            case R.id.downloadBtn:
                downloadFile();
                break;
            case R.id.openDownloadBtn:
                openOurDownload();
                break;
            case R.id.viewDownloadsBtn:
                viewAllDownloads();
                break;
            case R.id.deleteBtn:
                deleteDownloadedFile();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDownloadManager();
    }
}
