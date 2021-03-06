
package in.thyferny.nlp.corpus.dictionary;


import static in.thyferny.nlp.utility.Predefine.logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TMDictionaryMaker implements ISaveAble
{
    Map<String, Map<String, Integer>> transferMatrix;

    public TMDictionaryMaker()
    {
        transferMatrix = new TreeMap<String, Map<String, Integer>>();
    }

    
    public void addPair(String first, String second)
    {
        Map<String, Integer> firstMatrix = transferMatrix.get(first);
        if (firstMatrix == null)
        {
            firstMatrix = new TreeMap<String, Integer>();
            transferMatrix.put(first, firstMatrix);
        }
        Integer frequency = firstMatrix.get(second);
        if (frequency == null) frequency = 0;
        firstMatrix.put(second, frequency + 1);
    }

    @Override
    public String toString()
    {
        Set<String> labelSet = new TreeSet<String>();
        for (Map.Entry<String, Map<String, Integer>> first : transferMatrix.entrySet())
        {
            labelSet.add(first.getKey());
            labelSet.addAll(first.getValue().keySet());
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(' ');
        for (String key : labelSet)
        {
            sb.append(',');
            sb.append(key);
        }
        sb.append('\n');
        for (String first : labelSet)
        {
            Map<String, Integer> firstMatrix = transferMatrix.get(first);
            if (firstMatrix == null) firstMatrix = new TreeMap<String, Integer>();
            sb.append(first);
            for (String second : labelSet)
            {
                sb.append(',');
                Integer frequency = firstMatrix.get(second);
                if (frequency == null) frequency = 0;
                sb.append(frequency);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean saveTxtTo(String path)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
            bw.write(toString());
            bw.close();
        }
        catch (Exception e)
        {
            logger.warning("在保存转移矩阵词典到" + path + "时发生异常" + e);
            return false;
        }
        return true;
    }
}
