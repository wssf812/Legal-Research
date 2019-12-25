import config.IKAnalyzer6x;
import javafx.scene.control.IndexRange;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Search {
    public static ArrayList<DataModel> Search(String indexdir,String query) throws Exception {
        Directory dir = FSDirectory.open(Paths.get(indexdir));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer6x(true);
        //多域查找
        String fields[] = {"id", "title", "content", "ftime", "ltime"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        Query multiquery = parser.parse(query);
        //查询时间
        long start = System.currentTimeMillis();
        TopDocs hits = searcher.search(multiquery, 100);
        long end = System.currentTimeMillis();
        System.out.println("匹配‘" + query + "’耗时" + (end - start) + "毫秒,查询到" + hits.totalHits + "个记录");
        //常亮

        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color ='red'>", "</font></b>");

        QueryScorer titileScorer = new QueryScorer(multiquery);
        Highlighter titlehighlighter = new Highlighter(simpleHTMLFormatter, titileScorer);

        QueryScorer contentScorer = new QueryScorer(multiquery);
        Highlighter contenthighlighter = new Highlighter(simpleHTMLFormatter,contentScorer);

        ArrayList<DataModel> dataList = new ArrayList<>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String id = doc.get("id");
            String content = doc.get("content");
            String title = doc.get("title");

            TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
            Fragmenter fragmenter = new SimpleSpanFragmenter(titileScorer);
            titlehighlighter.setTextFragmenter(fragmenter);
            String titlelight = titlehighlighter.getBestFragment(tokenStream, title);

            tokenStream = analyzer.tokenStream("content",new StringReader(content));
            fragmenter = new SimpleSpanFragmenter(contentScorer);
            contenthighlighter.setTextFragmenter(fragmenter);
            String contentlight = contenthighlighter.getBestFragment(tokenStream,content);
            DataModel dataModel = new DataModel(id,titlelight!=null?titlelight:title,contentlight!=null?contentlight:content);
            dataList.add(dataModel);
            }
        reader.close();
        return dataList;
    }

    public static void main(String args[])throws Exception{
        String indexdir = "D:\\Spring\\FalvSystem\\src\\main\\java\\index" ;
        String query = "郑某某";
        System.out.println(Search(indexdir,query));

    }
}
