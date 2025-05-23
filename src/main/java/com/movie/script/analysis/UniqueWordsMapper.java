package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UniqueWordsMapper extends Mapper<Object, Text, Text, Text> {

    private Text character = new Text();
    private Text word = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
                String line = value.toString();
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String chars = parts[0].trim();
                    String dialogue = parts[1].trim().toLowerCase();
                    
                    String[] words = dialogue.split("\\s+");
                    for (String word : words) {
                        word = word.replaceAll("[^a-zA-Z]", "");
                        if (!word.isEmpty()) {
                            context.write(new Text(chars), new Text(word));
                            context.getCounter("Movie Metrics", "Total Unique Words Identified").increment(1);
                        }
                    }
                }
}
}