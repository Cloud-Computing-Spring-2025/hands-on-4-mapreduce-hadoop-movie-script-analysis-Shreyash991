package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class CharacterWordMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private Text characterWord = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
         String line=value.toString();
         if(line.contains(":")){
            String[] part=line.split(":",2);
            String dialogue=part[1].trim().toLowerCase();

            String[] words=dialogue.split("\\s+");
            for(String word:words){
                word=word.replaceAll("[^a-zA-Z]","");
                if(!word.isEmpty()){
                    context.write(new Text(word), new IntWritable(1));
                    context.getCounter("Movie Metrics", "Total Words Processed").increment(1);
                    context.getCounter("Movie Metrics", "Total Characters Processed").increment(word.length());
                    }
                }
                    context.getCounter("Movie Metrics", "Total Lines Processed").increment(1);
                 }


    }
}
