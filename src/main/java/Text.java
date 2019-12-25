import config.IKAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.StringReader;

public class Text {
     private static String str = "厉害了我的哥！中国环保部门即将发布治理北京雾霾的方法";
     public static void main(String args[])throws Exception{
         Analyzer analyzer = new IKAnalyzer6x(true);
         StringReader reader = new StringReader(str);
         TokenStream tokenStream = analyzer.tokenStream(str,reader);
         tokenStream.reset();
         CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
         while (tokenStream.incrementToken()){
             System.out.print(charTermAttribute.toString()+"/");
         }

     }
}
