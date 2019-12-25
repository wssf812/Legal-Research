import config.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;

public class Index {
    public static void indexFile(String datadir,String indexdir)throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(datadir));
//        InputStreamReader reader = new InputStreamReader(inputStream,"utf-8");
        ArrayList<String> arrayList = new ArrayList<String>();
        String line = "";
//        String everyLine = "";
        while ((line=reader.readLine())!=null){
//            everyLine = line;
//            System.out.println(everyLine);
            arrayList.add(line);
        }
        reader.close();
        Directory dir = FSDirectory.open(Paths.get ( indexdir ));
        Analyzer analyzer = new IKAnalyzer6x(true);
        IndexWriterConfig  inc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir,inc);
        long start = System.currentTimeMillis();
        for (int i=1;i<arrayList.size();i++){
             String[] result = arrayList.get(i).split(",");
//        System.out.println(result[0]);
//        System.out.println(result[1]);
//        System.out.println(result[2]);
//        System.out.println(result[3]);
//        System.out.println(result[4]);
            Document document = new Document();
            document.add(new StringField("id",result[0], Field.Store.YES ));
            document.add(new TextField("title",result[1], Field.Store.YES ));
            document.add(new TextField("content",result[2], Field.Store.YES ));
            document.add(new StringField("ftime",result[3], Field.Store.YES ));
            document.add(new StringField("ltime",result[4], Field.Store.YES ));
            writer.addDocument(document);
        }
        long end = System.currentTimeMillis();

        writer.close();
        System.out.print("索引建立完成,共耗时"+(end-start)+"毫秒，共索引了"+arrayList.size()+"个文件");

    }
    public static void main(String args[])throws Exception{
        String indexdir = "D:\\Spring\\FalvSystem\\src\\main\\java\\index" ;
        String datadir = "F:\\data\\court_wenshu_gov_content.csv";
        indexFile(datadir,indexdir);

    }
}
