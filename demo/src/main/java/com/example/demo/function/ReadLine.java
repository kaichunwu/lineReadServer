package com.example.demo.function;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.TreeMap;

import com.example.demo.domain.StatusString;

/**
 * This class is used to read and store lines from the given file
 * Use File Read class RandomAccessFile
 * The reason use this class is because this class can point to lines by pointers,
 * which can be saved in a TreeMap for future reuse.
 * Default splitLine is 10000, which means save pointer each 10000 lines.
 * Can change fileAddress and splitLine by command line.
 * Using args like --file.name=String and --file.count=long.
 *
 */

public class ReadLine {

	private String fileAddress;

	public ReadLine(String fileAddress,long fileCount) {
		super();
		this.fileAddress = fileAddress;
		this.splitLine = fileCount;
		buildCache();
	}

	public ReadLine() {
		super();
		this.fileAddress = "";
		this.fileNotFound = true;
	}

	public String getFileAddress() {
		return fileAddress;
	}
	
	/**
	 * Default return StatusString if some errors occur
	 * @param err
	 * @return error String with status code 413
	 */
	private StatusString someError(String err) {
		return new StatusString("Error Happen!\t"+err, 413);
	}
	
	
	private long splitLine = 10000;
	private TreeMap<Long,Long> cacheMap;
	private long endLine = 0;
	private boolean fileNotFound = false;
	private String error = "";
	
	/**
	 * Build cache -> TreeMap each splitLine lines using
	 * RandomAccessFile
	 * The Pointers are just long numbers
	 * Record errors if has
	 */	
	private void buildCache() {
		try {
			RandomAccessFile raf = new RandomAccessFile(fileAddress, "r");
			long line = 0;
			cacheMap = new TreeMap<>();
			cacheMap.put(0L, 0L);
			while(raf.readLine()!=null) {
				line++;
				if(line%splitLine==0) {
					cacheMap.put(line, raf.getFilePointer());
				}
			}
			endLine = line;
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(e instanceof FileNotFoundException) {
				fileNotFound = true;
				error = "FileNotFound: " + fileAddress;
			}
			e.printStackTrace();			
		}
	}
	
	/**
	 * Read lines using cache
	 * Find closest lines that appear before the desire line
	 * Loop to find the desire after that
	 * RandomAccessFile should be instantiated each time this method called
	 * to avoid multi-thread confliction
	 * @param line
	 * @return Line that is requested with status code 200 or 413 if exceed
	 */
	public StatusString read(long line) {
		if(fileNotFound) return someError(error);
		if(line>endLine) return someError("Line Number Exceed");
		Long closestKey = cacheMap.floorKey(line-1);
		if(closestKey==null) closestKey = 0L;
		try {
			RandomAccessFile raf = new RandomAccessFile(fileAddress, "r");
			raf.seek(cacheMap.getOrDefault(closestKey,0L));
			while(closestKey<line-1) {
				if(raf.readLine()!=null) closestKey++;
				else break;
			}
			if(closestKey==line-1) {
				String lineString = raf.readLine();
				raf.close();
				return new StatusString(lineString,200);
			}
			else {
				raf.close();
				return someError("Line Number Error");			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof FileNotFoundException) return someError(error);
			if(e instanceof IOException) return someError("IOException");
			return someError("Other Error");
		}
	}
}
