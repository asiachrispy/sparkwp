package chap.mr;

/**
 * Description : some words
 * Author: chris
 * Date: 2014/9/5
 */

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CustomOutputFormat extends FileOutputFormat<LongWritable, Text> {

    private String prefix = "custom_";

    @Override
    public RecordWriter<LongWritable, Text> getRecordWriter(TaskAttemptContext job)
            throws IOException, InterruptedException {
        Path outputDir = FileOutputFormat.getOutputPath(job);
        String subfix = job.getTaskAttemptID().getTaskID().toString();
        Path path = new Path(outputDir.toString() + "/" + prefix + subfix.substring(subfix.length() - 5, subfix.length()));
        FSDataOutputStream fileOut = path.getFileSystem(job.getConfiguration()).create(path);
        return new CustomRecordWriter(fileOut);
    }

}
