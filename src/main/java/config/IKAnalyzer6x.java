package config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * Created by 自由翱翔峰 on 2018/12/6 22:53
 */
//要想低版本的ik分词器匹配高版本的lucene必须重写ik中的两个类，实现两个接口
public class IKAnalyzer6x extends Analyzer {
    private boolean useSmart;

    public boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart( boolean useSmart ) {
        this.useSmart=useSmart;
    }

     public IKAnalyzer6x(){
         this(false);//IK分词器Lucene Analyzer接口实现类
         //默认细粒度切分算法
     }
     //Ik分词器LuceneAnalyzer接口实现类；当为true时，分词器进行智能切分
     public IKAnalyzer6x(boolean useSmart){
         super();
         this.useSmart = useSmart;
     }
     //重写新版本的createComponents：重载Analyzer接口，构造分词组件
    @Override
    protected TokenStreamComponents createComponents( String fieldName ) {
        Tokenizer _IKTonkenizer = new IKTokenizer6x ( this.useSmart() );
        return  new TokenStreamComponents ( _IKTonkenizer );
    }
}
