package chap.mr;

/**
 * Description : some words
 * Author: chris
 * Date: 2014/9/5
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FileOutputFormatDriver {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();//config();

        Path in = new Path("/test/wp");
        Path out = new Path("/test/wpout");
        boolean delete = out.getFileSystem(conf).delete(out, true);
        System.out.println("deleted " + out + "?" + delete);
        Job job = new Job(conf, "fileouttputformat test job");
        job.setJarByClass(FileOutputFormatDriver.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(CustomOutputFormat.class);

        job.setMapperClass(Mapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);
          job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        job.setNumReduceTasks(2);
        job.setReducerClass(Reducer.class);
        FileInputFormat.setInputPaths(job, in);
        FileOutputFormat.setOutputPath(job, out);

        job.waitForCompletion(true);
    }
}
