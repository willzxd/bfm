package bigframe.workflows.BusinessIntelligence.RTG.exploratory;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import bigframe.workflows.HadoopJob;
import bigframe.workflows.BusinessIntelligence.RTG.exploratory.TwitterRankConstant;



public class GroupSenByProdHadoop extends HadoopJob {

	public GroupSenByProdHadoop(Configuration _mapred_config) {
		super(_mapred_config);
		// TODO Auto-generated constructor stub
	}

	static class GroupSenByProdMapper extends Mapper<LongWritable, Text, LongWritable, FloatWritable> {
		
		@Override
		protected void map(LongWritable key,
				Text value, final Context context)
						throws IOException, InterruptedException {	
			
			String [] fields = value.toString().split("\\|");
			
			context.write(new LongWritable(Long.parseLong(fields[0])),
						new FloatWritable(Float.parseFloat(fields[2])));
		}
	}
	
	static class GroupSenByProdReducer extends Reducer<LongWritable, FloatWritable, LongWritable, FloatWritable> {
		@Override
		protected void reduce(LongWritable key, Iterable<FloatWritable> values, 
	    		final Context context) throws IOException, InterruptedException {
			
			float ans = 0;
			
			for(FloatWritable value : values) {
				ans += value.get();
			}
			
			context.write(key, new FloatWritable(ans));
		}
	}
	
	
	@Override
	public Boolean runHadoop(Configuration mapred_config) {
		if(mapred_config == null)
			mapred_config = mapred_config();
		
		try {
			FileSystem fs = FileSystem.get(mapred_config);
		
			String hdfs_dir = TwitterRankConstant.GROUP_SEN_BY_PROD();
			Path outputDir = new Path(hdfs_dir);	
		

			if(fs.exists(outputDir))
				fs.delete(outputDir, true);
			
			// For hadoop 1.0.4
			mapred_config.set("mapred.textoutputformat.separator", "|");	
		
			Job job;
		
			job = new Job(mapred_config);
			
			FileInputFormat.addInputPath(job, new Path(TwitterRankConstant.JOIN_SEN_INFLUENCE()));
			
			FileOutputFormat.setOutputPath(job, outputDir);
			
			job.setJarByClass(GroupSenByProdHadoop.class);
			job.setJobName("Group sentiment score by product");
			

			job.setMapperClass(GroupSenByProdMapper.class);
			//job.setMapOutputKeyClass(Text.class);
			//job.setMapOutputValueClass(FloatWritable.class);
			
			job.setCombinerClass(GroupSenByProdReducer.class);
			job.setReducerClass(GroupSenByProdReducer.class);
			job.setOutputKeyClass(LongWritable.class);
			job.setOutputValueClass(FloatWritable.class);
			
			job.setNumReduceTasks(1);

			
			return job.waitForCompletion(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}
