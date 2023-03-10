package test3;

import java.io.IOException;
import java.util.*;


import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TopHitsUrl {

    public static class ngMap extends
            Mapper<Object ,Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            String line = value.toString();
            int firstquote = line.indexOf('\"');
            int firstwhitespace = line.indexOf(" ", firstquote);
            int secondwhitespace = line.indexOf(" ", firstwhitespace+1);
            if(firstwhitespace!=-1 && secondwhitespace!=-1){
                String url = line.substring(firstwhitespace+1, secondwhitespace);
                context.write(new Text(url), one);
            }
        }
    }

    public static class ngReduce extends
            Reducer<Text, IntWritable, Text, IntWritable>{

        private TreeMap<Hits, Text> tmap = new TreeMap<Hits, Text>(new hitscomp());

        public void reduce(Text key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {

            int sum = 0;
            for(IntWritable x : values){
                sum += x.get();
            }
            tmap.put(new Hits(sum), new Text(key));
            if(tmap.size() > 10){
                tmap.remove(tmap.firstKey());
            }
        }

        protected void cleanup(Context context) throws IOException, InterruptedException{
            for(Hits h: tmap.descendingMap().keySet()){
                context.write(tmap.get(h),new IntWritable(h.getHits()));
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "TopUrlCount");
        job.setJarByClass(TopHitsUrl.class);
        job.setMapperClass(ngMap.class);
        job.setReducerClass(ngReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setMapOutputKeyClass(Text.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true)? 0 : 1);
    }
}
