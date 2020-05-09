import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import java.io.IOException;
import java.util.TreeSet;


class wordPair implements Comparable<wordPair> {
	double Frequency;
	String key;
	String value;

	wordPair(double Frequency, String key, String value){
		this.Frequency = Frequency;
		this.key = key;
		this.value = value;
	}

	public int compareTo(wordPair wordpair){
		if (this.Frequency >= wordpair.Frequency){
			return 1;
		}
		else{
			return -1;
		}
	}
}


public class relativeFrequency {
	public static void main(String[] args) throws Exception {

		Job job = new Job(new Configuration());
		job.setJarByClass(relativeFrequency.class);

		if(args.length != 2) {
			System.err.println("Useage <inputFile> <outputDir>");
			System.exit(2);
		}

		job.setNumReduceTasks(1);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);
		job.setCombinerClass(Merge.class);
		job.setReducerClass(Reduce.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

		boolean status = job.waitForCompletion(true);
        if(status){
        	System.exit(0);
        }
        else{
        	System.exit(1);
        }
	}

	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		public void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException {

			String sepRegex = " ";
			String seperator = ",";

			String[] words = text.toString().split(sepRegex);

			for(int i=1;i<words.length;i++){
				String tempString = words[i-1].toLowerCase()+seperator+words[i].toLowerCase();

				context.write(new Text(tempString), new Text("1"));
			}
		}
	}

	private static class Merge extends Reducer<Text, Text, Text, Text> {
		public void reduce(Text text, Iterable<Text> iterableValues, Context context) throws IOException, InterruptedException {

			int count = 0;
			for (Text value : iterableValues) {
				count = count + Integer.parseInt(value.toString());
			}
			context.write(text, new Text(String.valueOf(count)));
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {

		TreeSet<wordPair> treeSet = new TreeSet<wordPair>();
		double total = 0;

		public void reduce(Text text, Iterable<Text> iteratorValue, Context context) throws IOException, InterruptedException {
			int count = 0;
			String regex = ".*\\\\*";
			String seperator = ",";

			for (Text valueIn : iteratorValue){
				count = count + Integer.parseInt(valueIn.toString());
			}

			if (text.toString().matches(regex)){
				total = count;
			}
			else{
				String[] pairOfWords = text.toString().split(seperator);
				treeSet.add(new wordPair(count/total, pairOfWords[0], pairOfWords[1]));

				if (treeSet.size() > 100){
					treeSet.pollFirst();
				}
			}
		}

		protected void cleanup(Context context) throws IOException, InterruptedException{
			while (!treeSet.isEmpty()){
				wordPair pair = treeSet.pollLast();
				context.write(new Text(pair.key), new Text(pair.value));
			}
		}
	}
}
