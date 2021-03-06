package in.thyferny.lucene;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

import in.thyferny.nlp.MyNLP;

public class HanLPTokenizerFactory extends TokenizerFactory
{
    private boolean enableIndexMode;
    private boolean enablePorterStemming;

    public HanLPTokenizerFactory(Map<String, String> args)
    {
        super(args);
        enableIndexMode = getBoolean(args, "enableIndexMode", true);
        enablePorterStemming = getBoolean(args, "enablePorterStemming", false);
    }

    @Override
    public Tokenizer create(AttributeFactory factory)
    {
        return new HanLPTokenizer(MyNLP.newSegment().enableOffset(true).enableIndexMode(enableIndexMode), null, enablePorterStemming);
    }
}